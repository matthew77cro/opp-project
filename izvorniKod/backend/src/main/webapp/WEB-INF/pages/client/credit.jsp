<%@page import="hr.fer.opp.bugbusters.dao.model.Racun"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.opp.bugbusters.dao.model.VrstaKredita"%>
<%@page import="hr.fer.opp.bugbusters.dao.model.Kredit"%>
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
	<link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/credit.css">
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
		<a class="active" href="krediti" id="krediti">Krediti</a>
		<a href="transakcije" id="transakcije">Transakcije</a>
		<a href="logout" id="logout">Odjava</a>
	</nav>
	<div id="newCreditForm">
		<form action="" method="POST">
			<label>Vrsta kredita:</label>
			<select name="creditType">
				<%
					for(VrstaKredita vrsta : ((Map<Integer, VrstaKredita>) request.getAttribute("vrsteKredita")).values()) {
						out.print("<option value=\"" + vrsta.getSifVrsteKredita() + "\">" + vrsta.getNazVrsteKredita() + "</option>");
					}
				%>
			</select>
			<label>Iznos:</label>
			<input id="amount" name="amount" type="text" placeholder="HRK">
			<label>Rok otplate:</label>
			<input id="time" name="time" type="text" placeholder="BROJ MJESECI">
			<input id="request" type="submit" name="" value="Provedi zahtjev">
		</form>
		<button id="cancel">Odustani</button>
	</div>
	<div id="payCreditForm">
		<form action="" method="POST">
			<label>Račun:</label>
			<select name="account">
				<%
					List<Racun> racuni = (List<Racun>) request.getAttribute("racuni");
					for(Racun r : racuni) {
						out.print("<option value=\"" + r.getBrRacun() + "\">" + r.getBrRacun() + " : " + r.getStanje().toString() + "HRK</option>");
					}
				%>
			</select>
			<label>Kredit:</label>
			<select name="credit">
				<%
					List<Kredit> krediti = (List<Kredit>) request.getAttribute("krediti");
					Map<Integer, VrstaKredita> vrsteKredita = (Map<Integer, VrstaKredita>) request.getAttribute("vrsteKredita");
					for(Kredit k : krediti) {
						out.print("<option value=\"" + k.getBrKredit() + "\">" + vrsteKredita.get(k.getSifVrsteKredita()).getNazVrsteKredita() + " : " + k.getBrKredit() + " : " + k.getPreostaloDugovanje().toString() + "HRK</option>");
					}
				%>
			</select>
			<label>Iznos:</label>
			<input id="amount2" name="amount" type="text" placeholder="HRK">
			<input id="pay" type="submit" name="" value="Plati">
		</form>
		<button id="cancelPayment">Odustani</button>
	</div>
	<div id="container">
		<% 
			if(request.getAttribute("errorMsg")!=null) {
				out.println(request.getAttribute("errorMsg").toString());
			}
		%>
		<h1>Krediti</h1>
		<button id="newCreditBtn">Novi kredit</button>
		<button id="payCreditBtn">Otplati kredit</button>
		<table id="credit-data-table">
			<tr>
				<th>Broj</th>
				<th>Vrsta</th>
				<th>Datum ugovaranja</th>
				<th>Period otplate</th>
				<th>Datum plaćanja rate</th>
				<th>Iznos</th>
				<th>Preostalo dugovanje</th>
				<th>Kamata</th>
			</tr>
			<% 
				for(Kredit kredit : krediti) {
					out.print("<tr>");
					out.print("<td>" + kredit.getBrKredit() + "</td>");
					out.print("<td>" + vrsteKredita.get(kredit.getSifVrsteKredita()).getNazVrsteKredita() + "</td>");
					out.print("<td>" + kredit.getDatUgovaranja() + "</td>");
					out.print("<td>" + kredit.getPeriodOtplate() + "</td>");
					out.print("<td>" + kredit.getDatRate() + "</td>");
					out.print("<td>" + kredit.getIznos() + "</td>");
					out.print("<td>" + kredit.getPreostaloDugovanje() + "</td>");
					out.print("<td>" + vrsteKredita.get(kredit.getSifVrsteKredita()).getKamStopa() + "</td>");
					out.print("</tr>");
				}
			%>
		</table>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<%= request.getServletContext().getContextPath() %>/js/credit.js"></script>
</body>
</html>