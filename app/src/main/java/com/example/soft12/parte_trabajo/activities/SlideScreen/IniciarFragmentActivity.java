package com.example.soft12.parte_trabajo.activities.SlideScreen;

/**
 * Created by soft12 on 30/06/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.activities.InicioActivity;
import com.example.soft12.parte_trabajo.dao.CAUDAO;
import com.example.soft12.parte_trabajo.dao.ClienteDAO;
import com.example.soft12.parte_trabajo.dao.CocheDAO;
import com.example.soft12.parte_trabajo.dao.DiarioDAO;
import com.example.soft12.parte_trabajo.dao.LoginDAO;
import com.example.soft12.parte_trabajo.dao.SolucionDAO;
import com.example.soft12.parte_trabajo.model.CAU;
import com.example.soft12.parte_trabajo.model.Cliente;
import com.example.soft12.parte_trabajo.model.Coche;
import com.example.soft12.parte_trabajo.model.Diario;
import com.example.soft12.parte_trabajo.model.Login;
import com.example.soft12.parte_trabajo.model.Solucion;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IniciarFragmentActivity extends FragmentActivity{

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
                invalidateOptionsMenu();
            }
        });
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return FirstFragment.newInstance(pos);
                case 1: return SecondFragment.newInstance(pos);
                case 2: return ThirdFragment.newInstance(pos);
                case 3: return ForthFragment.newInstance(pos);
                default: return ThirdFragment.newInstance(pos);
            }
        }

        @Override
        public int getCount() {
            return 4;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

        menu.findItem(R.id.action_previous).setEnabled(pager.getCurrentItem() > 0);

        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (pager.getCurrentItem() == 3)
                        ? R.string.action_finish
                        : R.string.action_next);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, InicioActivity.class));
                return true;

            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                pager.setCurrentItem(pager.getCurrentItem() - 1);
                return true;

            case R.id.action_next:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.
                pager.setCurrentItem(pager.getCurrentItem() + 1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void EnviarDatosDiario(View v){
        DiarioDAO diarioDAO;
        // necesitamos el coche para poder poner los kilómetros actuale
        // también necesitaremos los datos de Login para el técnico

        CAUDAO caudao;
        ClienteDAO clienteDAO;
        SolucionDAO solucionDAO;
        LoginDAO loginDAO;
        CocheDAO cocheDAO;

        caudao = new CAUDAO(this);
        clienteDAO = new ClienteDAO(this);
        solucionDAO = new SolucionDAO(this);
        loginDAO = new LoginDAO(this);
        cocheDAO = new CocheDAO(this);

        // cambiar lo de diario de todos los fragmentos para aquí, pasar los datos
        // con el shared preferences y poner aquí para crear un nuevo diario
        // pero aquí y no en cada fragment


        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        String caunombre = prefs.getString("cau", " ");
        String clientenombre = prefs.getString("cliente", " ");
        String hora_ini = prefs.getString("hora_ini", " ");
        String hora_fin = prefs.getString("hora_fin", " ");
        String solucionnombre = prefs.getString("solucion", " ");
        String desplazamiento = prefs.getString("desplazamiento", " ");
        String km_ini = prefs.getString("km_ini", " ");
        String km_fin = prefs.getString("km_fin", " ");
        String cochematricula = prefs.getString("coche", " ");
        String tecnico = prefs.getString("trabajador", " ");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = sdf.format(new Date());

        Cliente cliente = clienteDAO.getSinlgeClienteEntry(clientenombre);
        Solucion solucion = solucionDAO.getSinlgeSolucionEntry(solucionnombre);
        Login login = loginDAO.getSinlgeLoginIdEntry(tecnico);
        Coche coche = cocheDAO.getSingleCocheMatriculaEntry(cochematricula);
        CAU cau = caudao.getSinlgeCAUEntry(caunombre);

        diarioDAO = new DiarioDAO(this);

        Diario createdDiario = diarioDAO.createDiario(fecha,
                cau.getcNombre(),
                cliente.getnNombre(),
                solucion.getnNombre(),
                hora_ini,
                hora_fin,
                desplazamiento,
                Double.parseDouble(km_ini),
                Double.parseDouble(km_fin),
                login.getcId(),
                coche.getId()
        );

        lanzarEmail(createdDiario);
    }

    private void lanzarEmail(Diario diario) {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        String[] to = { "martagalmangarcia@gmail.com" };
        String subject = "" + diario.getCau();
        String body = diario.toString();
        i.putExtra(Intent.EXTRA_EMAIL, to);
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(i);
    }
}