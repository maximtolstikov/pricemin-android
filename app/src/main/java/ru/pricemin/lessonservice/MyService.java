package ru.pricemin.lessonservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by name on 03/10/17.
 */

public class MyService extends Service {

    final String LOG_TAG = "myLogs";
    final String[] CLIENTS = {"TP-LINK_7FA15C"}; //список извесных точек
    final int SLEEP = 60;
    final long WAITING = 30 * 60 * 1000; //(секунд)время ожидания удаления файла после которого
                                      // возможно новое уведомление
    private ArrayList<String> points; //возвращенные точки после скакнирования
    public boolean isRunning = false; //Для проверки на запущеный сервис или нет
    private String reminder = "reminder"; //Название файла для напоминания

    MyNotify myNotify = new MyNotify(this);
    MyWiFi myWiFi = new MyWiFi(this);
    MyFiles fl = new MyFiles(this);

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

                while (true) {
                    points = myWiFi.getPoints();
                    for (String client : CLIENTS) {
                        Log.d(LOG_TAG, client);
                        if (points.contains(client)) {
                            if (fl.checkFile(client)) {
                                Log.d(LOG_TAG, client + "conteins in tmp");
                                try {
                                    TimeUnit.SECONDS.sleep(SLEEP);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                continue;
                            } else {
                                Log.d(LOG_TAG, client + " add in tmp");
                                try {
                                    fl.createFile(client);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                myNotify.sendNotif();
                                try {
                                    TimeUnit.SECONDS.sleep(SLEEP);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (fl.checkFile(client)) {
                            if (getDelta(client) > WAITING) {
                                fl.removeFile(client);
                            }
                        }
                    }
                    Log.d(LOG_TAG, "scan");
                    if(fl.checkFile(reminder)) {
                        Log.d(LOG_TAG, "reminder " + getDelta(reminder) + " (" + WAITING + " ) ");
                        if (getDelta(reminder) > WAITING) {
                            fl.removeFile(reminder);
                            try {
                                myWiFi.checkEnabledWiFi(); //проверяем включен ли WiFi
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        try {
                            myWiFi.checkEnabledWiFi(); //проверяем включен ли WiFi
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        TimeUnit.SECONDS.sleep(SLEEP);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //Вычисляет разницу текущего времени и времени создания файла
    long getDelta(String nameFile){
        Date date = new Date();
        long lastModifiedFile = fl.getLastModifiedFile(nameFile);
        long delta =  date.getTime() - lastModifiedFile;
        return delta;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
