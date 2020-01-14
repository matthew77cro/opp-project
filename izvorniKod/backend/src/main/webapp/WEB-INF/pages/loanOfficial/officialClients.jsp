<%@page import="hr.fer.opp.bugbusters.dao.model.VrstaKredita"%>
<%@page import="hr.fer.opp.bugbusters.dao.model.Kredit"%>
<%@page import="hr.fer.opp.bugbusters.dao.model.VrstaKartice"%>
<%@page import="hr.fer.opp.bugbusters.dao.model.Kartica"%>
<%@page import="java.util.Map"%>
<%@page import="hr.fer.opp.bugbusters.dao.model.VrstaRacuna"%>
<%@page import="hr.fer.opp.bugbusters.dao.model.Racun"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Bugbusters banka</title>
    <link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/clientProfile.css">
    <link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/bankerClients.css">
    <%
    	String ime = request.getAttribute("firstName")==null ? "X" : request.getAttribute("firstName").toString();
   		String prezime = request.getAttribute("lastName")==null ? "X" : request.getAttribute("lastName").toString();
    	String adresa = request.getAttribute("address")==null ? "X" : request.getAttribute("address").toString();
    	String adresaUl = request.getAttribute("addressUl")==null ? "X" : request.getAttribute("addressUl").toString();
    	String adresaPbr = request.getAttribute("addressPbr")==null ? "X" : request.getAttribute("addressPbr").toString();
    	String oib = request.getAttribute("oib")==null ? "X" : request.getAttribute("oib").toString();
    	String datRod = request.getAttribute("birthday")==null ? "X" : request.getAttribute("birthday").toString();
    	String email = request.getAttribute("email")==null ? "X" : request.getAttribute("email").toString();
    %>
</head>
<body>
    <header>
		<div>
			<h1 id="nav-title">BugBusters banka</h1> 
		</div>
	</header>
	<nav class="topnav">
		<a href="profil" id="moj-profil">Moj profil</a>
		<a class="active" href="sluzbenik-klijenti" id="klijenti">Klijenti</a>
		<a href="sluzbenik-zahtjevi-kartice" id="kartice">Kartični zahtjevi</a>
        <a href="sluzbenik-kreditni-zahtjevi" id="krediti">Kreditni zahtjevi</a>
        <a href="logout" id="logout">Odjava</a>
    </nav>
    <div id="container">
		<h1>Korisnički profili</h1><br>
		<% 
			if(request.getAttribute("errorMsg")!=null) {
				out.println(request.getAttribute("errorMsg").toString());
			}
		%><br>
		<form action="" method="POST">
			<input type="hidden" name="action" value="search">
			<label>Upiši OIB: </label>
			<input id="searchBox" name="oib" type="text">
			<button id="searchProfileBtn">Pretraži</button>
		</form>
		<table id="user-data">
			<tr>
				<th>Ime</th>
				<td>
				<% out.print(ime); %>
				</td>
			</tr>
			<tr>
				<th>Prezime</th>
				<td>
				<% out.print(prezime); %>
				</td>
			</tr>
			<tr>
				<th>Adresa</th>
				<td>
				<% out.print(adresa); %>
				</td>
			</tr>
			<tr>
				<th>OIB</th>
				<td>
				<% out.print(oib); %>
				</td>
			</tr>
			<tr>
				<th>Datum rođenja</th>
				<td>
				<% out.print(datRod); %>
				</td>
			</tr>
			<tr>
				<th>E-mail</th>
				<td>
				<% out.print(email); %>
				</td>
			</tr>
		</table><br>
		
		<h1>Računi korisnika</h1><br>
		<table id="user-data">
			<tr>
				<th>Broj računa</th>
				<th>Vrsta računa</th>
				<th>Stanje računa</th>
				<th>Prekoračenje</th>
			</tr>
			<%
				List<Racun> racuni = (List<Racun>) request.getAttribute("racuni");
				Map<Integer, VrstaRacuna> vrsteRacuna = (Map<Integer, VrstaRacuna>) request.getAttribute("vrsteRacuna");
				if(racuni==null) {
					out.print("<tr> <td>X</td> <td>X</td> <td>X</td> <td>X</td> </tr>");
				} else {
					for(Racun racun : racuni) {
						out.print("<tr>");
						out.print("<td>" + racun.getBrRacun() + "</td>");
						out.print("<td>" + vrsteRacuna.get(racun.getSifVrsteRacuna()).getNazVrsteRacuna() + "</td>");
						out.print("<td>" + racun.getStanje() + "</td>");
						out.print("<td>" + racun.getPrekoracenje() + "</td>");
						out.print("</tr>");
					}
				}
			%>
		</table><br>
		
		<h1>Kartice korisnika</h1><br>
		<table id="user-data">
			<tr>
				<th>Broj kartice</th>
				<th>Vrsta kartice</th>
				<th>Stanje kartice</th>
				<th>Limit</th>
				<th>Kamatna stopa</th>
			</tr>
			<%
				List<Kartica> kartice = (List<Kartica>) request.getAttribute("kartice");
				Map<Integer, VrstaKartice> vrsteKartica = (Map<Integer, VrstaKartice>) request.getAttribute("vrsteKartica");
				if(kartice==null) {
					out.print("<tr> <td>X</td> <td>X</td> <td>X</td> <td>X</td> <td>X</td> </tr>");
				} else {
					for(Kartica kartica : kartice) {
						out.print("<tr>");
						out.print("<td>" + kartica.getBrKartica() + "</td>");
						out.print("<td>" + (kartica.getSifVrstaKartice()==null ? "Debitna" : vrsteKartica.get(kartica.getSifVrstaKartice()).getNazVrsteKartice()) + "</td>");
						out.print("<td>" + kartica.getStanje() + "</td>");
						out.print("<td>" + kartica.getLimitKartice() + "</td>");
						out.print("<td>" + kartica.getKamStopa() + "</td>");
						out.print("</tr>");
					}
				}
			%>
		</table><br>
		
		<h1>Krediti korisnika</h1><br>
		<table id="user-data">
			<tr>
				<th>Broj kredita</th>
				<th>Vrsta kredita</th>
				<th>Iznos</th>
				<th>Preostalo dugovanje</th>
				<th>Rok otplate</th>
				<th>Kamatna stopa</th>
			</tr>
			<%
				List<Kredit> krediti = (List<Kredit>) request.getAttribute("krediti");
				Map<Integer, VrstaKredita> vrsteKredita = (Map<Integer, VrstaKredita>) request.getAttribute("vrsteKredita");
				if(krediti==null) {
					out.print("<tr> <td>X</td> <td>X</td> <td>X</td> <td>X</td> <td>X</td> <td>X</td> </tr>");
				} else {
					for(Kredit kredit : krediti) {
						out.print("<tr>");
						out.print("<td>" + kredit.getBrKredit() + "</td>");
						out.print("<td>" + vrsteKredita.get(kredit.getSifVrsteKredita()).getNazVrsteKredita() + "</td>");
						out.print("<td>" + kredit.getIznos() + "</td>");
						out.print("<td>" + kredit.getPreostaloDugovanje() + "</td>");
						out.print("<td>" + kredit.getPeriodOtplate() + "</td>");
						out.print("<td>" + vrsteKredita.get(kredit.getSifVrsteKredita()).getKamStopa() + "</td>");
						out.print("</tr>");
					}
				}
			%>
		</table><br>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<%= request.getServletContext().getContextPath() %>/js/userProfile.js"></script>
</body>
</html>