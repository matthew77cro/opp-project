package hr.fer.opp.bugbusters.dao.model;

public class ZahtjevKartica {
	
	private int sifZahtjeva;
	private String oib;
	private int sifVrstaKartice;
	private boolean odobren;
	
	public ZahtjevKartica(int sifZahtjeva, String oib, int sifVrstaKartice, boolean odobren) {
		this.sifZahtjeva = sifZahtjeva;
		this.oib = oib;
		this.sifVrstaKartice = sifVrstaKartice;
		this.odobren = odobren;
	}
	
	public int getSifZahtjeva() {
		return sifZahtjeva;
	}
	
	public String getOib() {
		return oib;
	}
	
	public int getSifVrstaKartice() {
		return sifVrstaKartice;
	}
	
	public boolean isOdobren() {
		return odobren;
	}

}
