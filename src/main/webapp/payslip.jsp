<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<title>Pay slip</title>
</head>
<body>
	<jsp:useBean id="dao" class="com.hibernate.EmployPayroll.DAO" />
	<form>
		Employ No: <input type="number" name="empno"><br /><br /> 
		Month: <select name="month">
			<option value="11">November</option>
			<option value="12">December</option>
		</select><br />
		<br /> <input type="submit" value="submit" /><br />
		<br />
	</form>
	
	
	<c:if test="${param.empno != null }">
		<c:set var="id" value="${param.empno}" />
		<c:set var="month" value="${param.month}" />
		<c:set var="employ" value="${dao.searchById(id)}" />
		<jsp:useBean id="leave" class="com.hibernate.EmployPayroll.Leave" />
		<c:set var="pay" value="${dao.lossOfPay(id, month)}" />
		
		<h4>Employ Id:${id}</h4>
		<table border="3">
			<tr>
				<th>Earnings</th>
				<th>Deduction</th>
			</tr>
			<tr>
				<td>Basic: ${employ.salary}</td>
				<td>PF: ${employ.pf}</td>
			</tr>
			<tr>
				<td>HRA: ${employ.hra}</td>
				<td>Tax: ${employ.tax}</td>
			</tr>
			<tr>
				<td>DA: ${employ.da}</td>
				<td>Loss Of Pay: ${pay}</td>
			</tr>
			<tr>
				<td>TA: ${employ.ta}</td>
			</tr>
			<tr>
				<td>Total Earning: ${employ.gross}</td>
				<td>Total Deduction: <c:out value="${employ.pf + employ.tax + pay }" /></td>
			</tr>
			<tr>
				<c:set var="total" value="${employ.pf + employ.tax + pay }" />
				<th>Net Pay:</th>
				<th><c:out value="${employ.gross - total}" /></th>
			</tr>
		</table>
	</c:if>
</body>
</html>