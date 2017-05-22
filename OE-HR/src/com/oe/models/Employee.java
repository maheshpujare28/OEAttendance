package com.oe.models;

public class Employee {
	
	private int id;
	private String name;
	private Month month;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Month getMonth() {
		return month;
	}
	public void setMonth(Month month) {
		this.month = month;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", month=" + month + "]";
	}
	
	
	
	

}
