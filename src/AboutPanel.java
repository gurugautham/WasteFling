import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;


public class AboutPanel extends JPanel implements ActionListener{


	private JButton back;
	private Image imageToShow, rImage, cImage, gImage, hImage;
	private Image rInfo, cInfo, gInfo, hInfo;

	private JScrollPane allScroll;
	private JMenuBar menuBar;
	private JMenu waste;
	private JMenuItem recyclable, compostable, garbage, hazardous;
	private JMenuItem rI, cI, gI, hI;

	private JMenu info;



	public AboutPanel(){
		setLayout(null);
		//back button
		back = new JButton("Home");
		//allScroll = new JScrollPane();

		//back.setBounds((int)(HomeScreen.WIDTH*0.1), (int)(HomeScreen.HEIGHT*0.8), 50, 50);
		back.setBackground(new Color(0,250,100, 255));
		back.setBorderPainted(false);
		add(back);
		back.addActionListener(this);
		System.out.println(back.getPreferredSize());

		back.setBounds((int)(HomeScreen.WIDTH*0.5)-34, 5, 69, 26);

		gImage = scaleToFitScrollPane(new ImageIcon(HomeScreen.SOURCE_PATH+"resources/TrashAbout.PNG").getImage());
		rImage = scaleToFitScrollPane(new ImageIcon(HomeScreen.SOURCE_PATH+"resources/RecyclableAbout.PNG").getImage());
		cImage = scaleToFitScrollPane(new ImageIcon(HomeScreen.SOURCE_PATH+"resources/CompostableAbout.PNG").getImage());
		hImage = scaleToFitScrollPane(new ImageIcon(HomeScreen.SOURCE_PATH+"resources/HazardousAbout.png").getImage());

		rInfo = scaleToFitScrollPane(new ImageIcon(HomeScreen.SOURCE_PATH+"resources/RecyclingInfographic.png").getImage());
		hInfo = scaleToFitScrollPane(new ImageIcon(HomeScreen.SOURCE_PATH+"resources/HazardousInfographic.png").getImage());
		gInfo = scaleToFitScrollPane(new ImageIcon(HomeScreen.SOURCE_PATH+"resources/GarbageInfographic.jpg").getImage());
		cInfo = scaleToFitScrollPane(new ImageIcon(HomeScreen.SOURCE_PATH+"resources/CompostingImportance.jpg").getImage());

		repaint();
	}

	public void paintComponent(Graphics g){
		g.drawImage(HomeScreen.BgImage, 0, 0, HomeScreen.WIDTH, HomeScreen.HEIGHT, this);

		if(!(allScroll==null)){
			remove(allScroll);
			allScroll = null;
		}
		if(imageToShow==null){
			allScroll = new JScrollPane(new JLabel("Use the Menus above to explore more about waste!", SwingConstants.CENTER));
			allScroll.setBounds(HomeScreen.WIDTH/2-350,(int) (HomeScreen.HEIGHT*0.2),700, 500);
			add(allScroll);
			allScroll.requestFocusInWindow();

			allScroll.revalidate();
		}
		else {
			allScroll = new JScrollPane(new JLabel(new ImageIcon(imageToShow)));
			allScroll.setBounds(HomeScreen.WIDTH/2-350,(int) (HomeScreen.HEIGHT*0.2),700, 500);
			add(allScroll);
			allScroll.requestFocusInWindow();

			allScroll.revalidate();
			//allScroll.repaint();

		}
	}

	public void createMenuNPanels(){

		// Create the menu bar
		menuBar = new JMenuBar();
		// menuBar.setBackground(new Color(0,250,100, 25));
		// Create a menu
		waste = new JMenu("Types of Waste");
		info = new JMenu("More Info");

		menuBar.add(waste);
		menuBar.add(info);
		//waste.setBackground(new Color(0,250,100, 255));
		// Create a menu item
		recyclable = new JMenuItem("Recyclables");
		compostable = new JMenuItem("Compostables");
		garbage = new JMenuItem("Garbage");
		hazardous = new JMenuItem("Hazardous");

		rI = new JMenuItem("Recycle");
		cI = new JMenuItem("Compost");
		gI = new JMenuItem("Garbage");
		hI = new JMenuItem("Hazardous Materials!");



		recyclable.addActionListener(this);
		compostable.addActionListener(this);
		garbage.addActionListener(this);
		hazardous.addActionListener(this);

		rI.addActionListener(this);
		cI.addActionListener(this);
		gI.addActionListener(this);
		hI.addActionListener(this);

		waste.add(recyclable);
		waste.add(compostable);
		waste.add(garbage);
		waste.add(hazardous);

		info.add(rI);
		info.add(cI);
		info.add(gI);
		info.add(hI);

		// Install the menu bar in the frame
		HomeScreen.homeFrame.setJMenuBar(menuBar);

		/*
	    DisplayPanel blank = new DisplayPanel();
		DisplayPanel recycle = new DisplayPanel(new ImageIcon(HomeScreen.SOURCE_PATH+"RecyclableAbout.PNG").getImage());
		DisplayPanel compost = new DisplayPanel(new ImageIcon(HomeScreen.SOURCE_PATH+"CompostableAbout.PNG").getImage());
		DisplayPanel garbage = new DisplayPanel(new ImageIcon(HomeScreen.SOURCE_PATH+"TrashAbout.PNG").getImage());

		blank.showImage();
		 */

	}

	public void removeMenuNPanels(){

		menuBar.removeAll();
		remove(menuBar);

		recyclable.removeActionListener(this);
		compostable.removeActionListener(this);
		garbage.removeActionListener(this);
		hazardous.removeActionListener(this);

		rI.removeActionListener(this);
		cI.removeActionListener(this);
		gI.removeActionListener(this);
		hI.removeActionListener(this);

		recyclable = null;
		compostable = null;
		garbage = null;
		hazardous = null;

		rI = null;
		cI = null;
		gI = null;
		hI = null;




	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(back)){
			removeMenuNPanels();
			HomeScreen.cards.show(HomeScreen.cardPanel, "Home");
		}

		if(e.getSource().equals(recyclable)){
			imageToShow = rImage;
			repaint();
		}
		else if(e.getSource().equals(compostable)){
			imageToShow = cImage;
			repaint();
		}
		else if(e.getSource().equals(garbage)){
			imageToShow = gImage;
			repaint();
		}
		else if(e.getSource().equals(hazardous)){
			imageToShow = hImage;
			repaint();
		}
		else if(e.getSource().equals(rI)){
			imageToShow = rInfo;
			repaint();
		}
		else if(e.getSource().equals(gI)){
			imageToShow = gInfo;
			repaint();
		}
		else if(e.getSource().equals(cI)){
			imageToShow = cInfo;
			repaint();
		}
		else if(e.getSource().equals(hI)){
			imageToShow = hInfo;
			repaint();
		}


	}

	public Image scaleToFitScrollPane(Image image){
		double ratio = (double)image.getHeight(null)/image.getWidth(null);
		int newHeight =  (int) (660 * ratio);
		return image.getScaledInstance(700, newHeight, Image.SCALE_DEFAULT);
	}
}