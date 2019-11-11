package hr.fer.opp.bugbusters.servleti.rest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.Mjesto;
import hr.fer.opp.bugbusters.dao.model.Profil;
import hr.fer.opp.bugbusters.dao.model.Zupanija;
import hr.fer.opp.bugbusters.servleti.control.LoginHandler;

@SuppressWarnings("serial")
@WebServlet(name="rest-profil", urlPatterns= {"/rest/profil"})
public class RestProfilServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp)) {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		Profil profil = DAOProvider.getDao().getProfilByKorisnickoIme(LoginHandler.getUsername(req, resp));
		Mjesto mjesto = DAOProvider.getDao().getMjesto(profil.getPbr());
		Zupanija zupanija = DAOProvider.getDao().getZupanija(mjesto.getSifraZupanija());
		
		ProfileDescriptor pd = new ProfileDescriptor(profil.getIme(), 
				profil.getPrezime(), 
				profil.getAdresa(), 
				mjesto.getPbr(), 
				mjesto.getNazMjesto(), 
				zupanija.getNazZupanija(), 
				profil.getOib(), 
				profil.getDatRod(), 
				profil.getEmail());
		
		Gson gson = new Gson();
		String json = gson.toJson(pd);
		
		var os = resp.getOutputStream();
		os.write(json.getBytes(StandardCharsets.UTF_8));
		os.close();
		
	}
	
	private static class ProfileDescriptor {
		
		private String firstName;
		private String lastName;
		private String address;
		private int cityPostCode;
		private String cityName;
		private String countyName;
		private String oib;
		private Date birthday;
		private String email;
		
		public ProfileDescriptor(String firstName, String lastName, String address, int cityPostCode, String cityName,
				String countyName, String oib, Date birthday, String email) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.address = address;
			this.cityPostCode = cityPostCode;
			this.cityName = cityName;
			this.countyName = countyName;
			this.oib = oib;
			this.birthday = birthday;
			this.email = email;
		}
		
	}

}
