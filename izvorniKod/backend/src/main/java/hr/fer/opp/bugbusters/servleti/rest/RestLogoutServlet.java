package hr.fer.opp.bugbusters.servleti.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.control.LoginHandler;

@SuppressWarnings("serial")
@WebServlet(name="rest-logout", urlPatterns= {"/rest/logout"})
public class RestLogoutServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		LoginHandler.doLogout(req, resp);
		resp.setStatus(HttpServletResponse.SC_OK);
		
	}
	
}
