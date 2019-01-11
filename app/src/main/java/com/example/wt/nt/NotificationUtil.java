package com.example.wt.nt;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

/**
 * 通知
 */
public class NotificationUtil{

    private static final int NOTIFICATION_TAG = 2;

    private static final String AUTO_CHANNEL = "auto_channel"; //自动接单通知
    private static final String NEW_CHANNEL = "new_channel"; //新订单通知
    private static final String BLUE_CHANNEL = "blue_channel"; //蓝牙通知
    private static final String PRINT_CHANNEL = "print_channel"; //打印通知

    private static void notify(final Context context, int sound, String description,
                              final String title, String text, String channeldId) {

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channeldId)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_stat_util)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setTicker("有新消息了")
                .setWhen(System.currentTimeMillis())
                .setVisibility(Notification.VISIBILITY_SECRET)
                .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + sound))
                .setAutoCancel(true);

        notify(context, channeldId, description, builder.build(), sound);
    }

    @SuppressLint("NewApi")
    private static NotificationChannel createChannel(Context context, String channelId, String description,
                                                    int sound) {
        NotificationChannel channel = new NotificationChannel(channelId, channelId,
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + sound),
                new AudioAttributes.Builder().build());
        channel.setDescription(description);
        return channel;
    }

    private static void notify(final Context context, String channelId, String description,
                               final Notification notification, int sound) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(createChannel(context, channelId, description, sound));
        }
        nm.notify(NOTIFICATION_TAG, notification);
    }

    /**
     * 自动接单通知
     * @param context
     */
    public static void autoNotify(Context context) {
        notify(context, R.raw.jiedan, "自动接单通知", "新订单",
                "您有新订单了，已为您自动接单", AUTO_CHANNEL);
    }
    /**
     * 新订单通知
     * @param context
     */
    public static void newNotify(Context context) {
        notify(context, R.raw.jiedan, "新订单通知", "新订单",
                "您有新订单了，请及时处理", NEW_CHANNEL);
    }
    /**
     * 蓝牙通知
     * @param context
     */
    public static void blueNotify(Context context) {
        notify(context, R.raw.lanya, "蓝牙连接通知", "蓝牙连接",
                "蓝牙连接异常，请查看", BLUE_CHANNEL);
    }
    /**
     * 打印通知
     * @param context
     */
    public static void printNotify(Context context) {
        notify(context, R.raw.print, "打印连接通知", "打印连接",
                "打印设备连接异常，请查看", PRINT_CHANNEL);
    }
}
