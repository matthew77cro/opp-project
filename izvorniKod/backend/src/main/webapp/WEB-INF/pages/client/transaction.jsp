<%@page import="hr.fer.opp.bugbusters.dao.model.Racun"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.opp.bugbusters.dao.model.Transakcija"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>BugBusters banka</title>
    <link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/clientProfile.css">
	<link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/transaction.css">
</head>
<body>
	<header>
		<div>
			<h1 id="nav-title">BugBusters banka</h1> 
		</div>
	</header>
	<nav class="topnav">
		<a href="profil" id="moj-profil">Moj profil</a>
		<a href="racuni" id="racuni">Računi</a>
		<a href="kartice" id="kartice">Kartice</a>
		<a href="krediti" id="krediti">Krediti</a>
		<a class="active" href="transakcije" id="transakcije">Transakcije</a>
		<a href="logout" id="logout">Odjava</a>
	</nav>
	<div id="newTransaction">			
		<form action="transakcije" method="POST">
			<label>Račun terećenja:</label>
			<select name="debitingAccount">
				<% 
					for(Racun racun : (List<Racun>) request.getAttribute("racuni")) {
						out.print("<option value=\"" + racun.getBrRacun() + "\">" + racun.getBrRacun() + " : " + racun.getStanje() + "HRK</option>");
					}
				%>
			</select>
			<label>Račun terećenja:</label>
			<input id="approvalAccount" type="text" name="approvalAccount">
			<label>Iznos plaćanja:</label>
			<input id="amount" type="text" placeholder="HRK" name="amount">
			<input id="finishTransaction" type="submit" name="" value="Provedi transakciju">
			<button id="cancel">Odustani</button>
		</form>
	</div>
	<div id="container">	
		<h1>Transakcije</h1>
		<button id="newTransactionBtn">Nova transakcija</button>
		<div id="search-container">
			<form action="transakcije" method="GET">
				<label>Od: </label>
				<input type="date" name="fromDate">
				<label>Do: </label>
				<input type="date" name="toDate">
				<input type="submit" id="search" value="Pretraži">
			</form>
		</div>
		<table id="transaction-data-table">
			<tr>
				<th>Broj</th>
				<th>Račun terećenja</th>
				<th>Račun odobrenja</th>
				<th>Iznos</th>
				<th>Datum</th>
			</tr>
			<% 
				for(Transakcija transakcija : (List<Transakcija>) request.getAttribute("transakcije")) {
					out.print("<tr>");
					out.print("<td>" + transakcija.getBrTransakcija() + "</td>");
					out.print("<td>" + transakcija.getRacTerecenja() + "</td>");
					out.print("<td>" + transakcija.getRacOdobrenja() + "</td>");
					out.print("<td>" + transakcija.getIznos() + "</td>");
					out.print("<td>" + transakcija.getDatTransakcije() + "</td>");
					out.print("</tr>");
				}
			%>
		</table>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<%= request.getServletContext().getContextPath() %>/js/transaction.js"></script>
</body>
</html>