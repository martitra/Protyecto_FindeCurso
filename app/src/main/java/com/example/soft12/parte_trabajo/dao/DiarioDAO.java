package com.example.soft12.parte_trabajo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.soft12.parte_trabajo.model.Coche;
import com.example.soft12.parte_trabajo.model.Diario;
import com.example.soft12.parte_trabajo.model.Login;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soft12 on 04/06/2015.
 */
public class DiarioDAO {

    public static final String TAG = "DiarioDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = { DBHelper.COLUMN_DIARIO_ID,
                                    DBHelper.COLUMN_DIARIO_FECHA,
                                    DBHelper.COLUMN_DIARIO_CAU,
                                    DBHelper.COLUMN_DIARIO_CLIENTE,
                                    DBHelper.COLUMN_DIARIO_SOLUCION,
                                    DBHelper.COLUMN_DIARIO_HORA_INI,
                                    DBHelper.COLUMN_DIARIO_HORA_FIN,
                                    DBHelper.COLUMN_DIARIO_DESPLAZAMIENTO,
                                    DBHelper.COLUMN_DIARIO_KMS_INI,
                                    DBHelper.COLUMN_DIARIO_KMS_FIN,
                                    DBHelper.COLUMN_DIARIO_TECNICO,
                                    DBHelper.COLUMN_DIARIO_COCHE_ID};

    public DiarioDAO(Context context) {
        mDbHelper = new DBHelper(context);
        this.mContext = context;
        // open the database
        try {
            open();
        }
        catch(SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Diario createDiario(String fecha, String cau, String cliente, String solucion,
                               String horaIni, String horaFin, String desplazamiento, double kmini,
                               double kmfin, long tecnicoId, long cocheId) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_DIARIO_FECHA, fecha);
        values.put(DBHelper.COLUMN_DIARIO_CAU, cau);
        values.put(DBHelper.COLUMN_DIARIO_CLIENTE, cliente);
        values.put(DBHelper.COLUMN_DIARIO_SOLUCION, solucion);
        values.put(DBHelper.COLUMN_DIARIO_HORA_INI, horaIni);
        values.put(DBHelper.COLUMN_DIARIO_HORA_FIN, horaFin);
        values.put(DBHelper.COLUMN_DIARIO_DESPLAZAMIENTO, desplazamiento);
        values.put(DBHelper.COLUMN_DIARIO_KMS_INI, kmini);
        values.put(DBHelper.COLUMN_DIARIO_KMS_FIN, kmfin);
        values.put(DBHelper.COLUMN_DIARIO_TECNICO, tecnicoId);
        values.put(DBHelper.COLUMN_DIARIO_COCHE_ID, cocheId);
        long insertId = mDatabase.insert(DBHelper.TABLE_DIARIO, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_DIARIO,
                mAllColumns, DBHelper.COLUMN_DIARIO_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Diario newDiario = cursorToDiario(cursor);
        cursor.close();
        return newDiario;
    }

    public void deleteDiario(Diario diario) {
        long id = diario.getId();
        System.out.println("the deleted diario has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_DIARIO, DBHelper.COLUMN_DIARIO_ID + " = " + id, null);
    }

    public List<Diario> getAllDiario() {
        List<Diario> listDiario = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_DIARIO,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Diario diario = cursorToDiario(cursor);
            listDiario.add(diario);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listDiario;
    }

    public List<Diario> getDateDiario(String date){
        List<Diario> diarioList = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_DIARIO,
                mAllColumns, DBHelper.COLUMN_DIARIO_FECHA + " = ? ",
                new String[]{date},null,null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Diario diario = cursorToDiario(cursor);
            diarioList.add(diario);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return diarioList;

    }
    private Diario cursorToDiario(Cursor cursor) {
        Diario diario = new Diario();
        // id,fecha cau, cliente, solucion, fechaini, fechafin, viaje, kms
        diario.setId(cursor.getLong(0));
        diario.setFecha(cursor.getString(1));
        diario.setCau(cursor.getString(2));
        diario.setCliente(cursor.getString(3));
        diario.setSolucion(cursor.getString(4));
        diario.setHoraIni(cursor.getString(5));
        diario.setHoraFin(cursor.getString(6));
        diario.setDesplazamiento(cursor.getString(7));
        diario.setKmIni(Double.parseDouble(cursor.getString(8)));
        diario.setKmFin(Double.parseDouble(cursor.getString(9)));

        //get the tecnico by id
        long tecnicoId = cursor.getLong(10);
        LoginDAO loginDAO = new LoginDAO(mContext);
        Login login = loginDAO.getLoginById(tecnicoId);
        if (login != null){
            diario.setTecnico(login);
        }

        //get the coche by id
        long cocheId = cursor.getLong(11);
        CocheDAO cocheDAO = new CocheDAO(mContext);
        Coche coche = cocheDAO.getCocheById(cocheId);
        if (coche != null){
            diario.setCoche(coche);
        }

        return diario;
    }

    public int updateDiario(Diario diario) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_DIARIO_FECHA, diario.getFecha());
        values.put(DBHelper.COLUMN_DIARIO_CAU, diario.getCau());
        values.put(DBHelper.COLUMN_DIARIO_CLIENTE, diario.getCliente());
        values.put(DBHelper.COLUMN_DIARIO_SOLUCION, diario.getSolucion());
        values.put(DBHelper.COLUMN_DIARIO_HORA_INI, diario.getHoraIni());
        values.put(DBHelper.COLUMN_DIARIO_HORA_FIN, diario.getHoraFin());
        values.put(DBHelper.COLUMN_DIARIO_DESPLAZAMIENTO, diario.getDesplazamiento());
        values.put(DBHelper.COLUMN_DIARIO_KMS_INI, diario.getKmIni());
        values.put(DBHelper.COLUMN_DIARIO_KMS_FIN, diario.getKmFin());
        values.put(DBHelper.COLUMN_DIARIO_TECNICO, diario.getTecnico().getcId());
        values.put(DBHelper.COLUMN_DIARIO_COCHE_ID, diario.getCoche().getId());

        return mDatabase.update(DBHelper.TABLE_DIARIO, values, DBHelper.COLUMN_DIARIO_ID + " = ?",
                new String[] { String.valueOf(diario.getId()) });
    }

}
