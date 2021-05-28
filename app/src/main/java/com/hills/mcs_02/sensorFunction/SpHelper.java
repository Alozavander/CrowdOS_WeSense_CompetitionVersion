package com.hills.mcs_02.sensorFunction;

import android.content.Context;
import android.content.SharedPreferences;

/** Helper to store and get data from sharedPreference */
public class SpHelper {
    private String SpName;
    private Context mContext;
    private SharedPreferences sp;

    private SpHelper(String spName, Context context){
        SpName = spName;
        mContext = context;
        sp = mContext.getSharedPreferences(SpName, Context.MODE_PRIVATE);
    }

    public boolean dataStore(String key, String data){
        return sp.edit().putString(key,data).commit();
    }
}
