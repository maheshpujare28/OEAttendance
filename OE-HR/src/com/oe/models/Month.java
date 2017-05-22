package com.oe.models;

import java.util.List;

public class Month {
	private String monthlyWorkFromHomeHours;
	private String monthlyHours;
	private List<Week> weeks;
	
	public String getMonthlyHours() {
		return monthlyHours;
	}
	public void setMonthlyHours(String monthlyHours2) {
		this.monthlyHours = monthlyHours2;
	}
	public List<Week> getWeeks() {
		return weeks;
	}
	public void setWeeks(List<Week> weeks) {
		this.weeks = weeks;
	}
	public String getMonthlyWorkFromHomeHours() {
		return monthlyWorkFromHomeHours;
	}
	public void setMonthlyWorkFromHomeHours(String monthlyWorkFromHomeHours2) {
		this.monthlyWorkFromHomeHours = monthlyWorkFromHomeHours2;
	}
	
	
	@Override
	public String toString() {
		return "Month [monthlyWorkFromHomeHours=" + monthlyWorkFromHomeHours + ", monthlyHours=" + monthlyHours
				+ ", weeks=" + weeks + "]";
	}
	
	
	

}
