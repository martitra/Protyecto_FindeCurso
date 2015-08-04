package com.example.soft12.parte_trabajo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.soft12.parte_trabajo.model.Tecnico;

import java.sql.SQLException;

/**
 * Created by soft12 on 04/08/2015.
 */
public class TecnicoDAO {

    public static final String TAG = "TecnicoDAO";
    public SQLiteDatabase mDatabase;
    private String[] mAllColumns = {DBHelper.COLUMN_TECNICO_ID, DBHelper.COLUMN_TECNICO_NOMBRE,
            DBHelper.COLUMN_TECNICO_PASS, DBHelper.COLUMN_TECNICO_EMAIL};
    // Context of the application using the database.
    private Context mContext;
    // Database open/upgrade helper
    private DBHelper mDbHelper;

    public TecnicoDAO(Context context) {
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

    public TecnicoDAO open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDatabase.close();
    }

    public Tecnico insertEntry(String userName, String password, String mail) {
        ContentValues values = new ContentValues();
        // Assign values for each row.
        values.put(DBHelper.COLUMN_TECNICO_NOMBRE, userName);
        values.put(DBHelper.COLUMN_TECNICO_PASS, password);
        values.put(DBHelper.COLUMN_TECNICO_EMAIL, mail);

        long insertId = mDatabase.insert(DBHelper.TABLE_TECNICO, null, values);

        Cursor cursor = mDatabase.query(DBHelper.TABLE_TECNICO,
                mAllColumns, DBHelper.COLUMN_TECNICO_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Tecnico tecnico = cursorToTecnico(cursor);
        cursor.close();
        return tecnico;
    }

    public Tecnico getTecnicoById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_TECNICO, mAllColumns,
                DBHelper.COLUMN_TECNICO_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursorToTecnico(cursor);
    }

    public Tecnico getSinlgeTecnicoMailEntry(String mail) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_TECNICO, mAllColumns,
                DBHelper.COLUMN_TECNICO_EMAIL + " = ? ",
                new String[]{mail}, null, null, null);
        if (cursor.getCount() == 0) // UserName Not Exist
        {
            cursor.close();
        }
        cursor.moveToFirst();
        return cursorToTecnico(cursor);
    }

    public Tecnico getSinlgeTecnicoIdEntry(String userName) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_TECNICO, mAllColumns,
                DBHelper.COLUMN_TECNICO_NOMBRE + " = ? ",
                new String[]{userName}, null, null, null);
        if (cursor.getCount() == 0) // UserName Not Exist
        {
            cursor.close();
        }
        cursor.moveToFirst();
        return cursorToTecnico(cursor);

    }

    protected Tecnico cursorToTecnico(Cursor cursor) {
        Tecnico tecnico = new Tecnico();

        if (cursor.getCount() != 0) {
            tecnico.setcId(cursor.getLong(0));
            tecnico.setNombre(cursor.getString(1));
            tecnico.setPass(cursor.getString(2));
            tecnico.setMail(cursor.getString(3));
        }
        return tecnico;
    }

    /*public int updateTecnico(Tecnico t) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TECNICO_NOMBRE, t.getNombre());
        values.put(DBHelper.COLUMN_TECNICO_PASS, t.getPass());
        values.put(DBHelper.COLUMN_TECNICO_EMAIL, t.getMail());

        return mDatabase.update(DBHelper.TABLE_TECNICO, values, DBHelper.COLUMN_TECNICO_ID + " = ?",
                new String[] { String.valueOf(t.getcId()) });
    }*/

}
