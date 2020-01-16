package hr.fer.opp.bugbusters.dao.model;

import java.math.BigDecimal;
import java.util.Objects;

public class VrstaKredita {
	
	private Integer sifVrsteKredita;
	private String nazVrsteKredita;
	private BigDecimal kamStopa;
	
	public VrstaKredita(Integer sifVrsteKredita, String nazVrsteKredita, BigDecimal kamStopa) {
		this.sifVrsteKredita = sifVrsteKredita;
		this.nazVrsteKredita = nazVrsteKredita;
		this.kamStopa = kamStopa;
	}

	public Integer getSifVrsteKredita() {
		return sifVrsteKredita;
	}
	
	public String getNazVrsteKredita() {
		return nazVrsteKredita;
	}
	
	public BigDecimal getKamStopa() {
		return kamStopa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sifVrsteKredita);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof VrstaKredita))
			return false;
		VrstaKredita other = (VrstaKredita) obj;
		return sifVrsteKredita == other.sifVrsteKredita;
	}
	
}
