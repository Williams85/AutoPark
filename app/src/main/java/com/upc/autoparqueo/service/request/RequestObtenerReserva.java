package com.upc.autoparqueo.service.request;

import com.google.gson.annotations.SerializedName;

public class RequestObtenerReserva {

    /*{
	"Nom_Estacionamiento":"",
	"Fecha_Reserva":""
}*/
    @SerializedName("Codi_Usuario")
    private int codUsuario;

    @SerializedName("Nom_Estacionamiento")
    private String nomEstacionamiento = "";

    @SerializedName("Fecha_Reserva")
    private String fechaReserva = "";

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNomEstacionamiento() {
        return nomEstacionamiento;
    }

    public void setNomEstacionamiento(String nomEstacionamiento) {
        this.nomEstacionamiento = nomEstacionamiento;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
}
