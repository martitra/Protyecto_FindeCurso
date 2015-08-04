package com.example.soft12.parte_trabajo.model;

import android.os.Bundle;

import com.example.soft12.parte_trabajo.dao.DBHelper;

import java.io.Serializable;

/**
 * Created by soft12 on 04/08/2015.
 */
public class Tecnico implements Serializable {

    public static final String TAG = "Tecnico";

    private long cId;
    private String nombre;
    private String pass;
    private String mail;

    public Tecnico() {
    }

    public long getcId() {
        return cId;
    }

    public void setcId(long cId) {
        this.cId = cId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(DBHelper.COLUMN_TECNICO_ID, cId);
        bundle.putString(DBHelper.COLUMN_TECNICO_NOMBRE, nombre);
        bundle.putString(DBHelper.COLUMN_TECNICO_PASS, pass);
        bundle.putString(DBHelper.COLUMN_TECNICO_EMAIL, mail);
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        cId = bundle.getLong(DBHelper.COLUMN_TECNICO_ID);
        nombre = bundle.getString(DBHelper.COLUMN_TECNICO_NOMBRE);
        pass = bundle.getString(DBHelper.COLUMN_TECNICO_PASS);
        mail = bundle.getString(DBHelper.COLUMN_TECNICO_EMAIL);
    }

}