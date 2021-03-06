package hr.fer.opp.bugbusters.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.control.LoginHandler;

@SuppressWarnings("serial")
@WebServlet(name="passwordchange", urlPatterns= {"/banka/passwordchange"})
public class PasswordChangeServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp)) {
			resp.sendRedirect("login");
			return;
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/passwordChange.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp)) {
			resp.sendRedirect("login");
			return;
		}
		
		if(!LoginHandler.changePassword(req, resp)) {
			req.setAttribute("errorMsg", "Greška! Pokušajte ponovno. <br> Napomena: Nova lozinka ne smije biti prazna ili ista kao prošla.");
			req.getRequestDispatcher("/WEB-INF/pages/passwordChange.jsp").forward(req, resp);
		} else {
			LoginHandler.doLogout(req, resp);
			resp.sendRedirect("profil");
		}
		
	}

}
