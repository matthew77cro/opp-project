<%@page import="hr.fer.opp.bugbusters.dao.model.VrstaKartice"%>
<%@page import="hr.fer.opp.bugbusters.dao.model.Kartica"%>
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
	<link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/cards.css">
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
		<a class="active" href="kartice" id="kartice">Kartice</a>
		<a href="krediti" id="krediti">Krediti</a>
		<a href="transakcije" id="transakcije">Transakcije</a>
		<a href="logout" id="logout">Odjava</a>
	</nav>
	<div id="newCardForm">
		<form action="" method="POST">
			<label>Vrsta kartice:</label>
			<select name="cardType">
				<option value="AmericanExpress">AmericanExpress</option>
				<option value="Diners">Diners</option>
				<option value="Discover">Discover</option>
				<option value="MasterCard">MasterCard</option>
				<option value="Visa">Visa</option>
			</select>
			<input id="request" type="submit" name="" value="Provedi zahtjev">
			<button id="cancel">Odustani</button>
		</form>
	</div>
	<div id="container">
		<h1>Kartice</h1>
		<button id="newCardBtn">Dodaj karticu</button>
		<table id="cards-data-table">
			<tr>
				<th>Naziv</th>
				<th>Broj</th>
				<th>Broj računa</th>
				<th>Stanje</th>
				<th>Valjanost</th>
				<th>Limit</th>
				<th>Kamatna stopa</th>
				<th>Datum rate</th>
			</tr>
			<% 
				Map<Kartica, VrstaKartice> mapa = (Map<Kartica, VrstaKartice>) request.getAttribute("kartice");
				for(Map.Entry<Kartica, VrstaKartice> kartica : mapa.entrySet()) {
					out.print("<tr>");
					out.print("<td>" + kartica.getValue().getNazVrsteKartice() + "</td>");
					out.print("<td>" + kartica.getKey().getBrKartica() + "</td>");
					out.print("<td>" + kartica.getKey().getBrRacun() + "</td>");
					out.print("<td>" + kartica.getKey().getStanje() + "</td>");
					out.print("<td>" + kartica.getKey().getValjanost() + "</td>");
					out.print("<td>" + kartica.getKey().getLimitKartice() + "</td>");
					out.print("<td>" + kartica.getKey().getKamStopa() + "</td>");
					out.print("<td>" + kartica.getKey().getDatRate() + "</td>");
					out.print("</tr>");
				}
			%>
		</table>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<%= request.getServletContext().getContextPath() %>/js/cards.js"></script>
</body>
</html>