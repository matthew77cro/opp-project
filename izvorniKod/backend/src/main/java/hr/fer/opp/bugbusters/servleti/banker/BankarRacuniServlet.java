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
import hr.fer.opp.bugbusters.dao.model.Profil;
import hr.fer.opp.bugbusters.dao.model.Racun;
import hr.fer.opp.bugbusters.dao.model.VrstaRacuna;

@SuppressWarnings("serial")
@WebServlet(name="bankar-racuni", urlPatterns= {"/banka/bankar-racuni"})
@MultipartConfig
public class BankarRacuniServlet extends HttpServlet {
	
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
		
		req.setAttribute("vrste", DAOProvider.getDao().getAllVrstaRacuna());
		
		req.getRequestDispatcher("/WEB-INF/pages/banker/bankerAccounts.jsp").forward(req, resp);
				
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
				Map<Integer, VrstaRacuna> vrsteRacuna = new HashMap<>();
				
				Map<Racun, VrstaRacuna> racunJsp = new HashMap<>();
				for(var racun : racuni) {
					VrstaRacuna vrsta = vrsteRacuna.getOrDefault(racun.getSifVrsteRacuna(), DAOProvider.getDao().getVrstaRacuna(racun.getSifVrsteRacuna()));
					vrsteRacuna.put(vrsta.getSifVrsteRacuna(), vrsta);
					
					racunJsp.put(racun, vrsta);
				}
				
				req.setAttribute("oib", oib);
				req.setAttribute("racuni", racunJsp);
				
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
				
				int sifVrsteRacuna = Integer.parseInt(req.getParameter("vrstaRacuna"));
				String brojRacuna = req.getParameter("brojRacuna");
				String prekoracenje = req.getParameter("prekoracenje");
				String kamStopa = req.getParameter("kamStopa");

				Racun racun = DAOProvider.getDao().getRacun(brojRacuna);
				if(racun!=null) {
					req.setAttribute("errorMsg", "Request error - account number already exists");
					break;
				}
				
				BigDecimal prekoracenjeBD = null, kamStopaBD = null;
				try {
					prekoracenjeBD = new BigDecimal(prekoracenje);
					kamStopaBD = new BigDecimal(kamStopa);
				} catch (NumberFormatException ex) {
					req.setAttribute("errorMsg", "Request error - invalid values");
					break;
				}
				
				boolean update = DAOProvider.getDao().addRacun(new Racun(brojRacuna, oib, new Date(System.currentTimeMillis()), BigDecimal.ZERO, sifVrsteRacuna, prekoracenjeBD, kamStopaBD, null));
				if(!update) {
					req.setAttribute("errorMsg", "Request error");
					break;
				}
				
				break;
				
			case "delete" :
				oib = req.getParameter("oib");				
				brojRacuna = req.getParameter("brojRacuna");
				racun = DAOProvider.getDao().getRacun(brojRacuna);
				if(racun==null || !racun.getOib().equals(oib)) {
					req.setAttribute("errorMsg", "Request error - invalid parameters");
					break;
				}
				if(racun.getStanje().compareTo(BigDecimal.ZERO) != 0) {
					req.setAttribute("errorMsg", "Request error - account has balance");
					break;
				}
				
				DAOProvider.getDao().removeRacun(brojRacuna);
				
				break;
				
			default :
				req.setAttribute("errorMsg", "Request error - invalid action");
		}
				
		doGet(req, resp);
		
	}

}
