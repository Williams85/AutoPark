package com.upc.autoparqueo.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Estacionamiento implements Parcelable {
    @SerializedName("Cod_Estacionamiento")
    private int id;
    @SerializedName("Cod_Distrito")
    private int codDistrito;
    @SerializedName("Hora_Inicio")
    private String horaInicio;
    @SerializedName("Hora_Fin")
    private String horaFin;
    @SerializedName("Nom_Estacionamiento")
    private String nombre;
    @SerializedName("Dir_Estacionamiento")
    private String direccion;
    @SerializedName("Nom_Distrito")
    private String nomDistrito;
    @SerializedName("Latitud")
    private Double latitud;
    @SerializedName("Longitud")
    private Double longitud;
    @SerializedName("Precio")
    private Double precio;
    @SerializedName("Activo")
    private boolean activo;

    protected Estacionamiento(Parcel in) {
        id = in.readInt();
        codDistrito = in.readInt();
        horaInicio = in.readString();
        horaFin = in.readString();
        nombre = in.readString();
        direccion = in.readString();
        nomDistrito = in.readString();
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
        if (in.readByte() == 0) {
            precio = null;
        } else {
            precio = in.readDouble();
        }
        activo = in.readByte() != 0;
    }

    public static final Creator<Estacionamiento> CREATOR = new Creator<Estacionamiento>() {
        @Override
        public Estacionamiento createFromParcel(Parcel in) {
            return new Estacionamiento(in);
        }

        @Override
        public Estacionamiento[] newArray(int size) {
            return new Estacionamiento[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodDistrito() {
        return codDistrito;
    }

    public void setCodDistrito(int codDistrito) {
        this.codDistrito = codDistrito;
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

    public String getNomDistrito() {
        return nomDistrito;
    }

    public void setNomDistrito(String nomDistrito) {
        this.nomDistrito = nomDistrito;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public boolean isActivo() {
        return activo;

    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(id);
        parcel.writeInt(codDistrito);
        parcel.writeString(horaInicio);
        parcel.writeString(horaFin);
        parcel.writeString(nombre);
        parcel.writeString(direccion);
        parcel.writeString(nomDistrito);
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
        if (precio == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(precio);
        }
        parcel.writeByte((byte) (activo ? 1 : 0));
    }

}
