package ru.pricemin.lessonservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by name on 03/10/17.
 */

public class MyService extends Service {

    final String LOG_TAG = "myLogs";
    final String[] CLIENTS = {"TP-LINK_7FA15C", "test1", "test"}; //список извесных точек
    private ArrayList<String> points; //возвращенные точки после скакнирования
    private ArrayList<String> templatePoints; //Для временного хранения точек
    public boolean isRunning = false; //Для проверки на запущеный сервис или нет

    WriteReadFile writeReadFile = new WriteReadFile(this);
    MyNotify myNotify = new MyNotify(this);
    MyWiFi myWiFi = new MyWiFi(this);

    public void onCreate(){
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        if (isRunning){
            Log.d(LOG_TAG, "Service already running!");
        } else {
            Log.d(LOG_TAG, "onStartCommand");
            isRunning = true;
            runService();
        }
        return  START_STICKY;
    }

    public void onDestroy(){
        super.onDestroy();
        isRunning = false;
        Log.d(LOG_TAG, "onDestroy");
    }

    private void runService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //templatePoints = new ArrayList<>();

                while (true) {
                    points = myWiFi.getPoints();
                    for (String client : CLIENTS) {
                        Log.d(LOG_TAG, client);
                        templatePoints = templatePointsGet();
                        if (points.contains(client)) {
                            Log.d(LOG_TAG, client + " avialable");
                            if (templatePoints.contains(client)) {
                                Log.d(LOG_TAG, client + "conteins in tmp");
                                try {
                                    TimeUnit.SECONDS.sleep(5);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                continue;
                            } else {
                                templatePoints.add(client);
                                writeReadFile.writeFile(templatePoints);
                                myNotify.sendNotif();
                                Log.d(LOG_TAG, client + " add in tmp");
                                try {
                                    TimeUnit.SECONDS.sleep(5);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (templatePoints.contains(client)) {
                            templatePoints.remove(client);
                            writeReadFile.writeFile(templatePoints);
                            Log.d(LOG_TAG, client + " remove from tmp");
                        }
                    }
                    Log.d(LOG_TAG, "scan");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public ArrayList<String> templatePointsGet(){
        try {
            templatePoints = writeReadFile.readFile();
            if (templatePoints.isEmpty()){
                Log.d(LOG_TAG, "tmp empty first");
            }
        } catch (Exception e){
            Log.d(LOG_TAG, "File not exist!");
            templatePoints = new ArrayList<>();
        }
        return templatePoints;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
