package com.transmilenio.fuerzaoperativa.models.json;

import java.util.List;

/**
 * Created by nataly on 12/12/2017.
 */

public class CO_Despacho {

    private String estacion;

    private List<RegCoDespachos> registros;

    public CO_Despacho() {
    }

    public CO_Despacho(String estacion, List<RegCoDespachos> registros) {
        this.estacion = estacion;
        this.registros = registros;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public List<RegCoDespachos> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegCoDespachos> registros) {
        this.registros = registros;
    }
}
