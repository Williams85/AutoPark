package com.upc.autoparqueo.fragmentos;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.upc.autoparqueo.R;
import com.upc.autoparqueo.Util;
import com.upc.autoparqueo.actividades.MenuActivity;
import com.upc.autoparqueo.adapters.ReservaAdapter;
import com.upc.autoparqueo.modelos.Estacionamiento;
import com.upc.autoparqueo.modelos.Reserva;
import com.upc.autoparqueo.modelos.Usuario;
import com.upc.autoparqueo.service.ApiClient;
import com.upc.autoparqueo.service.ResponseGeneric;
import com.upc.autoparqueo.service.ResponseListGeneric;
import com.upc.autoparqueo.service.request.RequestObtenerReserva;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservaFragment extends Fragment {
    private RecyclerView rvDatos;
    private ReservaAdapter adapter;
    private ProgressDialog progress;
    private SearchView srvBuscar;
    private TextView tvFecha;

    private Usuario usuario;
    public ReservaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reserva, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvDatos = view.findViewById(R.id.rvDatos);
        tvFecha = view.findViewById(R.id.tvFecha);
        srvBuscar = view.findViewById(R.id.srvBuscar);
        adapter = new ReservaAdapter(getContext());
        rvDatos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDatos.setAdapter(adapter);

        usuario =((MenuActivity) getActivity()).usuario;
        /*ArrayList<Reserva> reservas = new ArrayList<>();
        Reserva reserva1 = new Reserva();
        reserva1.setId(1);
        reserva1.setHoraInicio("8:00 am");
        reserva1.setHoraFin("8:00 pm");
        reserva1.setNombre("Los Portales - Navarrete");
        reserva1.setDireccion("Av. Los Geraneos 125");
        //reserva1.setLatitud();
        //reserva1.setLongitud();
        reservas.add(reserva1);

        Reserva reserva2 = new Reserva();
        reserva2.setId(1);
        reserva2.setHoraInicio("8:00 am");
        reserva2.setHoraFin("8:00 pm");
        reserva2.setNombre("Los Portales - Caminos del Inca");
        reserva2.setDireccion("Av. Caminos del inca 751");
        //reserva2.setLatitud();
        //reserva2.setLongitud();
        reservas.add(reserva2);

        Reserva reserva3 = new Reserva();
        reserva3.setId(1);
        reserva3.setHoraInicio("8:00 am");
        reserva3.setHoraFin("10:00 pm");
        reserva3.setNombre("Los Portales - Risso");
        reserva3.setDireccion("Av. Arequipa 2575");
        //reserva3.setLatitud();
        //reserva3.setLongitud();
        reservas.add(reserva3);*/


        tvFecha.setOnClickListener(textView -> {
            obtenerFecha((TextView)textView);
        });
        tvFecha.setText(Util.formatDate(c.getTime()));

        srvBuscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listarReserva();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    listarReserva();
                }

                return false;
            }
        });

        showProgress();


    }

    @Override
    public void onResume() {
        super.onResume();
        listarReserva();

    }

    public void listarReserva(){
        RequestObtenerReserva request = new RequestObtenerReserva();
        request.setCodUsuario(usuario.getCodUsuario());
        request.setNomEstacionamiento(srvBuscar.getQuery().toString());
        request.setFechaReserva(tvFecha.getText().toString());

        ApiClient.getInstance().obtenerReserva(request, new Callback<ResponseListGeneric<Reserva>>() {
            @Override
            public void onResponse(Call<ResponseListGeneric<Reserva>> call, Response<ResponseListGeneric<Reserva>> response) {
                hideProgress();
                if(response.isSuccessful()){
                    ResponseListGeneric<Reserva> data = response.body();

                    adapter.agregar(data.getData());
                }
            }

            @Override
            public void onFailure(Call<ResponseListGeneric<Reserva>> call, Throwable t) {
                hideProgress();
            }
        });
    }

    private Calendar c = Calendar.getInstance();
    private int anio = c.get(Calendar.YEAR);
    private int mes = c.get(Calendar.MONTH);
    private int dia = c.get(Calendar.DAY_OF_MONTH);

    private void obtenerFecha(TextView tvView){
        DatePickerDialog recogerFecha = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {


                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year =  datePicker.getYear();

                c.set(year, month, day);

                tvView.setText(Util.formatDate(c.getTime()));
                listarReserva();
            }
        },anio,mes,dia);

        recogerFecha.show();
    }


    public void showProgress(){
        progress = new ProgressDialog(getContext());
        progress.setMessage("Obteniendo reservas...");
        progress.show();
    }

    public void hideProgress(){
        if (progress != null){
            progress.dismiss();
            progress = null;
        }
    }
}
