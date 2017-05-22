package com.oe.services;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class OperationHelper {
	
	public static boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return false;
		}
		return true;
	}
	
	public static String getDailyHours(List<Date> inOutList) throws ParseException {
		long dailyHours = 0;
		Date[] inOutArray = new Date[inOutList.size()];
		inOutArray = inOutList.toArray(inOutArray);
		int lastTimeLocation = getLastFilledIndex(inOutArray);
		Date date1 = inOutList.get(0);
		Date date2 = inOutList.get(lastTimeLocation);
		dailyHours = date2.getTime() - date1.getTime(); 
		String daily = CalenderService.milliToString(dailyHours);

		return daily;
	}
	
	public static int getLastFilledIndex(Date[] p) {
	    for(int i=p.length-1;i>=0;i--){
	        if(p[i]!=null){
	            return i;
	        }
	    }
	    return -1;
	}
	
	
	public static boolean isInt(String s){
		try {
			int i = Integer.parseInt(s);
			return true;
		}
		catch (NumberFormatException er) {
			return false;
		}
	}
	
	public static boolean isRowContainsEmployeeId(Row row) {
		boolean rowContainsEmployeeId = false;
		if (row != null) {
			// check row that contains empId
			Cell cell = row.getCell(0);
			if (cell != null) {
				if (cell.getCellType() != Cell.CELL_TYPE_BLANK) {
					if (OperationHelper.isInt(cell.getStringCellValue())) {
						rowContainsEmployeeId = true;
					}
				}
				return rowContainsEmployeeId;
			}
		}
		return rowContainsEmployeeId;
	}
	
	public static int isRowValid(ArrayList<Date> inOutList){
		//0 = empty off day
		//1 = no intime
		//2 = no outtime
		//4 OK
		
		Date[] inOutArray = new Date[inOutList.size()];
		inOutArray = inOutList.toArray(inOutArray);
		
		int valid = 4;
		
		if(OperationHelper.getLastFilledIndex(inOutArray)==-1){
			valid = 0;
			inOutArray=null;
			return valid;
		}
		for (int i = 0; i < inOutArray.length; i++) {
			if (i==0 && inOutArray[i]==null) {
				valid = 1;
				inOutArray=null;
				return valid;
			}
			else{
				if(OperationHelper.getLastFilledIndex(inOutArray)%2==0){
					valid = 2;
					inOutArray=null;
					return valid;
				}
			}
		}
			
		inOutArray=null;
		return valid;
		
	}
	public static String addTime(String s1 , String s2) throws ParseException{
		s1 = s1.replace(":", ".");
		s2 = s2.replace(":", "."); 

		float f1 = Float.parseFloat(s1);
		float f2 = Float.parseFloat(s2);
		
		long iPartOfF1;
		long iPartOfF2;
		
		double fPartOfF1;
		double fPartOfF2;
		iPartOfF1 = (long) f1;
		iPartOfF2 = (long) f2;
		fPartOfF1 = f1 - iPartOfF1;
		fPartOfF2 = f2 - iPartOfF2;
		
		double addOfFPart = fPartOfF1+ fPartOfF2;
		double addOfIPart = iPartOfF1+ iPartOfF2;
		
		while(addOfFPart>=0.60){
			addOfFPart-=0.60;
			addOfIPart+=1;
		}
		double result = (addOfIPart+addOfFPart);
		NumberFormat nf = NumberFormat.getInstance(); // get instance
		nf.setMaximumFractionDigits(2); // set decimal places
		String s = nf.format(result);
		return s;
		
		
	}

}
