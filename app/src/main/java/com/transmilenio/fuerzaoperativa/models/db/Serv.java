package com.transmilenio.fuerzaoperativa.models.db;

import com.transmilenio.fuerzaoperativa.app.MyApplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Serv extends RealmObject {

    @PrimaryKey
    private int id;

    private String nombre;

    public Serv(String nombre) {
        this.id = MyApplication.servID.incrementAndGet();
        this.nombre = nombre;
    }

    public Serv() {
        this.id = MyApplication.servID.incrementAndGet();
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

}
