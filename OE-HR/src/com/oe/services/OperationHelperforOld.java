package com.oe.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class OperationHelperforOld {
	
	public static boolean isRowContainsEmployeeId(Row row) {
		boolean rowContainsEmployeeId = false;
		if (row != null) {
			// check row that contains empId
			Cell cell = row.getCell(0);
			if (cell != null) {
				if (cell.getCellType() != Cell.CELL_TYPE_BLANK && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					if (OperationHelperforOld.isDouble(cell.getNumericCellValue()+"")) {
						rowContainsEmployeeId = true;
					}
				}
				return rowContainsEmployeeId;
			}
		}
		return rowContainsEmployeeId;
	}

	private static boolean isDouble(String numericCellValue) {
		try {
			double i = Double.parseDouble(numericCellValue);
			return true;
		}
		catch (NumberFormatException er) {
			return false;
		}
	}

	public static boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return false;
		}
		return true;
	}
}
