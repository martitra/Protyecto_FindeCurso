package com.example.soft12.parte_trabajo.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.dao.DiarioDAO;
import com.example.soft12.parte_trabajo.model.Diario;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by soft12 on 06/07/2015.
 */
public class EnviarExcel extends Activity {

    public static final String TAG = "Enviar Excel";

    EditText fecha;
    Calendar calendar = Calendar.getInstance();

    /* DATE */
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.YEAR, year);

            fecha.setText(String.format("%02d/%02d/%4d", dayOfMonth, (monthOfYear + 1), year));

        }
    };

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    public static boolean isExternalSortageAvaliable() {
        String extSorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extSorageState);
    }

    public static boolean saveExcelFile(String filename, List<Diario> diarioList) {
        if (!isExternalSortageAvaliable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
            return false;
        }
        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c;

        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        Font font = wb.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
        cs.setFont(font);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        CellStyle cs2 = wb.createCellStyle();
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        //new sheet
        Sheet sheet1;
        sheet1 = wb.createSheet("Informe");

        // Generate dcolumn heading
        Row row = sheet1.createRow(0);

        c = row.createCell(0);
        c.setCellValue("CAU");
        c.setCellStyle(cs);

        c = row.createCell(1);
        c.setCellValue("CLIENTE");
        c.setCellStyle(cs);

        c = row.createCell(2);
        c.setCellValue("HORA INICIO");
        c.setCellStyle(cs);

        c = row.createCell(3);
        c.setCellValue("HORA FIN");
        c.setCellStyle(cs);

        c = row.createCell(4);
        c.setCellValue("SOLUCION");
        c.setCellStyle(cs);

        c = row.createCell(5);
        c.setCellValue("DESPLAZAMIENTO");
        c.setCellStyle(cs);

        c = row.createCell(6);
        c.setCellValue("FURGONETA");
        c.setCellStyle(cs);

        c = row.createCell(7);
        c.setCellValue("KMs INICIO");
        c.setCellStyle(cs);

        c = row.createCell(8);
        c.setCellValue("KMs FIN");
        c.setCellStyle(cs);

        sheet1.setColumnWidth(0, 1600 * 3);
        sheet1.setColumnWidth(1, 2000 * 3);
        sheet1.setColumnWidth(2, 1000 * 3);
        sheet1.setColumnWidth(3, 1000 * 3);
        sheet1.setColumnWidth(4, 7000 * 3);
        sheet1.setColumnWidth(5, 2000 * 3);
        sheet1.setColumnWidth(6, 1300 * 3);
        sheet1.setColumnWidth(7, 1300 * 3);
        sheet1.setColumnWidth(8, 1300 * 3);

        for (int i = 0; i < diarioList.size(); i++) {
            Row row2 = sheet1.createRow(i + 1);

            //sheet.addCell(new Label(y+1,i+1, diarios.get(y + 1).toString(),cellFormat));
            c = row2.createCell(0);
            c.setCellValue(diarioList.get(i).getCau());
            c.setCellStyle(cs2);

            c = row2.createCell(1);
            c.setCellValue(diarioList.get(i).getCliente());
            c.setCellStyle(cs2);

            c = row2.createCell(2);
            Date dateini = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
            try {
                dateini = dateFormat.parse(diarioList.get(i).getHoraIni());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.setCellValue(dateini);
            c.setCellStyle(cs2);

            c = row2.createCell(3);
            Date datefin = new Date();
            try {
                datefin = dateFormat.parse(diarioList.get(i).getHoraFin());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.setCellValue(datefin);
            c.setCellStyle(cs2);

            c = row2.createCell(4);
            c.setCellValue(diarioList.get(i).getSolucion());
            c.setCellStyle(cs2);

            c = row2.createCell(5);
            c.setCellValue(diarioList.get(i).getDesplazamiento());
            c.setCellStyle(cs2);

            c = row2.createCell(6);
            c.setCellValue(diarioList.get(i).getKmIni());
            c.setCellStyle(cs2);

            c = row2.createCell(7);
            c.setCellValue(diarioList.get(i).getKmFin());
            c.setCellStyle(cs2);

            c = row2.createCell(8);
            c.setCellValue(diarioList.get(i).getCoche().getMatricula());
            c.setCellStyle(cs2);
        }

        Row row3 = sheet1.createRow(9);
        c = row3.createCell(3);
        String strFormula = "D2-C2";
        c.setCellType(HSSFCell.CELL_TYPE_FORMULA);
        c.setCellFormula(strFormula);
        c.setCellStyle(cs2);

        //Create a path where we will place our list of objects on external sotrage
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file: " + file);
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_excel);

        initViews();
    }

    private void initViews() {
        this.fecha = (EditText) findViewById(R.id.txt_fecha_excel);

        /* FECHA */
        this.fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(v.getContext(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        this.fecha.setText(String.format("%02d/%02d/%4d", calendar.get(Calendar.DAY_OF_MONTH),
                (calendar.get(Calendar.MONTH) + 1),
                calendar.get(Calendar.YEAR)));
    }

    public void Enviar(View view) {
        SharedPreferences prefs = this.
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        long tecnicoid = prefs.getLong("trabajadorid", 0);

        // Buscar resultado por fecha y por técnico logueado.
        DiarioDAO diarioDAO = new DiarioDAO(this);
        List<Diario> diarioArrayList = diarioDAO.getDateDiario(
                String.valueOf(fecha.getText().toString()), tecnicoid);

        saveExcelFile("lars.xls", diarioArrayList);

        /*CreateExcel test = new CreateExcel();
        try {
            if (!diarioArrayList.isEmpty()) {
                test.write(this, diarioArrayList);
                System.out.println("Please check the result file under lars.xls ");
            }
            else {
                System.out.println("No hay registros para la fecha "+String.valueOf(fecha.getText()));
            }
        } catch (IOException | WriteException e) {
            e.printStackTrace();
        }
        */
    }

}
