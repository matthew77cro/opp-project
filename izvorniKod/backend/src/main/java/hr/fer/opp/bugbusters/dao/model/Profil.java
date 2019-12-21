package hr.fer.opp.bugbusters.dao.model;

import java.sql.Date;
import java.util.Objects;

public class Profil {
	
	private String ime;
	private String prezime;
	private String oib;
	private String adresa;
	private Integer pbr;
	private Date datRod;
	private String email;
	private String slika;
	
	public Profil(String ime, String prezime, String oib, String adresa, Integer pbr, Date datRod, String email,
			String slika) {
		this.ime = ime;
		this.prezime = prezime;
		this.oib = oib;
		this.adresa = adresa;
		this.pbr = pbr;
		this.datRod = datRod;
		this.email = email;
		this.slika = slika;
	}

	public String getIme() {
		return ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public String getOib() {
		return oib;
	}

	public String getAdresa() {
		return adresa;
	}

	public Integer getPbr() {
		return pbr;
	}

	public Date getDatRod() {
		return datRod;
	}

	public String getEmail() {
		return email;
	}

	public String getSlika() {
		return slika;
	}

	@Override
	public int hashCode() {
		return Objects.hash(oib);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Profil))
			return false;
		Profil other = (Profil) obj;
		return Objects.equals(oib, other.oib);
	}

}
