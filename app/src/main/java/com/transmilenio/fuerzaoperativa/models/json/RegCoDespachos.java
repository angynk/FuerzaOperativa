package com.transmilenio.fuerzaoperativa.models.json;

/**
 * Created by nataly on 12/12/2017.
 */

public class RegCoDespachos {

    private String num_bus;
    private String hora_despacho;
    private String servicio;

    public RegCoDespachos() {
    }

    public RegCoDespachos(String num_bus, String hora_despacho) {
        this.num_bus = num_bus;
        this.hora_despacho = hora_despacho;
    }

    public String getNum_bus() {
        return num_bus;
    }

    public void setNum_bus(String num_bus) {
        this.num_bus = num_bus;
    }

    public String getHora_despacho() {
        return hora_despacho;
    }

    public void setHora_despacho(String hora_despacho) {
        this.hora_despacho = hora_despacho;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
}
