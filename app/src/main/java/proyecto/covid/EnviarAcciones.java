package proyecto.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import proyecto.covid.Utilidades.Utilidades;

public class EnviarAcciones extends AppCompatActivity {

    EditText de;
    EditText para;
    EditText asunto;
    EditText mensaje;
    public static String EMAIL;
    public static String PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_acciones);

        de = (EditText) findViewById(R.id.txtFieldFrom);
        para = (EditText) findViewById(R.id.txtFieldTo);
        asunto = (EditText) findViewById(R.id.txtFieldSubject);
        mensaje = (EditText) findViewById(R.id.txtFieldMessage);
    }

    public void onClick(View view){
        enviarMensaje();
    }

    public void enviarMensaje(){

        String de, para, asunto, mensaje, contrasena = "";

        de = this.de.getText().toString().trim();
        para = this.para.getText().toString().trim();
        mensaje = this.mensaje.getText().toString();
        asunto = this.asunto.getText().toString().trim();

        EMAIL = de;

        ConexionSQLiteHelper conexionDos = new ConexionSQLiteHelper(this,"bd_usuarios", null,1);
        SQLiteDatabase dbDos = conexionDos.getReadableDatabase();

        String[] parametrosDos = {de};
        String[] campos = {Utilidades.CAMPO_CONTRASENA};
        try {
            Cursor cursor = dbDos.query(Utilidades.TABLA_USUARIO, campos, Utilidades.CAMPO_CORREO+"=?",parametrosDos, null, null, null);
            if(cursor.moveToFirst()){
                do {
                    contrasena = cursor.getString(0);
                } while (cursor.moveToNext());
            }else{
            }
            cursor.close();
        }catch (Exception e){
        }

        PASSWORD = contrasena;

        JavaMailAPI javaMailAPI = new JavaMailAPI(this,para,asunto,mensaje);

        javaMailAPI.execute();
    }
}