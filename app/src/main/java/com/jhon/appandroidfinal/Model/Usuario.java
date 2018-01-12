package com.jhon.appandroidfinal.Model;

/**
 * Created by DS on 07/01/2018.
 */

public class Usuario {
    String nombres;
    String apellidos;
    String correo;
    double latitud;
    double longitud;


    public Usuario() {
    }

    public Usuario(String nombres, String apellidos, String correo, double latitud, double longitud) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
