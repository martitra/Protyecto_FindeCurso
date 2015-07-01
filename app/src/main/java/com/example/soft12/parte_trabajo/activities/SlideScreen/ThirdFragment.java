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
import android.widget.Spinner;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.adapter.Coche.SpinnerCochesAdapter;
import com.example.soft12.parte_trabajo.dao.CocheDAO;
import com.example.soft12.parte_trabajo.model.Coche;
import com.example.soft12.parte_trabajo.model.Diario;

import java.util.List;

/**
 * Created by soft12 on 30/06/2015.
 */
public class ThirdFragment extends Fragment {

    // TODO CAMPOS NO VACÍOS
    // TODO KM INI MENOR QUE KM FIN

    EditText mTxtDesplazamiento, mTxtKmIni, mTxtKmFin;
    Spinner mSpinnerCoche;

    private SpinnerCochesAdapter mAdapter;
    private CocheDAO mCocheDao;
    Diario diario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.third_frag, container, false);
        diario = new Diario();

        initViews(v);
        establecerValores();
        return v;
    }

    private void initViews(View v) {
        this.mTxtDesplazamiento = (EditText) v.findViewById(R.id.editText_desplazamiento);
        this.mTxtKmIni = (EditText) v.findViewById(R.id.editText_km_ini);
        this.mTxtKmFin = (EditText) v.findViewById(R.id.editText_km_fin);
        this.mSpinnerCoche = (Spinner) v.findViewById(R.id.spinner_coches);

        this.mCocheDao = new CocheDAO(getActivity());

        //fill the spinner with companies
        List<Coche> listCoches = mCocheDao.getAllCoches();
        if(listCoches != null) {
            mAdapter = new SpinnerCochesAdapter(getActivity(), listCoches);
            mSpinnerCoche.setAdapter(mAdapter);
            //mSpinnerCoche.setOnItemSelectedListener(this);
        }
    }

    private void establecerValores() {
        mTxtDesplazamiento.setText(diario.getViaje());
        mTxtKmIni.setText(diario.getKmIni().toString());
        mTxtKmFin.setText(diario.getKmFin().toString());
        mSpinnerCoche.setSelection(0);

        Editable desplazamiento = mTxtDesplazamiento.getText();
        Editable kmini = mTxtKmIni.getText();
        Editable kmfin = mTxtKmFin.getText();
        Coche mSelectedCoche = (Coche) mSpinnerCoche.getSelectedItem();

        SharedPreferences.Editor editor = this.getActivity().
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE).edit();
        editor.putString("desplazamiento", desplazamiento.toString());
        editor.putString("km_ini", kmini.toString());
        editor.putString("km_fin", kmfin.toString());
        editor.putString("coche", mSelectedCoche.toString());
        editor.apply();
    }

    public static ThirdFragment newInstance(int text) {

        ThirdFragment f = new ThirdFragment();
        Bundle b = new Bundle();
        b.putInt("msg", text);

        f.setArguments(b);

        return f;
    }
}
