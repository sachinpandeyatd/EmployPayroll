package com.hibernate.EmployPayroll;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class DAO {
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<Employ> showEmploy() {
		sessionFactory = SessionHelper.getConnection();
		Session session = sessionFactory.openSession();
		
		return session.createQuery("from Employ").list();
	}
	
	public double hra(double salary) {
		return (salary * 2)/100;
	}
	
	public double da(double salary) {
		return (salary * 1.25)/100;
	}
	
	public double ta(double salary) {
		return (salary * 0.95)/100;
	}
	
	public double tax(double salary) {
		return (salary * 2.3)/100;
	}
	
	public double pf(double salary) {
		return (salary * 3)/100;
	}

	public double gross(double salary) {
		return salary + hra(salary) + da(salary) + ta(salary);
	}
	
	public double netPay(double salary) {
		return gross(salary) - ta(salary) - pf(salary);
	}
	
	public String addEmploy(Employ employ) {
		sessionFactory = SessionHelper.getConnection();
		Session session = sessionFactory.openSession();
		
		employ.setHra(hra(employ.getSalary()));
		employ.setDa(da(employ.getSalary()));
		employ.setTa(ta(employ.getSalary()));
		employ.setTax(tax(employ.getSalary()));
		employ.setPf(pf(employ.getSalary()));
		employ.setGross(gross(employ.getSalary()));
		employ.setNetPay(netPay(employ.getSalary()));
		employ.setLeaveAvailable(16);
		
		session.save(employ);
		session.beginTransaction().commit();
		session.close();
		
		return "Employee added.";
	}
	
	public Date convertDate(java.util.Date date ) {
		return new Date(date.getTime());
	}
	
	public int noOfDays(Date startDate, Date endDate) {
		return (int)(endDate.getTime() - startDate.getTime()) / (1000 * 24 * 60 * 60) + 1;
	}
	
	public Employ searchById(int empId) {
  		Session session = SessionHelper.getConnection().openSession(); 
  		Criteria cr = session.createCriteria(Employ.class);
  		cr.add(Restrictions.eq("empno", empId));
  		List<Employ> empList = cr.list();
  		return empList.get(0);
	}
	
	public Leave searchId(int empId) {
  		Session session = SessionHelper.getConnection().openSession(); 
  		Criteria cr = session.createCriteria(Leave.class);
  		cr.add(Restrictions.eq("empno", empId));
  		List<Leave> empList = cr.list();
  		return empList.get(0);
	}
	
	public double lossOfPay(int empno, int month) {
		Session session = SessionHelper.getConnection().openSession(); 
		
		Leave leave = new Leave();
		Employ employ = searchById(empno);

		double salary =	employ.getSalary();
		double salaryOfOneDay = salary / 30.46, lossOfPay = 0; long lossOfPayDays;
  		
		Query query=session.createQuery("select sum(noOfDays) from Leave where empno=:empno AND (MONTH(leaveStartDate)=:month AND MONTH(leaveEndDate)=:month)").setParameter("empno", empno).setParameter("month", month);

  		List<Long> count = query.list();
  		
  		long longNOD = (Long)count.get(0);
  		
		if(longNOD >= 3) {
			lossOfPayDays = longNOD - 3;
			lossOfPay = lossOfPayDays * salaryOfOneDay;
		}
		
		leave=searchId(empno);
		leave.setLossOfPay(lossOfPay);
		session.saveOrUpdate(leave);
		session.beginTransaction().commit();
		
		return lossOfPay;
	}
	
	public boolean areDatesOverlapping(Date startDate, Date endDate) {
		boolean areDatesOverlapping = false;
		
		Session session = SessionHelper.getConnection().openSession(); 
		int count = session.createQuery("from Leave where empno = 1 AND (LeaveStartDate between :startDate AND :endDate) OR (LeaveEndDate between :startDate AND :endDate)").setParameter("startDate", startDate).setParameter("endDate", endDate).list().size();
		
		if (count != 0) {
			areDatesOverlapping = true;
		}
		
		return areDatesOverlapping;
	}
	
	public String addLeave(Leave leave) {
		if (areDatesOverlapping(leave.getStartDate(), leave.getEndDate())) {
			return "Dates are overlapping, please check your dates and try again.";
		}
		
		Session session = SessionHelper.getConnection().openSession();
		
		int noOfDays = noOfDays(leave.getStartDate(), leave.getEndDate());
		leave.setNoOfDays(noOfDays);
		
		session.save(leave);
		session.beginTransaction().commit();
		session.close();
		
		Employ employ = searchById(leave.getEmpno());
		if (noOfDays <= 3) {
			employ.setLeaveAvailable(employ.getLeaveAvailable() - noOfDays);
		}else {
			employ.setLeaveAvailable(employ.getLeaveAvailable() - 3);
		}
		
		session = SessionHelper.getConnection().openSession();
		session.update(employ);
		session.close();
		
		return "Your leave request added.";
	}
}
