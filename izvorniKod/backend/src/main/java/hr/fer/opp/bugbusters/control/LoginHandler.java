package hr.fer.opp.bugbusters.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.KorisnickiRacun;

public class LoginHandler {
	
	// Returns true iff login is successful (username and password are not empty, user exists, password is ok)
	public static boolean doLogin(HttpServletRequest req, HttpServletResponse resp) {
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		if(username==null || password==null) return false;
		
		String passwordHash = Util.hash(password);
		KorisnickiRacun kr = DAOProvider.getDao().getKorisnickiRacun(username);
		if(kr==null || !passwordHash.equals(kr.getLozinka())) return false;
		
		req.getSession().setAttribute("login", username);
		req.getSession().setAttribute("passwordChange", kr.isPromjenaLozinke());
		
		return true;
		
	}
	
	// Logs out the user (removes attributes from the session)
	public static void doLogout(HttpServletRequest req, HttpServletResponse resp) {
					
		req.getSession().removeAttribute("login");
		req.getSession().removeAttribute("passwordChange");
		req.getSession().invalidate();
		
	}
	
	// Returns true iff user is logged in (attribute in the session exists)
	public static boolean isLoggedIn(HttpServletRequest req, HttpServletResponse resp) {
		
		return req.getSession().getAttribute("login")!=null;
		
	}
	
	// Returns username iff user is logged in (null otherwise)
	public static String getUsername(HttpServletRequest req, HttpServletResponse resp) {
		
		if(!isLoggedIn(req, resp)) return null;
		return req.getSession().getAttribute("login").toString();
		
	}
	
	public static boolean needsPasswordChange(HttpServletRequest req, HttpServletResponse resp) {
		
		return ((Boolean)req.getSession().getAttribute("passwordChange"));
		
	}
	
	// Returns false iff user is not logged in or req parameters are incorrect or new password is equal
	// to the old one or new password is empty
	// Returns true otherwise
	public static boolean changePassword(HttpServletRequest req, HttpServletResponse resp) {
		
		if(!isLoggedIn(req, resp)) return false;
		String username = getUsername(req, resp);
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirmPassword");
		if(password==null || confirmPassword==null || !confirmPassword.equals(password) || password.isEmpty()) return false;
		
		KorisnickiRacun kr = DAOProvider.getDao().getKorisnickiRacun(username);
		String newPasswordHash = Util.hash(password);
		if(kr.getLozinka().equals(newPasswordHash)) return false;
		
		boolean changed = DAOProvider.getDao().updateKorisinckiRacunPassword(username, newPasswordHash);
		if(changed) req.getSession().setAttribute("passwordChange", false);
		return changed;
		
	}
	
	public static class LoginDescriptor {
		private String username;
		private String oib;
		
		public LoginDescriptor(String username, String oib) {
			this.username = username;
			this.oib = oib;
		}
		
		public String getUsername() {
			return username;
		}
		
		public String getOib() {
			return oib;
		}
	}

}
