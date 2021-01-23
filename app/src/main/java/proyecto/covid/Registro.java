package proyecto.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import proyecto.covid.Utilidades.Utilidades;

public class Registro extends AppCompatActivity {

    private EditText campoNombre;
    private EditText campoApellido;
    private EditText campoCorreo;
    private EditText campoContrasena;
    private EditText campoEdad;
    private EditText campoCedula;

    private RadioButton campoMasculino;
    private RadioButton campoFemenino;
    private RadioButton campoOtro;

    private RadioButton campoUsuario;
    private RadioButton campoEntidad;
    private RadioButton campoClinica;

    private Spinner localidades;

    String localidad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        campoNombre = (EditText) findViewById(R.id.txtfieldNombre);
        campoApellido = (EditText) findViewById(R.id.txtfieldApellidos);
        campoCorreo = (EditText) findViewById(R.id.txtfieldCorreo);
        campoContrasena = (EditText) findViewById(R.id.txtfieldContrasenaRegistrate);
        campoEdad = (EditText) findViewById(R.id.txtfieldEdad);
        campoCedula = (EditText) findViewById(R.id.txtfieldCedula);

        campoMasculino = (RadioButton) findViewById(R.id.radioButtonMasculino);
        campoFemenino = (RadioButton) findViewById(R.id.radioButtonFemenino);
        campoOtro = (RadioButton) findViewById(R.id.radioButtonOtro);

        campoUsuario = (RadioButton) findViewById(R.id.radioButtonUsuario);
        campoEntidad = (RadioButton) findViewById(R.id.radioButtonEntidad);
        campoClinica = (RadioButton) findViewById(R.id.radioButtonClinica);

        localidades = (Spinner) findViewById(R.id.spinnerLocalidades);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Android_Spinner_Example, android.R.layout.simple_spinner_item);

        localidades.setAdapter(adapter);

        localidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                localidad = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void envioDatos(View view){

        registrarUsuarios();
    }


    public void registrarUsuarios(){

        String campoSexo = "";
        String campoTipoCuenta = "";

        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this,"bd_usuarios", null,1);

        SQLiteDatabase db = conexion.getWritableDatabase();

        if(campoMasculino.isChecked()){
            campoSexo = "Masculino";
        }if(campoFemenino.isChecked()){
            campoSexo = "Femenino";
        }if(campoOtro.isChecked()){
            campoSexo = "Otro";
        }

        if(campoUsuario.isChecked()){
            campoTipoCuenta = "Usuario";
        }if(campoEntidad.isChecked()){
            campoTipoCuenta = "Entidad";
        }if(campoClinica.isChecked()){
            campoTipoCuenta = "Clinica";
        }


        ContentValues values = new ContentValues();


        values.put(Utilidades.CAMPO_NOMBRE, campoNombre.getText().toString());
        values.put(Utilidades.CAMPO_APELLIDO, campoApellido.getText().toString());
        values.put(Utilidades.CAMPO_CORREO, campoCorreo.getText().toString());
        values.put(Utilidades.CAMPO_CONTRASENA, campoContrasena.getText().toString());
        values.put(Utilidades.CAMPO_EDAD, campoEdad.getText().toString());
        values.put(Utilidades.CAMPO_SEXO, campoSexo);
        values.put(Utilidades.CAMPO_TIPOCUENTA, campoTipoCuenta);
        values.put(Utilidades.CAMPO_ESTADO, "negativo");
        values.put(Utilidades.CAMPO_LOCALIDAD, localidad);
        values.put(Utilidades.CAMPO_CEDULA, campoCedula.getText().toString());

        Long idResultante = db.insert(Utilidades.TABLA_USUARIO,Utilidades.CAMPO_CEDULA,values);

        Toast.makeText(getApplicationContext(),"Id Registro: " + idResultante, Toast.LENGTH_SHORT).show();

        db.close();
    }
}