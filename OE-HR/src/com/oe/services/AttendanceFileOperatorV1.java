package com.oe.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.oe.models.Day;
import com.oe.models.Employee;
import com.oe.models.Month;
import com.oe.models.Week;

public class AttendanceFileOperatorV1 {
	
	DataFormatter formatter = new DataFormatter();
	private int dateColumnNumber = 2;
	private List<Employee> listOfEmployees = new ArrayList<>();
	private int inOutStartColumnNumber = 3;
	private int inOutEndColumnNumber = 32;
	private int wfhColumnNumber = 34;
	

	public void readFile() throws IOException, ParseException {
		String excelFilePath = "attendanceNew.xls";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

		Workbook workbook = getWorkbook(inputStream, excelFilePath);
		Sheet firstSheet = workbook.getSheetAt(0);
		
		System.out.println("Start Process..");
		
		Iterator<Row> rowIterator = firstSheet.iterator();
		Employee employee = null ;
		Month month = null;
		Week week = null;
		List<Day> days = null;
		List<Week> weeks = null;
		
		while (rowIterator.hasNext()) {
			Row nextRow = rowIterator.next();
			if (!OperationHelper.isRowEmpty(nextRow)) { 	// ignore empty rows.
				if (OperationHelper.isRowContainsEmployeeId(nextRow)) { //check row that contains empId
					//created all things for an employee
					employee = new Employee();
					weeks = new LinkedList<>();
					days =  new LinkedList<>();
					month = new Month();
					week = new Week();
					Day day = new Day();
					listOfEmployees.add(employee);
					
					//set id & name
					employee.setId(Integer.parseInt(nextRow.getCell(0).getStringCellValue()));
					employee.setName(nextRow.getCell(1).getStringCellValue());
					
					
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					List<Date> inOutList = new ArrayList<>();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if (cell.getColumnIndex() == dateColumnNumber) {  //this check is to create new week 
							day.setDayName(CalenderService.getNameOfDay(CalenderService.convertToDate(formatter.formatCellValue(cell))));
							if (CalenderService.getDayOfWeek(CalenderService.convertToDate(formatter.formatCellValue(cell))) == 2) {
								week = new Week();
								days =  new LinkedList<>();
								week.setDays(days);
								weeks.add(week);
								days.clear();
							}
							
							
						}
						if (cell.getColumnIndex() >= inOutStartColumnNumber	&& cell.getColumnIndex() <= inOutEndColumnNumber) {
							//calculation of in out and add it to dayOfWeek
								inOutList.add(cell.getDateCellValue());
						}
						
						if (cell.getColumnIndex() == wfhColumnNumber) {
							//calculation of in out and add it to dayOfWeek
							if (cell.getDateCellValue()!=null) {
								day.setWorkFromHome(true);
								day.setWorkFromHomeHours((cell.getNumericCellValue()+"").replace(".", ":"));
							}
							else{
								day.setWorkFromHome(false);
								day.setWorkFromHomeHours("00:00");
							}
						}

					}
					int valid = OperationHelper.isRowValid((ArrayList<Date>) inOutList);
					
					if (valid == 1 || valid ==2) {
						day.setValidDay(false);
						if(valid==1){
							day.setNoFirstIn(true);
						}
						else{
							day.setNoLastOut(true);
						}
						day.setDailyHours("00:00");
					}
					else if (valid == 0 ) {
						
						day.setValidDay(true);
						day.setDailyHours("00:00");
						
					}
					else if(valid ==4 ){
						String dailyHours ;
						dailyHours = OperationHelper.getDailyHours(inOutList);
						day.setDailyHours(dailyHours);
						
					}
					days.add(day);
					week.setDays(days);
					weeks.add(week);
					month.setWeeks(weeks);
					employee.setMonth(month);
					
				}
				else if(nextRow.getCell(nextRow.getFirstCellNum()).getStringCellValue() == ""){

					//check row that contains empId
					Day day = new Day();
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					List<Date> inOutList = new ArrayList<>();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if (cell.getColumnIndex() == dateColumnNumber) {
							day.setDayName(CalenderService.getNameOfDay(CalenderService.convertToDate(formatter.formatCellValue(cell))));//this check is to create new week
							if (CalenderService.getDayOfWeek(CalenderService.convertToDate(formatter.formatCellValue(cell))) == 2) {
								week = new Week();
								days =  new LinkedList<>();
								week.setDays(days);
								weeks.add(week);
								days.clear();

							}
						}

						if (cell.getColumnIndex() >= inOutStartColumnNumber	&& cell.getColumnIndex() <= inOutEndColumnNumber) {
							//calculation of in out and add it to day
							
								inOutList.add(cell.getDateCellValue());
						}
						
						if (cell.getColumnIndex() == wfhColumnNumber) {
							//calculation of in out and add it to dayOfWeek
							if (cell.getDateCellValue()!=null) {
								day.setWorkFromHome(true);
								day.setWorkFromHomeHours((cell.getNumericCellValue()+"").replace(".", ":"));
							}
							else{
								day.setWorkFromHome(false);
								day.setWorkFromHomeHours("00:00");
							}
						}

					}
					
					int valid = OperationHelper.isRowValid((ArrayList<Date>) inOutList);
					if (valid == 1 || valid == 2) {
						day.setValidDay(false);
						if(valid==1){
							day.setNoFirstIn(true);
						}
						else{
							day.setNoLastOut(true);
						}
						day.setDailyHours("00:00");
					}
					else if (valid== 0 ) {
						day.setValidDay(true);
						day.setDailyHours("00:00");
					}
					
					else if(valid ==4 ){
						String dailyHours ;
						dailyHours = OperationHelper.getDailyHours(inOutList);
						day.setDailyHours(dailyHours);
						day.setValidDay(true);
					}
					days.add(day);
					month.setWeeks(weeks);
				}
				
			}	////ignore empty rows ends.
		}
		
		afterProcessing(listOfEmployees);
		System.out.println(ObjectToJsonConvertor.convertObjectToJson(listOfEmployees));
		inputStream.close();
	}


	private void afterProcessing(List<Employee> listOfEmployee) throws ParseException {
		Iterator<Employee> listOfEmployeeItr = listOfEmployee.iterator();
		
		while(listOfEmployeeItr.hasNext()){
			Employee employee = (Employee) listOfEmployeeItr.next();
			List<Week> weeks = employee.getMonth().getWeeks();
			Iterator<Week> weeksItr = weeks.iterator();
			String monthlyHours = "00:00";
			String monthlyWorkFromHomeHours = "00:00";
			while (weeksItr.hasNext()) {
				Week week = (Week) weeksItr.next();
				List<Day> days = week.getDays();
				Iterator<Day> daysItr = days.iterator();
				String weeklyHours = "00:00";
				String weeklyWorkFromHomeHours = "00:00";
				while (daysItr.hasNext()) {
					Day day = (Day) daysItr.next();
					weeklyHours = OperationHelper.addTime(weeklyHours,day.getDailyHours());
					weeklyWorkFromHomeHours = OperationHelper.addTime(weeklyWorkFromHomeHours,day.getWorkFromHomeHours());
					
				}
				week.setWeeklyHours(weeklyHours);
				week.setWeeeklyWorkFromHomeHours(weeklyWorkFromHomeHours);
				monthlyHours = OperationHelper.addTime(monthlyHours, weeklyHours);
				monthlyWorkFromHomeHours = OperationHelper.addTime(monthlyWorkFromHomeHours, weeklyWorkFromHomeHours);
			}
			employee.getMonth().setMonthlyHours(monthlyHours);
			employee.getMonth().setMonthlyWorkFromHomeHours(monthlyWorkFromHomeHours);
			
		}
		
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
