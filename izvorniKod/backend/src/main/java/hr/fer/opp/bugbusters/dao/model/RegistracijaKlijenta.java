package hr.fer.opp.bugbusters.dao.model;

public class RegistracijaKlijenta {
	
	private String oib;
	private String privremeniKljuc;
	
	public RegistracijaKlijenta(String oib, String privremeniKljuc) {
		this.oib = oib;
		this.privremeniKljuc = privremeniKljuc;
	}
	
	public String getOib() {
		return oib;
	}
	
	public String getPrivremeniKljuc() {
		return privremeniKljuc;
	}

}
