package com.upc.autoparqueo.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.upc.autoparqueo.R;

public class ActualizarUsuarioActivity extends AppCompatActivity {
    private EditText etUsuario, etCorreo, etContrasena, etRepetirContrasena;
    private Spinner spTipo;
    private Button btnTomarFoto, btnActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etUsuario = findViewById(R.id.etUsuario);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        etRepetirContrasena = findViewById(R.id.etRepetirContrasena);
        spTipo = findViewById(R.id.spTipo);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
