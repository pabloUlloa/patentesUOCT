package usach.uoct.patentesuoct.Tools;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * @author: Jefferson Morales De la Parra
 * Clase que se utiliza para manipular objetos JSON
 */
public class JsonHandler {

    /**
     * Método que recibe un JSONArray en forma de String y devuelve un String[] con los nombres de actores
     */

    public String getFechaHoy(String result) {
        try {
            JSONObject jo = new JSONObject(result);
            return jo.getJSONObject("restriccion").getJSONObject("hoy").getString("fecha");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return null;
    }// getRestruccionHoy(Context c)

    public String getSituacionHoy(String result) {
        try {
            JSONObject jo = new JSONObject(result);
            return jo.getJSONObject("restriccion").getJSONObject("hoy").getString("tipo");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return null;
    }// getRestruccionHoy(Context c)

    public String[] getRestriccionHoy(String result) {
        try {
            JSONObject jo = new JSONObject(result);
            String[] res = new String[2];
            res[0] = jo.getJSONObject("restriccion").getJSONObject("hoy").getString("digitos_sin_sello");
            res[1] = jo.getJSONObject("restriccion").getJSONObject("hoy").getString("digitos_con_sello");
            return res;
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return null;
    }// getRestruccionHoy(Context c)

    public String getFechaManana(String result) {
        try {
            JSONObject jo = new JSONObject(result);
            return jo.getJSONObject("restriccion").getJSONObject("manana").getString("fecha");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return null;
    }// getRestruccionHoy(Context c)

    public String getSituacionManana(String result) {
        try {
            JSONObject jo = new JSONObject(result);
            return jo.getJSONObject("restriccion").getJSONObject("manana").getString("tipo");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return null;
    }// getRestruccionHoy(Context c)

    public String[] getRestriccionManana(String result) {
        try {
            JSONObject jo = new JSONObject(result);
            String[] res = new String[2];
            res[0] = jo.getJSONObject("restriccion").getJSONObject("manana").getString("digitos_sin_sello");
            res[1] = jo.getJSONObject("restriccion").getJSONObject("manana").getString("digitos_con_sello");
            return res;
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return null;
    }// getRestruccionHoy(Context c)


}// JsonHandler