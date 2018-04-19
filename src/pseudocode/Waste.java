package pseudocode;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Waste {				//class reperesenting the waste user can shoot (has info such as Image and X/Y coordinates)
	
	public String name;
	public Image currentImage;
	//image vars are top left
	private int imageX, imageY, imageVX, imageWidth, imageHeight;
	private boolean launched; 
	
	public Waste(String universalName){
		currentImage = WasteGenerator.imageCatalog.get(universalName);
		imageX = HomeScreen.WIDTH/2-currentImage.getWidth(null)/2;
		imageY = (int) (0.7*HomeScreen.HEIGHT);
		imageVX = 20;
		imageWidth = currentImage.getWidth(null);
		imageHeight = currentImage.getHeight(null);
		name = universalName;
		//if(loaded)
	}
	
	public void draw(Graphics g){
		g.drawImage(currentImage, imageX, imageY, getCurrentWidth(), getCurrentHeight(), HomeScreen.getArcadePanel());
	}
	
	private int getCurrentHeight() {
		return imageWidth;
	}

	private int getCurrentWidth() {
		return imageHeight;
	}
	
	//move based on keyboard input (method called from ArcadePanel keyPressed()) 
	public void move(KeyEvent e){
		int keyCode = e.getKeyCode();
		if(keyCode==KeyEvent.VK_RIGHT){
			imageX+=imageVX;
			if(imageX+imageWidth>HomeScreen.WIDTH) imageX=HomeScreen.WIDTH-imageWidth;
		}
		else if(keyCode==KeyEvent.VK_LEFT){
			imageX-=imageVX;
			if(imageX<0) imageX = 0;
		}
		
		else if(keyCode==KeyEvent.VK_SPACE){
			if(!launched){
				ArcadePanel.runner.start();
				launched = true;
			}
			//needGenerate = true;
		}
		
	}
	
	//overloaded move method for shooting the waste
	//to be improved...
	public void move(int incrementX, int incrementY){
		imageX+=incrementX;
		imageY+=incrementY;
	}
}
