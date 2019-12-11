package hr.fer.opp.bugbusters.dao;

import java.util.List;

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
 * Interface toward Data Subsystem.
 * 
 * @author Matija
 *
 */
public interface DAO {
	
	KorisnickiRacun getKorisnickiRacun(String korisnickoIme);
	Profil getProfil(String oib);
	Profil getProfilByKorisnickoIme(String korisnickoIme);
	Mjesto getMjesto(int pbr);
	Zupanija getZupanija(int sifZupanija);
	boolean updatePassword(String korisnickoIme, String newPasswordHash);
	RazinaOvlasti getRazinaOvlasti(int sifRazOvlasti);
	List<Kartica> getKarticaForOib(String oib);
	List<Kartica> getKarticaForBrRacun(String brRacun);
	List<Kredit> getKreditForOib(String oib);
	List<Racun> getRacunForOib(String oib);
	Racun getRacunForBrRacun(String brRacun);
	List<Transakcija> getTransakcijaForBrRacunTerecenja(String brRacunTerecenja);
	VrstaKartice getVrstaKartice(int sifVrstaKartice);
	VrstaKredita getVrstaKredita(int sifVrstaKredita);
	VrstaRacuna getVrstaRacuna(int sifVrstaRacuna);
	
}