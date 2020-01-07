package hr.fer.opp.bugbusters.servleti.rest;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.opp.bugbusters.control.LoginHandler;
import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.Constants;
import hr.fer.opp.bugbusters.dao.model.Kartica;
import hr.fer.opp.bugbusters.dao.model.Racun;
import hr.fer.opp.bugbusters.dao.model.VrstaKartice;

@SuppressWarnings("serial")
@WebServlet(name="rest-profil-kartice", urlPatterns= {"/rest/profil/kartice"})
public class RestKarticeServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp) || !LoginHandler.equalsRazinaOvlasti(req, resp, Constants.klijent)) {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		String oib = DAOProvider.getDao().getKorisnickiRacun(LoginHandler.getUsername(req, resp)).getOib();
		List<Racun> racuni = DAOProvider.getDao().getRacunByOib(oib);
		List<Kartica> kartice = DAOProvider.getDao().getKarticaByOib(oib);
		racuni.forEach(r -> kartice.addAll(DAOProvider.getDao().getKarticaByBrRacun(r.getBrRacun())));
		
		Map<Integer, VrstaKartice> vrsteKartice = DAOProvider.getDao().getAllVrstaKartice().stream().collect(Collectors.toMap(VrstaKartice::getSifVrsteKartice, v -> v));
		List<KarticaDescriptor> kd = new ArrayList<>();
		kartice.forEach(k -> kd.add(new KarticaDescriptor(k.getBrKartica(), k.getBrRacun(), k.getOib(), vrsteKartice.get(k.getSifVrstaKartice()), k.getStanje(), k.getValjanost(), k.getLimitKartice(), k.getKamStopa(), k.getDatRate())));
		
		Gson gson = new Gson();
		byte[] jsonData = gson.toJson(kd).getBytes(StandardCharsets.UTF_8);
		
		resp.setContentType("application/json");
		resp.setContentLength(jsonData.length);
		
		var os = resp.getOutputStream();
		os.write(jsonData);
		os.close();
		
	}
	
	private static class KarticaDescriptor {
		private String brKartica;
		private String brRacun;
		private String oib;
		private VrstaKartice vrstaKartice;
		private BigDecimal stanje;
		private Date valjanost;
		private BigDecimal limitKartice;
		private BigDecimal kamStopa;
		private Integer datRate;
		public KarticaDescriptor(String brKartica, String brRacun, String oib, VrstaKartice vrstaKartice,
				BigDecimal stanje, Date valjanost, BigDecimal limitKartice, BigDecimal kamStopa, Integer datRate) {
			this.brKartica = brKartica;
			this.brRacun = brRacun;
			this.oib = oib;
			this.vrstaKartice = vrstaKartice;
			this.stanje = stanje;
			this.valjanost = valjanost;
			this.limitKartice = limitKartice;
			this.kamStopa = kamStopa;
			this.datRate = datRate;
		}
	}

}
