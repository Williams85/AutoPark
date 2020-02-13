package com.upc.autoparqueo.service.request;

import com.google.gson.annotations.SerializedName;

public class RequestEliminarEstacionamiento {
    /*{
        "Cod_Estacionamiento":"1"
    }*/

    @SerializedName("Cod_Estacionamiento")
    private int codEstacionamiento;

    public int getCodEstacionamiento() {
        return codEstacionamiento;
    }

    public void setCodEstacionamiento(int codEstacionamiento) {
        this.codEstacionamiento = codEstacionamiento;
    }
}
