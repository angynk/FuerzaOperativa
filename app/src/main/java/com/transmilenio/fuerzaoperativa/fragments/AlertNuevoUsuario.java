package com.transmilenio.fuerzaoperativa.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.transmilenio.fuerzaoperativa.R;
import com.transmilenio.fuerzaoperativa.models.db.Aforador;
import com.transmilenio.fuerzaoperativa.models.util.Mensajes;

import io.realm.Realm;

public class AlertNuevoUsuario extends DialogFragment {

    private EditText userEditText,passUserEditText;
    private Button crearButton,cancelarButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_nuevo_usuario, container,
                false);
        return rootView;
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle(Mensajes.MSG_CREAR_USUARIO);

        userEditText = (EditText) view.findViewById(R.id.dn_usuario_editText);
        passUserEditText = (EditText) view.findViewById(R.id.dn_pass_editText);
        crearButton = (Button) view.findViewById(R.id.dn_nuevo_button);
        cancelarButton = (Button) view.findViewById(R.id.dn_cancelar_button);

        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // Cerrar Dialog
            }
        });

        crearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardar cambios
                crearUsuario();
//                Intent intent = new Intent(view.getContext(), CreacionUsuariosActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
                dismiss();
            }


        });


            }

    private void crearUsuario() {
        Realm realm = Realm.getDefaultInstance();
        String user = userEditText.getText().toString();
        String pass = passUserEditText.getText().toString();
        if(!TextUtils.isEmpty(user) &&
                !TextUtils.isEmpty(pass)){
            //Validar Existencia Usuario
           Aforador af =  realm.where(Aforador.class).equalTo("usuario",user).findFirst();
            if(af==null){
                realm.beginTransaction();
                Aforador nuevoAforador = new Aforador(user,pass);
                realm.copyToRealm(nuevoAforador);
                realm.commitTransaction();
                Toast.makeText(getContext(),"Nuevo Usuario Creado",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getContext(),"Ya existe un usuario con esa identificaciòn",Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(getContext(),"Información incompleta, Intente de nuevo",Toast.LENGTH_LONG).show();
        }

        realm.close();
    }

}
