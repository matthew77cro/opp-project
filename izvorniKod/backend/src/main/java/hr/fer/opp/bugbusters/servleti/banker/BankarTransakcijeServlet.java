package hr.fer.opp.bugbusters.servleti.banker;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

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
import hr.fer.opp.bugbusters.dao.model.Transakcija;

@SuppressWarnings("serial")
@WebServlet(name="bankar-transakcije", urlPatterns= {"/banka/bankar-transakcije"})
@MultipartConfig
public class BankarTransakcijeServlet extends HttpServlet {
	
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
		
		req.getRequestDispatcher("/WEB-INF/pages/banker/bankerTransaction.jsp").forward(req, resp);
				
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
				
				String fromDateParam = req.getParameter("fromDate");
				String toDateParam = req.getParameter("toDate");
				
				final Date fromDate = fromDateParam!=null && !fromDateParam.isEmpty() ? Date.valueOf(fromDateParam) : null;
				final Date toDate = toDateParam!=null && !toDateParam.isEmpty() ? Date.valueOf(toDateParam) : null;
				
				List<Racun> racuni = DAOProvider.getDao().getRacunByOib(oib);
				List<Transakcija> transakcije = DAOProvider.getDao().getTransakcijeByOib(oib);
				
				if(fromDate!=null) {
					transakcije.removeIf((t) -> t.getDatTransakcije().compareTo(fromDate)<0);
				}
				
				if(toDate!=null) {
					transakcije.removeIf((t) -> t.getDatTransakcije().compareTo(toDate)>0);
				}
				
				req.setAttribute("racuni", racuni);
				req.setAttribute("transakcije", transakcije);
				
				break;
				
			case "add" :
				String racTerecenja = req.getParameter("racTerecenja");
				String racOdobrenja = req.getParameter("racOdobrenja");
				String iznos = req.getParameter("iznos");

				if(racTerecenja.isEmpty() || racOdobrenja.isEmpty() || iznos.isEmpty()) {
					req.setAttribute("errorMsg", "Request error - invalid parameters (some empty)");
					break;
				}
				
				BigDecimal iznosBD = null;
				try {
					iznosBD = new BigDecimal(iznos);
				} catch (NumberFormatException ex) {
					req.setAttribute("errorMsg", "Request error - invalid amount parameter");
					break;
				}
				
				Racun r1 = DAOProvider.getDao().getRacun(racTerecenja);
				Racun r2 = DAOProvider.getDao().getRacun(racOdobrenja);
				
				if(iznosBD.compareTo(BigDecimal.ZERO)!=1 || r1==null || r1.getStanje().subtract(iznosBD).compareTo(r1.getPrekoracenje().negate()) == -1) {
					req.setAttribute("errorMsg", "Request error - invalid parameters");
					break;
				}
				
				r1 = new Racun(r1.getBrRacun(), r1.getOib(), r1.getDatOtvaranja(), r1.getStanje().subtract(iznosBD), r1.getSifVrsteRacuna(), r1.getPrekoracenje(), r1.getKamStopa(), r1.getDatZatvaranja());
				DAOProvider.getDao().updateRacun(r1.getBrRacun(), r1);
				if(r2!=null) {
					r2 = new Racun(r2.getBrRacun(), r2.getOib(), r2.getDatOtvaranja(), r2.getStanje().add(iznosBD), r2.getSifVrsteRacuna(), r2.getPrekoracenje(), r2.getKamStopa(), r2.getDatZatvaranja());
					DAOProvider.getDao().updateRacun(r2.getBrRacun(), r2);
				}
				
				DAOProvider.getDao().addTransakcija(new Transakcija(0, racTerecenja, racOdobrenja, iznosBD, new Date(System.currentTimeMillis())));
								
				break;
				
			default :
				req.setAttribute("errorMsg", "Request error - invalid action");
		}
				
		doGet(req, resp);
		
	}

}
