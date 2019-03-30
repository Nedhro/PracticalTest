package com.bits.common;

import java.util.Calendar;
import java.util.Date;


public class Utill {
	
	public static Date getDateByAge(int year){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -year);
		return c.getTime();
	}
}
