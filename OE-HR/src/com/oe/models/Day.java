package com.oe.models;

public class Day {
	
	private String dailyHours;
	private boolean workFromHome;
	private String workFromHomeHours;
	private String dayName;
	private boolean validDay;
	private boolean noFirstIn;
	private boolean noLastOut;
	
	
	
	public Day() {
		super();
		this.dailyHours = "00:00";
		this.workFromHome = false;
		this.workFromHomeHours = "00:00";
		this.validDay = false;
	}
	public boolean isNoFirstIn() {
		return noFirstIn;
	}
	public void setNoFirstIn(boolean noFirstIn) {
		this.noFirstIn = noFirstIn;
	}
	public String getDailyHours() {
		return dailyHours;
	}
	public void setDailyHours(String dailyHours) {
		this.dailyHours = dailyHours;
	}
	public boolean isWorkFromHome() {
		return workFromHome;
	}
	public void setWorkFromHome(boolean workFromHome) {
		this.workFromHome = workFromHome;
	}
	public String getWorkFromHomeHours() {
		return workFromHomeHours;
	}
	public void setWorkFromHomeHours(String workFromHomeHours) {
		this.workFromHomeHours = workFromHomeHours;
	}
	public String getDayName() {
		return dayName;
	}
	public void setDayName(String dayName) {
		this.dayName = dayName;
	}
	@Override
	public String toString() {
		return "Day [dailyHours=" + dailyHours + ", workFromHome=" + workFromHome + ", workFromHomeHours="
				+ workFromHomeHours + ", dayName=" + dayName + "]";
	}
	public boolean isValidDay() {
		return validDay;
	}
	public void setValidDay(boolean validDay) {
		this.validDay = validDay;
	}
	public boolean isNoLastOut() {
		return noLastOut;
	}
	public void setNoLastOut(boolean noLastOut) {
		this.noLastOut = noLastOut;
	}
	
	  
	

}
