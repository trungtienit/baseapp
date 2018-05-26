package com.example.trantien.appreview.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.trantien.appreview.GetMessageResults;
import com.example.trantien.appreview.MainActivity;
import com.example.trantien.appreview.R;
import com.example.trantien.appreview.mvp.login.view.ConnectFirebase;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getData().get("message"));
   //TODO:
    }

    private void showNotification(String message) {

        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("FCM Test")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        ConnectFirebase connectFirebase = new ConnectFirebase(getBaseContext());
        connectFirebase.getMessages( new GetMessageResults() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }
        });
        manager.notify(0, builder.build());
    }

}
