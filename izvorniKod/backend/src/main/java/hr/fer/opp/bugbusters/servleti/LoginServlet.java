package hr.fer.opp.bugbusters.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.control.LoginHandler;

@SuppressWarnings("serial")
@WebServlet(name="login", urlPatterns= {"/banka/login"})
public class LoginServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(LoginHandler.isLoggedIn(req, resp)) {
			resp.sendRedirect("profil");
		} else if(LoginHandler.isInRegisterState(req, resp)) {
			resp.sendRedirect("register");
		} else {
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(LoginHandler.isLoggedIn(req, resp)) {
			doGet(req, resp);
			return;
		}
		
		String action = req.getParameter("action");
		if(action==null) {
			doGet(req, resp);
			return;
		}
		
		if(action.equals("login")) {
			if(!LoginHandler.doLogin(req, resp)) {
				req.setAttribute("errorMsgLogin", "Pogrešno korisničko ime ili lozinka");			
			}
			
			doGet(req, resp);
			return;
		} else if(action.equals("register")) {
			if(!LoginHandler.triggerRegisterState(req, resp)) {
				req.setAttribute("errorMsgRegister", "Pogrešan oib ili privremeni ključ");
				doGet(req, resp);
				return;
			}
			
			resp.sendRedirect("register");
			return;
		}
		
		doGet(req, resp);
		
	}

}
