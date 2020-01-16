package hr.fer.opp.bugbusters.dao.model;

import java.util.Objects;

public class VrstaRacuna {

	private Integer sifVrsteRacuna;
	private String nazVrsteRacuna;
	
	public VrstaRacuna(Integer sifVrsteRacuna, String nazVrsteRacuna) {
		this.sifVrsteRacuna = sifVrsteRacuna;
		this.nazVrsteRacuna = nazVrsteRacuna;
	}

	public Integer getSifVrsteRacuna() {
		return sifVrsteRacuna;
	}
	
	public String getNazVrsteRacuna() {
		return nazVrsteRacuna;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sifVrsteRacuna);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof VrstaRacuna))
			return false;
		VrstaRacuna other = (VrstaRacuna) obj;
		return sifVrsteRacuna == other.sifVrsteRacuna;
	}
	
}
