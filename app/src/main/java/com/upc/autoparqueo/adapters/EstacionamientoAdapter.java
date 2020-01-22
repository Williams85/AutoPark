package com.upc.autoparqueo.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.upc.autoparqueo.R;
import com.upc.autoparqueo.actividades.ActualizarEstacionamientoActivity;
import com.upc.autoparqueo.actividades.ActualizarReservaActivity;
import com.upc.autoparqueo.actividades.ReservarEstacionamientoActivity;
import com.upc.autoparqueo.modelos.Estacionamiento;

import java.util.ArrayList;

public class EstacionamientoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Estacionamiento> lista;

    public EstacionamientoAdapter(Context context) {
        this.context = context;
        lista = new ArrayList<>();
    }

    public void agregar(ArrayList<Estacionamiento> data) {
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_estacionamiento, viewGroup, false);
        TextView tvHorario, tvNombre, tvDireccion;
        ImageView ivUbicacion;
        Button btnReservar;
        LinearLayout contenedor;

        tvHorario = view.findViewById(R.id.tvHorario);
        tvNombre = view.findViewById(R.id.tvNombre);
        tvDireccion = view.findViewById(R.id.tvDireccion);
        ivUbicacion = view.findViewById(R.id.ivUbicacion);
        btnReservar = view.findViewById(R.id.btnReservar);
        contenedor = view.findViewById(R.id.contenedor);

        Estacionamiento estacionamiento = (Estacionamiento) getItem(i);
        tvHorario.setText(estacionamiento.getHoraInicio() + " a " + estacionamiento.getHoraFin());
        tvNombre.setText(estacionamiento.getNombre());
        tvDireccion.setText(estacionamiento.getDireccion());
        ivUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReservarEstacionamientoActivity.class);
                context.startActivity(intent);
            }
        });

        contenedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Acciones a realizar");
                builder.setMessage("Seleccione una opción a realizar");
                builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(context, ActualizarEstacionamientoActivity.class);
                        context.startActivity(intent);
                    }
                });
                builder.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

        return view;
    }
}
