package com.example.soft12.parte_trabajo.activities.SlideScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.dao.CocheDAO;
import com.example.soft12.parte_trabajo.dao.DiarioDAO;
import com.example.soft12.parte_trabajo.dao.LoginDAO;
import com.example.soft12.parte_trabajo.model.Coche;
import com.example.soft12.parte_trabajo.model.Diario;
import com.example.soft12.parte_trabajo.model.Login;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by soft12 on 30/06/2015.
 */
public class ForthFragment  extends Fragment {

    // TODO COMPROBAR SI FIN DE DIA PARA MANDAR EXCEL
    // TODO AL DARLE AL BOTÓN ENVIAR MAIL


    CheckBox mCheck_FinalDia;
    EditText mTtxt_Trabajadores;
    Button mButtonEnviar;
    Diario diario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.forth_frag, container, false);

        diario = new Diario();
        initViews(v);

        return v;
    }
    private void lanzarEmail(Diario diario) {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        String[] to = { "rruiz@satplus.es" };
        String subject = "" + diario.getCau() + " - " + diario.getTecnico().getNombre();
        String body = diario.toString();
        i.putExtra(Intent.EXTRA_EMAIL, to);
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(i);
    }

    private void establecerValores() {
        //vai fallar o tecnico porque eu poño o nombre e no diairo ten que ser o id
        //mTtxt_Trabajadores.setText(diario.getTecnico().getNombre());

        Editable tecnico = mTtxt_Trabajadores.getText();
        SharedPreferences.Editor editor = this.getActivity().
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE).edit();
        editor.putString("tecnico", tecnico.toString());
        editor.apply();

    }

    private void initViews(View v) {
        this.mTtxt_Trabajadores = (EditText) v.findViewById(R.id.editText_trabajadores);
        this.mCheck_FinalDia = (CheckBox) v.findViewById(R.id.checkBox_FinalDia);
        this.mButtonEnviar = (Button) v.findViewById(R.id.ButtonEnviarDatos);

        SharedPreferences prefs = this.getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String trabajador = prefs.getString("trabajador", " ");
        mTtxt_Trabajadores.setText(trabajador);

        mCheck_FinalDia.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    establecerValores();
                }

            }
        });

        mTtxt_Trabajadores.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    establecerValores();
                }

            }
        });

        mButtonEnviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Poner aquí para que se envíe el correo y si el check está activo
                // crear excel  a partir de los datos
                if (mCheck_FinalDia.isChecked()) {
                    // es final de día para mandar excel
                }
                // aunque se cree el excel hay que mandar el correo
                LoginDAO loginDAO;
                CocheDAO cocheDAO;

                loginDAO = new LoginDAO(getActivity());
                cocheDAO = new CocheDAO(getActivity());

                // cambiar lo de diario de todos los fragmentos para aquí, pasar los datos
                // con el shared preferences y poner aquí para crear un nuevo diario
                // pero aquí y no en cada fragment
                diario = new Diario();

                SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

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

                //Cliente cliente = clienteDAO.getSinlgeClienteEntry(clientenombre);
                //Solucion solucion = solucionDAO.getSinlgeSolucionEntry(solucionnombre);
                Login login = loginDAO.getSinlgeLoginIdEntry(tecnico);
                Coche coche = cocheDAO.getSingleCocheMatriculaEntry(cochematricula);
                //CAU cau = caudao.getSinlgeCAUEntry(caunombre);

                DiarioDAO diarioDAO = new DiarioDAO(getActivity());

                Diario createdDiario = diarioDAO.createDiario(fecha,
                        caunombre,
                        clientenombre,
                        solucionnombre,
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
        });
    }

    public static ForthFragment newInstance(int text) {

        ForthFragment f = new ForthFragment();
        Bundle b = new Bundle();
        b.putInt("msg", text);

        f.setArguments(b);

        return f;
    }
}
