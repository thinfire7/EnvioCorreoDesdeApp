package proyecto.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import proyecto.covid.Utilidades.Utilidades;

public class MainActivity extends AppCompatActivity {

    static EditText campoCorreoLogin;
    EditText campoContrasenaLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this,"bd_usuarios", null,1);

        campoCorreoLogin = (EditText) findViewById(R.id.txtfieldCorreoLogin);
        campoContrasenaLogin = (EditText) findViewById(R.id.txtfieldContrasenaLogin);

    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.btnIngresar:
                login(campoCorreoLogin,campoContrasenaLogin);
                break;
            case R.id.btnRegistrate:
                Intent siguiente = new Intent(this, Registro.class);
                startActivity(siguiente);
                break;
        }
        
    }

    public void login(EditText correo, EditText contrasena){


        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this,"bd_usuarios", null,1);

        SQLiteDatabase db = conexion.getReadableDatabase();

        String[] parametros = {campoCorreoLogin.getText().toString()};
        String[] campos = {Utilidades.CAMPO_CORREO,Utilidades.CAMPO_CONTRASENA};

        try {
            Cursor cursor = db.query(Utilidades.TABLA_USUARIO, campos, Utilidades.CAMPO_CORREO + "=?", parametros, null, null, null);
            cursor.moveToFirst();

            if(cursor.getString(1).equals(contrasena.getText().toString())){

                String[] parametrosDos = {cursor.getString(0)};
                String[] camposDos = {Utilidades.CAMPO_TIPOCUENTA};
                try {
                    Cursor cursorDos = db.query(Utilidades.TABLA_USUARIO, camposDos, Utilidades.CAMPO_CORREO + "=?", parametrosDos, null, null, null);
                    cursorDos.moveToFirst();
                    if(cursorDos.getString(0).equals("Usuario")){
                        Intent siguiente = new Intent(this, PaginaPrincipal.class);
                        startActivity(siguiente);
                    }else if(cursorDos.getString(0).equals("Entidad")){
                        Intent siguiente = new Intent(this, PaginaEntidad.class);
                        startActivity(siguiente);
                    }else if(cursorDos.getString(0).equals("Clinica")){
                        Intent siguiente = new Intent(this, PaginaClinica.class);
                        startActivity(siguiente);
                    }
                    cursorDos.close();
                }catch (Exception e){
                }
            }else{
                Toast.makeText(getApplicationContext(),"Contraseña incorrecta", Toast.LENGTH_LONG).show();
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El usuario no existe", Toast.LENGTH_LONG).show();
        }
    }

}
