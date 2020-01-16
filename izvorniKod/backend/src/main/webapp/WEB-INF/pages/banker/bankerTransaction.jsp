<%@page import="hr.fer.opp.bugbusters.dao.model.Racun"%>
<%@page import="hr.fer.opp.bugbusters.dao.model.Transakcija"%>
<%@page import="java.util.List"%>
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
	<link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/bankerTransaction.css">
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
		<a href="bankar-kartice" id="kartice">Kartice</a>
		<a href="bankar-krediti" id="krediti">Krediti</a>
		<a class="active" href="bankar-transakcije" id="transakcije">Transakcije</a>
		<a href="logout" id="logout">Odjava</a>
    </nav>
    
    <div id="container">	
		<h1>Transakcije</h1>
		<button id="newTransactionBtn">Nova transakcija</button> <br>
		<% 
			if(request.getAttribute("errorMsg")!=null) {
				out.println(request.getAttribute("errorMsg").toString());
			}
		%> <br>
		<div id="search-container">
			<form action="" method="POST">
				<input type="hidden" name="action" value="search">
                <label>OIB klijenta:</label>
                <input type="text" name="oib" id=""><br>
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
				List<Transakcija> transakcije = (List<Transakcija>) request.getAttribute("transakcije");
				if(transakcije==null) {
					out.print("<tr> <td>X</td> <td>X</td> <td>X</td> <td>X</td> <td>X</td> </tr>");
				} else {
					for(Transakcija t : transakcije) {
						out.print("<tr>");
						out.print("<td>" + t.getBrTransakcija() + "</td>");
						out.print("<td>" + t.getRacTerecenja() + "</td>");
						out.print("<td>" + t.getRacOdobrenja() + "</td>");
						out.print("<td>" + t.getIznos() + "</td>");
						out.print("<td>" + t.getDatTransakcije() + "</td>");
						out.print("</tr>");
					}
				}
			%>
		</table>
	</div>

	<div id="new-transaction">		
		<form action="" method="POST">
			<input type="hidden" name="action" value="add">
			<label>Račun terećenja:</label>
			<select name="racTerecenja">
				<%
					List<Racun> racuni = (List<Racun>) request.getAttribute("racuni");
					if(racuni==null) {
						out.print("<option value=\"null\">Nema dostupnih računa</option>");
					} else {
						for(Racun r : racuni) {
							out.print("<option value=\"" + r.getBrRacun() + "\">" + r.getBrRacun() + " : " + r.getStanje() + "</option>");
						}
					}
				%>
			</select>
			<label>Račun odobrenja:</label>
			<input id="approvalAccount" type="text" name="racOdobrenja">
			<label>Iznos plaćanja:</label>
			<input id="amount" type="text" placeholder="HRK" name="iznos">
			<input id="finishTransaction" type="submit" name="" value="Provedi transakciju">
			<button type="button" id="cancel">Odustani</button>
		</form>
	</div>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<%= request.getServletContext().getContextPath() %>/js/bankerTransaction.js"></script>
</body>
</html>