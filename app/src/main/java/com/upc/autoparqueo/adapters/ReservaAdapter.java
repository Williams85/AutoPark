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

import com.upc.autoparqueo.MapsActivity;
import com.upc.autoparqueo.R;
import com.upc.autoparqueo.actividades.ActualizarReservaActivity;
import com.upc.autoparqueo.actividades.MenuActivity;
import com.upc.autoparqueo.modelos.Reserva;
import com.upc.autoparqueo.service.ApiClient;
import com.upc.autoparqueo.service.ResponseGeneric;
import com.upc.autoparqueo.service.request.RequestEliminarReserva;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Reserva> lista;
    private Context context;

    public ReservaAdapter(Context context) {
        this.context = context;
        lista = new ArrayList<>();
    }

    public void agregar(List<Reserva> reservas) {
        lista.clear();
        lista.addAll(reservas);
        notifyDataSetChanged();
    }

    class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvDireccion, tvDistrito, tvHoraInicio, tvHoraFin, tvFecha;
        ImageView ivUbicacion;
        LinearLayout contenedor;

        public ReservaViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHoraInicio = itemView.findViewById(R.id.tvHoraInicio);
            tvHoraFin = itemView.findViewById(R.id.tvHoraFin);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvDistrito = itemView.findViewById(R.id.tvDistrito);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            ivUbicacion = itemView.findViewById(R.id.ivUbicacion);

            contenedor = itemView.findViewById(R.id.contenedor);
        }

        public void bind(Reserva reserva) {
            tvHoraInicio.setText(reserva.getHoraInicio());
            tvHoraFin.setText(reserva.getHoraFin());
            tvNombre.setText(reserva.getNombre());
            tvDireccion.setText(reserva.getDireccion());
            tvDistrito.setText(reserva.getDistrito());
            tvFecha.setText(reserva.getFecha());
            ivUbicacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("KEY_NOMBRE_ESTACIONAMIENTO",reserva.getNombre());
                    intent.putExtra("KEY_LATITUD",reserva.getLatitud());
                    intent.putExtra("KEY_LONGITUD",reserva.getLongitud());

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
                            Intent intent = new Intent(context, ActualizarReservaActivity.class);
                            intent.putExtra("KEY_RESERVA", reserva);
                            intent.putExtra("KEY_USUARIO", ((MenuActivity) context).usuario);

                            context.startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            RequestEliminarReserva request = new RequestEliminarReserva();
                            request.setCodReserva(reserva.getId());
                            ApiClient.getInstance().eliminarReserva(request, new Callback<ResponseGeneric<Boolean>>() {
                                @Override
                                public void onResponse(Call<ResponseGeneric<Boolean>> call, Response<ResponseGeneric<Boolean>> response) {

                                    if(response.isSuccessful()){

                                        ResponseGeneric<Boolean> data = response.body();

                                        if(data.getValor()){
                                            lista.remove(reserva);
                                            notifyItemRemoved(getAdapterPosition());
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseGeneric<Boolean>> call, Throwable t) {

                                }
                            });
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
