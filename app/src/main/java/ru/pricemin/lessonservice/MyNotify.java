package ru.pricemin.lessonservice;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

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
}
