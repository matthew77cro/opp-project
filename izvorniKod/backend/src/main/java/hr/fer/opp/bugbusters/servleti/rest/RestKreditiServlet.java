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
import hr.fer.opp.bugbusters.dao.model.Kredit;
import hr.fer.opp.bugbusters.dao.model.VrstaKredita;

@SuppressWarnings("serial")
@WebServlet(name="rest-profil-krediti", urlPatterns= {"/rest/profil/krediti"})
public class RestKreditiServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!LoginHandler.isLoggedIn(req, resp)) {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		String oib = DAOProvider.getDao().getKorisnickiRacun(LoginHandler.getUsername(req, resp)).getOib();
		List<Kredit> krediti = DAOProvider.getDao().getKreditByOib(oib);
		
		Map<Integer, VrstaKredita> vrsteKredita = DAOProvider.getDao().getAllVrstaKredita().stream().collect(Collectors.toMap(VrstaKredita::getSifVrsteKredita, v -> v));
		List<KreditDescriptor> kd = new ArrayList<>();
		krediti.forEach(k -> kd.add(new KreditDescriptor(k.getBrKredit(), oib, k.getIznos(), vrsteKredita.get(k.getSifVrsteKredita()), k.getDatUgovaranja(), k.getPeriodOtplate(), k.getDatRate(), k.getPreostaloDugovanje())));
				
		Gson gson = new Gson();
		byte[] jsonData = gson.toJson(kd).getBytes(StandardCharsets.UTF_8);
		
		resp.setContentType("application/json");
		resp.setContentLength(jsonData.length);
		
		var os = resp.getOutputStream();
		os.write(jsonData);
		os.close();
		
	}
	
	private static class KreditDescriptor {
		private Integer brKredit;
		private String oib;
		private BigDecimal iznos;
		private VrstaKredita vrstaKredita;
		private Date datUgovaranja;
		private Integer periodOtplate;
		private Integer datRate;
		private BigDecimal preostaloDugovanje;
		public KreditDescriptor(Integer brKredit, String oib, BigDecimal iznos, VrstaKredita vrstaKredita,
				Date datUgovaranja, Integer periodOtplate, Integer datRate, BigDecimal preostaloDugovanje) {
			this.brKredit = brKredit;
			this.oib = oib;
			this.iznos = iznos;
			this.vrstaKredita = vrstaKredita;
			this.datUgovaranja = datUgovaranja;
			this.periodOtplate = periodOtplate;
			this.datRate = datRate;
			this.preostaloDugovanje = preostaloDugovanje;
		}
	}

}
