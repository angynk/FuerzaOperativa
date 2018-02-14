package com.transmilenio.fuerzaoperativa.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.transmilenio.fuerzaoperativa.R;
import com.transmilenio.fuerzaoperativa.models.util.ExtrasID;

public class SeleccionModoActivity extends AppCompatActivity {

    private Button troncalButton,alimentadorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_modo);
        bindUI();
    }

    private void bindUI() {
        troncalButton = (Button) findViewById(R.id.sel_troncal_button);
        alimentadorButton = (Button) findViewById(R.id.sel_alimentador_button);

        troncalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeleccionModoActivity.this,ListaSurveyActivity.class);
                intent.putExtra(ExtrasID.EXTRA_MODO,ExtrasID.TIPO_SERVICIO_TRONCAL);
                startActivity(intent);
            }
        });

        alimentadorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeleccionModoActivity.this,ListaSurveyActivity.class);
                intent.putExtra(ExtrasID.EXTRA_MODO,ExtrasID.TIPO_SERVICIO_ALIMENTADOR);
                startActivity(intent);
            }
        });
    }
}
