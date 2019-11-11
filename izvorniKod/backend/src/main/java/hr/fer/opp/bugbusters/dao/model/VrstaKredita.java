package hr.fer.opp.bugbusters.dao.model;

import java.math.BigDecimal;

public class VrstaKredita {
	
	private int sifVrsteKredita;
	private String nazVrsteKredita;
	private BigDecimal kamStopa;
	
	public VrstaKredita(int sifVrsteKredita, String nazVrsteKredita, BigDecimal kamStopa) {
		this.sifVrsteKredita = sifVrsteKredita;
		this.nazVrsteKredita = nazVrsteKredita;
		this.kamStopa = kamStopa;
	}

	public int getSifVrsteKredita() {
		return sifVrsteKredita;
	}
	
	public String getNazVrsteKredita() {
		return nazVrsteKredita;
	}
	
	public BigDecimal getKamStopa() {
		return kamStopa;
	}

}
