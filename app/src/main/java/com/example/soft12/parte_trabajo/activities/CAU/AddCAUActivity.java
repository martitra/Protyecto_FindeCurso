package com.example.soft12.parte_trabajo.activities.CAU;

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
import com.example.soft12.parte_trabajo.dao.CAUDAO;
import com.example.soft12.parte_trabajo.dao.DBHelper;
import com.example.soft12.parte_trabajo.model.CAU;

/**
 * Created by soft12 on 02/06/2015.
 */
public class AddCAUActivity extends Activity implements View.OnClickListener {

    public static final String TAG = "AddCocheActivity";

    private EditText mTxtCAUNombre;

    private CAUDAO mCAUDAO;
    private CAU cauEdit;

    private boolean add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cau);

        initViews();

        cauEdit = new CAU();
        Bundle extras;
        extras = getIntent().getExtras();
        add = extras.getBoolean("add");
        if (add) {
            Toast.makeText(getBaseContext(), "ADD", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(),
                    extras.getString(DBHelper.COLUMN_CAU_NOMBRE),
                    Toast.LENGTH_LONG).show();
            cauEdit.setBundle(extras);
        }
        establecerValoresEditar();

        this.mCAUDAO = new CAUDAO(this);
    }

    private void establecerValoresEditar() {

        mTxtCAUNombre.setText(cauEdit.getcNombre());
        int position;

        if (!add) {
            long i = cauEdit.getCauId();
            position = (int) i;
            Log.i("INFO", "Position=" + position);
        }
    }

    private void initViews() {
        this.mTxtCAUNombre = (EditText) findViewById(R.id.txt_cau_nombre);
        Button mBtnAdd = (Button) findViewById(R.id.btn_add);

        mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Editable cauNombre = mTxtCAUNombre.getText();
                if (!TextUtils.isEmpty(cauNombre) ) {
                    // add the car to database
                    if(add) {
                        CAU createdCAU = mCAUDAO.createCAU(cauNombre.toString());
                        Log.d(TAG, "added coche : " + createdCAU.getcNombre());
                        Intent intent = new Intent();
                        intent.putExtra(ListCAUActivity.EXTRA_ADDED_CAU, createdCAU);
                        setResult(RESULT_OK, intent);
                        Toast.makeText(this, R.string.cau_created_successfully, Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else {
                        cauEdit.setCauId(cauEdit.getCauId());
                        cauEdit.setcNombre(mTxtCAUNombre.getText().toString());
                        mCAUDAO.updateCAU(cauEdit);
                        Intent i = new Intent(this, ListCAUActivity.class);
                        startActivity(i);
                        Toast.makeText(this, R.string.cau_created_successfully, Toast.LENGTH_LONG).show();
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
        mCAUDAO.close();
    }

}
