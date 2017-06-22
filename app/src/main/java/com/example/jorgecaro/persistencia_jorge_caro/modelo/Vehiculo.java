package com.example.jorgecaro.persistencia_jorge_caro.modelo;

/**
 * Created by jorge caro on 6/21/2017.
 */

public class Vehiculo {
    public String matricula;
    public String tipo;

    public Vehiculo(String matricula) {
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
