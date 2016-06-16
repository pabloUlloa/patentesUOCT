package usach.uoct.patentesuoct.Vistas;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import usach.uoct.patentesuoct.Modelos.Vehiculo;
import usach.uoct.patentesuoct.R;
import usach.uoct.patentesuoct.Tools.DBHelper;

public class Patente extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patente);
        refreshList();
        ListView lv = (ListView)findViewById(R.id.listaPatentesAll);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String patenteElegida = (String)parent.getItemAtPosition(position);
                patenteElegida=patenteElegida.split(" - ")[1];
                EliminarPatente elim = new EliminarPatente();
                elim.setPatente(patenteElegida);
                elim.show(getSupportFragmentManager(),"Eliminar Patente");
                return false;
            }
        });
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
        for(int i=0;i<vehiculos.size();i++){
            if(vehiculos.get(i).getNombre().length()>0){
                patentes[i]= vehiculos.get(i).getNombre() + " - " + vehiculos.get(i).getPatente();
            }else{
                patentes[i] = "Patente sin nombre - " + vehiculos.get(i).getPatente();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,patentes);
        ListView lv = (ListView)findViewById(R.id.listaPatentesAll);
        lv.setAdapter(adapter);
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
        alias=(alias.endsWith(" "))?alias.substring(0,alias.length()-2):alias;
        boolean sello = ch.isChecked();
        if(checkPatente(patente)){
            dbHelper.insertVehiculo(alias,patente.toUpperCase(),sello);
            refreshList();
        }else{
            Toast toast = Toast.makeText(this,"Error\nPatente no válida",Toast.LENGTH_LONG);
            toast.show();
        }
    }

}
