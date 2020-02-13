package com.upc.autoparqueo.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Reserva implements Parcelable {

    /*"Cod_Distrito": 40,
            "Cod_Estacionamiento": 13,
            "Cod_Reserva": 21,
            "Codi_Usuario": 0,
            "Dir_Estacionamiento": "el polo 397",
            "Fecha_Reserva": "09/02/2020",
            "Hora_Fin": "11:00 AM",
            "Hora_Inicio": "10:00 AM",
            "Horas_Reserva": 1,
            "Monto": 15,
            "Nom_Distrito": "SANTIAGO DE SURCO",
            "Nom_Estacionamiento": "SURCO"*/
    @SerializedName("Cod_Reserva")
    private int id;
    @SerializedName("Cod_Estacionamiento")
    private int idEstacionamiento;

    @SerializedName("Hora_Inicio")
    private String horaInicio;
    @SerializedName("Hora_Fin")
    private String horaFin;
    @SerializedName("Horas_Reserva")
    private String horaReserva;
    @SerializedName("Nom_Estacionamiento")
    private String nombre;
    @SerializedName("Dir_Estacionamiento")
    private String direccion;


    @SerializedName("Fecha_Reserva")
    private String fecha;

    @SerializedName("Nom_Distrito")
    private String distrito;

    @SerializedName("Monto")
    private double monto;

    @SerializedName("Codi_Usuario")
    private String codUsuario;

    @SerializedName("Latitud")
    private Double latitud = 0.0;
    @SerializedName("Longitud")
    private Double longitud = 0.0;

    protected Reserva(Parcel in) {
        id = in.readInt();
        idEstacionamiento = in.readInt();
        horaInicio = in.readString();
        horaFin = in.readString();
        horaReserva = in.readString();
        nombre = in.readString();
        direccion = in.readString();
        fecha = in.readString();
        distrito = in.readString();
        monto = in.readDouble();
        codUsuario = in.readString();
        if (in.readByte() == 0) {
            latitud = null;
        } else {
            latitud = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitud = null;
        } else {
            longitud = in.readDouble();
        }
    }

    public static final Creator<Reserva> CREATOR = new Creator<Reserva>() {
        @Override
        public Reserva createFromParcel(Parcel in) {
            return new Reserva(in);
        }

        @Override
        public Reserva[] newArray(int size) {
            return new Reserva[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public int getIdEstacionamiento() {
        return idEstacionamiento;
    }

    public void setIdEstacionamiento(int idEstacionamiento) {
        this.idEstacionamiento = idEstacionamiento;
    }

    public String getHoraReserva() {
        return horaReserva;
    }

    public void setHoraReserva(String horaReserva) {
        this.horaReserva = horaReserva;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(idEstacionamiento);
        parcel.writeString(horaInicio);
        parcel.writeString(horaFin);
        parcel.writeString(horaReserva);
        parcel.writeString(nombre);
        parcel.writeString(direccion);
        parcel.writeString(fecha);
        parcel.writeString(distrito);
        parcel.writeDouble(monto);
        parcel.writeString(codUsuario);
        if (latitud == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(latitud);
        }
        if (longitud == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(longitud);
        }
    }
}

