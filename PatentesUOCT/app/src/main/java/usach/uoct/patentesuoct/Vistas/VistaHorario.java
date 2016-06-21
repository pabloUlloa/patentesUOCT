package usach.uoct.patentesuoct.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import usach.uoct.patentesuoct.Modelos.Horario;
import usach.uoct.patentesuoct.R;
import usach.uoct.patentesuoct.Tools.AlarmSetter;
import usach.uoct.patentesuoct.Tools.DBHelper;

public class VistaHorario extends AppCompatActivity {

    private DBHelper dbHelper;
    private int Hid;
    private AlarmSetter as;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        dbHelper.minimo();
        setContentView(R.layout.activity_horario);
        actualizar();
        NumberPicker np = (NumberPicker)findViewById(R.id.hora);
        np.setMinValue(0);
        np.setMaxValue(23);
        np=(NumberPicker)findViewById(R.id.min);
        np.setMinValue(0);
        np.setMaxValue(59);

    }

    private void actualizar(){
        /* Horario por defecto */
        as = new AlarmSetter(this);
        Switch sw = (Switch)findViewById(R.id.horarioDefecto);
        Button b = (Button)findViewById(R.id.btnEditar0);
        b.setEnabled(false);
        b = (Button)findViewById(R.id.btnEditar1);
        b.setEnabled(true);
        b = (Button)findViewById(R.id.btnEditar2);
        b.setEnabled(true);
        sw.setEnabled(false);
        /* Horario opcional 1 */
        Switch sw2=(Switch)findViewById(R.id.horarioOpc1);
        Horario horOpc1 = dbHelper.getHorario(1);
        sw2.setText("Opcional 1 - "+horOpc1.getHoraMin());
        sw2.setEnabled(true);
        sw2.setChecked(horOpc1.isActivo());
        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Horario h = dbHelper.getHorario(1);
                    dbHelper.updateHorario(1, h.getHora(), h.getMinuto(), isChecked);
                    as.startUserAlarms();
                }else {
                    Horario h = dbHelper.getHorario(1);
                    dbHelper.updateHorario(1, h.getHora(), h.getMinuto(), isChecked);
                    as.startUserAlarms();
                }
            }
        });
        /* Horario opcional 2 */
        Switch sw3=(Switch)findViewById(R.id.horarioOpc2);
        Horario horOpc2 = dbHelper.getHorario(2);
        sw3.setText("Opcional 2 - "+horOpc2.getHoraMin());
        sw3.setEnabled(true);
        sw3.setChecked(horOpc2.isActivo());
        sw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Horario h = dbHelper.getHorario(2);
                    dbHelper.updateHorario(2, h.getHora(), h.getMinuto(), isChecked);
                    as.startUserAlarms();
                    Log.i("alarmas","seted true");
                }else{
                    Horario h = dbHelper.getHorario(2);
                    dbHelper.updateHorario(2, h.getHora(), h.getMinuto(), isChecked);
                    as.startUserAlarms();
                    Log.i("alarmas","seted false");
                }
            }
        });
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

    public void clickEditar1(View v){
        TextView tv = (TextView)findViewById(R.id.textHorario);
        tv.setText("Opcional 1");
        NumberPicker np = (NumberPicker)findViewById(R.id.hora);
        np.setValue(dbHelper.getHorario(1).getHora());
        np = (NumberPicker)findViewById(R.id.min);
        np.setValue(dbHelper.getHorario(1).getMinuto());
        Hid=1;
    }

    public void clickEditar2(View v){
        TextView tv = (TextView)findViewById(R.id.textHorario);
        tv.setText("Opcional 2");
        NumberPicker np = (NumberPicker)findViewById(R.id.hora);
        np.setValue(dbHelper.getHorario(2).getHora());
        np = (NumberPicker)findViewById(R.id.min);
        np.setValue(dbHelper.getHorario(2).getMinuto());
        Hid=2;
    }

    public void clickGuardar(View v){
        as = new AlarmSetter(this);
        NumberPicker np = (NumberPicker)findViewById(R.id.hora);
        int hora=np.getValue();
        np = (NumberPicker)findViewById(R.id.min);
        int min=np.getValue();
        boolean b;
        Switch sw;
        if(Hid==1) {
            sw = (Switch) findViewById(R.id.horarioOpc1);
            b = sw.isChecked();
            dbHelper.updateHorario(Hid,hora,min,b);
        }else if(Hid==2){
            sw = (Switch) findViewById(R.id.horarioOpc2);
            b = sw.isChecked();
            dbHelper.updateHorario(Hid,hora,min,b);
        }
        as.startUserAlarms();
        actualizar();
    }

}
