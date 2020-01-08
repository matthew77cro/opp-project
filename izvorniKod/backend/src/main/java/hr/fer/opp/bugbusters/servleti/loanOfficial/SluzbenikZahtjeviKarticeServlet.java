package hr.fer.opp.bugbusters.servleti.loanOfficial;

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
import hr.fer.opp.bugbusters.dao.model.Kartica;
import hr.fer.opp.bugbusters.dao.model.VrstaKartice;
import hr.fer.opp.bugbusters.dao.model.ZahtjevKartica;

@SuppressWarnings("serial")
@WebServlet(name="sluzbenik-zahtjevi-kartice", urlPatterns= {"/banka/sluzbenik-zahtjevi-kartice"})
public class SluzbenikZahtjeviKarticeServlet extends HttpServlet {
	
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
		
		List<ZahtjevKartica> zahtjevi = DAOProvider.getDao().getAllZahtjevKartica();
		Map<Integer, VrstaKartice> vrsteKartica = DAOProvider.getDao().getAllVrstaKartice().stream().collect(Collectors.toMap(k -> k.getSifVrsteKartice(), k -> k));
		req.setAttribute("zahtjevi", zahtjevi);
		req.setAttribute("vrsteKartica", vrsteKartica);
		
		req.getRequestDispatcher("/WEB-INF/pages/loanOfficial/officialCardReq.jsp").forward(req, resp);
				
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
				String brojKartice = req.getParameter("brojKartice");
				String datRate = req.getParameter("datRate");
				String limit = req.getParameter("limit");
				String kamStopa = req.getParameter("kamStopa");
				String valjanost = req.getParameter("valjanost");
				
				ZahtjevKartica zk = DAOProvider.getDao().getZahtjevKartica(sifZahtjeva);
				
				int datRateInt = 0;
				BigDecimal limitBD = null, kamStopaBD = null;
				Date valjanostDate = null;
				try {
					datRateInt = Integer.parseInt(datRate);
					limitBD = new BigDecimal(limit);
					kamStopaBD = new BigDecimal(kamStopa);
					valjanostDate = Date.valueOf(valjanost);
				} catch (Exception ex) {
					req.setAttribute("errorMsg", "Request error - invalid parameters");
					break;
				}
				
				Kartica k = DAOProvider.getDao().getKartica(brojKartice);
				if(k!=null) {
					req.setAttribute("errorMsg", "Request error - card number already exists");
					break;
				}
				
				k = new Kartica(brojKartice, null, zk.getOib(), zk.getSifVrstaKartice(), BigDecimal.ZERO, valjanostDate, limitBD, kamStopaBD, datRateInt);
				
				DAOProvider.getDao().addKartica(k);
				DAOProvider.getDao().removeZahtjevKartica(sifZahtjeva);
				
				break;
				
			case "block" :
				
				sifZahtjeva = Integer.parseInt(req.getParameter("sifZahtjeva"));
				DAOProvider.getDao().removeZahtjevKartica(sifZahtjeva);
				
				break;
				
			default :
				req.setAttribute("errorMsg", "Request error - invalid action");
		}
				
		doGet(req, resp);
		
	}

}
