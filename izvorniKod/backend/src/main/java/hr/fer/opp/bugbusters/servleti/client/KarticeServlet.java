package hr.fer.opp.bugbusters.servleti.client;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.control.LoginHandler;
import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.Constants;
import hr.fer.opp.bugbusters.dao.model.Kartica;
import hr.fer.opp.bugbusters.dao.model.Racun;
import hr.fer.opp.bugbusters.dao.model.Transakcija;
import hr.fer.opp.bugbusters.dao.model.VrstaKartice;
import hr.fer.opp.bugbusters.dao.model.ZahtjevKartica;

@SuppressWarnings("serial")
@WebServlet(name="kartice", urlPatterns= {"/banka/kartice"})
public class KarticeServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp) || !LoginHandler.equalsRazinaOvlasti(req, resp, Constants.klijent)) {
			resp.sendRedirect("login");
			return;
		}
		
		if(LoginHandler.needsPasswordChange(req, resp)) {
			resp.sendRedirect("passwordchange");
			return;
		}
		
		String oib = DAOProvider.getDao().getKorisnickiRacun(LoginHandler.getUsername(req, resp)).getOib();
		List<Racun> racuni = DAOProvider.getDao().getRacunByOib(oib);
		
		List<Kartica> kartice = DAOProvider.getDao().getKarticaByOib(oib);
		racuni.forEach((r) -> kartice.addAll(DAOProvider.getDao().getKarticaByBrRacun(r.getBrRacun())));
		
		List<VrstaKartice> vrsteKartice = DAOProvider.getDao().getAllVrstaKartice();
		Map<Integer, VrstaKartice> vrsteKarticeMap = vrsteKartice.stream().collect(Collectors.toMap(VrstaKartice::getSifVrsteKartice, v -> v));
		
		req.setAttribute("racuni", racuni);
		req.setAttribute("kartice", kartice);
		req.setAttribute("vrstaKartice", vrsteKarticeMap);
		
		req.getRequestDispatcher("/WEB-INF/pages/client/cards.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if(!LoginHandler.isLoggedIn(req, resp) || !LoginHandler.equalsRazinaOvlasti(req, resp, Constants.klijent)) {
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
			case "new" :
				String cardType = req.getParameter("cardType");		
				Integer sifVrstaKartice = null;
				try {
					sifVrstaKartice = Integer.parseInt(cardType);
				} catch (NumberFormatException ex) {
					req.setAttribute("errorMsg", "Request error - cardType");
					break;
				}
				
				ZahtjevKartica zk = new ZahtjevKartica(0, DAOProvider.getDao().getKorisnickiRacun(LoginHandler.getUsername(req, resp)).getOib(), sifVrstaKartice, false);
				boolean success = DAOProvider.getDao().addZahtjevKartica(zk);
				if(!success) {
					req.setAttribute("errorMsg", "Zahtjev za karticom nije uspio! Molimo kontaktirajte vašeg bankara.");
					break;
				}
				
				break;
				
			case "pay" :
				String oib = DAOProvider.getDao().getKorisnickiRacun(LoginHandler.getUsername(req, resp)).getOib();
				
				String brojKartice = req.getParameter("brojKartice");
				String brojRacuna = req.getParameter("brojRacuna");
				String iznos = req.getParameter("iznos");
				
				BigDecimal iznosBD = null;
				try {
					iznosBD = new BigDecimal(iznos);
				} catch (NumberFormatException ex) {
					req.setAttribute("errorMsg", "Request error - cardType");
					break;
				}
				
				Racun racun = DAOProvider.getDao().getRacun(brojRacuna);
				if(racun == null || !racun.getOib().equals(oib)) {
					req.setAttribute("errorMsg", "Request error - oib does not match our records");
					break;
				}
				
				Kartica kartica = DAOProvider.getDao().getKartica(brojKartice);
				if(kartica == null) {
					req.setAttribute("errorMsg", "Request error - oib does not match our records");
					break;
				}
				
				if(iznosBD.compareTo(BigDecimal.ZERO)!=1 || racun.getStanje().subtract(iznosBD).compareTo(racun.getPrekoracenje().negate()) == -1 || kartica.getStanje().subtract(iznosBD).compareTo(BigDecimal.ZERO) == -1) {
					req.setAttribute("errorMsg", "Request error - invalid paramteres");
					break;
				}
				
				racun = new Racun(racun.getBrRacun(), racun.getOib(), racun.getDatOtvaranja(), racun.getStanje().subtract(iznosBD), racun.getSifVrsteRacuna(), racun.getPrekoracenje(), racun.getKamStopa(), racun.getDatZatvaranja());
				kartica = new Kartica(kartica.getBrKartica(), kartica.getBrRacun(), kartica.getOib(), kartica.getSifVrstaKartice(), kartica.getStanje().subtract(iznosBD), kartica.getValjanost(), kartica.getLimitKartice(), kartica.getKamStopa(), kartica.getDatRate());
				Transakcija transakcija = new Transakcija(0, racun.getBrRacun(), "Otplata kartice: " + kartica.getBrKartica(), iznosBD, new Date(System.currentTimeMillis()));
				
				DAOProvider.getDao().updateRacun(racun.getBrRacun(), racun);
				DAOProvider.getDao().updateKartica(kartica.getBrKartica(), kartica);
				DAOProvider.getDao().addTransakcija(transakcija);
				
				break;
				
			default :
				req.setAttribute("errorMsg", "Request error - invalid action");
		}
		
		doGet(req, resp);
		
	}
	
}
