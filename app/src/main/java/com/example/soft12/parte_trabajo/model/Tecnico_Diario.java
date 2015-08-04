package com.example.soft12.parte_trabajo.model;

import android.os.Bundle;

import com.example.soft12.parte_trabajo.dao.DBHelper;

/**
 * Created by soft12 on 03/08/2015.
 */
public class Tecnico_Diario {

    public static final String TAG = "Tecnico_Diario";

    private long id;
    private Login tecnico;
    private Diario diario;
    private String fecha;

    public Tecnico_Diario() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Login getTecnico() {
        return tecnico;
    }

    public void setTecnico(Login tecnico) {
        this.tecnico = tecnico;
    }

    public Diario getDiario() {
        return diario;
    }

    public void setDiario(Diario diario) {
        this.diario = diario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(DBHelper.COLUMN_TD_ID, id);
        bundle.putString(DBHelper.COLUMN_TD_FECHA, fecha);

        bundle.putLong(DBHelper.COLUMN_DIARIO_ID, diario.getId());
        bundle.putString(DBHelper.COLUMN_DIARIO_FECHA, diario.getFecha());
        bundle.putString(DBHelper.COLUMN_DIARIO_CAU, diario.getCau());
        bundle.putLong(DBHelper.COLUMN_CLIENTE_ID, diario.getCliente().getcId());
        bundle.putString(DBHelper.COLUMN_CLIENTE_NOMBRE, diario.getCliente().getnNombre());
        bundle.putString(DBHelper.COLUMN_CLIENTE_CODIGO, diario.getCliente().getCodigo());
        bundle.putString(DBHelper.COLUMN_DIARIO_SOLUCION, diario.getSolucion());
        bundle.putLong(DBHelper.COLUMN_DIARIO_COCHE_ID, diario.getCoche().getId());
        bundle.putString(DBHelper.COLUMN_COCHE_MATRICULA, diario.getCoche().getMatricula());
        bundle.putInt(DBHelper.COLUMN_COCHE_KMS, diario.getCoche().getcKilometros());
        bundle.putString(DBHelper.COLUMN_DIARIO_HORA_INI, diario.getHoraIni());
        bundle.putString(DBHelper.COLUMN_DIARIO_HORA_FIN, diario.getHoraFin());
        bundle.putString(DBHelper.COLUMN_DIARIO_DESPLAZAMIENTO, diario.getDesplazamiento());
        bundle.putDouble(DBHelper.COLUMN_DIARIO_KMS_INI, diario.getKmIni());
        bundle.putDouble(DBHelper.COLUMN_DIARIO_KMS_FIN, diario.getKmFin());

        bundle.putLong(DBHelper.COLUMN_TECNICO_ID, tecnico.getcId());
        bundle.putString(DBHelper.COLUMN_TECNICO_NOMBRE, tecnico.getNombre());
        bundle.putString(DBHelper.COLUMN_TECNICO_PASS, tecnico.getPass());
        bundle.putString(DBHelper.COLUMN_TECNICO_EMAIL, tecnico.getMail());

        return bundle;
    }

    public void setBundle(Bundle bundle) {
        id = bundle.getLong(DBHelper.COLUMN_TD_DIARIO_ID);
        fecha = bundle.getString(DBHelper.COLUMN_TD_FECHA);

        diario = new Diario();
        diario.setFecha(bundle.getString(DBHelper.COLUMN_DIARIO_FECHA));
        diario.setCau(bundle.getString(DBHelper.COLUMN_DIARIO_CAU));

        Cliente cliente = new Cliente();
        cliente.setcId(bundle.getLong(DBHelper.COLUMN_CLIENTE_ID));
        cliente.setnNombre(bundle.getString(DBHelper.COLUMN_CLIENTE_NOMBRE));
        cliente.setCodigo(bundle.getString(DBHelper.COLUMN_CLIENTE_CODIGO));

        diario.setSolucion(bundle.getString(DBHelper.COLUMN_DIARIO_SOLUCION));

        Coche coche = new Coche();
        coche.setId(bundle.getLong(DBHelper.COLUMN_COCHE_ID));
        coche.setMatricula(bundle.getString(DBHelper.COLUMN_COCHE_MATRICULA));
        coche.setcKilometros(bundle.getInt(DBHelper.COLUMN_COCHE_KMS));

        diario.setHoraIni(bundle.getString(DBHelper.COLUMN_DIARIO_HORA_INI));
        diario.setHoraFin(bundle.getString(DBHelper.COLUMN_DIARIO_HORA_FIN));
        diario.setDesplazamiento(bundle.getString(DBHelper.COLUMN_DIARIO_DESPLAZAMIENTO));
        diario.setKmIni(bundle.getDouble(DBHelper.COLUMN_DIARIO_KMS_INI));
        diario.setKmFin(bundle.getDouble(DBHelper.COLUMN_DIARIO_KMS_FIN));

        tecnico = new Login();
        tecnico.setcId(bundle.getLong(DBHelper.COLUMN_TECNICO_ID));
        tecnico.setNombre(bundle.getString(DBHelper.COLUMN_TECNICO_NOMBRE));
        tecnico.setPass(bundle.getString(DBHelper.COLUMN_TECNICO_PASS));
        tecnico.setMail(bundle.getString(DBHelper.COLUMN_TECNICO_EMAIL));
    }

}
