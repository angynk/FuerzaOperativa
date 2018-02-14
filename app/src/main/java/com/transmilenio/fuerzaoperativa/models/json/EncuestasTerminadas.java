package com.transmilenio.fuerzaoperativa.models.json;

import java.util.List;


public class EncuestasTerminadas {

    private List<EncuestaJSON> encuestas;

    public EncuestasTerminadas() {
    }

    public List<EncuestaJSON> getEncuestas() {
        return encuestas;
    }

    public void setEncuestas(List<EncuestaJSON> encuestas) {
        this.encuestas = encuestas;
    }
}
