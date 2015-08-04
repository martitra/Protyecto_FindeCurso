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
import android.widget.EditText;
import android.widget.Toast;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.dao.ClienteDAO;
import com.example.soft12.parte_trabajo.dao.CocheDAO;
import com.example.soft12.parte_trabajo.dao.DiarioDAO;
import com.example.soft12.parte_trabajo.dao.TecnicoDAO;
import com.example.soft12.parte_trabajo.dao.Tecnico_DiarioDAO;
import com.example.soft12.parte_trabajo.model.Cliente;
import com.example.soft12.parte_trabajo.model.Coche;
import com.example.soft12.parte_trabajo.model.Diario;
import com.example.soft12.parte_trabajo.model.Tecnico;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by soft12 on 30/06/2015.
 */
public class ForthFragment  extends Fragment {

    //TODO mirar para más de un trajador, mirar como hacer

    public static final String TAG = "ForthFragment";
    EditText mTtxt_Trabajadores;
    Button mButtonEnviar, mButtonSalir;
    Diario diario;
    String[] tec;

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
    private void initViews(View v) {
        this.mTtxt_Trabajadores = (EditText) v.findViewById(R.id.editText_trabajadores);
        this.mButtonEnviar = (Button) v.findViewById(R.id.ButtonEnviarDatos);
        this.mButtonSalir = (Button) v.findViewById(R.id.SalirFF);

        SharedPreferences prefs = this.getActivity().
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        final String trabajador = prefs.getString("trabajador", " ");
        mTtxt_Trabajadores.setText(trabajador);

        mButtonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mButtonEnviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Poner aquí para que se envíe el correo y si el check está activo
                // crear excel  a partir de los datos

                // aunque se cree el excel hay que mandar el correo
                TecnicoDAO tecnicoDAO;
                CocheDAO cocheDAO;
                ClienteDAO clienteDAO;
                DiarioDAO diarioDAO;

                tecnicoDAO = new TecnicoDAO(getActivity());
                cocheDAO = new CocheDAO(getActivity());
                clienteDAO = new ClienteDAO(getActivity());
                diarioDAO = new DiarioDAO(getActivity());

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

                Editable t = mTtxt_Trabajadores.getText();
                String tecnicoFormateado = t.toString().replace(" ", "");
                if (tecnicoFormateado.contains(",")) {
                    tec = tecnicoFormateado.split(",");
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String fecha = sdf.format(new Date());

                assert hora_ini != null;
                String[] h_ini = hora_ini.split(":");

                assert hora_fin != null;
                String[] h_fin = hora_fin.split(":");

                if (!TextUtils.isEmpty(clientenombre) && !TextUtils.isEmpty(hora_ini)
                        && !TextUtils.isEmpty(hora_fin) && !TextUtils.isEmpty(solucionnombre)
                        && !TextUtils.isEmpty(desplazamiento) && km_ini >= 0
                        && km_fin >= 0 && !TextUtils.isEmpty(cochematricula)
                        && !TextUtils.isEmpty(tecnico)) {
                    if (Integer.parseInt(h_ini[0]) <= Integer.parseInt(h_fin[0]))
                    // si la hora inicial es menor que la final ó
                    { // son iguales
                        if ((Integer.parseInt(h_ini[0]) == Integer.parseInt(h_fin[0]) &&
                                Integer.parseInt(h_ini[1]) < Integer.parseInt(h_fin[1]))
                                || (Integer.parseInt(h_ini[0]) < Integer.parseInt(h_fin[0]))) {
                            // si son iguales las horas e os minutos_ini < que minutos_fin
                            if (km_ini <= km_fin) { // si km iniciales mayores que acutales
                                Tecnico tecnico1;
                                List<Tecnico> tecnicos = new ArrayList<>();
                                if (tec == null) {
                                    tecnico1 = tecnicoDAO.getSinlgeTecnicoIdEntry(tecnico);
                                    tecnicos.add(tecnico1);
                                } else {
                                    for (String aTec : tec) {
                                        tecnico1 = tecnicoDAO.getSinlgeTecnicoIdEntry(aTec);
                                        tecnicos.add(tecnico1);
                                    }
                                }
                                Coche coche = cocheDAO.
                                        getSingleCocheMatriculaEntry(cochematricula);
                                Cliente cliente = clienteDAO.getSinlgeClienteEntry(clientenombre);
                                Diario createdDiario = diarioDAO.createDiario(fecha,
                                        caunombre,
                                        cliente.getcId(),
                                        solucionnombre,
                                        hora_ini,
                                        hora_fin,
                                        desplazamiento,
                                        Double.parseDouble(String.valueOf(km_ini)),
                                        Double.parseDouble(String.valueOf(km_fin)),
                                        coche.getId()
                                );
                                Tecnico_DiarioDAO tecnico_diarioDAO = new Tecnico_DiarioDAO(
                                        getActivity());
                                tecnico_diarioDAO.createTecnico_Diario(tecnicos,
                                        createdDiario.getId(), createdDiario.getFecha());
                                lanzarEmail(createdDiario, prefs);
                                Log.e(TAG, createdDiario.toString());
                                getActivity().finish();
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
            }
        });
    }

    private void lanzarEmail(Diario diario, SharedPreferences prefs) {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        String[] to = {"rruiz@satplus.es"};
        String subject = "" + diario.getCau() + " - " + prefs.getString("trabajador", " ");
        String body = diario.toString() + "\n Técnico = " + prefs.getString("trabajador", " ");
        i.putExtra(Intent.EXTRA_EMAIL, to);
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(i);
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
