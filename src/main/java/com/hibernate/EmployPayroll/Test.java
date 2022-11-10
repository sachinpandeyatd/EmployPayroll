package com.hibernate.EmployPayroll;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

public class Test {
	public static void main(String[] args) {
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
		
		System.out.println(new DAO().searchId(1));
	}
}
