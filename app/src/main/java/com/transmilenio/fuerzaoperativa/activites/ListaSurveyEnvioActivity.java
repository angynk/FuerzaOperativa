
package com.transmilenio.fuerzaoperativa.activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.transmilenio.fuerzaoperativa.R;
import com.transmilenio.fuerzaoperativa.adapters.SurveySendAdapter;
import com.transmilenio.fuerzaoperativa.fragments.AlertObservacion;
import com.transmilenio.fuerzaoperativa.http.API;
import com.transmilenio.fuerzaoperativa.http.SurveyService;
import com.transmilenio.fuerzaoperativa.models.db.ConteoDesEncuesta;
import com.transmilenio.fuerzaoperativa.models.db.RegistroConteo;
import com.transmilenio.fuerzaoperativa.models.db.Resultado;
import com.transmilenio.fuerzaoperativa.models.json.CO_Despacho;
import com.transmilenio.fuerzaoperativa.models.json.EncuestaJSON;
import com.transmilenio.fuerzaoperativa.models.json.EncuestaTM;
import com.transmilenio.fuerzaoperativa.models.json.EncuestasTerminadas;
import com.transmilenio.fuerzaoperativa.models.json.RegCoDespachos;
import com.transmilenio.fuerzaoperativa.models.json.TipoEncuesta;
import com.transmilenio.fuerzaoperativa.models.util.ExtrasID;
import com.transmilenio.fuerzaoperativa.models.util.Mensajes;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

;

public class ListaSurveyEnvioActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<EncuestaTM>> {

    private ListView listView;
    private Button buttonEnviar;
    private SurveySendAdapter surveyAdapter;
    private RealmResults<EncuestaTM> encuestas;
    private SharedPreferences prefs;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_survey_envio);
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        realm = Realm.getDefaultInstance();
        encuestas = realm.where(EncuestaTM.class).findAll();
        encuestas.addChangeListener(this);
        bindUI();
    }

    private void bindUI() {

        listView = (ListView) findViewById(R.id.listView_send_surveys);
        buttonEnviar = (Button) findViewById(R.id.button_enviar_encuestas);
        surveyAdapter = new SurveySendAdapter(this,encuestas,R.layout.list_view_send_survey);
        listView.setAdapter(surveyAdapter);

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(surveyAdapter.getSelectedItems().size()>0){
                    enviarDatosEncuesta();
                }else{
                    Toast.makeText(ListaSurveyEnvioActivity.this,Mensajes.MSG_NO_HAY_ENCUESTAS,Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void enviarDatosEncuesta() {
        ArrayList<EncuestaTM> selectedItems = surveyAdapter.getSelectedItems();
        progressDoalog = new ProgressDialog(ListaSurveyEnvioActivity.this);
        progressDoalog.setMessage(Mensajes.MSG_ENVIANDO);
        progressDoalog.setTitle(Mensajes.MSG_ENCUESTA);
        progressDoalog.setCanceledOnTouchOutside(false);
        progressDoalog.show();

        EncuestasTerminadas encuestas = new EncuestasTerminadas();
        ArrayList<EncuestaJSON> valores = new ArrayList<>();
        for(EncuestaTM encuestaTM:selectedItems) {
            valores.add(convertToJSON(encuestaTM));
        }
        encuestas.setEncuestas(valores);
        enviarEncuesta(encuestas);

    }

    private EncuestaJSON convertToJSON(EncuestaTM encuestaTM) {
        EncuestaJSON encuestaJSON = new EncuestaJSON();
        encuestaJSON.setTipo(encuestaTM.getTipo());
        encuestaJSON.setNombre_encuesta(encuestaTM.getNombre_encuesta());
        encuestaJSON.setAforador(encuestaTM.getAforador());
        encuestaJSON.setId_realm(encuestaTM.getId());
        encuestaJSON.setFecha_encuesta(encuestaTM.getFecha_encuesta());
        encuestaJSON.setDia_semana(encuestaTM.getDia_semana());
        if (encuestaTM.getTipo() == TipoEncuesta.ENC_CONT_DESPACHOS){
            generarEncuestaConteoDespachos(encuestaTM,encuestaJSON);
        }

        return encuestaJSON;
    }





    private void generarEncuestaConteoDespachos(EncuestaTM encuestaTM, EncuestaJSON encuestaJSON) {
        ConteoDesEncuesta conteoDesEncuesta = encuestaTM.getCo_despachos();
        CO_Despacho co_despacho = new CO_Despacho();
        co_despacho.setEstacion(conteoDesEncuesta.getEstacion());

        RealmList<RegistroConteo> registroBD = conteoDesEncuesta.getRegistros();
        List<RegCoDespachos> registros = new ArrayList<>();
        for(RegistroConteo reg:registroBD){
            RegCoDespachos registro = new RegCoDespachos();
            registro.setNum_bus(reg.getNumBus());
            registro.setHora_despacho(reg.getHoradespacho());
            registro.setServicio(reg.getServicio());
            registros.add(registro);
        }

        co_despacho.setRegistros(registros);
        encuestaJSON.setCo_despacho(co_despacho);
    }


    ProgressDialog progressDoalog;

    private void enviarEncuesta(final EncuestasTerminadas encuestas) {
        SurveyService surveyService = API.getApi().create(SurveyService.class);
        Call<List<Resultado>> call = surveyService.sendSurvey(encuestas);
               call.enqueue(new Callback<List<Resultado>>() {
            @Override
            public void onResponse(Call<List<Resultado>> call, Response<List<Resultado>> response) {
                List<Resultado>  resulta = response.body();
                progressDoalog.dismiss();
                showAlertDialog(Mensajes.MSG_ENCUESTAS_ENVIADAS,resulta);
            }



                   @Override
            public void onFailure(Call<List<Resultado>> call, Throwable t) {
                progressDoalog.dismiss();
                showAlertDialog(Mensajes.MSG_ENCUESTAS_NO_ENVIADAS, new ArrayList<Resultado>());
            }
        });
    }



    FragmentManager fm = getSupportFragmentManager();

    private void showAlertDialog(String mensaje, final List<Resultado> resultado){

                 AlertObservacion dFragment = newInstance(resultado,mensaje);
                    dFragment.show(fm, Mensajes.MSG_SALIR_ENVIO);
    }



    public static AlertObservacion newInstance(final List<Resultado> resultado,String mensaje) {
        AlertObservacion f = new AlertObservacion();
        ArrayList<Integer> lista = new ArrayList<>();
        for(Resultado res : resultado){
            lista.add(res.getId());
        }

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putIntegerArrayList("lista", lista);
        args.putString(ExtrasID.EXTRA_MENSAJE,mensaje);
        f.setArguments(args);

        return f;
    }



    @Override
    public void onChange(RealmResults<EncuestaTM> cuadros) {
        surveyAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
