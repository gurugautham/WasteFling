import java.awt.Image;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class WasteGenerator {

	public static HashMap<String, Image> imageCatalog;				//data structure for storing images with names
	//public static ArrayList<String> loadedImages;

	public PrintWriter outFile;
	public Scanner inFile;
	public static int numOfRecyclables, numOfCompostables, numOfGarbage, numOfHazardous, fitSizeW = 200, fitSizeH = 175, imageWidth, imageHeight;
	public double imageRatio, projectedHeight, projectedWidth;
	public boolean imagesLoaded;
	public Image loading;

	private JPanel drawingPanel;


	public WasteGenerator(JPanel parentPanel){ //create catalog text file a PrintWriter and Scanner
		parentPanel = drawingPanel;
		
		
		imageCatalog = new HashMap<String, Image>();
		//loadedImages = new ArrayList<String>();

		File catalog = new File("catalog.txt");
		try{
			outFile = new PrintWriter(catalog);
			inFile = new Scanner(new File("catalog.txt"));
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		//finds all images and loads them into the hash map
		numOfRecyclables = loadImages("Recyclables");
		numOfGarbage = loadImages("Garbage");
		numOfCompostables = loadImages("Compostables");
		numOfHazardous = loadImages("Hazardous");

		outFile.flush();
		imagesLoaded=true;
	}


	public Waste generate(){						//uses math.random and genereates in 2 steps
													//Step 1: randomly figure out between Recycleable, Compstable or Garbage
													//Step 2: for repsective identification above, display respective image
		try {
			inFile = new Scanner(new File("catalog.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int type = ((int)((Math.random()*4)+1));     //generates a number between 1 - 4 inclusive


		int lineNumber = 0;
		int lineNumberToChose;

		//RECYCLABLES
		if(type==1){
			while(inFile.hasNext()){
				lineNumber++;
				String temp = inFile.nextLine();
				System.out.println(temp);
				if(temp.indexOf("Recyclables:")>=0){
					break;
				}
			}
			lineNumberToChose = (int)(Math.random()*numOfRecyclables+1);

			for(int i = 0; i<lineNumberToChose-1; i++){
				inFile.nextLine();
			}

			String name = inFile.nextLine();
			return new Recyclable(name, drawingPanel);

		}

		//GARGBAGE
		else if(type==2){
			while(inFile.hasNext()){
				lineNumber++;
				if(inFile.nextLine().indexOf("Garbage:")>=0){
					break;
				}
			}
			lineNumberToChose = (int)(Math.random()*numOfGarbage+1);

			for(int i = 0; i<lineNumberToChose-1; i++){
				inFile.nextLine();
			}

			String name = inFile.nextLine();
			return new Garbage(name, drawingPanel);
		}

		//COMPOSTABLES
		else if(type==3){
			while(inFile.hasNext()){
				lineNumber++;
				if(inFile.nextLine().indexOf("Compostables:")>=0){
					break;
				}
			}
			lineNumberToChose = (int)(Math.random()*numOfCompostables+1);

			for(int i = 0; i<lineNumberToChose-1; i++){
				inFile.nextLine();
			}

			String name = inFile.nextLine();
			return new Compostable(name, drawingPanel);
		}

		//HAZARDOUS
		else if(type==4){
			while(inFile.hasNext()){
				lineNumber++;
				if(inFile.nextLine().indexOf("Hazardous:")>=0){
					break;
				}
			}
			lineNumberToChose = (int)(Math.random()*numOfHazardous+1);

			for(int i = 0; i<lineNumberToChose-1; i++){
				inFile.nextLine();
			}

			String name = inFile.nextLine();
			return new Hazardous(name, drawingPanel);
		}




		//pass Object name and image source to Waste


		return null;
	}

	public Image scaleImage(Image toLoad){
		//Scale Logic
		imageWidth = toLoad.getWidth(null);
		imageHeight = toLoad.getHeight(null);
		//Hamburger
		if(toLoad.getWidth(null)>toLoad.getHeight(null)){
			imageRatio = imageWidth/imageHeight;
			projectedHeight = (int)(fitSizeW/imageRatio);
			return toLoad.getScaledInstance(fitSizeW, (int) projectedHeight, Image.SCALE_DEFAULT);

		}

		//Portrait
		else{
			imageRatio = imageHeight/imageWidth;
			projectedWidth = (int)(fitSizeH/imageRatio);
			return toLoad.getScaledInstance((int) projectedWidth, fitSizeH, Image.SCALE_DEFAULT);
		}

	}

	public int loadImages(String catalogMarker){

		//RECYCLABLES
		outFile.println(catalogMarker+":");

		Image toLoad = null;
		File dir = new File(HomeScreen.SOURCE_PATH+catalogMarker);
		String name;

		//For Loop begins
		File[] filesList = dir.listFiles();
		System.out.println(filesList);
		File file;
		for (int i = 0 ; i<filesList.length; i++) {
			file = filesList[i];
			name = file.getName();
			if (file.isFile()) {

				//ensuring files in folder are only .png
				if(name.indexOf(".png")<0 || name.indexOf("._")>=0){
					file.delete();
					//System.out.println("File deleting" + file.delete());
				}

				/*
				 * 1. Load Image
				 * 2. Put in Hash Map (key = image name)
				 * 3. Put image name in catalog under respective sort
				 */
				else{
					System.out.println(name);
					try {
						toLoad = ImageIO.read(new File(HomeScreen.SOURCE_PATH+catalogMarker+"/"+name));
					} catch (IOException e) {

						e.printStackTrace();
					}
					toLoad = scaleImage(toLoad);
					imageCatalog.put(name.substring(0,name.length()-4), toLoad);
					outFile.println(name.substring(0,name.length()-4));
				}
			}
		}

		//count num of sort
		return new File(HomeScreen.SOURCE_PATH+catalogMarker).listFiles().length;

	}

}
