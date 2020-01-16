package hr.fer.opp.bugbusters.servleti.rest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.opp.bugbusters.control.LoginHandler;
import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.Constants;
import hr.fer.opp.bugbusters.dao.model.Transakcija;

@SuppressWarnings("serial")
@WebServlet(name="rest-profil-transakcije", urlPatterns= {"/rest/profil/transakcije"})
public class RestTranskacijeServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp) || !LoginHandler.equalsRazinaOvlasti(req, resp, Constants.klijent)) {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		String oib = DAOProvider.getDao().getKorisnickiRacun(LoginHandler.getUsername(req, resp)).getOib();
		List<Transakcija> transakcije = DAOProvider.getDao().getTransakcijeByOib(oib);
		
		Gson gson = new Gson();
		byte[] jsonData = gson.toJson(transakcije).getBytes(StandardCharsets.UTF_8);
		
		resp.setContentType("application/json");
		resp.setContentLength(jsonData.length);
		
		var os = resp.getOutputStream();
		os.write(jsonData);
		os.close();
		
	}

}
