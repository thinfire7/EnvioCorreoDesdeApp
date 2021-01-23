package proyecto.covid.Utilidades;


import java.sql.Timestamp;
import java.util.Date;

public class Utilidades {

    //Constantes campos tabla usuario

    public final static String TABLA_USUARIO="usuario";
    public final static String CAMPO_NOMBRE="nombre";
    public final static String CAMPO_APELLIDO="apellido";
    public final static String CAMPO_CORREO="correo";
    public final static String CAMPO_CONTRASENA="contrasena";
    public final static String CAMPO_EDAD="edad";
    public final static String CAMPO_SEXO="sexo";
    public final static String CAMPO_TIPOCUENTA="tipoCuenta";
    public final static String CAMPO_ESTADO="estado";
    public final static String CAMPO_LOCALIDAD="localidad";
    public final static String CAMPO_CEDULA="cedula";

    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + " ("+CAMPO_NOMBRE+" TEXT, "+CAMPO_APELLIDO+" TEXT, "
            +CAMPO_CORREO+" TEXT, "+CAMPO_CONTRASENA+" TEXT, "+CAMPO_SEXO+" TEXT, " +CAMPO_TIPOCUENTA+" TEXT, "+CAMPO_ESTADO+" TEXT, "+CAMPO_EDAD+" INTEGER, "+CAMPO_LOCALIDAD+" TEXT, "+CAMPO_CEDULA+" TEXT) ";

    public final static String TABLA_UBICACION="ubicacion";
    public final static String CAMPO_LUGARVISITADO = "lugarVisitado";
    public final static String CAMPO_TIEMPOLUGARVISITADO = "tiempoLugarVisitado";
    public final static String CAMPO_FECHA = "fecha";
    public final static String CAMPO_HORA = "hora";
    public final static String CAMPO_LONGITUD = "longitud";
    public final static String CAMPO_LATITUD = "latitud";

        public static final String CREAR_TABLA_UBICACION = "CREATE TABLE " + TABLA_UBICACION + " ("+CAMPO_LUGARVISITADO+" TEXT, "+CAMPO_TIEMPOLUGARVISITADO+" TEXT, "+CAMPO_FECHA+" TEXT, "
            +CAMPO_HORA+" TEXT, "+CAMPO_LONGITUD+" DOUBLE, "+CAMPO_LATITUD+" DOUBLE, "+CAMPO_CEDULA+" TEXT) ";


}
