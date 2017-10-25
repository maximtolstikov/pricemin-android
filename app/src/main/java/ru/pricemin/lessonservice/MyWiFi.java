package ru.pricemin.lessonservice;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static android.R.id.list;

/**
 * Created by name on 10/10/17.
 */

public class MyWiFi {

    private MyNotify myNotify;
    private MyFiles myFiles;

    public MyWiFi(Context ctx) {
        this.context = ctx;
    }

    Context context;
    ArrayList<String> points;
    List list;

    final String REMINDER = "reminder";
    final String LOG_TAG = "myLogs";

    private int hore = 0;
    private String sdf = "";

    public ArrayList<String> getPoints() {
        points = new ArrayList<>();
        WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        list = wifi.getScanResults();
        Iterator<ScanResult> iterator = list.iterator();
        while (iterator.hasNext()) {
            String point = iterator.next().SSID.toString();
            points.add(point);
        }
        return points;
    }
    public void enableWiFi(){
        WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);  //Включение WIFI
    }

    // Проверяет включенли WiFi, а так же время когда можно включить уведомление после чего
    // создает файл который указывает на начало отсчета
    public void checkEnabledWiFi() throws IOException {
        WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        Log.d(LOG_TAG, "hour " + hour);
        if (!wifi.isWifiEnabled()) { //Проверяем выключен WIFI тогда уведомление
            if( hour > 10 & hour < 20){
                myNotify = new MyNotify(context);
                myNotify.sendNotifWifiOff();
                myFiles = new MyFiles(context);
                myFiles.createFile(REMINDER);
            }
        }
    }
}
