package pseudocode;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;


public class Hoop extends JPanel{

	Image hoopImage;
	int width = HomeScreen.WIDTH/3;
	int height = HomeScreen.HEIGHT/3;
	static int vgap = 45;
	static int hgap = 15;
	public static int EACH_HOOP_HEIGHT;
	
	
	public Hoop(String imgName){
		if(imgName.equals("blueHoop")) hoopImage = ArcadePanel.blueHoop;
		else if(imgName.equals("greenHoop")) hoopImage = ArcadePanel.greenHoop;
		else if(imgName.equals("greyHoop")) hoopImage = ArcadePanel.greyHoop;
		repaint();
	}
	
	public void paintComponent(Graphics g){
		
		g.drawImage(hoopImage,hgap, 0, hoopImage.getWidth(null)-2*hgap,hoopImage.getHeight(null)-2*vgap,this);
		EACH_HOOP_HEIGHT = hoopImage.getHeight(null)-2*vgap;
		//System.out.println(EACH_HOOP_HEIGHT);
	}
	
}
