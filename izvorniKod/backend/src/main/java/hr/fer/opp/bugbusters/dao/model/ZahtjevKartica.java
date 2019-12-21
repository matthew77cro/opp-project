package hr.fer.opp.bugbusters.dao.model;

import java.util.Objects;

public class ZahtjevKartica {
	
	private Integer sifZahtjeva;
	private String oib;
	private Integer sifVrstaKartice;
	private Boolean odobren;
	
	public ZahtjevKartica(Integer sifZahtjeva, String oib, Integer sifVrstaKartice, Boolean odobren) {
		this.sifZahtjeva = sifZahtjeva;
		this.oib = oib;
		this.sifVrstaKartice = sifVrstaKartice;
		this.odobren = odobren;
	}
	
	public Integer getSifZahtjeva() {
		return sifZahtjeva;
	}
	
	public String getOib() {
		return oib;
	}
	
	public Integer getSifVrstaKartice() {
		return sifVrstaKartice;
	}
	
	public Boolean isOdobren() {
		return odobren;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sifZahtjeva);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ZahtjevKartica))
			return false;
		ZahtjevKartica other = (ZahtjevKartica) obj;
		return sifZahtjeva == other.sifZahtjeva;
	}
	
}
