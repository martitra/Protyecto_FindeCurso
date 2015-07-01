package com.example.soft12.parte_trabajo.activities.SlideScreen;

import android.content.Context;
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
import com.example.soft12.parte_trabajo.model.Diario;

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
        establecerValores();
        SharedPreferences prefs = this.getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String trabajador = prefs.getString("trabajador", " ");
        mTtxt_Trabajadores.setText(trabajador);

        mButtonEnviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Poner aquí para que se envíe el correo y si el check está activo
                // crear excel  a partir de los datos
                if (mCheck_FinalDia.isChecked()){
                    // es final de día para mandar excel
                }
                // aunque se cree el excel hay que mandar el correo

            }
        });

        return v;
    }

    private void establecerValores() {
        //vai fallar o tecnico porque eu poño o nombre e no diairo ten que ser o id
        mTtxt_Trabajadores.setText(diario.getTecnico().toString());

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
    }

    public static ForthFragment newInstance(int text) {

        ForthFragment f = new ForthFragment();
        Bundle b = new Bundle();
        b.putInt("msg", text);

        f.setArguments(b);

        return f;
    }
}
