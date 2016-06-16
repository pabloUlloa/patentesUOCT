package usach.uoct.patentesuoct.Vistas;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import usach.uoct.patentesuoct.Modelos.Vehiculo;
import usach.uoct.patentesuoct.R;
import usach.uoct.patentesuoct.Tools.DBHelper;
import usach.uoct.patentesuoct.Tools.HttpGet;
import usach.uoct.patentesuoct.Tools.JsonHandler;

public class Home extends AppCompatActivity {

    private final String URL_GET = "http://www.uoct.cl/historial/ultimos-eventos/json/";
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        String restSinSello = jh.getRestriccion(json)[0];
        String restConSello = jh.getRestriccion(json)[1];
        String fecha = jh.getFecha(json);

        restSinSello=restSinSello.replaceAll(" ","");
        restConSello=restConSello.replaceAll(" ","");

        int sello=restConSello.split("-").length;
        int ssello=restSinSello.split("-").length;
        int[] digitosSello = new int[sello];
        int[] digitossSello = new int[ssello];

        for(int i=0;i<sello;i++){
            digitosSello[i]=Integer.parseInt(restConSello.split("-")[i]);
        }

        for(int i=0;i<ssello;i++){
            digitossSello[i]=Integer.parseInt(restSinSello.split("-")[i]);
        }

        t=(TextView)findViewById(R.id.textSinSello);
        t.setText((CharSequence)restSinSello);
        t=(TextView)findViewById(R.id.textConSello);
        t.setText((CharSequence)restConSello);
        t=(TextView)findViewById(R.id.fecha);
        t.setText(fecha);

        TableLayout tabla = (TableLayout)findViewById(R.id.tablaRestricciones);
        ArrayList<Vehiculo> patentes = dbHelper.getAllVehiculos();
        for(int i=0;i<patentes.size();i++) {
            for (int j = 0; j < sello; j++) {
                if (Integer.parseInt(patentes.get(i).getPatente().substring(5)) == digitosSello[j]) {
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView tv = new TextView(this);
                    if(patentes.get(i).getNombre().length()>0){
                        tv.setText(patentes.get(i).getNombre() + " - " + patentes.get(i).getPatente());
                    }else{
                        tv.setText("VistaPatente sin nombre - " + patentes.get(i).getPatente());
                    }
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setTypeface(null, Typeface.BOLD);
                    tv.setTextColor(Color.WHITE);
                    tv.setTextSize(18);
                    tr.addView(tv);
                    tabla.addView(tr);
                    patentes.remove(i);
                }
            }
        }
        for(int i=0;i<patentes.size();i++){
            for(int j=0;j<ssello;j++){
                if(Integer.parseInt(patentes.get(i).getPatente().substring(5))==digitossSello[j]){
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView tv = new TextView(this);
                    if(patentes.get(i).getNombre().length()>0){
                        tv.setText(patentes.get(i).getNombre() + " - " + patentes.get(i).getPatente());
                    }else{
                        tv.setText("VistaPatente sin nombre - " + patentes.get(i).getPatente());
                    }
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setTypeface(null, Typeface.BOLD);
                    tv.setTextColor(Color.WHITE);
                    tv.setTextSize(18);
                    tr.addView(tv);
                    tabla.addView(tr);
                }
            }
        }

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
