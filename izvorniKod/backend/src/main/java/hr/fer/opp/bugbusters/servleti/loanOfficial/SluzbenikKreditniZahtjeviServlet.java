package hr.fer.opp.bugbusters.servleti.loanOfficial;

import java.io.IOException;
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
import hr.fer.opp.bugbusters.dao.model.VrstaKredita;
import hr.fer.opp.bugbusters.dao.model.ZahtjevKredit;

@SuppressWarnings("serial")
@WebServlet(name="sluzbenik-kreditni-zahtjevi", urlPatterns= {"/banka/sluzbenik-kreditni-zahtjevi"})
public class SluzbenikKreditniZahtjeviServlet extends HttpServlet {
	
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
		
		List<ZahtjevKredit> zahtjevi = DAOProvider.getDao().getAllZahtjevKredit();
		Map<Integer, VrstaKredita> vrsteKartica = DAOProvider.getDao().getAllVrstaKredita().stream().collect(Collectors.toMap(k -> k.getSifVrsteKredita(), k -> k));
		req.setAttribute("zahtjevi", zahtjevi);
		req.setAttribute("vrsteKredita", vrsteKartica);
		
		req.getRequestDispatcher("/WEB-INF/pages/loanOfficial/officialCreditReq.jsp").forward(req, resp);
				
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
			case "approve" :
				
				int sifZahtjeva = Integer.parseInt(req.getParameter("sifZahtjeva"));
				String datRate = req.getParameter("datRate");
				
				ZahtjevKredit zk = DAOProvider.getDao().getZahtjevKredit(sifZahtjeva);
				
				int datRateInt = 0;
				try {
					datRateInt = Integer.parseInt(datRate);
				} catch (Exception ex) {
					req.setAttribute("errorMsg", "Request error - invalid parameters");
					break;
				}
				
				Kredit k = new Kredit(0, zk.getOib(), zk.getIznos(), zk.getSifVrsteKredita(), new Date(System.currentTimeMillis()), zk.getPeriodOtplate(), datRateInt, zk.getIznos());
				
				DAOProvider.getDao().addKredit(k);
				DAOProvider.getDao().removeZahtjevKredit(sifZahtjeva);
				
				break;
				
			case "block" :
				
				sifZahtjeva = Integer.parseInt(req.getParameter("sifZahtjeva"));
				DAOProvider.getDao().removeZahtjevKredit(sifZahtjeva);
				
				break;
				
			default :
				req.setAttribute("errorMsg", "Request error - invalid action");
		}
				
		doGet(req, resp);
		
	}

}
