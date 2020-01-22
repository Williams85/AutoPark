package com.upc.autoparqueo.fragmentos;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upc.autoparqueo.R;
import com.upc.autoparqueo.adapters.ReservaAdapter;
import com.upc.autoparqueo.modelos.Estacionamiento;
import com.upc.autoparqueo.modelos.Reserva;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservaFragment extends Fragment {
    private RecyclerView rvDatos;
    private ReservaAdapter adapter;

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
        adapter = new ReservaAdapter(getContext());
        rvDatos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDatos.setAdapter(adapter);

        ArrayList<Reserva> reservas = new ArrayList<>();
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
        reservas.add(reserva3);

        adapter.agregar(reservas);
    }
}
