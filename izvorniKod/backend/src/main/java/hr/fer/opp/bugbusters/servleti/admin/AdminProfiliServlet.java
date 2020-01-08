package hr.fer.opp.bugbusters.servleti.admin;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.control.LoginHandler;
import hr.fer.opp.bugbusters.control.Util;
import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.Constants;
import hr.fer.opp.bugbusters.dao.model.KorisnickiRacun;
import hr.fer.opp.bugbusters.dao.model.Mjesto;
import hr.fer.opp.bugbusters.dao.model.Profil;
import hr.fer.opp.bugbusters.dao.model.Zupanija;

@SuppressWarnings("serial")
@WebServlet(name="admin-profili", urlPatterns= {"/banka/admin-profili"})
@MultipartConfig
public class AdminProfiliServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp) || !LoginHandler.equalsRazinaOvlasti(req, resp, Constants.administrator)) {
			resp.sendRedirect("login");
			return;
		}
		
		if(LoginHandler.needsPasswordChange(req, resp)) {
			resp.sendRedirect("passwordchange");
			return;
		}
		
		req.setAttribute("razineOvlasti", Constants.razOvlastiMap);
		
		req.getRequestDispatcher("/WEB-INF/pages/admin/userProfile.jsp").forward(req, resp);
				
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp) || !LoginHandler.equalsRazinaOvlasti(req, resp, Constants.administrator)) {
			resp.sendRedirect("login");
			return;
		}
		
		if(LoginHandler.needsPasswordChange(req, resp)) {
			resp.sendRedirect("passwordchange");
			return;
		}
		
		String action = req.getParameter("action");
		if(action==null) {
			req.setAttribute("errorMsg", "Request error - no action");
			doGet(req, resp);
			return;
		}
		
		switch(action) {
			case "search" :
				String oib = req.getParameter("oib");
				if(oib==null || oib.length()==0) {
					req.setAttribute("errorMsg", "Request error - OIB not sent");
					break;
				}
				Profil profil = DAOProvider.getDao().getProfil(oib);
				if(profil==null) {
					req.setAttribute("errorMsg", "Request error - incorrect OIB");
					break;
				}
				Mjesto mjesto = DAOProvider.getDao().getMjesto(profil.getPbr());
				Zupanija zupanija = DAOProvider.getDao().getZupanija(mjesto.getSifraZupanija());
				
				String address = profil.getAdresa() + "," + mjesto.getPbr() + " " + mjesto.getNazMjesto() + "\n" + zupanija.getNazZupanija();
				
				List<KorisnickiRacun> korisnickiRacuni = DAOProvider.getDao().getKorisnickiRacunByOib(oib);
				
				req.setAttribute("firstName", profil.getIme());
				req.setAttribute("lastName", profil.getPrezime());
				req.setAttribute("address", address);
				req.setAttribute("addressUl", profil.getAdresa());
				req.setAttribute("addressPbr", mjesto.getPbr());
				req.setAttribute("oib", profil.getOib());
				req.setAttribute("birthday", profil.getDatRod());
				req.setAttribute("email", profil.getEmail());
				req.setAttribute("korisnickiRacuni", korisnickiRacuni);
				
				break;
				
			case "remove" :
				
				String korIme = req.getParameter("korIme");
				boolean update = DAOProvider.getDao().removeKorisnickiRacun(korIme);
				if(!update) {
					req.setAttribute("errorMsg", "Error removeing user account for username " + korIme);
					break;
				}
				
				break;
				
			case "add" :
				oib = req.getParameter("oib");
				if(oib==null || oib.length()==0) {
					req.setAttribute("errorMsg", "Request error - OIB not sent");
					break;
				}
				profil = DAOProvider.getDao().getProfil(oib);
				if(profil==null) {
					req.setAttribute("errorMsg", "Request error - incorrect OIB");
					break;
				}
				
				String username = req.getParameter("username");
				String password = req.getParameter("password");
				int razOvlasti = Integer.parseInt(req.getParameter("razOvlasti"));
				
				if(DAOProvider.getDao().getKorisnickiRacun(username)!=null) {
					req.setAttribute("errorMsg", "Request error - username already exists");
					break;
				}
				
				if(username.isEmpty() || password.isEmpty()) {
					req.setAttribute("errorMsg", "Request error - invalid parameters");
					break;
				}
				
				String hashedPassword = Util.hash(password);
				KorisnickiRacun kr = new KorisnickiRacun(username, hashedPassword, oib, razOvlasti, true);
				update = DAOProvider.getDao().addKorisnickiRacun(kr);
				if(!update) {
					req.setAttribute("errorMsg", "Request error");
					break;
				}
				
				break;
				
			case "change" :
				oib = req.getParameter("oib");
				if(oib==null || oib.length()==0) {
					req.setAttribute("errorMsg", "Request error - OIB not sent");
					break;
				}
				profil = DAOProvider.getDao().getProfil(oib);
				if(profil==null) {
					req.setAttribute("errorMsg", "Request error - incorrect OIB");
					break;
				}
				
				username = req.getParameter("username");
				razOvlasti = Integer.parseInt(req.getParameter("razOvlasti"));
				
				kr = DAOProvider.getDao().getKorisnickiRacun(username);				
				if(kr==null) {
					req.setAttribute("errorMsg", "Request error - username does not exist");
					break;
				}
				
				if(!kr.getOib().equals(oib)) {
					req.setAttribute("errorMsg", "Request error - oib does not match our records");
					break;
				}
				
				kr = new KorisnickiRacun(kr.getKorisnickoIme(), kr.getLozinka(), kr.getOib(), razOvlasti, true);
				update = DAOProvider.getDao().updateKorisinckiRacun(username, kr);
				if(!update) {
					req.setAttribute("errorMsg", "Request error");
					break;
				}				
				
				break;
				
			case "create" :
				String ime = req.getParameter("ime");
				String prezime = req.getParameter("prezime");
				String adresa = req.getParameter("adresa");
				String pbrString = req.getParameter("pbr");
				oib = req.getParameter("oib");
				String datRod = req.getParameter("datRod");
				String email = req.getParameter("email");
				
				int pbr = 0;
				try {
					pbr = Integer.parseInt(pbrString);
				} catch (NumberFormatException ex) {
					req.setAttribute("errorMsg", "Request error - wrong format of field PBR");
					break;
				}
				
				profil = new Profil(ime, prezime, oib, adresa, pbr, Date.valueOf(datRod), email, "null");
				update = DAOProvider.getDao().addProfil(profil);
				if(!update) {
					req.setAttribute("errorMsg", "Error adding new profile for oib " + oib);
					break;
				}
				
				LoginHandler.changeProfilePicture(req, resp, oib, "pic");
				
				break;
				
			case "update" :
				String oldOib = req.getParameter("oldOib");
				if(oldOib==null || oldOib.length()==0) {
					req.setAttribute("errorMsg", "Request error - OIB not sent");
					break;
				}
				profil = DAOProvider.getDao().getProfil(oldOib);
				
				ime = req.getParameter("ime");
				prezime = req.getParameter("prezime");
				adresa = req.getParameter("adresa");
				pbrString = req.getParameter("pbr");
				oib = req.getParameter("oib");
				datRod = req.getParameter("datRod");
				email = req.getParameter("email");
				
				pbr = 0;
				try {
					pbr = Integer.parseInt(pbrString);
				} catch (NumberFormatException ex) {
					req.setAttribute("errorMsg", "Request error - wrong format of field PBR");
					break;
				}
				
				Profil newData = new Profil(ime, prezime, oib, adresa, pbr, Date.valueOf(datRod), email, profil.getSlika());
				update = DAOProvider.getDao().updateProfil(oldOib, newData);
				if(!update) {
					req.setAttribute("errorMsg", "Error updating the profile for oib " + oldOib);
					break;
				}
				
				LoginHandler.changeProfilePicture(req, resp, oib, "pic");
				
				break;
				
			case "delete" :
				oib = req.getParameter("oib");
				update = DAOProvider.getDao().removeProfil(oib);
				if(!update) {
					req.setAttribute("errorMsg", "Error deleting the profile for oib " + oib);
					break;
				}
				
				break;
				
			default :
				req.setAttribute("errorMsg", "Request error - invalid action");
		}
				
		doGet(req, resp);
		
	}

}
