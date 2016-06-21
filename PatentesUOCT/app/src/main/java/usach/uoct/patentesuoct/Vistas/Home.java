package usach.uoct.patentesuoct.Vistas;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import usach.uoct.patentesuoct.Modelos.Vehiculo;
import usach.uoct.patentesuoct.R;
import usach.uoct.patentesuoct.Tools.AlarmReScheduler;
import usach.uoct.patentesuoct.Tools.AlarmReceiver;
import usach.uoct.patentesuoct.Tools.AlarmSetter;
import usach.uoct.patentesuoct.Tools.DBHelper;
import usach.uoct.patentesuoct.Tools.HttpGet;
import usach.uoct.patentesuoct.Tools.JsonHandler;

public class Home extends AppCompatActivity {

    JsonHandler jh = new JsonHandler();
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        dbHelper.minimo();
        setContentView(R.layout.activity_home);
        TextView t;
        String json = jsonContent();
        String rSinSello = JsonHandler.getRestriccion(json);
        String rConSello = JsonHandler.getRestriccionConSello(json);
        String fecha = JsonHandler.getFecha();
        AlarmSetter as = new AlarmSetter(this);
        as.startAlarm();
        as.startUserAlarms();
        t=(TextView)findViewById(R.id.textSinSello);
        t.setText((CharSequence)rSinSello);
        t=(TextView)findViewById(R.id.textConSello);
        t.setText((CharSequence)rConSello);
        t=(TextView)findViewById(R.id.fecha);
        t.setText(fecha);

        TableLayout tabla = (TableLayout)findViewById(R.id.tablaRestricciones);
        ArrayList<String> p = patentesSello(rConSello);
        for(String patente: p){
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView tv = new TextView(this);
            tv.setText(patente);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tv.setTypeface(null, Typeface.BOLD);
            tv.setTextColor(Color.GREEN);
            tv.setTextSize(18);
            tr.addView(tv);
            tabla.addView(tr);
        }
        ArrayList<String> q = patentesSinSello(rSinSello);
        dbHelper.insertRestriccion(p.size()>0 || q.size()>0);
        for(String patente: q){
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView tv = new TextView(this);
            tv.setText(patente);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tv.setTypeface(null, Typeface.BOLD);
            tv.setTextColor(Color.RED);
            tv.setTextSize(18);
            tr.addView(tv);
            tabla.addView(tr);
        }
    }

    public String jsonContent(){
        HttpGet c = new HttpGet(this);
        c.execute("http://www.uoct.cl/historial/ultimos-eventos/json/");
        String json=null;
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
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
        return json;
    }

    public void clickHome(View v){
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
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

    public void clickConfig(View v){
        Intent intent = new Intent(this,VistaConfiguracion.class);
        startActivity(intent);
    }

    public void clickFaq(View v){
        Intent intent = new Intent(this,VistaFAQ.class);
        startActivity(intent);
    }

    public void clickSituacion(View v){
        Intent intent = new Intent(this,VistaSituacionAmbiental.class);
        startActivity(intent);
    }



}
