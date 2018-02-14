package com.transmilenio.fuerzaoperativa.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.transmilenio.fuerzaoperativa.R;
import com.transmilenio.fuerzaoperativa.activites.MainActivity;
import com.transmilenio.fuerzaoperativa.models.json.EncuestaTM;
import com.transmilenio.fuerzaoperativa.models.json.TipoEncuesta;
import com.transmilenio.fuerzaoperativa.models.util.ExtrasID;
import com.transmilenio.fuerzaoperativa.models.util.Mensajes;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by nataly on 10/10/2017.
 */

public class AlertGuardarDatos extends DialogFragment {

    private Button buttonGuardar,buttonCancelar;
    private int idEncuesta;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogguardar, container,
                false);
        idEncuesta =  getArguments().getInt(ExtrasID.EXTRA_ID_ENCUESTA);
        return rootView;
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle(Mensajes.MSG_SALIR_ENCUESTA);

        buttonGuardar = (Button) view.findViewById(R.id.button_guardar_alert);
        buttonCancelar = (Button) view.findViewById(R.id.button_cancelar_alert);

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // Cerrar Dialog
            }
        });

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardar cambios
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });



    }




}
