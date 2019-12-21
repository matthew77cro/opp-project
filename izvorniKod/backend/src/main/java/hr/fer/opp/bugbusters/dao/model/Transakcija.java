package hr.fer.opp.bugbusters.dao.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class Transakcija {

	private Integer brTransakcija;
	private String racTerecenja;
	private String racOdobrenja;
	private BigDecimal iznos;
	private Date datTransakcije;
	
	public Transakcija(Integer brTransakcija, String racTerecenja, String racOdobrenja, BigDecimal iznos,
			Date datTransakcije) {
		this.brTransakcija = brTransakcija;
		this.racTerecenja = racTerecenja;
		this.racOdobrenja = racOdobrenja;
		this.iznos = iznos;
		this.datTransakcije = datTransakcije;
	}
	
	public Integer getBrTransakcija() {
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

	@Override
	public int hashCode() {
		return Objects.hash(brTransakcija);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Transakcija))
			return false;
		Transakcija other = (Transakcija) obj;
		return brTransakcija == other.brTransakcija;
	}
	
}
