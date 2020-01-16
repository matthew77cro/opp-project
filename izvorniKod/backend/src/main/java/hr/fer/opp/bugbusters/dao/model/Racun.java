package hr.fer.opp.bugbusters.dao.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class Racun {
	
	private String brRacun;
	private String oib;
	private Date datOtvaranja;
	private BigDecimal stanje;
	private Integer sifVrsteRacuna;
	private BigDecimal prekoracenje;
	private BigDecimal kamStopa;
	private Date datZatvaranja;
	
	public Racun(String brRacun, String oib, Date datOtvaranja, BigDecimal stanje, Integer sifVrsteRacuna,
			BigDecimal prekoracenje, BigDecimal kamStopa, Date datZatvaranja) {
		this.brRacun = brRacun;
		this.oib = oib;
		this.datOtvaranja = datOtvaranja;
		this.stanje = stanje;
		this.sifVrsteRacuna = sifVrsteRacuna;
		this.prekoracenje = prekoracenje;
		this.kamStopa = kamStopa;
		this.datZatvaranja = datZatvaranja;
	}

	public String getBrRacun() {
		return brRacun;
	}

	public String getOib() {
		return oib;
	}

	public Date getDatOtvaranja() {
		return datOtvaranja;
	}

	public BigDecimal getStanje() {
		return stanje;
	}

	public Integer getSifVrsteRacuna() {
		return sifVrsteRacuna;
	}

	public BigDecimal getPrekoracenje() {
		return prekoracenje;
	}

	public BigDecimal getKamStopa() {
		return kamStopa;
	}

	public Date getDatZatvaranja() {
		return datZatvaranja;
	}

	@Override
	public int hashCode() {
		return Objects.hash(brRacun);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Racun))
			return false;
		Racun other = (Racun) obj;
		return Objects.equals(brRacun, other.brRacun);
	}
	
}
