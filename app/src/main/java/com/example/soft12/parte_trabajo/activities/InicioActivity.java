package com.example.soft12.parte_trabajo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.activities.CAU.ListCAUActivity;
import com.example.soft12.parte_trabajo.activities.Cliente.ListClientesActivity;
import com.example.soft12.parte_trabajo.activities.Coche.ListCochesActivity;
import com.example.soft12.parte_trabajo.activities.Diario.ListDiarioActivity;
import com.example.soft12.parte_trabajo.activities.Repostaje.ListRepostajeActivity;
import com.example.soft12.parte_trabajo.activities.Solucion.ListSolucionesActivity;


public class InicioActivity extends Activity {

    private static final String LOGTAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
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

    // Al Hacer Click en EditDiario
//    public void HacerDiario(View v){
//        try {
//            Intent i = new Intent(this, EditDiario.class);
//            startActivity(i);
//        } catch (Exception e) {
//            Log.e(LOGTAG, e.getMessage());
//        }
//    }
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

    public void VerCoches(View v){
        try{
            Intent i = new Intent(this, ListCochesActivity.class);
            startActivity(i);
        }catch (Exception e){
            Log.e(LOGTAG, e.getMessage());
        }
    }

    public void HacerSolucion(View v){
        try{
            Intent i = new Intent(this, ListSolucionesActivity.class);
            startActivity(i);
        }catch (Exception e){
            Log.e(LOGTAG, e.getMessage());
        }
    }

    public void HacerCliente(View v){
        try{
            Intent i = new Intent(this, ListClientesActivity.class);
            startActivity(i);
        }catch (Exception e){
            Log.e(LOGTAG, e.getMessage());
        }
    }

    public void HacerCAU(View v){
        try{
            Intent i = new Intent(this, ListCAUActivity.class);
            startActivity(i);
        }catch (Exception e){
            Log.e(LOGTAG, e.getMessage());
        }
    }

    public void HacerDiario(View v){
        try {
            Intent i = new Intent(this, ListDiarioActivity.class);
            startActivity(i);
        }catch (Exception e){
            Log.e(LOGTAG, e.getMessage());
        }
    }
}
