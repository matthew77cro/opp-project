package hr.fer.opp.bugbusters.dao.model;

import java.util.Objects;

public class RazinaOvlasti {
	
	private Integer sifRazOvlasti;
	private String nazivRazOvlasti;
	
	public RazinaOvlasti(Integer sifRazOvlasti, String nazivRazOvlasti) {
		this.sifRazOvlasti = sifRazOvlasti;
		this.nazivRazOvlasti = nazivRazOvlasti;
	}

	public Integer getSifRazOvlasti() {
		return sifRazOvlasti;
	}

	public String getNazivRazOvlasti() {
		return nazivRazOvlasti;
	}

	public RazinaOvlasti(Integer sifRazOvlasti) {
		this.sifRazOvlasti = sifRazOvlasti;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sifRazOvlasti);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RazinaOvlasti))
			return false;
		RazinaOvlasti other = (RazinaOvlasti) obj;
		return sifRazOvlasti == other.sifRazOvlasti;
	}

}
