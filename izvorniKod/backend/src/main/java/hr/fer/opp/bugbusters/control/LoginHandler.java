package hr.fer.opp.bugbusters.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.Constants;
import hr.fer.opp.bugbusters.dao.model.KorisnickiRacun;
import hr.fer.opp.bugbusters.dao.model.RazinaOvlasti;
import hr.fer.opp.bugbusters.dao.model.RegistracijaKlijenta;

public class LoginHandler {
	
	// Returns true iff login is successful (username and password are not empty, user exists, password is ok)
	public static boolean doLogin(HttpServletRequest req, HttpServletResponse resp) {
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		if(username==null || password==null) return false;
		
		String passwordHash = Util.hash(password);
		KorisnickiRacun kr = DAOProvider.getDao().getKorisnickiRacun(username);
		if(kr==null || !passwordHash.equals(kr.getLozinka())) return false;
		
		req.getSession().setAttribute("login", new LoginDescriptor(username,  Constants.razOvlastiMap.get(kr.getSifRazOvlasti()), kr.isPromjenaLozinke()));
		
		return true;
		
	}
	
	public static boolean triggerRegisterState(HttpServletRequest req, HttpServletResponse resp) {
		
		req.getSession().removeAttribute("register");
		
		if(isLoggedIn(req, resp)) return false;
		
		String oib = req.getParameter("oib");
		String key = req.getParameter("key");
		
		if(oib==null || key==null) return false;
		
		RegistracijaKlijenta rk = DAOProvider.getDao().getRegistracijaKlijenta(oib);
		if(rk==null || !key.equals(rk.getPrivremeniKljuc())) return false;
		
		req.getSession().setAttribute("register", oib);
		
		return true;
		
	}
	
	public static boolean isInRegisterState(HttpServletRequest req, HttpServletResponse resp) {
		
		return req.getSession().getAttribute("register") != null;
		
	}
	
	public static void removeRegisterState(HttpServletRequest req, HttpServletResponse resp) {
		
		req.getSession().removeAttribute("register");
		
	}
	
	public static boolean doRegister(HttpServletRequest req, HttpServletResponse resp) {
		
		if(isLoggedIn(req, resp) || !isInRegisterState(req, resp)) return false;
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		if(username==null || password==null) return false;
		
		KorisnickiRacun kr = DAOProvider.getDao().getKorisnickiRacun(username);
		if(kr!=null) return false;
		
		KorisnickiRacun newKr = new KorisnickiRacun(username, Util.hash(password), (String) req.getSession().getAttribute("register"), 
				Constants.klijent.getSifRazOvlasti(), false);
		boolean toReturn = DAOProvider.getDao().addKorisnickiRacun(newKr);
		
		if(toReturn) {
			DAOProvider.getDao().removeRegistracijaKlijenta((String) req.getSession().getAttribute("register"));
			removeRegisterState(req, resp);
		}
		
		return toReturn;
		
	}
	
	// Logs out the user (removes attributes from the session)
	public static void doLogout(HttpServletRequest req, HttpServletResponse resp) {
					
		req.getSession().removeAttribute("login");
		req.getSession().removeAttribute("register");
		req.getSession().invalidate();
		
	}
	
	// Returns true iff user is logged in (attribute in the session exists)
	public static boolean isLoggedIn(HttpServletRequest req, HttpServletResponse resp) {
		
		return req.getSession().getAttribute("login")!=null;
		
	}
	
	// Returns username iff user is logged in (null otherwise)
	public static String getUsername(HttpServletRequest req, HttpServletResponse resp) {
		
		if(!isLoggedIn(req, resp)) return null;
		return ((LoginDescriptor)req.getSession().getAttribute("login")).getUsername();
		
	}
	
	public static boolean needsPasswordChange(HttpServletRequest req, HttpServletResponse resp) {
		
		return ((LoginDescriptor)req.getSession().getAttribute("login")).isPasswordChange();
		
	}
	
	// Returns false iff user is not logged in or req parameters are incorrect or new password is equal
	// to the old one or new password is empty
	// Returns true otherwise
	public static boolean changePassword(HttpServletRequest req, HttpServletResponse resp) {
		
		if(!isLoggedIn(req, resp)) return false;
		String username = getUsername(req, resp);
		String oldPassword = req.getParameter("oldPassword");
		String newPassword = req.getParameter("newPassword");
		String confirmNewPassword = req.getParameter("confirmNewPassword");
		KorisnickiRacun kr = DAOProvider.getDao().getKorisnickiRacun(username);
		
		if(oldPassword==null || newPassword==null || confirmNewPassword==null || 
				!confirmNewPassword.equals(newPassword) || newPassword.isEmpty() || 
				!Util.hash(oldPassword).equals(kr.getLozinka())) return false;
		
		String newPasswordHash = Util.hash(newPassword);
		if(kr.getLozinka().equals(newPasswordHash)) return false;
		
		KorisnickiRacun newData = new KorisnickiRacun(kr.getKorisnickoIme(), newPasswordHash, kr.getOib(), kr.getSifRazOvlasti(), false);
		boolean changed = DAOProvider.getDao().updateKorisinckiRacun(username, newData);
		if(changed) ((LoginDescriptor)req.getSession().getAttribute("login")).passwordChange = false;
		return changed;
		
	}
	
	public static class LoginDescriptor {
		private String username;
		private RazinaOvlasti razOvlasti;
		private boolean passwordChange;
		
		public LoginDescriptor(String username, RazinaOvlasti razOvlasti, boolean passwordChange) {
			this.username = username;
			this.razOvlasti = razOvlasti;
			this.passwordChange = passwordChange;
		}
		
		public String getUsername() {
			return username;
		}
		
		public RazinaOvlasti getRazOvlasti() {
			return razOvlasti;
		}
		
		public boolean isPasswordChange() {
			return passwordChange;
		}
	}

}
