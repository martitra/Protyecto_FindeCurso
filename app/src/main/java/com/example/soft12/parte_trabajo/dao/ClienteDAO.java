package com.example.soft12.parte_trabajo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.soft12.parte_trabajo.model.Cliente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soft12 on 03/06/2015.
 */
public class ClienteDAO {

    public static final String TAG = "ClienteDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = { DBHelper.COLUMN_CLIENTE_ID,
            DBHelper.COLUMN_CLIENTE_NOMBRE };

    public ClienteDAO(Context context) {
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

    public Cliente createCliente(String nombre) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_CLIENTE_NOMBRE, nombre);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_CLIENTE, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_CLIENTE, mAllColumns,
                DBHelper.COLUMN_CLIENTE_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Cliente newCliente = cursorToCliente(cursor);
        cursor.close();
        return newCliente;
    }

    public void deleteCliente(Cliente cliente) {
        long id = cliente.getcId();

        System.out.println("the deleted cliente has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_CLIENTE, DBHelper.COLUMN_CLIENTE_ID
                + " = " + id, null);
    }

    public List<Cliente> getAllClientes() {
        List<Cliente> listClientes = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_CLIENTE,
                mAllColumns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Cliente cliente = cursorToCliente(cursor);
                listClientes.add(cliente);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listClientes;
    }

    public Cursor fetchItemsByDesc(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = mDatabase.query(true, DBHelper.TABLE_CLIENTE,
                new String[] {DBHelper.COLUMN_CLIENTE_ID,
                        DBHelper.COLUMN_CLIENTE_NOMBRE},
                DBHelper.COLUMN_CLIENTE_NOMBRE + " like '%" + inputText + "%'", null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cliente getClienteById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_CLIENTE, mAllColumns,
                DBHelper.COLUMN_CLIENTE_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursorToCliente(cursor);
    }

    public Cliente getSinlgeClienteEntry(String nombre)
    {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_CLIENTE, mAllColumns,
                DBHelper.COLUMN_CLIENTE_NOMBRE + " = ? ",
                new String[]{nombre}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
        }
        cursor.moveToFirst();
        return cursorToCliente(cursor);

    }

    protected Cliente cursorToCliente(Cursor cursor) {
        Cliente cliente = new Cliente();
        cliente.setcId(cursor.getLong(0));
        cliente.setnNombre(cursor.getString(1));
        return cliente;
    }
    public int updateCliente(Cliente c) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_CLIENTE_NOMBRE, c.getnNombre());

        return mDatabase.update(DBHelper.TABLE_CLIENTE, values, DBHelper.COLUMN_CLIENTE_ID + " = ?",
                new String[] { String.valueOf(c.getcId()) });
    }

}
