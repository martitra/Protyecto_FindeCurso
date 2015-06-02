package com.example.soft12.parte_trabajo.model;

import android.os.Bundle;

import com.example.soft12.parte_trabajo.dao.DBHelper;

import java.io.Serializable;

public class Coche implements Serializable {

	public static final String TAG = "Employee";
	
	private long cId;
	private String nMatricula;

	
	public Coche() {}
	
	
	public Coche(String matriucla) {
		this.nMatricula = matriucla;

	}
	
	public long getId() {
		return cId;
	}
	public void setId(long cId) {
		this.cId = cId;
	}
	public String getMatricula() {
		return nMatricula;
	}
	public void setMatricula(String matricula) {
		this.nMatricula = matricula;
	}

	public Bundle getBundle() {
		Bundle bundle = new Bundle();
		bundle.putLong(DBHelper.COLUMN_COCHE_ID, cId);
		bundle.putString(DBHelper.COLUMN_COCHE_MATRICULA, nMatricula);
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		cId = bundle.getLong(DBHelper.COLUMN_COCHE_ID);
		nMatricula = bundle.getString(DBHelper.COLUMN_COCHE_MATRICULA);
	}
}
