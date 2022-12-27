package music.minimize.fstech.minimize_music;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Notification {

    private static final int NOTIFICATION_ID = 68584;
    /**
     * This pending intent id is used to uniquely reference the pending intent
     */
    private static final int PENDING_ID = 2048;
    private static final int PENDING_ID2 = 2165;
    /**
     * This notification channel id is used to link notifications to this channel
     */
    private static final String NOTIFICATION_CHANNEL = "notification_channel";
    //  COMPLETED (1) Create a method to clear all notifications
    private static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.cancelAll();
    }
    public static void minimizemusic(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL,
                    context.getString(R.string.main_string_for_notifiction),
                    NotificationManager.IMPORTANCE_HIGH);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }
        AudioManager audiomanagerss = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        assert audiomanagerss != null;
        int howmode =audiomanagerss.getRingerMode();
        // For Normal mode
        int currentVoicelevel = audiomanagerss.getStreamVolume(AudioManager.STREAM_NOTIFICATION);

//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.big_string))
                .setContentText(context.getString(R.string.small_string))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.big_string)))
//                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent(context))
                // COMPLETED (17) Add the two new actions using the addAction method and your helper methods
                .addAction(openMainApp(context))
                .addAction(RateAction(context))
                .setVibrate(null)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                .setSound(alarmSound)
                .setAutoCancel(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        if (howmode!=2) {
            audiomanagerss.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            audiomanagerss.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 15, 1);
            assert notificationManager != null;
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
            audiomanagerss.setStreamVolume(AudioManager.STREAM_NOTIFICATION, currentVoicelevel, 1);
            audiomanagerss.setRingerMode(howmode);
        }
        else{
            audiomanagerss.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 15, 0);
            assert notificationManager != null;
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
            audiomanagerss.setStreamVolume(AudioManager.STREAM_NOTIFICATION, currentVoicelevel, 0);
        }
    }
    //  COMPLETED (5) Add a static method called ignoreReminderAction
    private static NotificationCompat.Action RateAction(Context context) {

        // COMPLETED (6) Create an Intent to launch WaterReminderIntentService
        Intent Rate = new Intent(context, Rate.class);
        // COMPLETED (7) Set the action of the intent to designate you want to dismiss the notification

        // COMPLETED (8) Create a PendingIntent from the intent to launch WaterReminderIntentService
        PendingIntent Rateit = PendingIntent.getActivity(
                context,
                PENDING_ID,
                Rate,
                PendingIntent.FLAG_CANCEL_CURRENT);
        // COMPLETED (9) Create an Action for the user to ignore the notification (and dismiss it)
        NotificationCompat.Action rate = new NotificationCompat.Action(R.drawable.ic_stars,
                context.getString(R.string.rate),
                Rateit);

//        clearAllNotifications(context);
        // COMPLETED (10) Return the action
        return rate;
    }

    //  COMPLETED (11) Add a static method called drinkWaterAction
    private static NotificationCompat.Action openMainApp(Context context) {
        // COMPLETED (12) Create an Intent to launch WaterReminderIntentService
        Intent OpenTheApp = new Intent(context, MainActivity.class);
        // COMPLETED (13) Set the action of the intent to designate you want to increment the water count

        // COMPLETED (14) Create a PendingIntent from the intent to launch WaterReminderIntentService
        PendingIntent openMainActivity = PendingIntent.getActivity(
                context,
                PENDING_ID2,
                OpenTheApp,
                PendingIntent.FLAG_CANCEL_CURRENT);
        // COMPLETED (15) Create an Action for the user to tell us they've had a glass of water
        NotificationCompat.Action showtheapp = new NotificationCompat.Action( R.drawable.ic_stat_name,
                context.getString(R.string.open_main_activity),
                openMainActivity);
        clearAllNotifications(context);
        // COMPLETED (16) Return the action
        return showtheapp;
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, hide.class);
        return PendingIntent.getActivity(
                context,
                PENDING_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        return BitmapFactory.decodeResource(res, R.drawable.icon);
    }
}