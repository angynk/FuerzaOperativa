package com.transmilenio.fuerzaoperativa.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.transmilenio.fuerzaoperativa.R;
import com.transmilenio.fuerzaoperativa.activites.MainActivity;
import com.transmilenio.fuerzaoperativa.models.db.ConteoDesEncuesta;
import com.transmilenio.fuerzaoperativa.models.db.RegistroConteo;
import com.transmilenio.fuerzaoperativa.models.json.EncuestaTM;
import com.transmilenio.fuerzaoperativa.models.json.TipoEncuesta;
import com.transmilenio.fuerzaoperativa.models.util.ExtrasID;
import com.transmilenio.fuerzaoperativa.models.util.Mensajes;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;


public class AlertObservacion extends DialogFragment {

    private Button buttonFinalizar;
    private TextView mensajeTextView;
    private int idEncuesta;
    private String mensaje;
    private List<Integer> eliminar;


    public AlertObservacion() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_observacion, container,
                false);
        mensaje =  getArguments().getString(ExtrasID.EXTRA_MENSAJE);
        eliminar =  getArguments().getIntegerArrayList("lista");
        return rootView;
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle(Mensajes.MSG_OBSERVACIONES);

        buttonFinalizar = (Button) view.findViewById(R.id.d_finalizar_button);
        mensajeTextView = (TextView) view.findViewById(R.id.d_message_textView);
        mensajeTextView.setText(mensaje);

        buttonFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarResultados(eliminar);
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }

    private void eliminarResultados(int encuesta) {
        Realm realm = Realm.getDefaultInstance();
        EncuestaTM encuestaTM = realm.where(EncuestaTM.class).equalTo("id", encuesta).findFirst();
        if(encuestaTM!=null){
            if(encuestaTM.isValid()){
                int tipo = encuestaTM.getTipo();
                if ( tipo == TipoEncuesta.ENC_CONT_DESPACHOS){
                    ConteoDesEncuesta conteoDesEncuesta = encuestaTM.getCo_despachos();
                    realm.beginTransaction();
                    RealmList<RegistroConteo> registros = conteoDesEncuesta.getRegistros();
                    List<Integer> regIn= new ArrayList<>();
                    for(RegistroConteo re:registros){
                        regIn.add(re.getId());
                    }
                    for(Integer value:regIn){
                        RegistroConteo registro = realm.where(RegistroConteo.class).equalTo("id", value).findFirst();
                        if(registro!=null){
                            if(registro.isValid()){
                                registro.deleteFromRealm();
                            }
                        }
                    }
                    realm.commitTransaction();

                    realm.beginTransaction();
                    conteoDesEncuesta.deleteFromRealm();
                    realm.commitTransaction();
                    realm.beginTransaction();
                    encuestaTM.deleteFromRealm();
                    realm.commitTransaction();
                }

            }

        }




        realm.close();
    }


    private void eliminarResultados(List<Integer> resultado) {

        for(Integer resul:resultado){
            if(resul!=-1){
               eliminarResultados(resul);
            }
        }

    }
}
