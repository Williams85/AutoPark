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

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface ApiService {


    /*@GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
*/


    @POST("RegistraUsuario")
    @Headers("Content-Type: application/json")
    Call<ResponseGeneric<Boolean>> registrarUsuario(@Body RequestRegistrarUsuario request);

    @POST("ActualizaUsuario")
    @Headers("Content-Type: application/json")
    Call<ResponseGeneric<Boolean>> actualizarUsuario(@Body RequestRegistrarUsuario request);

    @POST("ValidaUsuario")
    @Headers("Content-Type: application/json")
    Call<ResponseGeneric<Usuario>> validarUsuario(@Body RequestValidarUsuario request);



    @GET("BuscaEstacionamientoxId/{id}")
    Call<ResponseGeneric<Estacionamiento>> obtenerEstacionamiento(@Path("id") int idEstacionamiento );

    @POST("ConsultaEstacionamiento")
    @Headers("Content-Type: application/json")
    Call<ResponseListGeneric<Estacionamiento>> consultaEstacionamiento(@Body RequestEstacionamiento requestEstacionamiento);

    @POST("FiltraEstacionamiento")
    @Headers("Content-Type: application/json")
    Call<ResponseListGeneric<Estacionamiento>> filtraEstacionamiento(@Body RequestEstacionamiento requestEstacionamiento);

    @POST("FiltraEstacionamiento")
    @Headers("Content-Type: application/json")
    Call<ResponseListGeneric<Estacionamiento>> filtraEstacionamientoActivo(@Body RequestEstacionamiento requestEstacionamiento);

    @POST("RegistraEstacionamiento")
    @Headers("Content-Type: application/json")
    Call<ResponseGeneric<Boolean>> registrarEstacionamiento(@Body RequestRegistrarEstacionamiento request);

    @POST("ActualizaEstacionamiento")
    @Headers("Content-Type: application/json")
    Call<ResponseGeneric<Boolean>> actualizarEstacionamiento(@Body RequestRegistrarEstacionamiento request);

    @POST("EliminaEstacionamiento")
    @Headers("Content-Type: application/json")
    Call<ResponseGeneric<Boolean>> eliminarEstacionamiento(@Body RequestEliminarEstacionamiento request);


    @GET("ListaDistritos")
    Call<ResponseListGeneric<Distrito>> obtenerDistrito();


    //RESERVA
    @POST("FiltraReserva")
    @Headers("Content-Type: application/json")
    Call<ResponseListGeneric<Reserva>> obtenerReserva(@Body RequestObtenerReserva request);

    @POST("RegistraReserva")
    @Headers("Content-Type: application/json")
    Call<ResponseGeneric<Boolean>> reservarEstacionamiento(@Body RequestReservaEstacionamiento request);

    @POST("ActualizaReserva")
    @Headers("Content-Type: application/json")
    Call<ResponseGeneric<Boolean>> actualizarReserva(@Body RequestReservaEstacionamiento request);


    @POST("EliminaReserva")
    @Headers("Content-Type: application/json")
    Call<ResponseGeneric<Boolean>> eliminarReserva(@Body RequestEliminarReserva request);



}
