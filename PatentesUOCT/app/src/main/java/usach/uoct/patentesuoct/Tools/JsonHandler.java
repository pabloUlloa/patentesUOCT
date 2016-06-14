package usach.uoct.patentesuoct.Tools;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author: Jefferson Morales De la Parra
 * Clase que se utiliza para manipular objetos JSON
 */
public class JsonHandler {

    /**
     * Método que recibe un JSONArray en forma de String y devuelve un String[] con los nombres de actores
     */



    public String[] getActors(String actors) {
        try {
            JSONArray ja = new JSONArray(actors);
            String[] result = new String[ja.length()];
            String actor;
            for (int i = 0; i < ja.length(); i++) {
                JSONObject row = ja.getJSONObject(i);
                actor = " " + row.getString("firstName") + " " + row.getString("lastName");
                result[i] = actor;
            }
            return result;
        } catch (JSONException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString() + "\n " + actors);
        }
        return null;
    }// getActors(String actors)

    /**
     * Método que recibe un JSONObject en forma de String y devuelve un String con los datos de un actor
     */
    public String getActor(String actor) {
        try {
            JSONObject row = new JSONObject(actor);
            String id=row.getString("actorId");
            String fn=row.getString("firstName");
            String ln=row.getString("lastName");
            String lu=row.getString("lastUpdate");
            return "\n\n\n id:"+id+"\n Nombre: "+fn+"\n Apellido: "+ln+"\n LastUpdate: "+lu;
        } catch (JSONException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        }
        return null;
    }// getActors(String actors)

}// JsonHandler