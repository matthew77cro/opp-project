package hr.fer.opp.bugbusters.servleti.loanOfficial;

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
import hr.fer.opp.bugbusters.dao.model.Kredit;
import hr.fer.opp.bugbusters.dao.model.Mjesto;
import hr.fer.opp.bugbusters.dao.model.Profil;
import hr.fer.opp.bugbusters.dao.model.Racun;
import hr.fer.opp.bugbusters.dao.model.VrstaKartice;
import hr.fer.opp.bugbusters.dao.model.VrstaKredita;
import hr.fer.opp.bugbusters.dao.model.VrstaRacuna;
import hr.fer.opp.bugbusters.dao.model.Zupanija;

@SuppressWarnings("serial")
@WebServlet(name="sluzbenik-klijenti", urlPatterns= {"/banka/sluzbenik-klijenti"})
public class SluzbenikKlijentiServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp) || !LoginHandler.equalsRazinaOvlasti(req, resp, Constants.sluzbenikZaKredite)) {
			resp.sendRedirect("login");
			return;
		}
		
		if(LoginHandler.needsPasswordChange(req, resp)) {
			resp.sendRedirect("passwordchange");
			return;
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/loanOfficial/officialClients.jsp").forward(req, resp);
				
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp) || !LoginHandler.equalsRazinaOvlasti(req, resp, Constants.sluzbenikZaKredite)) {
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
				
				List<Racun> racuni = DAOProvider.getDao().getRacunByOib(oib);
				List<Kartica> kartice = DAOProvider.getDao().getKarticaByOib(oib);
				racuni.forEach(r -> kartice.addAll(DAOProvider.getDao().getKarticaByBrRacun(r.getBrRacun())));
				List<Kredit> krediti = DAOProvider.getDao().getKreditByOib(oib);
				
				Map<Integer, VrstaRacuna> vrsteRacuna = DAOProvider.getDao().getAllVrstaRacuna().stream().collect(Collectors.toMap(r -> r.getSifVrsteRacuna(), r -> r));
				Map<Integer, VrstaKartice> vrsteKartica = DAOProvider.getDao().getAllVrstaKartice().stream().collect(Collectors.toMap(k -> k.getSifVrsteKartice(), k -> k));
				Map<Integer, VrstaKredita> vrsteKredita = DAOProvider.getDao().getAllVrstaKredita().stream().collect(Collectors.toMap(k -> k.getSifVrsteKredita(), k -> k));
								
				req.setAttribute("firstName", profil.getIme());
				req.setAttribute("lastName", profil.getPrezime());
				req.setAttribute("address", address);
				req.setAttribute("addressUl", profil.getAdresa());
				req.setAttribute("addressPbr", mjesto.getPbr());
				req.setAttribute("oib", profil.getOib());
				req.setAttribute("birthday", profil.getDatRod());
				req.setAttribute("email", profil.getEmail());
				
				req.setAttribute("racuni", racuni);
				req.setAttribute("kartice", kartice);
				req.setAttribute("krediti", krediti);
				req.setAttribute("vrsteRacuna", vrsteRacuna);
				req.setAttribute("vrsteKartica", vrsteKartica);
				req.setAttribute("vrsteKredita", vrsteKredita);
				break;
				
			default :
				req.setAttribute("errorMsg", "Request error - invalid action");
		}
				
		doGet(req, resp);
		
	}

}
