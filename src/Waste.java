import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class Waste {			  //class representing the waste user can shoot (has info such as Image and X/Y coordinates)

	public String name;
	public Image currentImage;
	private JPanel drawingPanel;
	//image vars are top left
	private int imageX, imageY, imageVX;				//image X and Y are center of image
	private int imageWidth, imageHeight, sizeIncrementX, sizeIncrementY;
	private int rawImageWidth, rawImageHeight;
	private boolean launched, launchOver, parabolicInit=false, correctVacinity, correctHoop; 
	int startXValueForCurve, type;
	private Font font;

	public Waste(String universalName, int typeIn, JPanel parentPanel){
		currentImage = WasteGenerator.imageCatalog.get(universalName);
		//imageX = HomeScreen.WIDTH/2-currentImage.getWidth(null)/2;
		imageX = HomeScreen.WIDTH/2;
		imageY = (int) (0.75*HomeScreen.HEIGHT);
		imageVX = 20;
		imageWidth = currentImage.getWidth(null);
		imageHeight = currentImage.getHeight(null);
		rawImageWidth = imageWidth;
		rawImageHeight = imageHeight;
		name = universalName;
		type = typeIn;
		drawingPanel = parentPanel;
		font = new Font("Arial Black", Font.BOLD, 100);
		//if(loaded)
	}

	public int checkShot(){
		launchOver = true;
		if(type==1 && imageX>= 0  && imageX<HomeScreen.WIDTH/4) correctVacinity = true;
		else if(type==2 && imageX>=HomeScreen.WIDTH/4 && imageX<HomeScreen.WIDTH/2) correctVacinity = true;
		else if (type==3 && imageX>=HomeScreen.WIDTH/2 && imageX<3*HomeScreen.WIDTH/4) correctVacinity = true;
		else if(type==4 && imageX>=3*HomeScreen.WIDTH/4 && imageX<= HomeScreen.WIDTH) correctVacinity = true;

		//params of first hoop rect: g.drawRect(HomeScreen.WIDTH/15+15, HomeScreen.HEIGHT/3-5, 116,56);


		if(correctVacinity){
			if(type==1 && imageX>=HomeScreen.WIDTH/15+15  && imageX<HomeScreen.WIDTH/15+15+116 && imageY>=HomeScreen.HEIGHT/3-5 && imageY<HomeScreen.HEIGHT/3-5+56){
				correctHoop=true;
				correctVacinity=false;
			}
			else if(type==2 && imageX>=HomeScreen.WIDTH/15+15+HomeScreen.WIDTH/4  && imageX<(HomeScreen.WIDTH/15+15+HomeScreen.WIDTH/4)+116 && imageY>=HomeScreen.HEIGHT/3-5 && imageY<HomeScreen.HEIGHT/3-5+56){
				correctHoop=true;
				correctVacinity=false;
			}
			else if(type==3 && imageX>=HomeScreen.WIDTH/15+15+HomeScreen.WIDTH/2  && imageX<(HomeScreen.WIDTH/15+15+HomeScreen.WIDTH/2)+116 && imageY>=HomeScreen.HEIGHT/3-5 && imageY<HomeScreen.HEIGHT/3-5+56){
				correctHoop=true;
				correctVacinity=false;
			}
			else if(type==4 && imageX>=HomeScreen.WIDTH/15+15+(3*HomeScreen.WIDTH/4)  && imageX<(HomeScreen.WIDTH/15+15+(3*HomeScreen.WIDTH/4))+116 && imageY>=HomeScreen.HEIGHT/3-5 && imageY<HomeScreen.HEIGHT/3-5+56){
				correctHoop=true;
				correctVacinity=false;
			}

		}

		if(correctVacinity) return 2;
		else if(correctHoop) return 5;
		else return 0;
	}



	public void draw(Graphics g){
		//draw logic
		if(!launchOver)g.drawImage(currentImage, imageX-getCurrentWidth()/2, imageY-getCurrentHeight()/2, getCurrentWidth(), getCurrentHeight(), drawingPanel);

		else{
			g.setFont(font);
			if(correctVacinity){
				g.setColor(new Color(0,100, 255));
				g.drawString("+2", (int)(0.9*HomeScreen.WIDTH/2), HomeScreen.HEIGHT/2);
				//g.drawImage(ArcadePanel.correct, imageX, imageY, drawingPanel);
			}
			else if(correctHoop){
				g.setColor(new Color(0,100, 255));
				g.drawString("+5", (int)(0.9*HomeScreen.WIDTH/2), HomeScreen.HEIGHT/2);
			}
			else{
				g.setColor(new Color(255, 75, 0));
				g.drawString("0", (int)(0.95*HomeScreen.WIDTH/2), HomeScreen.HEIGHT/2);
			}
				//g.drawImage(ArcadePanel.incorrect, imageX, imageY, drawingPanel);


		}

	}

	private int getCurrentHeight() {
		return imageWidth;
	}

	public String getTypeName(){
		if(type == 1) return "Recycle";
		else if(type == 2) return "Garbage";
		else if(type == 3) return "Compost";
		else if(type == 4) return "Hazardous";
		else return null;
	}
	
	private int getCurrentWidth() {
		return imageHeight;
	}

	//move based on keyboard input (method called from ArcadePanel keyPressed()) 
	public boolean move(KeyEvent e){
		int keyCode = e.getKeyCode();
		if(keyCode==KeyEvent.VK_RIGHT){
			imageX+=imageVX;
			if(imageX+imageWidth/2>HomeScreen.WIDTH) imageX=HomeScreen.WIDTH-imageWidth/2;
			return false;
		}
		else if(keyCode==KeyEvent.VK_LEFT){
			imageX-=imageVX;
			if(imageX-imageWidth/2<0) imageX = imageWidth/2;
			return false;
		}

		else if(keyCode==KeyEvent.VK_SPACE || keyCode==KeyEvent.VK_UP){
			if(!launched){
				//ArcadePanel.getRunner().start();
				launched = true;
				return true;
			}
			return false;
			//needGenerate = true;
		}
		return false;

	}
	
	//overloaded move method for shooting the waste
	//user parabolic data now to shoot

	public void move(int fractionPart, int total, int sleepMillis, double spaceBarConstant){

		

		double fractionCoefficient = 1 - (double)fractionPart/total;
		//System.out.println(fractionCoefficient);

		imageWidth = (int)(rawImageWidth*fractionCoefficient);
		imageHeight = (int)(rawImageHeight*fractionCoefficient);


		//System.out.println(imageWidth+", "+imageHeight);
		//imageX= imageX+(widthDifference/2);


		//parabolic curve
		//equation for now is y = - (x^2)
		if(!parabolicInit){
			boolean even = false;

			if(total%2==0) even = true;

			if(even) startXValueForCurve = -(total/2);
			else startXValueForCurve = -((total-1)/2);
			parabolicInit = true;
		}


		imageY  = (int)(spaceBarConstant*(startXValueForCurve)*(startXValueForCurve));

		//System.out.println("Image Y: "+imageY+"XVal in curve: "+startXValueForCurve);

		startXValueForCurve++;
	}
}
