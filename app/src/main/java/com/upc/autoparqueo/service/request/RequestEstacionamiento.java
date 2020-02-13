package com.upc.autoparqueo.service.request;

import com.google.gson.annotations.SerializedName;

public class RequestEstacionamiento {

    @SerializedName("Nom_Estacionamiento")
    private String nomEstacionamiento = "";

    public String getNomEstacionamiento() {
        return nomEstacionamiento;
    }

    public void setNomEstacionamiento(String nomEstacionamiento) {
        this.nomEstacionamiento = nomEstacionamiento;
    }
}
