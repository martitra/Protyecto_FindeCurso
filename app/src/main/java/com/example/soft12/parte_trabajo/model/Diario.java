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

    private String cau;
    private String solucion;
    private Cliente cliente;
    private String horaIni;
    private String horaFin;
    private String desplazamiento;
    private Double kmIni;
    private Double kmFin;
    private Coche coche;

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

    public String getCau() {
        return cau;
    }

    public void setCau(String cau) {
        this.cau = cau;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
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

    public String getDesplazamiento() {
        return desplazamiento;
    }

    public void setDesplazamiento(String desplazamiento) {
        this.desplazamiento = desplazamiento;
    }

    public Double getKmIni() {
        return kmIni;
    }

    public void setKmIni(Double kmIni) {
        this.kmIni = kmIni;
    }

    public Double getKmFin() {
        return kmFin;
    }

    public void setKmFin(Double kmFin) {
        this.kmFin = kmFin;
    }

    public Coche getCoche() {
        return coche;
    }

    public void setCoche(Coche coche) {
        this.coche = coche;
    }

    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(DBHelper.COLUMN_DIARIO_ID, id);
        bundle.putString(DBHelper.COLUMN_DIARIO_FECHA, fecha);
        bundle.putString(DBHelper.COLUMN_DIARIO_CAU, cau);

        bundle.putLong(DBHelper.COLUMN_CLIENTE_ID, cliente.getcId());
        bundle.putString(DBHelper.COLUMN_CLIENTE_NOMBRE, cliente.getnNombre());
        bundle.putString(DBHelper.COLUMN_CLIENTE_CODIGO, cliente.getCodigo());

        bundle.putString(DBHelper.COLUMN_DIARIO_SOLUCION, solucion);

        bundle.putLong(DBHelper.COLUMN_DIARIO_COCHE_ID, coche.getId());
        bundle.putString(DBHelper.COLUMN_COCHE_MATRICULA, coche.getMatricula());
        bundle.putInt(DBHelper.COLUMN_COCHE_KMS, coche.getcKilometros());

        bundle.putString(DBHelper.COLUMN_DIARIO_HORA_INI, horaIni);
        bundle.putString(DBHelper.COLUMN_DIARIO_HORA_FIN, horaFin);
        bundle.putString(DBHelper.COLUMN_DIARIO_DESPLAZAMIENTO, desplazamiento);
        bundle.putDouble(DBHelper.COLUMN_DIARIO_KMS_INI, kmIni);
        bundle.putDouble(DBHelper.COLUMN_DIARIO_KMS_FIN, kmFin);

        return bundle;
    }

    public void setBundle(Bundle bundle) {
        id = bundle.getLong(DBHelper.COLUMN_DIARIO_ID);
        fecha = bundle.getString(DBHelper.COLUMN_DIARIO_FECHA);

        cau = bundle.getString(DBHelper.COLUMN_DIARIO_CAU);

        cliente = new Cliente();
        cliente.setcId(bundle.getLong(DBHelper.COLUMN_CLIENTE_ID));
        cliente.setnNombre(bundle.getString(DBHelper.COLUMN_CLIENTE_NOMBRE));
        cliente.setCodigo(bundle.getString(DBHelper.COLUMN_CLIENTE_CODIGO));

        solucion = bundle.getString(DBHelper.COLUMN_DIARIO_SOLUCION);

        coche = new Coche();
        coche.setId(bundle.getLong(DBHelper.COLUMN_COCHE_ID));
        coche.setMatricula(bundle.getString(DBHelper.COLUMN_COCHE_MATRICULA));
        coche.setcKilometros(bundle.getInt(DBHelper.COLUMN_COCHE_KMS));

        horaIni = bundle.getString(DBHelper.COLUMN_DIARIO_HORA_INI);
        horaFin = bundle.getString(DBHelper.COLUMN_DIARIO_HORA_FIN);
        desplazamiento = bundle.getString(DBHelper.COLUMN_DIARIO_DESPLAZAMIENTO);
        kmIni = bundle.getDouble(DBHelper.COLUMN_DIARIO_KMS_INI);
        kmFin = bundle.getDouble(DBHelper.COLUMN_DIARIO_KMS_FIN);
    }


    @Override
    public String toString() {
        return "Fecha = '" + fecha + '\'' +
                "\nCau =" + cau +
                "\nSolucion =" + solucion +
                "\nCliente = " + cliente.getnNombre() +
                "\nHora de Inicio = '" + horaIni + '\'' +
                "\nHora de Finalización = '" + horaFin + '\'' +
                "\nDesplazamiento = '" + desplazamiento + '\'' +
                "\nKilómetros Iniciales = " + kmIni +
                "\nKilómetros Finales =" + kmFin +
                "\nCoche = " + coche.getMatricula();
    }
}
