package gui;

import javax.swing.JToolBar;

import actions.ActionManager;

public class ToolBar extends JToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ToolBar() {

		add(ActionManager.getInstance().getDialogRobaAction());
		add(ActionManager.getInstance().getDialogMagacinAction());
		add(ActionManager.getInstance().getDialogKupciAction());
		add(ActionManager.getInstance().getDialogKursAction());
		add(ActionManager.getInstance().getDialogPorudzbinaAction());
		add(ActionManager.getInstance().getDialogFakturaAction());
		add(ActionManager.getInstance().getDialogOtpremnicaAction());
		add(ActionManager.getInstance().getDialogOtvoreneStavkeAction());
	}

}
