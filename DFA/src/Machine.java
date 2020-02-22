import java.util.ArrayList;

public abstract class Machine {
	
	ArrayList<String> SuccessStates = new ArrayList<String>();

	String Representation;
	ArrayList<Object> MyStates = new ArrayList<Object>();
public Machine(String Representation) {
	
	Start(Representation);
	
		
	}

     abstract void Start(String Representation);

}
