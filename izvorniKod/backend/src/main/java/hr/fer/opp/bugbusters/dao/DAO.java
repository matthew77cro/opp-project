package hr.fer.opp.bugbusters.dao;

import java.util.List;

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
 * Interface toward Data Subsystem.
 * 
 * @author Matija
 *
 */
public interface DAO {
	
	Profil getProfil(String oib);
	List<Profil> getAllProfil();
	boolean addProfil(Profil profil);
	boolean removeProfil(String oib);
	
	Mjesto getMjesto(int pbr);	
	Zupanija getZupanija(int sifZupanija);
	
	KorisnickiRacun getKorisnickiRacun(String korisnickoIme);
	List<KorisnickiRacun> getKorisnickiRacunByOib(String oib);
	boolean addKorisnickiRacun(KorisnickiRacun korisnickiRacun);
	boolean removeKorisnickiRacun(String oib);
	boolean updateKorisinckiRacunPassword(String korisnickoIme, String newPasswordHash);

	Racun getRacun(String brRacun);
	List<Racun> getRacunByOib(String oib);
	boolean addRacun(Racun racun);
	
	Kartica getKartica(String brKartica);
	List<Kartica> getKarticaByOib(String oib);
	List<Kartica> getKarticaByBrRacun(String brRacun);
	boolean addKartica(Kartica kartica);
	
	Kredit getKredit(int brKredit);
	List<Kredit> getKreditByOib(String oib);
	boolean addKredit(Kredit kredit);
	
	Transakcija getTransakcija(int brTransakcija);
	List<Transakcija> getTransakcijaByBrRacunTerecenja(String brRacun);
	List<Transakcija> getTransakcijaByBrRacunOdobrenja(String brRacun);
	boolean addTransakcija(Transakcija transakcija);
	
	RazinaOvlasti getRazinaOvlasti(int sifRazOvlasti);
	VrstaRacuna getVrstaRacuna(int sifVrstaRacuna);
	VrstaKartice getVrstaKartice(int sifVrstaKartice);
	VrstaKredita getVrstaKredita(int sifVrstaKredita);
	
	RegistracijaKlijenta getRegistracijaKlijenta(String oib);
	boolean addRegistracijaKlijenta(RegistracijaKlijenta registracijaKlijenta);	
	
	List<ZahtjevKartica> getAllZahtjevKartica();
	boolean addZahtjevKartica(ZahtjevKartica zahtjevKartica);
	boolean removeZahtjevKartica(int sifZahtjeva);
	boolean resolveZahtjevKartica(int sifZahtjeva, boolean odobren);
	
	List<ZahtjevKredit> getAllZahtjevKredit();
	boolean addZahtjevKredit(ZahtjevKredit zahtjevKredit);
	boolean removeZahtjevKredit(int sifZahtjeva);
	boolean resolveZahtjevKredit(int sifZahtjeva, boolean odobren);
	
}