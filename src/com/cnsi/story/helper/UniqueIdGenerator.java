package com.cnsi.story.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class UniqueIdGenerator {

	public String createUniqueID(){
		 String usr = "usr";
		 Calendar cal = Calendar.getInstance();
		
		String month = ((Integer)(cal.get(Calendar.MONTH)+1)).toString();
		String year = ((Integer)(cal.get(Calendar.YEAR))).toString();
		Random random = new Random();
		String randNo =  ((Integer)(random.nextInt(1000))).toString();
		String Id = usr+"-"+randNo+"-"+month+"-"+year;
		return Id;
	}
	
	public static void main(String[] args) {
		UniqueIdGenerator generator = new UniqueIdGenerator();
		generator.createUniqueID();
	}
}
