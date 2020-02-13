package com.upc.autoparqueo.actividades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.upc.autoparqueo.R;
import com.upc.autoparqueo.service.ApiClient;
import com.upc.autoparqueo.service.ResponseGeneric;
import com.upc.autoparqueo.service.request.RequestRegistrarUsuario;

import java.io.ByteArrayOutputStream;

import javax.net.ssl.ManagerFactoryParameters;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarUsuarioActivity extends AppCompatActivity {
    private EditText etUsuario, etCorreo, etContrasena, etRepetirContrasena;
    private Spinner spTipo;
    private Button btnTomarFoto, btnGuardar;
    private ImageView ivImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etUsuario = findViewById(R.id.etUsuario);
        etCorreo = findViewById(R.id.etEmail);
        etContrasena = findViewById(R.id.etContrasena);
        etRepetirContrasena = findViewById(R.id.etRepetirContrasena);
        spTipo = findViewById(R.id.spTipo);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);
        ivImagen = findViewById(R.id.ivImagen);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });
        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callCamara();
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

    private int ID_REQUEST_FOTO = 1;

    private int REQUEST_PERMISION_CAMARE = 1;

    public void callCamara(){
        //Validar permisos
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},
                    REQUEST_PERMISION_CAMARE);
        } else {
            openCamera();
        }
    }

    public void openCamera(){
        Intent takePictureIntent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, ID_REQUEST_FOTO);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISION_CAMARE)
        {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }


    private byte[] byteArray = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ID_REQUEST_FOTO && resultCode == RESULT_OK){
             Bitmap image = (Bitmap) data.getExtras().get("data");
            ivImagen.setImageBitmap(image);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();

          //      image.recycle();



        }
    }


    public void registrarUsuario(){

        if(etUsuario.getText().toString().isEmpty()
                || etContrasena.getText().toString().isEmpty()){
            Toast.makeText(this,"Ingresar su usuario y contrasena.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!etContrasena.getText().toString().equals(etRepetirContrasena.getText().toString())){
            Toast.makeText(this,"Las contrasenas no coinciden.",Toast.LENGTH_SHORT).show();
            return;
        }


        showProgress("Registrando usuario...");




        RequestRegistrarUsuario request = new RequestRegistrarUsuario();
        request.setNombre(etUsuario.getText().toString());
        request.setPass(etContrasena.getText().toString());
        request.setEmail(etCorreo.getText().toString());
        request.setFoto(byteArray);




        ApiClient.getInstance().registrarUsuario(request, new Callback<ResponseGeneric<Boolean>>() {
            @Override
            public void onResponse(Call<ResponseGeneric<Boolean>> call, Response<ResponseGeneric<Boolean>> response) {
                hideProgress();
                if(response.isSuccessful()){
                    ResponseGeneric<Boolean> data = response.body();
                    if(data.getValor()){

                        finish();
                    } else {
                        Toast.makeText(RegistrarUsuarioActivity.this,data.getMensaje(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseGeneric<Boolean>> call, Throwable t) {
                hideProgress();
                Toast.makeText(RegistrarUsuarioActivity.this,"Ocurrio un error al registrar el usuario.",Toast.LENGTH_SHORT).show();
            }
        });


    }
    private ProgressDialog progress;
    public void showProgress(String msg){
        progress = new ProgressDialog(this);
        progress.setMessage(msg);
        progress.show();
    }

    public void hideProgress(){
        if (progress != null){
            progress.dismiss();
            progress = null;
        }
    }
}
