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
				<option value="Stambeni">Stambeni</option>
				<option value="Namjenski">Namjenski</option>
				<option value="Nenamjenski">Nenamjenski</option>
			</select>
			<label>Iznos:</label>
			<input id="amount" type="text" placeholder="HRK">
			<label>Rok otplate:</label>
			<input id="time" type="text" placeholder="BROJ MJESECI">
			<input id="request" type="submit" name="" value="Provedi zahtjev">
			<button id="cancel">Odustani</button>
		</form>
	</div>
	<div id="payCreditForm">
		<form action="" method="POST">
			<label>Kredit:</label>
			<select name="credit">
				<option value="">Kredit 1</option>
				<option value="">Kredit 2</option>
				<option value="">Kredit 3</option>
			</select>
			<label>Iznos:</label>
			<input id="amount" type="text" placeholder="HRK">
			<input id="pay" type="submit" name="" value="Plati">
			<button id="cancelPayment">Odustani</button>
		</form>
	</div>
	<div id="container">
		<h1>Krediti</h1>
		<button id="newCreditBtn">Novi kredit</button>
		<button id="payCreditBtn">Otplati kredit</button>
		<table id="credit-data-table">
			<tr>
				<th>Vrsta</th>
				<th>Datum ugovaranja</th>
				<th>Period otplate</th>
				<th>Datum plaćanja rate</th>
				<th>Iznos</th>
				<th>Preostalo dugovanje</th>
				<th>Kamata</th>
			</tr>
			<% 
				Map<Kredit, VrstaKredita> mapa = (Map<Kredit, VrstaKredita>) request.getAttribute("krediti");
				for(Map.Entry<Kredit, VrstaKredita> kredit : mapa.entrySet()) {
					out.print("<tr>");
					out.print("<td>" + kredit.getValue().getNazVrsteKredita() + "</td>");
					out.print("<td>" + kredit.getKey().getDatUgovaranja() + "</td>");
					out.print("<td>" + kredit.getKey().getPeriodOtplate() + "</td>");
					out.print("<td>" + kredit.getKey().getDatRate() + "</td>");
					out.print("<td>" + kredit.getKey().getIznos() + "</td>");
					out.print("<td>" + kredit.getKey().getPreostaloDugovanje() + "</td>");
					out.print("<td>" + kredit.getValue().getKamStopa() + "</td>");
					out.print("</tr>");
				}
			%>
		</table>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<%= request.getServletContext().getContextPath() %>/js/credit.js"></script>
</body>
</html>