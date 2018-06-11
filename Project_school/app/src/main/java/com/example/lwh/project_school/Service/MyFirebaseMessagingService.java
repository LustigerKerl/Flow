package com.example.lwh.project_school.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.lwh.project_school.R;
import com.example.lwh.project_school.Activity.Notice.NoticeDetail.NoticeDetailActivity;
import com.example.lwh.project_school.Activity.Result.ResultActivity;
import com.example.lwh.project_school.DataBase.DatabaseHelper;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    DatabaseHelper myDb;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        myDb = new DatabaseHelper(getApplicationContext());
        Log.d("test235123512351235", String.valueOf(remoteMessage.getData().values()));
        //Log.d("hi",Integer.toString(remoteMessage.getData().size()));
        Log.d("test-332", remoteMessage.getNotification().getBody());
        Log.d("test-332", remoteMessage.getData().get("type"));
        Log.d("test-332", remoteMessage.getData().get("idx"));
        //sendNotification(myDb.getData(1));
        sendNotification(remoteMessage.getData().get("type"), remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody(), remoteMessage.getData().get("idx"));

    }


    private void sendNotification(String type, String title, String content, String idx) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel;
        Intent intent = null;
        switch (type) {
            case "notice":
                intent = new Intent(this, NoticeDetailActivity.class);
                intent.putExtra("idx", idx);
                break;
            case "go_out":
                myDb.updateData("go_out_table", "1", idx);
                intent = new Intent(this, ResultActivity.class);
                break;
            case "sleep_out":
                myDb.updateData("sleep_out_table", "1", idx);
                intent = new Intent(this, ResultActivity.class);
                break;
        }
        PendingIntent pendingIntent = null;
        if (intent != null) {
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= 26) {
            channel = new NotificationChannel("3000", "fcmGet", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            builder =
                    new NotificationCompat.Builder(this, "3000")
                            .setSmallIcon(R.drawable.ic_menu_notice)
                            .setContentText(content)
                            .setContentTitle(title)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);
        } else {
            builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_menu_notice)
                            .setContentText(content)
                            .setContentTitle(title)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);
        }


        manager.notify(0, builder.build());

    }
}
