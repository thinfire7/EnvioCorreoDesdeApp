package proyecto.covid.DataBase;

import java.util.Date;

public class LugaresVisitados {

    private String lugarVisitado;
    private String fecha;
    private String hora;
    private double longitud;
    private double latitud;
    private String cedula;
    private String tiempoLugarVisitado;

    public LugaresVisitados(String lugarVisitado, String fecha, String hora, double longitud, double latitud, String cedula, String tiempoLugarVisitado) {
        this.lugarVisitado = lugarVisitado;
        this.fecha = fecha;
        this.hora = hora;
        this.longitud = longitud;
        this.latitud = latitud;
        this.cedula = cedula;
        this.tiempoLugarVisitado = tiempoLugarVisitado;
    }

    public String getLugarVisitado() {
        return lugarVisitado;
    }

    public void setLugarVisitado(String lugarVisitado) {
        this.lugarVisitado = lugarVisitado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTiempoLugarVisitado() {
        return tiempoLugarVisitado;
    }

    public void setTiempoLugarVisitado(String tiempoLugarVisitado) {
        this.tiempoLugarVisitado = tiempoLugarVisitado;
    }
}
