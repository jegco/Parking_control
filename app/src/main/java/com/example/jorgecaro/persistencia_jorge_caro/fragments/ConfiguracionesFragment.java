package com.example.jorgecaro.persistencia_jorge_caro.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.TextView;

import com.example.jorgecaro.persistencia_jorge_caro.Constantes_de_preferencia;
import com.example.jorgecaro.persistencia_jorge_caro.R;

/**
 * Created by jorge caro on 6/12/2017.
 */

public class ConfiguracionesFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.configuraciones);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        TextView txtUsuario = (TextView)getActivity().findViewById(R.id.usuario);
        TextView txtEmail = (TextView)getActivity().findViewById(R.id.email);
        txtUsuario.setText(sharedPreferences.getString("usuario","n/a"));
        txtEmail.setText(sharedPreferences.getString("usuario","n/a")+"@nextu.com");
    }
}
