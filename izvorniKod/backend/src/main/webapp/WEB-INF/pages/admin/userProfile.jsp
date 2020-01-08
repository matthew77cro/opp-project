<%@page import="java.util.Map.Entry"%>
<%@page import="hr.fer.opp.bugbusters.dao.model.RazinaOvlasti"%>
<%@page import="java.util.Map"%>
<%@page import="hr.fer.opp.bugbusters.dao.model.KorisnickiRacun"%>
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
    <link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/adminProfile.css">
	<link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/userProfile.css">
	<%
    	String ime = request.getAttribute("firstName")==null ? "X" : request.getAttribute("firstName").toString();
   		String prezime = request.getAttribute("lastName")==null ? "X" : request.getAttribute("lastName").toString();
    	String adresa = request.getAttribute("address")==null ? "X" : request.getAttribute("address").toString();
    	String adresaUl = request.getAttribute("addressUl")==null ? "X" : request.getAttribute("addressUl").toString();
    	String adresaPbr = request.getAttribute("addressPbr")==null ? "X" : request.getAttribute("addressPbr").toString();
    	String oib = request.getAttribute("oib")==null ? "X" : request.getAttribute("oib").toString();
    	String datRod = request.getAttribute("birthday")==null ? "X" : request.getAttribute("birthday").toString();
    	String email = request.getAttribute("email")==null ? "X" : request.getAttribute("email").toString();
    	
		Map<Integer, RazinaOvlasti> razineOvlasti = (Map<Integer, RazinaOvlasti>) request.getAttribute("razineOvlasti");
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
		<a class="active" href="admin-profili" id="racuni">Korisnički profili</a>
		<a href="logout" id="logout">Odjava</a>
	</nav>
	<div id="container">
		<h1>Korisnički profili</h1>
		<button id="addProfileBtn" class="btn">Dodaj profil</button>
		<button id="updateProfileBtn" class="btn">Izmijeni profil</button>
		<button id="deleteProfileBtn" class="btn">Izbriši profil</button><br>
		<% 
			if(request.getAttribute("errorMsg")!=null) {
				out.println(request.getAttribute("errorMsg").toString());
			}
		%><br>
		<form action="" method="post">
			<input type="hidden" name="action" value="search">
			<label>Upiši OIB: </label>
			<input name="oib" type="text">
			<button id="searchProfileBtn" class="btn">Pretraži</button>
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
			<tr>
				<th>Korisnički računi</th>
				<td>
					<button id="showAccounts" class="accountBtn">Prikaži</button>
					<button id="createAccount" class="accountBtn">Novi račun</button>
				</td>
			</tr>
		</table>
		<table id="userAccounts">
			<tr>
				<th>Korisničko ime</th>
				<th>Razina ovlasti</th>
				<th><button id="hideAccounts" class="accountBtn">Sakrij</button></th>
			</tr>
			<%
				List<KorisnickiRacun> korisnickiRacuni = (List<KorisnickiRacun>) request.getAttribute("korisnickiRacuni");
			
				if(korisnickiRacuni==null) {
					out.print("<tr> <td>X</td> <td>X</td> <td>X</td> </tr>");
				} else {
					for(KorisnickiRacun racun : korisnickiRacuni) {
						out.print("<tr>");
						out.print("<td>" + racun.getKorisnickoIme() + "</td>");
						out.print("<td>" + razineOvlasti.get(racun.getSifRazOvlasti()).getNazivRazOvlasti() + "</td>");
						out.print("<td>" +
						"<button id=\"update2\" class=\"updateBtn accountBtn\">Izmijeni</button>" +
						"<form action=\"\" method=\"post\"> <input type=\"hidden\" name=\"action\" value=\"remove\"> <input type=\"hidden\" name=\"korIme\" value=\"" + racun.getKorisnickoIme() + "\"> <button class=\"deleteBtn accountBtn\">Obriši</button> </form>" +
						"</td>");
						out.print("</tr>");
					}
				}
			%>
		</table>
	</div>
	<div id="addProfileForm">
		<form action="" method="post" enctype="multipart/form-data">
			<input type="hidden" name="action" value="create">
			<label>Ime</label>
			<input id="name" name="ime" type="text">
			<label>Prezime</label>
			<input id="surname" name="prezime" type="text">
			<label>Adresa (ulica i kućni broj)</label>
			<input id="address" name="adresa" type="text">
			<label>Pbr</label>
			<input id="addressPbr" name="pbr" type="text">
			<label>OIB</label>
			<input id="oib" name="oib" type="text">
			<label>Datum rođenja</label>
			<input id="birthDate" name="datRod" type="date"><br>
			<label>E-mail</label>
			<input id="text" name="email" type="text">
			<label>Slika profila</label>
			<input type="file" name="pic" accept="image/*"><br>
			<input id="add" type="submit" name="" value="Dodaj korisnika">
			<button type="button" id="cancel">Odustani</button>
		</form>
	</div>
	<div id="updateProfileForm">
		<center><img src="<%= request.getServletContext().getContextPath() %>/rest/profil/slika?oib=<%= oib %>" alt="" id="avatar"></center>
		<form action="" method="post" enctype="multipart/form-data">
			<input type="hidden" name="action" value="update">
			<input type="hidden" name="oldOib" value="<% out.print(oib); %>">
			<label>Ime</label>
			<input id="name2" name="ime" type="text" value="<% out.print(ime); %>">
			<label>Prezime</label>
			<input id="surname2" name="prezime" type="text" value="<% out.print(prezime); %>">
			<label>Adresa (ulica i kućni broj)</label>
			<input id="address2" name="adresa" type="text" value="<% out.print(adresaUl); %>">
			<label>Pbr</label>
			<input id="addressPbr" name="pbr" type="text" value="<% out.print(adresaPbr); %>">
			<label>OIB</label>
			<input id="oib2" name="oib" type="text" value="<% out.print(oib); %>">
			<label>Datum rođenja</label>
			<input id="birthDate2" name="datRod" type="date" value="<% out.print(datRod); %>"><br>
			<label>E-mail</label>
			<input id="email2" name="email" type="text" value="<% out.print(email); %>">
			<label>Slika profila</label> <br>
			<input type="file" name="pic" accept="image/*"><br>
			<input id="update" type="submit" name="" value="Ažuriraj podatke">
			<button type="button" id="cancel2">Odustani</button>
		</form>
	</div>
	<div id="deleteProfileForm">
		<form action="" method="post">
			<input type="hidden" name="action" value="delete">
			<input type="hidden" name="oib" value="<% out.print(oib); %>">
			<label>Ime</label>
			<input id="name3" value="<% out.print(ime); %>" disabled class="data">
			<label>Prezime</label>
			<input id="surname3" value="<% out.print(prezime); %>" disabled class="data">
			<label>Adresa</label>
			<input id="address3" value="<% out.print(adresa); %>" disabled class="data">
			<label>OIB</label>
			<input id="oib3" value="<% out.print(oib); %>" disabled class="data">
			<label>Datum rođenja</label>
			<input id="birthDate3" value="<% out.print(datRod); %>" disabled class="data">
			<label>E-mail</label>
			<input id="email3" value="<% out.print(email); %>" disabled class="data">
			<label>Slika profila</label> <br>
			<center><img src="<%= request.getServletContext().getContextPath() %>/rest/profil/slika?oib=<%= oib %>" alt="" id="avatar"></center> <br>
			<input id="delete" type="submit" name="" value="Izbriši profil">
			<button type="button" id="cancel3">Odustani</button>
		</form>
	</div>
	<div id="addAccountForm">
		<form action="" method="POST">
			<input type="hidden" name="action" value="add">
			<input type="hidden" name="oib" value="<% out.print(oib); %>">
			<label>Korisničko ime</label>
			<input id="username" name="username" type="text">
			<label>Lozinka</label>
			<input id="username" name="password" type="password">
			<label>Razina ovlasti</label>
			<select name="razOvlasti">
				<%
					for(RazinaOvlasti ro : razineOvlasti.values()) {
						out.print("<option value=\"" + ro.getSifRazOvlasti() + "\">" + ro.getNazivRazOvlasti() + "</option>");
					}
				%>
			</select>
			<input id="addAccount" type="submit" name="" value="Dodaj račun">
			<button type="button" id="cancel4">Odustani</button>
		</form>
	</div>
	<div id="updateAccountForm">
		<form action="" method="POST">
			<input type="hidden" name="action" value="change">
			<input type="hidden" name="oib" value="<% out.print(oib); %>">
			<label>Korisničko ime</label>
			<input id="username2" type="text" name="username">
			<label>Razina ovlasti</label>
			<select name="razOvlasti">
				<%
					for(RazinaOvlasti ro : razineOvlasti.values()) {
						out.print("<option value=\"" + ro.getSifRazOvlasti() + "\">" + ro.getNazivRazOvlasti() + "</option>");
					}
				%>
			</select>
			<input id="updateAccount" type="submit" name="" value="Izmijeni račun">
			<button type="button" id="cancel5">Odustani</button>
		</form>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<%= request.getServletContext().getContextPath() %>/js/userProfile.js"></script>
</body>
</html>