insert into korisnik (korisnicko_ime, lozinka) values ('1xohvehyvpyhrrfdpnqb', 'vqfy5lp9rohn7av2aevf');

insert into korisnik (korisnicko_ime, lozinka) values ('7t3cninx25ug6k3wsgoe', 'dhcg9ceabihm8ih44hah');

insert into korisnik (korisnicko_ime, lozinka) values ('xsfvp0xbgxkb7d8jxbq', 'pin5m2912xo7l9yu7oke');

insert into korisnik (korisnicko_ime, lozinka) values ('exgfbck19r548phy48qh', 'xruti1w3vk5mi6ftkgv6');

insert into korisnik (korisnicko_ime, lozinka) values ('admin', 'admin');

insert into magacin (sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina) values ('CRW1', 'Glavni magacin', 'Zrenjanin', 'Sef 1', '023555666');

insert into magacin (sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina) values ('CRW2', 'Drugi magacin', 'Beograd', 'Sef 2', '011555666');

insert into magacin (sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina) values ('CRW3', 'Treci magacin', 'Novi Sad', 'Sef 3', '021555666');

insert into magacin (sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina) values ('SPC', 'SPC magacin', 'Zrenjanin', 'Sef 4', '023444555');

insert into magacin (sifra_magacina, naziv_magacina, adresa_magacina, sef_magacina, telefon_magacina) values ('RAME', 'Rame magacin', 'Zrenjanin', 'Sef 5', '023666777');

insert into jedinica_mere (redni_broj, naziv) values (1, 'komad');

insert into jedinica_mere (redni_broj, naziv) values (2, 'set');

insert into jedinica_mere (redni_broj, naziv) values (3, 'metar');

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

insert into roba (sifra_robe, jedinica_mere, interna_sifra_robe, naziv_robe, komada_u_setu, tezina_robe, kvalitet_robe, cena_evri, cena_roni) values ('001', 1, '1', 'Prva roba', 0, 4.6, '7vquee fdn7kiy5x8ph1', 4.7, 50.14);

insert into roba (sifra_robe, jedinica_mere, interna_sifra_robe, naziv_robe, komada_u_setu, tezina_robe, kvalitet_robe, cena_evri, cena_roni) values ('002', 2, '2', 'Druga roba ', 5, 10, '9y3327d8co7l9dsnou9', 1.2, 15.6);

insert into roba (sifra_robe, jedinica_mere, interna_sifra_robe, naziv_robe, komada_u_setu, tezina_robe, kvalitet_robe, cena_evri, cena_roni) values ('003', 3, '3', 'Treca roba', 0, 2.5, '2lywrkwykdrips', 3, 12.6);

insert into roba (sifra_robe, jedinica_mere, interna_sifra_robe, naziv_robe, komada_u_setu, tezina_robe, kvalitet_robe, cena_evri, cena_roni) values ('004', 1, '4', 'Cetvrta roba', 0, 3.8, 'r1v8r1v2nunk', 2.8, 22.4);

insert into roba (sifra_robe, jedinica_mere, interna_sifra_robe, naziv_robe, komada_u_setu, tezina_robe, kvalitet_robe, cena_evri, cena_roni) values ('005', 2, '5', 'Peta roba', 10, 4.7, 'lvj8kvb6wchl1sovt', 1, 10.5);

insert into porudzbina (sifra_porudzbine, sifra_magacina, korisnicko_ime, pib_kupca, datum_porudzbine) values ('v4xcjqc0sftoo8p2qysf', 'CRW1', 'admin', '0000000004', '2016-4-5');

insert into porudzbina (sifra_porudzbine, sifra_magacina, korisnicko_ime, pib_kupca, datum_porudzbine) values ('afey8j pey4ygvriivef', 'SPC', 'admin', '0000000003', '2018-6-18');

insert into porudzbina (sifra_porudzbine, sifra_magacina, korisnicko_ime, pib_kupca, datum_porudzbine) values ('enwem42i j954absle8p', 'CRW1', 'exgfbck19r548phy48qh', '0000000001', '2018-3-23');

insert into porudzbina (sifra_porudzbine, sifra_magacina, korisnicko_ime, pib_kupca, datum_porudzbine) values ('4cefbnn4kgx2qpjls3r3', 'CRW2', 'exgfbck19r548phy48qh', '0000000003', '2017-11-6');

insert into porudzbina (sifra_porudzbine, sifra_magacina, korisnicko_ime, pib_kupca, datum_porudzbine) values ('hjawx4qubflkp4p3ssuh', 'SPC', '1xohvehyvpyhrrfdpnqb', '0000000001', '2018-9-18');

insert into faktura (sifra_fakture, korisnicko_ime, datum_fakture, paritet_fakture, bruto_fakture, neto_fakture, ukupno_komada_robe) values ('9d7q278jjxynhwjhbbr0', '1xohvehyvpyhrrfdpnqb', '2018-10-6', 'd4n63573f', 20.75, 42.34, 17);

insert into faktura (sifra_fakture, korisnicko_ime, datum_fakture, paritet_fakture, bruto_fakture, neto_fakture, ukupno_komada_robe) values ('nf11uyv43ffj3qi1ee5v', '1xohvehyvpyhrrfdpnqb', '2017-11-8', 'q22pgc8a', 34.85, 12.52, 36);

insert into faktura (sifra_fakture, korisnicko_ime, datum_fakture, paritet_fakture, bruto_fakture, neto_fakture, ukupno_komada_robe) values ('qwp9qft4sggnfwblteyf', 'admin', '2018-12-30', 'c7dogsyy55', 54.69, 24.67, 54);

insert into faktura (sifra_fakture, korisnicko_ime, datum_fakture, paritet_fakture, bruto_fakture, neto_fakture, ukupno_komada_robe) values ('wfxtq536evdsc8oa8ic7', '7t3cninx25ug6k3wsgoe', '2018-1-1', 't7orsx49j', 35, 254.7, 4);

insert into faktura (sifra_fakture, korisnicko_ime, datum_fakture, paritet_fakture, bruto_fakture, neto_fakture, ukupno_komada_robe) values ('evh2p9nvbylgn13ejukg', 'admin', '2016-11-4', 'cisn24kw', 11.45, 45.8, 17);

insert into narucena_roba (sifra_robe, sifra_porudzbine, komada_naruceno, komada_poslato, komada_ostalo, datum_narucivanja) values ('001', 'v4xcjqc0sftoo8p2qysf', 22, 1, 21, '2018-8-6');

insert into narucena_roba (sifra_robe, sifra_porudzbine, komada_naruceno, komada_poslato, komada_ostalo, datum_narucivanja) values ('003', 'afey8j pey4ygvriivef', 30, 0, 30, '2018-11-7');

insert into narucena_roba (sifra_robe, sifra_porudzbine, komada_naruceno, komada_poslato, komada_ostalo, datum_narucivanja) values ('002', 'enwem42i j954absle8p', 50, 25, 25, '2018-1-1');

insert into narucena_roba (sifra_robe, sifra_porudzbine, komada_naruceno, komada_poslato, komada_ostalo, datum_narucivanja) values ('001', '4cefbnn4kgx2qpjls3r3', 1, 1, 0, '2017-11-12');

insert into narucena_roba (sifra_robe, sifra_porudzbine, komada_naruceno, komada_poslato, komada_ostalo, datum_narucivanja) values ('002', 'afey8j pey4ygvriivef', 12, 8, 4, '2018-6-20');

insert into fakturisana_roba (sifra_robe, sifra_porudzbine, sifra_fakture, komada_fakturisano) values ('001', 'v4xcjqc0sftoo8p2qysf', '9d7q278jjxynhwjhbbr0', 2);

insert into fakturisana_roba (sifra_robe, sifra_porudzbine, sifra_fakture, komada_fakturisano) values ('003', 'afey8j pey4ygvriivef', 'nf11uyv43ffj3qi1ee5v', 5);

insert into fakturisana_roba (sifra_robe, sifra_porudzbine, sifra_fakture, komada_fakturisano) values ('002', 'enwem42i j954absle8p', 'qwp9qft4sggnfwblteyf', 3);

insert into fakturisana_roba (sifra_robe, sifra_porudzbine, sifra_fakture, komada_fakturisano) values ('001', '4cefbnn4kgx2qpjls3r3', 'wfxtq536evdsc8oa8ic7', 1);

insert into fakturisana_roba (sifra_robe, sifra_porudzbine, sifra_fakture, komada_fakturisano) values ('002', 'enwem42i j954absle8p', 'evh2p9nvbylgn13ejukg', 4);

