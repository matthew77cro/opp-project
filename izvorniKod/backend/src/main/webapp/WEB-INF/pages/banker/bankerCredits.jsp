<%@page import="java.util.List"%>
<%@page import="java.util.Map.Entry"%>
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
    <link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/clientProfile.css">
    <link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/bankerCredits.css">
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
		<a href="bankar-kartice" id="kartice">Kartice</a>
		<a class="active" href="bankar-krediti" id="krediti">Krediti</a>
		<a href="bankar-transakcije" id="transakcije">Transakcije</a>
		<a href="logout" id="logout">Odjava</a>
    </nav>
    <div id="container">
        <h1>Krediti</h1>
        <button id="newCreditBtn">Novi kredit</button> <br>
        <% 
			if(request.getAttribute("errorMsg")!=null) {
				out.println(request.getAttribute("errorMsg").toString());
			}
		%> <br>
        <form action="" method="post">
        	<input type="hidden" name="action" value="search">
            <label>OIB klijenta: </label>
            <input name="oib" type="text"> 
            <button id="select-credit">Odaberi</button>
        </form>
        <table id="credit-data-table">
            <tr>
                <th>Vrsta</th>
                <th>Broj kredita</th>
                <th>Datum ugovaranja</th>
                <th>Period otplate</th>
                <th>Datum plaćanja rate</th>
                <th>Iznos</th>
                <th>Preostalo dugovanje</th>
                <th>Kamata</th>
            </tr>
            <%
				Map<Kredit, VrstaKredita> krediti = (Map<Kredit, VrstaKredita>) request.getAttribute("krediti");
				if(krediti==null) {
					out.print("<tr> <td>X</td> <td>X</td> <td>X</td> <td>X</td> <td>X</td> <td>X</td> <td>X</td> <td>X</td> </tr>");
				} else {
					for(Entry<Kredit, VrstaKredita> entry : krediti.entrySet()) {
						out.print("<tr>");
						out.print("<td>" + entry.getValue().getNazVrsteKredita() + "</td>");
						out.print("<td>" + entry.getKey().getBrKredit() + "</td>");
						out.print("<td>" + entry.getKey().getDatUgovaranja() + "</td>");
						out.print("<td>" + entry.getKey().getPeriodOtplate() + "</td>");
						out.print("<td>" + entry.getKey().getDatRate() + "</td>");
						out.print("<td>" + entry.getKey().getIznos() + "</td>");
						out.print("<td>" + entry.getKey().getPreostaloDugovanje() + "</td>");
						out.print("<td>" + entry.getValue().getKamStopa() + "</td>");
						out.print("</tr>");
					}
				}
			%>
        </table>
    </div>
    <div id="new-credit">
        <form action="" method="POST">
        	<input type="hidden" name="action" value="add">
            <label>OIB klijenta:</label>
            <input type="text" name="oib" id="">
            <label>Vrsta kredita:</label>
			<select name="vrstaKredita">
				<%
					List<VrstaKredita> vrste = (List<VrstaKredita>) request.getAttribute("vrste");
					for(VrstaKredita vr : vrste) {
						out.print("<option value=\"" + vr.getSifVrsteKredita() + "\">" + vr.getNazVrsteKredita() + " : " + vr.getKamStopa() + "</option>");
					}
				%>
			</select>
			<label>Iznos:</label>
			<input id="amount" type="text" placeholder="HRK" name="iznos">
			<label>Rok otplate:</label>
			<input id="time" type="text" placeholder="BROJ MJESECI" name="rokOtplate">
			<input id="request" type="submit" name="" value="Provedi zahtjev">
			<button type="button" id="cancel">Odustani</button>
		</form>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<%= request.getServletContext().getContextPath() %>/js/bankerCredits.js"></script>
</body>
</html>