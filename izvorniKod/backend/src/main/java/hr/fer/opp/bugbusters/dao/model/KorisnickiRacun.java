package hr.fer.opp.bugbusters.dao.model;

public class KorisnickiRacun {
	
	private String korisnickoIme;
	private String lozinka;
	private String oib;
	private int sifRazOvlasti;
	private boolean promjenaLozinke;
	
	public KorisnickiRacun(String korisnickoIme, String lozinka, String oib, int sifRazOvlasti,
			boolean promjenaLozinke) {
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.oib = oib;
		this.sifRazOvlasti = sifRazOvlasti;
		this.promjenaLozinke = promjenaLozinke;
	}
	
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	
	public String getLozinka() {
		return lozinka;
	}
	
	public String getOib() {
		return oib;
	}
	
	public int getSifRazOvlasti() {
		return sifRazOvlasti;
	}
	
	public boolean isPromjenaLozinke() {
		return promjenaLozinke;
	}

}
