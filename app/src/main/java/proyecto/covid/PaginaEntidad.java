package proyecto.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PaginaEntidad extends AppCompatActivity {

    ListView opcionesEntidad;
    String opcionesEntidadDos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_entidad);

        opcionesEntidad = (ListView) findViewById(R.id.listViewPaginaEntidad);
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this,R.array.OpcionesEntidad,
                android.R.layout.simple_list_item_1);

        opcionesEntidad.setAdapter(adaptador);
        opcionesEntidad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                opcionesEntidadDos = adapterView.getItemAtPosition(i).toString();
                irActivitys(opcionesEntidadDos);

            }
        });
    }

    public void irActivitys(String opciones){

        if(opciones.equals("Consultar cantidad de infectados")) {

        }else if(opciones.equals("Reporte de contagios confirmados")){

        }
    }
}