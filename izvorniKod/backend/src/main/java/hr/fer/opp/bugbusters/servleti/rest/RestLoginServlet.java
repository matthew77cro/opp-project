package hr.fer.opp.bugbusters.servleti.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.servleti.control.LoginHandler;

@SuppressWarnings("serial")
@WebServlet(name="rest-login", urlPatterns= {"/rest/login"})
public class RestLoginServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(LoginHandler.doLogin(req, resp) && !LoginHandler.needsPasswordChange(req, resp)) {
			resp.setStatus(HttpServletResponse.SC_OK);
		} else {
			LoginHandler.doLogout(req, resp);
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
	}

}
