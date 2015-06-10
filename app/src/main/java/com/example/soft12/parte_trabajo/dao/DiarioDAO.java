package com.example.soft12.parte_trabajo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.soft12.parte_trabajo.model.CAU;
import com.example.soft12.parte_trabajo.model.Cliente;
import com.example.soft12.parte_trabajo.model.Diario;
import com.example.soft12.parte_trabajo.model.Solucion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soft12 on 04/06/2015.
 */
public class DiarioDAO {

    public static final String TAG = "DiarioDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = { DBHelper.COLUMN_DIARIO_ID,
                                    DBHelper.COLUMN_DIARIO_FECHA,
                                    DBHelper.COLUMN_DIARIO_CAU,
                                    DBHelper.COLUMN_DIARIO_CLIENTE,
                                    DBHelper.COLUMN_DIARIO_SOLUCION,
                                    DBHelper.COLUMN_DIARIO_HORA_INI,
                                    DBHelper.COLUMN_DIARIO_HORA_FIN,
                                    DBHelper.COLUMN_DIARIO_VIAJE,
                                    DBHelper.COLUMN_DIARIO_KMS};

    public DiarioDAO(Context context) {
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

    public Diario createDiario(String fecha, long cauId, long clienteId, long solucionId,
                               String horaIni, String horaFin, double viaje, double kms) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_DIARIO_FECHA, fecha);
        values.put(DBHelper.COLUMN_DIARIO_CAU, cauId);
        values.put(DBHelper.COLUMN_DIARIO_CLIENTE, clienteId);
        values.put(DBHelper.COLUMN_DIARIO_SOLUCION, solucionId);
        values.put(DBHelper.COLUMN_DIARIO_HORA_INI, horaIni);
        values.put(DBHelper.COLUMN_DIARIO_HORA_FIN, horaFin);
        values.put(DBHelper.COLUMN_DIARIO_VIAJE, viaje);
        values.put(DBHelper.COLUMN_DIARIO_KMS, kms);
        long insertId = mDatabase.insert(DBHelper.TABLE_DIARIO, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_DIARIO,
                mAllColumns, DBHelper.COLUMN_DIARIO_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Diario newDiario = cursorToDiario(cursor);
        cursor.close();
        return newDiario;
    }

    public void deleteDiario(Diario diario) {
        long id = diario.getId();
        System.out.println("the deleted diario has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_DIARIO, DBHelper.COLUMN_DIARIO_ID + " = " + id, null);
    }

    public List<Diario> getAllDiario() {
        List<Diario> listDiario = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_DIARIO,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Diario diario = cursorToDiario(cursor);
            listDiario.add(diario);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listDiario;
    }

    private Diario cursorToDiario(Cursor cursor) {
        Diario diario = new Diario();
        // id,fecha cau, cliente, solucion, fechaini, fechafin, viaje, kms
        diario.setId(cursor.getLong(0));
        diario.setFecha(cursor.getString(1));

        // get The cau by id
        long cauId = cursor.getLong(2);
        CAUDAO caudao = new CAUDAO(mContext);
        CAU cau = caudao.getCAUById(cauId);
        if(cau != null)
            diario.setCau(cau);

        // get the cliente by id
        long clienteId = cursor.getLong(3);
        ClienteDAO clienteDAO = new ClienteDAO(mContext);
        Cliente cliente = clienteDAO.getClienteById(clienteId);
        if(cliente != null)
            diario.setCliente(cliente);

        //get the solucion by id
        long solucionId = cursor.getLong(4);
        SolucionDAO solucionDAO = new SolucionDAO(mContext);
        Solucion solucion = solucionDAO.getSolucionById(solucionId);
        if(solucion != null)
            diario.setSolucion(solucion);

        diario.setHoraIni(cursor.getString(5));
        diario.setHoraFin(cursor.getString(6));
        diario.setViaje(cursor.getString(7));
        diario.setKms(Double.parseDouble(cursor.getString(8)));



        return diario;
    }

    public int updateDiario(Diario diario) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_DIARIO_FECHA, diario.getFecha());
        values.put(DBHelper.COLUMN_DIARIO_CAU, diario.getCau().getCauId());
        values.put(DBHelper.COLUMN_DIARIO_CLIENTE, diario.getCliente().getcId());
        values.put(DBHelper.COLUMN_DIARIO_SOLUCION, diario.getSolucion().getcId());
        values.put(DBHelper.COLUMN_DIARIO_HORA_INI, diario.getHoraIni());
        values.put(DBHelper.COLUMN_DIARIO_HORA_FIN, diario.getHoraFin());
        values.put(DBHelper.COLUMN_DIARIO_VIAJE, diario.getViaje());
        values.put(DBHelper.COLUMN_DIARIO_KMS, diario.getKms());

        return mDatabase.update(DBHelper.TABLE_DIARIO, values, DBHelper.COLUMN_DIARIO_ID + " = ?",
                new String[] { String.valueOf(diario.getId()) });
    }

}
