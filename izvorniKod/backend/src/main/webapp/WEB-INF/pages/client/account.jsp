<%@page import="hr.fer.opp.bugbusters.dao.model.VrstaRacuna"%>
<%@page import="hr.fer.opp.bugbusters.dao.model.Racun"%>
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
	<link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/account.css">
</head>
<body>
	<header>
		<div>
			<h1 id="nav-title">BugBusters banka</h1> 
		</div>
	</header>
	<nav class="topnav">
		<a href="profil" id="moj-profil">Moj profil</a>
		<a class="active" href="racuni" id="racuni">Računi</a>
		<a href="kartice" id="kartice">Kartice</a>
		<a href="krediti" id="krediti">Krediti</a>
		<a href="transakcije" id="transakcije">Transakcije</a>
		<a href="logout" id="logout">Odjava</a>
	</nav>
	<div id="container">
		<h1>Računi</h1>
		<table id="account-data-table">
			<tr>
				<th>Vrsta</th>
				<th>Broj</th>
				<th>Stanje</th>
				<th>Prekoračenje</th>
				<th>Kamatna stopa</th>
			</tr>
			<% 
				Map<Racun, VrstaRacuna> mapa = (Map<Racun, VrstaRacuna>) request.getAttribute("racuni");
				for(Map.Entry<Racun, VrstaRacuna> racun : mapa.entrySet()) {
					out.print("<tr>");
					out.print("<td>" + racun.getValue().getNazVrsteRacuna() + "</td>");
					out.print("<td>" + racun.getKey().getBrRacun() + "</td>");
					out.print("<td>" + racun.getKey().getStanje() + "</td>");
					out.print("<td>" + racun.getKey().getPrekoracenje() + "</td>");
					out.print("<td>" + racun.getKey().getKamStopa() + "</td>");
					out.print("</tr>");
				}
			%>
		</table>
	</div>
</body>
</html>