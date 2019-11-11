package hr.fer.opp.bugbusters.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.Mjesto;
import hr.fer.opp.bugbusters.dao.model.Profil;
import hr.fer.opp.bugbusters.dao.model.Zupanija;
import hr.fer.opp.bugbusters.servleti.control.LoginHandler;

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
		
		Profil profil = DAOProvider.getDao().getProfilByKorisnickoIme(LoginHandler.getUsername(req, resp));
		Mjesto mjesto = DAOProvider.getDao().getMjesto(profil.getPbr());
		Zupanija zupanija = DAOProvider.getDao().getZupanija(mjesto.getSifraZupanija());
		
		String address = profil.getAdresa() + "," + mjesto.getPbr() + " " + mjesto.getNazMjesto() + "<br>" + zupanija.getNazZupanija();
		
		req.setAttribute("firstName", profil.getIme());
		req.setAttribute("lastName", profil.getPrezime());
		req.setAttribute("address", address);
		req.setAttribute("oib", profil.getOib());
		req.setAttribute("birthday", profil.getDatRod());
		req.setAttribute("email", profil.getEmail());
		
		req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);
		
	}

}
