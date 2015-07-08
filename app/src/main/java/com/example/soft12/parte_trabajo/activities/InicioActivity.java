package com.example.soft12.parte_trabajo.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.model.Login;


public class InicioActivity extends Activity {

    private static final String LOGTAG = "InicioActivity";
    public String trabajador;

    Login loguearse = new Login();
    Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        extras = getIntent().getExtras();
        boolean login = extras.getBoolean("login");

        if(login) {
            loguearse.setBundle(extras);
            this.trabajador = loguearse.getNombre();
            SharedPreferences.Editor editor = getSharedPreferences("MisPreferencias", MODE_PRIVATE).edit();
            editor.putString("trabajador", trabajador);
            editor.putLong("trabajadorid",loguearse.getcId());
            editor.apply();
            Toast.makeText(getBaseContext(),
                    trabajador, Toast.LENGTH_SHORT)
                    .show();

        }else {
            Toast.makeText(getBaseContext(),
                    "No puedes acceder sin estar logueado.", Toast.LENGTH_SHORT)
                    .show();
            Intent i = new Intent(this, InicioSesion.class);
            startActivity(i);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Al hacer click en Salir
    public void Salir(View v){
        finish();
    }

    // Al hacer click en Historico

    // Al hacer click en Gasolinera
    public void HecharGasoil(View v){
        try{
            Intent i = new Intent(this, ListRepostajeActivity.class);
            startActivity(i);
        } catch (Exception e){
            Log.e(LOGTAG, e.getMessage());
        }
    }

    public void EnviarExcel(View v){
        try{
            Intent i = new Intent(this, EnviarExcel.class);
            startActivity(i);
        }
        catch (Exception e){
            Log.e(LOGTAG, e.getMessage());
        }
    }

    public void HacerDiario(View v){
        /*try {
            Intent i = new Intent(this, ListDiarioActivity.class);
            startActivity(i);
        }catch (Exception e){
            Log.e(LOGTAG, e.getMessage());
        }*/
       try{
           Bundle extras = loguearse.getBundle();
           Intent i = new Intent(this, IniciarFragmentActivity.class);
           extras.putBoolean("login", true);
           i.putExtras(extras);
           startActivity(i);
       }catch (Exception e){
           Log.e(LOGTAG, e.getMessage());
       }

    }

}
