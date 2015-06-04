package com.example.soft12.parte_trabajo.model;

import android.os.Bundle;

import com.example.soft12.parte_trabajo.dao.DBHelper;

import java.io.Serializable;

/**
 * Created by soft12 on 04/06/2015.
 */
public class Diario implements Serializable {

    public static final String TAG = "Diario";

    private long id;
    private String fecha;
    private CAU cau;
    private Solucion solucion;
    private Cliente cliente;
    private String horaIni;
    private String horaFin;
    private Double viaje;
    private Double kms;

    public Diario() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public CAU getCau() {
        return cau;
    }

    public void setCau(CAU cau) {
        this.cau = cau;
    }

    public Solucion getSolucion() {
        return solucion;
    }

    public void setSolucion(Solucion solucion) {
        this.solucion = solucion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(String horaIni) {
        this.horaIni = horaIni;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Double getViaje() {
        return viaje;
    }

    public void setViaje(Double viaje) {
        this.viaje = viaje;
    }

    public Double getKms() {
        return kms;
    }

    public void setKms(Double kms) {
        this.kms = kms;
    }

    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(DBHelper.COLUMN_DIARIO_ID, id);
        bundle.putString(DBHelper.COLUMN_DIARIO_FECHA, fecha);

        bundle.putLong(DBHelper.COLUMN_DIARIO_CAU, cau.getCauId());
        bundle.putString(DBHelper.COLUMN_CAU_NOMBRE, cau.getcNombre());
        bundle.putLong(DBHelper.COLUMN_DIARIO_CLIENTE, cliente.getcId());
        bundle.putString(DBHelper.COLUMN_CLIENTE_NOMBRE, cliente.getnNombre());
        bundle.putLong(DBHelper.COLUMN_DIARIO_SOLUCION, solucion.getcId());
        bundle.putString(DBHelper.COLUMN_SOLUCION_NOMBRE, solucion.getnNombre());

        bundle.putString(DBHelper.COLUMN_DIARIO_HORA_INI, horaIni);
        bundle.putString(DBHelper.COLUMN_DIARIO_HORA_FIN, horaFin);
        bundle.putDouble(DBHelper.COLUMN_DIARIO_VIAJE, viaje);
        bundle.putDouble(DBHelper.COLUMN_DIARIO_KMS, kms);

        return bundle;
    }

    public void setBundle(Bundle bundle) {
        id = bundle.getLong(DBHelper.COLUMN_REPOSTAJE_ID);
        fecha = bundle.getString(DBHelper.COLUMN_REPOSTAJE_FECHA);

        cau = new CAU();
        cau.setCauId(bundle.getLong(DBHelper.COLUMN_CAU_ID));
        cau.setcNombre(bundle.getString(DBHelper.COLUMN_CAU_NOMBRE));

        cliente = new Cliente();
        cliente.setcId(bundle.getLong(DBHelper.COLUMN_CLIENTE_ID));
        cliente.setnNombre(bundle.getString(DBHelper.COLUMN_CLIENTE_NOMBRE));

        solucion = new Solucion();
        solucion.setcId(bundle.getLong(DBHelper.COLUMN_SOLUCION_ID));
        solucion.setnNombre(bundle.getString(DBHelper.COLUMN_SOLUCION_NOMBRE));

        horaIni = bundle.getString(DBHelper.COLUMN_DIARIO_HORA_INI);
        horaFin = bundle.getString(DBHelper.COLUMN_DIARIO_HORA_FIN);
        viaje = bundle.getDouble(DBHelper.COLUMN_DIARIO_VIAJE);
        kms = bundle.getDouble(DBHelper.COLUMN_DIARIO_KMS);
    }

}
