package proyecto.covid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;

import proyecto.covid.DataBase.Usuarios;
import proyecto.covid.Utilidades.Utilidades;


public class PaginaPrincipal extends AppCompatActivity{

    LocationManager ubicacion;
    static double latitud;
    static double longitud;
    Location localizacion;
    String dirCalleAux;
    Address dirCalle;
    static String cedula;
    String fecha;
    String hora;

    private int seconds = 0;
    private boolean running;
    private String time ="0:00:00";

    String fechaAux;

    ListView opcionesUsuario;
    String opcionesUsuarioDos;

    int contador = 0;

    boolean ver = false;


    //int varDia = 0;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        opcionesUsuario = (ListView) findViewById(R.id.listViewPaginaUsuario);
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this,R.array.OpcionesUsuario,
                android.R.layout.simple_list_item_1);

        opcionesUsuario.setAdapter(adaptador);
        opcionesUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                opcionesUsuarioDos = adapterView.getItemAtPosition(i).toString();
                irActivitys(opcionesUsuarioDos);

            }
        });

        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this,"bd_usuarios", null,1);

        SQLiteDatabase dbDos = conexion.getReadableDatabase();

        String[] parametros = {MainActivity.campoCorreoLogin.getText().toString()};
        String[] camposDos = {Utilidades.CAMPO_CEDULA};

        try {
            Cursor cursor = dbDos.query(Utilidades.TABLA_USUARIO, camposDos, Utilidades.CAMPO_CORREO + "=?", parametros, null, null, null, null);
            cursor.moveToFirst();
                cedula = cursor.getString(0);
            cursor.close();
        }catch (Exception e){
        }

        String[] parametrosDos = {cedula};
        String[] campos = {Utilidades.CAMPO_LUGARVISITADO};

        try {
            Cursor cursor = dbDos.query(Utilidades.TABLA_UBICACION, campos, Utilidades.CAMPO_CEDULA + "=?", parametrosDos, null, null, null);
            if(cursor.moveToFirst()){
                do {
                    dirCalleAux = cursor.getString(0);
                } while (cursor.moveToNext());
            }else{
                dirCalleAux="";
            }
            cursor.close();
        }catch (Exception e){
        }

        runTimer();
        ejecutar();
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }


    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void restart() {
        running = false;
        seconds = 0;
    }

    public void irActivitys(String opciones){
        if(opciones.equals("Mi historial de ubicaciones")) {
            Intent siguiente = new Intent(this, HistorialUbicaciones.class);
            startActivity(siguiente);
        }else if(opciones.equals("Reportar mis sintomas")){

        }else if(opciones.equals("Tazas de contagio por localidades")){

        }else if(opciones.equals("Consultar estado")){

        }
    }

    private void runTimer(){

        final Handler handler = new Handler();
        handler.post(new Runnable(){
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                if (running) {
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    private void ejecutar() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    localizacion();
                    if(contador==10) {
                        consultarLugarVisitadoPorFecha();
                        contador=0;
                        ver = false;
                    }
                    contador++;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                handler.postDelayed(this, 30000);
            }
        }, 0);
    }


    public void localizacion() throws ParseException {

        boolean ver = true;
        do {
            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
                }
                ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                localizacion = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (ubicacion != null) {
                    latitud = localizacion.getLatitude();
                    longitud = localizacion.getLongitude();
                }
                ver = true;
            } catch (Exception e) {
                ver = false;
            }
        } while (ver == false);

        direccion(localizacion);
        fecha();
        hora();


        ConexionSQLiteHelper conexionDos = new ConexionSQLiteHelper(this,"bd_usuarios", null,1);
        SQLiteDatabase dbDos = conexionDos.getReadableDatabase();

        String[] parametrosDos = {cedula,dirCalle.getAddressLine(0)};
        String[] campos = {Utilidades.CAMPO_FECHA};
        try {
            Cursor cursor = dbDos.query(Utilidades.TABLA_UBICACION, campos, Utilidades.CAMPO_CEDULA+"=?"+" AND "+Utilidades.CAMPO_LUGARVISITADO+"=?",parametrosDos, null, null, null);
            if(cursor.moveToFirst()){
                do {
                    this.fechaAux = cursor.getString(0);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
        }



        if(dirCalle.getAddressLine(0).equals(dirCalleAux)==false){
            restart();
            dirCalleAux = dirCalle.getAddressLine(0);
            insertarUbicaciones();
        }else{

            if(this.fechaAux.equals(this.fecha)==false){
                insertarUbicaciones();
            }
            stop();
            ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this,"bd_usuarios", null,1);

            SQLiteDatabase db = conexion.getWritableDatabase();

            String[] parametros = {dirCalleAux,cedula,fecha};

            ContentValues values = new ContentValues();

            values.put(Utilidades.CAMPO_TIEMPOLUGARVISITADO,time);

            db.update(Utilidades.TABLA_UBICACION,values,Utilidades.CAMPO_LUGARVISITADO+"=?"+" AND "+Utilidades.CAMPO_CEDULA+"=?"+ " AND " +Utilidades.CAMPO_FECHA+"=?",parametros);

            db.close();

            start();
        }
    }

    public void consultarLugarVisitadoPorFecha(){

        ConexionSQLiteHelper conexionDos = new ConexionSQLiteHelper(this,"bd_usuarios", null,1);
        SQLiteDatabase dbDos = conexionDos.getReadableDatabase();

        String[] parametrosDos = {dirCalleAux,fecha};
        String[] campos = {Utilidades.CAMPO_CEDULA};
        try {
            Cursor cursor = dbDos.query(Utilidades.TABLA_UBICACION, campos, Utilidades.CAMPO_LUGARVISITADO+"=?"+" AND "+Utilidades.CAMPO_FECHA+"=?",parametrosDos, null, null, null);
            if(cursor.moveToFirst()){
                do {
                    consultarContagiados(cursor.getString(0));
                } while (cursor.moveToNext());
            }else{
            }
            cursor.close();
        }catch (Exception e){
        }

    }

    public void consultarContagiados(String cedula){



        ConexionSQLiteHelper conexionDos = new ConexionSQLiteHelper(this,"bd_usuarios", null,1);
        SQLiteDatabase dbDos = conexionDos.getReadableDatabase();

        String[] parametrosDos = {cedula};
        String[] campos = {Utilidades.CAMPO_ESTADO};
        try {
            Cursor cursor = dbDos.query(Utilidades.TABLA_USUARIO, campos, Utilidades.CAMPO_CEDULA+"=?",parametrosDos, null, null, null);
            if(cursor.moveToFirst()){
                do {
                    if(cursor.getString(0).equals("positivo")&&!ver){
                        if(cedula.equals(this.cedula)==false){
                            notificacionCovid();
                            ver = true;
                        }
                    }else{
                       //no tiene covid
                    }
                } while (cursor.moveToNext());
            }else{
            }
            cursor.close();
        }catch (Exception e){
        }

    }

    public void notificacionCovid(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle("SECOCO")
                .setContentText("SE HA CRUZADO CON UN CASO CONFIRMADO DE COVID, SE RECOMIENDA REALIZAR EXAMEN")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{0, 1000})
                .setAutoCancel(true);


        Intent actionLintent = new Intent(this, MainActivity.class);
        PendingIntent actionPendingIntend = PendingIntent.getActivity(this, 0, actionLintent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(actionPendingIntend);
        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }

    public void fecha() throws ParseException {

        String fechaD;
        int dia, mes, anio;

        Calendar calendar = Calendar.getInstance();
        dia = calendar.get(Calendar.DAY_OF_MONTH);
      ///dia = calendar.get(Calendar.DAY_OF_MONTH)+varDia;
        mes = calendar.get(Calendar.MONTH)+1;
        anio = calendar.get(Calendar.YEAR);

        fechaD = anio + "/" + mes + "/" + dia;

        this.fecha = fechaD;
      //varDia++;
    }

    public void hora(){

        String horaD;
        int hora, minutos, segundos;

        Calendar calendar = Calendar.getInstance();
        hora = calendar.get(Calendar.HOUR);
        minutos = calendar.get(Calendar.MINUTE);
        segundos = calendar.get(Calendar.SECOND);


        horaD = hora+":"+minutos+":"+segundos;

        this.hora = horaD;


    }

    public void direccion(Location loc) {

        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    dirCalle = list.get(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertarUbicaciones() throws ParseException {

        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this,"bd_usuarios", null,1);

        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Utilidades.CAMPO_LUGARVISITADO, dirCalle.getAddressLine(0));
        values.put(Utilidades.CAMPO_FECHA, this.fecha);
        values.put(Utilidades.CAMPO_HORA, this.hora);
        values.put(Utilidades.CAMPO_LONGITUD, longitud);
        values.put(Utilidades.CAMPO_LATITUD, latitud);
        values.put(Utilidades.CAMPO_TIEMPOLUGARVISITADO,time);

        SQLiteDatabase dbDos = conexion.getReadableDatabase();

        String[] parametros = {MainActivity.campoCorreoLogin.getText().toString()};
        String[] campos = {Utilidades.CAMPO_CEDULA};

        try {
            Cursor cursor = dbDos.query(Utilidades.TABLA_USUARIO, campos, Utilidades.CAMPO_CORREO + "=?", parametros, null, null, null);
            if(cursor.moveToFirst()) {
                do {
                    values.put(Utilidades.CAMPO_CEDULA, cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
        }
            db.insert(Utilidades.TABLA_UBICACION,Utilidades.CAMPO_CEDULA,values);
            db.close();
            start();
        }


    }

