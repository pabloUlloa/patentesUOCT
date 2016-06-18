package usach.uoct.patentesuoct.Tools;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Clase que se utiliza para manipular objetos JSON
 */
public class JsonHandler {

    private static boolean verHoy;

    public JsonHandler(){
        Calendar c = Calendar.getInstance();
        verHoy=(c.get(Calendar.HOUR_OF_DAY)<=20);
    }

    public static String getSituacion(String result) {
        try {
            JSONObject jo = new JSONObject(result);
            if(verHoy){
                return jo.getJSONObject("restriccion").getJSONObject("hoy").getString("tipo");
            }else{
                return jo.getJSONObject("restriccion").getJSONObject("manana").getString("tipo");
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return null;
    }// getRestruccionHoy(Context c)

    public static String getFecha(){
        String fecha = new SimpleDateFormat("EEEE dd 'de' MMMM, yyyy").format(Calendar.getInstance().getTime());
        return fecha.substring(0,1).toUpperCase()+fecha.substring(1);
    }

    public static String getRestriccionConSello(String result){
        try {
            JSONObject jo = new JSONObject(result);
            if(verHoy) {
                return jo.getJSONObject("restriccion").getJSONObject("hoy").getString("digitos_con_sello").replaceAll(" ","");
            }else{
                return jo.getJSONObject("restriccion").getJSONObject("manana").getString("digitos_con_sello").replaceAll(" ","");
            }
        } catch (JSONException e1){
            e1.printStackTrace();
        }
        return null;
    }

    public static String getRestriccion(String result) {
        try {
            JSONObject jo = new JSONObject(result);
            if(verHoy) {
                return jo.getJSONObject("restriccion").getJSONObject("hoy").getString("digitos_sin_sello").replaceAll(" ","");
            }else{
                return jo.getJSONObject("restriccion").getJSONObject("manana").getString("digitos_sin_sello").replaceAll(" ","");
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return null;
    }// getRestruccionHoy(Context c)


}// JsonHandler