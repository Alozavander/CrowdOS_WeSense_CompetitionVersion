package com.hills.mcs_02.saveFile;

import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**  SQLite data conversion file function class, currently supports CSV and text format */
public class FileExport {
    static String sParentFileName = "SensorDataStore";         /** Root path(can be changed) */
    static String sFileName = "senseData.csv";         /** File Name(can be changed) */
    public static File exportToCSV(Cursor cur, String fileName,String pParentFileName) {
        if(pParentFileName!=null) sParentFileName = pParentFileName;
        if(fileName!=null) sFileName = fileName;
        int rowCount = 0;
        int colCount = 0;
        FileWriter fw;
        BufferedWriter bfw;
        File sdCardDir = Environment.getExternalStorageDirectory();
        File parentFile = new File(sdCardDir, sParentFileName);
        if(!parentFile.exists()) parentFile.mkdirs();
        File saveFile = new File(parentFile, sFileName);
        if(saveFile.exists() && saveFile.isFile()) {
            saveFile.delete();
        }
        try {
            rowCount = cur.getCount();
            colCount = cur.getColumnCount();
            fw = new FileWriter(saveFile);
            bfw = new BufferedWriter(fw);
            if (rowCount > 0) {
                cur.moveToFirst();
                /** Write the column name of csv File*/
                for (int temp = 0; temp < colCount; temp++) {
                    if (temp != colCount - 1)
                        bfw.write(cur.getColumnName(temp) + ',');
                    else
                        bfw.write(cur.getColumnName(temp));
                }
                bfw.newLine();
                /** Write data */
                for (int i = 0; i < rowCount; i++) {
                    cur.moveToPosition(i);
                    for (int j = 0; j < colCount; j++) {
                        if (j != colCount - 1)
                            bfw.write(cur.getString(j) + ',');
                        else {
                            String tempS = cur.getString(j);
                            if(tempS == null) bfw.write("null");
                            else bfw.write(tempS);
                        }
                    }
                    bfw.newLine();
                }
            }
            bfw.flush();
            bfw.close();
            Log.v("数据导出", "数据导出完成！");
        } catch (IOException exp) {
            exp.printStackTrace();
        } finally {
            cur.close();
        }
        return saveFile;
    }

    public static File exportToTextForEachSensor(Cursor cur, String fileName,String pParentFileName) {
        if(pParentFileName!=null) sParentFileName = pParentFileName;
        if(fileName!=null) sFileName = fileName;
        int rowCount = 0;
        int colCount = 0;
        FileWriter fw;
        BufferedWriter bfw;
        File sdCardDir = Environment.getExternalStorageDirectory();
        File parentFile = new File(sdCardDir, sParentFileName);
        if(!parentFile.exists()) parentFile.mkdirs();
        File saveFile = new File(parentFile, sFileName);
        if(saveFile.exists() && saveFile.isFile()) {
            saveFile.delete();
        }
        try {
            rowCount = cur.getCount();
            colCount = cur.getColumnCount();
            fw = new FileWriter(saveFile);
            bfw = new BufferedWriter(fw);
            if (rowCount > 0) {
                cur.moveToFirst();
                /** Write the data */
                for (int i = 0; i < rowCount; i++) {
                    cur.moveToPosition(i);
                    bfw.write(cur.getString(1) + "/");
                    for (int j = 2; j < colCount; j++) {
                        if (j != colCount - 1)
                            bfw.write(cur.getString(j) + '_');
                        else {
                            String tempS = cur.getString(j);
                            if(tempS == null) bfw.write("null");
                            else bfw.write(tempS);
                        }
                    }
                    bfw.newLine();
                }
            }
            bfw.flush();
            bfw.close();
            Log.v("数据导出", "数据导出完成！");
        } catch (IOException exp) {
            // TODO Auto-generated catch block
            exp.printStackTrace();
        } finally {
            cur.close();
        }
        return saveFile;
    }
}
