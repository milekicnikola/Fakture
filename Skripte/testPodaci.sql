insert into korisnik (korisnicko_ime, lozinka) values ('jteoic1s5gyt037f3sv', 'uft02cuxr5ay9wcjxy');

insert into korisnik (korisnicko_ime, lozinka) values ('9yricdbvwkdpi4n2y86', 'daf42hla2o1u7h0c69y');

insert into korisnik (korisnicko_ime, lozinka) values ('fgjj56wcpkp0y5np6h8', '7txuwqly6qm8rxk7a9ax');

insert into korisnik (korisnicko_ime, lozinka) values ('admin', 'admin');

insert into magacin (sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina) values ('CRW1', 'Glavni magacin', 'Zrenjanin', 'Sef 1', '023555666');

insert into magacin (sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina) values ('CRW2', 'Drugi magacin', 'Beograd', 'Sef 2', '011555666');

insert into magacin (sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina) values ('CRW3', 'Treci magacin', 'Novi Sad', 'Sef 3', '021555666');

insert into magacin (sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina) values ('SPC', 'SPC magacin', 'Zrenjanin', 'Sef 4', '023444555');

insert into magacin (sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina) values ('RAME', 'Rame magacin', 'Zrenjanin', 'Sef 5', '023666777');

insert into jedinica_mere (redni_broj, naziv_mere) values (1, 'komad');

insert into jedinica_mere (redni_broj, naziv_mere) values (2, 'set');

insert into jedinica_mere (redni_broj, naziv_mere) values (3, 'metar');

insert into kurs (datum_kursa, ron_evro) values ('2016-3-30', 9.78);

insert into kurs (datum_kursa, ron_evro) values ('2017-1-1', 10.12);

insert into kurs (datum_kursa, ron_evro) values ('2017-6-22', 10.08);

insert into kurs (datum_kursa, ron_evro) values ('2018-3-15', 11.3);

insert into kurs (datum_kursa, ron_evro) values ('2018-5-5', 10.5);

insert into kupci (pib, naziv_kupca, naziv_kupca2, adresa_kupca, grad_kupca, drzava_kupca) values ('0000000001', 'SC TEREX SRL', 'kl87w1g6juj9yhss6', '8b9p9yw5ji869t', '9siqrxjivt', '7y2u19jsi9g8');

insert into kupci (pib, naziv_kupca, naziv_kupca2, adresa_kupca, grad_kupca, drzava_kupca) values ('0000000002', 'c0jbbd7py4uk9u68eu9', 'c7oj3oq1572uot14', 'p1pqg lvwq7cd16cjc', 'egles0qef', 'rrwntu5q6uml');

insert into kupci (pib, naziv_kupca, naziv_kupca2, adresa_kupca, grad_kupca, drzava_kupca) values ('0000000003', 'ngu16h7q6vk374514', 'f9jaxuaohkr0h', 'q8nptrfx4kwy41', 'wb8t3qe47ebs', 'ft9ppegdd');

insert into kupci (pib, naziv_kupca, naziv_kupca2, adresa_kupca, grad_kupca, drzava_kupca) values ('0000000004', 'bk5gsntx dy9kjrlchnmv', 'fldf7ur121t1t6lqv', 'hajfsnbe', 'y6ogcnodc', 't83vxylgd');

insert into kupci (pib, naziv_kupca, naziv_kupca2, adresa_kupca, grad_kupca, drzava_kupca) values ('0000000005', '1kysnc3pckqglscej', 'gpkuqkcyh48iww1s', 'l0w7p4jnqca', 'u5mma8y', 'g9 hn08kv9');

insert into porudzbina (sifra_porudzbine, sifra_magacina, korisnicko_ime, pib_kupca, datum_porudzbine) values ('111', 'CRW1', 'admin', '0000000004', '2016-4-5');

insert into porudzbina (sifra_porudzbine, sifra_magacina, korisnicko_ime, pib_kupca, datum_porudzbine) values ('112', 'SPC', 'admin', '0000000003', '2018-6-18');

insert into porudzbina (sifra_porudzbine, sifra_magacina, korisnicko_ime, pib_kupca, datum_porudzbine) values ('113', 'CRW1', '9yricdbvwkdpi4n2y86', '0000000001', '2018-3-23');

insert into porudzbina (sifra_porudzbine, sifra_magacina, korisnicko_ime, pib_kupca, datum_porudzbine) values ('114', 'CRW2', 'jteoic1s5gyt037f3sv', '0000000003', '2017-11-6');

insert into porudzbina (sifra_porudzbine, sifra_magacina, korisnicko_ime, pib_kupca, datum_porudzbine) values ('115', 'SPC', 'jteoic1s5gyt037f3sv', '0000000001', '2018-9-18');

insert into faktura (sifra_fakture, korisnicko_ime, datum_fakture, paritet_fakture, bruto_fakture, neto_fakture, ukupno_komada_robe, poslata_faktura) values ('221', 'jteoic1s5gyt037f3sv', '2018-10-6', 'd4n63573f', 20.75, 42.34, 17, "ne");

insert into faktura (sifra_fakture, korisnicko_ime, datum_fakture, paritet_fakture, bruto_fakture, neto_fakture, ukupno_komada_robe, poslata_faktura) values ('222', '9yricdbvwkdpi4n2y86', '2017-11-8', 'q22pgc8a', 34.85, 12.52, 36, "da");

insert into faktura (sifra_fakture, korisnicko_ime, datum_fakture, paritet_fakture, bruto_fakture, neto_fakture, ukupno_komada_robe, poslata_faktura) values ('223', 'admin', '2018-12-30', 'c7dogsyy55', 54.69, 24.67, 54, "ne");

insert into faktura (sifra_fakture, korisnicko_ime, datum_fakture, paritet_fakture, bruto_fakture, neto_fakture, ukupno_komada_robe, poslata_faktura) values ('224', 'fgjj56wcpkp0y5np6h8', '2018-1-1', 't7orsx49j', 35, 254.7, 4, "ne");

insert into faktura (sifra_fakture, korisnicko_ime, datum_fakture, paritet_fakture, bruto_fakture, neto_fakture, ukupno_komada_robe, poslata_faktura) values ('225', 'admin', '2016-11-4', 'cisn24kw', 11.45, 45.8, 17, "da");

insert into otpremnica (sifra_otpremnice, korisnicko_ime, sifra_magacina, datum_otpremnice) values ('551', 'admin', 'CRW1', '2017-6-8');

insert into otpremnica (sifra_otpremnice, korisnicko_ime, sifra_magacina, datum_otpremnice) values ('552', '9yricdbvwkdpi4n2y86', 'CRW1', '2018-1-1');

insert into otpremnica (sifra_otpremnice, korisnicko_ime, sifra_magacina, datum_otpremnice) values ('553', 'admin', 'SPC', '2018-3-15');

insert into otpremnica (sifra_otpremnice, korisnicko_ime, sifra_magacina, datum_otpremnice) values ('554', 'admin', 'RAME', '2017-8-4');

insert into otpremnica (sifra_otpremnice, korisnicko_ime, sifra_magacina, datum_otpremnice) values ('555', 'jteoic1s5gyt037f3sv', 'CRW2', '2018-11-1');

insert into prevod (redni_broj, naziv_prevoda) values (1, 'el. constructi metalice bucati');

insert into prevod (redni_broj, naziv_prevoda) values (2, 'el. constructi metalice in metri');

insert into prevod (redni_broj, naziv_prevoda) values (3, 'el. constructi metalice seturi');

insert into prevod (redni_broj, naziv_prevoda) values (4, 'cuti carton');

insert into prevod (redni_broj, naziv_prevoda) values (5, 'separator carton');

insert into prevod (redni_broj, naziv_prevoda) values (6, 'coltar carton');

insert into roba (sifra_robe, jedinica_mere, prevod, interna_sifra_robe, naziv_robe, interni_naziv, komada_u_setu, tezina_robe, cena_roni) values ('001', 1, 6, '1', 'Prva roba', 'Prva', 0, 4.6, 21);

insert into roba (sifra_robe, jedinica_mere, prevod, interna_sifra_robe, naziv_robe, interni_naziv, komada_u_setu, tezina_robe, cena_roni) values ('002', 2, 5, '2', 'Druga roba', 'Druga', 5, 10, 15);

insert into roba (sifra_robe, jedinica_mere, prevod, interna_sifra_robe, naziv_robe, interni_naziv, komada_u_setu, tezina_robe, cena_roni) values ('003', 3, 4, '3', 'Treca roba', 'Treca', 0, 2.5, 36);

insert into roba (sifra_robe, jedinica_mere, prevod, interna_sifra_robe, naziv_robe, interni_naziv, komada_u_setu, tezina_robe, cena_roni) values ('004', 1, 3, '4', 'Cetvrta roba', 'Cetvrta', 0, 0.2, 53);

insert into roba (sifra_robe, jedinica_mere, prevod, interna_sifra_robe, naziv_robe, interni_naziv, komada_u_setu, tezina_robe, cena_roni) values ('005', 2, 2, '5', 'Peta roba', 'Peta', 10, 2.1, 46);

insert into roba (sifra_robe, jedinica_mere, prevod, interna_sifra_robe, naziv_robe, interni_naziv, komada_u_setu, tezina_robe, cena_roni) values ('006', 1, 1, '6', 'Sesta roba', 'Sesta', 0, 7, 74);

insert into narucena_roba (sifra_robe, sifra_porudzbine, komada_naruceno, komada_poslato, komada_ostalo, datum_isporuke, ko_radi) values ('001', '112', 22, 1, 21, '2018-8-6', 'Radnik 1');

insert into narucena_roba (sifra_robe, sifra_porudzbine, komada_naruceno, komada_poslato, komada_ostalo, datum_isporuke, ko_radi) values ('003', '113', 30, 0, 30, '2018-11-7', 'Radnik 2');

insert into narucena_roba (sifra_robe, sifra_porudzbine, komada_naruceno, komada_poslato, komada_ostalo, datum_isporuke, ko_radi) values ('002', '114', 50, 25, 25, '2018-1-1', 'Radnik 3');

insert into narucena_roba (sifra_robe, sifra_porudzbine, komada_naruceno, komada_poslato, komada_ostalo, datum_isporuke, ko_radi) values ('001', '112', 1, 1, 0, '2017-11-12', 'Radnik 4');

insert into narucena_roba (sifra_robe, sifra_porudzbine, komada_naruceno, komada_poslato, komada_ostalo, datum_isporuke, ko_radi) values ('002', '111', 12, 8, 4, '2018-6-20', 'Radnik 5');

insert into fakturisana_roba (sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, komada_fakturisano, opis, roba_otpremljena) values ('001', '112', '2018-8-6', '222', 2, '', '0');

insert into fakturisana_roba (sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, komada_fakturisano, opis, roba_otpremljena) values ('003', '113', '2018-11-7', '222', 5, 'Ovde ide neki tamo opis.', '0');

insert into fakturisana_roba (sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, komada_fakturisano, opis, roba_otpremljena) values ('002', '114', '2018-1-1', '221', 3, '', '1');

insert into fakturisana_roba (sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, komada_fakturisano, opis, roba_otpremljena) values ('001', '112', '2017-11-12', '224', 1, '', '0');

insert into fakturisana_roba (sifra_robe, sifra_porudzbine, datum_isporuke, sifra_fakture, komada_fakturisano, opis, roba_otpremljena) values ('002', '114', '2018-1-1', '223', 4, '', '1');

insert into otpremljena_roba (sifra_otpremnice, sifra_robe, sifra_fakture) values ('554', '003', '222');

insert into otpremljena_roba (sifra_otpremnice, sifra_robe, sifra_fakture) values ('553', '001', '222');

insert into otpremljena_roba (sifra_otpremnice, sifra_robe, sifra_fakture) values ('551', '002', '221');

insert into otpremljena_roba (sifra_otpremnice, sifra_robe, sifra_fakture) values ('552', '001', '224');

insert into otpremljena_roba (sifra_otpremnice, sifra_robe, sifra_fakture) values ('555', '002', '223');

