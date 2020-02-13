package com.upc.autoparqueo.modelos;

import com.google.gson.annotations.SerializedName;

public class Distrito {

    /*
     "Cod_Distrito": 1,
            "Nom_Distrito": "ANCÓN"
            * */

    @SerializedName("Cod_Distrito")
    private int id;
    @SerializedName("Nom_Distrito")
    private String nombre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
