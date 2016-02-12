/**
 *   File Name: ReadExcel.java<br>
 *
 *   Adams, Nik<br>
 *   Created: Jan 21, 2016
 *
 */

package com.webdriver.na.junglesocks.util;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * ReadExcel //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 *
 * @author Adams, Nik
 * @version 1.0.0
 * @since 1.0
 *
 */
public class ReadExcel {

	public static String[][] getDataFromExcel(String xlsFilePath, String sheetName, String startPoint, String endPoint)
			throws BiffException, IOException {
		String[][] tabArray = null;
		// open an workbook
		Workbook workbook = Workbook.getWorkbook(new File(xlsFilePath));

		// open a sheet "Login"
		Sheet sheet = workbook.getSheet(sheetName);
		int startRow, startCol, endRow, endCol, ci, cj;
		// find a cell labeled with "StartPoint"
		Cell tableStart = sheet.findCell(startPoint);
		// get a row of that cell
		startRow = tableStart.getRow() + 1;
		// get a column of that cell
		startCol = tableStart.getColumn() + 1;
		// find a cell#2 labeled with "EndPoint"
		Cell tableEnd = sheet.findCell(endPoint);

		// get a row of that cell
		endRow = tableEnd.getRow();
		// get a row of that cell
		endCol = tableEnd.getColumn();

		tabArray = new String[(endRow - startRow)][(endCol - startCol)];
		for (int i = 0; i < endRow - 2; i++) {
			for (int j = 0; j < endCol - 1; j++) {
				tabArray[i][j] = sheet.getCell(startCol + j, startRow + i).getContents();
			}
		}
		return tabArray;
	}

}
