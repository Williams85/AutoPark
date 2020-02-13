package com.upc.autoparqueo.service.request;

import com.google.gson.annotations.SerializedName;

public class RequestRegistrarEstacionamiento {

    /*{
	"Nom_Estacionamiento":"",
	"Dir_Estacionamiento":"",
	"Cod_Distrito":"",
	"Longitud":"",
	"Latitud":"",
	"Hora_Inicio":"",
	"Hora_Fin":"",
	"Precio":""
}*/
    @SerializedName("Cod_Estacionamiento")
    private String codEstacionamiento;
    @SerializedName("Nom_Estacionamiento")
    private String nomEstacionamiento;
    @SerializedName("Dir_Estacionamiento")
    private String dirEstacionamiento;
    @SerializedName("Cod_Distrito")
    private int codDistrito;
    @SerializedName("Longitud")
    private String longitud;
    @SerializedName("Latitud")
    private String latitud;
    @SerializedName("Hora_Inicio")
    private String horaInicio;
    @SerializedName("Hora_Fin")
    private String horaFin;
    @SerializedName("Precio")
    private String precio;

    @SerializedName("Activo")
    private boolean activo;

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getCodEstacionamiento() {
        return codEstacionamiento;
    }

    public void setCodEstacionamiento(String codEstacionamiento) {
        this.codEstacionamiento = codEstacionamiento;
    }

    public String getNomEstacionamiento() {
        return nomEstacionamiento;
    }

    public void setNomEstacionamiento(String nomEstacionamiento) {
        this.nomEstacionamiento = nomEstacionamiento;
    }

    public String getDirEstacionamiento() {
        return dirEstacionamiento;
    }

    public void setDirEstacionamiento(String dirEstacionamiento) {
        this.dirEstacionamiento = dirEstacionamiento;
    }

    public int getCodDistrito() {
        return codDistrito;
    }

    public void setCodDistrito(int codDistrito) {
        this.codDistrito = codDistrito;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }


}
