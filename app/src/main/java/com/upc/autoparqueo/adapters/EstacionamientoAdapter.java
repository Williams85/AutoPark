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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.upc.autoparqueo.MapsActivity;
import com.upc.autoparqueo.R;
import com.upc.autoparqueo.actividades.ActualizarEstacionamientoActivity;
import com.upc.autoparqueo.actividades.ActualizarReservaActivity;
import com.upc.autoparqueo.actividades.MenuActivity;
import com.upc.autoparqueo.actividades.ReservarEstacionamientoActivity;
import com.upc.autoparqueo.modelos.Estacionamiento;
import com.upc.autoparqueo.service.ApiClient;
import com.upc.autoparqueo.service.ResponseGeneric;
import com.upc.autoparqueo.service.request.RequestEliminarEstacionamiento;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstacionamientoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Estacionamiento> lista;
    private boolean isAdmin;

    public EstacionamientoAdapter(Context context, boolean isAdmin) {
        this.isAdmin = isAdmin;
        this.context = context;
        lista = new ArrayList<>();
    }

    public void agregar(List<Estacionamiento> data) {

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
        TextView tvHorario, tvNombre, tvDireccion, tvHorarioFin, tvDistrito;
        ImageView ivUbicacion;
        Button btnReservar;
        LinearLayout contenedor;

        tvHorario = view.findViewById(R.id.tvHorarioInicio);
        tvHorarioFin = view.findViewById(R.id.tvHorarioFin);
        tvNombre = view.findViewById(R.id.tvNombre);
        tvDireccion = view.findViewById(R.id.tvDireccion);
        tvDistrito = view.findViewById(R.id.tvDistrito);
        ivUbicacion = view.findViewById(R.id.ivUbicacion);
        btnReservar = view.findViewById(R.id.btnReservar);
        contenedor = view.findViewById(R.id.contenedor);

        Estacionamiento estacionamiento = (Estacionamiento) getItem(i);
        tvHorario.setText(estacionamiento.getHoraInicio());
        tvHorarioFin.setText(estacionamiento.getHoraFin());
        tvNombre.setText(estacionamiento.getNombre());
        tvDireccion.setText(estacionamiento.getDireccion());
        tvDistrito.setText(estacionamiento.getNomDistrito());
        ivUbicacion.setOnClickListener(view12 -> {
            Intent intent = new Intent(context, MapsActivity.class);
            intent.putExtra("KEY_NOMBRE_ESTACIONAMIENTO",estacionamiento.getNombre());
            intent.putExtra("KEY_LATITUD",estacionamiento.getLatitud());
            intent.putExtra("KEY_LONGITUD",estacionamiento.getLongitud());

            context.startActivity(intent);
        });

        if(isAdmin) {
            btnReservar.setVisibility(View.GONE);
            contenedor.setOnClickListener(view13 -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Acciones a realizar");
                builder.setMessage("Seleccione una opción a realizar");
                builder.setPositiveButton("Actualizar", (dialogInterface, i1) -> {
                    Intent intent = new Intent(context, ActualizarEstacionamientoActivity.class);
                    intent.putExtra("KEY_ACTUAlIZAR_ESTACIONAMIENTO",estacionamiento);
                    context.startActivity(intent);
                });
                builder.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i1) {
                        eliminarEstacionamiento(estacionamiento);
                    }
                });
                builder.show();
            });
        } else {

            btnReservar.setOnClickListener(view1 -> {
                Intent intent = new Intent(context, ReservarEstacionamientoActivity.class);
                intent.putExtra("KEY_ESTACIONAMIENTO",estacionamiento);
                intent.putExtra("KEY_USUARIO", ((MenuActivity) context).usuario);
                context.startActivity(intent);
            });

        }



        return view;
    }

    public void eliminarEstacionamiento(Estacionamiento estacionamiento){
        RequestEliminarEstacionamiento request = new RequestEliminarEstacionamiento();
        request.setCodEstacionamiento(estacionamiento.getId());
        ApiClient.getInstance().eliminarEstacionamiento(request, new Callback<ResponseGeneric<Boolean>>() {
            @Override
            public void onResponse(Call<ResponseGeneric<Boolean>> call, Response<ResponseGeneric<Boolean>> response) {
                if(response.isSuccessful()) {
                    ResponseGeneric<Boolean> data = response.body();

                    if(data.getValor()){
                        Toast.makeText(context, data.getMensaje(),Toast.LENGTH_SHORT).show();
                        lista.remove(estacionamiento);
                        notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseGeneric<Boolean>> call, Throwable t) {
                Toast.makeText(context, "Ocurrió un error al eliminar.",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
