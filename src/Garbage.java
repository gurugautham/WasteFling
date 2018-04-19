import javax.swing.JPanel;


public class Garbage extends Waste{

	public Garbage(String universalName, JPanel parentPanel) {
		super(universalName, type(), parentPanel);
		// TODO Auto-generated constructor stub
	}

	public static int type(){
		return 2;
	}
	
}
