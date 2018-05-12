package actions;

public class ActionManager {

	private static ActionManager instance = null;
	private DialogRobaAction roba = new DialogRobaAction();
	private DialogMagacinAction magacin = new DialogMagacinAction();
	private DialogKupciAction kupci = new DialogKupciAction();
	private DialogKursAction kurs = new DialogKursAction();
	private DialogPorudzbinaAction porudzbina = new DialogPorudzbinaAction();	

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

}
