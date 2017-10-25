package ru.pricemin.lessonservice;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by name on 17/10/17.
 */

public class MyFiles {

    public MyFiles(Context ctx){
        this.context = ctx;
    }

    final String LOG_TAG = "myLogs";
    private Context context;
    Boolean fileExist = false;
    File file;

    public void createFile(String nameFile) throws IOException {
        file = new File(context.getFilesDir(), nameFile);
        FileWriter writer = new FileWriter(file);
        writer.write(0);
        writer.close();
        Log.d(LOG_TAG, "create file: " + nameFile);
        }

    public boolean checkFile(String nameFile){
        file = new File(context.getFilesDir(), nameFile);
        fileExist = file.exists();
        return fileExist;
    }

    public void removeFile(String nameFile){
        file = new File(context.getFilesDir(), nameFile);
        file.delete();
    }
    public long getLastModifiedFile(String nameFile){
        file = new File(context.getFilesDir(), nameFile);
        long modifiedTime = file.lastModified();
        return modifiedTime;
    }
}
