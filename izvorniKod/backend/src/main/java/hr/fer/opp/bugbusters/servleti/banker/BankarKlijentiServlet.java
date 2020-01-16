package hr.fer.opp.bugbusters.servleti.banker;

import java.io.IOException;
import java.sql.Date;

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
import hr.fer.opp.bugbusters.dao.model.Mjesto;
import hr.fer.opp.bugbusters.dao.model.Profil;
import hr.fer.opp.bugbusters.dao.model.RegistracijaKlijenta;
import hr.fer.opp.bugbusters.dao.model.Zupanija;

@SuppressWarnings("serial")
@WebServlet(name="bankar-klijenti", urlPatterns= {"/banka/bankar-klijenti"})
@MultipartConfig
public class BankarKlijentiServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp) || !LoginHandler.equalsRazinaOvlasti(req, resp, Constants.bankar)) {
			resp.sendRedirect("login");
			return;
		}
		
		if(LoginHandler.needsPasswordChange(req, resp)) {
			resp.sendRedirect("passwordchange");
			return;
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/banker/bankerClients.jsp").forward(req, resp);
				
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp) || !LoginHandler.equalsRazinaOvlasti(req, resp, Constants.bankar)) {
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
				
				req.setAttribute("firstName", profil.getIme());
				req.setAttribute("lastName", profil.getPrezime());
				req.setAttribute("address", address);
				req.setAttribute("addressUl", profil.getAdresa());
				req.setAttribute("addressPbr", mjesto.getPbr());
				req.setAttribute("oib", profil.getOib());
				req.setAttribute("birthday", profil.getDatRod());
				req.setAttribute("email", profil.getEmail());
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
				boolean update = DAOProvider.getDao().addProfil(profil);
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
				
			case "registerKey" :
				oib = req.getParameter("oib");
				if(oib==null || oib.isEmpty() || DAOProvider.getDao().getProfil(oib)==null) {
					req.setAttribute("errorMsg", "Invalid oib");
					break;
				}
				
				RegistracijaKlijenta rk = DAOProvider.getDao().getRegistracijaKlijenta(oib);
				if(rk==null) {
					rk = new RegistracijaKlijenta(oib, Util.getRandomString(32));
					DAOProvider.getDao().addRegistracijaKlijenta(rk);
				}
				
				req.setAttribute("errorMsg", "Key: " + rk.getPrivremeniKljuc());
				
				break;
				
			default :
				req.setAttribute("errorMsg", "Request error - invalid action");
		}
				
		doGet(req, resp);
		
	}

}
