package com.upc.autoparqueo.service.request;

import com.google.gson.annotations.SerializedName;

public class RequestEliminarReserva {
    /*{
        "Cod_Estacionamiento":"1"
    }*/

    @SerializedName("Cod_Reserva")
    private int codReserva;


    public int getCodReserva() {
        return codReserva;
    }

    public void setCodReserva(int codReserva) {
        this.codReserva = codReserva;
    }
}
