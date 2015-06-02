package com.example.soft12.parte_trabajo.activities.Repostaje;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.adapter.Coche.SpinnerCochesAdapter;
import com.example.soft12.parte_trabajo.dao.CocheDAO;
import com.example.soft12.parte_trabajo.dao.DBHelper;
import com.example.soft12.parte_trabajo.dao.RepostajeDAO;
import com.example.soft12.parte_trabajo.model.Coche;
import com.example.soft12.parte_trabajo.model.Repostaje;

import java.util.List;

public class AddRepostajeActivity extends Activity implements OnClickListener, OnItemSelectedListener {

	public static final String TAG = "AddRepostajeActivity";

	private EditText mTxtFecha;
	private EditText mTxtEuros;
	private EditText mTxtEuros_Litro;
	private EditText mTxtLitros;
	private Spinner mSpinnerCoche;

	private CocheDAO mCocheDao;
	private RepostajeDAO mRepostajeDao;
	
	private Coche mSelectedCoche;
	private SpinnerCochesAdapter mAdapter;
    private Repostaje repostajeEdit;

    private Double euros;
    private Double eur_lit;

    private boolean add;

	public static final String EXTRA_SELECTED_REPOSTAJE_ID = "extra_key_selected_repostaje_id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_repostaje);

		initViews();

        repostajeEdit = new Repostaje();
        Bundle extras;
        extras = getIntent().getExtras();
        add = extras.getBoolean("add");
        if (add) {
            Toast.makeText(getBaseContext(), "ADD", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(),
                    extras.getString(DBHelper.COLUMN_REPOSTAJE_FECHA),
                    Toast.LENGTH_LONG).show();
            repostajeEdit.setBundle(extras);
        }


		this.mCocheDao = new CocheDAO(this);
		this.mRepostajeDao = new RepostajeDAO(this);
		
		//fill the spinner with companies
		List<Coche> listCoches = mCocheDao.getAllCoches();
		if(listCoches != null) {
			mAdapter = new SpinnerCochesAdapter(this, listCoches);
			mSpinnerCoche.setAdapter(mAdapter);
			//mSpinnerCoche.setOnItemSelectedListener(this);
		}
        establecerValoresEditar();
	}

    private void establecerValoresEditar() {
        mTxtFecha.setText(repostajeEdit.getFecha());
        mTxtEuros.setText(String.valueOf(repostajeEdit.getEuros()));
        mTxtEuros_Litro.setText(String.valueOf(repostajeEdit.getEuros_litro()));
        mTxtLitros.setText(String.valueOf(repostajeEdit.getLitros()));

        int position = 0;

        if (!add) {
            position = mAdapter.getPositionById(repostajeEdit.getCoche().getId());
            Log.i("INFO", "Position=" + position);
        }
        mSpinnerCoche.setSelection(position);
    }

    private void initViews() {
		this.mTxtFecha = (EditText) findViewById(R.id.txt_fecha);
		this.mTxtEuros = (EditText) findViewById(R.id.txt_euros);
		this.mTxtEuros_Litro = (EditText) findViewById(R.id.txt_euros_litro);
		this.mTxtLitros = (EditText) findViewById(R.id.txt_litros);
		this.mSpinnerCoche = (Spinner) findViewById(R.id.spinner_coches);
		Button mBtnAdd = (Button) findViewById(R.id.btn_add);

        // para cuando pongamos algo en euros
        this.mTxtEuros.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().trim().equals("")){
                    euros = Double.parseDouble(s.toString());
                }
            }
        });

        //para cuando pongamos algo en euros/litro
        this.mTxtEuros_Litro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // si no está vacío el campo euros/litros
                if(!s.toString().trim().equals("")){
                    eur_lit = Double.parseDouble(s.toString());
                }
                // preguntamos si euros y euros/litro están vacíos; si no lo están
                if (euros!= null && eur_lit != null){
                    try {
                        // calculamos
                        calculate();
                    }catch (NumberFormatException nfe){
                        nfe.getMessage();
                    }
                }
            }
        });

		mBtnAdd.setOnClickListener(this);
	}

	private void calculate() {
        // totalLitros será los euros * euros/litro
        double totalLitros = euros*eur_lit;
        // cuando lo tengamos lo ponemos en litros
        mTxtLitros.setText(String.valueOf(totalLitros));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add:
			Editable fecha = mTxtFecha.getText();
			Editable euros = mTxtEuros.getText();
			Editable euros_litro = mTxtEuros_Litro.getText();
			Editable litros = mTxtLitros.getText();
			mSelectedCoche = (Coche) mSpinnerCoche.getSelectedItem();
			if (!TextUtils.isEmpty(fecha) && !TextUtils.isEmpty(euros)
					&& !TextUtils.isEmpty(euros_litro) && !TextUtils.isEmpty(litros)
					&& mSelectedCoche != null) {
                if(add) {
                    // add the repostaje to database
                    Repostaje createdRepostaje = mRepostajeDao.createRepostaje(fecha.toString(),
                            Double.parseDouble(euros.toString()),
                            Double.parseDouble(euros_litro.toString()),
                            Double.parseDouble(litros.toString()),
                            mSelectedCoche.getId());
                    Toast.makeText(this, R.string.repostaje_created_successfully, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "added repostaje : " + createdRepostaje.getFecha() + " "
                            + createdRepostaje.getEuros() + ", repostaje.cocheId : " + createdRepostaje.getCoche().getId());
                    setResult(RESULT_OK);
                    finish();
                }
                else{
                    Repostaje editRepostaje = new Repostaje();
                    editRepostaje.setId(repostajeEdit.getId());
                    editRepostaje.setFecha(repostajeEdit.getFecha());
                    editRepostaje.setEuros(repostajeEdit.getEuros());
                    editRepostaje.setEuros_litro(repostajeEdit.getEuros_litro());
                    editRepostaje.setLitros(repostajeEdit.getLitros());
                    editRepostaje.setCoche((Coche) mSpinnerCoche.getSelectedItem());
                    mRepostajeDao.updateRepostaje(editRepostaje);
                    Intent i = new Intent(this, ListRepostajeActivity.class);
                    startActivity(i);
                    Toast.makeText(this, R.string.repostaje_edited_successfully, Toast.LENGTH_LONG).show();
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
		mCocheDao.close();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		mSelectedCoche = mAdapter.getItem(position);
		Log.d(TAG, "selectedCompany : " + mSelectedCoche.getId());
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
}
