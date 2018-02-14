package com.transmilenio.fuerzaoperativa.models.db;

import com.transmilenio.fuerzaoperativa.app.MyApplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


public class Aforador extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String usuario;
    @Required
    private String contrasena;

    public Aforador() {
        this.id = MyApplication.aforadorID.incrementAndGet();
    }

    public Aforador(String user, String contrasena) {
        this.usuario = user;
        this.contrasena = contrasena;
        this.id = MyApplication.aforadorID.incrementAndGet();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
