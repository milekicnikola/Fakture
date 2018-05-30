package actions;

public class ActionManager {

	private static ActionManager instance = null;
	private DialogRobaAction roba = new DialogRobaAction();
	private DialogMagacinAction magacin = new DialogMagacinAction();
	private DialogKupciAction kupci = new DialogKupciAction();
	private DialogKursAction kurs = new DialogKursAction();
	private DialogPorudzbinaAction porudzbina = new DialogPorudzbinaAction();
	private DialogFakturaAction faktura = new DialogFakturaAction();
	private DialogOtpremnicaAction otpremnica = new DialogOtpremnicaAction();
	private DialogOtvoreneStavkeAction stavke = new DialogOtvoreneStavkeAction();

	public static ActionManager getInstance() {
		if (instance == null) {
			instance = new ActionManager();
		}
		return instance;
	}

	public DialogRobaAction getDialogRobaAction() {
		return roba;
	}

	public void setDialogRobaAction(DialogRobaAction roba) {
		this.roba = roba;
	}

	public DialogMagacinAction getDialogMagacinAction() {
		return magacin;
	}

	public void setDialogMagacinAction(DialogMagacinAction magacin) {
		this.magacin = magacin;
	}

	public DialogKupciAction getDialogKupciAction() {
		return kupci;
	}

	public void setDialogKupciAction(DialogKupciAction kupci) {
		this.kupci = kupci;
	}

	public DialogKursAction getDialogKursAction() {
		return kurs;
	}

	public void setDialogKursAction(DialogKursAction kurs) {
		this.kurs = kurs;
	}

	public DialogPorudzbinaAction getDialogPorudzbinaAction() {
		return porudzbina;
	}

	public void setDialogPorudzbinaAction(DialogPorudzbinaAction porudzbina) {
		this.porudzbina = porudzbina;
	}

	public DialogFakturaAction getDialogFakturaAction() {
		return faktura;
	}

	public void setDialogFakturaAction(DialogFakturaAction faktura) {
		this.faktura = faktura;
	}

	public DialogOtpremnicaAction getDialogOtpremnicaAction() {
		return otpremnica;
	}

	public void setDialogOtpremnicaAction(DialogOtpremnicaAction otpremnica) {
		this.otpremnica = otpremnica;
	}
	
	public DialogOtvoreneStavkeAction getDialogOtvoreneStavkeAction() {
		return stavke;
	}

	public void setDialogOtvoreneStavkeAction(DialogOtvoreneStavkeAction stavke) {
		this.stavke = stavke;
	}
	
	

}
