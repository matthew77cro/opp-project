package hr.fer.opp.bugbusters.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.control.LoginHandler;

@SuppressWarnings("serial")
@WebServlet(name="register", urlPatterns= {"/banka/register"})
public class RegisterServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		if(!LoginHandler.isInRegisterState(req, resp)) {
			resp.sendRedirect("login");
			return;
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/client/register.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isInRegisterState(req, resp)) {
			resp.sendRedirect("login");
			return;
		}
		
		if(!LoginHandler.doRegister(req, resp)) {
			req.setAttribute("errorMsg", "Korisničko ime već postoji.");
			req.getRequestDispatcher("/WEB-INF/pages/client/register.jsp").forward(req, resp);
		} else {
			resp.sendRedirect("login");
			return;
		}
		
	}
	
}
