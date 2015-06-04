package com.example.soft12.parte_trabajo.activities.Diario;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.activities.CAU.AddCAUActivity;
import com.example.soft12.parte_trabajo.activities.Cliente.AddClienteActivity;
import com.example.soft12.parte_trabajo.activities.Solucion.AddSolucionActivity;
import com.example.soft12.parte_trabajo.adapter.CAU.SpinnerCAUAdapter;
import com.example.soft12.parte_trabajo.adapter.Cliente.SpinnerClientesAdapter;
import com.example.soft12.parte_trabajo.adapter.Solucion.SpinnerSolucionesAdapter;
import com.example.soft12.parte_trabajo.dao.CAUDAO;
import com.example.soft12.parte_trabajo.dao.ClienteDAO;
import com.example.soft12.parte_trabajo.dao.DBHelper;
import com.example.soft12.parte_trabajo.dao.DiarioDAO;
import com.example.soft12.parte_trabajo.dao.SolucionDAO;
import com.example.soft12.parte_trabajo.model.CAU;
import com.example.soft12.parte_trabajo.model.Cliente;
import com.example.soft12.parte_trabajo.model.Diario;
import com.example.soft12.parte_trabajo.model.Solucion;

import java.util.Calendar;
import java.util.List;

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
    private EditText mTxtViaje;
    private EditText mTxtKms;

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
    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE,minute);

            mTxtHoraIni.setText(hourOfDay + ":" + minute);
        }
    };

    private CAUDAO mCAUDao;
    private ClienteDAO mClienteDAO;
    private SolucionDAO mSolucionDAO;
    private DiarioDAO mDiarioDao;

    private SpinnerCAUAdapter mCAUAdapter;
    private SpinnerClientesAdapter mClienteAdapter;
    private SpinnerSolucionesAdapter mSolucionAdapter;
    private Diario diarioEdit;

    private boolean add;

    //public static final String EXTRA_SELECTED_REPOSTAJE_ID = "extra_key_selected_repostaje_id";

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


        this.mCAUDao = new CAUDAO(this);
        this.mClienteDAO = new ClienteDAO(this);
        this.mSolucionDAO = new SolucionDAO(this);
        this.mDiarioDao = new DiarioDAO(this);

        //fill the spinner with cau
        List<CAU> listCAU = mCAUDao.getAllCAU();
        if(listCAU != null) {
            mCAUAdapter = new SpinnerCAUAdapter(this, listCAU);
            spinnerCau.setAdapter(mCAUAdapter);
        }

        //fill the spinner with cliente
        List<Cliente> listCliente = mClienteDAO.getAllClientes();
        if(listCliente != null) {
            mClienteAdapter = new SpinnerClientesAdapter(this, listCliente);
            spinnerCliente.setAdapter(mClienteAdapter);
        }

        //fill the spinner with solucion
        List<Solucion> listSolucion = mSolucionDAO.getAllSoluciones();
        if(listSolucion != null) {
            mSolucionAdapter = new SpinnerSolucionesAdapter(this, listSolucion);
            spinnerSolucion.setAdapter(mSolucionAdapter);
        }

        establecerValoresEditar();
    }

    private void establecerValoresEditar() {
        mTxtFecha.setText(diarioEdit.getFecha());
        mTxtHoraIni.setText(diarioEdit.getHoraIni());
        mTxtHoraFin.setText(diarioEdit.getHoraFin());
        mTxtViaje.setText(String.valueOf(diarioEdit.getViaje()));
        mTxtKms.setText(String.valueOf(diarioEdit.getKms()));

        //int position = 0;
        int posCAU = 0;
        int posCliente = 0;
        int posSolucion = 0;
        if (!add) {
            posCAU = mCAUAdapter.getPositionById(diarioEdit.getCau().getCauId());
            Log.i("INFO", "Position=" + posCAU);
            posCliente = mClienteAdapter.getPositionById(diarioEdit.getCliente().getcId());
            Log.i("INFO", "Position=" + posCliente);
            posSolucion = mSolucionAdapter.getPositionById(diarioEdit.getSolucion().getcId());
            Log.i("INFO", "Position=" + posSolucion);
        }
        spinnerCau.setSelection(posCAU);
        spinnerCliente.setSelection(posCliente);
        spinnerSolucion.setSelection(posSolucion);
    }

    private void initViews() {
        this.mTxtFecha = (EditText) findViewById(R.id.txt_fecha);
        this.mTxtHoraIni = (EditText) findViewById(R.id.txt_hora_ini);
        this.mTxtHoraFin = (EditText) findViewById(R.id.txt_hora_fin);
        this.mTxtViaje = (EditText) findViewById(R.id.txt_viaje);
        this.mTxtKms  = (EditText) findViewById(R.id.txt_kms);
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
                new TimePickerDialog(v.getContext(), time , myCalendar.get(Calendar.HOUR),
                        myCalendar.get(Calendar.MINUTE),true).show();
            }
        });
        mTxtHoraIni.setText(myCalendar.get(Calendar.HOUR_OF_DAY) + ":" + myCalendar.get(Calendar.MINUTE));
        /* HORA FIN */
        this.mTxtHoraFin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new TimePickerDialog(v.getContext(), time , myCalendar.get(Calendar.HOUR),
                        myCalendar.get(Calendar.MINUTE),true).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {

            case R.id.add_cau:
                lanzarNuevoCAU();
                break;
            case R.id.add_cliente:
                lanzarNuevoCliente();
                break;
            case R.id.add_solucion:
                lanzarNuevaSolucion();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void lanzarNuevoCAU() {
        Bundle extras = new Bundle();
        // TODO Auto-generated method stub
        extras.clear();
        extras.putBoolean("add", true);
        Intent i = new Intent(this, AddCAUActivity.class);
        i.putExtras(extras);// pasar add
        startActivity(i);
    }

    private void lanzarNuevoCliente() {
        Bundle extras = new Bundle();
        // TODO Auto-generated method stub
        extras.clear();
        extras.putBoolean("add", true);
        Intent i = new Intent(this, AddClienteActivity.class);
        i.putExtras(extras);// pasar add
        startActivity(i);
    }

    private void lanzarNuevaSolucion() {
        Bundle extras = new Bundle();
        // TODO Auto-generated method stub
        extras.clear();
        extras.putBoolean("add", true);
        Intent i = new Intent(this, AddSolucionActivity.class);
        i.putExtras(extras);// pasar add
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Editable fecha = mTxtFecha.getText();
                Editable hora_ini = mTxtHoraIni.getText();
                Editable hora_fin = mTxtHoraFin.getText();
                Editable viaje = mTxtViaje.getText();
                Editable kms = mTxtKms.getText();

                CAU mSelectedCAU = (CAU) spinnerCau.getSelectedItem();
                Cliente mSelectedCliente = (Cliente) spinnerCliente.getSelectedItem();
                Solucion mSelectedSolucion = (Solucion) spinnerSolucion.getSelectedItem();
                if (!TextUtils.isEmpty(fecha) && !TextUtils.isEmpty(hora_ini)
                        && !TextUtils.isEmpty(hora_fin) && !TextUtils.isEmpty(viaje)
                        && !TextUtils.isEmpty(kms) && mSelectedCliente != null
                        && mSelectedCAU != null && mSelectedSolucion != null){
                    //if() { poner aquí para comparar las fehca ini < fecha fin
                        if (add) {
                            // add the diario to database
                            Diario createdDiario = mDiarioDao.createDiario(fecha.toString(),
                                    mSelectedCAU.getCauId(),
                                    mSelectedCliente.getcId(),
                                    mSelectedSolucion.getcId(),
                                    hora_ini.toString(),
                                    hora_fin.toString(),
                                    Double.parseDouble(viaje.toString()),
                                    Double.parseDouble(kms.toString()));
                            Toast.makeText(this, R.string.diario_created_successfully, Toast.LENGTH_LONG).show();
                            Log.d(TAG, "added repostaje : " + createdDiario.getFecha() + " "
                                    + createdDiario.getViaje() + ", repostaje.cocheId : " + createdDiario.getCliente().getnNombre());
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Diario editDiario = new Diario();
                            editDiario.setId(diarioEdit.getId());
                            editDiario.setFecha(mTxtFecha.getText().toString());
                            editDiario.setHoraIni(mTxtHoraIni.getText().toString());
                            editDiario.setHoraFin(mTxtHoraFin.getText().toString());
                            editDiario.setViaje(Double.parseDouble(mTxtViaje.getText().toString()));
                            editDiario.setKms(Double.parseDouble(mTxtKms.getText().toString()));
                            editDiario.setCau((CAU) spinnerCau.getSelectedItem());
                            editDiario.setCliente((Cliente) spinnerCliente.getSelectedItem());
                            editDiario.setSolucion((Solucion) spinnerSolucion.getSelectedItem());
                            mDiarioDao.updateDiario(editDiario);
                            Intent i = new Intent(this, ListDiarioActivity.class);
                            startActivity(i);
                            Toast.makeText(this, R.string.diario_edited_successfully, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    //}

                }
                else {
                    Toast.makeText(this, R.string.empty_fields_message, Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCAUDao.close();
        mClienteDAO.close();
        mSolucionDAO.close();
    }

}
