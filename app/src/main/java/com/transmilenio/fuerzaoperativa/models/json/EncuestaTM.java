package com.transmilenio.fuerzaoperativa.models.json;

import com.transmilenio.fuerzaoperativa.app.MyApplication;
import com.transmilenio.fuerzaoperativa.models.db.ConteoDesEncuesta;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class EncuestaTM extends RealmObject {

    @PrimaryKey
    private int id;

    // Datos Básicos
    private Integer tipo;
    private String nombre_encuesta;
    private String aforador;
    private String identificador;
    private String fecha_encuesta;
    private String dia_semana;
    private int id_realm;

    //Datos por encuesta
    private ConteoDesEncuesta co_despachos; //CONTEO DESPACHOS


    public EncuestaTM() {
        this.id = MyApplication.encuestasTmID.incrementAndGet();
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getNombre_encuesta() {
        return nombre_encuesta;
    }

    public void setNombre_encuesta(String nombre_encuesta) {
        this.nombre_encuesta = nombre_encuesta;
    }

    public String getAforador() {
        return aforador;
    }

    public void setAforador(String aforador) {
        this.aforador = aforador;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getFecha_encuesta() {
        return fecha_encuesta;
    }

    public void setFecha_encuesta(String fecha_encuesta) {
        this.fecha_encuesta = fecha_encuesta;
    }

    public int getId_realm() {
        return id_realm;
    }

    public void setId_realm(int id_realm) {
        this.id_realm = id_realm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ConteoDesEncuesta getCo_despachos() {
        return co_despachos;
    }

    public void setCo_despachos(ConteoDesEncuesta co_despachos) {
        this.co_despachos = co_despachos;
    }

    public String getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }

}
