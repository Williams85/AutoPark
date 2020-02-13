package com.upc.autoparqueo.fragmentos;


import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.upc.autoparqueo.actividades.MenuActivity;
import com.upc.autoparqueo.adapters.DistritoAdapter;
import com.upc.autoparqueo.modelos.Distrito;
import com.upc.autoparqueo.modelos.Estacionamiento;
import com.upc.autoparqueo.service.ApiClient;
import com.upc.autoparqueo.service.ResponseGeneric;
import com.upc.autoparqueo.service.ResponseListGeneric;
import com.upc.autoparqueo.service.request.RequestRegistrarEstacionamiento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrarEstacionamientoFragment extends Fragment implements OnMapReadyCallback {

    private EditText etNombre, etDireccion, etPrecioHora;
    private TextView tvHoraInicio, tvHoraFin, tvLatitud, tvLongitud;
    private CheckBox chkActivo;

    private GoogleMap mMap;
    private DistritoAdapter adapter;
    private AppCompatSpinner spinner;
    private List<Distrito> lstDistrito = new ArrayList<>();

    private Button btnGuardar;
    private ProgressDialog progress;
    private LatLng position;
    private boolean isActivo = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrar_estacionamiento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View viewParent, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(viewParent, savedInstanceState);
        etNombre = viewParent.findViewById(R.id.etNombre);
        spinner = viewParent.findViewById(R.id.spnDistrito);
        etDireccion = viewParent.findViewById(R.id.etDireccion);
        tvHoraInicio = viewParent.findViewById(R.id.tvHorarioInicio);
        tvHoraFin = viewParent.findViewById(R.id.tvHorarioFin);
        etPrecioHora = viewParent.findViewById(R.id.etPrecioHora);
        tvLatitud = viewParent.findViewById(R.id.tvLatitud);
        tvLongitud = viewParent.findViewById(R.id.tvLongitud);
        chkActivo = viewParent.findViewById(R.id.chkActivo);
        btnGuardar = viewParent.findViewById(R.id.btnGuardar);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        obtenerDistrito();


        etPrecioHora.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});
        tvHoraInicio.setText("08:00 AM");
        tvHoraInicio.setOnClickListener(view -> {


            obtenerHora(8, 0, (TextView) view);
        });
        tvHoraFin.setText("08:00 AM");
        tvHoraFin.setOnClickListener(view -> {

            obtenerHora(8, 0, (TextView) view);

        });

        chkActivo.setOnCheckedChangeListener((compoundButton, b) -> {
            isActivo = b;
        });

        btnGuardar.setOnClickListener(view1 -> registrarEstacionamiento());
    }


    public void obtenerDistrito() {
        showProgress("Obteniendo Distritos...");
        ApiClient.getInstance().obtenerDistrito(new Callback<ResponseListGeneric<Distrito>>() {
            @Override
            public void onResponse(Call<ResponseListGeneric<Distrito>> call, Response<ResponseListGeneric<Distrito>> response) {
                hideProgress();
                if (response.isSuccessful()) {

                    ResponseListGeneric<Distrito> data = response.body();

                    if (data.isEstado()) {

                        lstDistrito = data.getData();

                        adapter = new DistritoAdapter(getContext(), lstDistrito);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinner.setAdapter(adapter);


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

    private static final String CERO = "0";
    private Calendar c = Calendar.getInstance();


    private void obtenerHora(int hourOfDayInput, int minuteInput, TextView tvView) {
        TimePickerDialog recogerHora = new TimePickerDialog(getContext(), (view, hourOfDay, minute) -> {
            String horaFormateada = (hourOfDay < 10) ? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
            String minutoFormateado = (minute < 10) ? String.valueOf(CERO + minute) : String.valueOf(minute);
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

    public void registrarEstacionamiento() {

        RequestRegistrarEstacionamiento request = new RequestRegistrarEstacionamiento();
        request.setNomEstacionamiento(etNombre.getText().toString());
        request.setCodDistrito(((Distrito) spinner.getSelectedItem()).getId());
        request.setDirEstacionamiento(etDireccion.getText().toString());
        request.setHoraInicio(tvHoraInicio.getText().toString());
        request.setHoraFin(tvHoraFin.getText().toString());
        request.setPrecio(etPrecioHora.getText().toString());
        request.setLatitud(tvLatitud.getText().toString());
        request.setLongitud(tvLongitud.getText().toString());
        request.setActivo(chkActivo.isChecked());

        showProgress("Registrando Estacionamiento...");

        ApiClient.getInstance().registrarEstacionamiento(request, new Callback<ResponseGeneric<Boolean>>() {
            @Override
            public void onResponse(Call<ResponseGeneric<Boolean>> call, Response<ResponseGeneric<Boolean>> response) {

                hideProgress();
                if (response.isSuccessful()) {
                    ResponseGeneric<Boolean> data = response.body();
                    if (data.getEstado()) {
                        Toast.makeText(getContext(), data.getMensaje(), Toast.LENGTH_SHORT).show();
                        ((MenuActivity)getActivity()).redirectConsulta();
                    }
                } else {
                    Toast.makeText(getContext(), "Ocurrio en error en el registro.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseGeneric<Boolean>> call, Throwable t) {
                hideProgress();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        position = mMap.getCameraPosition().target;
        LatLng sydney = new LatLng(-10.729368, -76.493495);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 4.0f));
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                position = mMap.getCameraPosition().target;
                tvLatitud.setText("" + position.latitude);
                tvLongitud.setText("" + position.longitude);
            }
        });
        // Add a marker in Sydney and move the camera

    }

    public void showProgress(String msg) {
        progress = new ProgressDialog(getContext());
        progress.setMessage(msg);
        progress.show();
    }

    public void hideProgress() {
        if (progress != null) {
            progress.dismiss();
            progress = null;
        }
    }


}
