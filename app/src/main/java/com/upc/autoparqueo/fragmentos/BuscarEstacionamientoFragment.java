package com.upc.autoparqueo.fragmentos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.upc.autoparqueo.R;
import com.upc.autoparqueo.adapters.EstacionamientoAdapter;
import com.upc.autoparqueo.modelos.Estacionamiento;

import java.util.ArrayList;

public class BuscarEstacionamientoFragment extends Fragment {
    private EditText etBuscar;
    private ImageView ivBuscar;
    private ListView lvEstacionamientos;
    private EstacionamientoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buscar_estacionamiento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etBuscar = view.findViewById(R.id.etBuscar);
        ivBuscar = view.findViewById(R.id.ivBuscar);
        lvEstacionamientos = view.findViewById(R.id.lvEstacionamientos);

        adapter = new EstacionamientoAdapter(getContext());
        lvEstacionamientos.setAdapter(adapter);

        ArrayList<Estacionamiento> estacionamientos = new ArrayList<>();
        Estacionamiento estacionamiento1 = new Estacionamiento();
        estacionamiento1.setId(1);
        estacionamiento1.setHoraInicio("8:00 am");
        estacionamiento1.setHoraFin("8:00 pm");
        estacionamiento1.setNombre("Los Portales - Navarrete");
        estacionamiento1.setDireccion("Av. Los Geraneos 125");
        //estacionamiento1.setLatitud();
        //estacionamiento1.setLongitud();
        estacionamientos.add(estacionamiento1);

        Estacionamiento estacionamiento2 = new Estacionamiento();
        estacionamiento2.setId(1);
        estacionamiento2.setHoraInicio("8:00 am");
        estacionamiento2.setHoraFin("8:00 pm");
        estacionamiento2.setNombre("Los Portales - Caminos del Inca");
        estacionamiento2.setDireccion("Av. Caminos del inca 751");
        //estacionamiento2.setLatitud();
        //estacionamiento2.setLongitud();
        estacionamientos.add(estacionamiento2);

        Estacionamiento estacionamiento3 = new Estacionamiento();
        estacionamiento3.setId(1);
        estacionamiento3.setHoraInicio("8:00 am");
        estacionamiento3.setHoraFin("10:00 pm");
        estacionamiento3.setNombre("Los Portales - Risso");
        estacionamiento3.setDireccion("Av. Arequipa 2575");
        //estacionamiento3.setLatitud();
        //estacionamiento3.setLongitud();
        estacionamientos.add(estacionamiento3);

        adapter.agregar(estacionamientos);
    }
}
