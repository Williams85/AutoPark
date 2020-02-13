package com.upc.autoparqueo.actividades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.upc.autoparqueo.R;
import com.upc.autoparqueo.Util;
import com.upc.autoparqueo.modelos.Usuario;
import com.upc.autoparqueo.service.ApiClient;
import com.upc.autoparqueo.service.ResponseGeneric;
import com.upc.autoparqueo.service.request.RequestRegistrarUsuario;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.upc.autoparqueo.Util.formatDate;

public class ActualizarUsuarioActivity extends AppCompatActivity {
    private ImageView ivImagen;
    private EditText etUsuario, etCorreo, etContrasena, etRepetirContrasena, etNombre, etApellido;
    private TextView tvFechaNacimiento;
    private Spinner spSexo;
    private Button btnTomarFoto, btnActualizar;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivImagen = findViewById(R.id.ivImagen);
        etUsuario = findViewById(R.id.etUsuario);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        etRepetirContrasena = findViewById(R.id.etRepetirContrasena);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);
        spSexo = findViewById(R.id.spTipo);
        tvFechaNacimiento = findViewById(R.id.tvFechaNacimiento);

        usuario = getIntent().getExtras().getParcelable("KEY_USUARIO");

        tvFechaNacimiento.setOnClickListener(view -> obtenerFecha(tvFechaNacimiento));
        btnActualizar.setOnClickListener(view -> actualizarUsuario());
        btnTomarFoto.setOnClickListener(view -> callCamara());
        ivImagen.setOnClickListener(view -> obtenerFecha((TextView) view));
        setValues();
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

    public void setValues() {
        etUsuario.setText(usuario.getCodigo());
        etCorreo.setText(usuario.getEmail());
        etNombre.setText(usuario.getNombre() != null ? usuario.getNombre() : "");
        etApellido.setText(usuario.getApellidos() != null ? usuario.getApellidos() : "");


        c.setTime(usuario.getFechaNacimiento() != null ? Util.formatStringtoDate(usuario.getFechaNacimiento()) : new Date());

        tvFechaNacimiento.setText(Util.formatDate(c.getTime()));
        spSexo.setSelection(usuario.getSexo() == 1 ? 0 : 1);
        if (usuario.getFoto() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(usuario.getFoto(), 0, usuario.getFoto().length);
            ivImagen.setImageBitmap(bitmap);
            byteArray = usuario.getFoto();
        }


    }

    public void actualizarUsuario() {

        if (etUsuario.getText().toString().isEmpty()
                || etContrasena.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingresar su usuario y contrasena.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!etContrasena.getText().toString().equals(etRepetirContrasena.getText().toString())) {
            Toast.makeText(this, "Las contrasenas no coinciden.", Toast.LENGTH_SHORT).show();
            return;
        }


        showProgress("Actualizando usuario...");

        RequestRegistrarUsuario request = new RequestRegistrarUsuario();
        request.setCodUsuario(usuario.getCodUsuario());
        request.setNombre(etUsuario.getText().toString());
        request.setPass(etContrasena.getText().toString());
        request.setEmail(etCorreo.getText().toString());
        request.setNombres(etNombre.getText().toString());
        request.setApellido(etApellido.getText().toString());
        request.setFechaNacimiento(tvFechaNacimiento.getText().toString());
        request.setSexo(spSexo.getSelectedItem().toString().equals("Masculino") ? 1 : 0);
        request.setFoto(byteArray);
        request.setCodUsuario(usuario.getCodUsuario());

        ApiClient.getInstance().actualizarUsuario(request, new Callback<ResponseGeneric<Boolean>>() {
            @Override
            public void onResponse(Call<ResponseGeneric<Boolean>> call, Response<ResponseGeneric<Boolean>> response) {
                hideProgress();
                if (response.isSuccessful()) {

                    ResponseGeneric<Boolean> data = response.body();
                    if (data.getValor()) {
                        Toast.makeText(ActualizarUsuarioActivity.this, data.getMensaje(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ActualizarUsuarioActivity.this, data.getMensaje(), Toast.LENGTH_SHORT).show();
                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponseGeneric<Boolean>> call, Throwable t) {
                hideProgress();
            }
        });
    }


    private int ID_REQUEST_FOTO = 1;

    private int REQUEST_PERMISION_CAMARE = 1;

    public void callCamara() {
        //Validar permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_PERMISION_CAMARE);
        } else {
            openCamera();
        }
    }

    public void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, ID_REQUEST_FOTO);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISION_CAMARE) {
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

        if (requestCode == ID_REQUEST_FOTO && resultCode == RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ivImagen.setImageBitmap(image);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }
    }

    private ProgressDialog progress;

    public void showProgress(String msg) {
        progress = new ProgressDialog(this);
        progress.setMessage(msg);
        progress.show();
    }

    public void hideProgress() {
        if (progress != null) {
            progress.dismiss();
            progress = null;
        }
    }

    public final Calendar c = Calendar.getInstance();

    private void obtenerFecha(TextView tvView) {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                c.set(year, month, day);

                tvView.setText(formatDate(c.getTime()));

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        recogerFecha.show();
    }

}
