package pseudocode;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ControlPanel extends JPanel{			//class loads image and pastes on panel. Image has all directions
	
	public static Image controlImage;

	
	
	public ControlPanel(){
		controlImage = new ImageIcon(HomeScreen.SOURCE_PATH+"controlPageCompressed.jpg").getImage();
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(controlImage, 0, 0, HomeScreen.WIDTH, HomeScreen.HEIGHT, this);
	}
	
}