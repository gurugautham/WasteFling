import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

class TimedPanel extends JPanel implements KeyListener, MouseListener, Runnable, ActionListener
{
	//Image of bball hoop
	public static Image blueHoop, greyHoop, greenHoop, orangeHoop/*, correct, incorrect*/;
	private WasteGenerator wasteGenerator;

	//Field Vars
	private int widthVX;
	public Waste currentWaste;
	static Thread runner;
	private Thread timer;
	
	//Related to levels and toughness of game
		private int score = 0, totalSecondsOfGame = 60;
		private boolean gameOver = false;
		
		
		private boolean spacePressedCurrently, startRunner;
		private long keyPressedMillis, keyPressLength;
		
		private boolean userOnPanel;

		private JButton back;
		private JTextField scoreboard, answer, time;

	public TimedPanel(JFrame homeFrame){
		
		setLayout(null);
		
		//create and init back button
		back = new JButton("Home");
		//back.setLocation((int)(HomeScreen.WIDTH*0.5)-25, (int)(HomeScreen.HEIGHT*0.1)/2);
		back.setBounds((int)(HomeScreen.WIDTH*0.5)-34, 5, 67, 26);
		back.setBackground(new Color(0,250,100, 255));
		back.setBorderPainted(false);
		add(back);

		//create and init textfields
		scoreboard = new JTextField();
		scoreboard.setHorizontalAlignment(JTextField.CENTER);
		scoreboard.setBounds(160, (int)(HomeScreen.HEIGHT*0.9)+5, 100, 30);
		scoreboard.setBackground(new Color(0,250,100, 255));
		scoreboard.setText("Score: 0");
		add(scoreboard);

		answer = new JTextField();
		answer.setHorizontalAlignment(JTextField.CENTER);
		answer.setBounds(160+105, (int)(HomeScreen.HEIGHT*0.9)+5, 180, 30);
		answer.setBackground(new Color(0,250,100, 255));
		answer.setText("Correct Waste: ");
		add(answer);
		
		time = new JTextField();
		time.setHorizontalAlignment(JTextField.CENTER);
		time.setBounds(5, (int)(HomeScreen.HEIGHT*0.9)+5, 150, 30);
		time.setBackground(new Color(0,250,100, 255));
		add(time);
		
		//add Listeners
		back.addActionListener(this);
		addKeyListener(this);
		addMouseListener(this);

		//init wasteGen, and generate new Waste
		wasteGenerator = new WasteGenerator(this);
		

		//load imgs
		try {
			blueHoop = ImageIO.read(new File(HomeScreen.SOURCE_PATH+"resources/blueHoopFinal.png"));
			greyHoop = ImageIO.read(new File(HomeScreen.SOURCE_PATH+"resources/greyHoopFinal.png"));
			greenHoop = ImageIO.read(new File(HomeScreen.SOURCE_PATH+"resources/greenHoopFinal.png"));
			orangeHoop = ImageIO.read(new File(HomeScreen.SOURCE_PATH+"resources/redHoopFinal.png"));
			//correct = wasteGenerator.scaleImage(ImageIO.read(new File(HomeScreen.SOURCE_PATH+"resources/correct.png")));
			//incorrect = wasteGenerator.scaleImage(ImageIO.read(new File(HomeScreen.SOURCE_PATH+"resources/incorrect.png")));

		} catch (IOException e) {
			e.printStackTrace();
		}

		startGame(false);

		homeFrame.addKeyListener(this);
	}


	public void paintComponent(Graphics g){
		/*
		 * 1.draw images of hoops
		 * 2.draw images of waste
		 */
		super.paintComponent(g);
		draw(g);
		if(!gameOver) currentWaste.draw(g);
	}
	
	public void startGame(boolean startTimer){
		userOnPanel = true;
		gameOver = false;
		currentWaste = wasteGenerator.generate();
		score = 0;
		scoreboard.setText("Score: 0");
		repaint();
		runner = new Thread(this);
		if(startTimer){
		timer = new Thread(this);
		timer.start();
		}
		spacePressedCurrently = false;
	}
	
	public boolean checkForExit(){
		return !userOnPanel;
	}
	
	public synchronized void reset(){							//method to be called after waste is shot
		int currentWasteScore = currentWaste.checkShot();
		score+=currentWasteScore;
		boolean shotCorrect = currentWasteScore>0;
				
		repaint();
		try {										//pause to show whether user scored correctly or incorrectly
			Thread.currentThread().sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		if(gameOver){
			timer = null;
			//leaderboard stuff
		}

		//create new waste and new Thread to paint (cycling)
		else{
			currentWaste = wasteGenerator.generate();
			repaint();
			runner = new Thread(this);
			spacePressedCurrently = false;
		}
		scoreboard.setText("Score: "+score);
		//System.out.println("Score: "+score);
	}

	public void draw(Graphics g){
		g.drawImage(HomeScreen.BgImage, 0, 0, this);
		
		g.drawImage(blueHoop,   Hoop.hgap                       , 0, HomeScreen.WIDTH/4-2*Hoop.hgap, blueHoop.getHeight(null)- 2*Hoop.vgap-50, this);
		g.drawImage(greyHoop,   Hoop.hgap+HomeScreen.WIDTH/4    , 0, HomeScreen.WIDTH/4-2*Hoop.hgap, greyHoop.getHeight(null)- 2*Hoop.vgap-50, this);
		g.drawImage(greenHoop,  Hoop.hgap+2*(HomeScreen.WIDTH/4), 0, HomeScreen.WIDTH/4-2*Hoop.hgap, greenHoop.getHeight(null)-2*Hoop.vgap-50, this);
		g.drawImage(orangeHoop, Hoop.hgap+3*(HomeScreen.WIDTH/4), 0, HomeScreen.WIDTH/4-2*Hoop.hgap, orangeHoop.getHeight(null)-2*Hoop.vgap-50, this);

		if(gameOver){
			g.setFont(new Font("Arial Black", Font.BOLD, 100));
			g.setColor(Color.PINK);
			g.drawString("TIME'S UP", (int)(0.5*HomeScreen.WIDTH/2), HomeScreen.HEIGHT/2);
		}
		
	}

	public void run(){								//Runnable method, used to paint the shooting animation,
		
		if(Thread.currentThread().equals(runner)){
		
		// calls repaint every 50 milllis
		int sleepTime = 37;
		int totalIs = 20;
		
		answer.setText("Current Answer: "+currentWaste.getTypeName());  //set answer for user to actively learn
		
		for(int i =0; i<totalIs; i++){
			currentWaste.move(i, totalIs, sleepTime, 6-((double)keyPressLength/100)); // change space bar constant later
			repaint();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reset();
		}
		
		else if(Thread.currentThread().equals(timer)){
			for(int i = totalSecondsOfGame; i>=0; i--){
				time.setText("Time left: "+i+" seconds");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(checkForExit()) return;
			}
			
			gameOver = true;
			reset();
		}
	}

	//Runnable Methods

	// Listener Methods

		// check error if pace is pressed more than once
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_SPACE || e.getKeyCode()==KeyEvent.VK_UP){
				keyPressLength = /*TimeUnit.MILLISECONDS.toSeconds(*/System.currentTimeMillis() - keyPressedMillis;
				//spacePressedCurrently = false;

				System.out.println("Key Released after: "+keyPressLength);

				if((int)(keyPressLength)>500) keyPressLength=500;
				else if((int)(keyPressLength)<100)keyPressLength=100;
				if(startRunner){
					System.out.println("\tRunner Starting");
					runner.start();
				}
			}
		}
		public void keyPressed(KeyEvent e) {
			if(spacePressedCurrently) return;
			if(!spacePressedCurrently && (e.getKeyCode()==KeyEvent.VK_SPACE || e.getKeyCode()==KeyEvent.VK_UP)){
				keyPressedMillis = System.currentTimeMillis();
				spacePressedCurrently = true;
				//	System.out.println("Key Pressed @: "+keyPressedMillis);
				startRunner = true;
			}
			else{	//button to move left or right pressed
				startRunner = currentWaste.move(e);
				//System.out.println("Start Runner: "+startRunner);

			}
			repaint();
		}
		public void mouseClicked(MouseEvent e) {		}
		public void mousePressed(MouseEvent e) {requestFocusInWindow();}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}

		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(back)){
				userOnPanel = false;
				HomeScreen.cards.show(HomeScreen.cardPanel, "Home");
			}
		}

	
}
