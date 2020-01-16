package hr.fer.opp.bugbusters.dao.model;

import java.util.Objects;

public class Zupanija {
	
	private Integer sifZupanija;
	private String nazZupanija;
	
	public Zupanija(Integer sifZupanija, String nazZupanija) {
		this.sifZupanija = sifZupanija;
		this.nazZupanija = nazZupanija;
	}

	public Integer getSifZupanija() {
		return sifZupanija;
	}
	
	public String getNazZupanija() {
		return nazZupanija;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sifZupanija);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Zupanija))
			return false;
		Zupanija other = (Zupanija) obj;
		return sifZupanija == other.sifZupanija;
	}
	
}
