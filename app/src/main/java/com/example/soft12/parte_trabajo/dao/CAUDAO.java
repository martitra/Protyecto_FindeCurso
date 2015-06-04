package com.example.soft12.parte_trabajo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.soft12.parte_trabajo.model.CAU;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soft12 on 02/06/2015.
 */
public class CAUDAO {

    public static final String TAG = "CocheDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DBHelper.COLUMN_CAU_ID,
            DBHelper.COLUMN_CAU_NOMBRE};

    public CAUDAO(Context context) {
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

    public CAU createCAU(String nombre) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_CAU_NOMBRE, nombre);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_CAU, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_CAU, mAllColumns,
                DBHelper.COLUMN_CAU_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        CAU newCAU = cursorToCAU(cursor);
        cursor.close();
        return newCAU;
    }

    public void deleteCAU(CAU cau) {
        long id = cau.getCauId();

        System.out.println("the deleted cau has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_CAU, DBHelper.COLUMN_CAU_ID
                + " = " + id, null);
    }

    public List<CAU> getAllCAU() {
        List<CAU> listCAU = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_CAU,
                mAllColumns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                CAU cau = cursorToCAU(cursor);
                listCAU.add(cau);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }
        return listCAU;
    }

    public CAU getCAUById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_CAU, mAllColumns,
                DBHelper.COLUMN_CAU_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursorToCAU(cursor);
    }

    protected CAU cursorToCAU(Cursor cursor) {
        CAU cau = new CAU();
        cau.setCauId(cursor.getLong(0));
        cau.setcNombre(cursor.getString(1));
        return cau;
    }
    public int updateCAU(CAU c) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_CAU_NOMBRE, c.getcNombre());

        return mDatabase.update(DBHelper.TABLE_CAU, values, DBHelper.COLUMN_CAU_ID + " = ?",
                new String[] { String.valueOf(c.getCauId()) });
    }
}
