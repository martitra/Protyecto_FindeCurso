package com.example.soft12.parte_trabajo.activities.Solucion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.dao.DBHelper;
import com.example.soft12.parte_trabajo.dao.SolucionDAO;
import com.example.soft12.parte_trabajo.model.Solucion;

/**
 * Created by soft12 on 04/06/2015.
 */
public class AddSolucionActivity extends Activity implements View.OnClickListener {

    public static final String TAG = "AddSolucionActivity";

    private EditText mTxtSolucionNombre;

    private SolucionDAO mSolucionDao;
    private Solucion solucionEdit;

    private boolean add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_solucion);

        initViews();

        solucionEdit = new Solucion();
        Bundle extras;
        extras = getIntent().getExtras();
        add = extras.getBoolean("add");
        if (add) {
            Toast.makeText(getBaseContext(), "ADD", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(),
                    extras.getString(DBHelper.COLUMN_SOLUCION_NOMBRE),
                    Toast.LENGTH_LONG).show();
            solucionEdit.setBundle(extras);
        }
        establecerValoresEditar();

        this.mSolucionDao = new SolucionDAO(this);
    }

    private void establecerValoresEditar() {

        mTxtSolucionNombre.setText(solucionEdit.getnNombre());
        int position;

        if (!add) {
            long i = solucionEdit.getcId();
            position = (int) i;
            Log.i("INFO", "Position=" + position);
        }
    }

    private void initViews() {
        this.mTxtSolucionNombre = (EditText) findViewById(R.id.txt_solucion_nombre);
        Button mBtnAdd = (Button) findViewById(R.id.btn_add);

        mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Editable solucionNombre = mTxtSolucionNombre.getText();
                if (!TextUtils.isEmpty(solucionNombre) ) {
                    // add the car to database
                    if(add) {
                        Solucion createdSolucion = mSolucionDao.createSolucion(solucionNombre.toString());
                        Log.d(TAG, "added solucion : " + createdSolucion.getnNombre());
                        Intent intent = new Intent();
                        intent.putExtra(ListSolucionesActivity.EXTRA_ADDED_SOLUCION, createdSolucion);
                        setResult(RESULT_OK, intent);
                        Toast.makeText(this, R.string.solucion_created_successfully, Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else {
                        solucionEdit.setcId(solucionEdit.getcId());
                        solucionEdit.setnNombre(mTxtSolucionNombre.getText().toString());
                        mSolucionDao.updateSolucion(solucionEdit);
                        Intent i = new Intent(this, ListSolucionesActivity.class);
                        startActivity(i);
                        Toast.makeText(this, R.string.solucion_edited_successfully, Toast.LENGTH_LONG).show();
                        finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSolucionDao.close();
    }

}
