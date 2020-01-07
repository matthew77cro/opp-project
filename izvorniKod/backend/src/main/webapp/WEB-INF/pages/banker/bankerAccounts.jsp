<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="hr.fer.opp.bugbusters.dao.model.Racun"%>
<%@page import="hr.fer.opp.bugbusters.dao.model.VrstaRacuna"%>
<%@page import="java.util.Map.Entry"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/clientProfile.css">
    <link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/bankerAccounts.css">
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
        <a class="active" href="bankar-racuni" id="racuni">Računi</a>
		<a href="bankar-kartice" id="kartice">Kartice</a>
		<a href="bankar-krediti" id="krediti">Krediti</a>
		<a href="bankar-transakcije" id="transakcije">Transakcije</a>
		<a href="logout" id="logout">Odjava</a>
    </nav>

    <div id="container">
        <h1>Računi</h1>
        <button id="addAccountBtn">Dodaj račun</button>
        <button id="deleteAccountBtn">Izbriši račun</button> <br>
        <% 
			if(request.getAttribute("errorMsg")!=null) {
				out.println(request.getAttribute("errorMsg").toString());
			}
		%>
        <br>
        <form action="" method="POST">
        	<input type="hidden" name="action" value="search">
			<label>Upiši OIB klijenta: </label>
			<input id="searchBox" name="oib" type="text">
			<button id="searchAccountBtn">Pretraži račune</button>
		</form>
		<table id="account-data-table">
			<tr>
				<th>Vrsta</th>
				<th>Broj</th>
				<th>Stanje</th>
				<th>Prekoračenje</th>
				<th>Kamatna stopa</th>
			</tr>
			<%
				Map<Racun, VrstaRacuna> racuni = (Map<Racun, VrstaRacuna>) request.getAttribute("racuni");
				if(racuni==null) {
					out.print("<tr> <td>X</td> <td>X</td> <td>X</td> <td>X</td> <td>X</td> </tr>");
				} else {
					for(Entry<Racun, VrstaRacuna> entry : racuni.entrySet()) {
						out.print("<tr>");
						out.print("<td>" + entry.getValue().getNazVrsteRacuna() + "</td>");
						out.print("<td>" + entry.getKey().getBrRacun() + "</td>");
						out.print("<td>" + entry.getKey().getStanje().toString() + "</td>");
						out.print("<td>" + entry.getKey().getPrekoracenje().toString() + "</td>");
						out.print("<td>" + entry.getKey().getKamStopa().toString() + "</td>");
						out.print("</tr>");
					}
				}
			%>
		</table>
	</div>
    <div id="new-account">
        <form action="" method="POST">
        	<input type="hidden" name="action" value="add">
			<label>OIB klijenta</label>
			<input id="client-oib" name="oib" type="text">
			<label>Vrsta računa</label>
			<select name="vrstaRacuna" id="account-type-option">
				<%
					List<VrstaRacuna> vrste = (List<VrstaRacuna>) request.getAttribute("vrste");
					for(VrstaRacuna vr : vrste) {
						out.print("<option value=\"" + vr.getSifVrsteRacuna() + "\">" + vr.getNazVrsteRacuna() + "</option>");
					}
				%>
            </select>
			<label>Broj računa</label>
			<input id="account-number" name="brojRacuna" type="text">
			<label>Prekoračenje</label>
			<input id="account-limit" name="prekoracenje" type="text">
			<label>Kamatna stopa</label>
			<input id="account-rate" name="kamStopa" type="text">		
			<input id="add" type="submit" value="Dodaj račun">
			<button type="button" id="cancel" class="cancel">Odustani</button>
		</form>
    </div>

    <div id="delete-account">
        <form action="" method="post">
        	<input type="hidden" name="action" value="delete">
            <label>OIB klijenta:</label>
            <input name="oib" type="text"> 
            <br>
            <label>Broj računa:</label>
            <input name="brojRacuna" type="text"> 
            <br>
            <input id="add" type="submit" name="" value="Obriši račun">
			<button type="button" id="cancel" class="cancel">Odustani</button>
        </form>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<%= request.getServletContext().getContextPath() %>/js/bankerAccounts.js"></script>
</body>
</html>