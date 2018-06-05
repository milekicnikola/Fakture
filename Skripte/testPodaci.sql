insert into korisnik (korisnicko_ime, lozinka) values ('olgica', 'olgica');

insert into korisnik (korisnicko_ime, lozinka) values ('milos', 'milos');

insert into korisnik (korisnicko_ime, lozinka) values ('admin', 'admin');

insert into jedinica_mere (redni_broj, naziv_mere) values (1, 'komad');

insert into jedinica_mere (redni_broj, naziv_mere) values (2, 'set');

insert into jedinica_mere (redni_broj, naziv_mere) values (3, 'metar');

insert into kurs (datum_kursa, ron_evro) values ('2016-3-30', 6.78);

insert into kurs (datum_kursa, ron_evro) values ('2017-1-1', 6.12);

insert into kurs (datum_kursa, ron_evro) values ('2017-6-22', 6.08);

insert into kurs (datum_kursa, ron_evro) values ('2018-3-15', 6.3);

insert into kurs (datum_kursa, ron_evro) values ('2018-5-5', 6.5);

insert into prevod (redni_broj, naziv_prevoda) values (1, 'el. constructi metalice bucati');

insert into prevod (redni_broj, naziv_prevoda) values (2, 'el. constructi metalice in metri');

insert into prevod (redni_broj, naziv_prevoda) values (3, 'el. constructi metalice seturi');

insert into prevod (redni_broj, naziv_prevoda) values (4, 'cuti carton');

insert into prevod (redni_broj, naziv_prevoda) values (5, 'separator carton');

insert into prevod (redni_broj, naziv_prevoda) values (6, 'coltar carton');

insert into magacin (sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina) values ('CRW1', 'Glavni magacin', 'Zrenjanin', 'Sef 1', '023555666');

insert into magacin (sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina) values ('CRW2', 'Drugi magacin', 'Beograd', 'Sef 2', '011555666');

insert into magacin (sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina) values ('CRW3', 'Treci magacin', 'Novi Sad', 'Sef 3', '021555666');

insert into magacin (sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina) values ('SPC', 'SPC magacin', 'Zrenjanin', 'Sef 4', '023444555');

insert into magacin (sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina) values ('RAME', 'Rame magacin', 'Zrenjanin', 'Sef 5', '023666777');

insert into kupci (pib, naziv_kupca, naziv_kupca2, adresa_kupca, grad_kupca, drzava_kupca) values ('0000000001', 'SC TEREX SRL', 'Tereks', 'Terex ulica', 'Terex grad', 'Terex drzava');

insert into kupci (pib, naziv_kupca, naziv_kupca2, adresa_kupca, grad_kupca, drzava_kupca) values ('0000000002', 'Kupac 1', 'Prvi kupac', 'Prva ulica', 'Prvi grad', 'Prva drzava');

insert into kupci (pib, naziv_kupca, naziv_kupca2, adresa_kupca, grad_kupca, drzava_kupca) values ('0000000003', 'Kupac 2', 'Drugi kupac', 'Druga ulica', 'Drugi grad', 'Druga drzava');

insert into kupci (pib, naziv_kupca, naziv_kupca2, adresa_kupca, grad_kupca, drzava_kupca) values ('0000000004', 'Kupac 3', 'Treci kupac', 'Treca ulica', 'Treci grad', 'Treca drzava');

insert into kupci (pib, naziv_kupca, naziv_kupca2, adresa_kupca, grad_kupca, drzava_kupca) values ('0000000005', 'Kupac 4', 'Cetvrti kupac', 'Cetvrta ulica', 'Cetvrti grad', 'Cetvrta drzava');

insert into roba (sifra_robe, jedinica_mere, prevod, interna_sifra_robe, naziv_robe, interni_naziv, komada_u_setu, tezina_robe, cena_roni) values ('001', 1, 6, '1', 'Prva roba', 'Prva', 1, 4.6, 21);

insert into roba (sifra_robe, jedinica_mere, prevod, interna_sifra_robe, naziv_robe, interni_naziv, komada_u_setu, tezina_robe, cena_roni) values ('002', 2, 5, '2', 'Druga roba', 'Druga', 5, 10, 15);

insert into roba (sifra_robe, jedinica_mere, prevod, interna_sifra_robe, naziv_robe, interni_naziv, komada_u_setu, tezina_robe, cena_roni) values ('003', 3, 4, '3', 'Treca roba', 'Treca', 1, 2.5, 36);

insert into roba (sifra_robe, jedinica_mere, prevod, interna_sifra_robe, naziv_robe, interni_naziv, komada_u_setu, tezina_robe, cena_roni) values ('004', 1, 3, '4', 'Cetvrta roba', 'Cetvrta', 1, 0.2, 53);

insert into roba (sifra_robe, jedinica_mere, prevod, interna_sifra_robe, naziv_robe, interni_naziv, komada_u_setu, tezina_robe, cena_roni) values ('005', 2, 2, '5', 'Peta roba', 'Peta', 10, 2.1, 46);

insert into roba (sifra_robe, jedinica_mere, prevod, interna_sifra_robe, naziv_robe, interni_naziv, komada_u_setu, tezina_robe, cena_roni) values ('006', 1, 1, '6', 'Sesta roba', 'Sesta', 1, 7, 74);

insert into porudzbina (sifra_porudzbine, sifra_magacina, pib_kupca, datum_porudzbine) values ('111', 'CRW1', '0000000004', '2016-4-5');

insert into porudzbina (sifra_porudzbine, sifra_magacina, pib_kupca, datum_porudzbine) values ('112', 'SPC', '0000000003', '2018-6-18');

insert into porudzbina (sifra_porudzbine, sifra_magacina, pib_kupca, datum_porudzbine) values ('113', 'CRW1', '0000000001', '2018-3-23');

insert into porudzbina (sifra_porudzbine, sifra_magacina, pib_kupca, datum_porudzbine) values ('114', 'CRW2', '0000000003', '2017-11-6');

insert into porudzbina (sifra_porudzbine, sifra_magacina, pib_kupca, datum_porudzbine) values ('115', 'SPC', '0000000001', '2018-9-18');

insert into faktura (sifra_fakture, datum_fakture, paritet_fakture, ukupna_tezina, transport_fakture, poslata_faktura) values ('221', '2018-10-6', 'd4n63573f', 20.75, 'ZR075CI/BD772ZR', "ne");

insert into faktura (sifra_fakture, datum_fakture, paritet_fakture, ukupna_tezina, transport_fakture, poslata_faktura) values ('222', '2017-11-8', 'q22pgc8a', 34.85, 'ZR075CI/BD772ZR', "ne");

insert into faktura (sifra_fakture, datum_fakture, paritet_fakture, ukupna_tezina, transport_fakture, poslata_faktura) values ('223', '2018-12-30', 'c7dogsyy55', 54.69, 'BG075CI/AC688BG', "ne");

insert into faktura (sifra_fakture, datum_fakture, paritet_fakture, ukupna_tezina, transport_fakture, poslata_faktura) values ('224', '2018-1-1', 't7orsx49j', 35, 'ZR075CI/AC688ZR', "ne");

insert into faktura (sifra_fakture, datum_fakture, paritet_fakture, ukupna_tezina, transport_fakture, poslata_faktura) values ('225', '2016-11-4', 'cisn24kw', 11.45, 'ZR12T85/AC688ZR', "ne");

insert into otpremnica (sifra_otpremnice, sifra_magacina, sifra_fakture, poslata_otpremnica) values ('551', 'CRW1', '221', 'ne');

insert into otpremnica (sifra_otpremnice, sifra_magacina, sifra_fakture, poslata_otpremnica) values ('552', 'CRW1', '224', 'ne');

insert into otpremnica (sifra_otpremnice, sifra_magacina, sifra_fakture, poslata_otpremnica) values ('553', 'SPC', '224', 'ne');

insert into otpremnica (sifra_otpremnice, sifra_magacina, sifra_fakture, poslata_otpremnica) values ('554', 'RAME', '222', 'ne');

insert into otpremnica (sifra_otpremnice, sifra_magacina, sifra_fakture, poslata_otpremnica) values ('555', 'CRW2', '223', 'ne');

insert into narucena_roba (sifra_robe, sifra_porudzbine, korisnicko_ime, komada_naruceno, komada_poslato, komada_ostalo, datum_isporuke, ko_radi) values ('001', '112', 'admin', 22, 0, 22, '2018-8-6', 'Radnik 1');

insert into narucena_roba (sifra_robe, sifra_porudzbine, korisnicko_ime, komada_naruceno, komada_poslato, komada_ostalo, datum_isporuke, ko_radi) values ('003', '113', 'milos', 30, 0, 30, '2018-11-7', 'Radnik 2');

insert into narucena_roba (sifra_robe, sifra_porudzbine, korisnicko_ime, komada_naruceno, komada_poslato, komada_ostalo, datum_isporuke, ko_radi) values ('002', '114', 'olgica', 50, 0, 50, '2018-1-1', 'Radnik 3');

insert into narucena_roba (sifra_robe, sifra_porudzbine, korisnicko_ime, komada_naruceno, komada_poslato, komada_ostalo, datum_isporuke, ko_radi) values ('001', '112', 'olgica', 1, 0, 1, '2017-11-12', 'Radnik 4');

insert into narucena_roba (sifra_robe, sifra_porudzbine, korisnicko_ime, komada_naruceno, komada_poslato, komada_ostalo, datum_isporuke, ko_radi) values ('002', '111', 'milos', 12, 0, 12, '2018-6-20', 'Radnik 5');

insert into fakturisana_roba (sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, komada_fakturisano, opis, status, komada_u_metru) values ('001', '112', '2018-8-6', '222', 2, '', 'narucena', '0');

insert into fakturisana_roba (sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, komada_fakturisano, opis, status, komada_u_metru) values ('003', '113', '2018-11-7', '222', 5, 'Ovde ide neki tamo opis.', 'narucena', '5');

insert into fakturisana_roba (sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, komada_fakturisano, opis, status, komada_u_metru) values ('002', '114', '2018-1-1', '221', 3, '', 'narucena', '0');

insert into fakturisana_roba (sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, komada_fakturisano, opis, status, komada_u_metru) values ('001', '112', '2017-11-12', '224', 1, '', 'narucena', '0');

insert into fakturisana_roba (sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, komada_fakturisano, opis, status, komada_u_metru) values ('002', '114', '2018-1-1', '223', 4, '', 'narucena', '0');

insert into otpremljena_roba (sifra_otpremnice, sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, status_robe) values ('554', '003', '113', '2018-11-7', '222', 'fakturisana');

insert into otpremljena_roba (sifra_otpremnice, sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, status_robe) values ('553', '001', '112', '2017-11-12', '224', 'fakturisana');

insert into otpremljena_roba (sifra_otpremnice, sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, status_robe) values ('551', '002', '114', '2018-1-1', '221', 'fakturisana');

insert into otpremljena_roba (sifra_otpremnice, sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, status_robe) values ('552', '001', '112', '2017-11-12', '224', 'fakturisana');

insert into otpremljena_roba (sifra_otpremnice, sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, status_robe) values ('555', '002', '114', '2018-1-1', '223', 'fakturisana');

