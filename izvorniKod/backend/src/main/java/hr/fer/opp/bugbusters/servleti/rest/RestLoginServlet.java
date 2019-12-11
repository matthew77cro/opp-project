package hr.fer.opp.bugbusters.servleti.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.control.LoginHandler;

@SuppressWarnings("serial")
@WebServlet(name="rest-login", urlPatterns= {"/rest/login"})
public class RestLoginServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		LoginHandler.doLogout(req, resp);
		
		if(LoginHandler.doLogin(req, resp)) {
			if(LoginHandler.needsPasswordChange(req, resp)) {
				resp.setStatus(HttpServletResponse.SC_RESET_CONTENT);
			} else {
				resp.setStatus(HttpServletResponse.SC_OK);
			}
		} else {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
	}

}
