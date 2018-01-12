package com.jhon.appandroidfinal.Model;

/**
 * Created by DS on 07/01/2018.
 */

public class Coche {
    String marca;
    String dueno;
    int puertas;
    int ruedas;

    public Coche() {
    }

    public Coche(String marca, String dueno, int puertas, int ruedas) {
        this.marca = marca;
        this.dueno = dueno;
        this.puertas = puertas;
        this.ruedas = ruedas;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDueno() {
        return dueno;
    }

    public void setDueno(String dueno) {
        this.dueno = dueno;
    }

    public int getPuertas() {
        return puertas;
    }

    public void setPuertas(int puertas) {
        this.puertas = puertas;
    }

    public int getRuedas() {
        return ruedas;
    }

    public void setRuedas(int ruedas) {
        this.ruedas = ruedas;
    }
}
