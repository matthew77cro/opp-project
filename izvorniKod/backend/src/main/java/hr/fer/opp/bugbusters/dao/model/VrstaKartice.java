package hr.fer.opp.bugbusters.dao.model;

public class VrstaKartice {
	
	private int sifVrsteKartice;
	private String nazVrsteKartice;
	
	public VrstaKartice(int sifVrstaKartice, String nazVrstaKartice) {
		this.sifVrsteKartice = sifVrstaKartice;
		this.nazVrsteKartice = nazVrstaKartice;
	}

	public int getSifVrsteKartice() {
		return sifVrsteKartice;
	}
	
	public String getNazVrsteKartice() {
		return nazVrsteKartice;
	}

}
