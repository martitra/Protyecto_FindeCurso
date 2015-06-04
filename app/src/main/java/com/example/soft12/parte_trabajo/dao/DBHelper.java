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

	// columna of the CAU table
	public static final String TABLE_CAU = "cau";
	public static final String COLUMN_CAU_ID = "_id";
	public static final String COLUMN_CAU_NOMBRE = "nombre";

	//column of the Solucion table
	public static final String TABLE_SOLUCION = "solucion";
	public static final String COLUMN_SOLUCION_ID = "_id";
	public static final String COLUMN_SOLUCION_NOMBRE = "nombre";

	//column of the Cliente table
	public static final String TABLE_CLIENTE = "cliente";
	public static final String COLUMN_CLIENTE_ID = "_id";
	public static final String COLUMN_CLIENTE_NOMBRE = "nombre";

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

	// SQL statement of the cau table creation
	public static final String SQL_CREATE_TABLE_CAU = "CREATE TABLE " + TABLE_CAU + "("
			+ COLUMN_CAU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_CAU_NOMBRE + " TEXT NOT NULL "
			+ ");";

	// SQL statement of the solucion table creation
	public static final String SQL_CREATE_TABLE_SOLUCION = "CREATE TABLE " + TABLE_SOLUCION + "("
			+ COLUMN_SOLUCION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_SOLUCION_NOMBRE + " TEXT NOT NULL "
			+ ");";

	// SQL statement of the cliente table creation
	public static final String SQL_CREATE_TABLE_CLIENTE = "CREATE TABLE " + TABLE_CLIENTE + "("
			+ COLUMN_CLIENTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_CLIENTE_NOMBRE + " TEXT NOT NULL "
			+ ");";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(SQL_CREATE_TABLE_COCHE);
		database.execSQL(SQL_CREATE_TABLE_REPOSTAJE);
		database.execSQL(SQL_CREATE_TABLE_CAU);
		database.execSQL(SQL_CREATE_TABLE_SOLUCION);
		database.execSQL(SQL_CREATE_TABLE_CLIENTE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG,
				"Upgrading the database from version " + oldVersion + " to " + newVersion);
		// clear all data
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPOSTAJE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COCHE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAU);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOLUCION);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTE);
		
		// recreate the tables
		onCreate(db);
	}

}
