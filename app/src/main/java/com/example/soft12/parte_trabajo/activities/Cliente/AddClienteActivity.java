package com.example.soft12.parte_trabajo.activities.Cliente;

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
import com.example.soft12.parte_trabajo.dao.ClienteDAO;
import com.example.soft12.parte_trabajo.dao.DBHelper;
import com.example.soft12.parte_trabajo.model.Cliente;

/**
 * Created by soft12 on 04/06/2015.
 */
public class AddClienteActivity  extends Activity implements View.OnClickListener{

    public static final String TAG = "AddClienteActivity";

    private EditText mTxtClienteNombre;

    private ClienteDAO mClienteDao;
    private Cliente clienteEdit;

    private boolean add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cliente);

        initViews();

        clienteEdit = new Cliente();
        Bundle extras;
        extras = getIntent().getExtras();
        add = extras.getBoolean("add");
        if (add) {
            Toast.makeText(getBaseContext(), "ADD", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(),
                    extras.getString(DBHelper.COLUMN_CLIENTE_NOMBRE),
                    Toast.LENGTH_LONG).show();
            clienteEdit.setBundle(extras);
        }
        establecerValoresEditar();

        this.mClienteDao = new ClienteDAO(this);
    }

    private void establecerValoresEditar() {

        mTxtClienteNombre.setText(clienteEdit.getnNombre());
        int position;

        if (!add) {
            long i = clienteEdit.getcId();
            position = (int) i;
            Log.i("INFO", "Position=" + position);
        }
    }

    private void initViews() {
        this.mTxtClienteNombre = (EditText) findViewById(R.id.txt_cliente_nombre);
        Button mBtnAdd = (Button) findViewById(R.id.btn_add);

        mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Editable clienteNombre = mTxtClienteNombre.getText();
                if (!TextUtils.isEmpty(clienteNombre) ) {
                    // add the car to database
                    if(add) {
                        Cliente createdCliente = mClienteDao.createCliente(clienteNombre.toString());
                        Log.d(TAG, "added coche : " + createdCliente.getnNombre());
                        Intent intent = new Intent();
                        intent.putExtra(ListClientesActivity.EXTRA_ADDED_CLIENTE, createdCliente);
                        setResult(RESULT_OK, intent);
                        Toast.makeText(this, R.string.cliente_created_successfully, Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else {
                        clienteEdit.setcId(clienteEdit.getcId());
                        clienteEdit.setnNombre(mTxtClienteNombre.getText().toString());
                        mClienteDao.updateCliente(clienteEdit);
                        Intent i = new Intent(this, ListClientesActivity.class);
                        startActivity(i);
                        Toast.makeText(this, R.string.cliente_edited_successfully, Toast.LENGTH_LONG).show();
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
        mClienteDao.close();
    }

}
