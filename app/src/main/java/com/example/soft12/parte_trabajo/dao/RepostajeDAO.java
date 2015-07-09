package com.example.soft12.parte_trabajo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.soft12.parte_trabajo.model.Coche;
import com.example.soft12.parte_trabajo.model.Login;
import com.example.soft12.parte_trabajo.model.Repostaje;

import java.util.ArrayList;
import java.util.List;

public class RepostajeDAO {

    public static final String TAG = "RepostajeDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = { DBHelper.COLUMN_REPOSTAJE_ID,
                                    DBHelper.COLUMN_REPOSTAJE_FECHA,
                                    DBHelper.COLUMN_REPOSTAJE_EUROS,
                                    DBHelper.COLUMN_REPOSTAJE_EUROS_LITRO,
                                    DBHelper.COLUMN_REPOSTAJE_LITROS,
            DBHelper.COLUMN_REPOSTAJE_COCHE_ID,
            DBHelper.COLUMN_REPOSTAJE_TECNICO_ID};

    public RepostajeDAO(Context context) {
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

    public Repostaje createRepostaje(String fecha, double euros, double euros_litro,
                                     double litros, long cocheId, long tecnicoid) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_REPOSTAJE_FECHA, fecha);
        values.put(DBHelper.COLUMN_REPOSTAJE_EUROS, euros);
        values.put(DBHelper.COLUMN_REPOSTAJE_EUROS_LITRO, euros_litro);
        values.put(DBHelper.COLUMN_REPOSTAJE_LITROS, litros);
        values.put(DBHelper.COLUMN_REPOSTAJE_COCHE_ID, cocheId);
        values.put(DBHelper.COLUMN_REPOSTAJE_TECNICO_ID, tecnicoid);
        long insertId = mDatabase.insert(DBHelper.TABLE_REPOSTAJE, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_REPOSTAJE,
                mAllColumns, DBHelper.COLUMN_REPOSTAJE_ID + " = "
                        + insertId, null, null, null, null);
        cursor.moveToFirst();
        Repostaje newRepostaje = cursorToRepostaje(cursor);
        cursor.close();
        return newRepostaje;
    }

    public void deleteRepostaje(Repostaje repostaje) {
        long id = repostaje.getId();
        System.out.println("the deleted repostaje has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_REPOSTAJE, DBHelper.COLUMN_REPOSTAJE_ID + " = " + id, null);
    }

    public List<Repostaje> getAllRepostaje() {
        List<Repostaje> listRepostaje = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_REPOSTAJE,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Repostaje repostaje = cursorToRepostaje(cursor);
            listRepostaje.add(repostaje);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listRepostaje;
    }

    private Repostaje cursorToRepostaje(Cursor cursor) {
        Repostaje repostaje = new Repostaje();
        repostaje.setId(cursor.getLong(0));
        repostaje.setFecha(cursor.getString(1));
        repostaje.setEuros(Double.parseDouble(cursor.getString(2)));
        repostaje.setEuros_litro(Double.parseDouble(cursor.getString(3)));
        repostaje.setLitros(Double.parseDouble(cursor.getString(4)));

        // get The coche by id
        long cocheId = cursor.getLong(5);
        CocheDAO dao = new CocheDAO(mContext);
        Coche coche = dao.getCocheById(cocheId);
        if(coche != null)
            repostaje.setCoche(coche);
        //get the usuario by id
        long tecnicoId = cursor.getLong(6);
        LoginDAO loginDAO = new LoginDAO(mContext);
        Login login = loginDAO.getLoginById(tecnicoId);
        if (login != null)
            repostaje.setLogin(login);
        return repostaje;
    }

    public int updateRepostaje(Repostaje r) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_REPOSTAJE_FECHA, r.getFecha());
        values.put(DBHelper.COLUMN_REPOSTAJE_EUROS, r.getEuros());
        values.put(DBHelper.COLUMN_REPOSTAJE_EUROS_LITRO, r.getEuros_litro());
        values.put(DBHelper.COLUMN_REPOSTAJE_LITROS, r.getLitros());
        values.put(DBHelper.COLUMN_REPOSTAJE_COCHE_ID, r.getCoche().getId());
        values.put(DBHelper.COLUMN_REPOSTAJE_TECNICO_ID, r.getLogin().getcId());

        return mDatabase.update(DBHelper.TABLE_REPOSTAJE, values, DBHelper.COLUMN_REPOSTAJE_ID + " = ?",
                new String[] { String.valueOf(r.getId()) });
    }

}