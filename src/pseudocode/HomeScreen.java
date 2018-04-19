package pseudocode;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;



public class HomeScreen{

	JFrame homeFrame;
	
	// Card Layout requirements and all panel refs
	private CardLayout cards;
	private JPanel cardPanel;
	private HomePanel hp;
	private static ArcadePanel ap;
	private ControlPanel cp;
	
	//Bacground images
	public static Image BgImage, HomeBgImage;
	
	public static final int WIDTH = 1280, HEIGHT = 720;
	public static final String SOURCE_PATH = "resources/";

	public HomeScreen(){							//create frame and card panel (layout of card Layout). Add all panels to card panel
		cards = new CardLayout();
		
		homeFrame = new JFrame("Waste Fling");
		homeFrame.setSize(WIDTH, HEIGHT);
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeFrame.setLocation(0, 0);
		homeFrame.setResizable(false);
		
		cardPanel = new JPanel();
		cardPanel.setLayout(cards);
		
		hp = new HomePanel(homeFrame);
		ap = new ArcadePanel(homeFrame);
		cp = new ControlPanel();

		
		
		cardPanel.add(ap, "Arcade");
		cardPanel.add(cp, "Controls");
		cardPanel.add(hp, "Home");
		
		cards.show(cardPanel, "Home");
				
		homeFrame.getContentPane().add(cardPanel);
		homeFrame.setVisible(true);
		
		cardPanel.requestFocusInWindow();
		
	}
	
	
	static{										//load images
		try {
			HomeBgImage = ImageIO.read(new File(SOURCE_PATH+"test2Compressed.jpg"));
			BgImage = ImageIO.read(new File(SOURCE_PATH+"bg1Compressed.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}



	class HomePanel extends JPanel				//home panel (JPanel with buttons to go to different parts of game)
	{
		JFrame frameRef;
		JButton about, arcade, timed, control, legend;
		ShowPanel sp;
		ButtonPanel bp;
		//int width, height;

		public HomePanel(JFrame frameIn){
			repaint();
			frameRef=frameIn;
			setLayout(new GridLayout(2,1,10,5));
			sp = new ShowPanel();
			bp = new ButtonPanel(this);
			
			add(sp);
			add(bp);
		}

		public void paintComponent(Graphics g){
			g.drawImage(HomeBgImage, 0, 0/*, WIDTH, HEIGHT*/, this);
		}

		class ShowPanel extends JPanel			//a transparent panel (simply to fill slot in grid layout)
		{
			public ShowPanel(){
				setBackground(new Color(0,0,0,0));
			}

		}

		class ButtonPanel extends JPanel implements ActionListener				//bottom half of homePanel, ha 5 buttons
		{
			
			public HomePanel homePanelRef;
			
			public ButtonPanel(HomePanel homePanelIn){
				
				homePanelRef = homePanelIn;
				
				//make buttons look more professional
				try {
					UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				setLayout(new FlowLayout());
				setBackground(new Color(0,0,0,0));

				about = new JButton("About Waste");
				arcade = new JButton("Arcade");
				timed = new JButton("Timed");
				control = new JButton("Controls");
				legend = new JButton("Legend");

				//set button color to aqua greenish
				about.setBackground(new Color(0,250,100, 255));
				about.setBorderPainted(false);
				arcade.setBackground(new Color(0,250,100, 255));
				arcade.setBorderPainted(false);
				timed.setBackground(new Color(0,250,100, 255));
				timed.setBorderPainted(false);
				control.setBackground(new Color(0,250,100, 255));
				control.setBorderPainted(false);
				legend.setBackground(new Color(0,250,100, 255));
				legend.setBorderPainted(false);
				
				about.addActionListener(this);
				arcade.addActionListener(this);
				timed.addActionListener(this);
				control.addActionListener(this);
				legend.addActionListener(this);

				add(about);
				add(arcade);
				add(timed);
				add(control);
				add(legend);


			}

			//show panel on card layout if button is clicked
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("About Waste")) System.out.println("About Page open");
				else if(e.getActionCommand().equals("Arcade")){
					cards.show(cardPanel, "Arcade");
					ap.requestFocusInWindow();
					}
				else if(e.getActionCommand().equals("Timed")) System.out.println("Timed open");
				else if(e.getActionCommand().equals("Controls")){cards.show(cardPanel, "Controls");}
				else if(e.getActionCommand().equals("Legend")) System.out.println("Legend open");

			}
		}

	}


	//arcadePanel getter Method
	public static ArcadePanel getArcadePanel() {
		return ap;
	}
}
