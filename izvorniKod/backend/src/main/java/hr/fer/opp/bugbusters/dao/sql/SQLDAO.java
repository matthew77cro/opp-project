package hr.fer.opp.bugbusters.dao.sql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import hr.fer.opp.bugbusters.dao.DAO;
import hr.fer.opp.bugbusters.dao.DAOProvider;
import hr.fer.opp.bugbusters.dao.model.Kartica;
import hr.fer.opp.bugbusters.dao.model.KorisnickiRacun;
import hr.fer.opp.bugbusters.dao.model.Kredit;
import hr.fer.opp.bugbusters.dao.model.Mjesto;
import hr.fer.opp.bugbusters.dao.model.Profil;
import hr.fer.opp.bugbusters.dao.model.Racun;
import hr.fer.opp.bugbusters.dao.model.RazinaOvlasti;
import hr.fer.opp.bugbusters.dao.model.RegistracijaKlijenta;
import hr.fer.opp.bugbusters.dao.model.Transakcija;
import hr.fer.opp.bugbusters.dao.model.VrstaKartice;
import hr.fer.opp.bugbusters.dao.model.VrstaKredita;
import hr.fer.opp.bugbusters.dao.model.VrstaRacuna;
import hr.fer.opp.bugbusters.dao.model.ZahtjevKartica;
import hr.fer.opp.bugbusters.dao.model.ZahtjevKredit;
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
	
	private List<Map<String, Object>> executeQuery(String query) {
		
		Objects.requireNonNull(query);
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		Connection con = SQLConnectionProvider.getConnection();
		
		try {
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			while (rs.next()){
				Map<String, Object> row = new HashMap<>(columns);
				for(int i=1; i<=columns; i++){ 
					row.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(row);
			}
			
			rs.close();
			pst.close();
		} catch (SQLException ex) {
			System.out.println("SQLException : " + ex.getMessage());
			System.out.println(query);
			System.out.println("---------------------");
		}
		
		return list;
		
	}
	
	private int executeUpdate(String update) {
		Objects.requireNonNull(update);
		
		int updateResult = 0;
		
		Connection con = SQLConnectionProvider.getConnection();
		try {
			Statement st = con.createStatement();
			updateResult = st.executeUpdate(update);
			st.close();
		} catch (SQLException ex) {
			System.out.println("SQLException : " + ex.getMessage());
			System.out.println(update);
			System.out.println("---------------------");
		}
		
		return updateResult;
	}
	
	@Override
	public Profil getProfil(String oib) {
		
		Objects.requireNonNull(oib);

		Profil profil = null;
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM profil WHERE oib='" + oib + "'");
		
		for(var l : list) {
			profil = new Profil((String)l.get("ime"), (String)l.get("prezime"), oib, 
					(String)l.get("adresa"), (Integer)l.get("pbr"), (Date)l.get("datrod"), 
					(String)l.get("email"), (String)l.get("slika"));
		}
		
		return profil;
		
	}
	
	@Override
	public List<Profil> getAllProfil() {

		List<Profil> profili = new ArrayList<>();
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM profil");
		
		for(var l : list) {
			profili.add(new Profil((String)l.get("ime"), (String)l.get("prezime"), (String)l.get("oib"), 
					(String)l.get("adresa"), (Integer)l.get("pbr"), (Date)l.get("datRod"), 
					(String)l.get("email"), (String)l.get("slika")));
		}
		
		return profili;
		
	}
	
	@Override
	public boolean addProfil(Profil profil) {
		Objects.requireNonNull(profil);
		return executeUpdate(
				String.format("INSERT INTO profil (oib, ime, prezime, adresa, pbr, datRod, email, slika) "
				+ "VALUES ('%s', '%s', '%s', '%s', %d, '%s', '%s', '%s')", 
				profil.getOib(), profil.getIme(), profil.getPrezime(), profil.getAdresa(), 
				profil.getPbr(), profil.getDatRod().toString(), 
				profil.getEmail(), profil.getSlika()))
				!= 0;
	}
	
	@Override
	public boolean removeProfil(String oib) {
		Objects.requireNonNull(oib);
		return executeUpdate("DELETE FROM profil WHERE oib = '" + oib + "'") != 0;
	}
	
	@Override
	public boolean updateProfil(String oib, Profil newData) {
		Objects.requireNonNull(oib);
		Objects.requireNonNull(newData);
		return executeUpdate(
				String.format("UPDATE profil SET oib='%s', ime='%s', prezime='%s', adresa='%s', pbr=%d, "
						+ "datRod='%s', email='%s', slika='%s' "
						+ "WHERE oib = '%s'", 
						newData.getOib(), newData.getIme(), newData.getPrezime(), newData.getAdresa(),
						newData.getPbr(), newData.getDatRod(), newData.getEmail(), newData.getSlika(), oib))
						!= 0;
	}
	
	@Override
	public Mjesto getMjesto(int pbr) {
		
		Mjesto mjesto = null;
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM mjesto WHERE pbr = " + pbr);
		
		for(var l : list) {
			mjesto = new Mjesto(pbr, (String)l.get("nazmjesto"), (Integer)l.get("sifzupanija"));
		}
		
		return mjesto;
	}
	
	@Override
	public Zupanija getZupanija(int sifZupanija) {
		
		Zupanija zupanija = null;
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM zupanija WHERE sifzupanija = " + sifZupanija);
		
		for(var l : list) {
			zupanija = new Zupanija(sifZupanija, (String)l.get("nazzupanija"));
		}
		
		return zupanija;
	}
	
	@Override
	public KorisnickiRacun getKorisnickiRacun(String korisnickoIme) {
		
		Objects.requireNonNull(korisnickoIme);
		
		KorisnickiRacun racun = null;
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM korisnickiRacun WHERE korisnickoIme='" + korisnickoIme + "'");
			
		for(var l : list) {
			racun = new KorisnickiRacun(korisnickoIme, (String)l.get("lozinka"), (String)l.get("oib"), 
					(Integer)l.get("sifrazovlasti"), (Boolean)l.get("promjenalozinke"));
		}

		return racun;
		
	}
	
	public List<KorisnickiRacun> getKorisnickiRacunByOib(String oib) {
		
		Objects.requireNonNull(oib);
		
		List<KorisnickiRacun> racuni = new ArrayList<>();
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM korisnickiRacun WHERE oib='" + oib + "'");
				
		for(var l : list) {
			racuni.add(new KorisnickiRacun((String)l.get("korisnickoime"), (String)l.get("lozinka"), oib, 
					(Integer)l.get("sifrazovlasti"), (Boolean)l.get("promjenalozinke")));
		}
		
		return racuni;
		
	}
	
	public boolean addKorisnickiRacun(KorisnickiRacun korisnickiRacun) {
		Objects.requireNonNull(korisnickiRacun);
		return executeUpdate(
				String.format("INSERT INTO korisnickiRacun (korisnickoIme, lozinka, oib, sifRazOvlasti, promjenaLozinke) "
				+ "VALUES ('%s', '%s', '%s', %d, '%b')", 
				korisnickiRacun.getKorisnickoIme(), korisnickiRacun.getLozinka(), korisnickiRacun.getOib(),
				korisnickiRacun.getSifRazOvlasti(), 
				korisnickiRacun.isPromjenaLozinke()))
				!= 0;		
	}
	
	@Override
	public boolean removeKorisnickiRacun(String korisnickoIme) {
		Objects.requireNonNull(korisnickoIme);
		return executeUpdate("DELETE FROM korisnickiRacun WHERE korisnickoIme = '" + korisnickoIme + "'") != 0;
	}
	
	@Override
	public boolean updateKorisinckiRacun(String korisnickoIme, KorisnickiRacun newData) {
		Objects.requireNonNull(korisnickoIme);
		Objects.requireNonNull(newData);
		return executeUpdate(
				String.format("UPDATE korisnickiRacun SET korisnickoIme='%s', lozinka='%s', oib='%s', "
						+ "sifRazOvlasti=%d, promjenaLozinke='%b' "
						+ "WHERE korisnickoIme = '%s'", 
						newData.getKorisnickoIme(), newData.getLozinka(), newData.getOib(),
						newData.getSifRazOvlasti(), newData.isPromjenaLozinke(), korisnickoIme))
						!= 0;
	}
	
	@Override
	public Racun getRacun(String brRacun) {
		Objects.requireNonNull(brRacun);

		Racun racun = null;
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM racun WHERE brRacun = '" + brRacun + "'");
				
		for(var l : list) {
			racun = new Racun(brRacun, (String)l.get("oib"), (Date)l.get("datotvaranja"), 
					(BigDecimal)l.get("stanje"), (Integer)l.get("sifvrsteracuna"), 
					(BigDecimal)l.get("prekoracenje"), (BigDecimal)l.get("kamstopa"), 
					(Date)l.get("datzatvaranja"));
		}
		
		return racun;
	}
	
	@Override
	public List<Racun> getRacunByOib(String oib) {
		Objects.requireNonNull(oib);

		List<Racun> racuni = new ArrayList<>();

		List<Map<String, Object>> list = executeQuery("SELECT * FROM racun WHERE oib = '" + oib + "'");
		
		for(var l : list) {
			racuni.add(new Racun((String)l.get("brracun"), oib, (Date)l.get("datotvaranja"), 
					(BigDecimal)l.get("stanje"), (Integer)l.get("sifvrsteracuna"), 
					(BigDecimal)l.get("prekoracenje"), (BigDecimal)l.get("kamstopa"), 
					(Date)l.get("datzatvaranja")));
		}
		
		return racuni;
	}
	
	@Override
	public boolean addRacun(Racun racun) {
		Objects.requireNonNull(racun);
		return executeUpdate(
				String.format("INSERT INTO racun (brRacun, oib, datOtvaranja, stanje, sifVrsteRacuna, prekoracenje, kamStopa, datZatvaranja) "
				+ "VALUES ('%s', '%s', '%s', %s, %d, %s, %s, " + (racun.getDatZatvaranja() == null ? "NULL" : "'" + racun.getDatZatvaranja().toString() + "'") + ")", 
				racun.getBrRacun(), racun.getOib(), racun.getDatOtvaranja(), 
				racun.getStanje().toString(), racun.getSifVrsteRacuna(), 
				racun.getPrekoracenje().toString(), racun.getKamStopa().toString()))
				!= 0;
	}
	
	@Override
	public boolean removeRacun(String brRacun) {
		Objects.requireNonNull(brRacun);
		return executeUpdate("DELETE FROM racun WHERE brRacun = '" + brRacun + "'") != 0;
	}
	
	@Override
	public boolean updateRacun(String brRacun, Racun newData) {
		Objects.requireNonNull(brRacun);
		Objects.requireNonNull(newData);
		return executeUpdate(
				String.format("UPDATE racun SET brRacun='%s', oib='%s', datOtvaranja='%s', stanje=%s, "
						+ "sifVrsteRacuna=%d, prekoracenje=%s, kamStopa=%s, datZatvaranja=" + 
						(newData.getDatZatvaranja() == null ? "NULL" : "'" + newData.getDatZatvaranja().toString() + "'")
						+ " WHERE brRacun = '%s'", 
						newData.getBrRacun(), newData.getOib(), newData.getDatOtvaranja().toString(), 
						newData.getStanje().toString(), newData.getSifVrsteRacuna(), 
						newData.getPrekoracenje().toString(), newData.getKamStopa().toString(), brRacun))
						!= 0;
	}
	
	@Override
	public Kartica getKartica(String brKartica) {
		
		Objects.requireNonNull(brKartica);

		Kartica kartica = null;
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM kartica WHERE brKartica = '" + brKartica + "'");
				
		for(var l : list) {
			kartica = new Kartica(brKartica, (String)l.get("brracun"), (String)l.get("oib"), 
					(Integer)l.get("sifvrstakartice"), (BigDecimal)l.get("stanje"), 
					(Date)l.get("valjanost"), (BigDecimal)l.get("limitkartice"), 
					(BigDecimal)l.get("kamstopa"), (Integer)l.get("datrate"));
		}
		
		return kartica;
		
	}

	@Override
	public List<Kartica> getKarticaByOib(String oib) {
		
		Objects.requireNonNull(oib);

		List<Kartica> kartice = new ArrayList<>();

		List<Map<String, Object>> list = executeQuery("SELECT * FROM kartica WHERE oib = '" + oib + "'");
		
		for(var l : list) {
			kartice.add(new Kartica((String)l.get("brkartica"), (String)l.get("brracun"), oib, 
					(Integer)l.get("sifvrstakartice"), (BigDecimal)l.get("stanje"), 
					(Date)l.get("valjanost"), (BigDecimal)l.get("limitkartice"), 
					(BigDecimal)l.get("kamstopa"), (Integer)l.get("datrate")));
		}
		
		return kartice;
		
	}

	@Override
	public List<Kartica> getKarticaByBrRacun(String brRacun) {
		
		Objects.requireNonNull(brRacun);

		List<Kartica> kartice = new ArrayList<>();
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM kartica WHERE brRacun = '" + brRacun + "'");
				
		for(var l : list) {
			kartice.add(new Kartica((String)l.get("brkartica"), brRacun, (String)l.get("oib"), 
					(Integer)l.get("sifvrstakartice"), (BigDecimal)l.get("stanje"), 
					(Date)l.get("valjanost"), (BigDecimal)l.get("limitkartice"), 
					(BigDecimal)l.get("kamstopa"), (Integer)l.get("datrate")));
		}
		
		return kartice;
		
	}
	
	@Override
	public boolean addKartica(Kartica kartica) {
		Objects.requireNonNull(kartica);
		return executeUpdate(
				String.format("INSERT INTO kartica (brKartica, brRacun, oib, stanje, sifVrstaKartice, valjanost, limitKartice, kamStopa, datRate) "
				+ "VALUES ('%s', " + 
				(kartica.getBrRacun() == null ? "NULL" : "'" + kartica.getBrRacun() + "'")
				+ ", " + 
				(kartica.getOib() == null ? "NULL" : "'" + kartica.getOib() + "'")
				+ ", " + 
				(kartica.getStanje() == null ? "NULL" : kartica.getStanje())
				+ ", " +
				((kartica.getSifVrstaKartice() == null || kartica.getSifVrstaKartice() == 0) ? "NULL" : kartica.getSifVrstaKartice())
				+ ", '%s', " + 
				(kartica.getLimitKartice() == null ? "NULL" : kartica.getLimitKartice())
				+ ", " + 
				(kartica.getKamStopa() == null ? "NULL" : kartica.getKamStopa())
				+ ", " + 
				((kartica.getDatRate() == null || kartica.getDatRate() == 0) ? "NULL" : kartica.getDatRate())
				+ ")", 
				kartica.getBrKartica(),
				kartica.getValjanost()))
				!= 0;
	}
	
	@Override
	public boolean removeKartica(String brKartica) {
		Objects.requireNonNull(brKartica);
		return executeUpdate("DELETE FROM kartica WHERE brKartica = '" + brKartica + "'") != 0;
	}

	@Override
	public boolean updateKartica(String brKartica, Kartica newData) {
		Objects.requireNonNull(brKartica);
		Objects.requireNonNull(newData);
		return executeUpdate(
				String.format("UPDATE kartica SET brKartica='%s', brRacun=" + 
						(newData.getBrRacun() == null ? "NULL" : "'" + newData.getBrRacun() + "'")	
						+ ", oib=" + 
						(newData.getOib() == null ? "NULL" : "'" + newData.getOib() + "'")	
						+ ", sifVrstaKartice=" +
						(newData.getSifVrstaKartice() == 0 ? "NULL" : newData.getSifVrstaKartice())
						+ ", "
						+ "stanje=" +
						(newData.getStanje() == null ? "NULL" : newData.getStanje())
						+ ", valjanost='%s', limitKartice=" +
						(newData.getLimitKartice() == null ? "NULL" : newData.getLimitKartice())
						+ ", kamStopa=" +
						(newData.getKamStopa() == null ? "NULL" : newData.getKamStopa())
						+ ", datRate=" +
						(newData.getDatRate() == 0 ? "NULL" : newData.getDatRate())
						+ " WHERE brKartica = '%s'", 
						newData.getBrKartica(),	newData.getValjanost().toString(), brKartica))
						!= 0;
	}
	
	@Override
	public Kredit getKredit(int brKredit) {

		Kredit kredit = null;
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM kredit WHERE brKredit = '" + brKredit + "'");
				
		for(var l : list) {
			kredit = new Kredit(brKredit, (String)l.get("oib"), (BigDecimal)l.get("iznos"), 
					(Integer)l.get("sifvrstekredita"), (Date)l.get("datugovaranja"), (Integer)l.get("periodotplate"), 
					(Integer)l.get("datrate"), (BigDecimal)l.get("preostalodugovanje"));
		}
		
		return kredit;
		
	}

	@Override
	public List<Kredit> getKreditByOib(String oib) {
		
		Objects.requireNonNull(oib);

		List<Kredit> krediti = new ArrayList<>();
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM kredit WHERE oib = '" + oib + "'");
			
		for(var l : list) {
			krediti.add(new Kredit((Integer)l.get("brkredit"), oib, (BigDecimal)l.get("iznos"), 
					(Integer)l.get("sifvrstekredita"), (Date)l.get("datugovaranja"), (Integer)l.get("periodotplate"), 
					(Integer)l.get("datrate"), (BigDecimal)l.get("preostalodugovanje")));
		}
		
		return krediti;
		
	}
	
	@Override
	public boolean addKredit(Kredit kredit) {
		Objects.requireNonNull(kredit);
		return executeUpdate(
				String.format("INSERT INTO kredit (oib, iznos, sifVrsteKredita, datUgovaranja, periodOtplate, datRate, preostaloDugovanje) "
				+ "VALUES ('%s', %s, %d, '%s', %d, %d, %s)", 
				kredit.getOib(), kredit.getIznos(),
				kredit.getSifVrsteKredita(), kredit.getDatUgovaranja(), kredit.getPeriodOtplate(),
				kredit.getDatRate(), kredit.getPreostaloDugovanje()))
				!= 0;
	}
	
	@Override
	public boolean removeKredit(int brKredit) {
		Objects.requireNonNull(brKredit);
		return executeUpdate("DELETE FROM kredit WHERE brKredit = '" + brKredit + "'") != 0;
	}
	
	@Override
	public boolean updateKredit(int brKredit, Kredit newData) {
		Objects.requireNonNull(brKredit);
		Objects.requireNonNull(newData);
		return executeUpdate(
				String.format("UPDATE kredit SET brKredit=%d, oib='%s', iznos=%s, sifVrsteKredita=%d, "
						+ "datUgovaranja='%s', periodOtplate=%d, datRate=%d, preostaloDugovanje=%s "
						+ "WHERE brKredit = %d", 
						newData.getBrKredit(), newData.getOib(), newData.getIznos(),
						newData.getSifVrsteKredita(), newData.getDatUgovaranja(), newData.getPeriodOtplate(),
						newData.getDatRate(), newData.getPreostaloDugovanje(), brKredit))
						!= 0;
	}
	
	@Override
	public Transakcija getTransakcija(int brTransakcija) {

		Transakcija transakcija = null;
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM transakcija WHERE brTransakcija = " + brTransakcija);
					
		for(var l : list) {
			transakcija = new Transakcija(brTransakcija, (String)l.get("racterecenja"), 
					(String)l.get("racodobrenja"), (BigDecimal)l.get("iznos"), 
					(Date)l.get("dattransakcije"));
		}
		
		return transakcija;
		
	}
	
	@Override
	public List<Transakcija> getTransakcijaByBrRacunTerecenja(String racTerecenja) {
		
		Objects.requireNonNull(racTerecenja);

		List<Transakcija> transakcije = new ArrayList<>();
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM transakcija WHERE racTerecenja = '" + racTerecenja + "'");
					
		for(var l : list) {
			transakcije.add(new Transakcija((Integer)l.get("brtransakcija"), racTerecenja, 
					(String)l.get("racodobrenja"), (BigDecimal)l.get("iznos"), (Date)l.get("dattransakcije")));
		}
		
		return transakcije;
		
	}
	
	@Override
	public List<Transakcija> getTransakcijaByBrRacunOdobrenja(String racOdobrenja) {
		
		Objects.requireNonNull(racOdobrenja);

		List<Transakcija> transakcije = new ArrayList<>();
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM transakcija WHERE racOdobrenja = '" + racOdobrenja + "'");
					
		for(var l : list) {
			transakcije.add(new Transakcija((Integer)l.get("brtransakcija"), (String)l.get("racterecenja"), 
					racOdobrenja, (BigDecimal)l.get("iznos"), (Date)l.get("dattransakcije")));
		}
		
		return transakcije;
		
	}
	
	@Override
	public List<Transakcija> getTransakcijaByBrRacun(String brRacun) {
		
		List<Transakcija> transakcije = new ArrayList<>();

		DAOProvider.getDao().getTransakcijaByBrRacunTerecenja(brRacun)
			.forEach((t) -> 
				transakcije.add(new Transakcija(t.getBrTransakcija(), t.getRacTerecenja(), t.getRacOdobrenja(), t.getIznos().negate(), t.getDatTransakcije()))
			);
		
		DAOProvider.getDao().getTransakcijaByBrRacunOdobrenja(brRacun)
			.forEach((t) -> 
				transakcije.add(new Transakcija(t.getBrTransakcija(), t.getRacOdobrenja(), t.getRacTerecenja(), t.getIznos(), t.getDatTransakcije()))
			);
		
		transakcije.sort((c1, c2) -> {
			int result = c2.getDatTransakcije().compareTo(c1.getDatTransakcije());
			if(result==0) result = Integer.compare(c2.getBrTransakcija(), c1.getBrTransakcija());
			return result;
		});
		
		return transakcije;
		
	}
	
	@Override
	public List<Transakcija> getTransakcijeByOib(String oib) {
		
		List<Racun> racuni = DAOProvider.getDao().getRacunByOib(oib);
		List<Transakcija> transakcije = new ArrayList<>();
		
		racuni.stream()
			.map((r) -> DAOProvider.getDao().getTransakcijaByBrRacunTerecenja(r.getBrRacun()))
			.forEach((tList) -> 
				tList.forEach((t) -> 
					transakcije.add(new Transakcija(t.getBrTransakcija(), t.getRacTerecenja(), t.getRacOdobrenja(), t.getIznos().negate(), t.getDatTransakcije()))
				)
			);
		racuni.stream()
		.map((r) -> DAOProvider.getDao().getTransakcijaByBrRacunOdobrenja(r.getBrRacun()))
		.forEach((tList) -> 
			tList.forEach((t) -> 
				transakcije.add(new Transakcija(t.getBrTransakcija(), t.getRacOdobrenja(), t.getRacTerecenja(), t.getIznos(), t.getDatTransakcije()))
			)
		);
		
		transakcije.sort((c1, c2) -> {
			int result = c2.getDatTransakcije().compareTo(c1.getDatTransakcije());
			if(result==0) result = Integer.compare(c2.getBrTransakcija(), c1.getBrTransakcija());
			return result;
		});
		
		return transakcije;
		
	}

	@Override
	public boolean addTransakcija(Transakcija transakcija) {
		Objects.requireNonNull(transakcija);
		return executeUpdate(
				String.format("INSERT INTO transakcija (racTerecenja, racOdobrenja, iznos, datTransakcije) "
				+ "VALUES ('%s', '%s', %s, '%s')", 
				transakcija.getRacTerecenja(), transakcija.getRacOdobrenja(),
				transakcija.getIznos().toString(), transakcija.getDatTransakcije().toString()))
				!= 0;
	}
	
	@Override
	public RazinaOvlasti getRazinaOvlasti(int sifRazOvlasti) {
		
		RazinaOvlasti razinaOvlasti = null;
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM razOvlasti WHERE sifRazOvlasti = " + sifRazOvlasti);
		
		for(var l : list) {
			razinaOvlasti = new RazinaOvlasti(sifRazOvlasti, (String)l.get("nazrazovlasti"));
		}
		
		return razinaOvlasti;
		
	}
	
	@Override
	public List<RazinaOvlasti> getAllRazinaOvlasti() {
		
		List<RazinaOvlasti> razinaOvlasti = new ArrayList<>();
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM razOvlasti");
		
		for(var l : list) {
			razinaOvlasti.add(new RazinaOvlasti((Integer)l.get("sifrazovlasti"), (String)l.get("nazrazovlasti")));
		}
		
		return razinaOvlasti;
		
	}
	
	@Override
	public boolean addRazinaOvlasti(RazinaOvlasti razinaOvlasti) {
		Objects.requireNonNull(razinaOvlasti);
		return executeUpdate(
				String.format("INSERT INTO razOvlasti (nazRazOvlasti) "
				+ "VALUES ('%s')", 
				razinaOvlasti.getNazivRazOvlasti()))
				!= 0;
	}

	@Override
	public VrstaRacuna getVrstaRacuna(int sifVrstaRacuna) {
		
		VrstaRacuna VrstaRacuna = null;
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM VrstaRacuna WHERE sifVrsteRacuna = " + sifVrstaRacuna);
		
		for(var l : list) {
			VrstaRacuna = new VrstaRacuna(sifVrstaRacuna, (String)l.get("nazvrsteracuna"));
		}
		
		return VrstaRacuna;
		
	}
	
	@Override
	public List<VrstaRacuna> getAllVrstaRacuna() {
		
		List<VrstaRacuna> vrstaRacuna = new ArrayList<>();
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM VrstaRacuna");
		
		for(var l : list) {
			vrstaRacuna.add(new VrstaRacuna((Integer)l.get("sifvrsteracuna"), (String)l.get("nazvrsteracuna")));
		}
		
		return vrstaRacuna;
		
	}
	
	@Override
	public boolean addVrstaRacuna(VrstaRacuna vrstaRacuna) {
		Objects.requireNonNull(vrstaRacuna);
		return executeUpdate(
				String.format("INSERT INTO VrstaRacuna (nazVrsteRacuna) "
				+ "VALUES ('%s')", 
				vrstaRacuna.getNazVrsteRacuna()))
				!= 0;
	}

	@Override
	public VrstaKartice getVrstaKartice(int sifVrstaKartice) {
		
		VrstaKartice VrstaKartice = null;
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM VrstaKartice WHERE sifVrstaKartice = " + sifVrstaKartice);
		
		for(var l : list) {
			VrstaKartice = new VrstaKartice(sifVrstaKartice, (String)l.get("nazvrstakartice"));
		}
		
		return VrstaKartice;
		
	}
	
	@Override
	public List<VrstaKartice> getAllVrstaKartice() {

		List<VrstaKartice> vrsteKartice = new ArrayList<>();
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM VrstaKartice");
		
		for(var l : list) {
			vrsteKartice.add(new VrstaKartice((Integer)l.get("sifvrstakartice"), (String)l.get("nazvrstakartice")));
		}
		
		return vrsteKartice;
		
	}
	
	@Override
	public boolean addVrstaKartice(VrstaKartice vrstaKartice) {
		Objects.requireNonNull(vrstaKartice);
		return executeUpdate(
				String.format("INSERT INTO VrstaKartice (nazVrstaKartice) "
				+ "VALUES ('%s')", 
				vrstaKartice.getNazVrsteKartice()))
				!= 0;
	}

	@Override
	public VrstaKredita getVrstaKredita(int sifVrstaKredita) {
		
		VrstaKredita VrstaKredita = null;
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM VrstaKredita WHERE sifVrsteKredita = " + sifVrstaKredita);
		
		for(var l : list) {
			VrstaKredita = new VrstaKredita(sifVrstaKredita, (String)l.get("nazvrstekredita"), 
					(BigDecimal)l.get("kamstopa"));
		}
		
		return VrstaKredita;
		
	}
	
	@Override
	public List<VrstaKredita> getAllVrstaKredita() {
		
		List<VrstaKredita> vrsteKredita = new ArrayList<>();
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM VrstaKredita");
		
		for(var l : list) {
			vrsteKredita.add(new VrstaKredita((Integer)l.get("sifvrstekredita"), (String)l.get("nazvrstekredita"), 
					(BigDecimal)l.get("kamstopa")));
		}
		
		return vrsteKredita;
		
	}
	
	@Override
	public boolean addVrstaKredita(VrstaKredita vrstaKredita) {
		Objects.requireNonNull(vrstaKredita);
		return executeUpdate(
				String.format("INSERT INTO VrstaKredita (nazVrsteKredita, kamStopa) "
				+ "VALUES ('%s', %s)", 
				vrstaKredita.getNazVrsteKredita(), 
				vrstaKredita.getKamStopa().toString()))
				!= 0;
	}
	
	@Override
	public RegistracijaKlijenta getRegistracijaKlijenta(String oib) {
		
		RegistracijaKlijenta regKli = null;
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM registracijaKlijenta WHERE oib = '" + oib + "'");
		
		for(var l : list) {
			regKli = new RegistracijaKlijenta(oib, (String)l.get("privremenikljuc"));
		}
		
		return regKli;
		
	}
	
	@Override
	public boolean addRegistracijaKlijenta(RegistracijaKlijenta registracijaKlijenta) {
		Objects.requireNonNull(registracijaKlijenta);
		return executeUpdate(
				String.format("INSERT INTO registracijaKlijenta (oib, privremeniKljuc) "
				+ "VALUES ('%s', '%s')", 
				registracijaKlijenta.getOib(), registracijaKlijenta.getPrivremeniKljuc()))
				!= 0;
	}
	
	@Override
	public boolean updateRegistracijaKlijenta(String oib, RegistracijaKlijenta newData) {
		Objects.requireNonNull(oib);
		Objects.requireNonNull(newData);
		return executeUpdate(
				String.format("UPDATE registracijaKlijenta SET  oib='%s', privremeniKljuc='%s'"
						+ " WHERE oib = '%s'", 
						newData.getOib(), newData.getPrivremeniKljuc(), oib))
						!= 0;
	}

	@Override
	public boolean removeRegistracijaKlijenta(String oib) {
		Objects.requireNonNull(oib);
		return executeUpdate("DELETE FROM registracijaKlijenta WHERE oib = '" + oib + "'") != 0;
	}
	
	@Override
	public List<ZahtjevKartica> getAllZahtjevKartica() {

		List<ZahtjevKartica> z = new ArrayList<>();
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM zahtjevKartica");
		
		for(var l : list) {
			z.add(new ZahtjevKartica((Integer)l.get("sifzahtjeva"), (String)l.get("oib"), 
					(Integer)l.get("sifvrstakartice"), (Boolean)l.get("odobren")));
		}
		
		return z;
		
	}
	
	@Override
	public ZahtjevKartica getZahtjevKartica(int sifZahtjeva) {
		ZahtjevKartica z = null;
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM zahtjevKartica WHERE sifZahtjeva=" + sifZahtjeva);
		
		for(var l : list) {
			z = new ZahtjevKartica((Integer)l.get("sifzahtjeva"), (String)l.get("oib"), 
					(Integer)l.get("sifvrstakartice"), (Boolean)l.get("odobren"));
		}
		
		return z;
	}

	@Override
	public boolean addZahtjevKartica(ZahtjevKartica zahtjevKartica) {
		Objects.requireNonNull(zahtjevKartica);
		return executeUpdate(
				String.format("INSERT INTO zahtjevKartica (oib, sifVrstaKartice, odobren) "
				+ "VALUES ('%s', %d, '%b')", 
				zahtjevKartica.getOib(), zahtjevKartica.getSifVrstaKartice(),
				zahtjevKartica.isOdobren()))
				!= 0;
	}

	@Override
	public boolean removeZahtjevKartica(int sifZahtjeva) {
		return executeUpdate("DELETE FROM zahtjevKartica WHERE sifZahtjeva = '" + sifZahtjeva + "'") != 0;
	}

	@Override
	public boolean resolveZahtjevKartica(int sifZahtjeva, boolean odobren) {
		return executeUpdate("UPDATE zahtjevKartica SET odobren='" + odobren + "' "
				+ "WHERE sifZahtjeva='" + sifZahtjeva + "'") != 0;
	}

	@Override
	public List<ZahtjevKredit> getAllZahtjevKredit() {

		List<ZahtjevKredit> z = new ArrayList<>();
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM zahtjevKredit");
		
		for(var l : list) {
			z.add(new ZahtjevKredit((Integer)l.get("sifzahtjeva"), (String)l.get("oib"), 
					(BigDecimal)l.get("iznos"), (Integer)l.get("sifvrstekredita"), 
					(Integer)l.get("periodotplate"), (Boolean)l.get("odobren")));
		}
		
		return z;
		
	}

	@Override
	public ZahtjevKredit getZahtjevKredit(int sifZahtjeva) {
		ZahtjevKredit z = null;
		
		List<Map<String, Object>> list = executeQuery("SELECT * FROM zahtjevKredit WHERE sifZahtjeva=" + sifZahtjeva);
		
		for(var l : list) {
			z = new ZahtjevKredit((Integer)l.get("sifzahtjeva"), (String)l.get("oib"), 
					(BigDecimal)l.get("iznos"), (Integer)l.get("sifvrstekredita"), 
					(Integer)l.get("periodotplate"), (Boolean)l.get("odobren"));
		}
		
		return z;
	}

	@Override
	public boolean addZahtjevKredit(ZahtjevKredit zahtjevKredit) {
		Objects.requireNonNull(zahtjevKredit);
		return executeUpdate(
				String.format("INSERT INTO zahtjevKredit (oib, iznos, sifVrsteKredita, periodOtplate, odobren) "
				+ "VALUES ('%s', %s, %d, %d, '%b')", 
				zahtjevKredit.getOib(), zahtjevKredit.getIznos().toString(),
				zahtjevKredit.getSifVrsteKredita(), zahtjevKredit.getPeriodOtplate(), zahtjevKredit.isOdobren()))
				!= 0;
	}

	@Override
	public boolean removeZahtjevKredit(int sifZahtjeva) {
		return executeUpdate("DELETE FROM zahtjevKredit WHERE sifZahtjeva = '" + sifZahtjeva + "'") != 0;
	}

	@Override
	public boolean resolveZahtjevKredit(int sifZahtjeva, boolean odobren) {
		return executeUpdate("UPDATE zahtjevKredit SET odobren='" + odobren + "' "
				+ "WHERE sifZahtjeva='" + sifZahtjeva + "'") != 0;
	}

}