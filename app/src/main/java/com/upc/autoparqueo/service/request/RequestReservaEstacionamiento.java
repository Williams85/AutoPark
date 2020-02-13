package com.upc.autoparqueo.service.request;

import com.google.gson.annotations.SerializedName;

public class RequestReservaEstacionamiento {

    /*{
	{
	"Cod_Usuario":1,
	"Cod_Estacionamiento":5,
	"Fecha_Reserva":"12/02/2020",
	"Hora_Inicio":"10:00AM",
	"Hora_Fin":"11:00AM",
	"Monto":40.0,
	"Horas_Reserva":2,
	"Activo":true
}
}*/
    @SerializedName("Cod_Reserva")
    private int idReserva = 0;

    @SerializedName("Codi_Usuario")
    private int codUsuario = 0;
    @SerializedName("Cod_Estacionamiento")
    private int codEstacionamiento = 0;
    @SerializedName("Fecha_Reserva")
    private String fechaReserva = "";
    @SerializedName("Hora_Inicio")
    private String horaInicio = "";
    @SerializedName("Hora_Fin")
    private String horaFin = "";
    @SerializedName("Monto")
    private double monto = 0.0;
    @SerializedName("Horas_Reserva")
    private double horasReserva = 0;

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public int getCodEstacionamiento() {
        return codEstacionamiento;
    }

    public void setCodEstacionamiento(int codEstacionamiento) {
        this.codEstacionamiento = codEstacionamiento;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
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

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getHorasReserva() {
        return horasReserva;
    }

    public void setHorasReserva(double horasReserva) {
        this.horasReserva = horasReserva;
    }
}
