package usach.uoct.patentesuoct.Vistas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import usach.uoct.patentesuoct.Modelos.Vehiculo;
import usach.uoct.patentesuoct.R;
import usach.uoct.patentesuoct.Tools.CustomAdapter;
import usach.uoct.patentesuoct.Tools.DBHelper;

public class VistaPatente extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this);
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patente);
        refreshList();
        ListView lv = (ListView)findViewById(R.id.listaPatentesAll);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(200);
                String patenteElegida = (String)parent.getAdapter().getItem(position);
                patenteElegida=patenteElegida.split(" - ")[1];
                DialogEliminarPatente elim = new DialogEliminarPatente();
                elim.setPatente(patenteElegida);
                elim.show(getSupportFragmentManager(),"Eliminar Patente");
                return false;
            }
        });
    }

    public Context getContext(){
        return this;
    }

    /**
     *
     */
    @Override
    public void onResume(){
        refreshList();
        super.onResume();
    }

    public void refreshList(){
        ArrayList<Vehiculo> vehiculos = dbHelper.getAllVehiculos();
        String[] patentes = new String[vehiculos.size()];
        boolean[] verdes = new boolean[vehiculos.size()];
        Button b = (Button)findViewById(R.id.btnAgregar);
        b.setEnabled(vehiculos.size()!=10);

        for(int i=0;i<vehiculos.size();i++){
            if(vehiculos.get(i).getNombre().length()>0){
                patentes[i]= vehiculos.get(i).getNombre() + " - " + vehiculos.get(i).getPatente();
            }else{
                patentes[i] = "Patente sin nombre - " + vehiculos.get(i).getPatente();
            }
            verdes[i]=vehiculos.get(i).isSelloVerde();
        }

        CustomAdapter adapter = new CustomAdapter(this,patentes,verdes);
        ListView lv = (ListView)findViewById(R.id.listaPatentesAll);
        lv.setAdapter(adapter);
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

    private boolean checkPatente(String patente){
        return patente.matches("^([A-Z]{2}+[0-9]{4}|[BCDFGHJKLMNPQRSTVWXYZÑ]{4}+[0-9]{2})$");
    }

    public void clickAgregar(View v){
        EditText ali = (EditText)findViewById(R.id.aliasInput);
        EditText pai = (EditText)findViewById(R.id.patenteInput);
        CheckBox ch = (CheckBox)findViewById(R.id.selloCheck);
        String alias = ali.getText().toString();
        String patente = pai.getText().toString();
        patente=(patente.endsWith(" "))?patente.substring(0,patente.length()-2):patente;
        patente=patente.toUpperCase().replace("-","");
        alias=(alias.endsWith(" "))?alias.substring(0,alias.length()-2):alias;
        boolean sello = ch.isChecked();
        if(checkPatente(patente)){
            dbHelper.insertVehiculo(alias,patente.toUpperCase(),sello);
            refreshList();
            toast = Toast.makeText(this,"Patente agregada exitosamente",Toast.LENGTH_LONG);
            toast.show();
        }else{
            toast = Toast.makeText(this,"Error\nPatente no válida",Toast.LENGTH_LONG);
            toast.show();
        }
    }

}
