package usach.uoct.patentesuoct.Tools;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by pablo on 09-06-16.
 */
public class HttpGet extends AsyncTask<String, Void, String> {
    private Context context;

    /**
     * Constructor
     */
    public HttpGet(Context context) {
        this.context = context;
    }// HttpGet(Context context)

    /**
     * Método que realiza la petición al servidor
     */
    @Override
    protected String doInBackground(String... urls) {
        String def = "{\"restriccion\":{"+
                "\"hoy\":{\"fecha\":\"-\",\"tipo\":\"Sin Conexión\",\"digitos_sin_sello\":\"Sin Conexión\",\"digitos_con_sello\":\"\"},"+
                "\"manana\":{\"fecha\":\"-\",\"tipo\":\"Sin Conexión\",\"digitos_sin_sello\":\"Sin Conexión\",\"digitos_con_sello\":\"\"}}}";
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
//            connection.setConnectTimeout(3000);
            connection.connect();
            int response = connection.getResponseCode();
            Log.d("Deb: ","The response is: " + response);
            InputStream is = connection.getInputStream();
            // Convert the InputStream into a string
            try{
                String ret = new Scanner(connection.getInputStream(), "UTF-8").useDelimiter("\\A").next();
                return ret;
            }catch (NoSuchElementException e){
                return def;
            }
            // Log.d("Deb",responseStr);
        } catch (MalformedURLException e) {
            Log.e("ERROR1", this.getClass().toString() + " " + e.toString());
        } catch (ProtocolException e) {
            Log.e("ERROR2", this.getClass().toString() + " " + e.toString());
        } catch (IOException e) {
            Log.e("ERROR3", this.getClass().toString() + " " + e.toString() + " " + urls[0]);
        }
        return def;    }// doInBackground(String... urls)

    /**
     * Método que manipula la respuesta del servidor
     */
    @Override
    protected void onPostExecute(String result) {
        Intent intent = new Intent("httpData").putExtra("data", result);
        context.sendBroadcast(intent);
    }// onPostExecute(String result)
}