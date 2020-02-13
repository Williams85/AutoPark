package com.upc.autoparqueo.service.request;

import com.google.gson.annotations.SerializedName;

public class RequestValidarUsuario {

    /*{
        "Nom_Usuario":"U201512638",
            "Pass_Usuario":"1234567"
    }*/
    @SerializedName("Nom_Usuario")
    private String codUsuario;
    @SerializedName("Pass_Usuario")
    private String passUsuario;

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getPassUsuario() {
        return passUsuario;
    }

    public void setPassUsuario(String passUsuario) {
        this.passUsuario = passUsuario;
    }
}
