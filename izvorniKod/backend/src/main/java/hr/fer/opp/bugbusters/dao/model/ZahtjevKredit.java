package hr.fer.opp.bugbusters.dao.model;

import java.math.BigDecimal;

public class ZahtjevKredit {
	
	private int sifZahtjeva;
	private String oib;
	private BigDecimal iznos;
	private int sifVrsteKredita;
	private int periodOtplate;
	private boolean odobren;
	
	public ZahtjevKredit(int sifZahtjeva, String oib, BigDecimal iznos, int sifVrsteKredita, int periodOtplate,
			boolean odobren) {
		this.sifZahtjeva = sifZahtjeva;
		this.oib = oib;
		this.iznos = iznos;
		this.sifVrsteKredita = sifVrsteKredita;
		this.periodOtplate = periodOtplate;
		this.odobren = odobren;
	}

	public int getSifZahtjeva() {
		return sifZahtjeva;
	}

	public String getOib() {
		return oib;
	}

	public BigDecimal getIznos() {
		return iznos;
	}

	public int getSifVrsteKredita() {
		return sifVrsteKredita;
	}

	public int getPeriodOtplate() {
		return periodOtplate;
	}

	public boolean isOdobren() {
		return odobren;
	}
	
}
