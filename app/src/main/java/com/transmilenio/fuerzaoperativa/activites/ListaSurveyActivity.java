package com.transmilenio.fuerzaoperativa.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.transmilenio.fuerzaoperativa.R;
import com.transmilenio.fuerzaoperativa.adapters.SurveyAdapter;
import com.transmilenio.fuerzaoperativa.models.db.Encuesta;
import com.transmilenio.fuerzaoperativa.models.util.ExtrasID;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ListaSurveyActivity extends AppCompatActivity {

    private ListView listView;
    private SurveyAdapter surveyAdapter;
    private List<Encuesta> encuestas;
    private Realm realm;
    private String modo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_survey);
        realm = Realm.getDefaultInstance();
        extraerExtras();
        bindUI();
        definirEventos();
    }

    private void extraerExtras() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            modo = (String) extras.get(ExtrasID.EXTRA_MODO);
        }
    }


    private void bindUI() {
        encuestas = new ArrayList<>();;
        encuestas.add(new Encuesta(ExtrasID.NOMBRE_ENCUESTA_CONTEO_DESPACHOS));
        listView = (ListView) findViewById(R.id.listView_surveys);
        surveyAdapter = new SurveyAdapter(this,encuestas,R.layout.list_view_surveys);
        listView.setAdapter(surveyAdapter);
    }

    private void definirEventos(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                Encuesta value = (Encuesta)adapter.getItemAtPosition(position);
                if (value.getNombre().equals(ExtrasID.NOMBRE_ENCUESTA_CONTEO_DESPACHOS)) {
                    Intent intent = new Intent(ListaSurveyActivity.this, ConteoDesActivity.class);
                    intent.putExtra(ExtrasID.EXTRA_NOMBRE,value.getNombre());
                    intent.putExtra(ExtrasID.EXTRA_MODO,modo);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
