package com.transmilenio.fuerzaoperativa.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.transmilenio.fuerzaoperativa.R;
import com.transmilenio.fuerzaoperativa.fragments.AlertObservacion;
import com.transmilenio.fuerzaoperativa.models.db.ConteoDesEncuesta;
import com.transmilenio.fuerzaoperativa.models.db.RegistroConteo;
import com.transmilenio.fuerzaoperativa.models.util.ExtrasID;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

public class ConteoRegistroActivity extends AppCompatActivity {

    private ImageButton buttonDespacho;
    private TextView textDespacho,textServicio;
    private EditText numBus;
    private String servicio,estacion;
    private FloatingActionButton buttonNuevo;
    private Realm realm;

    private int idCuadroEncuesta,idEncuesta;
    private ConteoDesEncuesta encuesta;


    public ConteoRegistroActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteo_registro);
        realm = Realm.getDefaultInstance();
        validarExtras();
        bindUI();
        setActionBarBotton();
    }

    private void validarExtras() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            idEncuesta = (int) extras.get(ExtrasID.EXTRA_ID_ENCUESTA);
            idCuadroEncuesta = (int) extras.get(ExtrasID.EXTRA_ID_CUADRO);
            servicio = (String) extras.get(ExtrasID.EXTRA_ID_SERVICIO);
            estacion = (String) extras.get(ExtrasID.EXTRA_ID_ESTACION);
            encuesta = realm.where(ConteoDesEncuesta.class).equalTo("id",idCuadroEncuesta).findFirst();
        }
    }

    private void bindUI() {
        buttonDespacho = (ImageButton) findViewById(R.id.cod_despacho_button);
        textDespacho = (TextView) findViewById(R.id.cod_despacho_textView);
        textServicio = (TextView) findViewById(R.id.cod_servicio_textView);
        numBus = (EditText) findViewById(R.id.cod_num_bus_editText);
        buttonNuevo = (FloatingActionButton) findViewById(R.id.cod_nuevoRegistro_button);

        agregarEventosBotones();
        textServicio.setText(servicio);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                inluirDatos();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        inluirDatos();
    }

    private void inluirDatos() {
        Intent intent = new Intent(this, ListaRegistrosConteoActivity.class);
        intent.putExtra(ExtrasID.EXTRA_ID_ENCUESTA,idEncuesta);
        intent.putExtra(ExtrasID.EXTRA_ID_CUADRO,idCuadroEncuesta);
        intent.putExtra(ExtrasID.EXTRA_ID_ESTACION,  estacion);
        startActivity(intent);
    }

    private void setActionBarBotton() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void agregarEventosBotones() {
        buttonDespacho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
                Date ahora = new Date();
                textDespacho.setText(formato.format(ahora));
            }
        });

        buttonNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(agregarRegistro()){
                    Intent intent = new Intent(ConteoRegistroActivity.this,ConteoServActivity.class);
                    intent.putExtra(ExtrasID.EXTRA_ID_ENCUESTA,  idEncuesta);
                    intent.putExtra(ExtrasID.EXTRA_ID_CUADRO,  idCuadroEncuesta);
                    intent.putExtra(ExtrasID.EXTRA_ID_ESTACION,  estacion);
                    startActivity(intent);
                    finish();
                }

            }
        });



    }

    public static AlertObservacion newInstance(int id, String servicio) {
        AlertObservacion f = new AlertObservacion();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt(ExtrasID.EXTRA_ID_ENCUESTA, id);
        args.putString(ExtrasID.EXTRA_ID_SERVICIO, servicio);
        f.setArguments(args);
        return f;
    }


    private boolean agregarRegistro(){
        if(informacionCompletada()){
            final RegistroConteo registro = crearObjetoRegistro();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(registro);
                    encuesta.getRegistros().add(registro);
                }
            });

        }else{
            Toast.makeText(ConteoRegistroActivity.this,"Complete todos los campos",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

    private RegistroConteo crearObjetoRegistro() {
        RegistroConteo registro = new RegistroConteo();
        registro.setNumBus(numBus.getText().toString());
        registro.setHoradespacho(textDespacho.getText().toString());
        registro.setServicio(servicio);
        return registro;
    }

    private boolean informacionCompletada() {
        if( textDespacho.getText().toString().trim().equals("H.Despacho") ||
                numBus.getText().toString().trim().equals("")){
            return false;
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

}
