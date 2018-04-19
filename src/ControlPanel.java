import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ControlPanel extends JPanel implements ActionListener{	////class loads image and pastes on panel. Image has all directions
	
	public static Image controlImage;
	private JButton back;
	
	
	public ControlPanel(){
		controlImage = new ImageIcon(HomeScreen.SOURCE_PATH+"resources/ControlPageCompressed.jpg").getImage();
	
		//back button
		back = new JButton("Home");
		//back.setBounds((int)(HomeScreen.WIDTH*0.1), (int)(HomeScreen.HEIGHT*0.8), 50, 50);
		back.setBackground(new Color(0,250,100, 255));
		back.setBorderPainted(false);
		add(back);
		back.addActionListener(this);
		System.out.println(back.getPreferredSize());
		
		repaint();
		}
	
	public void paintComponent(Graphics g){
		g.drawImage(controlImage, 0, 0, HomeScreen.WIDTH, HomeScreen.HEIGHT, this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(back)){
			HomeScreen.cards.show(HomeScreen.cardPanel, "Home");
		}
	}
	
}
