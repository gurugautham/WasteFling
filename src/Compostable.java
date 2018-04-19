import javax.swing.JPanel;


public class Compostable extends Waste{

	public Compostable(String universalName, JPanel parentPanel) {
		super(universalName, type(), parentPanel);
		
	}
	
	public static int type(){
		return 3;
	}

}
