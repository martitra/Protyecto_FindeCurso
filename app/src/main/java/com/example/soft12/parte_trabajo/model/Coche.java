package com.example.soft12.parte_trabajo.model;

import android.os.Bundle;

import com.example.soft12.parte_trabajo.dao.DBHelper;

import java.io.Serializable;

public class Coche implements Serializable {

	public static final String TAG = "Coche";
	
	private long cId;
	private String cMatricula;
	private int cKilometros;

    public Coche() {}
	
	public long getId() {
		return cId;
	}

	public void setId(long cId) {
		this.cId = cId;
	}

	public String getMatricula() {
		return cMatricula;
	}

	public void setMatricula(String matricula) {
		this.cMatricula = matricula;
	}

	public int getcKilometros() {
		return cKilometros;
	}

	public void setcKilometros(int cKilometros) {
		this.cKilometros = cKilometros;
	}

	public Bundle getBundle() {
		Bundle bundle = new Bundle();
		bundle.putLong(DBHelper.COLUMN_COCHE_ID, cId);
		bundle.putString(DBHelper.COLUMN_COCHE_MATRICULA, cMatricula);
		bundle.putInt(DBHelper.COLUMN_COCHE_KMS, cKilometros);
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		cId = bundle.getLong(DBHelper.COLUMN_COCHE_ID);
		cMatricula = bundle.getString(DBHelper.COLUMN_COCHE_MATRICULA);
		cKilometros = bundle.getInt(DBHelper.COLUMN_COCHE_KMS);
	}

}
