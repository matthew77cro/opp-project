package hr.fer.opp.bugbusters.servleti.client;

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
import hr.fer.opp.bugbusters.dao.model.Constants;
import hr.fer.opp.bugbusters.dao.model.Racun;
import hr.fer.opp.bugbusters.dao.model.VrstaRacuna;

@SuppressWarnings("serial")
@WebServlet(name="racuni", urlPatterns= {"/banka/racuni"})
public class RacuniServlet extends HttpServlet {
	
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
		Map<Integer, VrstaRacuna> vrsteRacuna = new HashMap<>();
		
		Map<Racun, VrstaRacuna> racunJsp = new HashMap<>();
		for(var racun : racuni) {
			VrstaRacuna vrsta = vrsteRacuna.getOrDefault(racun.getSifVrsteRacuna(), DAOProvider.getDao().getVrstaRacuna(racun.getSifVrsteRacuna()));
			vrsteRacuna.put(vrsta.getSifVrsteRacuna(), vrsta);
			
			racunJsp.put(racun, vrsta);
		}
		
		req.setAttribute("racuni", racunJsp);
		
		req.getRequestDispatcher("/WEB-INF/pages/client/account.jsp").forward(req, resp);
		
	}

}
