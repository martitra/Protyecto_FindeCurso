package com.example.soft12.parte_trabajo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	public static final String TAG = "DBHelper";

	// columns of the coches table
	public static final String TABLE_COCHE = "coche";
	public static final String COLUMN_COCHE_ID = "_id";
	public static final String COLUMN_COCHE_MATRICULA = "matricula";

	// columns of the repostaje table
	public static final String TABLE_REPOSTAJE = "repostaje";
	public static final String COLUMN_REPOSTAJE_ID = "_id";
	public static final String COLUMN_REPOSTAJE_FECHA = "fecha";
	public static final String COLUMN_REPOSTAJE_EUROS = "euros";
	public static final String COLUMN_REPOSTAJE_EUROS_LITRO = "euros_litro";
	public static final String COLUMN_REPOSTAJE_LITROS = "litros";
	public static final String COLUMN_REPOSTAJE_COCHE_ID = "coche_id";

	private static final String DATABASE_NAME = "parte.db";
	private static final int DATABASE_VERSION = 2;

	// SQL statement of the repostaje table creation
	private static final String SQL_CREATE_TABLE_REPOSTAJE = "CREATE TABLE " + TABLE_REPOSTAJE + "("
			+ COLUMN_REPOSTAJE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_REPOSTAJE_FECHA + " TEXT NOT NULL, "
			+ COLUMN_REPOSTAJE_EUROS + " REAL NOT NULL, "
			+ COLUMN_REPOSTAJE_EUROS_LITRO + " REAL NOT NULL, "
			+ COLUMN_REPOSTAJE_LITROS + " REAL NOT NULL, "
			+ COLUMN_REPOSTAJE_COCHE_ID + " INTEGER NOT NULL "
			+");";

	// SQL statement of the coches table creation
	private static final String SQL_CREATE_TABLE_COCHE = "CREATE TABLE " + TABLE_COCHE + "("
			+ COLUMN_COCHE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_COCHE_MATRICULA + " TEXT NOT NULL "
			+");";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(SQL_CREATE_TABLE_COCHE);
		database.execSQL(SQL_CREATE_TABLE_REPOSTAJE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG,
				"Upgrading the database from version " + oldVersion + " to " + newVersion);
		// clear all data
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPOSTAJE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COCHE);
		
		// recreate the tables
		onCreate(db);
	}

	/*public DBHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}*/
}
