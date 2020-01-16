package hr.fer.opp.bugbusters.dao.model;

import java.util.Objects;

public class VrstaKartice {
	
	private Integer sifVrsteKartice;
	private String nazVrsteKartice;
	
	public VrstaKartice(Integer sifVrstaKartice, String nazVrstaKartice) {
		this.sifVrsteKartice = sifVrstaKartice;
		this.nazVrsteKartice = nazVrstaKartice;
	}

	public Integer getSifVrsteKartice() {
		return sifVrsteKartice;
	}
	
	public String getNazVrsteKartice() {
		return nazVrsteKartice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sifVrsteKartice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof VrstaKartice))
			return false;
		VrstaKartice other = (VrstaKartice) obj;
		return sifVrsteKartice == other.sifVrsteKartice;
	}

}
