package com.transmilenio.fuerzaoperativa.models.json;

import java.util.List;

public class EstacionTs {

    private String nombre;
    private String tipo;
    private List<String> servicios;

    public EstacionTs() {
    }

    public EstacionTs(String nombre, String tipo, List<String> servicios) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.servicios = servicios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<String> getServicios() {
        return servicios;
    }

    public void setServicios(List<String> servicios) {
        this.servicios = servicios;
    }
}
