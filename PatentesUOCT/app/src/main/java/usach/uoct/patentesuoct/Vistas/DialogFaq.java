package usach.uoct.patentesuoct.Vistas;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import usach.uoct.patentesuoct.Tools.DBHelper;

/**
 * Created by pablo on 20-06-16.
 */
public class DialogFaq extends DialogFragment {

    private String msg;

    public void setMsg(String boton){
        switch (boton){
            case "sello":
                msg = "El sello verde es un adhesivo de porte obligatorio (en la práctica no se fiscaliza)"+
                        "que debe ir pegado en el parabrisas de los vehículos catáliticos y que acredita "+
                        "que este modelo cumple con los requisitos técnicos de emisiones para circular en "+
                        "la región metropolitana de Chile.";
                break;
            case "preemergencia":
                msg = "Cuando los niveles de contaminación se ubican en el rango de 300 a 499 se decreta "+
                        "Premergencia Ambiental, lo que implica restricción de circular a vehículos catalíticos "+
                        "(con sello verde), la cual afecta a 2 dígitos, que van rotando según los días.\n" +
                        "También se aplica un aumento de la restricción para los vehículos no catalíticos, de "+
                        "los 4 dígitos habituales pasa a 6.";
                break;
            case "restriccionPre":
                msg = "La restricción afecta a la totalidad de los vehículos particulares. Las motos y "+
                        "buses del Transantiago no tienen restricción. Los taxis y colectivos, así como "+
                        "los buses rurales, interurbanos y los buses privados, tienen restricción desde las "+
                        "10.00 hasta las 16.00 horas. \nEsto rige en toda la provincia de Santiago, además de "+
                        "las comunas de San Bernardo y Puente Alto. En tanto, el transporte de carga tiene "+
                        "restricción desde las 10.00 hasta las 18.00 horas, y ésta rige al interior del eje "+
                        "Américo Vespucio. Finalmente, la restricción para el transporte escolar rige entre las "+
                        "21.00 y las 6.30 horas del día siguiente.";
                break;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(msg)
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //
                    }
                })
                .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }


}
