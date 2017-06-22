package com.example.jorgecaro.persistencia_jorge_caro.modelo;

/**
 * Created by jorge caro on 6/21/2017.
 */

public class Cliente {
    private String id;
    private String nombre;
    private String email;

    public Cliente(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
