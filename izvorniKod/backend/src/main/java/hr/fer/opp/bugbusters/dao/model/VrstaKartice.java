package hr.fer.opp.bugbusters.dao.model;

public class VrstaKartice {
	
	private int sifVrstaKartice;
	private String nazVrstaKartice;
	
	public VrstaKartice(int sifVrstaKartice, String nazVrstaKartice) {
		this.sifVrstaKartice = sifVrstaKartice;
		this.nazVrstaKartice = nazVrstaKartice;
	}

	public int getSifVrstaKartice() {
		return sifVrstaKartice;
	}
	
	public String getNazVrstaKartice() {
		return nazVrstaKartice;
	}

}
