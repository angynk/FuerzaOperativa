package com.transmilenio.fuerzaoperativa.activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.transmilenio.fuerzaoperativa.R;
import com.transmilenio.fuerzaoperativa.http.API;
import com.transmilenio.fuerzaoperativa.http.SurveyService;
import com.transmilenio.fuerzaoperativa.models.db.Aforador;
import com.transmilenio.fuerzaoperativa.models.util.ExtrasID;
import com.transmilenio.fuerzaoperativa.models.util.Mensajes;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    private EditText editTextUsuario,editTextContrasena;
    private Switch switchRecordar;
    private Button buttonIngresar,buttonInvitado;
    private Realm realm;
    private ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        realm = Realm.getDefaultInstance();
        bindUI();
        createEvents();
        setcredentialsIfExist();
    }

    private void setcredentialsIfExist() {
        Boolean isLogged = prefs.getBoolean(ExtrasID.EXTRA_LOGGED,false);
        if(isLogged){
            goToMain(prefs.getString(ExtrasID.EXTRA_TIPO_USUARIO,ExtrasID.TIPO_USUARIO_INVITADO));
        }
    }

    private void createEvents() {
        buttonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user =editTextUsuario.getText().toString();
                String pass = editTextContrasena.getText().toString();

                if(switchRecordar.isChecked()){
                    loginAdministrador(user,pass);
                }else{
                    if(inicioSession(user,pass)){
                        goToMain(ExtrasID.TIPO_USUARIO_AFORADOR);
                        saveOnPreferences(user,ExtrasID.TIPO_USUARIO_AFORADOR);
                    }
                }

            }
        });

        buttonInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain(ExtrasID.TIPO_USUARIO_INVITADO);
                saveOnPreferences(ExtrasID.TIPO_USUARIO_INVITADO,ExtrasID.TIPO_USUARIO_INVITADO);
            }
        });
    }

    private void loginAdministrador(String user, String pass) {
        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)){
            Aforador aforador = new Aforador(user,pass);
            progressDoalog = new ProgressDialog(LoginActivity.this);
            progressDoalog.setMessage(Mensajes.MSG_AUTENTICANDO);
            progressDoalog.setTitle(Mensajes.MSG_USUARIO);
            progressDoalog.setCanceledOnTouchOutside(false);
            progressDoalog.show();
            validarUsuario(aforador);
        }

    }

    private void validarUsuario(final Aforador aforador) {
        SurveyService surveyService = API.getApi().create(SurveyService.class);
        Call<Boolean> call = surveyService.login(aforador);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean  resulta = response.body();
                progressDoalog.dismiss();
                if(resulta){
                    goToMain(ExtrasID.TIPO_USUARIO_ADMIN);
                    saveOnPreferences(aforador.getUsuario(),ExtrasID.TIPO_USUARIO_ADMIN);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                progressDoalog.dismiss();
            }
        });
    }

    private void bindUI(){
        editTextUsuario = (EditText) findViewById(R.id.lg_user_editText);
        editTextContrasena = (EditText) findViewById(R.id.lg_pass_editText);
        switchRecordar = (Switch) findViewById(R.id.lg_recordar_switch);
        buttonIngresar = (Button) findViewById(R.id.lg_iniciar_button);
        buttonInvitado = (Button) findViewById(R.id.lg_invitado_button);
    }

    private boolean inicioSession(String user,String password){
        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
            Aforador aforador = realm.where(Aforador.class).equalTo("usuario", user).findFirst();
            if(aforador !=null){
                if(aforador.getContrasena().equals(password)){
                    return true;
                }else{
                    Toast.makeText(this,"Información invalida, Intente de nuevo",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this,"El usuario no existe, contactese con el administrador",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this,"Información incompleta, Intente de nuevo",Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void goToMain(String tipoUsuario){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(ExtrasID.EXTRA_TIPO_USUARIO,tipoUsuario);
        startActivity(intent);
    }



    private void saveOnPreferences(String user,String tipoUsuario){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ExtrasID.EXTRA_USER,user);
        editor.putString(ExtrasID.EXTRA_TIPO_USUARIO,tipoUsuario);
        editor.putBoolean(ExtrasID.EXTRA_LOGGED,true);
        editor.apply();
    }
}
