package hr.fer.opp.bugbusters.dao.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Kredit {
	
	private int brKredit;
	private String oib;
	private BigDecimal iznos;
	private int sifVrsteKredita;
	private Date datUgovaranja;
	private int periodOtplate;
	private int datRate;
	private BigDecimal preostaloDugovanje;
	
	public Kredit(int brKredit, String oib, BigDecimal iznos, int sifVrsteKredita, Date datUgovaranja,
			int periodOtplate, int datRate, BigDecimal preostaloDugovanje) {
		this.brKredit = brKredit;
		this.oib = oib;
		this.iznos = iznos;
		this.sifVrsteKredita = sifVrsteKredita;
		this.datUgovaranja = datUgovaranja;
		this.periodOtplate = periodOtplate;
		this.datRate = datRate;
		this.preostaloDugovanje = preostaloDugovanje;
	}

	public int getBrKredit() {
		return brKredit;
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

	public Date getDatUgovaranja() {
		return datUgovaranja;
	}

	public int getPeriodOtplate() {
		return periodOtplate;
	}

	public int getDatRate() {
		return datRate;
	}

	public BigDecimal getPreostaloDugovanje() {
		return preostaloDugovanje;
	}
	
}
