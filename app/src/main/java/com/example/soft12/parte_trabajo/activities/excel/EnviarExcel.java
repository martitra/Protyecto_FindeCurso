package com.example.soft12.parte_trabajo.activities.excel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
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
    private static File file;

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
        CreationHelper createHelper = wb.getCreationHelper();

        Cell c;

        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        Font font = wb.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
        //cs.setWrapText(true);
        cs.setVerticalAlignment(CellStyle.ALIGN_FILL);
        cs.setFont(font);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        CellStyle cs2 = wb.createCellStyle();
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        cs2.setVerticalAlignment(CellStyle.ALIGN_FILL);

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("hh:mm"));
        cellStyle.setVerticalAlignment(CellStyle.ALIGN_FILL);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

        CellStyle cellStyle2 = wb.createCellStyle();
        cellStyle2.setDataFormat(
                createHelper.createDataFormat().getFormat("hh:mm"));
        cellStyle2.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle2.setVerticalAlignment(CellStyle.ALIGN_FILL);
        cellStyle2.setFont(font);

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
        c.setCellValue("KMs INICIO");
        c.setCellStyle(cs);

        c = row.createCell(7);
        c.setCellValue("KMs FIN");
        c.setCellStyle(cs);

        c = row.createCell(8);
        c.setCellValue("FURGONETA");
        c.setCellStyle(cs);

        sheet1.setColumnWidth(0, 1300 * 3);
        sheet1.setColumnWidth(1, 1300 * 3);
        sheet1.setColumnWidth(2, 600 * 3);
        sheet1.setColumnWidth(3, 600 * 3);
        sheet1.setColumnWidth(4, 6800 * 3);
        sheet1.setColumnWidth(5, 2000 * 3);
        sheet1.setColumnWidth(6, 1000 * 3);
        sheet1.setColumnWidth(7, 1000 * 3);
        sheet1.setColumnWidth(8, 1200 * 3);

        for (int i = 0; i < diarioList.size(); i++) {
            Row row2 = sheet1.createRow(i + 1);

            c = row2.createCell(0);
            c.setCellValue(diarioList.get(i).getCau());
            c.setCellStyle(cs2);

            c = row2.createCell(1);
            c.setCellValue(diarioList.get(i).getCliente());
            c.setCellStyle(cs2);

            c = row2.createCell(2);
            Date dateini = new Date();
            try {
                dateini = dateFormat.parse(diarioList.get(i).getHoraIni());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.setCellValue(dateini);
            c.setCellStyle(cellStyle);

            c = row2.createCell(3);
            Date datefin = new Date();
            try {
                datefin = dateFormat.parse(diarioList.get(i).getHoraFin());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.setCellValue(datefin);
            c.setCellStyle(cellStyle);

            c = row2.createCell(4);
            c.setCellValue(diarioList.get(i).getSolucion());
            c.setCellStyle(cs2);

            c = row2.createCell(5);
            Date datedes = new Date();
            try {
                datedes = dateFormat.parse(diarioList.get(i).getDesplazamiento());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.setCellValue(datedes);
            c.setCellStyle(cellStyle);

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

        int max = diarioList.size();

        Row row3 = sheet1.createRow(16);
        c = row3.createCell(0);
        c.setCellValue("Horas reparación:");
        c.setCellStyle(cs2);
        c = row3.createCell(1);

        String strFormula = "";
        String strDespla = "";
        for (int e = 1; e <= max; e++) {
            if (max == 1) {
                strFormula = "D" + (e + 1) + "-C" + (e + 1);
                strDespla = "F2";
            } else {
                if (e == max) {
                    strFormula = strFormula + "(D" + (e + 1) + "-C" + (e + 1) + ")";
                    strDespla = "SUM(F2:F" + (e + 1) + ")";
                } else {
                    strFormula = strFormula + "(D" + (e + 1) + "-C" + (e + 1) + ")+";
                }
            }
        }

        c.setCellType(HSSFCell.CELL_TYPE_FORMULA);
        c.setCellFormula(strFormula);
        c.setCellStyle(cellStyle);

        Row row4 = sheet1.createRow(17);
        c = row4.createCell(0);
        c.setCellValue("Horas Desplazamiento:");
        c.setCellStyle(cs2);

        c = row4.createCell(1);
        c.setCellType(HSSFCell.CELL_TYPE_FORMULA);
        c.setCellFormula(strDespla);
        c.setCellStyle(cellStyle);

        Row row5 = sheet1.createRow(20);
        c = row5.createCell(0);
        c.setCellValue("Total Horas:");
        c.setCellStyle(cs);

        c = row5.createCell(1);
        c.setCellType(HSSFCell.CELL_TYPE_FORMULA);
        c.setCellFormula("B17+B18");
        c.setCellStyle(cellStyle2);

        HSSFPrintSetup printSetup = (HSSFPrintSetup) sheet1.getPrintSetup();
        sheet1.getPrintSetup().setFitWidth((short) 1);
        sheet1.getPrintSetup().setFitHeight((short) 0);
        sheet1.setAutobreaks(true);
        printSetup.setLandscape(true);

        //sheet1.autoSizeColumn(8);

        //Create a path where we will place our list of objects on external sotrage
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        setFile(file);
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

    public static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        EnviarExcel.file = file;
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
        lanzarEmail(diarioArrayList);
    }

    public void lanzarEmail(List<Diario> diarioList) {
        // TODO Auto-generated method stub

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        //TODO cambiar mail por el de Isa
        String[] to = {"martagalmangarcia@gmail.com", diarioList.get(0).getTecnico().getMail()};
        String subject = "Informe";
        String body = "Informe de " + diarioList.get(0).getTecnico().getNombre() + ".";
        i.putExtra(Intent.EXTRA_EMAIL, to);
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, body);
        File file = getFile();
        i.putExtra(Intent.EXTRA_STREAM,
                Uri.parse("file://" + file));
        startActivity(i);
    }
}
