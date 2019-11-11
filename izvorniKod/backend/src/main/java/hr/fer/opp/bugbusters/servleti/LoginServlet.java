package hr.fer.opp.bugbusters.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.servleti.control.LoginHandler;

@SuppressWarnings("serial")
@WebServlet(name="login", urlPatterns= {"/banka/login"})
public class LoginServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(LoginHandler.isLoggedIn(req, resp)) {
			resp.sendRedirect("profil");
		} else {
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(LoginHandler.doLogin(req, resp)) {
			resp.sendRedirect("profil");
		} else {
			req.setAttribute("errorMsg", "Username or password incorrect!");
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
		}
		
	}

}
