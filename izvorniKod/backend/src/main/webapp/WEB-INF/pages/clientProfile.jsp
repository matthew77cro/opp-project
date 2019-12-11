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
</head>
<body>
	<header>
		<div>
			<h1 id="nav-title">BugBusters banka</h1> 
		</div>
	</header>
	<nav class="topnav">
		<a class="active" href="profil" id="moj-profil">Moj profil</a>
		<a href="racuni" id="racuni">Računi</a>
		<a href="kartice" id="kartice">Kartice</a>
		<a href="krediti" id="krediti">Krediti</a>
		<a href="transakcije" id="transakcije">Transakcije</a>
		<a href="logout" id="logout">Odjava</a>
	</nav>
	<div id="container">
		<h1>Osobni podaci</h1>
		<img src="<%= request.getServletContext().getContextPath() %>/rest/profil/slika" alt="" class="avatar">
		<table id="user-data-table">
			<tr>
				<th>Ime</th>
				<td>
				<% 
					if(request.getAttribute("firstName")!=null) {
						out.print(request.getAttribute("firstName").toString());
					}
				%>
				</td>
			</tr>
			<tr>
				<th>Prezime</th>
				<td>
				<% 
					if(request.getAttribute("lastName")!=null) {
						out.print(request.getAttribute("lastName").toString());
					}
				%>
				</td>
			</tr>
			<tr>
				<th>Adresa</th>
				<td>
				<% 
					if(request.getAttribute("address")!=null) {
						out.print(request.getAttribute("address").toString());
					}
				%>
				</td>
			</tr>
			<tr>
				<th>OIB</th>
				<td>
				<% 
					if(request.getAttribute("oib")!=null) {
						out.print(request.getAttribute("oib").toString());
					}
				%>
				</td>
			</tr>
			<tr>
				<th>Datum rođenja</th>
				<td>
				<% 
					if(request.getAttribute("birthday")!=null) {
						out.print(request.getAttribute("birthday").toString());
					}
				%>
				</td>
			</tr>
			<tr>
				<th>E-mail</th>
				<td>
				<% 
					if(request.getAttribute("email")!=null) {
						out.print(request.getAttribute("email").toString());
					}
				%>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>