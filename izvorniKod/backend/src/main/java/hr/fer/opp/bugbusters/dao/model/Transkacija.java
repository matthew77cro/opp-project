package hr.fer.opp.bugbusters.dao.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Transkacija {

	private int brTransakcija;
	private String racTerecenja;
	private String racOdobrenja;
	private BigDecimal iznos;
	private Date datTransakcije;
	
	public Transkacija(int brTransakcija, String racTerecenja, String racOdobrenja, BigDecimal iznos,
			Date datTransakcije) {
		this.brTransakcija = brTransakcija;
		this.racTerecenja = racTerecenja;
		this.racOdobrenja = racOdobrenja;
		this.iznos = iznos;
		this.datTransakcije = datTransakcije;
	}
	
	public int getBrTransakcija() {
		return brTransakcija;
	}
	
	public String getRacTerecenja() {
		return racTerecenja;
	}
	
	public String getRacOdobrenja() {
		return racOdobrenja;
	}
	
	public BigDecimal getIznos() {
		return iznos;
	}
	
	public Date getDatTransakcije() {
		return datTransakcije;
	}
	
}
