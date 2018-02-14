package com.transmilenio.fuerzaoperativa.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.transmilenio.fuerzaoperativa.R;
import com.transmilenio.fuerzaoperativa.adapters.RegistroConteoDeAdapter;
import com.transmilenio.fuerzaoperativa.fragments.AlertGuardarDatos;
import com.transmilenio.fuerzaoperativa.models.db.ConteoDesEncuesta;
import com.transmilenio.fuerzaoperativa.models.db.RegistroConteo;
import com.transmilenio.fuerzaoperativa.models.util.ExtrasID;
import com.transmilenio.fuerzaoperativa.models.util.Mensajes;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;

public class ListaRegistrosConteoActivity extends AppCompatActivity implements RealmChangeListener<RealmList<RegistroConteo>> {


    private FloatingActionButton buttonAdd;
    private Button buttonGuardar;
    private ListView listView;
    private RealmList<RegistroConteo> registros;
    private int  idEncuesta, idCuadro ;
    private String estacion;
    private RegistroConteoDeAdapter adapter;

    FragmentManager fm = getSupportFragmentManager();
    private Realm realm;

    public ListaRegistrosConteoActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_registros_conteo);
        realm = Realm.getDefaultInstance();
        validarExtras();
        bindUI();
        setActionBarBotton();
    }

    private void bindUI() {
        buttonAdd = (FloatingActionButton) findViewById(R.id.cod_nuevo_button);
        buttonGuardar = (Button) findViewById(R.id.cod_guardar_button);

        listView = (ListView) findViewById(R.id.cod_registros_listView);
        adapter = new RegistroConteoDeAdapter(this,registros,R.layout.list_view_registro_item);
        listView.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListaRegistrosConteoActivity.this,ConteoServActivity.class);
                intent.putExtra(ExtrasID.EXTRA_ID_ENCUESTA,  idEncuesta);
                intent.putExtra(ExtrasID.EXTRA_ID_CUADRO,  idCuadro);
                intent.putExtra(ExtrasID.EXTRA_ID_ESTACION,  estacion);
                startActivity(intent);
            }
        });

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertGuardarDatos dFragment = newInstance(idEncuesta);
                dFragment.show(fm, Mensajes.MSG_SALIR_ENCUESTA);
            }
        });
    }

    private void validarExtras() {

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            idEncuesta = (int) extras.get(ExtrasID.EXTRA_ID_ENCUESTA);
            idCuadro = (int) extras.get(ExtrasID.EXTRA_ID_CUADRO);
            estacion = (String) extras.get(ExtrasID.EXTRA_ID_ESTACION);
            ConteoDesEncuesta cuadro =  realm.where(ConteoDesEncuesta.class).equalTo("id",idCuadro).findFirst();
            if(cuadro!=null){
                registros = cuadro.getRegistros();
                registros.addChangeListener(this);
            }
        }
    }

    private void setActionBarBotton() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public void onChange(RealmList<RegistroConteo> registroAdPuntos) {
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertGuardarDatos dFragment = newInstance(idEncuesta);
                dFragment.show(fm, Mensajes.MSG_SALIR_ENCUESTA);
                break;
        }
        return true;
    }

    public static AlertGuardarDatos newInstance(int id) {
        AlertGuardarDatos f = new AlertGuardarDatos();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt(ExtrasID.EXTRA_ID_ENCUESTA, id);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onBackPressed() {
        AlertGuardarDatos dFragment = newInstance(idEncuesta);
        dFragment.show(fm, Mensajes.MSG_SALIR_ENCUESTA);
    }
}
