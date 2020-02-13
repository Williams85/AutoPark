package com.upc.autoparqueo.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.upc.autoparqueo.DecimalDigitsInputFilter;
import com.upc.autoparqueo.R;
import com.upc.autoparqueo.Util;
import com.upc.autoparqueo.adapters.DistritoAdapter;
import com.upc.autoparqueo.modelos.Distrito;
import com.upc.autoparqueo.modelos.Estacionamiento;
import com.upc.autoparqueo.service.ApiClient;
import com.upc.autoparqueo.service.ResponseGeneric;
import com.upc.autoparqueo.service.ResponseListGeneric;
import com.upc.autoparqueo.service.request.RequestRegistrarEstacionamiento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActualizarEstacionamientoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private EditText etNombre, etDireccion, etPrecioHora ;
    private TextView tvHoraInicio, tvHoraFin, tvLatitud, tvLongitud;
    private CheckBox cbActivo;

    private GoogleMap mMap;
    private DistritoAdapter adapter;
    private AppCompatSpinner spinner;
    private List<Distrito> lstDistrito = new ArrayList<>();

    private ProgressDialog progress;
    private Button btnActualizar;

    private Estacionamiento estacionamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_estacionamiento);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        estacionamiento = getIntent().getExtras().getParcelable("KEY_ACTUAlIZAR_ESTACIONAMIENTO");



        etNombre = findViewById(R.id.etNombre);
        spinner = findViewById(R.id.spnDistrito);
        etDireccion = findViewById(R.id.etDireccion);
        etPrecioHora = findViewById(R.id.etPrecioHora);
        tvLatitud = findViewById(R.id.tvLatitud);
        tvLongitud = findViewById(R.id.tvLongitud);
        tvHoraInicio = findViewById(R.id.tvHorarioInicio);
        tvHoraFin = findViewById(R.id.tvHorarioFin);

        cbActivo=findViewById(R.id.cbActivo);
        btnActualizar = findViewById(R.id.btnActualizar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        obtenerDistrito();
        setValues();


        etPrecioHora.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});
        tvHoraInicio.setOnClickListener(view -> {

            Date horaInicio = Util.formatHourString(tvHoraInicio.getText().toString());
            obtenerHora(horaInicio.getHours(),horaInicio.getMinutes(),(TextView) view);
        });

        tvHoraFin.setOnClickListener(view -> {
            Date horaFin = Util.formatHourString(tvHoraFin.getText().toString());
            obtenerHora(horaFin.getHours(),horaFin.getMinutes(),(TextView) view);

        });


        btnActualizar.setOnClickListener(view1 -> actualizarEstacionamiento());
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

    public void obtenerDistrito(){
        showProgress("Obteniendo Distritos...");
        ApiClient.getInstance().obtenerDistrito(new Callback<ResponseListGeneric<Distrito>>() {
            @Override
            public void onResponse(Call<ResponseListGeneric<Distrito>> call, Response<ResponseListGeneric<Distrito>> response) {
              hideProgress();
                if(response.isSuccessful()) {

                    ResponseListGeneric<Distrito> data = response.body();

                    if (data.isEstado()) {

                        lstDistrito = data.getData();

                        adapter = new DistritoAdapter(ActualizarEstacionamientoActivity.this,lstDistrito);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinner.setAdapter(adapter);
                        setPositionAdapter();

                    } else {


                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseListGeneric<Distrito>> call, Throwable t) {
                hideProgress();
            }
        });
    }

    public void actualizarEstacionamiento(){

        RequestRegistrarEstacionamiento request = new RequestRegistrarEstacionamiento();
        request.setCodEstacionamiento(estacionamiento.getId()+"");
        request.setNomEstacionamiento(etNombre.getText().toString());
        request.setCodDistrito(((Distrito) spinner.getSelectedItem()).getId());
        request.setDirEstacionamiento(etDireccion.getText().toString());
        request.setHoraInicio(tvHoraInicio.getText().toString());
        request.setHoraFin(tvHoraFin.getText().toString());
        request.setPrecio(etPrecioHora.getText().toString());
        request.setLatitud(tvLatitud.getText().toString());
        request.setLongitud(tvLongitud.getText().toString());
        request.setActivo(cbActivo.isChecked());
        showProgress("Actualizando Estacionamiento...");


        ApiClient.getInstance().actualizarEstacionamiento(request, new Callback<ResponseGeneric<Boolean>>() {
            @Override
            public void onResponse(Call<ResponseGeneric<Boolean>> call, Response<ResponseGeneric<Boolean>> response) {

                hideProgress();
                if(response.isSuccessful()) {
                    ResponseGeneric<Boolean> data = response.body();
                    if (data.getEstado()) {
                        Toast.makeText(ActualizarEstacionamientoActivity.this,data.getMensaje(),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else {
                    Toast.makeText(ActualizarEstacionamientoActivity.this,"Ocurrio en error en el registro.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseGeneric<Boolean>> call, Throwable t) {
                hideProgress();
            }
        });
    }

    public void setPositionAdapter(){
        if(lstDistrito.size() > 0){
            int i = 0;
            for (Distrito item: lstDistrito) {
                if(item.getId() == estacionamiento.getCodDistrito()){
                    spinner.setSelection(i);
                    return;
                } else {
                    i++;
                }
            }
        }


    }
    public void setValues(){
        etNombre.setText(estacionamiento.getNombre());
        etDireccion.setText(estacionamiento.getDireccion());
        tvHoraInicio.setText(estacionamiento.getHoraInicio());
        tvHoraFin.setText(estacionamiento.getHoraFin());
        etPrecioHora.setText(""+estacionamiento.getPrecio());
        tvLatitud.setText(""+estacionamiento.getLatitud());
        tvLongitud.setText(""+estacionamiento.getLongitud());
        cbActivo.setChecked(estacionamiento.isActivo());
    }


    private LatLng position;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(estacionamiento.getLatitud(), estacionamiento.getLongitud());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Estacionamiento: "+estacionamiento.getNombre()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17.0f));

        position = mMap.getCameraPosition().target;
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                position = mMap.getCameraPosition().target;
                tvLatitud.setText(""+position.latitude);
                tvLongitud.setText(""+position.longitude);
            }
        });

    }

    private void obtenerHora(int hourOfDayInput,int minuteInput, TextView tvView){
        TimePickerDialog recogerHora = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            String horaFormateada = (hourOfDay < 10) ? String.valueOf("0" + hourOfDay) : String.valueOf(hourOfDay);
            String minutoFormateado = (minute < 10) ? String.valueOf("0" + minute) : String.valueOf(minute);
            String AM_PM;
            if (hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }
            //String hora = horaFormateada + ":" + minutoFormateado + " " + AM_PM;
            String strHora = horaFormateada + ":" + minutoFormateado + " " + AM_PM;
            String strHoraFormat = Util.formatHour(strHora);

            tvView.setText(strHoraFormat);

        }, hourOfDayInput, minuteInput, false);

        recogerHora.setCancelable(false);
        recogerHora.show();
    }

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
