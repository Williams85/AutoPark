package com.upc.autoparqueo.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseListGeneric<T> {

    @SerializedName("Estado")
    @Expose
    private boolean estado;
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("Valor")
    @Expose
    private List<T> data;

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
