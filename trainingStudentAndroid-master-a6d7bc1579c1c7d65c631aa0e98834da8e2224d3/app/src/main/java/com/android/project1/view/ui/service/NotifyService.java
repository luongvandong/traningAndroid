package com.android.project1.view.ui.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.android.project1.R;
import com.android.project1.model.pojo.Notify;
import com.android.project1.view.ui.MainActivity;
import com.android.project1.view.ui.adapter.DbAdapter;
import com.android.project1.view.ui.prefs.Preferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @Date : 10/05/2017
 * @Author : ka
 */
public class NotifyService extends IntentService {

    private static final String TAG = "NotifyService";

    private static final long POLL_INTERVAL = 10000 * 60;
//    private static final long POLL_INTERVAL = 1000 * 20;

    public static String ACTION_SHOW = "ACTION_SHOW";

    public NotifyService() {
        super(TAG);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, NotifyService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = NotifyService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(
                context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);

        if (isOn) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), POLL_INTERVAL, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
        Preferences.setAlarmOn(isOn);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        DbAdapter helper = new DbAdapter(getApplicationContext());

        Notify n = new Notify("Notification", convertDateToStringFull(Calendar.getInstance().getTimeInMillis()));
        helper.insertNotification(n);

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pi = PendingIntent
                .getActivity(this, 0, i, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic8_bell)
                .setContentTitle(n.getName())
                .setContentText(n.getContent())
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        Log.i(TAG, "show");
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(0, notification);
        sendBroadcast(new Intent(ACTION_SHOW));
    }

    public String convertDateToStringFull(long l) {
        Date date = new Date(l);
        String DATE_PATTERN = "HH:mm:ss dd/MM/yyyy";
        return new SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(date);
    }
}
