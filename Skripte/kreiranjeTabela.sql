/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     5/17/2018 2:39:34 AM                         */
/*==============================================================*/


drop table if exists kurs;

drop table if exists fakturisana_roba;

drop table if exists narucena_roba;

drop table if exists faktura;

drop table if exists porudzbina;

drop table if exists kupci;

drop table if exists roba;

drop table if exists jedinica_mere;

drop table if exists magacin;

drop table if exists korisnik;

/*==============================================================*/
/* Table: faktura                                               */
/*==============================================================*/
create table faktura
(
   sifra_fakture        varchar(20) not null,
   korisnicko_ime       varchar(20) not null,
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
   sifra_porudzbine     varchar(20),
   sifra_fakture        varchar(20) not null,
   komada_fakturisano   numeric(10,0) not null,
   primary key (sifra_robe, sifra_fakture)
);

/*==============================================================*/
/* Table: jedinica_mere                                         */
/*==============================================================*/
create table jedinica_mere
(
   redni_broj           int not null,
   naziv_mere           varchar(20) not null,
   primary key (redni_broj)
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
   pib                  varchar(15) not null,
   naziv_kupca          varchar(50) not null,
   naziv_kupca2         varchar(50),
   adresa_kupca         varchar(50),
   grad_kupca           varchar(30),
   drzava_kupca         varchar(30),
   primary key (pib)
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
   sifra_magacina       varchar(10) not null,
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
   datum_narucivanja    date not null,
   primary key (sifra_robe, sifra_porudzbine)
);

/*==============================================================*/
/* Table: porudzbina                                            */
/*==============================================================*/
create table porudzbina
(
   sifra_porudzbine     varchar(20) not null,
   sifra_magacina       varchar(10) not null,
   korisnicko_ime       varchar(20) not null,
   pib_kupca            varchar(15) not null,
   datum_porudzbine     date not null,
   primary key (sifra_porudzbine)
);

/*==============================================================*/
/* Table: roba                                                  */
/*==============================================================*/
create table roba
(
   sifra_robe           varchar(75) not null,
   jedinica_mere        int not null,
   interna_sifra_robe   varchar(5) not null,
   naziv_robe           varchar(100) not null,
   komada_u_setu        numeric(4,0),
   tezina_robe          decimal(10,2),
   kvalitet_robe        varchar(50),
   cena_evri            decimal(10,4) not null,
   cena_roni            decimal(10,4),
   primary key (sifra_robe)
);

alter table faktura add constraint fk_korisnik_faktura foreign key (korisnicko_ime)
      references korisnik (korisnicko_ime) on delete restrict on update restrict;

alter table fakturisana_roba add constraint fk_fakturisana_roba foreign key (sifra_fakture)
      references faktura (sifra_fakture) on delete restrict on update restrict;

alter table fakturisana_roba add constraint fk_fakturisana_roba1 foreign key (sifra_robe, sifra_porudzbine)
      references narucena_roba (sifra_robe, sifra_porudzbine) on delete restrict on update restrict;

alter table narucena_roba add constraint fk_narucena_roba foreign key (sifra_porudzbine)
      references porudzbina (sifra_porudzbine) on delete restrict on update restrict;

alter table narucena_roba add constraint fk_narucena_roba1 foreign key (sifra_robe)
      references roba (sifra_robe) on delete restrict on update restrict;

alter table porudzbina add constraint fk_korisnik_porudzbina foreign key (korisnicko_ime)
      references korisnik (korisnicko_ime) on delete restrict on update restrict;

alter table porudzbina add constraint fk_porudzbina_iz_magacina foreign key (sifra_magacina)
      references magacin (sifra_magacina) on delete restrict on update restrict;

alter table porudzbina add constraint fk_porudzbina_kupca foreign key (pib_kupca)
      references kupci (pib) on delete restrict on update restrict;

alter table roba add constraint fk_jedinica_mere_robe foreign key (jedinica_mere)
      references jedinica_mere (redni_broj) on delete restrict on update restrict;

