package com.example.soft12.parte_trabajo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import com.example.soft12.parte_trabajo.adapter.SpinnerCochesAdapter;
import com.example.soft12.parte_trabajo.dao.CocheDAO;
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

	public static final String EXTRA_SELECTED_REPOSTAJE_ID = "extra_key_selected_repostaje_id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_repostaje);

		initViews();

		this.mCocheDao = new CocheDAO(this);
		this.mRepostajeDao = new RepostajeDAO(this);
		
		//fill the spinner with companies
		List<Coche> listCoches = mCocheDao.getAllCoches();
		if(listCoches != null) {
			mAdapter = new SpinnerCochesAdapter(this, listCoches);
			mSpinnerCoche.setAdapter(mAdapter);
			mSpinnerCoche.setOnItemSelectedListener(this);
		}
	}

	private void initViews() {
		this.mTxtFecha = (EditText) findViewById(R.id.txt_fecha);
		this.mTxtEuros = (EditText) findViewById(R.id.txt_euros);
		this.mTxtEuros_Litro = (EditText) findViewById(R.id.txt_euros_litro);
		this.mTxtLitros = (EditText) findViewById(R.id.txt_litros);
		this.mSpinnerCoche = (Spinner) findViewById(R.id.spinner_coches);
		Button mBtnAdd = (Button) findViewById(R.id.btn_add);

		mBtnAdd.setOnClickListener(this);
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
		Log.d(TAG, "selectedCompany : " + mSelectedCoche.getMatricula());
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
}
