package usach.uoct.patentesuoct.Tools;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * @author: Jefferson Morales De la Parra
 * Clase que se utiliza para manipular objetos JSON
 */
public class JsonHandler {

    /**
     * Método que recibe un JSONArray en forma de String y devuelve un String[] con los nombres de actores
     */

    private String formatoFecha(String fecha){
        String ano = fecha.split("-")[0];
        String mes = fecha.split("-")[1];
        String dia = fecha.split("-")[2];
        return dia+"/"+mes+"/"+ano;
    }

    public String getFecha(String result) {
        Calendar c = Calendar.getInstance();
        try {
            JSONObject jo = new JSONObject(result);
            if(c.get(Calendar.HOUR_OF_DAY)>20){
                return formatoFecha(jo.getJSONObject("restriccion").getJSONObject("manana").getString("fecha"));
            }else{
                return formatoFecha(jo.getJSONObject("restriccion").getJSONObject("hoy").getString("fecha"));
            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return null;
    }// getRestruccionHoy(Context c)

    public String getSituacion(String result) {
        Calendar c = Calendar.getInstance();
        try {
            JSONObject jo = new JSONObject(result);
            if(c.get(Calendar.HOUR_OF_DAY)>20){
                return jo.getJSONObject("restriccion").getJSONObject("manana").getString("tipo");
            }else{
                return jo.getJSONObject("restriccion").getJSONObject("hoy").getString("tipo");
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return null;
    }// getRestruccionHoy(Context c)

    public String[] getRestriccion(String result) {
        Calendar c = Calendar.getInstance();
        try {
            JSONObject jo = new JSONObject(result);
            String[] res = new String[2];
            if(c.get(Calendar.HOUR_OF_DAY)>20) {
                res[0] = jo.getJSONObject("restriccion").getJSONObject("manana").getString("digitos_sin_sello");
                res[1] = jo.getJSONObject("restriccion").getJSONObject("manana").getString("digitos_con_sello");
            }else{
                res[0] = jo.getJSONObject("restriccion").getJSONObject("hoy").getString("digitos_sin_sello");
                res[1] = jo.getJSONObject("restriccion").getJSONObject("hoy").getString("digitos_con_sello");
            }
            res[0]=res[0].length()==0?"No aplica":res[0];
            res[1]=res[1].length()==0?"No aplica":res[1];
            return res;
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return null;
    }// getRestruccionHoy(Context c)


}// JsonHandler