package com.example.androidtodolist;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver
{
    public NotificationManager myNotificationManager;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationCompat.Builder customActionReceivedNotificationBuilder = new NotificationCompat.Builder(context, "notifychannel");
        myNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification customActionReceivedNotification = customActionReceivedNotificationBuilder.setAutoCancel(true)
                .setContentTitle(intent.getStringExtra("category") + " : " + intent.getStringExtra("title") )
                .setContentText(intent.getStringExtra("message")+intent.getStringExtra("date"))
                .setColor(Color.RED)
                .setSmallIcon(R.drawable.ic_baseline_menu_book_24)
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        myNotificationManager.notify(1, customActionReceivedNotification);

//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "notifychannel")
//                .setSmallIcon(R.drawable.android_icon)
//                .setContentTitle("Notification Alert, Click Me!")
//                .setContentText("Hi, This is Android Notification Detail!")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(context);
//        mNotificationManager.notify(222, mBuilder.build());
    }
}