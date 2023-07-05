package com.ucuncuoglu.zeynepiremtermprj;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ToDoListIntentService extends IntentService {

    private int mNotificationId = 1;
    private Notification mNotification;

    public static final String CHANNEL_ID = "ServiceChannel";
    String insertNotify;

    public ToDoListIntentService() {
        super("ToDoListIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("Intent Service", "Service Started");

        insertNotify = intent.getStringExtra("notification");
        Log.d("Intent service", insertNotify);

        Intent pintent = new Intent(this, ShoppingActivity.class);
        //pintent.putExtra("notificationID", mNotificationId);

        // A PendingIntent is a token that you give to another application (e.g. Notification Manager,
        // Alarm Manager or other 3rd party applications), which allows this other application to use
        // the permissions of your application to execute a predefined piece of code.
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, pintent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setTicker("Text on the status bar")
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentTitle("To-Do List Notification")
                .setContentText(insertNotify)
                .setContentInfo("Notification Detail").
                setContentIntent(pendingIntent);
        notificationManager.notify(/*notification id*/1, notificationBuilder.build());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Intent Service", "onDestroy");
    }

}