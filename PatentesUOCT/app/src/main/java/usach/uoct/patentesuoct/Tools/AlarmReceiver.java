package usach.uoct.patentesuoct.Tools;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import usach.uoct.patentesuoct.Modelos.Vehiculo;
import usach.uoct.patentesuoct.R;
import usach.uoct.patentesuoct.Vistas.Home;

/**
 * Created by pablo on 18-06-16.
 */
public class AlarmReceiver extends BroadcastReceiver {

    DBHelper dbHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        dbHelper = new DBHelper(context);

        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){

            AlarmSetter as = new AlarmSetter(context);
            as.startAlarm();

        }else{

            HttpGet c = new HttpGet(context);
            c.execute("http://www.uoct.cl/historial/ultimos-eventos/json/");
            String json=null;
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if(networkInfo!=null && networkInfo.isConnected()) {
                try {
                    json = c.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }else{
                json = "{\"restriccion\":{"+
                        "\"hoy\":{\"fecha\":\"-\",\"tipo\":\"Sin Conexión\",\"digitos_sin_sello\":\"Sin Conexión\",\"digitos_con_sello\":\"\"},"+
                        "\"manana\":{\"fecha\":\"-\",\"tipo\":\"Sin Conexión\",\"digitos_sin_sello\":\"Sin Conexión\",\"digitos_con_sello\":\"\"}}}";
            }
            String rSinSello = JsonHandler.getRestriccion(json);
            String rConSello = JsonHandler.getRestriccionConSello(json);

            ArrayList<String> pSello = patentesSello(rConSello);
            ArrayList<String> pSinSello = patentesSinSello(rSinSello);
            String notificacion;
            if(pSello.size()> 0 || pSinSello.size()>0){
                notificacion  = "Restricción:\n";
                for(String vehiculo : pSello)notificacion+=vehiculo+"\n";
                for(String vehiculo : pSinSello)notificacion+=vehiculo+"\n";
            }else{
                notificacion = "No tiene restricción";
            }

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent repeating_intent = new Intent(context, Home.class);
            repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 101, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setContentTitle("Patentes UOCT")
                    .setContentText(notificacion)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound)
                    .setSmallIcon(R.mipmap.ic_launcher);
            notificationManager.notify(101, builder.build());


        }

    }

    public ArrayList<String> patentesSello(String lista){
        ArrayList<String> res = new ArrayList<>();
        ArrayList<Vehiculo> vehiculos = dbHelper.getVehiculosCataliticos();
        String[] digitos = lista.split("-");
        for(Vehiculo vehiculo : vehiculos){
            for(String digito : digitos){
                if(vehiculo.getPatente().endsWith(digito)){
                    res.add((vehiculo.getNombre().length()>0?vehiculo.getNombre():"Patente sin nombre")+
                            " - "+vehiculo.getPatente());
                }
            }
        }
        return res;
    }

    public ArrayList<String> patentesSinSello(String lista){
        ArrayList<String> res = new ArrayList<>();
        ArrayList<Vehiculo> vehiculos = dbHelper.getVehiculos();
        String[] digitos = lista.split("-");
        for(Vehiculo vehiculo : vehiculos){
            for(String digito : digitos){
                if(vehiculo.getPatente().endsWith(digito)){
                    res.add((vehiculo.getNombre().length()>0?vehiculo.getNombre():"Patente sin nombre")+
                            " - "+vehiculo.getPatente());
                }
            }
        }
        return res;
    }

}
