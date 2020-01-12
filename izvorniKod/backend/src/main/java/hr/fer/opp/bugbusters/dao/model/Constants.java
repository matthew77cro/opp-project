package hr.fer.opp.bugbusters.dao.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static final RazinaOvlasti administrator = new RazinaOvlasti(1, "Administrator");
	public static final RazinaOvlasti bankar = new RazinaOvlasti(2, "Bankar");
	public static final RazinaOvlasti sluzbenikZaKredite = new RazinaOvlasti(3, "Službenik za kredite");
	public static final RazinaOvlasti klijent = new RazinaOvlasti(4, "Klijent");
	
	public static final VrstaRacuna tekuci = new VrstaRacuna(1, "Tekući račun");
	public static final VrstaRacuna ziro = new VrstaRacuna(2, "Žiro račun");
	public static final VrstaRacuna stedni = new VrstaRacuna(3, "Štedni račun");
	
	public static final VrstaKredita stambeni = new VrstaKredita(1, "Stambeni kredit", new BigDecimal("2.6"));
	public static final VrstaKredita namjenski = new VrstaKredita(2, "Namjenski kredit", new BigDecimal("3.63"));
	public static final VrstaKredita nenamjenski = new VrstaKredita(3, "Nenamjenski kredit", new BigDecimal("4.16"));
	
	public static final VrstaKartice amex = new VrstaKartice(1, "American Express");
	public static final VrstaKartice diners = new VrstaKartice(2, "Diners");
	public static final VrstaKartice mastercard = new VrstaKartice(3, "Mastercard");
	public static final VrstaKartice visa = new VrstaKartice(4, "Visa");
	public static final VrstaKartice discover = new VrstaKartice(5, "Discover Card");
	
	public static final Map<Integer, RazinaOvlasti> razOvlastiMap;
	
	static {
		var roMap = new HashMap<Integer, RazinaOvlasti>();
		roMap.put(administrator.getSifRazOvlasti(), administrator);
		roMap.put(bankar.getSifRazOvlasti(), bankar);
		roMap.put(sluzbenikZaKredite.getSifRazOvlasti(), sluzbenikZaKredite);
		roMap.put(klijent.getSifRazOvlasti(), klijent);
		razOvlastiMap = Collections.unmodifiableMap(roMap);
	}
	
	public static final String initialValues = "INSERT INTO profil values ('01234567890', 'Eleven', '-', 'Ulica 1', 10000, '01.01.1970', 'eleven@gmail.com', '01234567890_slika_1.png');\r\n" + 
			"	INSERT INTO profil values ('01234567891', 'John', 'Doe', 'Ulica 1', 10000, '01.01.1970', 'john.doe@gmail.com', '01234567891_slika_1.png');\r\n" + 
			"\r\n" + 
			"	INSERT INTO korisnickiracun values ('admin', '24b1a812d4e3535c06011c430aaba3f59d32f36263ddcb99541f998266c5e84a52fb33f951cec78656f598a004f83c771388b9a80404f7432b714f4dcae4a00f', '01234567890', 1, false);\r\n" + 
			"	INSERT INTO korisnickiracun values ('bankar', '24b1a812d4e3535c06011c430aaba3f59d32f36263ddcb99541f998266c5e84a52fb33f951cec78656f598a004f83c771388b9a80404f7432b714f4dcae4a00f', '01234567890', 2, false);\r\n" + 
			"	INSERT INTO korisnickiracun values ('sluzbenik', '24b1a812d4e3535c06011c430aaba3f59d32f36263ddcb99541f998266c5e84a52fb33f951cec78656f598a004f83c771388b9a80404f7432b714f4dcae4a00f', '01234567890', 3, false);\r\n" +
			"	INSERT INTO korisnickiracun values ('klijent', '24b1a812d4e3535c06011c430aaba3f59d32f36263ddcb99541f998266c5e84a52fb33f951cec78656f598a004f83c771388b9a80404f7432b714f4dcae4a00f', '01234567891', 4, false);\r\n" +
			"\r\n" + 
			"	INSERT INTO kredit(oib, iznos, sifVrsteKredita, datUgovaranja, periodOtplate, datRate, preostaloDugovanje) values ('01234567891', 15000.00, 1, '01.01.2014', 5, 15, 1000.00 );\r\n" + 
			"	INSERT INTO kredit(oib, iznos, sifVrsteKredita, datUgovaranja, periodOtplate, datRate, preostaloDugovanje) values ('01234567891', 17000.00, 2, '01.01.2014', 5, 15, 1000.00 );\r\n" + 
			"\r\n" + 
			"	INSERT INTO racun values ('HR0201235720', '01234567891', '01.03.2016', 630.00, 1, 1000.00, 0, null);\r\n" + 
			"	INSERT INTO racun values ('HR0201235730', '01234567891', '01.03.2016', 730.00, 2, 0, 0, null);\r\n" + 
			"	INSERT INTO racun values ('HR0201235721', '01234567891', '01.03.2016', 630.00, 3, 0, 0.15, null);\r\n" + 
			"\r\n" + 
			"	INSERT INTO kartica values ('16257380071', null, '01234567891', 1, 1653.00, '01.03.2020', 2000, 18.93, 20);\r\n" + 
			"	INSERT INTO kartica values ('16257390072', 'HR0201235720', null, null, null, '01.03.2020', null, null, null);\r\n" + 
			"	INSERT INTO kartica values ('16257400073', 'HR0201235730', null, null, null, '01.03.2020', null, null, null);\r\n" + 
			"\r\n" + 
			"	INSERT INTO transakcija(racterecenja, racodobrenja, iznos, dattransakcije) VALUES ('HR0201235720', 'HR0201235730', '12.54', '2019-12-8');\r\n" + 
			"	INSERT INTO transakcija(racterecenja, racodobrenja, iznos, dattransakcije) VALUES ('HR0201235720', 'HR0201235730', '88.95', '2019-12-9');\r\n" + 
			"	INSERT INTO transakcija(racterecenja, racodobrenja, iznos, dattransakcije) VALUES ('HR0201235720', 'HR0201235730', '54.75', '2019-12-10');\r\n" + 
			"	INSERT INTO transakcija(racterecenja, racodobrenja, iznos, dattransakcije) VALUES ('HR0201235720', 'HR0201235730', '588.99', '2019-12-11');\r\n" + 
			"\r\n" + 
			"	-- default password for all accounts : 'bb'\r\n" + 
			"	-- password hashing algorithm : SHA-512";
	
}
