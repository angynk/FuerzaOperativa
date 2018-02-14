package com.transmilenio.fuerzaoperativa.models.db;

import com.transmilenio.fuerzaoperativa.app.MyApplication;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class EstacionServicio extends RealmObject {

    @PrimaryKey
    private int id;

    private String nombre;
    private String tipo;
    private RealmList<Serv> servicios;

    public EstacionServicio() {
        this.id = MyApplication.estacionServicioID.incrementAndGet();
        servicios = new RealmList<Serv>();
    }

    public EstacionServicio(String nombre, String tipo) {
        this.id = MyApplication.estacionServicioID.incrementAndGet();
        this.nombre = nombre;
        this.tipo = tipo;
        servicios = new RealmList<Serv>();
    }

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public RealmList<Serv> getServicios() {
        return servicios;
    }

    public void setServicios(RealmList<Serv> servicios) {
        this.servicios = servicios;
    }
}
