package hr.fer.opp.bugbusters.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.control.LoginHandler;
import hr.fer.opp.bugbusters.control.LoginHandler.LoginDescriptor;
import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.Constants;
import hr.fer.opp.bugbusters.dao.model.Mjesto;
import hr.fer.opp.bugbusters.dao.model.Profil;
import hr.fer.opp.bugbusters.dao.model.Zupanija;

@SuppressWarnings("serial")
@WebServlet(name="profil", urlPatterns= {"/banka/profil"})
public class ProfilServlet extends HttpServlet {
	
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
		
		loadData(req, resp);
		
		var ro = ((LoginDescriptor)req.getSession().getAttribute("login")).getRazOvlasti();
		if(ro.equals(Constants.klijent))
			req.getRequestDispatcher("/WEB-INF/pages/client/clientProfile.jsp").forward(req, resp);
		else if(ro.equals(Constants.bankar))
			req.getRequestDispatcher("/WEB-INF/pages/banker/bankerProfile.jsp").forward(req, resp);
		else if(ro.equals(Constants.sluzbenikZaKredite))
			req.getRequestDispatcher("/WEB-INF/pages/loanOfficial/creditOfficial.jsp").forward(req, resp);
		else if(ro.equals(Constants.administrator))
			req.getRequestDispatcher("/WEB-INF/pages/admin/adminProfile.jsp").forward(req, resp);
		else
			resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!LoginHandler.isLoggedIn(req, resp)) {
			resp.sendRedirect("login");
			return;
		}
		
		if(LoginHandler.needsPasswordChange(req, resp)) {
			resp.sendRedirect("passwordchange");
			return;
		}
		
		if(!LoginHandler.changePassword(req, resp)) {
			req.setAttribute("errorMsg", "Greška! Pokušajte ponovno. <br> Napomena: Nova lozinka ne smije biti prazna ili ista kao prošla.");
			loadData(req, resp);
			req.getRequestDispatcher("/WEB-INF/pages/client/clientProfile.jsp").forward(req, resp);
		} else {
			LoginHandler.doLogout(req, resp);
			resp.sendRedirect("logout");
		}
	}
	
	private void loadData(HttpServletRequest req, HttpServletResponse resp) {
		String oib = DAOProvider.getDao().getKorisnickiRacun(LoginHandler.getUsername(req, resp)).getOib();
		Profil profil = DAOProvider.getDao().getProfil(oib);
		Mjesto mjesto = DAOProvider.getDao().getMjesto(profil.getPbr());
		Zupanija zupanija = DAOProvider.getDao().getZupanija(mjesto.getSifraZupanija());
		
		String address = profil.getAdresa() + "," + mjesto.getPbr() + " " + mjesto.getNazMjesto() + "\n" + zupanija.getNazZupanija();
		
		req.setAttribute("firstName", profil.getIme());
		req.setAttribute("lastName", profil.getPrezime());
		req.setAttribute("address", address);
		req.setAttribute("oib", profil.getOib());
		req.setAttribute("birthday", profil.getDatRod());
		req.setAttribute("email", profil.getEmail());
	}

}
