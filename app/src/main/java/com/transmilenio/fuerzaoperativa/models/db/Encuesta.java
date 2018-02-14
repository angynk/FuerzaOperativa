package com.transmilenio.fuerzaoperativa.models.db;

/**
 * Created by nataly on 04/10/2017.
 */

public class Encuesta {

    private String nombre;

    public Encuesta() {
    }

    public Encuesta(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
