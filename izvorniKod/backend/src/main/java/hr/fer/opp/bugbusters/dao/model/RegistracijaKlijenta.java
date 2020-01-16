package hr.fer.opp.bugbusters.dao.model;

import java.util.Objects;

public class RegistracijaKlijenta {
	
	private String oib;
	private String privremeniKljuc;
	
	public RegistracijaKlijenta(String oib, String privremeniKljuc) {
		this.oib = oib;
		this.privremeniKljuc = privremeniKljuc;
	}
	
	public String getOib() {
		return oib;
	}
	
	public String getPrivremeniKljuc() {
		return privremeniKljuc;
	}

	@Override
	public int hashCode() {
		return Objects.hash(oib);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RegistracijaKlijenta))
			return false;
		RegistracijaKlijenta other = (RegistracijaKlijenta) obj;
		return Objects.equals(oib, other.oib);
	}

}
