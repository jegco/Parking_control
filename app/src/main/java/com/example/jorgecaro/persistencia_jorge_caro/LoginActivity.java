package com.example.jorgecaro.persistencia_jorge_caro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    EditText editText_nombre, editText_pass;
    TextInputLayout nombre, pass;
    Button iniciar_sesion;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferencesCompat;
    CheckBox recordarme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nombre = (TextInputLayout) findViewById(R.id.til_nombre_usuario);
        pass = (TextInputLayout) findViewById(R.id.til_password);
        iniciar_sesion = (Button) findViewById(R.id.button_iniciar_sesion);
        recordarme = (CheckBox) findViewById(R.id.recordarme);
        sharedPreferencesCompat = getSharedPreferences(Constantes_de_preferencia.PREFERENCIA_1, MODE_PRIVATE);
        editor = sharedPreferencesCompat.edit();

        if(!sharedPreferencesCompat.getString(Constantes_de_preferencia.USUARIO,"n/a").equals("n/a")&&!sharedPreferencesCompat.getString(Constantes_de_preferencia.PASS,"n/a").equals("n/a")){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }

        iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_nombre = nombre.getEditText();
                editText_pass = pass.getEditText();
                if(!editText_nombre.getText().toString().equals("")&&!editText_pass.getText().toString().equals("")) {
                    if(recordarme.isChecked()) {
                        editor.putString("usuario", editText_nombre.getText().toString());
                        editor.putString("pass", editText_pass.getText().toString());
                        editor.apply();
                    }
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }else if(editText_nombre.getText().toString().equals("")){
                    pass.setError("");
                    nombre.setError(getResources().getString(R.string.error_usuario));
                }else if(editText_pass.getText().toString().equals("")){
                    nombre.setError("");
                    pass.setError(getResources().getString(R.string.error_password));
                }
            }
        });

    }
}
