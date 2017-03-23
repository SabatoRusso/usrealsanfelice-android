package app.srusso.usrealsanfelice.notifacation;

/**
 * Created by italdata on 30/11/16.
 */

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Map;

import app.srusso.usrealsanfelice.R;

public class FCMMessageHandler extends FirebaseMessagingService {


    public static final int MESSAGE_NOTIFICATION_ID = 435345;
    public static final String TAG = "NOTIFICA";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String from = remoteMessage.getFrom();

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        createNotification(notification);



    }

    // Creates notification based on title and body received
    private void createNotification(RemoteMessage.Notification notification) {

        Uri uri = Uri.parse(PreferenceManager.getDefaultSharedPreferences(this).
                getString("pref_tone", "content://settings/system/notification_sound"));
        Context context = getBaseContext();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                 .setSmallIcon(getNotificationIcon())
                 .setContentTitle(notification.getTitle())
                 .setContentText(notification.getBody());


        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());

    }


    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }

}