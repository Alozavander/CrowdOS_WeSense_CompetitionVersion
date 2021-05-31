package com.hills.mcs_02.sensorfunction;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.hills.mcs_02.DateHelper;
import com.hills.mcs_02.StringStore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/*
 * Edit by Zeron
 * Time: 2020.2.26
 *  Sensing Service, the java class that has the main function of sensing data
 *  Function 1：Record the sensor type that task demands and bind the listener for it.
 *  Function 2：Collect the listened sensor data and store them into SQLite Database.
 *  Function 3：Open or close the sensor according to the task states.
 */

public class SensorService extends Service implements SensorEventListener {
    private static final String TAG = "SensorService";
    private String className = "SensorService";
    private SenseHelper mSenseHelper;
    private SensorManager mSensorManager;
    private HashMap<String, Integer> mSensorWorkMap;
    private HashMap<Integer, ContentValues> mSensorWorkDataMap;
    private HashMap<Integer, Boolean> isSensorDataChangeMap;                               /** Record sensor data storage state and filter the unchanged data */
    private SQLiteDatabase mSensorWritableDb;
    private ContentValues contentValues;
    private Timer timer;

    public SensorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        bindInit();
        return new sensorServiceBinder();
    }

    private void bindInit() {
        Log.i("SensorService", "SensorService is on! " + mSenseHelper.getSensorListTypeIntString());
        String[] typesStrings = mSenseHelper.getSensorListTypeIntString().split(StringStore.DIVIDER1);
        int[] types = new int[typesStrings.length];
        for (int temp = 0; temp < typesStrings.length; temp++) {
            types[temp] = Integer.parseInt(typesStrings[temp]);
            Log.i("SensorService", "type_" + temp + ":  " + types[temp]);
        }
        initTimer();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSenseHelper = new SenseHelper(this);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        /** Use hashMap to record the sensor state that opened or not. Each task will add 1 to demanded sensor and will minus 1 while task is finished. If the value of the map < 0, release the sensor resource. */
        mSensorWorkMap = new HashMap<>();
        /** Initialize the SQLite Database */
        mSensorWritableDb = new SensorSQLiteOpenHelper(this).getWritableDatabase();
        /** Format class used for data storage  */
        contentValues = new ContentValues();

        mSensorWorkDataMap = new HashMap<>();
        isSensorDataChangeMap = new HashMap<>();
    }

    /** Open Service */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String record = intent.getStringExtra("task_sensor_need");
        if(record != null) {
            String[] typesString = record.split(StringStore.DIVIDER1);
            int[] typesInt = new int[typesString.length];
            if (typesString.length > 0) {
                for (int temp = 0; temp < typesString.length; temp++)
                    typesInt[temp] = Integer.parseInt(typesString[temp]);
                senseTaskAcceptSensorOn(typesInt);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void initTimer() {
        Log.i("SensorService", "SensorService Timer starts");
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                /** Working state tag */
                boolean work = false;
                for (int temp : mSensorWorkMap.values()) {
                    if (temp >= 1) work = true;
                }
                Log.i(TAG, "boolean work :" + work);
                /** If working, get the sensor type and contentValues to write in SQLite from hashmap */
                if (work) {
                    Iterator iterator = mSensorWorkDataMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<Integer, ContentValues> entry = (Map.Entry<Integer, ContentValues>) iterator.next();
                        Log.i(TAG, "Now SensorData Store, type:" + entry.getKey());
                        ContentValues temp = entry.getValue();
                        /** If sensor data changed, write them into database. When successfully inserting the data, use -1 to mark it to avoid repeated recording of data in the next iteration. If the insertion fails, the return is -1 */
                        if (isSensorDataChangeMap.get(entry.getKey())) {
                            if (mSensorWritableDb
                                .insert(StringStore.SENSOR_DATATABLE_NAME, null, temp) > 0) {
                                isSensorDataChangeMap.put(entry.getKey(), false);
                                Log.i(TAG, "SQLite DB insert Error! Sensor:" + entry.getKey() + " data record!");
                            } else {
                                Log.i(TAG, "SQLite DB insert Error! Sensor:" + entry.getKey() + " data didn't record!");
                            }
                        }
                    }
                    Log.i(TAG, "Now SensorData Store displaying over.");
                }
            }
        };
        /** per 'period' ms a collection for a sensing data */
        timer.schedule(task, 1, 5000);
        Log.i(TAG, "Timer Task now starts");

    }

    /** If close the Service, unbind all sensor listeners */
    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        mSensorWritableDb.close();
        mSensorManager.unregisterListener(this);
        Log.i("SensorService", "SensorService si off! " + mSenseHelper.getSensorListTypeIntString());
    }

    /** Service running state check */
    private boolean isRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningServiceInfo
                = (ArrayList<ActivityManager.RunningServiceInfo>) activityManager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo info : runningServiceInfo) {
            if (info.service.getClassName().equals(className)) return true;
        }
        return false;
    }

    /** Use sensor type to open sensing */
    private void senseTaskAcceptSensorOn(int[] types) {
        Log.i(TAG, "SenseTaskAccept_SensorOn is called.");
        if (sensorCheck(types)) {
            for (int type : types) {
                if (!mSensorWorkMap.containsKey(type + "")) {
                    mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(type), 1000 * 1000);
                    mSensorWorkMap.put(type + "", 1);
                    Log.i(TAG, "Sensor turn on,type : " + type);
                }
                /** If sensor is already on, then add 1 to the map value which means taskMount of demanding this sensor */
                else {
                    int tasksCount = mSensorWorkMap.get(type + "") + 1;
                    mSensorWorkMap.remove(type + "");
                    mSensorWorkMap.put(type + "", tasksCount);
                    Log.i(TAG, "Sensor is at work,type : " + type + "now task count : " + tasksCount);
                }
            }
        } else {
            Log.i(TAG, "There are some sensors that the device don't have!");
        }
    }

    /** Compulsorily close the sensors based on the type parameter pTypes */
    private void senseTaskFinishSensorOff(int[] pTypes) {
        for (int type : pTypes) {
            mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(type));
            mSensorWorkMap.remove(type + "");
        }
    }

    /** Multi-sensor check. Return false if the device don't have one of them. */
    private boolean sensorCheck(int[] types) {
        boolean[] contain = mSenseHelper.containSensors(types);
        boolean isHave = true;
        for (boolean yn : contain) {
            if (yn == false) isHave = false;
        }
        return isHave;
    }

    /** Single sensor check */
    private boolean sensorCheck(int type) {
        return mSenseHelper.containSensor(type);
    }


    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }


    /** When sensor data changed, write them to map waiting handle and storage */
    @Override
    public void onSensorChanged(SensorEvent event) {
        ContentValues tempContentValues = new ContentValues();
        tempContentValues.put(StringStore.SENSOR_DATATABLE_SENSE_TYPE, event.sensor.getType());
        /** Use the DateHelper class to help convert the current time to String  */
        tempContentValues.put(StringStore.SENSOR_DATATABLE_SENSE_TIME, DateHelper.getSimpleDateFormat().format(new Date(System.currentTimeMillis())));
        tempContentValues.put(StringStore.SENSOR_DATATABLE_SENSE_DATA_1, event.values[0]);
        if (event.values.length > 1) {
            tempContentValues.put(StringStore.SENSOR_DATATABLE_SENSE_DATA_2, event.values[1]);
            if (event.values.length > 2) {
                tempContentValues.put(StringStore.SENSOR_DATATABLE_SENSE_DATA_3, event.values[2]);
            }
        }
        mSensorWorkDataMap.put(event.sensor.getType(), tempContentValues);
        isSensorDataChangeMap.put(event.sensor.getType(), true);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class sensorServiceBinder extends Binder implements SensorServiceInterface {
        @Override
        public void binderSensorOn(int[] types) {
            /** Method for communication the activity and Service */
            Log.i("SensorService", "Remote the Sensor Service's method 'SenseTaskAccept_SensorOn' through SensorService_Interface;s method 'binder_sensorOn' with SensorService_Binder");
            senseTaskAcceptSensorOn(types);
        }

        @Override
        public void binderSensorOff(int[] types) {
            /** Interface method. Close the sensor */
            Log.i("SensorService", "Remote the Sensor Service's method 'SenseTaskAccept_SensorOn' through SensorService_Interface;s method 'binder_sensorOn' with SensorService_Binder");
            senseTaskFinishSensorOff(types);
        }
    }
}
