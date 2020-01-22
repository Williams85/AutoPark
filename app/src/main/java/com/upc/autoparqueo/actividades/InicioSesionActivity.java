package com.upc.autoparqueo.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.upc.autoparqueo.R;

public class InicioSesionActivity extends AppCompatActivity {
    private EditText etUsuario, etContrasena;
    private Button btnIngresar;
    private TextView tvRegistrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        btnIngresar = findViewById(R.id.btnIngresar);
        tvRegistrate = findViewById(R.id.tvRegistrate);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioSesionActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        tvRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioSesionActivity.this, RegistrarUsuarioActivity.class);
                startActivity(intent);
            }
        });
    }
}
