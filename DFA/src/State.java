
public class State {

	String OneState;
	String ZeroState;
	String PrevState;
	String Name;
	
	public State(String Name,String Zero,String One) {
		this.Name=Name;
		this.ZeroState=Zero;
		this.OneState=One;
		
	}
	
	public String toString() {
		return this.Name+","+this.ZeroState+","+this.OneState;
	}
}
