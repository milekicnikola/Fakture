package actions;

public class ActionManager {
	
	private static ActionManager instance = null;
	private DialogRobaAction roba = new DialogRobaAction();	

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
	
	

}
