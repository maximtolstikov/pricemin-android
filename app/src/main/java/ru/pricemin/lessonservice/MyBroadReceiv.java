package ru.pricemin.lessonservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by name on 06/10/17.
 */

public class MyBroadReceiv extends BroadcastReceiver{

    final String LOG_TAG = "myLogs";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "onReceive " + intent.getAction());
        context.startService(new Intent(context, MyService.class));
    }
}
