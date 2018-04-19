import javax.swing.JPanel;


public class Hazardous extends Waste{

	public Hazardous(String universalName, JPanel parentPanel){
		super(universalName, type(), parentPanel);
	}

	public static int type() {
		return 4;
	}
	
}
