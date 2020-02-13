package com.upc.autoparqueo.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.upc.autoparqueo.R;
import com.upc.autoparqueo.modelos.Usuario;
import com.upc.autoparqueo.service.ApiClient;
import com.upc.autoparqueo.service.ResponseGeneric;
import com.upc.autoparqueo.service.request.RequestValidarUsuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioSesionActivity extends AppCompatActivity {
    private EditText etUsuario, etContrasena;
    private Button btnIngresar;
    private TextView tvRegistrate;
    private ProgressDialog progressDialog;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        setTheme(android.R.style.Theme_Material_NoActionBar);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        btnIngresar = findViewById(R.id.btnIngresar);
        tvRegistrate = findViewById(R.id.tvRegistrate);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validarUsuario();
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

    public void validarUsuario(){

        String usuario = etUsuario.getText().toString();
        String pass = etContrasena.getText().toString();

        if(usuario.isEmpty() || pass.isEmpty()){

            Toast.makeText(this,"Ingrese su usuario y contraseña.",Toast.LENGTH_SHORT).show();
            return;
        }

        RequestValidarUsuario request = new RequestValidarUsuario();

        request.setCodUsuario(usuario);
        request.setPassUsuario(pass);

        showProgress();

        ApiClient.getInstance().validarUsuario(request, new Callback<ResponseGeneric<Usuario>>() {
            @Override
            public void onResponse(Call<ResponseGeneric<Usuario>> call, Response<ResponseGeneric<Usuario>> response) {
                hideProgress();
                if(response.isSuccessful()){
                    ResponseGeneric<Usuario> data = response.body();
                    if (data.getValor().getCodigo() !=null){
                        Intent intent = new Intent(InicioSesionActivity.this, MenuActivity.class);
                        intent.putExtra("KEY_USUARIO", data.getValor());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(InicioSesionActivity.this,"El usuario o contraseña son incorrectos.",Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseGeneric<Usuario>> call, Throwable t) {
                hideProgress();
            }
        });
    }


    public void showProgress(){
        progress = new ProgressDialog(this);
        progress.setMessage("Validando usuario...");
        progress.show();
    }

    public void hideProgress(){
        if (progress != null){
            progress.dismiss();
            progress = null;
        }
    }
}
