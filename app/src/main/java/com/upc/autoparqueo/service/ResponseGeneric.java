package com.upc.autoparqueo.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGeneric<T> {
    @SerializedName("Estado")
    @Expose
    private boolean estado;

    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    @SerializedName("Valor")
    @Expose
    private T valor;

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }
}
