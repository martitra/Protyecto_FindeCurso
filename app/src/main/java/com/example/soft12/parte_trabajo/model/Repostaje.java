package com.example.soft12.parte_trabajo.model;

import java.io.Serializable;

public class Repostaje implements Serializable {

	public static final String TAG = "Employee";
	private static final long serialVersionUID = -7406082437623008161L;

	private long mId;
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
		return mId;
	}

	public void setId(long mId) {
		this.mId = mId;
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
}
