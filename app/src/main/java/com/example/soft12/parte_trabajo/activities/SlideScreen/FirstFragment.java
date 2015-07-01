package com.example.soft12.parte_trabajo.activities.SlideScreen;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.model.Diario;

import java.util.Calendar;

/**
 * Created by soft12 on 30/06/2015.
 */
public class FirstFragment extends Fragment {

    public EditText mTxtHoraIni, mTxtHoraFin, mTxtCAU, mTxtCliente;
    Diario diario;
    Calendar myCalendar = Calendar.getInstance();

    // TODO COMPROBAR FEHCA INI MENOR QUE FECHA FIN
    // TODO QUE LOS CAMPOS NO ESTEAN VACÍOS

    /* TIME */
    TimePickerDialog.OnTimeSetListener timeIni = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            mTxtHoraIni.setText(String.format("%02d:%02d", hourOfDay, minute));
        }
    };
    /* TIME */
    TimePickerDialog.OnTimeSetListener timeFin = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay+1);
            myCalendar.set(Calendar.MINUTE,minute);
            mTxtHoraFin.setText(String.format("%02d:%02d", hourOfDay, minute));
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_frag, container, false);
        diario = new Diario();
        initViews(v);
        establecerValores();

        return v;
    }

    private void establecerValores() {
        mTxtCAU.setText(diario.getCau().toString());
        mTxtCliente.setText(diario.getCliente().toString());
        mTxtHoraIni.setText(diario.getHoraIni());
        mTxtHoraFin.setText(diario.getHoraFin());

        Editable cau = mTxtCAU.getText();
        Editable hora_ini = mTxtHoraIni.getText();
        Editable hora_fin = mTxtHoraFin.getText();
        Editable cliente = mTxtCliente.getText();

        SharedPreferences.Editor editor = this.getActivity().
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE).edit();
        editor.putString("cau", cau.toString());
        editor.putString("hora_ini", hora_ini.toString());
        editor.putString("hora_fin", hora_fin.toString());
        editor.putString("cliente", cliente.toString());
        editor.apply();
    }

    private void initViews(View v) {

        this.mTxtHoraIni = (EditText) v.findViewById(R.id.txt_hora_ini);
        this.mTxtHoraFin = (EditText) v.findViewById(R.id.txt_hora_fin);
        this.mTxtCliente = (EditText) v.findViewById(R.id.editText_cliente);
        this.mTxtCAU = (EditText) v.findViewById(R.id.editText_cau);

        /* HORA INICIO */
        this.mTxtHoraIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(v.getContext(), timeIni, myCalendar.get(Calendar.HOUR),
                        myCalendar.get(Calendar.MINUTE), true).show();
            }
        });

        mTxtHoraIni.setText(String.format("%02d:%02d", myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE)));

        /* HORA FIN */
        this.mTxtHoraFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(v.getContext(), timeFin, myCalendar.get(Calendar.HOUR),
                        myCalendar.get(Calendar.MINUTE), true).show();
            }
        });
        mTxtHoraFin.setText(String.format("%02d:%02d", myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE)));
    }

    public static FirstFragment newInstance(int pos) {

        FirstFragment f = new FirstFragment();
        Bundle b = new Bundle();
        b.putInt("msg", pos);

        f.setArguments(b);

        return f;
    }

}
