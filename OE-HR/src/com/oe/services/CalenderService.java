package com.oe.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalenderService {
	
	
	public static int getDayOfWeek(Date date){
		
		 int dayOfWeek;
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // the day of the week in numerical format //4
		return dayOfWeek;
		
	}
	
	public static int getDayOfMonth(Date date){
		
		int dayOfMonth;
        /*SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated   Wed
        System.out.println(simpleDateformat.format(date));
 
        simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely //Wednesday
        System.out.println(simpleDateformat.format(date));
         */
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        dayOfMonth = calendar.get( Calendar.DAY_OF_MONTH); // the day of the week in numerical format //4
       
		return dayOfMonth;
		
	}
	
public static String getNameOfDay(Date date){
		
		String nameOfDay= "";
       
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely //Wednesday
        nameOfDay = simpleDateformat.format(date);
        
       
		return nameOfDay;
		
	}
	
	public static Date convertToDate(String date) throws ParseException{
		return new SimpleDateFormat("dd-MMM-yyyy").parse(date);

	}
	
	public static String formatDate(Date date){
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
        return DATE_FORMAT.format(date);


	}
	
	public static Date convertToTime(String date) throws ParseException{
		return new SimpleDateFormat("hh:mm").parse(date);

	}
	
	public static Date convertToDateOld(String date) throws ParseException{
		return new SimpleDateFormat("yyyyMMdd").parse(date);

	}
	
	public static String milliToString(long millis) {

        long hrs = TimeUnit.MILLISECONDS.toHours(millis) % 24;
        long min = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
        /*long sec = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;*/
        //millis = millis - (hrs * 60 * 60 * 1000); //alternative way
        //millis = millis - (min * 60 * 1000);
        //millis = millis - (sec * 1000);
        //long mls = millis ;
        long mls = millis % 1000;
        String toRet = String.format("%02d:%02d", hrs, min);
        //System.out.println(toRet);
        return toRet;
    }

}
