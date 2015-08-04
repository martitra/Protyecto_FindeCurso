package com.example.soft12.parte_trabajo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.soft12.parte_trabajo.model.Diario;
import com.example.soft12.parte_trabajo.model.Login;
import com.example.soft12.parte_trabajo.model.Tecnico_Diario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soft12 on 03/08/2015.
 */
public class Tecnico_DiarioDAO {

    public static final String TAG = "Tecnico_DiarioDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = {DBHelper.COLUMN_TD_ID,
            DBHelper.COLUMN_TD_TECNICO_ID,
            DBHelper.COLUMN_TD_DIARIO_ID,
            DBHelper.COLUMN_TD_FECHA};

    public Tecnico_DiarioDAO(Context context) {
        mDbHelper = new DBHelper(context);
        this.mContext = context;
        // open the database
        try {
            open();
        } catch (SQLException e) {
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

    public Tecnico_Diario createTecnico_Diario(List<Login> tecnicos, long diarioId, String fecha) {

        ContentValues values = new ContentValues();
        Tecnico_Diario newTecnico_Diario = null;
        if (tecnicos.size() > 1) {

            for (int i = 0; i < tecnicos.size(); i++) {
                values.put(DBHelper.COLUMN_TD_TECNICO_ID, String.valueOf(tecnicos.get(i).getcId()));
                values.put(DBHelper.COLUMN_TD_DIARIO_ID, diarioId);
                values.put(DBHelper.COLUMN_TD_FECHA, fecha);
                long insertId = mDatabase.insert(DBHelper.TABLE_TECNICO_DIARIO, null, values);
                Cursor cursor = mDatabase.query(DBHelper.TABLE_TECNICO_DIARIO,
                        mAllColumns, DBHelper.COLUMN_TD_ID + " = " + insertId, null, null, null, null);
                cursor.moveToFirst();
                newTecnico_Diario = cursorToTecnicoDiario(cursor);
                cursor.close();
            }
        } else {
            values.put(DBHelper.COLUMN_TD_TECNICO_ID, String.valueOf(tecnicos.get(0).getcId()));
            values.put(DBHelper.COLUMN_TD_DIARIO_ID, diarioId);
            values.put(DBHelper.COLUMN_TD_FECHA, fecha);
            long insertId = mDatabase.insert(DBHelper.TABLE_TECNICO_DIARIO, null, values);
            Cursor cursor = mDatabase.query(DBHelper.TABLE_TECNICO_DIARIO,
                    mAllColumns, DBHelper.COLUMN_TD_ID + " = " + insertId, null, null, null, null);
            cursor.moveToFirst();
            newTecnico_Diario = cursorToTecnicoDiario(cursor);
            cursor.close();
        }

        return newTecnico_Diario;
    }

    public void deleteTecnicoDiario(Tecnico_Diario tecnico_diario) {
        long id = tecnico_diario.getId();
        System.out.println("the deleted diario has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_TECNICO_DIARIO, DBHelper.COLUMN_TD_ID + " = " + id, null);
    }

    public List<Tecnico_Diario> getAllTecnicoDiario() {
        List<Tecnico_Diario> listTecnicoDiario = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_TECNICO_DIARIO,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Tecnico_Diario tecnico_diario = cursorToTecnicoDiario(cursor);
            listTecnicoDiario.add(tecnico_diario);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listTecnicoDiario;
    }

    public List<Tecnico_Diario> getDateTecnicoDiario(String date, long tecnico) {
        List<Tecnico_Diario> tecnicoDiarioList = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_TECNICO_DIARIO,
                mAllColumns, DBHelper.COLUMN_TD_FECHA + " =  '" + date +
                        "' AND " + DBHelper.COLUMN_TD_TECNICO_ID + " = " + tecnico,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Tecnico_Diario tecnico_diario = cursorToTecnicoDiario(cursor);
            tecnicoDiarioList.add(tecnico_diario);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return tecnicoDiarioList;

    }

    private Tecnico_Diario cursorToTecnicoDiario(Cursor cursor) {
        Tecnico_Diario tecnico_diario = new Tecnico_Diario();
        tecnico_diario.setId(cursor.getLong(0));

        //get the cliente by id
        long tecnicoId = cursor.getLong(1);
        LoginDAO tecnicoDao = new LoginDAO(mContext);
        Login tecnico = tecnicoDao.getLoginById(tecnicoId);
        if (tecnico != null) {
            tecnico_diario.setTecnico(tecnico);
        }

        //get the coche by id
        long diarioId = cursor.getLong(2);
        DiarioDAO diarioDAO = new DiarioDAO(mContext);
        Diario diario = diarioDAO.getDiarioById(diarioId);
        if (diario != null) {
            tecnico_diario.setDiario(diario);
        }
        tecnico_diario.setFecha(cursor.getString(3));

        return tecnico_diario;
    }

    public int updateTecnicoDiario(Tecnico_Diario tecnico_diario) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TD_DIARIO_ID, tecnico_diario.getDiario().getId());
        values.put(DBHelper.COLUMN_TD_TECNICO_ID, tecnico_diario.getTecnico().getcId());

        return mDatabase.update(DBHelper.TABLE_TECNICO_DIARIO,
                values, DBHelper.COLUMN_TD_ID + " = ?", new String[]{
                        String.valueOf(tecnico_diario.getId())});
    }
}
