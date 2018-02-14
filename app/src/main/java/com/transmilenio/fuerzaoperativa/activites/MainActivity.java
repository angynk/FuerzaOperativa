package com.transmilenio.fuerzaoperativa.activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.transmilenio.fuerzaoperativa.R;
import com.transmilenio.fuerzaoperativa.adapters.OptionAdapter;
import com.transmilenio.fuerzaoperativa.http.API;
import com.transmilenio.fuerzaoperativa.http.SurveyService;
import com.transmilenio.fuerzaoperativa.models.db.Estacion;
import com.transmilenio.fuerzaoperativa.models.db.EstacionServicio;
import com.transmilenio.fuerzaoperativa.models.db.Opcion;
import com.transmilenio.fuerzaoperativa.models.db.Serv;
import com.transmilenio.fuerzaoperativa.models.db.ServicioRutas;
import com.transmilenio.fuerzaoperativa.models.json.Config;
import com.transmilenio.fuerzaoperativa.models.json.EncuestaTM;
import com.transmilenio.fuerzaoperativa.models.json.EstacionTs;
import com.transmilenio.fuerzaoperativa.models.json.Servicio;
import com.transmilenio.fuerzaoperativa.models.json.TipoEncuesta;
import com.transmilenio.fuerzaoperativa.models.util.ExtrasID;
import com.transmilenio.fuerzaoperativa.models.util.Mensajes;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {

    private ListView listView;
    private OptionAdapter adapter;
    private List<Opcion> opcionesList;
    private ProgressDialog progressDoalog;
    private TextView userNameTextView,encuePendientes,encuesEnviadas;

    private Realm realm;
    private SharedPreferences prefs;
    private String tipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        validarExtras();
        bindUI();
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        String nombreUsuario = prefs.getString(ExtrasID.EXTRA_USER,ExtrasID.TIPO_USUARIO_INVITADO);
        userNameTextView.setText(nombreUsuario);
        String encPendientes = "0";
        String encEnviadas = "0";
        encuePendientes.setText(encPendientes);
        encuesEnviadas.setText(encEnviadas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.lg_menu_salir:
                removeSharedPreferences();
                logOut();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }



    private void logOut(){
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void removeSharedPreferences(){
        prefs.edit().clear().apply();
    }

    private void bindUI(){
        listView = (ListView) findViewById(R.id.ini_opciones_listView);
        userNameTextView = (TextView) findViewById(R.id.bn_user_textView);
        encuesEnviadas = (TextView) findViewById(R.id.ini_encuestasC_textView);
        encuePendientes = (TextView) findViewById(R.id.ini_encuestasP_textView);
        opcionesList = new ArrayList<>();
        cargarOpciones();
        adapter = new OptionAdapter(this, opcionesList,R.layout.list_view_options);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                Opcion value = (Opcion) adapter.getItemAtPosition(position);
                if(value.getName().equals(Mensajes.OPCION_NUEVA)){
                    Intent intent = new Intent(MainActivity.this,SeleccionModoActivity.class);
                    startActivity(intent);
                }else if( value.getName().equals(Mensajes.OPCION_ENVIAR)){
                    Intent intent = new Intent(MainActivity.this,ListaSurveyEnvioActivity.class);
                    startActivity(intent);
                }else if( value.getName().equals(Mensajes.OPCION_CONFIG)){
                    cargarServiciosTemporal();
                }else if(value.getName().equals(Mensajes.OPCION_USUARIO)){
                    Intent intent = new Intent(MainActivity.this,CreacionUsuariosActivity.class);
                    startActivity(intent);
                }

            }
        });
    }


    //Validacion de datos entre actividades
    private void validarExtras(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            Object idEliminar =  extras.get(ExtrasID.EXTRA_ID_ENCUESTA);
            if(idEliminar!=null){
                eliminarEncuesta((int)idEliminar);
            }

        }
        tipoUsuario = prefs.getString(ExtrasID.EXTRA_TIPO_USUARIO,ExtrasID.TIPO_USUARIO_INVITADO);
    }

    private void eliminarEncuesta(final int idEncuesta) {
        //Eliminar Encuesta y Registros
        EncuestaTM encuestaTM = realm.where(EncuestaTM.class).equalTo("id", idEncuesta).findFirst();
        if(encuestaTM!=null){
            int tipo = encuestaTM.getTipo();

        }
    }


    private void cargarServiciosTemporal(){
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage(Mensajes.MSG_SINCRONIZANDO);
        progressDoalog.setTitle(Mensajes.MSG_CONFIGURACION);
        progressDoalog.setCanceledOnTouchOutside(false);
        progressDoalog.show();

        SurveyService surveyService = API.getApi().create(SurveyService.class);
        Call<Config> call = surveyService.getServicios();
        call.enqueue(new Callback<Config>() {
            @Override
            public void onResponse(Call<Config> call, Response<Config> response) {
                    guardarServicios(response.body().getServicios());
                    guardarEstaciones(response.body().getEstacionTs());
                    progressDoalog.dismiss();
                    Toast.makeText(MainActivity.this,Mensajes.MSG_SINCRONIZACION,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Config> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(MainActivity.this,Mensajes.MSG_SINCRONIZACION_FALLO,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarEstaciones(List<EstacionTs> estaciones) {
        eliminarInfoEstaciones();
        for (EstacionTs estacion:estaciones){
            EstacionServicio estacionServicio = new EstacionServicio(estacion.getNombre(),estacion.getTipo());
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(estacionServicio);
            realm.commitTransaction();
            cargarServicios(estacion.getNombre(),estacion.getServicios());

        }
    }

    private void cargarServicios(String nombre, List<String> servicios) {

        realm.beginTransaction();
        EstacionServicio estacionServicio = realm.where(EstacionServicio.class).equalTo("nombre", nombre).findFirst();
        for(String servNombre: servicios){
            Serv serv = realm.where(Serv.class).equalTo("nombre", servNombre).findFirst();
            if(serv==null){
                serv = new Serv(servNombre);
                realm.copyToRealmOrUpdate(serv);
            }
            estacionServicio.getServicios().add(serv);
        }
        realm.commitTransaction();
    }

    private void eliminarInfoEstaciones() {
        realm.beginTransaction();
        realm.delete(Serv.class);
        realm.delete(EstacionServicio.class);
        realm.commitTransaction();
    }


    public void guardarServicios(List<Servicio> servicios){

        eliminarInfoServicios();

        for (Servicio servicio:servicios){
            ServicioRutas servicioRutas = new ServicioRutas(servicio.getNombre(),servicio.getTipo());
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(servicioRutas);
            realm.commitTransaction();
            cargarEstaciones(servicioRutas.getNombre(),servicio.getEstaciones(),servicio.getTipo());

        }
    }

    private void eliminarInfoServicios() {
        realm.beginTransaction();
        realm.delete(Estacion.class);
        realm.delete(ServicioRutas.class);
        realm.commitTransaction();
    }

    private void cargarEstaciones(String nombre, List<String> estaciones, String tipo) {
        realm.beginTransaction();
        ServicioRutas servicioRutas = realm.where(ServicioRutas.class).equalTo("nombre", nombre).findFirst();
        for(String estacionNombre: estaciones){
            Estacion estacion = realm.where(Estacion.class).equalTo("nombre", estacionNombre).findFirst();
            if(estacion==null){
                estacion = new Estacion(estacionNombre,tipo);
                realm.copyToRealmOrUpdate(estacion);
            }
            servicioRutas.getEstaciones().add(estacion);
        }
        realm.commitTransaction();
    }


    //Cargar opciones
    private void cargarOpciones() {
        int[] covers = new int[]{
                R.drawable.ic_new_icon,
                R.drawable.ic_send_icon,
                R.drawable.ic_settings_icon,
                R.drawable.ic_user_icon};

        Opcion a = new Opcion(Mensajes.OPCION_NUEVA, covers[0]);
        opcionesList.add(a);

        a = new Opcion(Mensajes.OPCION_ENVIAR, covers[1]);
        opcionesList.add(a);

        a = new Opcion(Mensajes.OPCION_CONFIG, covers[2]);
        opcionesList.add(a);

        if(tipoUsuario.equals(ExtrasID.TIPO_USUARIO_ADMIN)){
            a = new Opcion(Mensajes.OPCION_USUARIO, covers[3]);
            opcionesList.add(a);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
