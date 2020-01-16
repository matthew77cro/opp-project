<%@page import="hr.fer.opp.bugbusters.dao.model.Racun"%>
<%@page import="java.util.List"%>
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
	
	<div id="newCardForm" class="cardForm">
		<form action="" method="POST">
			<input type="hidden" name="action" value="new">
			<label>Vrsta kartice:</label>
			<select name="cardType">
				<% 
					for(VrstaKartice vrsta : ((Map<Integer, VrstaKartice>)request.getAttribute("vrstaKartice")).values()) {
						out.print("<option value=\"" + vrsta.getSifVrsteKartice() + "\">" + vrsta.getSifVrsteKartice() + " : " + vrsta.getNazVrsteKartice() + "</option>");
					}
				%>
			</select>
			<input id="request" type="submit" value="Provedi zahtjev">
		</form>
		<button id="cancelNew" class="cancel">Odustani</button>
	</div>
	
	<div id="payCardForm" class="cardForm">
		<form action="" method="POST">
			<input type="hidden" name="action" value="pay">
			<label>Kartica:</label>
			<select name="brojKartice">
				<% 
					Map<Integer, VrstaKartice> vk = (Map<Integer, VrstaKartice>) request.getAttribute("vrstaKartice");
					for(Kartica kartica : (List<Kartica>) request.getAttribute("kartice")) {
						VrstaKartice currentVK = vk.get(kartica.getSifVrstaKartice());
						if(currentVK==null) continue;
						out.print("<option value=\"" + kartica.getBrKartica() + "\">" + kartica.getBrKartica() + " : " + kartica.getStanje() + "HRK</option>");
					}
				%>
			</select> <br>
			<label>Račun terećenja:</label>
			<select name="brojRacuna">
				<% 
					List<Racun> racuni = (List<Racun>) request.getAttribute("racuni");
					for(Racun racun : racuni) {
						out.print("<option value=\"" + racun.getBrRacun() + "\">" + racun.getBrRacun() + " : " + racun.getStanje() + "HRK</option>");
					}
				%>
			</select> <br>
			<label>Iznos:</label>
			<input type="text" name="iznos" placeholder="HRK"> <br>
			<input id="request" type="submit" value="Provedi zahtjev">
		</form>
		<button id="cancelPay" class="cancel">Odustani</button>
	</div>
	
	<div id="container">
		<% 
			if(request.getAttribute("errorMsg")!=null) {
				out.println(request.getAttribute("errorMsg").toString());
			}
		%>
		<h1>Kartice</h1>
		<button id="newCardBtn" class="cardBtn">Dodaj karticu</button>
		<button id="payCardBtn" class="cardBtn">Otplati karticu</button>
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
				for(Kartica kartica : (List<Kartica>) request.getAttribute("kartice")) {
					VrstaKartice currentVK = vk.get(kartica.getSifVrstaKartice());
					out.print("<tr>");
					out.print("<td>" + (currentVK==null ? "Debitna" : currentVK.getNazVrsteKartice()) + "</td>");
					out.print("<td>" + kartica.getBrKartica() + "</td>");
					out.print("<td>" + kartica.getBrRacun() + "</td>");
					out.print("<td>" + kartica.getStanje() + "</td>");
					out.print("<td>" + kartica.getValjanost() + "</td>");
					out.print("<td>" + kartica.getLimitKartice() + "</td>");
					out.print("<td>" + kartica.getKamStopa() + "</td>");
					out.print("<td>" + kartica.getDatRate() + "</td>");
					out.print("</tr>");
				}
			%>
		</table>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<%= request.getServletContext().getContextPath() %>/js/cards.js"></script>
</body>
</html>