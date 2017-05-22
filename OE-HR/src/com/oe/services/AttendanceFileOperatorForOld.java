package com.oe.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.oe.models.Day;
import com.oe.models.Employee;
import com.oe.models.Month;
import com.oe.models.Week;

public class AttendanceFileOperatorForOld {
	
	DataFormatter formatter = new DataFormatter();
	private List<Employee> listOfEmployees = new ArrayList<>();
	
	private int idColumn = 0;
	private int surNameColumn = 2;
	private int nameColumn = 3;
	private int dateColumn = 4;
	private int inTimeColumn = 6;
	private int outTimeColumn = 8;
	
	
	
	public void readFile() throws IOException, ParseException {
		String excelFilePath = "attendanceOld.xls";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

		Workbook workbook = getWorkbook(inputStream, excelFilePath);
		Sheet firstSheet = workbook.getSheetAt(0);
		
		System.out.println("Start Process..");
		
		Iterator<Row> rowIterator = firstSheet.iterator();
		
		Employee employee = null ;
		Object[] obj = null;
		Object[] inOutObj = null;
		
		Map<String, Object[]> data = new LinkedHashMap<String, Object[]>();
		Map timings = null;
		
		
		while (rowIterator.hasNext()) {
			Row nextRow = rowIterator.next();
			if (!OperationHelperforOld.isRowEmpty(nextRow)) { 
				if (OperationHelperforOld.isRowContainsEmployeeId(nextRow)) {
					
					timings = new LinkedHashMap<>();
					employee = new Employee();
					obj = new Object[5];
					
					employee.setId(Integer.parseInt((nextRow.getCell(idColumn).getNumericCellValue()+"").split("\\.")[0]));
					obj[0] = Integer.parseInt((nextRow.getCell(idColumn).getNumericCellValue()+"").split("\\.")[0]);
					
					employee.setName(nextRow.getCell(nameColumn).getStringCellValue()+" "+nextRow.getCell(surNameColumn).getStringCellValue());
					obj[1] = nextRow.getCell(nameColumn).getStringCellValue()+" "+nextRow.getCell(surNameColumn).getStringCellValue();
					
					System.out.println(employee);
					
					data.put(obj[0].toString(), obj);
				}
				
				else if (nextRow.getCell(dateColumn)!=null && nextRow.getCell(dateColumn).getCellType() == Cell.CELL_TYPE_NUMERIC){
					
					
					if (nextRow.getCell(dateColumn).getCellType() == Cell.CELL_TYPE_NUMERIC) {

						double date = nextRow.getCell(dateColumn).getNumericCellValue();
						DecimalFormat decimalFormat = new DecimalFormat("#");
						SimpleDateFormat dt1 = new SimpleDateFormat("dd-MMM-yyyy");
						System.out.println(dt1.format(CalenderService.convertToDateOld(decimalFormat.format(date))));

						
						

						if (nextRow.getCell(inTimeColumn) != null) {
							inOutObj = new Object[2];
							System.out.println(nextRow.getCell(inTimeColumn).getStringCellValue());
							inOutObj[0] = nextRow.getCell(inTimeColumn).getStringCellValue();

							System.out.println(nextRow.getCell(outTimeColumn).getStringCellValue());
							inOutObj[1] = nextRow.getCell(outTimeColumn).getStringCellValue();
							timings.put(dt1.format(CalenderService.convertToDateOld(decimalFormat.format(date))), inOutObj);
							obj[2] = timings;
							System.out.println("ok");
						}
					}
					
					
				}
				
				
			}
			
			
		}
		
		System.out.println(data);
		XlsWriter.writeXlsFile(data);
		System.out.println("end");
	}
	
	
	private Workbook getWorkbook(FileInputStream inputStream, String excelFilePath) throws IOException {
		Workbook workbook = null;
		if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (excelFilePath.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}

		return workbook;
	}
	
}
