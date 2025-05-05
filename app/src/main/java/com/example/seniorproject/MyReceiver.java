package com.example.seniorproject;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.RequiresPermission;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyReceiver extends BroadcastReceiver {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyLemubit")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Reminder")
                    .setContentText("Water your plants!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
                    builder.setAutoCancel(true);
                    builder.setDefaults(NotificationCompat.DEFAULT_ALL);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(200, builder.build());


        }
    }
