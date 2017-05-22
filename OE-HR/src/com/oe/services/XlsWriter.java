package com.oe.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;

public class XlsWriter {

	public static void writeXlsFile(Map<String, Object[]> data) throws ParseException{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sample sheet");
	
		
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			
			Row row = sheet.createRow(rownum++);
			Object [] objArr = data.get(key);
			int cellnum = 0;
			Cell cellId = row.createCell(cellnum++);
			cellId.setCellType(1);
			cellId.setCellValue(objArr[0].toString());
			
			Cell cellName = row.createCell(cellnum++);
			cellName.setCellType(1);
			cellName.setCellValue(objArr[1].toString());
			
			Map map = (Map) objArr[2];
			Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
			int count = 0;
			
			int dayCounter = 0;
			while (it.hasNext()) {
				Row row1 = null;
				
				String date = it.next().getKey()+"";
				
				int dayOfMonth = CalenderService.getDayOfMonth(CalenderService.convertToDate(date));
				
				while( dayCounter+1 <= dayOfMonth){
					if (count !=0) {
					row1 = sheet.createRow(rownum+dayCounter);
					row = row1;
					}
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(CalenderService.convertToDate(date));
					cal1.add(Calendar.DAY_OF_YEAR, -(dayOfMonth-(dayCounter+1)));
					Date previousDate = cal1.getTime();
					String dateString = CalenderService.formatDate(previousDate);
					

				Object[] inOutObj = (Object[]) map.get(dateString);
	
				Cell cellDate = row.createCell(2);
				cellDate.setCellType(1);
				cellDate.setCellValue(dateString);
				
					if (inOutObj != null) {

						CellStyle cellStyle = workbook.createCellStyle();
						CreationHelper createHelper = workbook.getCreationHelper();
						cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("hh:mm:ss"));
						Cell cellIn = row.createCell(3);
						cellIn.setCellType(0);
						cellIn.setCellValue(inOutObj[0].toString());
						cellIn.setCellStyle(cellStyle);

						Cell cellOut = row.createCell(4);
						cellOut.setCellType(0);
						cellOut.setCellValue(inOutObj[1].toString());
						cellOut.setCellStyle(cellStyle);

					}
				dayCounter++;
				if (count !=0) {
					Cell cellBlank = row.createCell(0);
					cellBlank.setCellType(Cell.CELL_TYPE_BLANK);
				}
				count = count+1;
				}
			}
			rownum= rownum+dayCounter;
			
			
		}
		
		try {
			FileOutputStream out = 
					new FileOutputStream(new File("D:\\new.xls"));
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
