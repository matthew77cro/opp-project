package hr.fer.opp.bugbusters.servleti;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.control.LoginHandler;
import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.Kartica;
import hr.fer.opp.bugbusters.dao.model.Racun;
import hr.fer.opp.bugbusters.dao.model.VrstaKartice;

@SuppressWarnings("serial")
@WebServlet(name="kartice", urlPatterns= {"/banka/kartice"})
public class KarticeServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp)) {
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
		Map<Integer, VrstaKartice> vrsteKartice = new HashMap<>();
		
		// Map : kartica -> naziv
		Map<Kartica, VrstaKartice> karticeJsp = new HashMap<>();
		for(var kartica : kartice) {
			VrstaKartice vrsta = vrsteKartice.getOrDefault(kartica.getSifVrstaKartice(), DAOProvider.getDao().getVrstaKartice(kartica.getSifVrstaKartice()));
			vrsteKartice.put(vrsta.getSifVrsteKartice(), vrsta);
			
			karticeJsp.put(kartica, vrsta);
		}
		
		req.setAttribute("kartice", karticeJsp);
		
		req.getRequestDispatcher("/WEB-INF/pages/client/cards.jsp").forward(req, resp);
		
	}
	
}
