package com.hills.mcs_02.sensorfunction;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.hills.mcs_02.StringStore;
import com.hills.mcs_02.exportfile.FileExport;

import java.io.File;

import static android.widget.Toast.LENGTH_LONG;

/*
 * Edit by Liao JiaHao
 * Time: 2020.7.9
 * Description : Class of the entire SenseFunction module
 * Function：
 * 1. Open/Close SenseService
 * 2. Open/Close sensor to collect data
 * 3. View sensing data
 * 4. Delete sensing data
 * 5. Output the sensing data to a file(csv)
 */
public class SenseFunction {
    private static final String TAG = "SenseFunction";
    private Context mContext;
    private SensorServiceInterface sensorServiceInterface;
    private boolean isBind;
    private ServiceConnection conn;
    public SenseHelper senseHelper;
    public SQLiteDatabase mReadableDatabase;

    /** Bind/Open Service using Context*/
    public SenseFunction(Context pContext) {
        mContext = pContext;
    }

    /** Open Sense Service */
    public void onSenseService() {
        Log.i(TAG, "=======Now Init the sensor Service...===========");
        /** Initiate the senseHelper */
        senseHelper = new SenseHelper(mContext);
        Intent intent = new Intent(mContext, SensorService.class);
        if (conn == null) {
            Log.i(TAG, "===========connection creating...============");
            conn = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    sensorServiceInterface = (SensorServiceInterface) service;
                    Log.i(TAG, "sensorService_interface connection is done.");
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    Log.i(TAG, "sensorService disconnected.");
                }
            };
        } else {
            Log.i(TAG, "===============sensorService connection exits.================");
        }
        /** bind the Service */
        isBind = mContext.getApplicationContext().bindService(intent, conn, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "=============SensorService has been bound :" + isBind + "==============");
    }

    /** Close SenseService */
    public void offSenseService() {
        if (isBind) {
            isBind = false;
            mContext.getApplicationContext().unbindService(conn);
        }
    }

    /** Start sensing with specific sensor types */
    public void onSensor(int[] sensorTypeArray) {
        if (isBind) {
            if (sensorServiceInterface != null) {
                sensorServiceInterface.binderSensorOn(sensorTypeArray);
                Log.i(TAG, "SensorService's sensorOn has been remote.");
            } else {
                Toast.makeText(mContext, "sensorService_interface is null. Please init SenseService use On_SenseService method.", LENGTH_LONG).show();
                Log.i(TAG, "sensorService_interface is null. Please init SenseService use On_SenseService method.");
            }
        } else {
            Toast.makeText(mContext, "SensorService has been bound :" + isBind + ". Please  init SenseService use On_SenseService method.", LENGTH_LONG).show();
            Log.i(TAG, "SensorService has been bound :" + isBind + ". Please  init SenseService use On_SenseService method.");
        }
    }

    /** Turn off the sensor*/
    public void offSensor(int[] sensorTypeArray) {
        if (isBind) {
            if (sensorServiceInterface != null) {
                sensorServiceInterface.binderSensorOff(sensorTypeArray);
                Log.i(TAG, "SensorService's sensorOff has been remote.");
            } else {
                Toast.makeText(mContext, "sensorService_interface is null. Please init SenseService use On_SenseService method.", LENGTH_LONG).show();
                Log.i(TAG, "sensorService_interface is null. Please init SenseService use On_SenseService method.");
            }
        } else {
            Toast.makeText(mContext, "SensorService has been bound :" + isBind + ". Please  init SenseService use On_SenseService method.", LENGTH_LONG).show();
            Log.i(TAG, "SensorService has been bound :" + isBind + ". Please  init SenseService use On_SenseService method.");
        }
    }

    /** Return the cursor of database */
    public Cursor querySenseData(int pSensorType) {
        SQLiteDatabase db = new SensorSQLiteOpenHelper(mContext).getReadableDatabase();
        Cursor cur = db.query(StringStore.SENSOR_DATATABLE_NAME,
                new String[]{StringStore.SENSOR_DATATABLE_ID,
                        StringStore.SENSOR_DATATABLE_SENSE_TYPE,
                        StringStore.SENSOR_DATATABLE_SENSE_TIME,
                        StringStore.SENSOR_DATATABLE_SENSE_DATA_1,
                        StringStore.SENSOR_DATATABLE_SENSE_DATA_2,
                        StringStore.SENSOR_DATATABLE_SENSE_DATA_3},
                StringStore.SENSOR_DATATABLE_SENSE_TYPE + "=?", new String[]{pSensorType + ""}, null, null, null);
        return cur;
    }

    /** Delete the sensor data from the sqlite */
    public int sqliteDelete(int sensorType, String startTime, String endTime) {
        SQLiteDatabase db = new SensorSQLiteOpenHelper(mContext).getReadableDatabase();
        int lI = -1;
        if (startTime == null && endTime != null) {
            /** whereclaus */
            String whereClaus = StringStore.SENSOR_DATATABLE_SENSE_TYPE + "=?" + " AND " + StringStore.SENSOR_DATATABLE_SENSE_TIME
                + " < ?";
            lI = db.delete(StringStore.SENSOR_DATATABLE_NAME,
                    whereClaus, new String[]{sensorType + "", endTime});
            Log.e(TAG, "Where Claus is : " + whereClaus);
            Log.e(TAG, "EndTime is : " + endTime);
            Log.e(TAG, "Delete result : " + lI);
        } else if (startTime != null && endTime == null) {
            String whereClaus = StringStore.SENSOR_DATATABLE_SENSE_TYPE + "=?" + " AND " + StringStore.SENSOR_DATATABLE_SENSE_TIME
                + " > ?";
            lI = db.delete(StringStore.SENSOR_DATATABLE_NAME,
                    whereClaus, new String[]{sensorType + "", startTime});
            Log.e(TAG, "Where Claus is : " + whereClaus);
            Log.e(TAG, "StartTime is : " + startTime);
            Log.e(TAG, "Delete result : " + lI);
        } else if (startTime == null && endTime == null) {
            String whereClaus = StringStore.SENSOR_DATATABLE_SENSE_TYPE + "=?";
            lI = db.delete(StringStore.SENSOR_DATATABLE_NAME,
                    whereClaus, new String[]{sensorType + ""});
            Log.e(TAG, "Where Claus is : " + whereClaus);
            Log.e(TAG, "Delete result : " + lI);
        } else {
            String whereClaus = StringStore.SENSOR_DATATABLE_SENSE_TYPE + "=?" + " AND " + StringStore.SENSOR_DATATABLE_SENSE_TIME
                + " > ? AND " + StringStore.SENSOR_DATATABLE_SENSE_TIME + " < ?";
            lI = db.delete(StringStore.SENSOR_DATATABLE_NAME,
                    whereClaus, new String[]{sensorType + "", startTime, endTime});
            Log.e(TAG, "Where Claus is : " + whereClaus);
            Log.e(TAG, "StartTime is : " + startTime);
            Log.e(TAG, "EndTime is : " + endTime);
            Log.e(TAG, "Delete result : " + lI);
        }
        db.close();
        return lI;
    }

    /** Output method. Export sensing data to a file. Return the file with default name senseData.csv and default path "sd/SensorDataStore/senseData.csv" */
    public File storeDataToCSV(int sensorType, String fileName, String fileParentPath) {
        File saveFile;
        Cursor cur = new SensorSQLiteOpenHelper(mContext).getReadableDatabase().query(StringStore.SENSOR_DATATABLE_NAME,
                new String[]{StringStore.SENSOR_DATATABLE_SENSE_TYPE,
                        StringStore.SENSOR_DATATABLE_SENSE_TIME,
                        StringStore.SENSOR_DATATABLE_SENSE_DATA_1,
                        StringStore.SENSOR_DATATABLE_SENSE_DATA_2,
                        StringStore.SENSOR_DATATABLE_SENSE_DATA_3},
                StringStore.SENSOR_DATATABLE_SENSE_TYPE + "=?", new String[]{sensorType+""}, null, null, null);
        saveFile = FileExport.exportToCSV(cur, fileName, fileParentPath);
        Toast.makeText(mContext, "Output finishing. The file path is :" + saveFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
        return saveFile;
    }
}
