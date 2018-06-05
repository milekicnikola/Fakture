package util;

public class Roba {

	private String sifra;
	private String porudzbina;
	private String datum;
	private String komada;

	public Roba(String sifraR, String porudzbinaR, String datumR, String komadaR) {
		this.sifra = sifraR;
		this.porudzbina = porudzbinaR;
		this.datum = datumR;
		this.komada = komadaR;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getPorudzbina() {
		return porudzbina;
	}

	public void setPorudzbina(String porudzbina) {
		this.porudzbina = porudzbina;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getKomada() {
		return komada;
	}

	public void setKomada(String komada) {
		this.komada = komada;
	}

}
