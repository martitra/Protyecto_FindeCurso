package com.example.soft12.parte_trabajo.model;

import android.os.Bundle;

import com.example.soft12.parte_trabajo.dao.DBHelper;

import java.io.Serializable;

/**
 * Created by soft12 on 03/06/2015.
 */
public class Cliente implements Serializable{

    public static final String TAG = "Cliente";

    private long cId;
    private String codigo;
    private String nNombre;


    public Cliente() {}


    public Cliente(String nombre) {
        this.nNombre = nombre;
    }

    public long getcId() {
        return cId;
    }

    public void setcId(long cId) {
        this.cId = cId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getnNombre() {
        return nNombre;
    }

    public void setnNombre(String nNombre) {
        this.nNombre = nNombre;
    }

    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(DBHelper.COLUMN_CLIENTE_ID, cId);
        bundle.putString(DBHelper.COLUMN_CLIENTE_CODIGO, codigo);
        bundle.putString(DBHelper.COLUMN_CLIENTE_NOMBRE, nNombre);
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        cId = bundle.getLong(DBHelper.COLUMN_CLIENTE_ID);
        codigo = bundle.getString(DBHelper.COLUMN_CLIENTE_CODIGO);
        nNombre = bundle.getString(DBHelper.COLUMN_CLIENTE_NOMBRE);
    }

}
