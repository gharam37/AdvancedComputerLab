import java.util.ArrayList;
public class DFA {

	String[] SuccessStates;
	String Representation;
	ArrayList<State> MyStates = new ArrayList<State>();
	public DFA(String Representation) {
		this.Representation=Representation;
		String[] parts = Representation.split("#");
		String[] States=parts[0].split(";");
		SuccessStates=parts[1].split(",");
		for(int i=0;i<States.length;i++)
		{
			String[] StateArray=States[i].split(",");
			State s=new State(StateArray[0],StateArray[1],StateArray[2]);
			MyStates.add(s);
			//System.out.println(s);
			
		}
		//System.out.println(MyStates.size());
		

		
	}
	public boolean run(String Input) {
		boolean Success=false;

		String[] InputArray=Input.split(",");
		String CurrentState="0";
		for(int i=0;i<InputArray.length;i++) {
			for(int j=0;j<MyStates.size();j++) {
				if(MyStates.get(j).Name.equals(CurrentState)) {
					if(InputArray[i].equals("0"))
					{
						//System.out.println("Zero Given");
						//System.out.println(CurrentState);

						CurrentState=MyStates.get(j).ZeroState;
						//System.out.println(CurrentState);

					}
					else if(InputArray[i].equals("1"))
					{
						//System.out.println("One Given");
						//System.out.println(CurrentState);

						CurrentState=MyStates.get(j).OneState;
						//System.out.println(CurrentState);
					}
					else {
						return false;
					}
					break;
					
				}
			}
			
		}
		System.out.println(CurrentState);
		for(int i=0;i<SuccessStates.length;i++) {
			if(CurrentState.equals(SuccessStates[i])) {

				return true;
			}
			
		}
		return false;
		
	}
	public String toString() {
		return Representation;
		
		
	}
	public static void main(String[]args) {
		DFA dfa= new DFA("0,0,1;1,2,1;2,0,3;3,3,3#1,3");
		System.out.println(dfa.run("0,1,0,0,1,0,1,0,0,0,1,1,1"));
		System.out.println(dfa);
	}
}
