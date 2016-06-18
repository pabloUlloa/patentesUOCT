package usach.uoct.patentesuoct.Tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import usach.uoct.patentesuoct.Modelos.Horario;
import usach.uoct.patentesuoct.Modelos.Vehiculo;

/**
 * Created by esteban on 09-06-16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BDPatentesUOCT.db";
    public static final int DATABASE_VERSION = 1;
    public static final String VEHICULOS_TABLE_NAME = "vehiculos";
    public static final String VEHICULOS_COLUMN_ID = "_id";
    public static final String VEHICULOS_COLUMN_NOMBRE= "nombre";
    public static final String VEHICULOS_COLUMN_PATENTE= "patente";
    public static final String VEHICULOS_COLUMN_SELLOVERDE= "sello_verde";
    public static final String VEHICULOS_SQL_CREATE =
            "CREATE TABLE "+ VEHICULOS_TABLE_NAME +" ("
                    + VEHICULOS_COLUMN_ID +" INTEGER PRIMARY KEY, "
                    + VEHICULOS_COLUMN_NOMBRE + " VARCHAR(63) NOT NULL, "
                    + VEHICULOS_COLUMN_PATENTE + " VARCHAR(7) NOT NULL, "
                    + VEHICULOS_COLUMN_SELLOVERDE + " INTEGER NOT NULL"
                    + ");";

    public static final String HORARIO_TABLE_NAME = "horarios";
    public static final String HORARIO_COLUMN_ID = "_id";
    public static final String HORARIO_COLUMN_HORA= "hora";
    public static final String HORARIO_COLUMN_MINUTO= "minuto";
    public static final String HORARIO_COLUMN_ACTIVO= "activo";
    public static final String HORARIO_SQL_CREATE =
            "CREATE TABLE "+ HORARIO_TABLE_NAME +" ("
                    + HORARIO_COLUMN_ID +" INTEGER PRIMARY KEY, "
                    + HORARIO_COLUMN_HORA + " INTEGER NOT NULL, "
                    + HORARIO_COLUMN_MINUTO + " INTEGER NOT NULL, "
                    + HORARIO_COLUMN_ACTIVO + " BOOLEAN NOT NULL"
                    + ");";

    public static final String JSON_TABLE_NAME = "json";
    public static final String JSON_COLUMN_CONTENIDO= "contenido";
    public static final String JSON_SQL_CREATE =
            "CREATE TABLE "+ JSON_TABLE_NAME +" ("
                    + JSON_COLUMN_CONTENIDO +" TEXT NOT NULL"
                    + ");";
    /**
     * Constructor de clase.
     * @param context
     */
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    /**
     * Permite crear la BD. No es necesario llamarla normalmente.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(VEHICULOS_SQL_CREATE);
        db.execSQL(HORARIO_SQL_CREATE);
        db.execSQL(JSON_SQL_CREATE);
    }

    public void minimo(){
        if(numberOfHorarios()==0){
            insertHorario(22,0,false);
            insertHorario(6,0,false);
        }
    }

    /**
     * Metodo llamado una vez que se realiza una nueva version de la aplicacion.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    /**
     * Permite insertar un nuevo vehiculo dentro de la base de datos. REVISA SOLAMENTE si esta guardado o no
     * la patente previamente.
     * @param patente patente a guardar.
     * @param selloVerde si tiene o no sello verde.
     * @return true si se inserta el vehiculo. false, si ya estaba guardado.
     */
    public boolean insertVehiculo(String nombre, String patente, boolean selloVerde)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        if(numberOfVehiculos(patente)>0){
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(VEHICULOS_COLUMN_PATENTE, patente);
        contentValues.put(VEHICULOS_COLUMN_NOMBRE, nombre);

        if(selloVerde)
            contentValues.put(VEHICULOS_COLUMN_SELLOVERDE, 1);
        else
            contentValues.put(VEHICULOS_COLUMN_SELLOVERDE, 0);

        db.insert(VEHICULOS_TABLE_NAME,null,contentValues);
        return true;
    }

    /**
     * Similar a insertVehiculo, pero con la clase del modelo.
     * @param v
     * @return
     */
    public boolean insertVehiculo(Vehiculo v){
        return insertVehiculo(v.getNombre(), v.getPatente(),v.isSelloVerde());
    }
    /**
     * Elimina vehiculo por id
     * @param id
     * @return Cantidad de vehiculos eliminados
     */
    public Integer deleteVehiculo(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(VEHICULOS_TABLE_NAME, VEHICULOS_COLUMN_ID + " = ?",
                new String[]{Integer.toString(id)});
    }

    /**
     * Elimina vehiculos por patente
     * @param patente
     * @return
     */
    public Integer deleteVehiculo(String patente){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(VEHICULOS_TABLE_NAME, VEHICULOS_COLUMN_PATENTE + " = ?",
                new String[]{patente});
    }

    /**
     * Obtiene todos los vehiculos.
     * @return lista de vehiculos.
     */
    public ArrayList<Vehiculo> getAllVehiculos(){
        ArrayList<Vehiculo> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+VEHICULOS_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(newVehiculo(res));
            res.moveToNext();
        }
        return array_list;
    }

    /**
     * Obtiene todos los vehiculos.
     * @return lista de vehiculos.
     */
    public ArrayList<Vehiculo> getVehiculosCataliticos(){
        ArrayList<Vehiculo> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+VEHICULOS_TABLE_NAME+" WHERE sello_verde=1", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(newVehiculo(res));
            res.moveToNext();
        }
        return array_list;
    }

    /**
     * Obtiene todos los vehiculos.
     * @return lista de vehiculos.
     */
    public ArrayList<Vehiculo> getVehiculos(){
        ArrayList<Vehiculo> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+VEHICULOS_TABLE_NAME+" WHERE sello_verde=0", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(newVehiculo(res));
            res.moveToNext();
        }
        return array_list;
    }


    /**
     * Cantidad de elementos de la tabla.
     * @return
     */
    public int numberOfVehiculos() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, VEHICULOS_TABLE_NAME);
        return numRows;
    }

    /**
     * Cantidad de elementos de la tabla, que tienen una patente determinada (para evitar insertar
     * nuevamente el elemento).
     * @param patente
     * @return
     */
    public int numberOfVehiculos(String patente) {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, VEHICULOS_TABLE_NAME,
                VEHICULOS_COLUMN_PATENTE+" = ?", new String[]{patente});
        return numRows;
    }

    /**
     * Obtiene un vehiculo por id.
     * @param id
     * @return
     */
    public Vehiculo getVehiculo(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+VEHICULOS_TABLE_NAME+" WHERE "+
                VEHICULOS_COLUMN_ID + " = "+id+";",null);
        res.moveToFirst();
        return newVehiculo(res);
    }

    /**
     * Obtiene un vehiculo por patente.
     * @param patente
     * @return
     */
    public Vehiculo getVehiculo(String patente){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+VEHICULOS_TABLE_NAME+" WHERE " +
                VEHICULOS_COLUMN_PATENTE + " = \""+patente+"\";",null);
        res.moveToFirst();
        return newVehiculo(res);
    }


    /**
     * Metodo privado, genera un vehiculo dado un cursor.
     * @param res
     * @return
     */
    private Vehiculo newVehiculo(Cursor res){
        boolean selloVerde = false;
        if(res.getInt(res.getColumnIndex(VEHICULOS_COLUMN_SELLOVERDE))==1){
            selloVerde = true;
        }
        return new Vehiculo(res.getInt(res.getColumnIndex(VEHICULOS_COLUMN_ID)),
                res.getString(res.getColumnIndex(VEHICULOS_COLUMN_PATENTE)),
                selloVerde,
                res.getString(res.getColumnIndex(VEHICULOS_COLUMN_NOMBRE))
        );
    }

    /*
        --------------------------- VistaHorario ---------------------------
     */

    /**
     * Inserta un horario. Formato 0:00-23:59 hrs
     * @param hora
     * @param minuto
     * @param activo
     * @return
     */
    public void insertHorario(int hora,int minuto, boolean activo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HORARIO_COLUMN_HORA, hora);
        contentValues.put(HORARIO_COLUMN_MINUTO, minuto);
        contentValues.put(HORARIO_COLUMN_ACTIVO, activo);
        db.insert(HORARIO_TABLE_NAME,null,contentValues);
    }

    /**
     * Inserta un horario, dado un elemento de la clase orario.
     * @param h
     */
    public void insertHorario(Horario h){
        insertHorario(h.getHora(),h.getMinuto(),h.isActivo());
    }

    /**
     * Actualiza un horario dado el id.
     * @param id
     * @return
     */
    public void updateHorario(int id,int hora, int min, boolean activo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+HORARIO_TABLE_NAME+" SET "+HORARIO_COLUMN_HORA+
                "="+hora+", "+HORARIO_COLUMN_MINUTO+"="+min+", "+HORARIO_COLUMN_ACTIVO+"="+(activo?1:0)+
                " WHERE "+HORARIO_COLUMN_ID+"="+id);
//        return db.update(HORARIO_TABLE_NAME, HORARIO_COLUMN_ID+" = ?",
//                new String[]{Integer.toString(id)});
    }

    /**
     * Elimina un horario dado el id.
     * @param id
     * @return
     */
    public Integer deleteHorario(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(HORARIO_TABLE_NAME, HORARIO_COLUMN_ID+" = ?",
                new String[]{Integer.toString(id)});
    }

    /**
     * Obtiene todos los horarios.
     * @return
     */
    public ArrayList<Horario> getAllHorarios(){
        ArrayList<Horario> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+ HORARIO_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(newHorario(res));
            res.moveToNext();
        }
        return array_list;
    }

    /**
     * Crea un horario, metodo privado.
     * @param res
     * @return
     */
    private Horario newHorario(Cursor res) {
        boolean activo = false;
        if(res.getInt(res.getColumnIndex(HORARIO_COLUMN_ACTIVO))==1){
            activo=true;
        }
        return new Horario(
                res.getInt(res.getColumnIndex(HORARIO_COLUMN_ID)),
                res.getInt(res.getColumnIndex(HORARIO_COLUMN_HORA)),
                res.getInt(res.getColumnIndex(HORARIO_COLUMN_MINUTO)),
                activo
        );
    }

    /**
     * Obtiene un horario, dado un id.
     * @param id
     * @return
     */
    public Horario getHorario(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+HORARIO_TABLE_NAME+" WHERE "+
                HORARIO_COLUMN_ID + " = "+id+";",null);
        res.moveToFirst();
        return newHorario(res);
    }

    /**
     * Retorna la cantidad de horarios
     * @return
     */
    public int numberOfHorarios() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, HORARIO_TABLE_NAME);
        return numRows;
    }

    /*
        --------------------------- JSON ---------------------------
     */

    /**
     * Inserta un JSON. Formato 0:00-23:59 hrs
     * @param Json
     * @return
     */
    public void insertJSON(String Json){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(JSON_COLUMN_CONTENIDO, Json);
        db.insert(JSON_TABLE_NAME,null,contentValues);
    }

    /**
     * Obtiene último Json respaldado,
     * @return
     */
    public String getJson(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+JSON_TABLE_NAME,null);
        res.moveToFirst();
        return res.getString(res.getColumnIndex(JSON_COLUMN_CONTENIDO));
    }

}
