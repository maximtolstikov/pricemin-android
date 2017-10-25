package ru.pricemin.lessonservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by name on 10/10/17.
 */

public class MyNotify {

    public MyNotify(Context ctx){
        this.context = ctx;
    }
    private Context context;
    private static final int NOTIFY_ID = 101;

    void sendNotif() {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notif = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Привет Дом!")
                .setContentText("С возвращением!")
                .setSound(alarmSound)
                .setVibrate(new long[] { 1000, 1000, 1000 });

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(NOTIFY_ID, notif.build());
    }

    void sendNotifWifiOff() {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notif = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.freepik)
                .setContentTitle("Выключен WiFi!")
                .setContentText("Для поиска магазина требуется WIFI.")
                .setSound(alarmSound)
                .setVibrate(new long[] { 1000, 1000, 1000 });

        Intent resultIntent = new Intent(context, WiFiOffActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
// Creates the PendingIntent
        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

// Puts the PendingIntent into the notification builder
        notif.setContentIntent(notifyPendingIntent);
        notif.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(NOTIFY_ID, notif.build());
    }
}
