package usach.uoct.patentesuoct.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import usach.uoct.patentesuoct.R;
import usach.uoct.patentesuoct.Tools.HttpGet;
import usach.uoct.patentesuoct.Tools.JsonHandler;

public class SituacionAmbiental extends AppCompatActivity {

    private final String URL_GET = "http://www.uoct.cl/historial/ultimos-eventos/json/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situacion_ambiental);

        HttpGet c = new HttpGet(this);
        c.execute(URL_GET);
        String json=null;
        try {
            json=c.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        JsonHandler jh = new JsonHandler();
        TextView t = new TextView(this);
        String fecha = jh.getFecha(json);
        String situacion = jh.getSituacion(json);

        t=(TextView)findViewById(R.id.fecha);
        t.setText(fecha);
        t=(TextView)findViewById(R.id.textSituacion);
        t.setText(situacion);

    }

    public void clickHome(View v){
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
    }

    public void clickConfig(View v){
        Intent intent = new Intent(this,Configuracion.class);
        startActivity(intent);
    }

    public void clickFaq(View v){
        Intent intent = new Intent(this,FAQ.class);
        startActivity(intent);
    }

    public void clickSituacion(View v){
        Intent intent = new Intent(this,SituacionAmbiental.class);
        startActivity(intent);
    }


}
