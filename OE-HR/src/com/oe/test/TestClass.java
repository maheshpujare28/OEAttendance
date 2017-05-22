package com.oe.test;

import java.io.IOException;
import java.text.ParseException;
import com.oe.services.AttendanceFileOperator;
import com.oe.services.AttendanceFileOperatorForOld;
import com.oe.services.AttendanceFileOperatorV1;
import com.oe.services.XlsWriter;

public class TestClass {
	
	public static void main(String[] args) throws IOException, ParseException {
	
		AttendanceFileOperatorForOld afo = new AttendanceFileOperatorForOld();
		afo.readFile();
		
		AttendanceFileOperator afo1 = new AttendanceFileOperator();
		afo1.readFile();
		
		
		
		/*XlsWriter.writeXlsFile(null);*/
	}
}
