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
import hr.fer.opp.bugbusters.dao.model.Kredit;
import hr.fer.opp.bugbusters.dao.model.Profil;
import hr.fer.opp.bugbusters.dao.model.VrstaKredita;

@SuppressWarnings("serial")
@WebServlet(name="krediti", urlPatterns= {"/banka/krediti"})
public class KreditiServlet extends HttpServlet {
	
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
		List<Kredit> krediti = DAOProvider.getDao().getKreditForOib(profil.getOib());
		Map<Integer, VrstaKredita> vrsteKredita = new HashMap<>();
		
		// Map : kartica -> naziv
		Map<Kredit, VrstaKredita> kreditiJsp = new HashMap<>();
		for(var kredit : krediti) {
			VrstaKredita vrsta = vrsteKredita.getOrDefault(kredit.getSifVrsteKredita(), DAOProvider.getDao().getVrstaKredita(kredit.getSifVrsteKredita()));
			vrsteKredita.put(vrsta.getSifVrsteKredita(), vrsta);
			
			kreditiJsp.put(kredit, vrsta);
		}
		
		req.setAttribute("krediti", kreditiJsp);
		
		req.getRequestDispatcher("/WEB-INF/pages/credit.jsp").forward(req, resp);
		
	}
	
}
