package hr.fer.opp.bugbusters.dao.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Kartica {
	
	private String brKartica;
	private String brRacun;
	private String oib;
	private int sifVrstaKartice;
	private BigDecimal stanje;
	private Date valjanost;
	private BigDecimal limitKartice;
	private BigDecimal kamStopa;
	private int datRate;
	
	public Kartica(String brKartica, String brRacun, String oib, int sifVrstaKartice, BigDecimal stanje, Date valjanost,
			BigDecimal limitKartice, BigDecimal kamStopa, int datRate) {
		this.brKartica = brKartica;
		this.brRacun = brRacun;
		this.oib = oib;
		this.sifVrstaKartice = sifVrstaKartice;
		this.stanje = stanje;
		this.valjanost = valjanost;
		this.limitKartice = limitKartice;
		this.kamStopa = kamStopa;
		this.datRate = datRate;
	}

	public String getBrKartica() {
		return brKartica;
	}

	public String getBrRacun() {
		return brRacun;
	}

	public String getOib() {
		return oib;
	}

	public int getSifVrstaKartice() {
		return sifVrstaKartice;
	}

	public BigDecimal getStanje() {
		return stanje;
	}

	public Date getValjanost() {
		return valjanost;
	}

	public BigDecimal getLimitKartice() {
		return limitKartice;
	}

	public BigDecimal getKamStopa() {
		return kamStopa;
	}

	public int getDatRate() {
		return datRate;
	}

}
