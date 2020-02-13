package com.upc.autoparqueo.service;

import com.upc.autoparqueo.modelos.Distrito;
import com.upc.autoparqueo.modelos.Estacionamiento;
import com.upc.autoparqueo.modelos.Reserva;
import com.upc.autoparqueo.modelos.Usuario;
import com.upc.autoparqueo.service.request.RequestEliminarEstacionamiento;
import com.upc.autoparqueo.service.request.RequestEliminarReserva;
import com.upc.autoparqueo.service.request.RequestEstacionamiento;
import com.upc.autoparqueo.service.request.RequestObtenerReserva;
import com.upc.autoparqueo.service.request.RequestRegistrarEstacionamiento;
import com.upc.autoparqueo.service.request.RequestRegistrarUsuario;
import com.upc.autoparqueo.service.request.RequestReservaEstacionamiento;
import com.upc.autoparqueo.service.request.RequestValidarUsuario;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    public static ApiClient instance = null;

    private static ApiService service;

    private ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://autoparkservicios.estilow3b.com/AutoParqueo.svc/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service =  retrofit.create(ApiService.class);


    }
    public static ApiClient getInstance() {

        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }


    public void registrarUsuario(RequestRegistrarUsuario request, Callback<ResponseGeneric<Boolean>> callback){
        Call<ResponseGeneric<Boolean>> response = service.registrarUsuario(request);
        response.enqueue(callback);
    }

    public void actualizarUsuario(RequestRegistrarUsuario request, Callback<ResponseGeneric<Boolean>> callback){
        Call<ResponseGeneric<Boolean>> response = service.actualizarUsuario(request);
        response.enqueue(callback);
    }

    public void validarUsuario(RequestValidarUsuario request, Callback<ResponseGeneric<Usuario>> callback){
        Call<ResponseGeneric<Usuario>> response = service.validarUsuario(request);
        response.enqueue(callback);
    }


    public void obtenerEstacionamiento(int id, Callback<ResponseGeneric<Estacionamiento>> callback){
        Call<ResponseGeneric<Estacionamiento>> response = service.obtenerEstacionamiento(id);
        response.enqueue(callback);
    }

    public void consultaEstacionamiento(RequestEstacionamiento requestEstacionamiento, Callback<ResponseListGeneric<Estacionamiento>> callback ){
        Call<ResponseListGeneric<Estacionamiento>> response = service.consultaEstacionamiento(requestEstacionamiento);
        response.enqueue(callback);
    }
    public void obtenerEstacionamientos(RequestEstacionamiento requestEstacionamiento, Callback<ResponseListGeneric<Estacionamiento>> callback ){
        Call<ResponseListGeneric<Estacionamiento>> response = service.filtraEstacionamiento(requestEstacionamiento);
        response.enqueue(callback);
    }

    public void obtenerEstacionamientosActivo(RequestEstacionamiento requestEstacionamiento, Callback<ResponseListGeneric<Estacionamiento>> callback ){
        Call<ResponseListGeneric<Estacionamiento>> response = service.filtraEstacionamientoActivo(requestEstacionamiento);
        response.enqueue(callback);
    }

    public void registrarEstacionamiento(RequestRegistrarEstacionamiento request, Callback<ResponseGeneric<Boolean>> callback ){
        Call<ResponseGeneric<Boolean>> response = service.registrarEstacionamiento(request);
        response.enqueue(callback);
    }

    public void actualizarEstacionamiento(RequestRegistrarEstacionamiento request, Callback<ResponseGeneric<Boolean>> callback ){
        Call<ResponseGeneric<Boolean>> response = service.actualizarEstacionamiento(request);
        response.enqueue(callback);
    }

    public void eliminarEstacionamiento(RequestEliminarEstacionamiento request, Callback<ResponseGeneric<Boolean>> callback ){
        Call<ResponseGeneric<Boolean>> response = service.eliminarEstacionamiento(request);
        response.enqueue(callback);
    }

    public void obtenerDistrito(Callback<ResponseListGeneric<Distrito>> callback ){
        Call<ResponseListGeneric<Distrito>> response = service.obtenerDistrito();
        response.enqueue(callback);
    }

    public void reservarEstacionamiento(RequestReservaEstacionamiento request, Callback<ResponseGeneric<Boolean>> callback ){
        Call<ResponseGeneric<Boolean>> response = service.reservarEstacionamiento(request);
        response.enqueue(callback);
    }

    public void actualizarReserva(RequestReservaEstacionamiento request, Callback<ResponseGeneric<Boolean>> callback ){
        Call<ResponseGeneric<Boolean>> response = service.actualizarReserva(request);
        response.enqueue(callback);
    }

    public void eliminarReserva(RequestEliminarReserva request, Callback<ResponseGeneric<Boolean>> callback ){
        Call<ResponseGeneric<Boolean>> response = service.eliminarReserva(request);
        response.enqueue(callback);
    }

    public void obtenerReserva(RequestObtenerReserva request,Callback<ResponseListGeneric<Reserva>> callback ){
        Call<ResponseListGeneric<Reserva>> response = service.obtenerReserva(request);
        response.enqueue(callback);
    }

}
