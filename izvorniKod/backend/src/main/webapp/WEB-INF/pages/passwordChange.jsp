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
		<div id="header-navbar">
			<h1 id="nav-title">BugBusters banka</h1>
		</div>   
	</header>
	<div id="changePasswordForm">
		<form action="passwordchange" method="POST">
			<% 
				if(request.getAttribute("errorMsg")!=null) {
					out.println(request.getAttribute("errorMsg").toString());
				}
			%>
			<center><h3>Promijenite lozinku</h3></center>
			<label>Stara lozinka</label>
			<input id="oldPassword" name="oldPassword" type="password">
			<label>Nova lozinka</label>
			<input id="newPassword" name="newPassword" type="password">
			<label>Potvrdi novu lozinku</label>
			<input id="confirmNewPassword" name="confirmNewPassword" type="password">
			<input id="change" type="submit" name="" value="Dovrši">
		</form>
	</div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<%= request.getServletContext().getContextPath() %>/js/main.js"></script>
</body>
</html>