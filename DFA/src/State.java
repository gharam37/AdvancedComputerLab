import java.util.ArrayList;

public class State {

	String OneState;
	String ZeroState;
	String PrevState;
	String EpsilonState;
     String Name;
	boolean constructured=false;
	ArrayList<String> ZeroStates;
	ArrayList<String> OneStates;
	ArrayList<String> EpsilonStates;


	
	
	public State(String Name,String Zero,String One) {
		this.Name=Name;
		this.ZeroState=Zero;
		this.OneState=One;
		
	}
	public State(String Name) {
		this.Name=Name;

		this.EpsilonStates= new ArrayList<String>();
		this.OneStates= new ArrayList<String>();
		this.ZeroStates= new ArrayList<String>();


		
	}
	public State(String Name,String Zero,String One,String epsilon) {
		this.Name=Name;
		this.ZeroState=Zero;
		this.OneState=One;
		this.EpsilonState=epsilon;
		
	}
	
	public State() {
		
	}
	/*public String toString() {
		if(this.constructured) {
			return this.Name+","+this.ZeroState+","+this.OneState;

		}
		else {
		return this.Name+","+this.ZeroStates+","+this.OneStates+","+this.EpsilonStates;
		}
	}*/
}
