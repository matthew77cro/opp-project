package hr.fer.opp.bugbusters.servleti.client;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.control.LoginHandler;
import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.Constants;
import hr.fer.opp.bugbusters.dao.model.Racun;
import hr.fer.opp.bugbusters.dao.model.Transakcija;

@SuppressWarnings("serial")
@WebServlet(name="transakcije", urlPatterns= {"/banka/transakcije"})
public class TransakcijeServlet extends HttpServlet {
	
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
		
		String fromDateParam = req.getParameter("fromDate");
		String toDateParam = req.getParameter("toDate");
		
		final Date fromDate = fromDateParam!=null && !fromDateParam.isEmpty() ? Date.valueOf(fromDateParam) : null;
		final Date toDate = toDateParam!=null && !toDateParam.isEmpty() ? Date.valueOf(toDateParam) : null;
		
		String oib = DAOProvider.getDao().getKorisnickiRacun(LoginHandler.getUsername(req, resp)).getOib();
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
		
		req.getRequestDispatcher("/WEB-INF/pages/client/transaction.jsp").forward(req, resp);
		
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
		
		String debitingAccount = req.getParameter("debitingAccount");
		String approvalAccount = req.getParameter("approvalAccount");
		String amount = req.getParameter("amount");
		if(debitingAccount == null || approvalAccount == null || amount == null) {
			req.setAttribute("errorMsg", "Krivi parametri otplate.");
			doGet(req, resp);
			return;
		}
		
		BigDecimal amountBD = null;
		try {
			amountBD = new BigDecimal(amount);
		} catch (NumberFormatException ex) {
			req.setAttribute("errorMsg", "Krivi iznos");
			doGet(req, resp);
			return;
		}
		
		Racun r1 = DAOProvider.getDao().getRacun(debitingAccount);
		Racun r2 = DAOProvider.getDao().getRacun(approvalAccount);
		
		if(amountBD.compareTo(BigDecimal.ZERO)!=1 || r1==null || r1.getStanje().subtract(amountBD).compareTo(r1.getPrekoracenje().negate()) == -1) {
			req.setAttribute("errorMsg", "Krivi parametri otplate.");
			doGet(req, resp);
			return;
		}
		
		r1 = new Racun(r1.getBrRacun(), r1.getOib(), r1.getDatOtvaranja(), r1.getStanje().subtract(amountBD), r1.getSifVrsteRacuna(), r1.getPrekoracenje(), r1.getKamStopa(), r1.getDatZatvaranja());
		DAOProvider.getDao().updateRacun(r1.getBrRacun(), r1);
		if(r2!=null) {
			r2 = new Racun(r2.getBrRacun(), r2.getOib(), r2.getDatOtvaranja(), r2.getStanje().add(amountBD), r2.getSifVrsteRacuna(), r2.getPrekoracenje(), r2.getKamStopa(), r2.getDatZatvaranja());
			DAOProvider.getDao().updateRacun(r2.getBrRacun(), r2);
		}
		
		Transakcija t = new Transakcija(0, r1.getBrRacun(), approvalAccount, amountBD, new Date(System.currentTimeMillis()));
		DAOProvider.getDao().addTransakcija(t);
		
		doGet(req, resp);
	}
	
}
