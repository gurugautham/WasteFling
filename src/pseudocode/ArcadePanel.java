package pseudocode;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class ArcadePanel extends JPanel implements KeyListener, MouseListener, Runnable //runnable is interface for multi-threading
{
	//Image of bball hoops
	public static Image blueHoop, greyHoop, greenHoop, orangeHoop;
	
	
	private WasteGenerator wasteGenerator;

	// Field Vars
	private int widthVX;
	public Waste currentWaste;
	public Timer timer;
	static Thread runner;

	public ArcadePanel(JFrame homeFrame){
		// add Listeners
		addKeyListener(this);
		addMouseListener(this);
		homeFrame.addKeyListener(this);
		
		//init wasteGen, and generate a new Waste
		wasteGenerator = new WasteGenerator();
		currentWaste = wasteGenerator.generate();
		
		//load imgs
		try {
			blueHoop = ImageIO.read(new File(HomeScreen.SOURCE_PATH+"blueHoopFinal.png"));
			greyHoop = ImageIO.read(new File(HomeScreen.SOURCE_PATH+"greyHoopFinal.png"));
			greenHoop = ImageIO.read(new File(HomeScreen.SOURCE_PATH+"greenHoopFinal.png"));
			orangeHoop = ImageIO.read(new File(HomeScreen.SOURCE_PATH+"orangeHoopFinal.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		repaint();
		runner = new Thread(this);
		timer = new Timer();
	}


	public void paintComponent(Graphics g){
		/*
		 * 1.draw images of hoops
		 * 2.draw images of waste
		 */
		super.paintComponent(g);
		draw(g);
		currentWaste.draw(g);
	}
	
	public void reset(){ 							//method to be called when waste is shot
		currentWaste = wasteGenerator.generate();
		runner = new Thread(this);
	}

	public void draw(Graphics g){
		g.drawImage(HomeScreen.BgImage, 0, 0, this);
		g.drawImage(blueHoop,Hoop.hgap, 0, blueHoop.getWidth(null)-2*Hoop.hgap,blueHoop.getHeight(null)-2*Hoop.vgap,this);
		g.drawImage(greyHoop,Hoop.hgap+HomeScreen.WIDTH/3, 0, greyHoop.getWidth(null)-2*Hoop.hgap,greyHoop.getHeight(null)-2*Hoop.vgap,this);
		g.drawImage(greenHoop,Hoop.hgap+2*(HomeScreen.WIDTH/3), 0, greenHoop.getWidth(null)-2*Hoop.hgap,greenHoop.getHeight(null)-2*Hoop.vgap,this);

	}

	public void run(){								//Runnable method, used to paint the shooting animation,
		int increment = 20;							// calls repaint every 50 milllis
			for(int i =0; i<20; i++){
			if(i==14) increment = -10;
			currentWaste.move(0, -increment);
			repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		currentWaste = wasteGenerator.generate();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
		reset();
	}


	/*
			for(int i = 0; i<10; i++){
				currentWaste.imageY--;
				System.out.println(currentWaste.imageY);
				timer.pause(100);

				}
	 */

	//KeyListener Methods

	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		
		currentWaste.move(e);

		repaint();
		runner = new Thread(this);
	}
	public void mouseClicked(MouseEvent e) {		}
	public void mousePressed(MouseEvent e) {requestFocusInWindow();}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}


}