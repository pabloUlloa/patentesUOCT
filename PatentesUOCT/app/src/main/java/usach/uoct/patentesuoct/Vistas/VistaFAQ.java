package usach.uoct.patentesuoct.Vistas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import usach.uoct.patentesuoct.R;

public class VistaFAQ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

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

    public void clickEnlace(View v){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.uoct.cl/")));
    }

    public void clickSello(View v){
        DialogFaq d = new DialogFaq();
        d.setMsg("sello");
        d.show(getFragmentManager(),"Sello Verde");
    }

    public void clickPre(View v){
        DialogFaq d = new DialogFaq();
        d.setMsg("preemergencia");
        d.show(getFragmentManager(),"Preemergencia");
    }

    public void clickRest(View v){
        DialogFaq d = new DialogFaq();
        d.setMsg("restriccionPre");
        d.show(getFragmentManager(),"Restricciones");
    }

}
