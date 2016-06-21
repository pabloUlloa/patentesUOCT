package usach.uoct.patentesuoct.Tools;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

import usach.uoct.patentesuoct.R;
import usach.uoct.patentesuoct.Vistas.Home;

/**
 * Created by zarrizarri on 21-06-16.
 */
public class AlarmReScheduler extends BroadcastReceiver {

    DBHelper dbHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        dbHelper = new DBHelper(context);
        Calendar calendar = Calendar.getInstance();
        Intent intent1 = new Intent(context, AlarmReScheduler.class);
        Intent intent2 = new Intent(context, AlarmReScheduler.class);

        if (Intent.ACTION_BOOT_COMPLETED == intent.getAction()) {
            calendar.set(Calendar.HOUR_OF_DAY, dbHelper.getHorario(1).getHora());
            calendar.set(Calendar.MINUTE, dbHelper.getHorario(1).getMinuto());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 202, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY, pendingIntent);
            } else {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }

            calendar.set(Calendar.HOUR_OF_DAY, dbHelper.getHorario(2).getHora());
            calendar.set(Calendar.MINUTE, dbHelper.getHorario(2).getMinuto());
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 303, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager2 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY, pendingIntent);
            } else {
                alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }


        } else {
            if (dbHelper.getHorario(1).isActivo() && dbHelper.getRestriccionActiva()) {//IMPLEMETAR getRestriccionActiva
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Intent repeating_intent = new Intent(context, Home.class);
                repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 203, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setContentTitle("Patentes UOCT")
                        .setContentText("Tiene restriccion")
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setSound(alarmSound)
                        .setSmallIcon(R.mipmap.ic_launcher);
                notificationManager.notify(203, builder.build());
            }
            if (dbHelper.getHorario(2).isActivo() && dbHelper.getRestriccionActiva()) {//IMPLEMETAR getRestriccionActiva
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Intent repeating_intent = new Intent(context, Home.class);
                repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 302, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setContentTitle("Patentes UOCT")
                        .setContentText("Tiene restriccion")
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setSound(alarmSound)
                        .setSmallIcon(R.mipmap.ic_launcher);
                notificationManager.notify(302, builder.build());
            }
        }
    }

}
