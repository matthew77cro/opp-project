package hr.fer.opp.bugbusters.servleti.banker;

import java.io.IOException;
import java.math.BigDecimal;
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
import hr.fer.opp.bugbusters.dao.model.Kredit;
import hr.fer.opp.bugbusters.dao.model.Profil;
import hr.fer.opp.bugbusters.dao.model.VrstaKredita;
import hr.fer.opp.bugbusters.dao.model.ZahtjevKredit;

@SuppressWarnings("serial")
@WebServlet(name="bankar-krediti", urlPatterns= {"/banka/bankar-krediti"})
@MultipartConfig
public class BankarKreditiServlet extends HttpServlet {
	
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
		
		req.setAttribute("vrste", DAOProvider.getDao().getAllVrstaKredita());
		
		req.getRequestDispatcher("/WEB-INF/pages/banker/bankerCredits.jsp").forward(req, resp);
				
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

				List<Kredit> krediti = DAOProvider.getDao().getKreditByOib(oib);
				
				Map<Integer, VrstaKredita> vrsteKredita = new HashMap<>();
				
				Map<Kredit, VrstaKredita> kreditJsp = new HashMap<>();
				for(var kredit : krediti) {
					VrstaKredita vrsta = vrsteKredita.getOrDefault(kredit.getSifVrsteKredita(), DAOProvider.getDao().getVrstaKredita(kredit.getSifVrsteKredita()));
					vrsteKredita.put(vrsta.getSifVrsteKredita(), vrsta);
					
					kreditJsp.put(kredit, vrsta);
				}
				
				req.setAttribute("oib", oib);
				req.setAttribute("krediti", kreditJsp);
				
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
				
				int sifVrsteKredita = Integer.parseInt(req.getParameter("vrstaKredita"));
				String iznos = req.getParameter("iznos");
				String rokOtplate = req.getParameter("rokOtplate");

				BigDecimal iznosBD = null;
				int rokOtplateInt = 0;
				try {
					iznosBD = new BigDecimal(iznos);
					rokOtplateInt = Integer.parseInt(rokOtplate);
				} catch(NumberFormatException ex) {
					req.setAttribute("errorMsg", "Request error - invalid paramteres");
					break;
				}

				ZahtjevKredit zk = new ZahtjevKredit(null, oib, iznosBD, sifVrsteKredita, rokOtplateInt, false);
				
				boolean update = DAOProvider.getDao().addZahtjevKredit(zk);
				if(!update) {
					req.setAttribute("errorMsg", "Request error");
					break;
				}
				
				break;
				
			default :
				req.setAttribute("errorMsg", "Request error - invalid action");
		}
				
		doGet(req, resp);
		
	}

}
