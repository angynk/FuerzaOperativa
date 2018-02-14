package com.transmilenio.fuerzaoperativa.models.db;

/**
 * Created by nataly on 05/10/2017.
 */

public class Opcion {

    private String name;
    private int thumbnail;

    public Opcion() {
    }

    public Opcion(String name,  int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
