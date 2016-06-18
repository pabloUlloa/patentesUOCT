package usach.uoct.patentesuoct.Vistas;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import usach.uoct.patentesuoct.R;
import usach.uoct.patentesuoct.Tools.HttpGet;
import usach.uoct.patentesuoct.Tools.JsonHandler;

public class VistaSituacionAmbiental extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situacion_ambiental);

        TextView t;
        String fecha = JsonHandler.getFecha();
        String situacion = JsonHandler.getSituacion(jsonContent());

        t=(TextView)findViewById(R.id.fecha);
        t.setText(fecha);
        t=(TextView)findViewById(R.id.textSituacion);
        t.setText(situacion);

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