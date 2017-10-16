package ru.pricemin.lessonservice;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.R.id.list;

/**
 * Created by name on 10/10/17.
 */

public class MyWiFi {

    public MyWiFi(Context ctx){
        this.context = ctx;
    }
    Context context;
    ArrayList<String> points;
    List list;

    public ArrayList<String> getPoints() {
        points = new ArrayList<>();
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        list = wifi.getScanResults();
        Iterator<ScanResult> iterator = list.iterator();
        while (iterator.hasNext()) {
            String point = iterator.next().SSID.toString();
            points.add(point);
        }
        return points;
    }
}
