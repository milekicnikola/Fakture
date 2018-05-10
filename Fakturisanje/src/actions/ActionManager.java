package actions;

public class ActionManager {
	
	private static ActionManager instance = null;
	private DialogRobaAction roba = new DialogRobaAction();
	private DialogMagacinAction magacin = new DialogMagacinAction();	

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
	
	

}
