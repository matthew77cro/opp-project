package hr.fer.opp.bugbusters.dao.model;

public class Mjesto {

	private int pbr;
	private String nazMjesto;
	private int sifraZupanija;
	
	public Mjesto(int pbr, String nazMjesto, int sifraZupanija) {
		this.pbr = pbr;
		this.nazMjesto = nazMjesto;
		this.sifraZupanija = sifraZupanija;
	}
	
	public int getPbr() {
		return pbr;
	}
	
	public String getNazMjesto() {
		return nazMjesto;
	}
	
	public int getSifraZupanija() {
		return sifraZupanija;
	}
	
}
