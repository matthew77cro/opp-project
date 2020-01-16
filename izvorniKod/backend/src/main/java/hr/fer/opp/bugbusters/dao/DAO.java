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
	boolean updateProfil(String oib, Profil newData);
	
	Mjesto getMjesto(int pbr);	
	Zupanija getZupanija(int sifZupanija);
	
	KorisnickiRacun getKorisnickiRacun(String korisnickoIme);
	List<KorisnickiRacun> getKorisnickiRacunByOib(String oib);
	boolean addKorisnickiRacun(KorisnickiRacun korisnickiRacun);
	boolean removeKorisnickiRacun(String korisnickoIme);
	boolean updateKorisinckiRacun(String korisnickoIme, KorisnickiRacun newData);

	Racun getRacun(String brRacun);
	List<Racun> getRacunByOib(String oib);
	boolean addRacun(Racun racun);
	boolean removeRacun(String brRacun);
	boolean updateRacun(String brRacun, Racun newData);
	
	Kartica getKartica(String brKartica);
	List<Kartica> getKarticaByOib(String oib);
	List<Kartica> getKarticaByBrRacun(String brRacun);
	boolean addKartica(Kartica kartica);
	boolean removeKartica(String brKartica);
	boolean updateKartica(String brKartica, Kartica newData);
	
	Kredit getKredit(int brKredit);
	List<Kredit> getKreditByOib(String oib);
	boolean addKredit(Kredit kredit);
	boolean removeKredit(int brKredit);
	boolean updateKredit(int brKredit, Kredit newData);
	
	Transakcija getTransakcija(int brTransakcija);
	List<Transakcija> getTransakcijaByBrRacunTerecenja(String brRacun);
	List<Transakcija> getTransakcijaByBrRacunOdobrenja(String brRacun);
	List<Transakcija> getTransakcijaByBrRacun(String brRacun);
	List<Transakcija> getTransakcijeByOib(String oib);
	boolean addTransakcija(Transakcija transakcija);
	
	RazinaOvlasti getRazinaOvlasti(int sifRazOvlasti);
	List<RazinaOvlasti> getAllRazinaOvlasti();
	boolean addRazinaOvlasti(RazinaOvlasti razinaOvlasti);
	
	VrstaRacuna getVrstaRacuna(int sifVrstaRacuna);
	List<VrstaRacuna> getAllVrstaRacuna();
	boolean addVrstaRacuna(VrstaRacuna vrstaRacuna);
	
	VrstaKartice getVrstaKartice(int sifVrstaKartice);
	List<VrstaKartice> getAllVrstaKartice();
	boolean addVrstaKartice(VrstaKartice vrstaKartice);
	
	VrstaKredita getVrstaKredita(int sifVrstaKredita);
	List<VrstaKredita> getAllVrstaKredita();
	boolean addVrstaKredita(VrstaKredita vrstaKredita);
	
	RegistracijaKlijenta getRegistracijaKlijenta(String oib);
	boolean addRegistracijaKlijenta(RegistracijaKlijenta registracijaKlijenta);
	boolean updateRegistracijaKlijenta(String oib, RegistracijaKlijenta newData);
	boolean removeRegistracijaKlijenta(String oib);
	
	List<ZahtjevKartica> getAllZahtjevKartica();
	ZahtjevKartica getZahtjevKartica(int sifZahtjeva);
	boolean addZahtjevKartica(ZahtjevKartica zahtjevKartica);
	boolean removeZahtjevKartica(int sifZahtjeva);
	boolean resolveZahtjevKartica(int sifZahtjeva, boolean odobren);
	
	List<ZahtjevKredit> getAllZahtjevKredit();
	ZahtjevKredit getZahtjevKredit(int sifZahtjeva);
	boolean addZahtjevKredit(ZahtjevKredit zahtjevKredit);
	boolean removeZahtjevKredit(int sifZahtjeva);
	boolean resolveZahtjevKredit(int sifZahtjeva, boolean odobren);
	
}