package hr.fer.opp.bugbusters.dao.model;

public class Zupanija {
	
	private int sifZupanija;
	private String nazZupanija;
	
	public Zupanija(int sifZupanija, String nazZupanija) {
		this.sifZupanija = sifZupanija;
		this.nazZupanija = nazZupanija;
	}

	public int getSifZupanija() {
		return sifZupanija;
	}
	
	public String getNazZupanija() {
		return nazZupanija;
	}

}
