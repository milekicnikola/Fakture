package gui.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class DialogStatusBar extends JPanel {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StatusPane statusDialog;
	private StatusPane statusState;
	private StatusPane statusRow;

	public DialogStatusBar() {

		setLayout(new GridLayout(1, 3, 5, 5));
		setBackground(Color.lightGray);
		setBorder(BorderFactory.createLineBorder(Color.darkGray));

		statusDialog = new StatusPane("");
		statusState = new StatusPane("");
		statusRow = new StatusPane("");

		add(statusDialog);
		add(statusState);
		add(statusRow);
	}

	class StatusPane extends JLabel {
        
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public StatusPane(String text) {
			super(text);
			setHorizontalAlignment(CENTER);
			setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			setPreferredSize(new Dimension(200, 25));
		
		}
	
	}

	public StatusPane getStatusDialog() {
		return statusDialog;
	}	

	public StatusPane getStatusRow() {
		return statusRow;
	}	

	public StatusPane getStatusState() {
		return statusState;
	}	

}
