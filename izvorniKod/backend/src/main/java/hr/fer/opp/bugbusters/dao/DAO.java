package hr.fer.opp.bugbusters.dao;

import hr.fer.opp.bugbusters.dao.model.KorisnickiRacun;
import hr.fer.opp.bugbusters.dao.model.Mjesto;
import hr.fer.opp.bugbusters.dao.model.Profil;
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
	boolean changePassword(String korisnickoIme, String newPasswordHash);
	
}