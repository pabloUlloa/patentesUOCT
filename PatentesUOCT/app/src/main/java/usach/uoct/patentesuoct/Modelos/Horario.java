package usach.uoct.patentesuoct.Modelos;

/**
 * Created by esteban on 09-06-16.
 */
public class Horario {
    private int id;
    private int hora;
    private int minuto;
    private boolean activo;

    public Horario(int id, int hora, int minuto, boolean activo) {
        this.id = id;
        this.hora = hora;
        this.minuto = minuto;
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public int getHora() {
        return hora;
    }

    public String getHoraMin(){
        String out = "";
        out=(this.hora<10)?out+"0"+this.hora:out+this.hora;
        out=(this.minuto<10)?out+":0"+this.minuto:out+":"+this.minuto;
        return out;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
