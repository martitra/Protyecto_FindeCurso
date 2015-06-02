package com.example.soft12.parte_trabajo.model;

import android.os.Bundle;

import com.example.soft12.parte_trabajo.dao.DBHelper;

import java.io.Serializable;

public class Repostaje implements Serializable {

	public static final String TAG = "Employee";

	private long rId;
	private String fecha;
	private double euros;
	private double euros_litro;
	private double litros;
	private Coche mCoche;

	public Repostaje() {

	}

	public Repostaje(String fecha, double euros,double euros_litro, double litros) {
		this.fecha = fecha;
		this.euros = euros;
		this.euros_litro = euros_litro;
		this.litros = litros;
	}

	public long getId() {
		return rId;
	}

	public void setId(long mId) {
		this.rId = mId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public double getEuros() {
		return euros;
	}

	public void setEuros(double euros) {
		this.euros = euros;
	}

	public double getEuros_litro() {
		return euros_litro;
	}

	public void setEuros_litro(double euros_litro) {
		this.euros_litro = euros_litro;
	}

	public double getLitros() {
		return litros;
	}

	public void setLitros(double litros) {
		this.litros = litros;
	}

	public Coche getCoche() {
		return mCoche;
	}

	public void setCoche(Coche mCoche) {
		this.mCoche = mCoche;
	}

	public Bundle getBundle() {
		Bundle bundle = new Bundle();
		bundle.putLong(DBHelper.COLUMN_REPOSTAJE_ID, rId);
		bundle.putString(DBHelper.COLUMN_REPOSTAJE_FECHA, fecha);
		bundle.putDouble(DBHelper.COLUMN_REPOSTAJE_EUROS, euros);
		bundle.putDouble(DBHelper.COLUMN_REPOSTAJE_EUROS_LITRO, euros_litro);
		bundle.putDouble(DBHelper.COLUMN_REPOSTAJE_LITROS, litros);
		bundle.putLong(DBHelper.COLUMN_REPOSTAJE_COCHE_ID, mCoche.getId());
		bundle.putString(DBHelper.COLUMN_COCHE_MATRICULA, mCoche.getMatricula());
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		rId = bundle.getLong(DBHelper.COLUMN_REPOSTAJE_ID);
		fecha = bundle.getString(DBHelper.COLUMN_REPOSTAJE_FECHA);
		euros = bundle.getDouble(DBHelper.COLUMN_REPOSTAJE_EUROS);
		euros_litro = bundle.getDouble(DBHelper.COLUMN_REPOSTAJE_EUROS_LITRO);
		litros = bundle.getDouble(DBHelper.COLUMN_REPOSTAJE_LITROS);
		mCoche = new Coche();
		mCoche.setId(bundle.getLong(DBHelper.COLUMN_REPOSTAJE_COCHE_ID));
		mCoche.setMatricula(bundle.getString(DBHelper.COLUMN_COCHE_MATRICULA));
	}
}
