package com.example.jorgecaro.persistencia_jorge_caro.modelo;

import java.util.ArrayList;

/**
 * Created by jorge caro on 6/21/2017.
 */

public class Parqueadero {
    private ArrayList<Parqueo> parqueos;

    public Parqueadero(ArrayList<Parqueo> parqueos) {
        this.parqueos = parqueos;
    }

    public ArrayList<Parqueo> getParqueos() {
        return parqueos;
    }

    public void setParqueos(ArrayList<Parqueo> parqueos) {
        this.parqueos = parqueos;
    }

    public void agregarParqueo(Parqueo parqueo){
        parqueos.add(parqueo);
    }

}
