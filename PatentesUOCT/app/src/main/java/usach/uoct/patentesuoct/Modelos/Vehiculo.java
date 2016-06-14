package usach.uoct.patentesuoct.Modelos;

/**
 * Created by esteban on 09-06-16.
 */
public class Vehiculo {
    private int id;
    private String patente;
    private boolean selloVerde;
    private String nombre;

    public Vehiculo(int id, String patente, boolean selloVerde, String nombre) {
        this.id = id;
        this.patente = patente;
        this.selloVerde = selloVerde;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public boolean isSelloVerde() {
        return selloVerde;
    }

    public void setSelloVerde(boolean selloVerde) {
        this.selloVerde = selloVerde;
    }

    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
}
