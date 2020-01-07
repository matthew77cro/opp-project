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
import hr.fer.opp.bugbusters.dao.model.Racun;
import hr.fer.opp.bugbusters.dao.model.VrstaRacuna;

@SuppressWarnings("serial")
@WebServlet(name="rest-profil-racuni", urlPatterns= {"/rest/profil/racuni"})
public class RestRacuniServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp) || !LoginHandler.equalsRazinaOvlasti(req, resp, Constants.klijent)) {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		String oib = DAOProvider.getDao().getKorisnickiRacun(LoginHandler.getUsername(req, resp)).getOib();
		List<Racun> racuni = DAOProvider.getDao().getRacunByOib(oib);
		
		Map<Integer, VrstaRacuna> vrsteRacuna = DAOProvider.getDao().getAllVrstaRacuna().stream().collect(Collectors.toMap(VrstaRacuna::getSifVrsteRacuna, v -> v));
		List<RacunDescriptor> rd = new ArrayList<>();
		racuni.forEach(r -> rd.add(new RacunDescriptor(r.getBrRacun(), oib, r.getDatOtvaranja(), r.getStanje(), vrsteRacuna.get(r.getSifVrsteRacuna()), r.getPrekoracenje(), r.getKamStopa(), r.getDatZatvaranja())));
			
		
		Gson gson = new Gson();
		byte[] jsonData = gson.toJson(rd).getBytes(StandardCharsets.UTF_8);
		
		resp.setContentType("application/json");
		resp.setContentLength(jsonData.length);
		
		var os = resp.getOutputStream();
		os.write(jsonData);
		os.close();
		
	}
	
	private static class RacunDescriptor {
		private String brRacun;
		private String oib;
		private Date datOtvaranja;
		private BigDecimal stanje;
		private VrstaRacuna vrstaRacuna;
		private BigDecimal prekoracenje;
		private BigDecimal kamStopa;
		private Date datZatvaranja;
		public RacunDescriptor(String brRacun, String oib, Date datOtvaranja, BigDecimal stanje,
				VrstaRacuna vrstaRacuna, BigDecimal prekoracenje, BigDecimal kamStopa, Date datZatvaranja) {
			this.brRacun = brRacun;
			this.oib = oib;
			this.datOtvaranja = datOtvaranja;
			this.stanje = stanje;
			this.vrstaRacuna = vrstaRacuna;
			this.prekoracenje = prekoracenje;
			this.kamStopa = kamStopa;
			this.datZatvaranja = datZatvaranja;
		}
	}

}
