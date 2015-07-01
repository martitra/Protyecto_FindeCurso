package com.example.soft12.parte_trabajo.model;

import android.os.Bundle;

import com.example.soft12.parte_trabajo.dao.DBHelper;

import java.io.Serializable;

/**
 * Created by soft12 on 30/06/2015.
 */
public class Login implements Serializable {

    public static final String TAG = "Login";

    private long cId;
    private String nombre;
    private String pass;
    private String mail;

    public Login() {}

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
        bundle.putLong(DBHelper.COLUMN_USUARIO_ID, cId);
        bundle.putString(DBHelper.COLUMN_USUARIO_NOMBRE, nombre);
        bundle.putString(DBHelper.COLUMN_USUARIO_PASS, pass);
        bundle.putString(DBHelper.COLUMN_USUARIO_EMAIL, mail);
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        cId = bundle.getLong(DBHelper.COLUMN_USUARIO_ID);
        nombre = bundle.getString(DBHelper.COLUMN_USUARIO_NOMBRE);
        pass = bundle.getString(DBHelper.COLUMN_USUARIO_PASS);
        mail = bundle.getString(DBHelper.COLUMN_USUARIO_EMAIL);
    }

}
