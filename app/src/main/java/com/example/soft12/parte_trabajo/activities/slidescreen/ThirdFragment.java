package com.example.soft12.parte_trabajo.activities.slidescreen;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.adapter.Coche.SpinnerCochesAdapter;
import com.example.soft12.parte_trabajo.dao.CocheDAO;
import com.example.soft12.parte_trabajo.model.Coche;

import java.util.Calendar;
import java.util.List;

/**
 * Created by soft12 on 30/06/2015.
 */
public class ThirdFragment extends Fragment {

    EditText mTxtDesplazamiento, mTxtKmIni, mTxtKmFin;
    Spinner mSpinnerCoche;

    private CustomTimePickerDialog.OnTimeSetListener timeSetListener = new CustomTimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mTxtDesplazamiento.setText(String.format("%02d", hourOfDay) +
                    ":" + String.format("%02d", minute));
        }
    };

    public static ThirdFragment newInstance(int text) {

        ThirdFragment f = new ThirdFragment();
        Bundle b = new Bundle();
        b.putInt("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.third_frag, container, false);

        initViews(v);
        mTxtDesplazamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RangeTimePickerDialog timePickerDialog = new RangeTimePickerDialog(
                        getActivity(), timeSetListener,
                        Calendar.getInstance().get(Calendar.HOUR),
                        CustomTimePickerDialog.getRoundedMinute(
                                Calendar.getInstance().get(Calendar.MINUTE) +
                                        CustomTimePickerDialog.TIME_PICKER_INTERVAL),  true);
                timePickerDialog.setTitle("Duración Desplazamiento");
                timePickerDialog.setMin(0, 0);
                timePickerDialog.setMax(15,55);
                timePickerDialog.show();
            }
        });

        return v;
    }

    private void initViews(View v) {
        this.mTxtDesplazamiento = (EditText) v.findViewById(R.id.editText_desplazamiento);
        this.mTxtKmIni = (EditText) v.findViewById(R.id.editText_km_ini);
        this.mTxtKmFin = (EditText) v.findViewById(R.id.editText_km_fin);
        this.mSpinnerCoche = (Spinner) v.findViewById(R.id.spinner_coches);

        CocheDAO mCocheDao = new CocheDAO(getActivity());

        //fill the spinner with companies
        List<Coche> listCoches = mCocheDao.getAllCoches();
        if(listCoches != null) {
            SpinnerCochesAdapter mAdapter = new SpinnerCochesAdapter(getActivity(), listCoches);
            mSpinnerCoche.setAdapter(mAdapter);
        }

        mTxtKmFin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    establecerValores();
                }
            }
        });

        mSpinnerCoche.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                establecerValores();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void establecerValores() {

        Editable desplazamiento = mTxtDesplazamiento.getText();
        Editable kmini = mTxtKmIni.getText();
        Editable kmfin = mTxtKmFin.getText();
        Coche mSelectedCoche = (Coche) mSpinnerCoche.getSelectedItem();

        SharedPreferences.Editor editor = this.getActivity().
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE).edit();
        if (!TextUtils.isEmpty(desplazamiento) && !TextUtils.isEmpty(kmini)
                && !TextUtils.isEmpty(kmfin)) {
            editor.putString("desplazamiento", desplazamiento.toString());
            editor.putInt("km_ini", Integer.parseInt(String.valueOf(kmini)));
            editor.putInt("km_fin", Integer.parseInt(kmfin.toString()));
            if (mSelectedCoche != null) {
                editor.putString("coche", mSelectedCoche.getMatricula());
            }
            editor.apply();
        }
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

    public static class CustomTimePickerDialog extends TimePickerDialog {

        public static final int TIME_PICKER_INTERVAL = 5;
        private boolean mIgnoreEvent = false;

        public CustomTimePickerDialog(Context context, OnTimeSetListener callBack,
                                      int hourOfDay, int minute, boolean is24HourView) {
            super(context, callBack, hourOfDay, minute, is24HourView);
        }

        public static int getRoundedMinute(int minute) {
            if (minute % TIME_PICKER_INTERVAL != 0) {
                int minuteFloor = minute - (minute % TIME_PICKER_INTERVAL);
                minute = minuteFloor + (minute == minuteFloor + 1 ? TIME_PICKER_INTERVAL : 0);
                if (minute == 60) minute = 0;
            }

            return minute;
        }

        @Override
        public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
            super.onTimeChanged(timePicker, hourOfDay, minute);
            if (!mIgnoreEvent) {
                minute = getRoundedMinute(minute);
                mIgnoreEvent = true;
                timePicker.setCurrentMinute(minute);
                mIgnoreEvent = false;
            }
        }
    }
}
