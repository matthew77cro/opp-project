<%@page import="hr.fer.opp.bugbusters.dao.model.VrstaKredita"%>
<%@page import="java.util.Map"%>
<%@page import="hr.fer.opp.bugbusters.dao.model.ZahtjevKredit"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/clientProfile.css">
    <link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/creditRequestOfficial.css">
    <title>Bugbusters banka</title>
</head>
<body>
    <header>
		<div>
			<h1 id="nav-title">BugBusters banka</h1> 
		</div>
	</header>
	<nav class="topnav">
		<a href="profil" id="moj-profil">Moj profil</a>
		<a href="sluzbenik-klijenti" id="klijenti">Klijenti</a>
		<a href="sluzbenik-zahtjevi-kartice" id="kartice">Kartični zahtjevi</a>
        <a class="active" href="sluzbenik-kreditni-zahtjevi" id="krediti">Kreditni zahtjevi</a>
        <a href="logout" id="logout">Odjava</a>
    </nav>
    <div id="container">
		<h1>Kreditni zahtjevi</h1><br>
		<% 
			if(request.getAttribute("errorMsg")!=null) {
				out.println(request.getAttribute("errorMsg").toString());
			}
		%><br>
		<table class="credit-data-table">
			<tr>
				<th>Broj zahtjeva</th>
				<th>Vrsta</th>
				<th>OIB</th>
				<th>Iznos</th>
				<th>Period otplate</th>
				<th></th>
                <th></th>
			</tr>
			<%
				List<ZahtjevKredit> zahtjevi = (List<ZahtjevKredit>) request.getAttribute("zahtjevi");
				Map<Integer, VrstaKredita> vrsteKredita = (Map<Integer, VrstaKredita>) request.getAttribute("vrsteKredita");
				for(ZahtjevKredit z : zahtjevi) {
					out.print("<tr>");
					out.print("<td>" + z.getSifZahtjeva() + "</td>");
					out.print("<td>" + vrsteKredita.get(z.getSifVrsteKredita()).getNazVrsteKredita() + "</td>");
					out.print("<td>" + z.getOib() + "</td>");
					out.print("<td>" + z.getIznos() + "</td>");
					out.print("<td>" + z.getPeriodOtplate() + "</td>");
					out.print("<form action=\"\" method=\"post\">");
					out.print("<input type=\"hidden\" name=\"sifZahtjeva\" value=\"" + z.getSifZahtjeva() + "\">");
					out.print("<input type=\"hidden\" name=\"action\" value=\"approve\">");
					out.print("<td>Datum rate <input type=\"text\" name=\"datRate\" id=\"datRate\"> </td>");
					out.print("<td> <button id=\"approve\">Odobri zahtjev</button>");
					out.print("</form>");
					out.print("<form action=\"\" method=\"post\">");
					out.print("<input type=\"hidden\" name=\"sifZahtjeva\" value=\"" + z.getSifZahtjeva() + "\">");
					out.print("<input type=\"hidden\" name=\"action\" value=\"block\">");
					out.print("<button id=\"block\">Blokiraj zahtjev</button> </td>");
					out.print("</form>");
					out.print("</tr>");
				}
			%>
		</table>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<%= request.getServletContext().getContextPath() %>/js/creditRequestOfficial.js"></script>
</body>
</html>