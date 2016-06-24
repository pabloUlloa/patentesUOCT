package usach.uoct.patentesuoct.Tools;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by pablo on 19-06-16.
 */
public class AlarmSetter extends AppCompatActivity {

    private Context context;

    public AlarmSetter(Context context){
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startAlarm();
        startUserAlarms();
    }

    public void startAlarm(){
        DBHelper dbHelper = new DBHelper(context);
        Calendar calendar = Calendar.getInstance();
        Intent intent = new Intent(context,AlarmReceiver.class);
        // alarma default
        calendar.set(Calendar.HOUR_OF_DAY,21);
        calendar.set(Calendar.MINUTE,30);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,101,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(calendar.getTimeInMillis() < System.currentTimeMillis()){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+AlarmManager.INTERVAL_DAY,AlarmManager.INTERVAL_DAY,pendingIntent);
        }else{
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }
        // alarma opcional 1
        /*if(dbHelper.getHorario(1).isActivo()){
            calendar.set(Calendar.HOUR_OF_DAY,dbHelper.getHorario(1).getHora());
            calendar.set(Calendar.MINUTE,dbHelper.getHorario(1).getMinuto());
            pendingIntent = PendingIntent.getBroadcast(context,202,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if(calendar.getTimeInMillis() < System.currentTimeMillis()){
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+AlarmManager.INTERVAL_DAY,AlarmManager.INTERVAL_DAY,pendingIntent);
            }else{
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            }
        }

        // alarma opcional 1
        if(dbHelper.getHorario(2).isActivo()){
            calendar.set(Calendar.HOUR_OF_DAY,dbHelper.getHorario(2).getHora());
            calendar.set(Calendar.MINUTE,dbHelper.getHorario(2).getMinuto());
            pendingIntent = PendingIntent.getBroadcast(context,303,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if(calendar.getTimeInMillis() < System.currentTimeMillis()){
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+AlarmManager.INTERVAL_DAY,AlarmManager.INTERVAL_DAY,pendingIntent);
            }else{
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            }
        }*/

    }

    public void startUserAlarms(){
        DBHelper dbHelper = new DBHelper(context);
        Calendar calendar = Calendar.getInstance();
        Intent intent1 = new Intent(context,AlarmReScheduler.class);
        Intent intent2 = new Intent(context,AlarmReScheduler.class);

        if(dbHelper.getHorario(1).isActivo()){
            calendar.set(Calendar.HOUR_OF_DAY,dbHelper.getHorario(1).getHora());
            calendar.set(Calendar.MINUTE,dbHelper.getHorario(1).getMinuto());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,202,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if(calendar.getTimeInMillis() < System.currentTimeMillis()){
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+AlarmManager.INTERVAL_DAY,AlarmManager.INTERVAL_DAY,pendingIntent);
            }else{
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            }
        }

        if(dbHelper.getHorario(2).isActivo()){
            calendar.set(Calendar.HOUR_OF_DAY,dbHelper.getHorario(2).getHora());
            calendar.set(Calendar.MINUTE,dbHelper.getHorario(2).getMinuto());
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context,303,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager2 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if(calendar.getTimeInMillis() < System.currentTimeMillis()){
                alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+AlarmManager.INTERVAL_DAY,AlarmManager.INTERVAL_DAY,pendingIntent2);
            }else{
                alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent2);
            }
        }
    }

}
