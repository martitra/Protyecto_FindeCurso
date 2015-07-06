package com.example.soft12.parte_trabajo.activities.Diario;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.dao.ClienteDAO;
import com.example.soft12.parte_trabajo.dao.DBHelper;
import com.example.soft12.parte_trabajo.dao.DiarioDAO;
import com.example.soft12.parte_trabajo.model.Cliente;
import com.example.soft12.parte_trabajo.model.Diario;

import java.util.Calendar;

/**
 * Created by soft12 on 04/06/2015.
 */
public class AddDiarioActivity extends Activity implements View.OnClickListener {

    public static final String TAG = "AddDiarioActivity";

    private EditText mTxtFecha;
    private Spinner spinnerCau;
    private Spinner spinnerCliente;
    private Spinner spinnerSolucion;
    private EditText mTxtHoraIni;
    private EditText mTxtHoraFin;
    private EditText mTxtDesplazamiento;
    private EditText mTxtKmini;
    private EditText mTxtKmFin;

    Calendar myCalendar = Calendar.getInstance();

    /* DATE */
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            mTxtFecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        }

    };

    /* TIME */
    TimePickerDialog.OnTimeSetListener timeIni = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE,minute);

            mTxtHoraIni.setText(hourOfDay + ":" + minute);
        }
    };
    /* TIME */
    TimePickerDialog.OnTimeSetListener timeFin = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay+1);
            myCalendar.set(Calendar.MINUTE,minute);

            mTxtHoraFin.setText(hourOfDay + ":" + minute);
        }
    };


    private ClienteDAO mClienteDAO;

    private DiarioDAO mDiarioDao;

    private Diario diarioEdit;

    private boolean add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diario);

        initViews();

        diarioEdit = new Diario();
        Bundle extras;
        extras = getIntent().getExtras();
        add = extras.getBoolean("add");
        if (add) {
            Toast.makeText(getBaseContext(), "ADD", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(),
                    extras.getString(DBHelper.COLUMN_DIARIO_FECHA),
                    Toast.LENGTH_LONG).show();
            diarioEdit.setBundle(extras);
        }

        this.mClienteDAO = new ClienteDAO(this);
        this.mDiarioDao = new DiarioDAO(this);

        establecerValoresEditar();
    }

    private void establecerValoresEditar() {
        mTxtFecha.setText(diarioEdit.getFecha());
        mTxtHoraIni.setText(diarioEdit.getHoraIni());
        mTxtHoraFin.setText(diarioEdit.getHoraFin());
        mTxtDesplazamiento.setText(diarioEdit.getDesplazamiento());
        mTxtKmini.setText(String.valueOf(diarioEdit.getKmIni()));
        mTxtKmFin.setText(String.valueOf(diarioEdit.getKmFin()));

        //int position = 0;
        int posCAU = 0;
        int posCliente = 0;
        int posSolucion = 0;
        if (!add) {
            /*posCAU = mCAUAdapter.getPositionById(diarioEdit.getCau().getCauId());
            Log.i("INFO", "Position CAU = " + posCAU);
            posCliente = mClienteAdapter.getPositionById(diarioEdit.getCliente().getcId());
            Log.i("INFO", "Position Cliente = " + posCliente);
            posSolucion = mSolucionAdapter.getPositionById(diarioEdit.getSolucion().getcId());
            Log.i("INFO", "Position Soluión = " + posSolucion);*/
        }
        spinnerCau.setSelection(posCAU);
        spinnerCliente.setSelection(posCliente);
        spinnerSolucion.setSelection(posSolucion);
    }

    private void initViews() {
        this.mTxtFecha = (EditText) findViewById(R.id.txt_fecha);
        this.mTxtHoraIni = (EditText) findViewById(R.id.txt_hora_ini);
        this.mTxtHoraFin = (EditText) findViewById(R.id.txt_hora_fin);
        this.mTxtDesplazamiento = (EditText) findViewById(R.id.txt_viaje);
        this.mTxtKmini  = (EditText) findViewById(R.id.txt_kmini);
        this.mTxtKmFin = (EditText) findViewById(R.id.txt_kmfin);
        this.spinnerCau = (Spinner) findViewById(R.id.spinner_cau);
        this.spinnerCliente = (Spinner) findViewById(R.id.spinner_cliente);
        this.spinnerSolucion = (Spinner) findViewById(R.id.spinner_solucion);
        Button mBtnAdd = (Button) findViewById(R.id.btn_add);

        /* FECHA */
        this.mTxtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(v.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        this.mTxtFecha.setText(myCalendar.get(Calendar.DAY_OF_MONTH) + "/" +
                (myCalendar.get(Calendar.MONTH) + 1) + "/" +
                myCalendar.get(Calendar.YEAR));
        /* HORA INICIO */
        this.mTxtHoraIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(v.getContext(), timeIni , myCalendar.get(Calendar.HOUR),
                        myCalendar.get(Calendar.MINUTE),true).show();
            }
        });
        mTxtHoraIni.setText(myCalendar.get(Calendar.HOUR_OF_DAY) + ":" + myCalendar.get(Calendar.MINUTE));
        /* HORA FIN */
        this.mTxtHoraFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(v.getContext(), timeFin, myCalendar.get(Calendar.HOUR),
                        myCalendar.get(Calendar.MINUTE), true).show();
            }
        });
        mTxtHoraFin.setText(myCalendar.get(Calendar.HOUR_OF_DAY) + ":" + myCalendar.get(Calendar.MINUTE));

        mBtnAdd.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_diario, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Editable fecha = mTxtFecha.getText();
                Editable hora_ini = mTxtHoraIni.getText();
                Editable hora_fin = mTxtHoraFin.getText();
                Editable viaje = mTxtDesplazamiento.getText();
                Editable kmini = mTxtKmini.getText();
                Editable kmfin = mTxtKmFin.getText();

                String hora_inicial = mTxtHoraIni.getText().toString();
                String [] h_ini = hora_inicial.split(":");

                String hora_final = mTxtHoraFin.getText().toString();
                String [] h_fin = hora_final.split(":");

                Cliente mSelectedCliente = (Cliente) spinnerCliente.getSelectedItem();
                if (!TextUtils.isEmpty(fecha) && !TextUtils.isEmpty(hora_ini)
                        && !TextUtils.isEmpty(hora_fin) && !TextUtils.isEmpty(viaje)
                        && !TextUtils.isEmpty(kmini) && !TextUtils.isEmpty(kmfin)
                        && mSelectedCliente != null) {
                    if(Integer.parseInt(h_ini[0]) < Integer.parseInt(h_fin[0]) // si la hora inicial es menor que la final ó
                            || Integer.parseInt(h_ini[0]) == Integer.parseInt(h_fin[0])) { // son iguales
                        if(Integer.parseInt(h_ini[0]) == Integer.parseInt(h_fin[0])) { // si son iguales las horas
                            if (Integer.parseInt(h_ini[1]) < Integer.parseInt(h_fin[1])) { // los minutos ini tienen que ser menores que fin
                                if (add) {
                                    SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                                    Long trabajador = prefs.getLong("trabajadorid", 0);
                                    // add the diario to database
                                    /*Diario createdDiario = mDiarioDao.createDiario(fecha.toString(),
                                            mSelectedCAU.getcNombre(),
                                            mSelectedCliente.getnNombre(),
                                            mSelectedSolucion.getnNombre(),
                                            hora_ini.toString(),
                                            hora_fin.toString(),
                                            Double.parseDouble(viaje.toString()),
                                            Double.parseDouble(kmini.toString()),
                                            Double.parseDouble(kmfin.toString()),
                                            trabajador);
                                    Toast.makeText(this, R.string.diario_created_successfully, Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "added diario : " + createdDiario.getFecha() + " ");
                                    setResult(RESULT_OK);
                                    // aquí crear un mail para la incidencia
                                    lanzarEmail(createdDiario);*/
                                    finish();
                                } else {
                                    Diario editDiario = new Diario();
                                    editDiario.setId(diarioEdit.getId());
                                    editDiario.setFecha(mTxtFecha.getText().toString());
                                    editDiario.setHoraIni(mTxtHoraIni.getText().toString());
                                    editDiario.setHoraFin(mTxtHoraFin.getText().toString());
                                    editDiario.setDesplazamiento(mTxtDesplazamiento.getText().toString());
                                    editDiario.setKmIni(Double.parseDouble(mTxtKmini.getText().toString()));
                                    editDiario.setKmFin(Double.parseDouble(mTxtKmFin.getText().toString()));
                                    //editDiario.setCau((CAU) spinnerCau.getSelectedItem());
                                    //editDiario.setCliente((Cliente) spinnerCliente.getSelectedItem());
                                    //editDiario.setSolucion((Solucion) spinnerSolucion.getSelectedItem());
                                    mDiarioDao.updateDiario(editDiario);
                                    Intent i = new Intent(this, ListDiarioActivity.class);
                                    startActivity(i);
                                    Toast.makeText(this, R.string.diario_edited_successfully, Toast.LENGTH_LONG).show();
                                    setResult(RESULT_OK);
                                    // aquí crear un mail para la incidencia
                                    lanzarEmail(editDiario);
                                    finish();
                                }
                            }
                            else {
                                Toast.makeText(this, R.string.minutos_ini_mayor, Toast.LENGTH_LONG).show();
                            }
                        }
                        else { // si no son iguales pero la fecha ini es menor que la fecha fin entra
                            if (add) {
                                SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                                Long trabajador = prefs.getLong("trabajadorid", 0);
                                // add the diario to database
                                /*Diario createdDiario = mDiarioDao.createDiario(fecha.toString(),
                                        mSelectedCAU.getcNombre(),
                                        mSelectedCliente.getnNombre(),
                                        mSelectedSolucion.getnNombre(),
                                        hora_ini.toString(),
                                        hora_fin.toString(),
                                        Double.parseDouble(viaje.toString()),
                                        Double.parseDouble(kmini.toString()),
                                        Double.parseDouble(kmfin.toString()),
                                        trabajador);
                                Toast.makeText(this, R.string.diario_created_successfully, Toast.LENGTH_LONG).show();
                                Log.d(TAG, "added repostaje : " + createdDiario.getFecha() + " ");
                                setResult(RESULT_OK);
                                // aquí crear un mail para la incidencia
                                lanzarEmail(createdDiario);*/
                                finish();
                            } else {
                                Diario editDiario = new Diario();
                                editDiario.setId(diarioEdit.getId());
                                editDiario.setFecha(mTxtFecha.getText().toString());
                                editDiario.setHoraIni(mTxtHoraIni.getText().toString());
                                editDiario.setHoraFin(mTxtHoraFin.getText().toString());
                                editDiario.setDesplazamiento(mTxtDesplazamiento.getText().toString());
                                editDiario.setKmIni(Double.parseDouble(mTxtKmini.getText().toString()));
                                editDiario.setKmFin(Double.parseDouble(mTxtKmFin.getText().toString()));
                                //editDiario.setCau((CAU) spinnerCau.getSelectedItem());
                                //editDiario.setCliente((Cliente) spinnerCliente.getSelectedItem());
                                //editDiario.setSolucion((Solucion) spinnerSolucion.getSelectedItem());
                                mDiarioDao.updateDiario(editDiario);
                                Intent i = new Intent(this, ListDiarioActivity.class);
                                startActivity(i);
                                Toast.makeText(this, R.string.diario_edited_successfully, Toast.LENGTH_LONG).show();
                                // aquí crear un mail para la incidencia
                                lanzarEmail(editDiario);
                                finish();
                            }
                        }
                    }
                    else {
                        Toast.makeText(this, R.string.hora_ini_mayor, Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(this, R.string.empty_fields_message, Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }
    //
    private void lanzarEmail(Diario diario) {
        // TODO Auto-generated method stub

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
