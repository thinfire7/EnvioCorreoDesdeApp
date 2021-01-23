package proyecto.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import proyecto.covid.Utilidades.Utilidades;

public class HistorialUbicaciones extends AppCompatActivity {

    TextView historial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_ubicaciones);

        historial = (TextView)findViewById(R.id.txtFieldHistorial);

        historialUbicaciones();
    }

    public void onClick(View view){
        Intent siguiente = new Intent(this, MiUbicacionGoogleMaps.class);
        startActivity(siguiente);
    }

    public void historialUbicaciones() {

        String historial = "";

        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this, "bd_usuarios", null, 1);

        SQLiteDatabase db = conexion.getReadableDatabase();

        String[] parametros = {PaginaPrincipal.cedula};
        String[] campos = {Utilidades.CAMPO_LUGARVISITADO};

        try {
            Cursor cursor = db.query(Utilidades.TABLA_UBICACION, campos, Utilidades.CAMPO_CEDULA + "=?", parametros, null, null, null, null);
            if(cursor.moveToFirst()) {
                do {
                    historial += cursor.getString(0) + "\n";
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
        }
        this.historial.setText(historial);
    }
}