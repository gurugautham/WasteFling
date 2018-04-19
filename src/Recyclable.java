import javax.swing.JPanel;


public class Recyclable extends Waste{

	public Recyclable(String universalName, JPanel parentPanel) {
		super(universalName, type(), parentPanel);
		// TODO Auto-generated constructor stub
	}

	public static int type(){
		return 1;
	}
	
}
