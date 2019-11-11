package hr.fer.opp.bugbusters.dao.model;

public class RazinaOvlasti {
	
	private int sifRazOvlasti;
	private String nazivRazOvlasti;
	
	public RazinaOvlasti(int sifRazOvlasti, String nazivRazOvlasti) {
		this.sifRazOvlasti = sifRazOvlasti;
		this.nazivRazOvlasti = nazivRazOvlasti;
	}

	public int getSifRazOvlasti() {
		return sifRazOvlasti;
	}

	public String getNazivRazOvlasti() {
		return nazivRazOvlasti;
	}

}
