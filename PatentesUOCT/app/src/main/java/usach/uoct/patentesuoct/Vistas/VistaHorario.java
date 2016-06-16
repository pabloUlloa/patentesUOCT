package usach.uoct.patentesuoct.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import usach.uoct.patentesuoct.Modelos.Horario;
import usach.uoct.patentesuoct.R;
import usach.uoct.patentesuoct.Tools.DBHelper;

public class VistaHorario extends AppCompatActivity {

    DBHelper dbHelper;
    int Hid;

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
        Switch sw = (Switch)findViewById(R.id.horarioDefecto);
        Button b = (Button)findViewById(R.id.btnEditar0);
        b.setEnabled(false);
        sw.setEnabled(false);
        /* Horario opcional 1 */
        sw=(Switch)findViewById(R.id.horarioOpc1);
        Horario horOpc1 = dbHelper.getHorario(1);
        sw.setText("Opcional 1 - "+horOpc1.getHoraMin());
        sw.setEnabled(true);
        sw.setChecked(horOpc1.isActivo());
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Horario h = dbHelper.getHorario(1);
                dbHelper.updateHorario(1,h.getHora(),h.getMinuto(),!h.isActivo());
            }
        });
        /* Horario opcional 2 */
        sw=(Switch)findViewById(R.id.horarioOpc2);
        Horario horOpc2 = dbHelper.getHorario(2);
        sw.setText("Opcional 2 - "+horOpc2.getHoraMin());
        sw.setEnabled(true);
        sw.setChecked(horOpc2.isActivo());
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Horario h = dbHelper.getHorario(2);
                dbHelper.updateHorario(2,h.getHora(),h.getMinuto(),!h.isActivo());
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
        NumberPicker np = (NumberPicker)findViewById(R.id.hora);
        int hora=np.getValue();
        np = (NumberPicker)findViewById(R.id.min);
        int min=np.getValue();
        Switch sw=(Switch)findViewById(R.id.horarioOpc1);
        dbHelper.updateHorario(Hid,hora,min,sw.isChecked());
        actualizar();
    }

}
