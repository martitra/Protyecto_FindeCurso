package com.example.soft12.parte_trabajo.activities.Excel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.soft12.parte_trabajo.Excel.CreateExcel;
import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.dao.DiarioDAO;
import com.example.soft12.parte_trabajo.model.Diario;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import jxl.write.WriteException;

/**
 * Created by soft12 on 06/07/2015.
 */
public class EnviarExcel extends Activity {

    EditText fecha;
    Calendar calendar = Calendar.getInstance();

    /* DATE */
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.YEAR, year);

            fecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_excel);

        initViews();
    }

    private void initViews() {
        this.fecha = (EditText) findViewById(R.id.txt_fecha_excel);

        /* FECHA */
        this.fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(v.getContext(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        this.fecha.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                (calendar.get(Calendar.MONTH) + 1) + "/" +
                calendar.get(Calendar.YEAR));
    }

    public void Enviar(View view) {
        SharedPreferences prefs = this.
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        long tecnicoid = prefs.getLong("trabajadorid", 0);

        // Buscar resultado por fecha y por técnico logueado.
        DiarioDAO diarioDAO = new DiarioDAO(this);
        List<Diario> diarioArrayList = diarioDAO.getDateDiario(String.valueOf(fecha), tecnicoid);
        CreateExcel test = new CreateExcel();
        try {
            if (!diarioArrayList.isEmpty()) test.write(diarioArrayList);
        } catch (IOException | WriteException e) {
            e.printStackTrace();
        }
        System.out.println("Please check the result file under lars.xls ");
    }
}
