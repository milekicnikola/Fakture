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
	private DialogOtvoreneStavkeAction otvoreneStavke = new DialogOtvoreneStavkeAction();
	private DialogPoslateStavkeAction poslateStavke = new DialogPoslateStavkeAction();
	private DialogFakturisaneStavkeAction fakturisaneStavke = new DialogFakturisaneStavkeAction();

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
		return otvoreneStavke;
	}

	public void setDialogOtvoreneStavkeAction(DialogOtvoreneStavkeAction otvoreneStavke) {
		this.otvoreneStavke = otvoreneStavke;
	}

	public DialogPoslateStavkeAction getDialogPoslateStavkeAction() {
		return poslateStavke;
	}

	public void setDialogPoslateStavkeAction(DialogPoslateStavkeAction poslateStavke) {
		this.poslateStavke = poslateStavke;
	}

	public DialogFakturisaneStavkeAction getDialogFakturisaneStavkeAction() {
		return fakturisaneStavke;
	}

	public void setDialogFakturisaneStavkeAction(DialogFakturisaneStavkeAction fakturisaneStavke) {
		this.fakturisaneStavke = fakturisaneStavke;
	}

}
