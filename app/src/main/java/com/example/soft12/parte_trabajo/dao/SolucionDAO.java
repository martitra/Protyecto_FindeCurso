package com.example.soft12.parte_trabajo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.soft12.parte_trabajo.model.Solucion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soft12 on 03/06/2015.
 */
public class SolucionDAO {

    public static final String TAG = "SolucionDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = { DBHelper.COLUMN_SOLUCION_ID,
            DBHelper.COLUMN_SOLUCION_NOMBRE };

    public SolucionDAO(Context context) {
        mDbHelper = new DBHelper(context);
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

    public Solucion createSolucion(String nombre) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_SOLUCION_NOMBRE, nombre);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_SOLUCION, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_SOLUCION, mAllColumns,
                DBHelper.COLUMN_SOLUCION_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Solucion newSolucion= cursorToSolucion(cursor);
        cursor.close();
        return newSolucion;
    }

    public void deleteSolucion(Solucion solucion) {
        long id = solucion.getcId();
        System.out.println("the deleted solucion has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_SOLUCION, DBHelper.COLUMN_SOLUCION_ID
                + " = " + id, null);
    }

    public List<Solucion> getAllSoluciones() {
        List<Solucion> listSoluciones = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_SOLUCION,
                mAllColumns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Solucion solucion = cursorToSolucion(cursor);
                listSoluciones.add(solucion);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listSoluciones;
    }

    public Solucion getSolucionById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_SOLUCION, mAllColumns,
                DBHelper.COLUMN_SOLUCION_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursorToSolucion(cursor);
    }

    protected Solucion cursorToSolucion(Cursor cursor) {
        Solucion solucion = new Solucion();
        solucion.setcId(cursor.getLong(0));
        solucion.setnNombre(cursor.getString(1));
        return solucion;
    }
    public int updateSolucion(Solucion s) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_SOLUCION_NOMBRE, s.getnNombre());

        return mDatabase.update(DBHelper.TABLE_SOLUCION, values, DBHelper.COLUMN_SOLUCION_ID + " = ?",
                new String[] { String.valueOf(s.getcId()) });
    }

}
