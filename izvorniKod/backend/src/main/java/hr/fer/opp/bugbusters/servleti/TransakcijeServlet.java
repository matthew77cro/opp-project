package hr.fer.opp.bugbusters.servleti;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.control.LoginHandler;
import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.Racun;
import hr.fer.opp.bugbusters.dao.model.Transakcija;

@SuppressWarnings("serial")
@WebServlet(name="transakcije", urlPatterns= {"/banka/transakcije"})
public class TransakcijeServlet extends HttpServlet {
	
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
		
		String fromDateParam = req.getParameter("fromDate");
		String toDateParam = req.getParameter("toDate");
		
		final Date fromDate = fromDateParam!=null && !fromDateParam.isEmpty() ? Date.valueOf(fromDateParam) : null;
		final Date toDate = toDateParam!=null && !toDateParam.isEmpty() ? Date.valueOf(toDateParam) : null;
		
		String oib = DAOProvider.getDao().getKorisnickiRacun(LoginHandler.getUsername(req, resp)).getOib();
		List<Racun> racuni = DAOProvider.getDao().getRacunByOib(oib);
		List<Transakcija> transakcije = new ArrayList<>();
		racuni.forEach((r) -> transakcije.addAll(DAOProvider.getDao().getTransakcijaByBrRacunOdobrenja(r.getBrRacun())));
		racuni.stream()
			.map((r) -> DAOProvider.getDao().getTransakcijaByBrRacunTerecenja(r.getBrRacun()))
			.forEach((tList) -> 
				tList.forEach((t) -> 
					transakcije.add(new Transakcija(t.getBrTransakcija(), t.getRacTerecenja(), t.getRacOdobrenja(), t.getIznos().negate(), t.getDatTransakcije()))
				)
			);
		
		transakcije.sort((c1, c2) -> c2.getDatTransakcije().compareTo(c2.getDatTransakcije()));
		
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
	
}
