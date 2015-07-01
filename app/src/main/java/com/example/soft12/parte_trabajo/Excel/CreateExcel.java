package com.example.soft12.parte_trabajo.Excel;

/**
 * Created by soft12 on 29/06/2015.
 */

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class CreateExcel {
    private WritableCellFormat times;
    private String inputFile;

    public CreateExcel() {
    }

    public void setOutputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void write() throws IOException, WriteException {
        File file = new File(this.inputFile);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("Report", 0);
        WritableSheet excelSheet = workbook.getSheet(0);
        this.createLabel(excelSheet);
        this.createContent(excelSheet);
        workbook.write();
        workbook.close();
    }

    private void createLabel(WritableSheet sheet) throws WriteException {
        WritableFont times12pt = new WritableFont(WritableFont.ARIAL, 12);
        this.times = new WritableCellFormat(times12pt);
        WritableFont times10ptBold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false);
        WritableCellFormat timesBold = new WritableCellFormat(times10ptBold);
        timesBold.setWrap(true);
        WritableCellFormat cellFormat = new WritableCellFormat(timesBold);
        cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        cellFormat.setAlignment(Alignment.CENTRE);
        sheet.setColumnGroup(0,8,true);
        sheet.setColumnView(1, 16);
        sheet.setColumnView(2, 20);
        sheet.setColumnView(3, 10);
        sheet.setColumnView(4, 10);
        sheet.setColumnView(5, 70);
        sheet.setColumnView(6, 22);
        sheet.setColumnView(7, 13);
        sheet.setColumnView(8, 13);
        sheet.setColumnView(9, 13);
        sheet.addCell(new Label(1, 0, "CAU", cellFormat));
        sheet.addCell(new Label(2, 0, "CLIENTE", cellFormat));
        sheet.addCell(new Label(3, 0, "HORA INICIO", cellFormat));
        sheet.addCell(new Label(4, 0, "HORA FIN", cellFormat));
        sheet.addCell(new Label(5, 0, "SOLUCION", cellFormat));
        sheet.addCell(new Label(6, 0, "DESPLAZAMIENTO", cellFormat));
        sheet.addCell(new Label(7, 0, "FURGONETA", cellFormat));
        sheet.addCell(new Label(8, 0, "KILOMETROS INICIO", cellFormat));
        sheet.addCell(new Label(9, 0, "KILOMETROS FIN", cellFormat));
        CellView cv = new CellView();
        cv.setFormat(this.times);
        cv.setFormat(timesBold);
        cv.setAutosize(true);
    }

    private void createContent(WritableSheet sheet) throws WriteException {

        // aquí se llamaría a los datos para poner

        for(int buf = 1; buf < 10; ++buf) {
            this.addNumber(sheet, 0, buf, buf + 10);
            this.addNumber(sheet, 1, buf, buf * buf);
        }

        StringBuffer var5 = new StringBuffer();
        var5.append("SUM(A2:A10)");
        Formula f = new Formula(0, 10, var5.toString());
        sheet.addCell(f);
        var5 = new StringBuffer();
        var5.append("SUM(B2:B10)");
        f = new Formula(1, 10, var5.toString());
        sheet.addCell(f);

        for(int i = 12; i < 20; ++i) {
            this.addLabel(sheet, 0, i, "Boring text " + i);
            this.addLabel(sheet, 1, i, "Another text");
        }


    }

    /*
    private void addCaption(WritableSheet sheet, int column, int row, String s) throws WriteException {
        Label label = new Label(column, row, s, this.timesBold);
        sheet.addCell(label);
    }
    */

    private void addNumber(WritableSheet sheet, int column, int row, Integer integer) throws WriteException {
        jxl.write.Number number = new Number(column, row, (double) integer, this.times);
        sheet.addCell(number);
    }

    private void addLabel(WritableSheet sheet, int column, int row, String s) throws WriteException {
        Label label = new Label(column, row, s, this.times);
        sheet.addCell(label);
    }

    public static void main(String[] args) throws WriteException, IOException {
        CreateExcel test = new CreateExcel();
        test.setOutputFile("lars.xls");
        test.write();
        System.out.println("Please check the result file under c:/temp/lars.xls ");
    }
}