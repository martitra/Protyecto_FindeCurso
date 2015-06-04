package com.example.soft12.parte_trabajo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.soft12.parte_trabajo.model.Coche;

import java.util.ArrayList;
import java.util.List;

public class CocheDAO {

    public static final String TAG = "CocheDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DBHelper.COLUMN_COCHE_ID,
            DBHelper.COLUMN_COCHE_MATRICULA };

    public CocheDAO(Context context) {
        this.mContext = context;
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

    public Coche createCoche(String matricula) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_COCHE_MATRICULA, matricula);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_COCHE, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_COCHE, mAllColumns,
                DBHelper.COLUMN_COCHE_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Coche newCoche = cursorToCoche(cursor);
        cursor.close();
        return newCoche;
    }

    public void deleteCoche(Coche coche) {
        long id = coche.getId();
        System.out.println("the deleted coche has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_COCHE, DBHelper.COLUMN_COCHE_ID
                + " = " + id, null);
    }

    public List<Coche> getAllCoches() {
        List<Coche> listCoches = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_COCHE,
                mAllColumns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Coche coche = cursorToCoche(cursor);
                listCoches.add(coche);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listCoches;
    }

    public Coche getCocheById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_COCHE, mAllColumns,
                DBHelper.COLUMN_COCHE_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursorToCoche(cursor);
    }

    protected Coche cursorToCoche(Cursor cursor) {
        Coche coche = new Coche();
        coche.setId(cursor.getLong(0));
        coche.setMatricula(cursor.getString(1));
        return coche;
    }
    public int updateCoche(Coche c) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_COCHE_MATRICULA, c.getMatricula());

        return mDatabase.update(DBHelper.TABLE_COCHE, values, DBHelper.COLUMN_COCHE_ID + " = ?",
        new String[] { String.valueOf(c.getId()) });
    }
}
