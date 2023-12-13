package com.example.health_alarm_v2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        // Perform actions based on the received broadcast
        if (intent.getAction() != null && intent.getAction().equals("my.custom.action")) {
            // Handle the specific action
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "ReminderChannel")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("Your Notification Title")
                    .setContentText("Your Notification Text")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            notificationManager.notify(1, builder.build());

            Toast.makeText(context, "Custom broadcast received", Toast.LENGTH_SHORT).show();
        }
    }
}

