package com.example.jorgecaro.persistencia_jorge_caro.fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.app.Fragment;
import android.app.AlertDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jorgecaro.persistencia_jorge_caro.MainActivity;
import com.example.jorgecaro.persistencia_jorge_caro.R;
import com.example.jorgecaro.persistencia_jorge_caro.modelo.Cliente;
import com.example.jorgecaro.persistencia_jorge_caro.modelo.Parqueadero;
import com.example.jorgecaro.persistencia_jorge_caro.modelo.Parqueo;
import com.example.jorgecaro.persistencia_jorge_caro.modelo.Vehiculo;
import com.example.jorgecaro.persistencia_jorge_caro.recycler_view.RecyclerViewAdapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;



public class ParqueosFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private String archivo = "parking_contr.txt";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_parqueos, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo_registrar().show();
            }
        });
        try {
            if (getActivity().openFileInput(archivo) != null) {
                for(int i = 0; i<((MainActivity)getActivity()).getParqueadero().getParqueos().size(); i++) ((MainActivity)getActivity()).getParqueadero().getParqueos().remove(0);
                cargar();
            }
        }catch (IOException e){

        }
        refresh();
        return view;
    }

    public void refresh(){
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), ((MainActivity)getActivity()).getParqueadero());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayout = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayout);
    }

    public void cargar(){

        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getActivity().openFileInput(archivo)));
            String iteracion;
            while((iteracion =  bufferedReader.readLine()) != null){
                String[] datos = iteracion.split("#");
                ((MainActivity)getActivity()).getParqueadero().agregarParqueo(new Parqueo(new Cliente(datos[0]), new Vehiculo(datos[1]),0));
            }
        }catch (IOException e){
            Toast.makeText(getContext(),"error al abrir archivo", Toast.LENGTH_SHORT);
        }

    }
    public void guardar(String cliente, String matricula){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(getActivity().openFileOutput(archivo,Context.MODE_APPEND)));
                bufferedWriter.write(cliente);
                bufferedWriter.write("#");
                bufferedWriter.write(matricula);
                bufferedWriter.newLine();
            bufferedWriter.close();
        }catch (IOException e){

        }
    }


    public AlertDialog dialogo_registrar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Registrar parqueo");
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText matricula = new EditText(getContext());
        matricula.setHint("No. de la matricula");
        layout.addView(matricula);

        final EditText cliente = new EditText(getContext());
        cliente.setHint("Id del cliente");
        layout.addView(cliente);
        builder.setView(layout);
        builder.setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ((MainActivity)getActivity()).getParqueadero().agregarParqueo(new Parqueo(new Cliente(cliente.getText().toString()), new Vehiculo(matricula.getText().toString()),0));
                guardar(cliente.getText().toString(),matricula.getText().toString());
                Toast.makeText(getContext(), "parqueo registrado" , Toast.LENGTH_LONG).show();
                refresh();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder.create();
    }

}
