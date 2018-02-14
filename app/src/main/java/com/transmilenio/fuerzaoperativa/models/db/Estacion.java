package com.transmilenio.fuerzaoperativa.models.db;


import com.transmilenio.fuerzaoperativa.app.MyApplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Estacion  extends RealmObject {

    @PrimaryKey
    private int id;

    private String nombre;
    private String tipo;


    public Estacion() {
        this.id = MyApplication.estacionID.incrementAndGet();
    }


    public Estacion(String nombre,String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.id = MyApplication.estacionID.incrementAndGet();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


}
