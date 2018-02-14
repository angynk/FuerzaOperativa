package com.transmilenio.fuerzaoperativa.models.db;


import com.transmilenio.fuerzaoperativa.app.MyApplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ServTemp extends RealmObject {

    @PrimaryKey
    private int id;

    private String servicio;

    public ServTemp() {
        this.id = MyApplication.servTempID.incrementAndGet();
    }

    public ServTemp(String servicio) {
        this.servicio = servicio;
        this.id = MyApplication.servTempID.incrementAndGet();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
}
