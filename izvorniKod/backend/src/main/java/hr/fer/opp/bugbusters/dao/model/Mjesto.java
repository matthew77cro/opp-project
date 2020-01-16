package hr.fer.opp.bugbusters.dao.model;

import java.util.Objects;

public class Mjesto {

	private Integer pbr;
	private String nazMjesto;
	private Integer sifraZupanija;
	
	public Mjesto(Integer pbr, String nazMjesto, Integer sifraZupanija) {
		this.pbr = pbr;
		this.nazMjesto = nazMjesto;
		this.sifraZupanija = sifraZupanija;
	}
	
	public Integer getPbr() {
		return pbr;
	}
	
	public String getNazMjesto() {
		return nazMjesto;
	}
	
	public Integer getSifraZupanija() {
		return sifraZupanija;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pbr);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Mjesto))
			return false;
		Mjesto other = (Mjesto) obj;
		return pbr == other.pbr;
	}
	
}
