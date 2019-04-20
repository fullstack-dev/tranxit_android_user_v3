package com.tranxitpro.user.common.fcm;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tranxitpro.user.MvpApplication;
import com.tranxitpro.user.R;
import com.tranxitpro.user.data.SharedHelper;
import com.tranxitpro.user.ui.activity.main.MainActivity;

import static com.tranxitpro.user.common.Constants.BroadcastReceiver.INTENT_FILTER;

public class MyFireBaseMessagingService extends FirebaseMessagingService {

    private int notificationId = 0;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        @SuppressLint("HardwareIds")
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        SharedHelper.putKey(this, "device_token", s);
        SharedHelper.putKey(this, "device_id", deviceId);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String chat = null;
        System.out.println("RRR FireBaseMessaging data payload: " + remoteMessage.getData());
        if (remoteMessage.getData().size() > 0) {
            try {
                chat = remoteMessage.getData().get("custom");
            } catch (Exception e) {
                e.printStackTrace();
            }
            openMainActivity(remoteMessage.getData().get("message"), !TextUtils.isEmpty(chat));
            sendBroadcast(new Intent(INTENT_FILTER));
        } else sendBroadcast(new Intent(INTENT_FILTER));
    }

    private void openMainActivity(String messageBody, boolean isChat) {
        System.out.println("RRR FireBaseMessaging messageBody = [" + messageBody + "], isChat = [" + isChat + "]");

        MvpApplication.canGoToChatScreen = isChat;
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity
                (this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat
                .Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_push)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(uri)
                .setContentIntent(pIntent);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(channel);
        }

        nm.notify(notificationId++, notificationBuilder.build());
    }
}
