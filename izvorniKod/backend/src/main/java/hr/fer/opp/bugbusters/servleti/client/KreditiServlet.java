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
import hr.fer.opp.bugbusters.dao.model.Kredit;
import hr.fer.opp.bugbusters.dao.model.Racun;
import hr.fer.opp.bugbusters.dao.model.Transakcija;
import hr.fer.opp.bugbusters.dao.model.VrstaKredita;
import hr.fer.opp.bugbusters.dao.model.ZahtjevKredit;

@SuppressWarnings("serial")
@WebServlet(name="krediti", urlPatterns= {"/banka/krediti"})
public class KreditiServlet extends HttpServlet {
	
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
		List<Kredit> krediti = DAOProvider.getDao().getKreditByOib(oib);
		List<VrstaKredita> vrsteKredita = DAOProvider.getDao().getAllVrstaKredita();
		Map<Integer, VrstaKredita> vrsteKreditaMap = vrsteKredita.stream().collect(Collectors.toMap(VrstaKredita::getSifVrsteKredita, v -> v));
		List<Racun> racuni = DAOProvider.getDao().getRacunByOib(oib);
		
		req.setAttribute("racuni", racuni);
		req.setAttribute("krediti", krediti);
		req.setAttribute("vrsteKredita", vrsteKreditaMap);
		
		req.getRequestDispatcher("/WEB-INF/pages/client/credit.jsp").forward(req, resp);
		
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
		
		if(req.getParameter("creditType") != null) {
			int creditType = 0, time = 0;
			BigDecimal amount = null;
			try {
				creditType = Integer.parseInt(req.getParameter("creditType"));
				amount = new BigDecimal(req.getParameter("amount"));
				time = Integer.parseInt(req.getParameter("time"));
			} catch (NumberFormatException ex) {
				req.setAttribute("errorMsg", "Zahtjev za kreditom nije uspio! Molimo provjerite unos prije potvrde.");
				doGet(req, resp);
				return;
			}
			
			if(!DAOProvider.getDao().addZahtjevKredit(new ZahtjevKredit(0, DAOProvider.getDao().getKorisnickiRacun(LoginHandler.getUsername(req, resp)).getOib(), amount, creditType, time, false))) {
				req.setAttribute("errorMsg", "Zahtjev za kreditom nije uspio! Molimo kontaktirajte vašeg bankara.");
			}
		} else if(req.getParameter("account") != null) {
			String account = req.getParameter("account");
			int credit = 0;
			BigDecimal amount = null;
			try {
				credit = Integer.parseInt(req.getParameter("credit"));
				amount = new BigDecimal(req.getParameter("amount"));
			} catch (NumberFormatException ex) {
				req.setAttribute("errorMsg", "Zahtjev za kreditom nije uspio! Molimo provjerite unos prije potvrde.");
				doGet(req, resp);
				return;
			}
			
			Racun r = DAOProvider.getDao().getRacun(account);
			Kredit k = DAOProvider.getDao().getKredit(credit);
			if(amount.compareTo(BigDecimal.ZERO)!=1 || r==null || k==null || 
					!r.getOib().equals(DAOProvider.getDao().getKorisnickiRacun(LoginHandler.getUsername(req, resp)).getOib()) ||
					k.getPreostaloDugovanje().compareTo(amount) == -1 ||
					r.getStanje().subtract(amount).compareTo(r.getPrekoracenje().negate()) == -1) {
				req.setAttribute("errorMsg", "Krivi parametri otplate.");
				doGet(req, resp);
				return;
			}
			
			r = new Racun(r.getBrRacun(), r.getOib(), r.getDatOtvaranja(), r.getStanje().subtract(amount), r.getSifVrsteRacuna(), r.getPrekoracenje(), r.getKamStopa(), r.getDatZatvaranja());
			k = new Kredit(k.getBrKredit(), k.getOib(), k.getIznos(), k.getSifVrsteKredita(), k.getDatUgovaranja(), k.getPeriodOtplate(), k.getDatRate(), k.getPreostaloDugovanje().subtract(amount));
			Transakcija t = new Transakcija(0, r.getBrRacun(), "Kredit broj " + k.getBrKredit(), amount, new Date(System.currentTimeMillis()));
		
			DAOProvider.getDao().updateRacun(r.getBrRacun(), r);
			DAOProvider.getDao().updateKredit(k.getBrKredit(), k);
			DAOProvider.getDao().addTransakcija(t);
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		doGet(req, resp);
		
	}
	
}
