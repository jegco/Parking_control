package com.example.jorgecaro.persistencia_jorge_caro.recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorgecaro.persistencia_jorge_caro.R;
import com.example.jorgecaro.persistencia_jorge_caro.modelo.Parqueadero;

/**
 * Created by jorge caro on 6/21/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private Parqueadero parqueadero;


    Context context;
    LayoutInflater layoutInflater;

    public RecyclerViewAdapter(Context context, Parqueadero parqueadero) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.parqueadero = parqueadero;

    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.getPlaca().setText(parqueadero.getParqueos().get(position).getCliente().getId());
        holder.getCliente().setText(parqueadero.getParqueos().get(position).getVehiculo().getMatricula());
    }

    @Override
    public int getItemCount() {
        return parqueadero.getParqueos().size();
    }
}
