package com.example.lwh.project_school.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.example.lwh.project_school.Activity.Notice.NoticeDetail.NoticeDetailActivity;
import com.example.lwh.project_school.Activity.Result.ResultActivity;
import com.example.lwh.project_school.DataBase.DatabaseHelper;
import com.example.lwh.project_school.R;
import com.google.firebase.messaging.RemoteMessage;
import static com.example.lwh.project_school.Activity.Main.MainActivity.badge_noti1;
import static com.example.lwh.project_school.Activity.Main.MainActivity.badge_noti2;

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    DatabaseHelper myDb;
    Handler handler = new Handler();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        myDb = new DatabaseHelper(getApplicationContext());
        sendNotification(remoteMessage.getData().get("type"), remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody(), remoteMessage.getData().get("idx"));

        updateNotiCount(remoteMessage.getData().get("type"));
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

    private void updateNotiCount(final String type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        switch (type) {
                            case "sleep_out":
                                badge_noti2.setText(String.valueOf(Integer.parseInt(badge_noti2.getText().toString())+1));
                                badge_noti2.setVisibility(View.VISIBLE);
                                break;
                            case "go_out":
                                badge_noti2.setText(String.valueOf(Integer.parseInt(badge_noti2.getText().toString())+1));
                                badge_noti2.setVisibility(View.VISIBLE);
                                break;
                            case "notice":
                                badge_noti1.setText(String.valueOf(Integer.parseInt(badge_noti1.getText().toString())+1));
                                badge_noti1.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                });
            }
        }).start();
    }
}
