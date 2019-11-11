<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>BugBusters banka</title>
    <link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/profile.css">
</head>
<body>
	<header>
		<div>
			<h1 id="nav-title">BugBusters banka</h1> 
		</div>
	</header>
	<nav class="topnav">
		<a class="active" href="#" id="moj-profil">Moj profil</a>
		<a href="#" id="racuni">Računi</a>
		<a href="#" id="kartice">Kartice</a>
		<a href="#" id="krediti">Krediti</a>
		<a href="logout" id="logout">Odjava</a>
	</nav>
	<div id="container">
		<h1>Osobni podaci</h1>
		<img src="<%= request.getServletContext().getContextPath() %>/rest/profil/slika" alt="" class="avatar">
		<table id="user-data-table">
			<tr>
				<td>Ime</td>
				<td>
				<% 
					if(request.getAttribute("firstName")!=null) {
						out.print(request.getAttribute("firstName").toString());
					}
				%>
				</td>
			</tr>
			<tr>
				<td>Prezime</td>
				<td>
				<% 
					if(request.getAttribute("lastName")!=null) {
						out.print(request.getAttribute("lastName").toString());
					}
				%>
				</td>
			</tr>
			<tr>
				<td>Adresa</td>
				<td>
				<% 
					if(request.getAttribute("address")!=null) {
						out.print(request.getAttribute("address").toString());
					}
				%>
				</td>
			</tr>
			<tr>
				<td>OIB</td>
				<td>
				<% 
					if(request.getAttribute("oib")!=null) {
						out.print(request.getAttribute("oib").toString());
					}
				%>
				</td>
			</tr>
			<tr>
				<td>Datum rođenja</td>
				<td>
				<% 
					if(request.getAttribute("birthday")!=null) {
						out.print(request.getAttribute("birthday").toString());
					}
				%>
				</td>
			</tr>
			<tr>
				<td>E-mail</td>
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