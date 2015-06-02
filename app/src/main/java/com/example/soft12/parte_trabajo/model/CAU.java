package com.example.soft12.parte_trabajo.model;

import android.os.Bundle;

import com.example.soft12.parte_trabajo.dao.DBHelper;

import java.io.Serializable;

/**
 * Created by soft12 on 02/06/2015.
 */
public class CAU implements Serializable{

    public static final String TAG = "CAU";

    private long cauId;
    private String cNombre;

    public CAU(String cNombre) {
        this.cNombre = cNombre;
    }

    public CAU() {
    }

    public long getCauId() {
        return cauId;
    }

    public void setCauId(long cauId) {
        this.cauId = cauId;
    }

    public String getcNombre() {
        return cNombre;
    }

    public void setcNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(DBHelper.COLUMN_CAU_ID, cauId);
        bundle.putString(DBHelper.COLUMN_CAU_NOMBRE, cNombre);
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        cauId = bundle.getLong(DBHelper.COLUMN_CAU_ID);
        cNombre = bundle.getString(DBHelper.COLUMN_CAU_NOMBRE);
    }
}
