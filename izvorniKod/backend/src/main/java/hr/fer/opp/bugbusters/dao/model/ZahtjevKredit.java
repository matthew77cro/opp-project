package hr.fer.opp.bugbusters.dao.model;

import java.math.BigDecimal;
import java.util.Objects;

public class ZahtjevKredit {
	
	private Integer sifZahtjeva;
	private String oib;
	private BigDecimal iznos;
	private Integer sifVrsteKredita;
	private Integer periodOtplate;
	private Boolean odobren;
	
	public ZahtjevKredit(Integer sifZahtjeva, String oib, BigDecimal iznos, Integer sifVrsteKredita, Integer periodOtplate,
			Boolean odobren) {
		this.sifZahtjeva = sifZahtjeva;
		this.oib = oib;
		this.iznos = iznos;
		this.sifVrsteKredita = sifVrsteKredita;
		this.periodOtplate = periodOtplate;
		this.odobren = odobren;
	}

	public Integer getSifZahtjeva() {
		return sifZahtjeva;
	}

	public String getOib() {
		return oib;
	}

	public BigDecimal getIznos() {
		return iznos;
	}

	public Integer getSifVrsteKredita() {
		return sifVrsteKredita;
	}

	public Integer getPeriodOtplate() {
		return periodOtplate;
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
		if (!(obj instanceof ZahtjevKredit))
			return false;
		ZahtjevKredit other = (ZahtjevKredit) obj;
		return sifZahtjeva == other.sifZahtjeva;
	}
	
}
