package com.oe.services;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.oe.models.Employee;

public class ObjectToJsonConvertor {


	public static String convertObjectToJson(List<Employee>employees) throws JsonIOException, IOException{
		Gson gson = new Gson();
	

		// 1. Java object to JSON, and save into a file
		gson.toJson(employees, new FileWriter("D:\\file.json"));

		// 2. Java object to JSON, and assign to a String
		String jsonInString = gson.toJson(employees);
		//return jsonInString;
		return jsonInString;
	}
	
	
}
