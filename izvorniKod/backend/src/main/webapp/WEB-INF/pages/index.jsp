<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>BugBusters banka</title>
    <link rel="stylesheet" href="<%= request.getServletContext().getContextPath() %>/css/index.css">
</head>
<body>
	<header>
		<div id="header-navbar">
			<h1 id="nav-title">BugBusters banka</h1>
			<nav>
				<ul>
					<li><a href="#" id="nav-login"><h2>Prijava</h2></a></li>
					<li><a href="#" id="nav-register"><h2>Registracija</h2></a></li>
				</ul>
			</nav>
		</div>   
	</header>
	<div class="form-container">
		<form id="login-form" action="login" method="POST">
			<img src="<%= request.getServletContext().getContextPath() %>/pics/avatar.png" alt="" class="avatar">
			<h1 class="form-name">Prijava</h1>
			<% 
				if(request.getAttribute("errorMsg")!=null) {
					out.println(request.getAttribute("errorMsg").toString());
				}
			%>
			<p>Korisničko ime</p>
			<input type="text" name="username">
			<p>Lozinka</p>
			<input type="password" name="password">
			<input type="submit" name="" value="Prijava">
		</form>
	</div>
	<div class="form-container">
		<form id="register-form" action="register" method="POST">
			<img src="<%= request.getServletContext().getContextPath() %>/pics/avatar.png" alt="" class="avatar">
			<h1 class="form-name">Registracija</h1>
			<p>OIB</p>
			<input type="text" name="oib">
			<p>Ključ za registraciju</p>
			<input type="password" name="key">
			<input type="submit" name="" value="Registriraj se">
		</form>
	</div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<%= request.getServletContext().getContextPath() %>/js/main.js"></script>
</body>
</html>