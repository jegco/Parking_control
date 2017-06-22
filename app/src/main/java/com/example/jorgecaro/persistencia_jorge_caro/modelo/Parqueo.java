package com.example.jorgecaro.persistencia_jorge_caro.modelo;

import java.io.Serializable;

/**
 * Created by jorge caro on 6/21/2017.
 */

public class Parqueo implements Serializable{
    private Cliente cliente;
    private Vehiculo vehiculo;
    private int id;

    public Parqueo(Cliente cliente, Vehiculo vehiculo, int id) {
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
