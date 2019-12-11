package hr.fer.opp.bugbusters.dao.sql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.opp.bugbusters.dao.DAO;
import hr.fer.opp.bugbusters.dao.model.Kartica;
import hr.fer.opp.bugbusters.dao.model.KorisnickiRacun;
import hr.fer.opp.bugbusters.dao.model.Kredit;
import hr.fer.opp.bugbusters.dao.model.Mjesto;
import hr.fer.opp.bugbusters.dao.model.Profil;
import hr.fer.opp.bugbusters.dao.model.Racun;
import hr.fer.opp.bugbusters.dao.model.RazinaOvlasti;
import hr.fer.opp.bugbusters.dao.model.Transakcija;
import hr.fer.opp.bugbusters.dao.model.VrstaKartice;
import hr.fer.opp.bugbusters.dao.model.VrstaKredita;
import hr.fer.opp.bugbusters.dao.model.VrstaRacuna;
import hr.fer.opp.bugbusters.dao.model.Zupanija;

/**
 * This is the implementation of the DAO subsystem using SQL technology. 
 * This concrete implementation expects that link is available via the {@link SQLConnectionProvider} 
 * class, which means that someone, before execution gets to this point, would have to set it up there. 
 * In web applications, a typical solution is to configure a single filter that intercepts the 
 * servlet calls and before that insert a connection from the connection-pool here and after 
 * the processing finishes it is removed.
 *  
 * @author Matija
 */
public class SQLDAO implements DAO {
	
	@Override
	public KorisnickiRacun getKorisnickiRacun(String korisnickoIme) {
		
		Objects.requireNonNull(korisnickoIme);
		
		KorisnickiRacun racun = null;
		
		String lozinka;
		String oib;
		int sifRazOvlasti;
		boolean promjenaLozinke;
		
		Connection con = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM korisnickiRacun WHERE korisnickoIme='" + korisnickoIme + "'");
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				lozinka = rs.getString(2);
				oib = rs.getString(3);
				sifRazOvlasti = rs.getInt(4);
				promjenaLozinke = rs.getBoolean(5);
				racun = new KorisnickiRacun(korisnickoIme, lozinka, oib, sifRazOvlasti, promjenaLozinke);
			}
			
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}

		return racun;
		
	}
	
	@Override
	public Profil getProfil(String oib) {
		
		Objects.requireNonNull(oib);

		Profil profil = null;
		
		String ime = null, prezime = null, adresa = null, email = null, slika = null;
		int pbr = 0;
		Date datRod = null;
		
		Connection con = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM profil WHERE oib='" + oib + "'");
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				ime = rs.getString(2);
				prezime = rs.getString(3);
				adresa = rs.getString(4);
				pbr = rs.getInt(5);
				datRod = rs.getDate(6);
				email = rs.getString(7);
				slika = rs.getString(8);
				profil = new Profil(ime, prezime, oib, adresa, pbr, datRod, email, slika);
			}
			
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return profil;
		
	}
	
	@Override
	public Profil getProfilByKorisnickoIme(String korisnickoIme) {
		
		Objects.requireNonNull(korisnickoIme);

		Profil profil = null;
		
		String oib = null, ime = null, prezime = null, adresa = null, email = null, slika = null;
		int pbr = 0;
		Date datRod = null;
		
		Connection con = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT profil.* FROM korisnickiracun NATURAL JOIN profil WHERE korisnickoIme = '" + korisnickoIme + "'");
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				oib = rs.getString(1);
				ime = rs.getString(2);
				prezime = rs.getString(3);
				adresa = rs.getString(4);
				pbr = rs.getInt(5);
				datRod = rs.getDate(6);
				email = rs.getString(7);
				slika = rs.getString(8);
				profil = new Profil(ime, prezime, oib, adresa, pbr, datRod, email, slika);
			}
			
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return profil;
		
	}

	@Override
	public Mjesto getMjesto(int pbr) {
		
		Mjesto mjesto = null;
		
		String nazMjesto;
		int sifZupanija;
		
		Connection con = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM mjesto WHERE pbr = " + pbr);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				nazMjesto = rs.getString(2);
				sifZupanija = rs.getInt(3);
				mjesto = new Mjesto(pbr, nazMjesto, sifZupanija);
			}
			
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return mjesto;
	}
	
	@Override
	public Zupanija getZupanija(int sifZupanija) {
		
		Zupanija zupanija = null;
		
		String nazZupanija;
		
		Connection con = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM zupanija WHERE sifzupanija = " + sifZupanija);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				nazZupanija = rs.getString(2);
				zupanija = new Zupanija(sifZupanija, nazZupanija);
			}

			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return zupanija;
	}

	@Override
	public boolean updatePassword(String korisnickoIme, String newPasswordHash) {
		
		Objects.requireNonNull(korisnickoIme);
		Objects.requireNonNull(newPasswordHash);
		
		int result;
		Connection con = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("UPDATE korisnickiracun SET lozinka='" + newPasswordHash + "', promjenaLozinke = false WHERE korisnickoIme='" + korisnickoIme + "'");
			result = pst.executeUpdate();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return result!=0;
		
	}

	@Override
	public RazinaOvlasti getRazinaOvlasti(int sifRazOvlasti) {
		RazinaOvlasti razinaOvlasti = null;
		
		String nazRazinaOvlasti;
		
		Connection con = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM razOvlasti WHERE sifRazOvlasti = " + sifRazOvlasti);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				nazRazinaOvlasti = rs.getString(2);
				razinaOvlasti = new RazinaOvlasti(sifRazOvlasti, nazRazinaOvlasti);
			}

			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return razinaOvlasti;
	}

	@Override
	public List<Kartica> getKarticaForOib(String oib) {
		Objects.requireNonNull(oib);

		List<Kartica> kartice = new ArrayList<>();
		
		Connection con = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM kartica WHERE oib = '" + oib + "'");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				kartice.add(new Kartica(rs.getString(1), rs.getString(2), oib, 
						rs.getInt(4), rs.getBigDecimal(5), rs.getDate(6), rs.getBigDecimal(7), rs.getBigDecimal(8), 
						rs.getInt(9)));
			}
			
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return kartice;
	}

	@Override
	public List<Kartica> getKarticaForBrRacun(String brRacun) {
		Objects.requireNonNull(brRacun);

		List<Kartica> kartice = new ArrayList<>();
		
		Connection con = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM kartica WHERE brRacun = '" + brRacun + "'");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				kartice.add(new Kartica(rs.getString(1), brRacun, rs.getString(3), 
						rs.getInt(4), rs.getBigDecimal(5), rs.getDate(6), rs.getBigDecimal(7), rs.getBigDecimal(8), 
						rs.getInt(9)));
			}
			
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return kartice;
	}

	@Override
	public List<Kredit> getKreditForOib(String oib) {
		Objects.requireNonNull(oib);

		List<Kredit> krediti = new ArrayList<>();
		
		Connection con = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM kredit WHERE oib = '" + oib + "'");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				krediti.add(new Kredit(rs.getInt(1), oib, rs.getBigDecimal(3), rs.getInt(4), rs.getDate(5), rs.getInt(6), rs.getInt(7), rs.getBigDecimal(8)));
			}
			
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return krediti;
	}
	
	@Override
	public List<Racun> getRacunForOib(String oib) {
		Objects.requireNonNull(oib);

		List<Racun> racuni = new ArrayList<>();
		
		Connection con = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM racun WHERE oib = '" + oib + "'");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				racuni.add(new Racun(rs.getString(1), oib, rs.getDate(3), rs.getBigDecimal(4), rs.getInt(5), rs.getBigDecimal(6), rs.getBigDecimal(7), rs.getDate(8)));
			}
			
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return racuni;
	}

	@Override
	public Racun getRacunForBrRacun(String brRacun) {
		Objects.requireNonNull(brRacun);

		Racun racun = null;
		
		Connection con = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM racun WHERE brRacun = '" + brRacun + "'");
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				racun = new Racun(brRacun, rs.getString(2), rs.getDate(3), rs.getBigDecimal(4), rs.getInt(5), rs.getBigDecimal(6), rs.getBigDecimal(7), rs.getDate(8));
			}
			
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return racun;
	}

	@Override
	public List<Transakcija> getTransakcijaForBrRacunTerecenja(String brRacunTerecenja) {
		Objects.requireNonNull(brRacunTerecenja);

		List<Transakcija> transakcije = new ArrayList<>();
		
		Connection con = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM transakcija WHERE racTerecenja = '" + brRacunTerecenja + "'");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				transakcije.add(new Transakcija(rs.getInt(1), brRacunTerecenja, rs.getString(3), rs.getBigDecimal(4), rs.getDate(5)));
			}
			
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return transakcije;
	}

	@Override
	public VrstaKartice getVrstaKartice(int sifVrstaKartice) {
		VrstaKartice vrstaKartice = null;
		
		String nazVrstaKartice;
		
		Connection con = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM vrstaKartice WHERE sifVrstaKartice = " + sifVrstaKartice);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				nazVrstaKartice = rs.getString(2);
				vrstaKartice = new VrstaKartice(sifVrstaKartice, nazVrstaKartice);
			}

			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return vrstaKartice;
	}

	@Override
	public VrstaKredita getVrstaKredita(int sifVrstaKredita) {
		VrstaKredita vrstaKredita = null;
		
		String nazVrstaKredita;
		BigDecimal kamStopa;
		
		Connection con = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM vrstaKredita WHERE sifVrsteKredita = " + sifVrstaKredita);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				nazVrstaKredita = rs.getString(2);
				kamStopa = rs.getBigDecimal(3);
				vrstaKredita = new VrstaKredita(sifVrstaKredita, nazVrstaKredita, kamStopa);
			}

			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return vrstaKredita;
	}

	@Override
	public VrstaRacuna getVrstaRacuna(int sifVrstaRacuna) {
		VrstaRacuna vrstaRacuna = null;
		
		String nazVrstaRacuna;
		
		Connection con = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM vrstaRacuna WHERE sifVrsteRacuna = " + sifVrstaRacuna);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				nazVrstaRacuna = rs.getString(2);
				vrstaRacuna = new VrstaRacuna(sifVrstaRacuna, nazVrstaRacuna);
			}

			rs.close();
			pst.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getCause() + " : " + ex.getMessage());
		}
		
		return vrstaRacuna;
	}

}