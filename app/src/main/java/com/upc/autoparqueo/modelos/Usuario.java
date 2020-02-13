package com.upc.autoparqueo.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Usuario implements Parcelable {

    /*{
    "Valor": {
        "Activo": false,
        "Administrador": false,
        "Apellidos": null,
        "Codi_Usuario": 0,
        "Ema_Usuario": null,
        "FechaNacimiento": null,
        "Foto_Usuario": null,
        "Nom_Usuario": null,
        "Nombres": null,
        "Pass_Usuario": null,
        "Sexo": 0
    }
}*/
    @SerializedName("Activo")
    private boolean activo;
    @SerializedName("Administrador")
    private boolean administrador;
    @SerializedName("Apellidos")
    private String apellidos = "";
    @SerializedName("Codi_Usuario")
    private int codUsuario = -1;
    @SerializedName("Ema_Usuario")
    private String email = "";
    @SerializedName("FechaNacimiento")
    private String fechaNacimiento;
    @SerializedName("Foto_Usuario")
    private byte[] foto;
    @SerializedName("Nom_Usuario")
    private String codigo = "";
    @SerializedName("Pass_Usuario")
    private String pass = "";
    @SerializedName("Nombres")
    private String nombre = "";
    @SerializedName("Sexo")
    private int sexo = 1;

    protected Usuario(Parcel in) {
        activo = in.readByte() != 0;
        administrador = in.readByte() != 0;
        apellidos = in.readString();
        codUsuario = in.readInt();
        email = in.readString();
        fechaNacimiento = in.readString();
        foto = in.createByteArray();
        codigo = in.readString();
        pass = in.readString();
        nombre = in.readString();
        sexo = in.readInt();

    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }


    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (activo ? 1 : 0));
        parcel.writeByte((byte) (administrador ? 1 : 0));
        parcel.writeString(apellidos);
        parcel.writeInt(codUsuario);
        parcel.writeString(email);
        parcel.writeString(fechaNacimiento);
        parcel.writeByteArray(foto);
        parcel.writeString(codigo);
        parcel.writeString(pass);
        parcel.writeString(nombre);
        parcel.writeInt(sexo);

    }
}
