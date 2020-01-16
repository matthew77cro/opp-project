package hr.fer.opp.bugbusters.dao.model;

import java.util.Objects;

public class KorisnickiRacun {
	
	private String korisnickoIme;
	private String lozinka;
	private String oib;
	private Integer sifRazOvlasti;
	private Boolean promjenaLozinke;
	
	public KorisnickiRacun(String korisnickoIme, String lozinka, String oib, Integer sifRazOvlasti,
			Boolean promjenaLozinke) {
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
	
	public Integer getSifRazOvlasti() {
		return sifRazOvlasti;
	}
	
	public Boolean isPromjenaLozinke() {
		return promjenaLozinke;
	}

	@Override
	public int hashCode() {
		return Objects.hash(korisnickoIme);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof KorisnickiRacun))
			return false;
		KorisnickiRacun other = (KorisnickiRacun) obj;
		return Objects.equals(korisnickoIme, other.korisnickoIme);
	}

}
