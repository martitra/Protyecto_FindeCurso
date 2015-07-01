package com.example.soft12.parte_trabajo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.soft12.parte_trabajo.model.Login;

import java.sql.SQLException;

/**
 * Created by soft12 on 30/06/2015.
 */
public class LoginDAO {

    public static final String TAG = "LoginDAO";
    public SQLiteDatabase mDatabase;
    private String[] mAllColumns = { DBHelper.COLUMN_USUARIO_ID, DBHelper.COLUMN_USUARIO_NOMBRE,
            DBHelper.COLUMN_USUARIO_PASS, DBHelper.COLUMN_USUARIO_EMAIL };
    // Context of the application using the database.
    private Context mContext;
    // Database open/upgrade helper
    private DBHelper mDbHelper;

    public LoginDAO(Context context){
        mDbHelper = new DBHelper(context);
        this.mContext =  context;
        // open the database
        try {
            open();
        }
        catch(SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public  LoginDAO open() throws SQLException
    {
        mDatabase = mDbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        mDatabase.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return mDatabase;
    }

    public Login insertEntry(String userName,String password, String mail)
    {
        ContentValues values = new ContentValues();
        // Assign values for each row.
        values.put(DBHelper.COLUMN_USUARIO_NOMBRE, userName);
        values.put(DBHelper.COLUMN_USUARIO_PASS, password);
        values.put(DBHelper.COLUMN_USUARIO_EMAIL, mail);

        long insertId = mDatabase.insert(DBHelper.TABLE_USUARIO, null, values);

        Cursor cursor = mDatabase.query(DBHelper.TABLE_USUARIO,
                mAllColumns, DBHelper.COLUMN_USUARIO_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Login login = cursorToLogin(cursor);
        cursor.close();
        return login;
    }
    public Login getLoginById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_USUARIO, mAllColumns,
                DBHelper.COLUMN_USUARIO_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursorToLogin(cursor);
    }

    public Login getSinlgeEntry(String userName)
    {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_USUARIO, mAllColumns,
                DBHelper.COLUMN_USUARIO_EMAIL + " = ? ",
                new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
        }
        cursor.moveToFirst();
        return cursorToLogin(cursor);

    }

    protected Login cursorToLogin(Cursor cursor) {
        Login login = new Login();
        login.setcId(cursor.getLong(0));
        login.setNombre(cursor.getString(1));
        login.setPass(cursor.getString(2));
        login.setMail(cursor.getString(3));
        return login;
    }

    public int updateLogin(Login l) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_USUARIO_NOMBRE, l.getNombre());
        values.put(DBHelper.COLUMN_USUARIO_PASS, l.getPass());
        values.put(DBHelper.COLUMN_USUARIO_EMAIL, l.getMail());

        return mDatabase.update(DBHelper.TABLE_USUARIO, values, DBHelper.COLUMN_USUARIO_ID + " = ?",
                new String[] { String.valueOf(l.getcId()) });
    }
}
