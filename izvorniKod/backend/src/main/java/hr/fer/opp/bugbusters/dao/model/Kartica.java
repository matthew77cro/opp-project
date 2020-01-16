package hr.fer.opp.bugbusters.dao.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class Kartica {
	
	private String brKartica;
	private String brRacun;
	private String oib;
	private Integer sifVrstaKartice;
	private BigDecimal stanje;
	private Date valjanost;
	private BigDecimal limitKartice;
	private BigDecimal kamStopa;
	private Integer datRate;
	
	public Kartica(String brKartica, String brRacun, String oib, Integer sifVrstaKartice, BigDecimal stanje, Date valjanost,
			BigDecimal limitKartice, BigDecimal kamStopa, Integer datRate) {
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

	public Integer getSifVrstaKartice() {
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

	public Integer getDatRate() {
		return datRate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(brKartica);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Kartica))
			return false;
		Kartica other = (Kartica) obj;
		return Objects.equals(brKartica, other.brKartica);
	}

}
