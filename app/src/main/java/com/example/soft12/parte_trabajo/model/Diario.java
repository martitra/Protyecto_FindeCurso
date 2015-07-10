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
    private String cliente;
    private String horaIni;
    private String horaFin;
    private String desplazamiento;
    private Double kmIni;
    private Double kmFin;
    private Login tecnico;
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

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
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

    public Login getTecnico() {
        return tecnico;
    }

    public void setTecnico(Login tecnico) {
        this.tecnico = tecnico;
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
        bundle.putString(DBHelper.COLUMN_DIARIO_CLIENTE, cliente);
        bundle.putString(DBHelper.COLUMN_DIARIO_SOLUCION, solucion);

        bundle.putLong(DBHelper.COLUMN_DIARIO_TECNICO, tecnico.getcId());
        bundle.putString(DBHelper.COLUMN_USUARIO_NOMBRE, tecnico.getNombre());
        bundle.putString(DBHelper.COLUMN_USUARIO_EMAIL, tecnico.getMail());

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
        id = bundle.getLong(DBHelper.COLUMN_REPOSTAJE_ID);
        fecha = bundle.getString(DBHelper.COLUMN_DIARIO_FECHA);

        cau = bundle.getString(DBHelper.COLUMN_DIARIO_CAU);

        cliente = bundle.getString(DBHelper.COLUMN_DIARIO_CLIENTE);

        solucion = bundle.getString(DBHelper.COLUMN_DIARIO_SOLUCION);

        tecnico = new Login();
        tecnico.setcId(bundle.getLong(DBHelper.COLUMN_USUARIO_ID));
        tecnico.setNombre(bundle.getString(DBHelper.COLUMN_USUARIO_NOMBRE));
        tecnico.setMail(bundle.getString(DBHelper.COLUMN_USUARIO_EMAIL));

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
                "\nCliente = " + cliente +
                "\nHora de Inicio = '" + horaIni + '\'' +
                "\nHora de Finalización = '" + horaFin + '\'' +
                "\nDesplazamiento = '" + desplazamiento + '\'' +
                "\nKilómetros Iniciales = " + kmIni +
                "\nKilómetros Finales =" + kmFin +
                "\nTécnico = " + tecnico.getNombre() +
                "\nCoche = " + coche.getMatricula();
    }
}
