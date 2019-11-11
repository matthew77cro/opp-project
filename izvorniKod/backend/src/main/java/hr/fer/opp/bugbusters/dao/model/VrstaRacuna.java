package hr.fer.opp.bugbusters.dao.model;

public class VrstaRacuna {

	private int sifVrsteRacuna;
	private String nazVrsteRacuna;
	
	public VrstaRacuna(int sifVrsteRacuna, String nazVrsteRacuna) {
		this.sifVrsteRacuna = sifVrsteRacuna;
		this.nazVrsteRacuna = nazVrsteRacuna;
	}

	public int getSifVrsteRacuna() {
		return sifVrsteRacuna;
	}
	
	public String getNazVrsteRacuna() {
		return nazVrsteRacuna;
	}
	
}
