package com.upc.autoparqueo.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.upc.autoparqueo.R;
import com.upc.autoparqueo.actividades.ActualizarReservaActivity;
import com.upc.autoparqueo.modelos.Reserva;

import java.util.ArrayList;

public class ReservaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Reserva> lista;
    private Context context;

    public ReservaAdapter(Context context) {
        this.context = context;
        lista = new ArrayList<>();
    }

    public void agregar(ArrayList<Reserva> reservas) {
        lista.clear();
        lista.addAll(reservas);
        notifyDataSetChanged();
    }

    class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView tvHorario, tvNombre, tvDireccion;
        ImageView ivUbicacion;
        LinearLayout contenedor;

        public ReservaViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHorario = itemView.findViewById(R.id.tvHorario);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            ivUbicacion = itemView.findViewById(R.id.ivUbicacion);
            contenedor = itemView.findViewById(R.id.contenedor);
        }

        public void bind(Reserva reserva) {
            tvHorario.setText(reserva.getHoraInicio() + " a " + reserva.getHoraFin());
            tvNombre.setText(reserva.getNombre());
            tvDireccion.setText(reserva.getDireccion());
            ivUbicacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
                            Intent intent = new Intent(context, ActualizarReservaActivity.class);
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
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reserva, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReservaViewHolder viewHolder = (ReservaViewHolder) holder;
        viewHolder.bind(lista.get(position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
