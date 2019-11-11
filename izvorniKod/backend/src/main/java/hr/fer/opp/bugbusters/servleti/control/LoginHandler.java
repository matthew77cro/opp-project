package hr.fer.opp.bugbusters.servleti.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.KorisnickiRacun;

public class LoginHandler {
	
public static boolean doLogin(HttpServletRequest req, HttpServletResponse resp) {
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		if(username==null || password==null) return false;
		
		String passwordHash = Util.hash(password);
		KorisnickiRacun kr = DAOProvider.getDao().getKorisnickiRacun(username);
		if(kr==null || !passwordHash.equals(kr.getLozinka())) return false;
		
		req.getSession().setAttribute("login", username);
		
		return true;
		
	}
	
	public static void doLogout(HttpServletRequest req, HttpServletResponse resp) {
					
		req.getSession().removeAttribute("login");
		req.getSession().invalidate();
		
	}
	
	public static boolean isLoggedIn(HttpServletRequest req, HttpServletResponse resp) {
		
		return req.getSession().getAttribute("login")!=null;
		
	}
	
	public static String getUsername(HttpServletRequest req, HttpServletResponse resp) {
		
		return req.getSession().getAttribute("login").toString();
		
	}

}
