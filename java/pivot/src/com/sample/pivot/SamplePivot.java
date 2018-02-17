package com.sample.pivot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*import org.apache.poi.hssf.util.AreaReference;
import org.apache.poi.ss.usermodel.DataConsolidateFunction;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
*/
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.SpreadsheetVersion;


@SuppressWarnings("deprecation")
public class SamplePivot {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

        FileInputStream input_document;
		try {
			input_document = new FileInputStream(new File("D:\\PivotSample.xlsx"));
		 
        /* Create a POI XSSFWorkbook Object from the input file */
        XSSFWorkbook my_xlsx_workbook = new XSSFWorkbook(input_document); 
        /* Read Data to be Pivoted - we have only one worksheet */
        XSSFSheet sheet = my_xlsx_workbook.getSheetAt(0); 
        /* Get the reference for Pivot Data */
        AreaReference a=new AreaReference("A1:D4", SpreadsheetVersion.EXCEL2007);
        /* Find out where the Pivot Table needs to be placed */
        CellReference b=new CellReference("I5");
        /* Create Pivot Table */
        XSSFPivotTable pivotTable = sheet.createPivotTable(a,b);
        /* Add filters */
      //  pivotTable.addReportFilter(0);
       // pivotTable.addRowLabel(0);
        pivotTable.addRowLabel(0);
       pivotTable.addColumnLabel(DataConsolidateFunction.SUM, 3); 
        /* Write Pivot Table to File */
        FileOutputStream output_file = new FileOutputStream(new File("D:\\Sample.xlsx")); 
        my_xlsx_workbook.write(output_file);
        input_document.close(); 
        output_file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}

}
