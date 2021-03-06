CREATE TABLE zupanija(
	sifZupanija int primary key,
	nazZupanija varchar(40) not null
	);

CREATE Table mjesto(
	pbr int primary key,
	nazMjesto varchar(25) not null,
	sifZupanija int not null, 
	FOREIGN KEY (sifZupanija) references zupanija(sifZupanija) ON DELETE CASCADE ON UPDATE CASCADE
	);

CREATE TABLE profil (
	oib varchar(11) primary key,
	ime varchar (25) not null,
	prezime varchar (25) not null,
	adresa varchar (40) not null,
	pbr int not null references mjesto(pbr),
	datRod date not null,
	email varchar(40) not null,
	slika varchar(100) not null
	);

CREATE TABLE registracijaKlijenta (
	oib varchar(11) primary key,
	privremeniKljuc varchar(64) not null,
	FOREIGN KEY (oib) references profil(oib) ON DELETE CASCADE ON UPDATE CASCADE
	);

CREATE TABLE razOvlasti(
		sifRazOvlasti serial primary key,
		nazRazOvlasti varchar(100) not null
		);
CREATE INDEX id_naziv ON razOvlasti(nazRazOvlasti);

CREATE TABLE korisnickiRacun (
	korisnickoIme varchar(25) primary key,
	lozinka varchar(128) not null,
	oib varchar(11) not null,
	sifRazOvlasti int not null,
	promjenaLozinke boolean not null,
	FOREIGN KEY (oib) references profil(oib) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (sifRazOvlasti) references razOvlasti(sifRazOvlasti) ON DELETE CASCADE ON UPDATE CASCADE
	);

CREATE TABLE vrstaKredita(
	sifVrsteKredita serial primary key,
	nazVrsteKredita varchar(25) not null,
	kamStopa decimal(3,2) not null
	);

CREATE TABLE kredit(
	brKredit serial primary key,
	oib varchar(11) not null,
	iznos decimal(10,2) check (iznos > 0),
	sifVrsteKredita int not null,
	datUgovaranja date not null,
	periodOtplate int not null,
	datRate int not null,
	preostaloDugovanje decimal(10,2) not null,
	FOREIGN KEY (oib) references profil(oib) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (sifVrsteKredita) references vrstaKredita(sifVrsteKredita) ON DELETE CASCADE ON UPDATE CASCADE
	);

CREATE TABLE vrstaRacuna(
	sifVrsteRacuna serial primary key,
	nazVrsteRacuna varchar(25) not null
	);

CREATE TABLE racun(
	brRacun varchar(25) primary key,
	oib varchar(11) not null,
	datOtvaranja date not null,
	stanje decimal(10,2) not null,
	sifVrsteRacuna int not null,
	prekoracenje decimal(10,2) not null,
	kamStopa decimal(3,2) not null,
	datZatvaranja date,
	FOREIGN KEY (oib) references profil(oib) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (sifVrsteRacuna) references vrstaRacuna(sifVrsteRacuna) ON DELETE CASCADE ON UPDATE CASCADE
	);	
	
CREATE TABLE transakcija(
	brTransakcija serial primary key,
	racTerecenja varchar(30) not null,
	racOdobrenja varchar(30) not null,
	iznos decimal(10,2) check (iznos > 0),
	datTransakcije date not null,
	FOREIGN KEY (racTerecenja) references racun(brRacun) ON DELETE CASCADE ON UPDATE CASCADE
	);	

CREATE TABLE vrstaKartice(
	sifVrstaKartice serial primary key,
	nazVrstaKartice varchar(25) not null
	);

CREATE TABLE kartica(
	brKartica varchar(32) primary key,
	brRacun varchar(25),
	oib varchar(11),
	sifVrstaKartice int,
	stanje decimal(10,2),
	valjanost date not null,
	limitKartice decimal(10,2),
	kamStopa decimal(5,2),
	datRate int,
	FOREIGN KEY (brRacun) references racun(brRacun) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (oib) references profil(oib) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (sifVrstaKartice) references vrstaKartice(sifVrstaKartice) ON DELETE CASCADE ON UPDATE CASCADE,
	CHECK ((brRacun IS NOT NULL AND oib IS NULL AND sifVrstaKartice IS NULL AND stanje IS NULL AND limitKartice IS NULL AND kamStopa IS NULL AND datRate IS NULL)
			OR (brRacun IS NULL AND oib IS NOT NULL AND sifVrstaKartice IS NOT NULL AND stanje IS NOT NULL AND limitKartice IS NOT NULL AND kamStopa IS NOT NULL AND datRate IS NOT NULL))
	);
	
CREATE TABLE zahtjevKartica (
	sifZahtjeva serial primary key,
	oib varchar(11) UNIQUE not null,
	sifVrstaKartice int not null,
	odobren boolean not null,
	FOREIGN KEY (oib) references profil(oib) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (sifVrstaKartice) references vrstaKartice(sifVrstaKartice) ON DELETE CASCADE ON UPDATE CASCADE
	);
	
CREATE TABLE zahtjevKredit (
	sifZahtjeva serial primary key,
	oib varchar(11) UNIQUE not null,
	iznos decimal(10,2) check (iznos > 0),
	sifVrsteKredita int not null,
	periodOtplate int not null,
	odobren boolean not null,
		FOREIGN KEY (oib) references profil(oib) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (sifVrsteKredita) references vrstaKredita(sifVrsteKredita) ON DELETE CASCADE ON UPDATE CASCADE
	);

INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (0, 'Nepoznata županija');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (1, 'Zagrebačka');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (10, 'Virovitičko-podravska');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (11, 'Požeško-slavonska');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (12, 'Brodsko-posavska');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (13, 'Zadarska');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (14, 'Osječko-baranjska');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (15, 'Šibensko-kninska');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (16, 'Vukovarsko-srijemska');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (17, 'Splitsko-dalmatinska');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (18, 'Istarska');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (19, 'Dubrovačko-neretvanska');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (2, 'Krapinsko-zagorska');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (20, 'Međimurska');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (21, 'Grad Zagreb');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (3, 'Sisačko-moslavačka');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (4, 'Karlovačka');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (5, 'Varaždinska');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (6, 'Koprivničko-križevačka');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (7, 'Bjelovarsko-bilogorska');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (8, 'Primorsko-goranska');
INSERT INTO zupanija (sifZupanija, nazZupanija) VALUES (9, 'Ličko-senjska');

INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10000, 'Zagreb', 21);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10010, 'Zagreb-Sloboština', 21);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10020, 'Zagreb-Novi Zagreb', 21);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10040, 'Zagreb-Dubrava', 21);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10090, 'Zagreb-Susedgrad', 21);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10250, 'Lučko', 1);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10255, 'Gornji Stupnik', 1);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10290, 'Zaprešić', 1);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10295, 'Kupljenovo', 1);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10310, 'Ivanić-Grad', 1);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10315, 'Novoselec', 1);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10340, 'Vrbovec', 1);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10345, 'Gradec', 1);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10360, 'Sesvete', 1);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10370, 'Dugo Selo', 1);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10380, 'Sveti Ivan Zelina', 21);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10410, 'Velika Gorica', 1);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10415, 'Novo Čiče', 1);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10430, 'Samobor', 1);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10435, 'Sveti Martin pod Okićem', 1);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10450, 'Jastrebarsko', 1);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (10455, 'Kostanjevac', 1);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20000, 'Dubrovnik', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20205, 'Topolo', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20210, 'Cavtat', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20215, 'Gruda', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20225, 'Babino Polje', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20230, 'Ston', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20235, 'Zaton Veliki', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20240, 'Trpanj', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20242, 'Oskorušno', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20245, 'Trstenik', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20250, 'Orebić', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20260, 'Korčula', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20270, 'Vela Luka', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20275, 'Žrnovo', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20290, 'Lastovo', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20340, 'Ploče', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20345, 'Staševica', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20350, 'Metković', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (20355, 'Opuzen', 19);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21000, 'Split', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21205, 'Donji Dolac', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21210, 'Solin', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21215, 'Kaštel Lukšić', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21220, 'Trogir', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21225, 'Drvenik Veliki', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21230, 'Sinj', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21232, 'Dicmo', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21235, 'Otišić', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21240, 'Trilj', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21245, 'Tijarica', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21250, 'Šestanovac', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21255, 'Zadvarje', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21260, 'Imotski', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21265, 'Studenci', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21270, 'Zagvozd', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21275, 'Dragljane', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21300, 'Makarska', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21310, 'Omiš', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21315, 'Dugi Rat', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21320, 'Baška Voda', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21325, 'Tučepi', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21330, 'Gradac', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21335, 'Podaca', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21400, 'Supetar', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21405, 'Milna', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21410, 'Postira', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21420, 'Bol', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21425, 'Selca', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21430, 'Grohote', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21450, 'Hvar', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21460, 'Stari Grad', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21465, 'Jelsa', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21480, 'Vis', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (21485, 'Komiža', 17);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (22000, 'Šibenik', 15);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (22010, 'Šibenik-Brodarica', 15);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (22020, 'Šibenik-Ražine', 15);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (22030, 'Šibenik-Zablaće', 15);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (22205, 'Perković', 15);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (22215, 'Zaton', 15);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (22235, 'Kaprije', 15);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (22240, 'Tisno', 15);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (22300, 'Knin', 15);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (22305, 'Kistanje', 15);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (22310, 'Kijevo', 15);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (22320, 'Drniš', 15);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (23000, 'Zadar', 13);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (23205, 'Bibinje', 13);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (23210, 'Biograd na moru', 13);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (23235, 'Vrsi', 13);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (23245, 'Tribanj', 13);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (23250, 'Pag', 13);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (23275, 'Ugljan', 13);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (23285, 'Brbinj', 13);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (23295, 'Silba', 13);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (23420, 'Benkovac', 13);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (23440, 'Gračac', 13);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (23445, 'Srb', 13);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (23450, 'Obrovac', 13);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31000, 'Osijek', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31205, 'Aljmaš', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31215, 'Ernestinovo', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31220, 'Višnjevac', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31225, 'Breznica Našička', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31300, 'Beli Manastir', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31301, 'Branjin Vrh', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31305, 'Draž', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31315, 'Karanac', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31325, 'Čeminac', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31400, 'Đakovo', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31410, 'Strizivojna', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31415, 'Selci Đakovački', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31500, 'Našice', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31530, 'Podravska Moslavina', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31531, 'Viljevo', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31540, 'Donji Miholjac', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31550, 'Valpovo', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (31555, 'Marijanci', 14);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (32000, 'Vukovar', 16);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (32100, 'Vinkovci', 16);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (32225, 'Bobota', 16);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (32235, 'Bapska', 16);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (32240, 'Mirkovci', 16);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (32245, 'Nijemci', 16);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (32255, 'Soljani', 16);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (32260, 'Gunja', 16);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (32270, 'Županja', 16);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (32275, 'Bošnjaci', 16);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (32280, 'Jarmina', 16);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (33000, 'Virovitica', 10);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (33405, 'Pitomača', 10);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (33410, 'Suhopolje', 10);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (33515, 'Orahovica', 10);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (33520, 'Slatina', 10);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (33525, 'Sopje', 10);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (34000, 'Požega', 11);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (34310, 'Pleternica', 11);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (34315, 'Ratkovica', 11);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (34320, 'Orljavac', 11);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (34330, 'Velika', 11);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (34335, 'Vetovo', 11);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (34340, 'Kutjevo', 11);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (34350, 'Čaglin', 11);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (34550, 'Pakrac', 11);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (35000, 'Slavonski Brod', 12);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (35210, 'Vrpolje', 12);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (35215, 'Svilaj', 12);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (35220, 'Slavonski Šamac', 12);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (35250, 'Oriovac', 12);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (35255, 'Slavonski Kobaš', 12);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (35400, 'Nova Gradiška', 12);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (35420, 'Staro Petrovo Selo', 12);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (35425, 'Davor', 12);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (35430, 'Okučani', 12);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (35435, 'Stara Gradiška', 12);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (40000, 'Čakovec', 20);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (40305, 'Nedelišče', 20);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (40315, 'Mursko Središče', 20);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (40320, 'Donji Kraljevec', 20);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (40325, 'Draškovec', 20);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (42000, 'Varaždin', 5);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (42205, 'Vidovec', 5);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (42220, 'Novi Marof', 5);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (42225, 'Breznički Hum', 5);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (42230, 'Ludbreg', 5);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (42240, 'Ivanec', 5);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (42245, 'Donja Voća', 5);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (42250, 'Lepoglava', 5);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (42255, 'Donja Višnjica', 5);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (43000, 'Bjelovar', 7);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (43240, 'Čazma', 7);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (43245, 'Gornji Draganec', 7);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (43270, 'Veliki Grđevac', 7);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (43271, 'Velika Pisanica', 7);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (43280, 'Garešnica', 7);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (43285, 'Velika Trnovitica', 7);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (43290, 'Grubišno Polje', 7);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (43500, 'Daruvar', 7);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (43505, 'Končanica', 7);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (43531, 'Veliki Bastaji', 7);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44000, 'Sisak', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44010, 'Sisak-Caprag', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44205, 'Donja Bačuga', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44210, 'Sunja', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44250, 'Petrinja', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44271, 'Letovanić', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44320, 'Kutina', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44325, 'Krapje', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44330, 'Novska', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44400, 'Glina', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44405, 'Mali Gradac', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44410, 'Gvozd', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44415, 'Topusko', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44425, 'Gornja Bučica', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44430, 'Hrvatska Kostajnica', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44435, 'Divuša', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44440, 'Dvor', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (44450, 'Hrvatska Dubica', 3);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (47000, 'Karlovac', 4);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (47201, 'Draganići', 4);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (47205, 'Vukmanić', 4);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (47220, 'Vojnić', 4);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (47240, 'Slunj', 4);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (47245, 'Rakovica', 4);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (47250, 'Duga Resa', 4);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (47251, 'Bosiljevo', 4);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (47280, 'Ozalj', 4);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (47285, 'Radatovići', 4);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (47300, 'Ogulin', 4);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (47302, 'Oštarije', 4);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (47305, 'Lička Jesenica', 4);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (48000, 'Koprivnica', 6);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (48260, 'Križevci', 6);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (48265, 'Raven', 6);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (48305, 'Reka', 6);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (48325, 'Novigrad Podravski', 6);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (48350, 'Đurđevac', 6);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (48355, 'Novo Virje', 6);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (49000, 'Krapina', 2);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (49210, 'Zabok', 2);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (49215, 'Tuhelj', 2);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (49221, 'Bedekovčina', 2);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (49225, 'Đurmanec', 2);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (49240, 'Donja Stubica', 2);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (49245, 'Gornja Stubica', 2);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (49246, 'Marija Bistrica', 2);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (49247, 'Zlatar Bistrica', 2);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (49250, 'Zlatar', 2);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (49255, 'Novi Golubovec', 2);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (49290, 'Klanjec', 2);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (49295, 'Kumrovec', 2);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51000, 'Rijeka', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51211, 'Matulji', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51215, 'Kastav', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51225, 'Praputnjak', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51250, 'Novi Vinodolski', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51251, 'Ledenice', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51260, 'Crikvenica', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51265, 'Dramalj', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51280, 'Rab', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51300, 'Delnice', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51305, 'Tršće', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51315, 'Mrkopalj', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51325, 'Moravice', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51410, 'Opatija', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51415, 'Lovran', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51500, 'Krk', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51515, 'Šilo', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51550, 'Mali Lošinj', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (51555, 'Belej', 8);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (52000, 'Pazin', 18);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (52100, 'Pula', 18);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (52210, 'Rovinj (Rovigno)', 18);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (52215, 'Vodnjan (Dignano)', 18);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (52220, 'Labin', 18);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (52420, 'Buzet', 18);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (52425, 'Roč', 18);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (52440, 'Poreč', 18);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (52445, 'Baderna', 18);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (52450, 'Vrsar', 18);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (52460, 'Buje (Buie)', 18);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (52465, 'Tar', 18);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (52470, 'Umag (Umago)', 18);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (52475, 'Savudrija (Salvore)', 18);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (53000, 'Gospić', 9);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (53205, 'Medak', 9);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (53220, 'Otočac', 9);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (53225, 'Švica', 9);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (53230, 'Korenica', 9);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (53235, 'Bunić', 9);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (53250, 'Donji Lapac', 9);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (53260, 'Brinje', 9);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (53270, 'Senj', 9);
INSERT INTO mjesto (pBr, nazMjesto, sifZupanija) VALUES (53285, 'Lukovo', 9);