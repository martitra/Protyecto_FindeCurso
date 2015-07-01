package com.example.soft12.parte_trabajo.Excel;

/**
 * Created by soft12 on 29/06/2015.
 */

import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {
    private String inputFile;

    public ReadExcel() {
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void read() throws IOException {
        File inputWorkbook = new File(this.inputFile);

        try {
            Workbook w = Workbook.getWorkbook(inputWorkbook);
            Sheet e = w.getSheet(0);

            for(int j = 0; j < e.getColumns(); ++j) {
                for(int i = 0; i < e.getRows(); ++i) {
                    Cell cell = e.getCell(j, i);
                    CellType type = cell.getType();
                    if(type == CellType.LABEL) {
                        System.out.println("I got a label " + cell.getContents());
                    }

                    if(type == CellType.NUMBER) {
                        System.out.println("I got a number " + cell.getContents());
                    }
                }
            }
        } catch (BiffException var8) {
            var8.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        ReadExcel test = new ReadExcel();
        test.setInputFile("lars.xls");
        test.read();
    }
}

