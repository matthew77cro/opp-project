package hr.fer.opp.bugbusters.servleti.client;

import java.io.IOException;
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
		
		String cardType = req.getParameter("cardType");		
		Integer sifVrstaKartice = null;
		try {
			sifVrstaKartice = Integer.parseInt(cardType);
		} catch (NumberFormatException ex) {
			resp.setStatus(HttpServletResponse.SC_CONFLICT);
			return;
		}
		
		ZahtjevKartica zk = new ZahtjevKartica(0, DAOProvider.getDao().getKorisnickiRacun(LoginHandler.getUsername(req, resp)).getOib(), sifVrstaKartice, false);
		boolean success = DAOProvider.getDao().addZahtjevKartica(zk);
		if(!success) {
			req.setAttribute("errorMsg", "Zahtjev za karticom nije uspio! Molimo kontaktirajte vašeg bankara.");
		}
		doGet(req, resp);
		
	}
	
}
