package com.oe.models;

import java.util.List;

public class Week {
	
	private String weeeklyWorkFromHomeHours;
	private String weeklyHours;
	private List<Day> days;
	private int weekNumber;
	
	
	public int getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}
	public String getWeeklyHours() {
		return weeklyHours;
	}
	public void setWeeklyHours(String weeklyHours) {
		this.weeklyHours = weeklyHours;
	}
	public List<Day> getDays() {
		return days;
	}
	public void setDays(List<Day> days) {
		this.days = days;
	}
	public String getWeeeklyWorkFromHomeHours() {
		return weeeklyWorkFromHomeHours;
	}
	public void setWeeeklyWorkFromHomeHours(String weeklyWorkFromHomeHours) {
		this.weeeklyWorkFromHomeHours = weeklyWorkFromHomeHours;
	}
	
	
	@Override
	public String toString() {
		return "Week [weeeklyWorkFromHomeHours=" + weeeklyWorkFromHomeHours + ", weeklyHours=" + weeklyHours + ", days="
				+ days + ", weekNumber=" + weekNumber + "]";
	}
	
	
	

}
