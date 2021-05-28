package com.hills.mcs_02.sensorFunction;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import com.hills.mcs_02.R;
import com.hills.mcs_02.StringStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** Sense function helper class */
public class SenseHelper {
    private final String TAG = "SenseHelper";
    private static Context mContext;
    private List<Integer> mSensorTypeList;
    private String mDivider;

    public SenseHelper(Context context) {
        mContext = context;
        mSensorTypeList = new ArrayList<Integer>();
        List<String> sensorNameList = new ArrayList<>();
        mDivider = StringStore.DIVIDER1;
        String tempString = getSensorListTypeIntString();
        Log.i(TAG, "Now SenseHelp Create, sensorTypeGet: " + tempString);
        /** If the tempString is default error value, then reload all sensors' type */
        if (tempString.equals(StringStore.SP_STRING_ERROR)) {
            Log.i(TAG, "Now SenseHelp Get The Sensor List of the Device");
            SensorManager sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
            List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
            Integer[] typeList = new Integer[sensorList.size()];
            for (int temp = 0; temp < sensorList.size(); temp++) {
                Sensor sensor = sensorList.get(temp);
                typeList[temp] = sensor.getType();
                mSensorTypeList.add(typeList[temp]);
                sensorNameList.add(sensor.getName());
            }
            Log.i(TAG,"sensorName_List:" + sensorNameList.toString());
            /** print out the sensor list information */
            if (storeSensorTypeInfo(typeList))
                Log.i(TAG, "mSensorType_List has been wrote into the SQLite");
            else Log.i(TAG, "mSensorType_List has not been wrote into the SQLite");
        }
        /** Handle the string with default divider */
        else {
            Log.i(TAG, "Sensor List Has Been Wrote");
            String[] typeInt = tempString.split(mDivider);
            for (String string : typeInt) {
                int i = Integer.parseInt(string);
                Log.i(TAG, "typeInt:" + string);
                mSensorTypeList.add(i);
            }
        }
    }

    /**  Return a string containing all sensor names. If failing to get, return StringStore.SP_STRING_ERROR  */
    public String getSensorListName() {
        return mContext.getSharedPreferences(StringStore.SENSOR_SP_XML_NAME, Context.MODE_PRIVATE).
                getString(StringStore.SENSOR_DATA_SP_LIST_STRING, StringStore.SP_STRING_ERROR);
    }

    /** Return a string containing all sensor types(ints). If failing to get, return StringStore.SP_STRING_ERROR */
    public String getSensorListTypeIntString() {
        //测试所用mContext.getSharedPreferences(StringStore.SensorSP_XMLName, Context.MODE_PRIVATE).edit().remove(StringStore.SensorDataSP_List_TypeInt).commit();
        return mContext.getSharedPreferences(StringStore.SENSOR_SP_XML_NAME, Context.MODE_PRIVATE).
                getString(StringStore.SENSOR_DATA_SP_LIST_TYPEINT, StringStore.SP_STRING_ERROR);
    }

    /** Return an array of string containing all sensor name(string) */
    public String[] getSensorListTypeIntStrings() {
        String tempString = getSensorListTypeIntString();
        String[] types = tempString.split(mDivider);
        if(types.length <= 0){
            return new String[]{"Null"};
        }else {
            String[] sensors = new String[types.length];
            for(int temp = 0; temp < sensors.length; temp ++){
                sensors[temp] = sensorType2XmlName(mContext, Integer.parseInt(types[temp]));
            }
            return sensors;
        }
    }


    /** Return an array of string containing all sensor types(ints) */
    public int[] getSensorListTypeIntIntegers() {
        String temp = getSensorListTypeIntString();
        String[] tempStrings = temp.split(mDivider);
        int[] result;
        if (temp.equals(StringStore.SP_STRING_ERROR)) {
            Toast.makeText(mContext, "Sensor List SP Error by SenseHelper", Toast.LENGTH_SHORT).show();
            result = new int[1];
            result[0] = -1;
        } else {
            result = new int[tempStrings.length];
            for (int i = 0; i < tempStrings.length; i++) {
                result[i] = Integer.parseInt(tempStrings[i]);
            }
        }
        return result;
    }

    /** Check the device has specific sensor or not. */
    public boolean containSensor(int sensorType) {
        if (mSensorTypeList.size() <= 0) {
            Log.i(TAG, "mSensorType_List's size is :" + mSensorTypeList.size());
            return false;
        } else {
            Log.i(TAG, "mSensorType_List:" + mSensorTypeList.toString());
            if (mSensorTypeList.contains(sensorType)) return true;
            else {
                Log.i(TAG, "The device dont have sensor :" + sensorType);
                return false;
            }
        }
    }

    /** Check the device has specific sensors or not. Return an boolean array which index means the sensor location in sensor list */
    public boolean[] containSensors(int[] sensorTypeList) {
        boolean[] results = new boolean[sensorTypeList.length];
        for (int temp = 0; temp < sensorTypeList.length; temp++) {
            if (containSensor(sensorTypeList[temp])) results[temp] = true;
            else results[temp] = false;
        }
        return results;
    }

    /**  Store sensor Type data, using Divider character segmentation specified uniformly in StringStore. */
    public boolean storeSensorTypeInfo(Integer[] sensorTypes) {
        if (sensorTypes.length <= 0) return false;
        else {
            /** Eliminate redundancy */
            Set<Integer> typeSet = new HashSet<>();
            for(int i : sensorTypes) typeSet.add(i);
            /** Sort the list */
            LinkedList<Integer> lLinkedList = new LinkedList<>(typeSet);
            Collections.sort(lLinkedList);

            Iterator lIterator = lLinkedList.iterator();
            String record = lIterator.next() + "";
            while(lIterator.hasNext()){
                record = record + mDivider + lIterator.next() ;
            }
            return mContext.getSharedPreferences(StringStore.SENSOR_SP_XML_NAME, Context.MODE_PRIVATE).edit()
                    .putString(StringStore.SENSOR_DATA_SP_LIST_TYPEINT, record)
                    .commit();
        }
    }

    /** Convert sensorType list(ints) to sensor name list(strings) */
    public String[] sensorListNameStrings1TypeInts(int[] typeList) {
        String[] lStrings = new String[typeList.length];
        for(int temp = 0; temp < typeList.length; temp ++){
            lStrings[temp] = sensorType2XmlName(mContext, typeList[temp]);
        }
        return lStrings;
    }

    public int[] sensorListNameStrings2TypeInts(String[] nameList) {
        int[] lInts = new int[nameList.length];
        for(int temp = 0; temp < nameList.length; temp ++){
            lInts[temp] = sensorType2XmlName(mContext, nameList[temp]);
        }
        return lInts;
    }

    /** Convert a string name of sensor which is defined in string.xml resource file into sensorType(int)*/
    public static int sensorType2XmlName(Context pContext, String xmlName) {
        int sensorType = -2;                 /** Defalut value, -1 means all sensors */
        if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_ACCELEROMETER))) sensorType = Sensor.TYPE_ACCELEROMETER;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_MAGNETIC_FIELD))) sensorType = Sensor.TYPE_MAGNETIC_FIELD;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_ORIENTATION))) sensorType = Sensor.TYPE_ORIENTATION;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_GYROSCOPE))) sensorType = Sensor.TYPE_GYROSCOPE;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_LIGHT))) sensorType = Sensor.TYPE_LIGHT;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_PRESSURE))) sensorType = Sensor.TYPE_PRESSURE;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_TEMPERATURE))) sensorType = Sensor.TYPE_TEMPERATURE;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_PROXIMITY))) sensorType = Sensor.TYPE_PROXIMITY;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_GRAVITY))) sensorType = Sensor.TYPE_GRAVITY;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_LINEAR_ACCELERATION))) sensorType = Sensor.TYPE_LINEAR_ACCELERATION;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_ROTATION_VECTOR))) sensorType = Sensor.TYPE_ROTATION_VECTOR;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_RELATIVE_HUMIDITY))) sensorType = Sensor.TYPE_RELATIVE_HUMIDITY;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_AMBIENT_TEMPERATURE))) sensorType = Sensor.TYPE_AMBIENT_TEMPERATURE;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_MAGNETIC_FIELD_UNCALIBRATED))) sensorType = Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_GAME_ROTATION_VECTOR))) sensorType = Sensor.TYPE_GAME_ROTATION_VECTOR;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_GYROSCOPE_UNCALIBRATED))) sensorType = Sensor.TYPE_GYROSCOPE_UNCALIBRATED;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_SIGNIFICANT_MOTION))) sensorType = Sensor.TYPE_SIGNIFICANT_MOTION;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_STEP_DETECTOR))) sensorType = Sensor.TYPE_STEP_DETECTOR;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_STEP_COUNTER))) sensorType = Sensor.TYPE_STEP_COUNTER;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_GEOMAGNETIC_ROTATION_VECTOR))) sensorType = Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_HEART_RATE))) sensorType = Sensor.TYPE_HEART_RATE;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_HEART_BEAT))) sensorType = Sensor.TYPE_HEART_BEAT;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_POSE_6DOF))) sensorType = Sensor.TYPE_POSE_6DOF;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_STATIONARY_DETECT))) sensorType = Sensor.TYPE_STATIONARY_DETECT;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_MOTION_DETECT))) sensorType = Sensor.TYPE_MOTION_DETECT;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_LOW_LATENCY_OFFBODY_DETECT))) sensorType = Sensor.TYPE_LOW_LATENCY_OFFBODY_DETECT;
        else if(xmlName.equals(pContext.getString(R.string.Sensor_TYPE_ACCELEROMETER_UNCALIBRATED))) sensorType = Sensor.TYPE_ACCELEROMETER_UNCALIBRATED;
        return sensorType;
    }

    /** Convert sensorType(int) into a string name which is defined in string.xml resource file */
    public static String sensorType2XmlName(Context pContext, Integer sensorType) {
        String result = StringStore.SP_STRING_ERROR;
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                result = pContext.getString(R.string.Sensor_TYPE_ACCELEROMETER);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                result = pContext.getString(R.string.Sensor_TYPE_MAGNETIC_FIELD);
                break;
            case Sensor.TYPE_ORIENTATION:
                result = pContext.getString(R.string.Sensor_TYPE_ORIENTATION);
                break;
            case Sensor.TYPE_GYROSCOPE:
                result = pContext.getString(R.string.Sensor_TYPE_GYROSCOPE);
                break;
            case Sensor.TYPE_LIGHT:
                result = pContext.getString(R.string.Sensor_TYPE_LIGHT);
                break;
            case Sensor.TYPE_PRESSURE:
                result = pContext.getString(R.string.Sensor_TYPE_PRESSURE);
                break;
            case Sensor.TYPE_TEMPERATURE:
                result = pContext.getString(R.string.Sensor_TYPE_TEMPERATURE);
                break;
            case Sensor.TYPE_PROXIMITY:
                result = pContext.getString(R.string.Sensor_TYPE_PROXIMITY);
                break;
            case Sensor.TYPE_GRAVITY:
                result = pContext.getString(R.string.Sensor_TYPE_GRAVITY);
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                result = pContext.getString(R.string.Sensor_TYPE_LINEAR_ACCELERATION);
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                result = pContext.getString(R.string.Sensor_TYPE_ROTATION_VECTOR);
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                result = pContext.getString(R.string.Sensor_TYPE_RELATIVE_HUMIDITY);
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                result = pContext.getString(R.string.Sensor_TYPE_AMBIENT_TEMPERATURE);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                result = pContext.getString(R.string.Sensor_TYPE_MAGNETIC_FIELD_UNCALIBRATED);
                break;
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                result = pContext.getString(R.string.Sensor_TYPE_GAME_ROTATION_VECTOR);
                break;
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                result = pContext.getString(R.string.Sensor_TYPE_GYROSCOPE_UNCALIBRATED);
                break;
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                result = pContext.getString(R.string.Sensor_TYPE_SIGNIFICANT_MOTION);
                break;
            case Sensor.TYPE_STEP_DETECTOR:
                result = pContext.getString(R.string.Sensor_TYPE_STEP_DETECTOR);
                break;
            case Sensor.TYPE_STEP_COUNTER:
                result = pContext.getString(R.string.Sensor_TYPE_STEP_COUNTER);
                break;
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                result = pContext.getString(R.string.Sensor_TYPE_GEOMAGNETIC_ROTATION_VECTOR);
                break;
            case Sensor.TYPE_HEART_RATE:
                result = pContext.getString(R.string.Sensor_TYPE_HEART_RATE);
                break;
            case Sensor.TYPE_HEART_BEAT:
                result = pContext.getString(R.string.Sensor_TYPE_HEART_BEAT);
                break;
            case Sensor.TYPE_POSE_6DOF:
                result = pContext.getString(R.string.Sensor_TYPE_POSE_6DOF);
                break;
            case Sensor.TYPE_STATIONARY_DETECT:
                result = pContext.getString(R.string.Sensor_TYPE_STATIONARY_DETECT);
                break;
            case Sensor.TYPE_MOTION_DETECT:
                result = pContext.getString(R.string.Sensor_TYPE_MOTION_DETECT);
                break;
            case Sensor.TYPE_LOW_LATENCY_OFFBODY_DETECT:
                result = pContext.getString(R.string.Sensor_TYPE_LOW_LATENCY_OFFBODY_DETECT);
                break;
            case Sensor.TYPE_ACCELEROMETER_UNCALIBRATED:
                result = pContext.getString(R.string.Sensor_TYPE_ACCELEROMETER_UNCALIBRATED);
                break;
            default:
                break;
        }
        return result;
    }
}
