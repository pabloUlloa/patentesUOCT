package usach.uoct.patentesuoct.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import usach.uoct.patentesuoct.R;

public class SituacionAmbiental extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situacion_ambiental);
/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void clickHome(View v){
        Intent i = new Intent(this,Home.class);
        startActivity(i);
    }

    public void clickConfiguracion(View v){
        Intent i = new Intent(this,Configuracion.class);
        startActivity(i);
    }

    public void clickFAQ(View v){
        Intent i = new Intent(this,FAQ.class);
        startActivity(i);
    }

    public void clickSituacion(View v){
        Intent i = new Intent(this,SituacionAmbiental.class);
        startActivity(i);
    }

}
