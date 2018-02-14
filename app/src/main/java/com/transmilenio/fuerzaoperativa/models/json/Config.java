package com.transmilenio.fuerzaoperativa.models.json;

import java.util.List;


public class Config {

    private List<Servicio> servicios;
    private List<EstacionTs> estacionTs;

    public Config() {
    }

    public Config(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public List<EstacionTs> getEstacionTs() {
        return estacionTs;
    }

    public void setEstacionTs(List<EstacionTs> estacionTs) {
        this.estacionTs = estacionTs;
    }
}
