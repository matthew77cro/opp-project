package hr.fer.opp.bugbusters.dao.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import hr.fer.opp.bugbusters.dao.DAO;
import hr.fer.opp.bugbusters.dao.model.KorisnickiRacun;
import hr.fer.opp.bugbusters.dao.model.Mjesto;
import hr.fer.opp.bugbusters.dao.model.Profil;
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
	
	//
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
	public boolean changePassword(String korisnickoIme, String newPasswordHash) {
		
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

}