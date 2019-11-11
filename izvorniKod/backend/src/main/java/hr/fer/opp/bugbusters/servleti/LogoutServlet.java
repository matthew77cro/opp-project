package hr.fer.opp.bugbusters.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.servleti.control.LoginHandler;

@SuppressWarnings("serial")
@WebServlet(name="logout", urlPatterns= {"/banka/logout"})
public class LogoutServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		LoginHandler.doLogout(req, resp);
		resp.sendRedirect("login");
		
	}

}
