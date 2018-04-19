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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;



public class HomeScreen{

	public static JFrame homeFrame;
	
	// Card Layout requirements and all panel refs
	public static CardLayout cards;
	public static JPanel cardPanel;
	private HomePanel hp;
	private static ArcadePanel ap;
	private ControlPanel cp;
	private static TimedPanel tp;
	private LegendPanel lp;
	private AboutPanel aBP;
	
	//Background images
	public static Image BgImage, HomeBgImage;
	
	public static final int WIDTH = 1280, HEIGHT = 720;
	public static final String SOURCE_PATH = ""; //for school code it is "../"

	public HomeScreen(){							//create frame and card panel (layout of card Layout). Add all panels to card panel
		
		LoadingScreen ls = new LoadingScreen();
		
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
		tp = new TimedPanel(homeFrame);
		cp = new ControlPanel();
		lp = new LegendPanel();
		aBP = new AboutPanel();

		
		
		cardPanel.add(ap, "Arcade");
		cardPanel.add(cp, "Controls");
		cardPanel.add(hp, "Home");
		cardPanel.add(tp, "Timed");
		cardPanel.add(lp, "Legend");
		cardPanel.add(aBP, "About");
		
		cards.show(cardPanel, "Controls");
				
		homeFrame.getContentPane().add(cardPanel);
		homeFrame.setVisible(true);
		
		cardPanel.requestFocusInWindow();
		
		ls.setVisible(false);
		ls = null;
		
		JOptionPane.showMessageDialog(HomeScreen.homeFrame, "Welcome to Waste Fling! You will first be directed to the Controls page where you can learn how to play the game.\n"
				+ "After this, go Home and visit the About Waste page to research how waste should be sorted. Test your knowledge\nby playing Arcade mode, and see how fast you are in"
				+ " Timed mode. Good Luck!");
		
	}
	
	
	static{										//load images
		try {
			HomeBgImage = ImageIO.read(new File(SOURCE_PATH+"resources/test2Compressed.jpg"));
			BgImage = ImageIO.read(new File(SOURCE_PATH+"resources/bg1Compressed.jpg"));
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
			/*
			try {
				factoryWorker = Font.createFont(Font.TRUETYPE_FONT, new File("src/factory worker.ttf")).deriveFont(12f);
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/factory worker.ttf")));
			} catch (FontFormatException | IOException e) {
				e.printStackTrace();
			}
			 */
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

		class ButtonPanel extends JPanel implements ActionListener			//bottom half of homePanel, has 5 button
		{
			
			public HomePanel homePanelRef;
			
			public ButtonPanel(HomePanel homePanelIn){
				
				homePanelRef = homePanelIn;
				
				/*
				try {
					UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				//width = getWidth();
				//height = getHeight();
				//System.out.println("Got width info" + width);
				setLayout(new FlowLayout());
				setBackground(new Color(0,0,0,0));

				//buttons init and declared
				about = new JButton("About Waste");
				arcade = new JButton("Arcade");
				timed = new JButton("Timed");
				control = new JButton("Controls");
				legend = new JButton("Legend");

				System.out.println(HomeScreen.HEIGHT);
				System.out.println(HomeScreen.WIDTH);
				//about.setFont(factoryWorker);

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
				if(e.getActionCommand().equals("About Waste")){
					aBP.createMenuNPanels();
					cards.show(cardPanel, "About");
				}
				else if(e.getActionCommand().equals("Arcade")){
					ap.startGame();
					cards.show(cardPanel, "Arcade");
					ap.requestFocusInWindow();
					}
				else if(e.getActionCommand().equals("Timed")){
					tp.startGame(true);
					cards.show(cardPanel, "Timed");
					tp.requestFocusInWindow();
				}
				else if(e.getActionCommand().equals("Controls")){cards.show(cardPanel, "Controls");}
				else if(e.getActionCommand().equals("Legend")){cards.show(cardPanel, "Legend");}

			}
		}

	}


	//arcadePanel getter Method
	public static ArcadePanel getArcadePanel() {
		return ap;
	}
}

class LoadingScreen extends JFrame{
	
	public LoadingScreen(){
	
		JLabel loading = new JLabel(new ImageIcon(HomeScreen.SOURCE_PATH+"resources/loading.gif"));
		
		
		setResizable(false);
		setSize(100,100);
		setVisible(true);
		getContentPane().add(loading);
		pack();
	
	}
}
