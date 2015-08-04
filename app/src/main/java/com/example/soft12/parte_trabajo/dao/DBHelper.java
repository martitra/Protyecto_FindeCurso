package com.example.soft12.parte_trabajo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {


	public static final String TAG = "DBHelper";
	//colums of the daiario table
	public static final String TABLE_DIARIO = "diario";
	public static final String COLUMN_DIARIO_ID = "_id";
	public static final String COLUMN_DIARIO_FECHA = "fecha";
	public static final String COLUMN_DIARIO_CAU = "cau";
	// después cambiar el cliente text por un integer para que lo coja de la BD
	// aquí non se pon o técnico porque xa hai unha tabla a parte chamada tecnico_diario
	public static final String COLUMN_DIARIO_CLIENTE_ID = "cliente_id";
	public static final String COLUMN_DIARIO_SOLUCION = "solucion";
	public static final String COLUMN_DIARIO_HORA_INI = "fecha_ini";
	public static final String COLUMN_DIARIO_HORA_FIN = "fecha_fin";
	public static final String COLUMN_DIARIO_DESPLAZAMIENTO = "desplazamiento";
	public static final String COLUMN_DIARIO_KMS_INI = "kms_ini";
	public static final String COLUMN_DIARIO_KMS_FIN = "kms_fin";
	public static final String COLUMN_DIARIO_COCHE_ID = "coche_id";
	//column of the Cliente table
	public static final String TABLE_CLIENTE = "cliente";
	public static final String COLUMN_CLIENTE_ID = "_id";
	public static final String COLUMN_CLIENTE_CODIGO = "codigo";
	public static final String COLUMN_CLIENTE_NOMBRE = "nombre";
	// columns of the repostaje table
	public static final String TABLE_REPOSTAJE = "repostaje";
	public static final String COLUMN_REPOSTAJE_ID = "_id";
	public static final String COLUMN_REPOSTAJE_FECHA = "fecha";
	public static final String COLUMN_REPOSTAJE_EUROS = "euros";
	public static final String COLUMN_REPOSTAJE_EUROS_LITRO = "euros_litro";
	public static final String COLUMN_REPOSTAJE_LITROS = "litros";
	public static final String COLUMN_REPOSTAJE_COCHE_ID = "coche_id";
	public static final String COLUMN_REPOSTAJE_TECNICO_ID = "tecnico_id";
	// columns of the coches table
	public static final String TABLE_COCHE = "coche";
	public static final String COLUMN_COCHE_ID = "_id";
	public static final String COLUMN_COCHE_MATRICULA = "matricula";
	public static final String COLUMN_COCHE_KMS = "kms";
	// columns of the usuario table
	public static final String TABLE_TECNICO = "tecnico";
	public static final String COLUMN_TECNICO_ID = "_id";
	public static final String COLUMN_TECNICO_NOMBRE = "tecnico_nombre";
	public static final String COLUMN_TECNICO_PASS = "pass";
	public static final String COLUMN_TECNICO_EMAIL = "mail";
	// columns of the usuario_diairo table
	public static final String TABLE_TECNICO_DIARIO = "tecnico_diario";
	public static final String COLUMN_TD_ID = "_id";
	public static final String COLUMN_TD_TECNICO_ID = "tecnico_id";
	public static final String COLUMN_TD_DIARIO_ID = "diario_id";
	public static final String COLUMN_TD_FECHA = "fecha";
	// SQL statement of the cau table creation
	public static final String SQL_CREATE_TABLE_DIARIO = "CREATE TABLE " + TABLE_DIARIO + "("
			+ COLUMN_DIARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_DIARIO_FECHA + " TEXT NOT NULL, "
			+ COLUMN_DIARIO_CAU + " TEXT, "
			+ COLUMN_DIARIO_SOLUCION + " TEXT NOT NULL, "
			+ COLUMN_DIARIO_CLIENTE_ID + " TEXT NOT NULL, "
			+ COLUMN_DIARIO_HORA_INI + " TEXT NOT NULL, "
			+ COLUMN_DIARIO_HORA_FIN + " TEXT NOT NULL, "
			+ COLUMN_DIARIO_DESPLAZAMIENTO + " TEXT NOT NULL, "
			+ COLUMN_DIARIO_KMS_INI + " REAL NOT NULL, "
			+ COLUMN_DIARIO_KMS_FIN + " REAL NOT NULL, "
			+ COLUMN_DIARIO_COCHE_ID + " LONG NOT NULL "
			+ ");";
	// SQL statement of the cliente table creation
	public static final String SQL_CREATE_TABLE_CLIENTE = "CREATE TABLE " + TABLE_CLIENTE + "("
			+ COLUMN_CLIENTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_CLIENTE_CODIGO + " TEXT NOT NULL, "
			+ COLUMN_CLIENTE_NOMBRE + " TEXT NOT NULL "
			+ ");";
	private static final String DATABASE_NAME = "parte.db";
	private static final int DATABASE_VERSION = 21;
	// SQL statement of the repostaje table creation
	private static final String SQL_CREATE_TABLE_REPOSTAJE = "CREATE TABLE " + TABLE_REPOSTAJE + "("
			+ COLUMN_REPOSTAJE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_REPOSTAJE_FECHA + " TEXT NOT NULL, "
			+ COLUMN_REPOSTAJE_EUROS + " REAL NOT NULL, "
			+ COLUMN_REPOSTAJE_EUROS_LITRO + " REAL NOT NULL, "
			+ COLUMN_REPOSTAJE_LITROS + " REAL NOT NULL, "
			+ COLUMN_REPOSTAJE_COCHE_ID + " INTEGER NOT NULL, "
			+ COLUMN_REPOSTAJE_TECNICO_ID + " INTEGER NOT NULL "
			+");";

	// SQL statement of the coches table creation
	private static final String SQL_CREATE_TABLE_COCHE = "CREATE TABLE " + TABLE_COCHE + "("
			+ COLUMN_COCHE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_COCHE_MATRICULA + " TEXT NOT NULL, "
			+ COLUMN_COCHE_KMS + " INTEGER NOT NULL "
			+");";

	// SQL statement of the coches table creation
	private static final String SQL_CREATE_TABLE_TECNICO = "CREATE TABLE " + TABLE_TECNICO + "("
			+ COLUMN_TECNICO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_TECNICO_NOMBRE + " TEXT NOT NULL, "
			+ COLUMN_TECNICO_PASS + " TEXT NOT NULL, "
			+ COLUMN_TECNICO_EMAIL + " TEXT NOT NULL "
			+");";
	// SQL statement of the tecnico_diairo table creation
	private static final String SQL_CREATE_TABLE_TECNICO_DIARIO = "CREATE TABLE "
			+ TABLE_TECNICO_DIARIO + "("
			+ COLUMN_TD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_TD_TECNICO_ID + " INTEGER NOT NULL, "
			+ COLUMN_TD_DIARIO_ID + " INTEGER NOT NULL, "
			+ COLUMN_TD_FECHA + " TEXT NOT NULL "
			+ ");";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(SQL_CREATE_TABLE_COCHE);
		database.execSQL(SQL_CREATE_TABLE_REPOSTAJE);
		database.execSQL(SQL_CREATE_TABLE_CLIENTE);
		database.execSQL(SQL_CREATE_TABLE_DIARIO);
		database.execSQL(SQL_CREATE_TABLE_TECNICO);
		database.execSQL(SQL_CREATE_TABLE_TECNICO_DIARIO);

		database.execSQL("INSERT INTO tecnico (tecnico_nombre,pass,mail) VALUES('Pepe','123.','pepe@pepe.com') ");
		database.execSQL("INSERT INTO tecnico (tecnico_nombre,pass,mail) VALUES('Marta','123.','marta@marta.com') ");

		database.execSQL("INSERT INTO cliente (nombre,codigo) VALUES('Eroski','9654-8')");
		database.execSQL("INSERT INTO cliente (nombre,codigo) VALUES('Vegalsa','12-UI')");

		database.execSQL("INSERT INTO coche (matricula,kms) VALUES('Ninguno',0)");
		database.execSQL("INSERT INTO coche (matricula,kms) VALUES('8020 BJY',60)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG,
				"Upgrading the database from version " + oldVersion + " to " + newVersion);
		// clear all data
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPOSTAJE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COCHE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIARIO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TECNICO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TECNICO_DIARIO);
		// recreate the tables
		onCreate(db);
	}

}
