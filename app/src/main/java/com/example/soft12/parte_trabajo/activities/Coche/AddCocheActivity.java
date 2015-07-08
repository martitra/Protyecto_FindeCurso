package com.example.soft12.parte_trabajo.activities.Coche;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.dao.CocheDAO;
import com.example.soft12.parte_trabajo.dao.DBHelper;
import com.example.soft12.parte_trabajo.model.Coche;

import java.util.regex.Pattern;

public class AddCocheActivity extends Activity implements OnClickListener {

    public static final String TAG = "AddCocheActivity";
    public static final Pattern MATRICULA_COCHE = Pattern.compile (
        "^[A-Z]{0,2}"+
        "\\s?"+
        "\\d{4}" +
        "\\s" +
        "([B-D]|[F-H]|[J-N]|[P-T]|[V-Z]){2,3}$"
    );
    private EditText mTxtCocheMatricula, mTxtCocheKilometros;
    private CocheDAO mCocheDao;
    private Coche cocheEdit;

    private boolean add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coche);

        initViews();

        cocheEdit = new Coche();
        Bundle extras;
        extras = getIntent().getExtras();
        add = extras.getBoolean("add");
        if (add) {
            Toast.makeText(getBaseContext(), "ADD", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(),
                    extras.getString(DBHelper.COLUMN_COCHE_MATRICULA),
                    Toast.LENGTH_LONG).show();
            cocheEdit.setBundle(extras);
        }
        establecerValoresEditar();

        this.mCocheDao = new CocheDAO(this);
    }

    private void establecerValoresEditar() {

        mTxtCocheMatricula.setText(cocheEdit.getMatricula());
        mTxtCocheKilometros.setText(String.valueOf(cocheEdit.getcKilometros()));
        int position;

        if (!add) {
            long i = cocheEdit.getId();
            position = (int) i;
            Log.i("INFO", "Position = " + position);
        }
    }

    private void initViews() {
        this.mTxtCocheMatricula = (EditText) findViewById(R.id.txt_coche_matricula);
        this.mTxtCocheKilometros = (EditText) findViewById(R.id.txt_coche_kilometros);
        Button mBtnAdd = (Button) findViewById(R.id.btn_add);

        mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Editable cocheMatricula = mTxtCocheMatricula.getText();
                Editable cocheKilometros = mTxtCocheKilometros.getText();
                if (!TextUtils.isEmpty(cocheMatricula) && !TextUtils.isEmpty(cocheKilometros) ) {
                    // add the car to database
                    final String matricula = mTxtCocheMatricula.getText().toString();
                    if(MATRICULA_COCHE.matcher(matricula).matches()) {
                        if(add) {
                            Coche createdCoche = mCocheDao.createCoche(
                                    cocheMatricula.toString(),
                                    Integer.parseInt(cocheKilometros.toString()));
                            Log.d(TAG, "added coche : " + createdCoche.getMatricula());
                            Intent intent = new Intent();
                            intent.putExtra(ListCochesActivity.EXTRA_ADDED_COCHE, createdCoche);
                            setResult(RESULT_OK, intent);
                            Toast.makeText(this, R.string.coche_created_successfully, Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {
                            cocheEdit.setId(cocheEdit.getId());
                            cocheEdit.setMatricula(mTxtCocheMatricula.getText().toString());
                            cocheEdit.setcKilometros(Integer.parseInt(mTxtCocheKilometros.getText().toString()));
                            mCocheDao.updateCoche(cocheEdit);
                            Intent i = new Intent(this, ListCochesActivity.class);
                            startActivity(i);
                            Toast.makeText(this, R.string.coche_edited_successfully, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                    else {
                        Toast.makeText(this, R.string.eror_matricula, Toast.LENGTH_LONG).show();
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
        mCocheDao.close();
    }

}
