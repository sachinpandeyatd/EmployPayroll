package com.hibernate.EmployPayroll;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class Test {
	public static void main(String[] args) throws ParseException {
//		System.out.println(new DAO().lossOfPay(32, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now())));
//		System.out.println(Calendar.getInstance().getActualMaximum(Calendar.Date););
		
//		Date jDate = Calendar.getInstance().add(Calendar.MONTH, 2);
//		
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.getInstance().getTime(), cal.getActualMaximum(Calendar.getInstance().getTime().getMonth()));
//        System.out.println(cal.getTime().getDate());
//        System.out.println(Calendar.getInstance().getTime().getDate());
//		java.util.Date d = new java.util.Date();
//		java.sql.Date sd = new java.sql.Date(d.getTime());
////		System.out.println(new DAO().lossOfPay(sd, sd, 1));
		
		String s = "2022-11-12";
		String e = "2022-11-15";
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date st = sdf.parse(s);
	    java.sql.Date sd = new java.sql.Date(st.getTime());
	    java.util.Date et = sdf.parse(e);
	    java.sql.Date ed = new java.sql.Date(et.getTime());
		
		System.out.println(new DAO().areDatesOverlapping(sd, ed));
	}
}
