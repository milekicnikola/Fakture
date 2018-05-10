/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     5/11/2018 12:02:13 AM                        */
/*==============================================================*/


drop table if exists kurs;

drop table if exists narucena_roba;

drop table if exists fakturisana_roba;

drop table if exists otpremljena_roba;

drop table if exists porudzbina;

drop table if exists faktura;

drop table if exists otpremnica;

drop table if exists kupci;

drop table if exists roba;

drop table if exists magacin;

drop table if exists korisnik;

/*==============================================================*/
/* Table: faktura                                               */
/*==============================================================*/
create table faktura
(
   redni_broj_fakture   numeric(10,0) not null,
   sifra_fakture        varchar(20) not null,
   korisnicko_ime       varchar(20) not null,
   sifra_kupca          varchar(3) not null,
   datum_fakture        date not null,
   paritet_fakture      varchar(25),
   bruto_fakture        decimal(10,2),
   neto_fakture         decimal(10,2),
   ukupno_komada_robe   numeric(10,0),
   primary key (sifra_fakture)
);

/*==============================================================*/
/* Table: fakturisana_roba                                      */
/*==============================================================*/
create table fakturisana_roba
(
   sifra_robe           varchar(75) not null,
   sifra_fakture        varchar(20) not null,
   komada_fakturisano   numeric(10,0) not null,
   primary key (sifra_robe, sifra_fakture)
);

/*==============================================================*/
/* Table: korisnik                                              */
/*==============================================================*/
create table korisnik
(
   korisnicko_ime       varchar(20) not null,
   lozinka              varchar(20) not null,
   primary key (korisnicko_ime)
);

/*==============================================================*/
/* Table: kupci                                                 */
/*==============================================================*/
create table kupci
(
   sifra_kupca          varchar(3) not null,
   naziv_kupca          varchar(50) not null,
   naziv_kupca2         varchar(50),
   adresa_kupca         varchar(50),
   grad_kupca           varchar(30),
   drzava_kupca         varchar(30),
   primary key (sifra_kupca)
);

/*==============================================================*/
/* Table: kurs                                                  */
/*==============================================================*/
create table kurs
(
   datum_kursa          date not null,
   ron_evro             decimal(10,4) not null,
   primary key (datum_kursa)
);

/*==============================================================*/
/* Table: magacin                                               */
/*==============================================================*/
create table magacin
(
   sifra_magacina       varchar(5) not null,
   naziv_magacina       varchar(50) not null,
   adresa_magacina      varchar(50),
   sef_magacina         varchar(50),
   telefon_magacina     varchar(20),
   primary key (sifra_magacina)
);

/*==============================================================*/
/* Table: narucena_roba                                         */
/*==============================================================*/
create table narucena_roba
(
   sifra_robe           varchar(75) not null,
   sifra_porudzbine     varchar(20) not null,
   komada_naruceno      numeric(10,0) not null,
   komada_poslato       numeric(10,0),
   komada_ostalo        numeric(10,0),
   primary key (sifra_robe, sifra_porudzbine)
);

/*==============================================================*/
/* Table: otpremljena_roba                                      */
/*==============================================================*/
create table otpremljena_roba
(
   sifra_robe           varchar(75) not null,
   sifra_otpremnice     varchar(30) not null,
   komada_poslato       numeric(10,0),
   primary key (sifra_robe, sifra_otpremnice)
);

/*==============================================================*/
/* Table: otpremnica                                            */
/*==============================================================*/
create table otpremnica
(
   redni_broj_otpremnice numeric(10,0) not null,
   sifra_otpremnice     varchar(30) not null,
   sifra_porudzbine     varchar(20) not null,
   sifra_magacina       varchar(5) not null,
   sifra_fakture        varchar(20) not null,
   korisnicko_ime       varchar(20) not null,
   primary key (sifra_otpremnice)
);

/*==============================================================*/
/* Table: porudzbina                                            */
/*==============================================================*/
create table porudzbina
(
   redni_broj_porudzbine numeric(10,0) not null,
   sifra_porudzbine     varchar(20) not null,
   sifra_magacina       varchar(5) not null,
   korisnicko_ime       varchar(20) not null,
   sifra_kupca          varchar(3) not null,
   datum_isporuke       date not null,
   primary key (sifra_porudzbine)
);

/*==============================================================*/
/* Table: roba                                                  */
/*==============================================================*/
create table roba
(
   sifra_robe           varchar(75) not null,
   sifra_magacina       varchar(5) not null,
   interna_sifra_robe   varchar(20) not null,
   naziv_robe           varchar(100) not null,
   jedinica_mere_robe   varchar(10),
   komada_u_setu        numeric(4,0),
   tezina_robe          decimal(10,2),
   kvalitet_robe        varchar(50),
   cena_evri            decimal(10,4) not null,
   cena_roni            decimal(10,4),
   primary key (sifra_robe)
);

alter table faktura add constraint fk_faktura_kupca foreign key (sifra_kupca)
      references kupci (sifra_kupca) on delete restrict on update restrict;

alter table faktura add constraint fk_korisnik_faktura foreign key (korisnicko_ime)
      references korisnik (korisnicko_ime) on delete restrict on update restrict;

alter table fakturisana_roba add constraint fk_fakturisana_roba foreign key (sifra_fakture)
      references faktura (sifra_fakture) on delete restrict on update restrict;

alter table fakturisana_roba add constraint fk_fakturisana_roba1 foreign key (sifra_robe)
      references roba (sifra_robe) on delete restrict on update restrict;

alter table narucena_roba add constraint fk_narucena_roba foreign key (sifra_porudzbine)
      references porudzbina (sifra_porudzbine) on delete restrict on update restrict;

alter table narucena_roba add constraint fk_narucena_roba1 foreign key (sifra_robe)
      references roba (sifra_robe) on delete restrict on update restrict;

alter table otpremljena_roba add constraint fk_otpremljena_roba foreign key (sifra_otpremnice)
      references otpremnica (sifra_otpremnice) on delete restrict on update restrict;

alter table otpremljena_roba add constraint fk_otpremljena_roba1 foreign key (sifra_robe)
      references roba (sifra_robe) on delete restrict on update restrict;

alter table otpremnica add constraint fk_korisnik_otpremnica foreign key (korisnicko_ime)
      references korisnik (korisnicko_ime) on delete restrict on update restrict;

alter table otpremnica add constraint fk_otpremnica_iz_magacina foreign key (sifra_magacina)
      references magacin (sifra_magacina) on delete restrict on update restrict;

alter table otpremnica add constraint fk_otpremnica_po_fakturi foreign key (sifra_fakture)
      references faktura (sifra_fakture) on delete restrict on update restrict;

alter table otpremnica add constraint fk_otpremnica_po_porudzbini foreign key (sifra_porudzbine)
      references porudzbina (sifra_porudzbine) on delete restrict on update restrict;

alter table porudzbina add constraint fk_korisnik_porudzbina foreign key (korisnicko_ime)
      references korisnik (korisnicko_ime) on delete restrict on update restrict;

alter table porudzbina add constraint fk_porudzbina_iz_magacina foreign key (sifra_magacina)
      references magacin (sifra_magacina) on delete restrict on update restrict;

alter table porudzbina add constraint fk_porudzbina_kupca foreign key (sifra_kupca)
      references kupci (sifra_kupca) on delete restrict on update restrict;

alter table roba add constraint fk_roba_u_magacinu foreign key (sifra_magacina)
      references magacin (sifra_magacina) on delete restrict on update restrict;

