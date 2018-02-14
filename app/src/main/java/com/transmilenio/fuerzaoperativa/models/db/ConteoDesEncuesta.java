package com.transmilenio.fuerzaoperativa.models.db;

import com.transmilenio.fuerzaoperativa.app.MyApplication;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class ConteoDesEncuesta extends RealmObject {

    @PrimaryKey
    private int id;
    private String estacion;

    private RealmList<RegistroConteo> registros;

    public ConteoDesEncuesta() {
        this.id = MyApplication.conteoDespaID.incrementAndGet();
        this.registros = new RealmList<RegistroConteo>();
    }

    public ConteoDesEncuesta(String estacion, String servicio) {
        this.estacion = estacion;
        this.id = MyApplication.conteoDespaID.incrementAndGet();
        this.registros = new RealmList<RegistroConteo>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public RealmList<RegistroConteo> getRegistros() {
        return registros;
    }

    public void setRegistros(RealmList<RegistroConteo> registros) {
        this.registros = registros;
    }
}
