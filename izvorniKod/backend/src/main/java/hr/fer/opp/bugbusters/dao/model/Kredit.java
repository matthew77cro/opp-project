package hr.fer.opp.bugbusters.dao.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class Kredit {
	
	private Integer brKredit;
	private String oib;
	private BigDecimal iznos;
	private Integer sifVrsteKredita;
	private Date datUgovaranja;
	private Integer periodOtplate;
	private Integer datRate;
	private BigDecimal preostaloDugovanje;
	
	public Kredit(Integer brKredit, String oib, BigDecimal iznos, Integer sifVrsteKredita, Date datUgovaranja,
			Integer periodOtplate, Integer datRate, BigDecimal preostaloDugovanje) {
		this.brKredit = brKredit;
		this.oib = oib;
		this.iznos = iznos;
		this.sifVrsteKredita = sifVrsteKredita;
		this.datUgovaranja = datUgovaranja;
		this.periodOtplate = periodOtplate;
		this.datRate = datRate;
		this.preostaloDugovanje = preostaloDugovanje;
	}

	public Integer getBrKredit() {
		return brKredit;
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

	public Date getDatUgovaranja() {
		return datUgovaranja;
	}

	public Integer getPeriodOtplate() {
		return periodOtplate;
	}

	public Integer getDatRate() {
		return datRate;
	}

	public BigDecimal getPreostaloDugovanje() {
		return preostaloDugovanje;
	}

	@Override
	public int hashCode() {
		return Objects.hash(brKredit);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Kredit))
			return false;
		Kredit other = (Kredit) obj;
		return brKredit == other.brKredit;
	}
	
}
