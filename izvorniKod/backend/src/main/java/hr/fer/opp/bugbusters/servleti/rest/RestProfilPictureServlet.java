package hr.fer.opp.bugbusters.servleti.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.opp.bugbusters.control.LoginHandler;
import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.Profil;

@SuppressWarnings("serial")
@WebServlet(name="rest-profil-slika", urlPatterns= {"/rest/profil/slika"})
public class RestProfilPictureServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp) || LoginHandler.needsPasswordChange(req, resp)) {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		Profil profil = DAOProvider.getDao().getProfilByKorisnickoIme(LoginHandler.getUsername(req, resp));
		Path slika = Paths.get(req.getServletContext().getRealPath("/WEB-INF/profile-pics/") + profil.getSlika());
		if(!Files.exists(slika))
			slika = Paths.get(req.getServletContext().getRealPath("/avatar.png"));
		if(!Files.exists(slika)) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		byte[] slikaData = Files.readAllBytes(slika);
		
		resp.setContentType("image/png");
		resp.setContentLengthLong(slikaData.length);
		
		var os = resp.getOutputStream();
		os.write(slikaData);
		os.close();
		
	}

}
