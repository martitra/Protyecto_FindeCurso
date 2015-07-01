package com.example.soft12.parte_trabajo.activities.SlideScreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.model.Diario;

/**
 * Created by soft12 on 30/06/2015.
 */
public class SecondFragment extends Fragment {

    // TODO CAMPO NO VACÍO
    EditText mTxtSolucion;
    Diario diario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.second_frag, container, false);

        diario = new Diario();

        this.mTxtSolucion = (EditText) v.findViewById(R.id.editTextSolucion);

        mTxtSolucion.setText(diario.getSolucion().toString());

        Editable solucion = mTxtSolucion.getText();

        SharedPreferences.Editor editor = this.getActivity().
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE).edit();
        editor.putString("solucion", solucion.toString());
        editor.apply();

        return v;
    }



    public static SecondFragment newInstance(int text) {

        SecondFragment f = new SecondFragment();
        Bundle b = new Bundle();
        b.putInt("msg", text);

        f.setArguments(b);

        return f;
    }
}