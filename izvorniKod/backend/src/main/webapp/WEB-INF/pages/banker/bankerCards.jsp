<%@page import="java.util.List"%>
<%@page import="java.util.Map.Entry"%>
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
    <link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/clientProfile.css">
    <link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/bankerCards.css">
    <title>BugBusters banka</title>
</head>
<body>
    <header>
		<div>
			<h1 id="nav-title">BugBusters banka</h1> 
		</div>
	</header>
	<nav class="topnav">
		<a href="profil" id="moj-profil">Moj profil</a>
        <a href="bankar-klijenti" id="klijenti">Klijenti</a>
        <a href="bankar-racuni" id="racuni">Računi</a>
		<a class="active" href="bankar-kartice" id="kartice">Kartice</a>
		<a href="bankar-krediti" id="krediti">Krediti</a>
		<a href="bankar-transakcije" id="transakcije">Transakcije</a>
		<a href="logout" id="logout">Odjava</a>
    </nav>
    <div id="container">
		<h1>Kartice</h1>
        <button id="newDebitCardBtn">Dodaj debitnu karticu</button>
        <button id="newCreditCardBtn">Dodaj kreditnu karticu</button>
        <button id="deleteCardBtn">Izbriši karticu</button> <br>
        <% 
			if(request.getAttribute("errorMsg")!=null) {
				out.println(request.getAttribute("errorMsg").toString());
			}
		%>
        <br>
        <form action="" method="post">
        	<input type="hidden" name="action" value="search">
	        <label>OIB klijenta: </label>
	        <input type="text" name="oib" id="searchBox">
	        <button id="search-client">Pretraži</button>
        </form>
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
				Map<Kartica, VrstaKartice> kartice = (Map<Kartica, VrstaKartice>) request.getAttribute("kartice");
				if(kartice==null) {
					out.print("<tr> <td>X</td> <td>X</td> <td>X</td> <td>X</td> <td>X</td> <td>X</td> <td>X</td> <td>X</td> </tr>");
				} else {
					for(Entry<Kartica, VrstaKartice> entry : kartice.entrySet()) {
						out.print("<tr>");
						if(entry.getValue()==null)
							out.print("<td>Debitna</td>");
						else
							out.print("<td>" + entry.getValue().getNazVrsteKartice() + "</td>");
						out.print("<td>" + entry.getKey().getBrKartica() + "</td>");
						out.print("<td>" + entry.getKey().getBrRacun() + "</td>");
						out.print("<td>" + entry.getKey().getStanje() + "</td>");
						out.print("<td>" + entry.getKey().getValjanost().toString() + "</td>");
						out.print("<td>" + entry.getKey().getLimitKartice() + "</td>");
						out.print("<td>" + entry.getKey().getKamStopa() + "</td>");
						out.print("<td>" + entry.getKey().getDatRate() + "</td>");
						out.print("</tr>");
					}
				}
			%>
		</table>
    </div>
    <div id="new-debit-card" class="new-card">
        <form action="" method="post">
        	<input type="hidden" name="action" value="add">
            <label>Broj: </label>
            <input type="text" name="broj" id="">
            <label>Broj računa: </label>
            <input type="text" name="brojRacuna" id="">
            <label>Valjanost: </label>
            <input type="date" name="valjanost" id=""> <br>
            <input id="add" type="submit" name="" value="Dodaj karticu">
			<button type="button" class="cancel">Odustani</button>
        </form>
    </div>
    <div id="new-credit-card" class="new-card">
        <form action="" method="post">
        	<input type="hidden" name="action" value="add">
        	<label>OIB: </label>
            <input type="text" name="oib" id="">
            <label>Vrsta kartice: </label>
            <select name="vrstaKartice">
           	<%
				List<VrstaKartice> vrste = (List<VrstaKartice>) request.getAttribute("vrste");
				for(VrstaKartice vr : vrste) {
					out.print("<option value=\"" + vr.getSifVrsteKartice() + "\">" + vr.getNazVrsteKartice() + "</option>");
				}
			%>
            </select><br>
            <label>Broj: </label>
            <input type="text" name="broj" id="">
            <label>Valjanost: </label>
            <input type="date" name="valjanost" id=""> <br>
            <label>Limit: </label>
            <input type="text" name="limit" id="">
            <label>Kamatna stopa</label>
            <input type="text" name="kamStopa" id="">
            <label>Datum rate: </label>
            <input type="text" name="datRate" id="">
            <input id="add" type="submit" name="" value="Dodaj karticu">
			<button type="button" class="cancel">Odustani</button>
        </form>
    </div>
    <div id="delete-card">
        <form action="" method="post">
        	<input type="hidden" name="action" value="delete">
            <label>OIB klijenta: </label>
            <input id="" name="oib" type="text"> 
            <br>
            <label>Broj kartice: </label>
            <input id="" name="broj" type="text"> 
            <br>
            <input id="add" type="submit" name="" value="Obriši karticu">
			<button type="button" class="cancel">Odustani</button>
        </form>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<%= request.getServletContext().getContextPath() %>/js/bankerCards.js"></script>
</body>
</html>