package com.example.jorgecaro.persistencia_jorge_caro;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorgecaro.persistencia_jorge_caro.fragments.ConfiguracionesFragment;
import com.example.jorgecaro.persistencia_jorge_caro.fragments.ParqueosFragment;
import com.example.jorgecaro.persistencia_jorge_caro.modelo.Parqueadero;
import com.example.jorgecaro.persistencia_jorge_caro.modelo.Parqueo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    View header;
    SharedPreferences sharedPreferencesCompat;
    private Parqueadero parqueadero = new Parqueadero(new ArrayList<Parqueo>());
    private int escribir, leer, REQUEST_CODE = 1;
    boolean op = false;
    private String[] permisos = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);
        TextView nav_drawer_usuario = (TextView) header.findViewById(R.id.usuario);
        TextView nav_drawer_email = (TextView) header.findViewById(R.id.email);
        sharedPreferencesCompat = getSharedPreferences(Constantes_de_preferencia.PREFERENCIA_1, MODE_PRIVATE);
        nav_drawer_usuario.setText(sharedPreferencesCompat.getString(Constantes_de_preferencia.USUARIO,"n/a"));
        nav_drawer_email.setText(sharedPreferencesCompat.getString(Constantes_de_preferencia.USUARIO,"n/a")+"@example.com");

        getFragmentManager().beginTransaction().replace(R.id.content, new ParqueosFragment()).addToBackStack(null).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.exportar) {
            dialogo_registrar_externamente().show();
            getFragmentManager().beginTransaction().replace(R.id.content, new ParqueosFragment()).commit();
            return true;
        }
        if (id == R.id.cerrar_sesion) {
            String dir = getFilesDir().getAbsolutePath();
            File f0 = new File(dir, Constantes_de_preferencia.PREFERENCIA_1);
            f0.delete();
            clearSharedPreferences(getBaseContext());
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void clearSharedPreferences(Context ctx){
        File dir = new File(ctx.getFilesDir().getParent() + "/shared_prefs/");
        String[] children = dir.list();
        for (int i = 0; i < children.length; i++) {
            ctx.getSharedPreferences(children[i].replace(".xml", ""), Context.MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();
        }
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        for (int i = 0; i < children.length; i++) {
            new File(dir, children[i]).delete();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cuenta) {
            getFragmentManager().beginTransaction().replace(R.id.content, new ConfiguracionesFragment()).commit();
        } else if (id == R.id.nav_parqueos) {
            getFragmentManager().beginTransaction().replace(R.id.content, new ParqueosFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Parqueadero getParqueadero() {
        return parqueadero;
    }


    public void guardar_externamente(){
        escribir = ActivityCompat.checkSelfPermission(this, permisos[0]);
        leer = ActivityCompat.checkSelfPermission(this, permisos[1]);
        if(escribir != PackageManager.PERMISSION_GRANTED && leer != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, permisos, REQUEST_CODE);
        }else{
            try {
                File archivo_externo = new File(getExternalFilesDir(null), "parking_contr.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(archivo_externo, true));
                for (int i=0; i<getParqueadero().getParqueos().size();i++) {
                    bufferedWriter.write(getParqueadero().getParqueos().get(i).getCliente().getId());
                    bufferedWriter.write("#");
                    bufferedWriter.write(getParqueadero().getParqueos().get(i).getVehiculo().getMatricula());
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            } catch (IOException e) {
                Toast.makeText(this, "Error al guardar el archivo", Toast.LENGTH_SHORT);
            }
        }
    }
    public AlertDialog dialogo_registrar_externamente() {

        final String[] opciones = {"Sin conservar los registros localmente", "Conservando los registros localmente"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(opciones, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(opciones[which].equals("Sin conservar los registros localmente")){
                    op = false;
                }
                else op = true;
            }
        });
        builder.setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(op == true){
                    guardar_externamente();
                }
                getParqueadero().getParqueos().clear();
                File dir = getFilesDir();
                File file = new File(dir, "parking_contr.txt");
                file.delete();

            }
        });
        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
