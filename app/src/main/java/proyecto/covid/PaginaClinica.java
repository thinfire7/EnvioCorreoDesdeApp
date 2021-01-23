package proyecto.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PaginaClinica extends AppCompatActivity {

    ListView opcionesClinica;
    String opcionesClinicaDos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_clinica);

        opcionesClinica = (ListView) findViewById(R.id.listViewPaginaClinica);
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this,R.array.OpcionesClinica,
                android.R.layout.simple_list_item_1);

        opcionesClinica.setAdapter(adaptador);
        opcionesClinica.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                opcionesClinicaDos = adapterView.getItemAtPosition(i).toString();
                irActivitys(opcionesClinicaDos);

            }
        });
    }

    public void irActivitys(String opciones){

        if(opciones.equals("Enviar acciones a tomar")) {
            Intent siguiente = new Intent(this, EnviarAcciones.class);
            startActivity(siguiente);
        }else if(opciones.equals("Reporte sintomas de usuarios")){

        }else if(opciones.equals("Cambiar estado de usuario")){
            Intent siguiente = new Intent(this, CambioEstadoUsuario.class);
            startActivity(siguiente);
        }
    }

}
