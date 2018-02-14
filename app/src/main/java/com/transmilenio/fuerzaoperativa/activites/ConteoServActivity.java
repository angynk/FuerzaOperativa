package com.transmilenio.fuerzaoperativa.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.transmilenio.fuerzaoperativa.R;
import com.transmilenio.fuerzaoperativa.adapters.GridServiciosAdapter;
import com.transmilenio.fuerzaoperativa.models.db.EstacionServicio;
import com.transmilenio.fuerzaoperativa.models.db.Serv;
import com.transmilenio.fuerzaoperativa.models.util.ExtrasID;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class ConteoServActivity extends AppCompatActivity {

    private int idCuadro;
    private int idEncuesta;
    private String estacion;
    private GridView gridView;
    private List<String> servicios;
    private Realm realm;
    private FloatingActionButton buttonCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteo_serv);
        realm = Realm.getDefaultInstance();
        getExtras();
        bindUi();
    }

    public void getExtras(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            idCuadro = (int) extras.get(ExtrasID.EXTRA_ID_CUADRO);
            estacion = (String) extras.get(ExtrasID.EXTRA_ID_ESTACION);
            idEncuesta = (int) extras.get(ExtrasID.EXTRA_ID_ENCUESTA);
        }
    }

    public void bindUi(){
        servicios = new ArrayList<>();
        encontrarServicios();
        gridView = (GridView) findViewById(R.id.cod_gridView);
        buttonCerrar = (FloatingActionButton) findViewById(R.id.cos_cancelarRegistro_button);
        GridServiciosAdapter adapter = new GridServiciosAdapter(this,servicios,R.layout.service_layout);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
               Intent intent = new Intent(ConteoServActivity.this,ConteoRegistroActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra(ExtrasID.EXTRA_ID_ENCUESTA,  idEncuesta);
                intent.putExtra(ExtrasID.EXTRA_ID_CUADRO,  idCuadro);
                intent.putExtra(ExtrasID.EXTRA_ID_ESTACION,  estacion);
                intent.putExtra(ExtrasID.EXTRA_ID_SERVICIO,  servicios.get(position));
                startActivity(intent);
            }});

        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConteoServActivity.this,ListaRegistrosConteoActivity.class);
                intent.putExtra(ExtrasID.EXTRA_ID_ENCUESTA,  idEncuesta);
                intent.putExtra(ExtrasID.EXTRA_ID_CUADRO,  idCuadro);
                intent.putExtra(ExtrasID.EXTRA_ID_ESTACION,  estacion);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void encontrarServicios() {
        if(estacion!=null){
           EstacionServicio estaciones = realm.where(EstacionServicio.class).equalTo("nombre", estacion).findFirst();
            if(estaciones!=null){
                RealmList<Serv> listServ = estaciones.getServicios();
                for(Serv serv:listServ){
                    if(serv.getNombre().length()> 13){
                        servicios.add(serv.getNombre().substring(0,13));
                    }else{
                        servicios.add(serv.getNombre());
                    }

                }


            }
        }

    }
}
