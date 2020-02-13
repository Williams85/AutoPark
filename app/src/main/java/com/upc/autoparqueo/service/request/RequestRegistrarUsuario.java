package com.upc.autoparqueo.service.request;

import com.google.gson.annotations.SerializedName;

public class RequestRegistrarUsuario  {



    @SerializedName("Codi_Usuario")
    private int codUsuario;

    @SerializedName("Nom_Usuario")
    private String nombre;
    //@SerializedName("Nom_Usuario")
    //private String apellido;
    @SerializedName("Ema_Usuario")
    private String email;
    @SerializedName("Pass_Usuario")
    private String pass;
    @SerializedName("Foto_Usuario")
    private byte[] foto;

    @SerializedName("Nombres")
    private String nombres;

    @SerializedName("Apellidos")
    private String apellido;

    @SerializedName("Sexo")
    private int sexo;

    @SerializedName("FechaNacimiento")
    private String fechaNacimiento;


    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
