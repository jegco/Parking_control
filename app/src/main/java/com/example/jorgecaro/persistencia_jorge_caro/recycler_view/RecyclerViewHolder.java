package com.example.jorgecaro.persistencia_jorge_caro.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jorgecaro.persistencia_jorge_caro.R;

/**
 * Created by jorge caro on 6/21/2017.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder{


    private TextView placa, cliente;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        placa = (TextView) itemView.findViewById(R.id.placa);
        cliente = (TextView) itemView.findViewById(R.id.cliente);
    }



    public TextView getPlaca() {
        return placa;
    }

    public TextView getCliente() {
        return cliente;
    }
}
