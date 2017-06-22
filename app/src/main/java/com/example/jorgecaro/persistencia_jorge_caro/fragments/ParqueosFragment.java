package com.example.jorgecaro.persistencia_jorge_caro.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jorgecaro.persistencia_jorge_caro.R;
import com.example.jorgecaro.persistencia_jorge_caro.modelo.Parqueadero;
import com.example.jorgecaro.persistencia_jorge_caro.modelo.Parqueo;
import com.example.jorgecaro.persistencia_jorge_caro.recycler_view.RecyclerViewAdapter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class ParqueosFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private Parqueadero parqueadero;
    public String archivo = "parqueos.obj";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_parqueos, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        try {
            if (getActivity().openFileInput(archivo) != null) parqueadero = cargar();
        }catch (IOException e){

        }
        refresh();
        return view;
    }

    public void refresh(){
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), parqueadero);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public Parqueadero cargar(){
        try {
            Parqueadero parqueadero = new Parqueadero(new ArrayList<Parqueo>());
            ObjectInputStream objectInputStream = new ObjectInputStream(getActivity().openFileInput(archivo));
            Parqueo parqueo;
            while((parqueo = (Parqueo) objectInputStream.readObject()) != null){
                    parqueadero.getParqueos().add(parqueo);
            }
            return parqueadero;
        }catch (IOException e){
            Toast.makeText(getContext(),"error al abrir archivo", Toast.LENGTH_SHORT);
        }catch (ClassNotFoundException e){
            Toast.makeText(getContext(),"error al abrir archivo", Toast.LENGTH_SHORT);
        }
        return new Parqueadero(new ArrayList<Parqueo>());
    }
    public void guardar(){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(getActivity().openFileOutput(archivo,Context.MODE_PRIVATE));
            for(int i=0;i<parqueadero.getParqueos().size();i++) {
               objectOutputStream.writeObject(parqueadero.getParqueos().get(i));
            }
            objectOutputStream.close();
        }catch (IOException e){

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        guardar();
    }
}
