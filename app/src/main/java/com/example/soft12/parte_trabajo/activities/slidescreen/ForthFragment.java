package com.example.soft12.parte_trabajo.activities.slidescreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.activities.excel.EnviarExcel;
import com.example.soft12.parte_trabajo.dao.CocheDAO;
import com.example.soft12.parte_trabajo.dao.DiarioDAO;
import com.example.soft12.parte_trabajo.dao.LoginDAO;
import com.example.soft12.parte_trabajo.model.Coche;
import com.example.soft12.parte_trabajo.model.Diario;
import com.example.soft12.parte_trabajo.model.Login;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by soft12 on 30/06/2015.
 */
public class ForthFragment  extends Fragment {

    // TODO COMPROBAR SI FIN DE DIA PARA MANDAR EXCEL

    public static final String TAG = "FortFragment";
    CheckBox mCheck_FinalDia;
    EditText mTtxt_Trabajadores;
    Button mButtonEnviar;
    Diario diario;

    public static ForthFragment newInstance(int text) {

        ForthFragment f = new ForthFragment();
        Bundle b = new Bundle();
        b.putInt("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        SharedPreferences prefs = this.getActivity().
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
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

                // aunque se cree el excel hay que mandar el correo
                LoginDAO loginDAO;
                CocheDAO cocheDAO;
                DiarioDAO diarioDAO = new DiarioDAO(getActivity());

                loginDAO = new LoginDAO(getActivity());
                cocheDAO = new CocheDAO(getActivity());
                //diarioDAO = new DiarioDAO(getActivity());

                // cambiar lo de diario de todos los fragmentos para aquí, pasar los datos
                // con el shared preferences y poner aquí para crear un nuevo diario
                // pero aquí y no en cada fragment
                diario = new Diario();

                SharedPreferences prefs = getActivity().
                        getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

                String caunombre = prefs.getString("cau", " ");
                if (Objects.equals(caunombre, " ")) caunombre = "";
                String clientenombre = prefs.getString("cliente", " ");
                String hora_ini = prefs.getString("hora_ini", " ");
                String hora_fin = prefs.getString("hora_fin", " ");
                String solucionnombre = prefs.getString("solucion", " ");
                String desplazamiento = prefs.getString("desplazamiento", " ");
                int km_ini = prefs.getInt("km_ini", 0);
                int km_fin = prefs.getInt("km_fin", 0);
                String cochematricula = prefs.getString("coche", " ");
                String tecnico = prefs.getString("trabajador", " ");
                Long tecnicoid = prefs.getLong("trabajadorid", 0);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String fecha = sdf.format(new Date());

                assert hora_ini != null;
                String[] h_ini = hora_ini.split(":");

                assert hora_fin != null;
                String[] h_fin = hora_fin.split(":");

                if (!TextUtils.isEmpty(clientenombre) && !TextUtils.isEmpty(hora_ini)
                        && !TextUtils.isEmpty(hora_fin) && !TextUtils.isEmpty(solucionnombre)
                        && !TextUtils.isEmpty(desplazamiento) && km_ini != 0
                        && km_fin != 0 && !TextUtils.isEmpty(cochematricula)
                        && !TextUtils.isEmpty(tecnico)) {
                    if (Integer.parseInt(h_ini[0]) <= Integer.parseInt(h_fin[0]))
                    // si la hora inicial es menor que la final ó
                    { // son iguales
                        if ((Integer.parseInt(h_ini[0]) == Integer.parseInt(h_fin[0]) &&
                                Integer.parseInt(h_ini[1]) < Integer.parseInt(h_fin[1]))
                                || (Integer.parseInt(h_ini[0]) < Integer.parseInt(h_fin[0]))) {
                            // si son iguales las horas e os minutos_ini < que minutos_fin
                            if (km_ini < km_fin) { // si km iniciales mayoures que acutales

                                Login login = loginDAO.getSinlgeLoginIdEntry(tecnico);
                                Coche coche = cocheDAO.
                                        getSingleCocheMatriculaEntry(cochematricula);
                                diarioDAO = new DiarioDAO(getActivity());
                                Diario createdDiario = diarioDAO.createDiario(fecha,
                                        caunombre,
                                        clientenombre,
                                        solucionnombre,
                                        hora_ini,
                                        hora_fin,
                                        desplazamiento,
                                        Double.parseDouble(String.valueOf(km_ini)),
                                        Double.parseDouble(String.valueOf(km_fin)),
                                        login.getcId(),
                                        coche.getId()
                                );
                                lanzarEmail(createdDiario);
                                Log.e(TAG, createdDiario.toString());
                            } else {
                                Toast.makeText(getActivity(),
                                        R.string.km_ini_mayor, Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(getActivity(),
                                    R.string.minutos_ini_mayor, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(),
                                R.string.hora_ini_mayor, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.empty_fields_message,
                            Toast.LENGTH_LONG).show();
                }
                if (mCheck_FinalDia.isChecked()) {
                    // es final de día para mandar excel
                    // coller os datos do día de hoxe
                    List<Diario> diarioArrayList = diarioDAO.getDateDiario(fecha, tecnicoid);
                    new EnviarExcel();
                    EnviarExcel.saveExcelFile("lars.xls", diarioArrayList);
                    System.out.println("Please check the result file under lars.xls ");
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
