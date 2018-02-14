package com.transmilenio.fuerzaoperativa.models.db;

import com.transmilenio.fuerzaoperativa.app.MyApplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class RegistroConteo extends RealmObject {

    @PrimaryKey
    private int id;
    private String numBus;
    private String horadespacho;
    private String servicio;

    public RegistroConteo() {
        this.id = MyApplication.regConteoDespaID.incrementAndGet();
    }

    public RegistroConteo(String numBus, String horadespacho) {
        this.numBus = numBus;
        this.horadespacho = horadespacho;
        this.id = MyApplication.regConteoDespaID.incrementAndGet();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumBus() {
        return numBus;
    }

    public void setNumBus(String numBus) {
        this.numBus = numBus;
    }

    public String getHoradespacho() {
        return horadespacho;
    }

    public void setHoradespacho(String horadespacho) {
        this.horadespacho = horadespacho;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
}
