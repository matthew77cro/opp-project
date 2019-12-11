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
import hr.fer.opp.bugbusters.dao.model.Profil;
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
		
		Profil profil = DAOProvider.getDao().getProfilByKorisnickoIme(LoginHandler.getUsername(req, resp));
		List<Racun> racuni = DAOProvider.getDao().getRacunForOib(profil.getOib());
		List<Kartica> kartice = DAOProvider.getDao().getKarticaForOib(profil.getOib());
		racuni.forEach((r) -> kartice.addAll(DAOProvider.getDao().getKarticaForBrRacun(r.getBrRacun())));
		Map<Integer, VrstaKartice> vrsteKartice = new HashMap<>();
		
		// Map : kartica -> naziv
		Map<Kartica, VrstaKartice> karticeJsp = new HashMap<>();
		for(var kartica : kartice) {
			VrstaKartice vrsta = vrsteKartice.getOrDefault(kartica.getSifVrstaKartice(), DAOProvider.getDao().getVrstaKartice(kartica.getSifVrstaKartice()));
			vrsteKartice.put(vrsta.getSifVrstaKartice(), vrsta);
			
			karticeJsp.put(kartica, vrsta);
		}
		
		req.setAttribute("kartice", karticeJsp);
		
		req.getRequestDispatcher("/WEB-INF/pages/cards.jsp").forward(req, resp);
		
	}
	
}
