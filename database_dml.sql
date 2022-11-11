-- drop database if exists employ_payroll;

-- Create database employ_payroll;

use employ_payroll;

-- create table Employ(
-- 	Empno int auto_increment primary key,
--     Ename varchar(255),
--     Gender Enum("MALE", "FEMALE"),
--     Salary double,
--     Hra double,
--     Da double,
--     Ta double,
--     Tax double,
--     Pf double,
--     Gross double,
--     NetPay double,
--     LeaveAvailable int
-- );

create table LeaveTable(
	LeaveID int primary key auto_increment, 
    Empno int not null, 
    LeaveStartDate date, 
    LeaveEndDate date, 
    NoOfDays int,
    LeaveReason text(2000), 
    lossOfPay double
);