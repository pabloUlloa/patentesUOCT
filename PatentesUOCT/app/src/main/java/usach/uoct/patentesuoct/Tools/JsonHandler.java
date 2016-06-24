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
        String fecha;
        Calendar c = Calendar.getInstance();
        if(verHoy){
            fecha = new SimpleDateFormat("EEEE dd 'de' MMMM, yyyy").format(c.getTime());
        }else{
            c.add(Calendar.DAY_OF_YEAR,1);
            fecha = new SimpleDateFormat("EEEE dd 'de' MMMM, yyyy").format(c.getTime());
        }
        return fecha.substring(0,1).toUpperCase()+fecha.substring(1);
    }

    public static String getRestriccionConSello(String result){
        String res = "";
        try {
            JSONObject jo = new JSONObject(result);
            if(verHoy) {
                String aux = jo.getJSONObject("restriccion").getJSONObject("hoy").getString("digitos_con_sello");
                if(aux.length()<1){
                    return aux;
                }
                aux=aux.replace(" ","").replace("-","");
                for(int i=0;i<aux.length();i++){
                    res+="-"+aux.charAt(i);
                }
            }else{
                String aux = jo.getJSONObject("restriccion").getJSONObject("manana").getString("digitos_con_sello");
                if(aux.length()<1){
                    return aux;
                }
                aux=aux.replace(" ","").replace("-","");
                for(int i=0;i<aux.length();i++){
                    res+="-"+aux.charAt(i);
                }
            }
        } catch (JSONException e1){
            e1.printStackTrace();
        }
        return res.substring(0);
    }

    public static String getRestriccion(String result) {
        String res = "";
        try {
            JSONObject jo = new JSONObject(result);
            if(verHoy) {
                String aux = jo.getJSONObject("restriccion").getJSONObject("hoy").getString("digitos_sin_sello");
                if(aux.equals("Sin Conexión")){
                    return aux;
                }
                aux=aux.replaceAll(" ","").replace("-","");
                for(int i=0;i<aux.length();i++){
                    res+="-"+aux.charAt(i);
                }
            }else{
                String aux = jo.getJSONObject("restriccion").getJSONObject("manana").getString("digitos_sin_sello");
                if(aux.equals("Sin Conexión")){
                    return aux;
                }
                aux=aux.replaceAll(" ","").replace("-","");
                for(int i=0;i<aux.length();i++){
                    res+="-"+aux.charAt(i);
                }
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return res.substring(0);
    }// getRestruccionHoy(Context c)


}// JsonHandler