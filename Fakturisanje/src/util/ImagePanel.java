package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image img;

	  public ImagePanel(String img) {
	    this(new ImageIcon(img).getImage());
	    
	  }
	  
	  public ImagePanel(Image img) {
	    this.img = img;
	    setBackground(Color.WHITE);
	    
	  }

	  public void paintComponent(Graphics g) {
		  
	    g.drawImage(img, (int)(this.getSize().getWidth()-img.getWidth(null))/2,
	    				 (int)(this.getSize().getHeight()-img.getHeight(null))/2, null);
	    g.setColor(Color.blue);
	  }

}
