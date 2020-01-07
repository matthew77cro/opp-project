package hr.fer.opp.bugbusters.servleti.banker;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.control.LoginHandler;
import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.Constants;
import hr.fer.opp.bugbusters.dao.model.Kartica;
import hr.fer.opp.bugbusters.dao.model.Profil;
import hr.fer.opp.bugbusters.dao.model.Racun;
import hr.fer.opp.bugbusters.dao.model.VrstaKartice;

@SuppressWarnings("serial")
@WebServlet(name="bankar-kartice", urlPatterns= {"/banka/bankar-kartice"})
@MultipartConfig
public class BankarKarticeServlet extends HttpServlet {
	
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
		
		req.setAttribute("vrste", DAOProvider.getDao().getAllVrstaKartice());
		
		req.getRequestDispatcher("/WEB-INF/pages/banker/bankerCards.jsp").forward(req, resp);
				
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

				List<Racun> racuni = DAOProvider.getDao().getRacunByOib(oib);
				List<Kartica> kartice = DAOProvider.getDao().getKarticaByOib(oib);
				racuni.forEach(r -> kartice.addAll(DAOProvider.getDao().getKarticaByBrRacun(r.getBrRacun())));
				
				Map<Integer, VrstaKartice> vrsteKartice = new HashMap<>();
				
				Map<Kartica, VrstaKartice> karticaJsp = new HashMap<>();
				for(var kartica : kartice) {
					VrstaKartice vrsta = null;
					if(kartica.getSifVrstaKartice()!=null) {
						vrsta = vrsteKartice.getOrDefault(kartica.getSifVrstaKartice(), DAOProvider.getDao().getVrstaKartice(kartica.getSifVrstaKartice()));
						vrsteKartice.put(vrsta.getSifVrsteKartice(), vrsta);
					}
					
					karticaJsp.put(kartica, vrsta);
				}
				
				req.setAttribute("oib", oib);
				req.setAttribute("kartice", karticaJsp);
				
				break;
				
			case "add" :
				oib = req.getParameter("oib").isEmpty() ? null : req.getParameter("oib");				
				String vrstaKartice = req.getParameter("vrstaKartice");
				String broj = req.getParameter("broj");
				String brojRacuna = req.getParameter("brojRacuna").isEmpty() ? null : req.getParameter("brojRacuna");
				String valjanost = req.getParameter("valjanost");
				String limit = req.getParameter("limit").isEmpty() ? null : req.getParameter("limit");
				String kamStopa = req.getParameter("kamStopa").isEmpty() ? null : req.getParameter("kamStopa");
				Integer datumRate = req.getParameter("datRate").isEmpty() ? null : Integer.parseInt(req.getParameter("datRate"));
				BigDecimal stanje = null;

				if(valjanost.isEmpty()) {
					req.setAttribute("errorMsg", "Request error - exp date empty");
					break;
				}
				
				Kartica kartica = DAOProvider.getDao().getKartica(broj);
				if(kartica!=null) {
					req.setAttribute("errorMsg", "Request error - card number already exists");
					break;
				}
				
				BigDecimal limitBD = null, kamStopaBD = null;
				try {
					limitBD = new BigDecimal(limit);
					kamStopaBD = new BigDecimal(kamStopa);
				} catch (Exception ex) {
					// Ignore
				}
				
				Integer sifVrsteKartice = null;
				if(!vrstaKartice.equals("debitna")) {
					try {
						sifVrsteKartice = Integer.parseInt(vrstaKartice);
					} catch (NumberFormatException ex) {
						req.setAttribute("errorMsg", "Request error - invalid card type");
						break;
					}
					stanje = BigDecimal.ZERO;
				}
				
				
				boolean update = DAOProvider.getDao().addKartica(new Kartica(broj, brojRacuna, oib, sifVrsteKartice, stanje, Date.valueOf(valjanost), limitBD, kamStopaBD, datumRate));
				if(!update) {
					req.setAttribute("errorMsg", "Request error");
					break;
				}
				
				break;
				
			case "delete" :
				oib = req.getParameter("oib");				
				broj = req.getParameter("broj");
				
				kartica = DAOProvider.getDao().getKartica(broj);
				String oibVlasnikaKartice = kartica.getOib()!=null ? kartica.getOib() : DAOProvider.getDao().getRacun(kartica.getBrRacun()).getOib();
				if(kartica==null || !oibVlasnikaKartice.equals(oib)) {
					req.setAttribute("errorMsg", "Request error - invalid parameters");
					break;
				}
				
				if(kartica.getSifVrstaKartice()!=null && kartica.getStanje().compareTo(BigDecimal.ZERO) != 0) {
					req.setAttribute("errorMsg", "Request error - card has unpaid balance");
					break;
				}
				
				DAOProvider.getDao().removeKartica(broj);
				
				break;
				
			default :
				req.setAttribute("errorMsg", "Request error - invalid action");
		}
				
		doGet(req, resp);
		
	}

}
