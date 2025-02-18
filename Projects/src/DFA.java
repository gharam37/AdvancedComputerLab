import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
public class DFA {

	ArrayList<String> SuccessStates = new ArrayList<String>();

	String Representation;
	ArrayList<State> MyStates = new ArrayList<State>();
	public DFA(String Representation,boolean NFA) {
	
		if(NFA) {
			constructNFA(Representation);
		}
		else {
			//constructDFA(Representation);

			
		}
		//System.out.println(MyStates.size());
		

		
	}
	public void constructNFA(String NFARepresentation) {
		this.Representation=NFARepresentation;
		ArrayList<State> NFAStates = new ArrayList<State>();
		ArrayList<String> NFASuccessStates = new ArrayList<String>();

		ArrayList<String> NFAStatesNames = new ArrayList<String>();


		String[] parts = NFARepresentation.split("#");

		for(int i=0;i<parts.length;i++)
		{
			String[] transitions = parts[i].split(";");
			for(int j=0;j<transitions.length;j++)
			{
				String[] transitionStates=transitions[j].split(",");
			   //System.out.println(transitionStates[0]);
				if(!NFAStatesNames.contains(transitionStates[0])){
					NFAStatesNames.add(transitionStates[0]);
					NFAStates.add(new State(transitionStates[0]));
					
				}
				// Zero Transitions
			    for (int k=0;k<NFAStates.size();k++) {
				if(NFAStates.get(k).Name.equals(transitionStates[0])) {
					if(i==0) {
							NFAStates.get(k).ZeroStates.add(transitionStates[1]);
					}
					else if(i==1) {
						NFAStates.get(k).OneStates.add(transitionStates[1]);

						
					}
					else if(i==2) {
						if(transitionStates.length>1) {
						NFAStates.get(k).EpsilonStates.add(transitionStates[1]);
						}

					}
					else {
						for (int l=0;l<transitionStates.length;l++) {
							
							NFASuccessStates.add(transitionStates[l]);
							
							
						}
					
						
					}
				}
						
					
					
				}

			}
			

		}
		for (int i=0;i<NFAStates.size();i++)
		{
			System.out.println(NFAStates.get(i));
		}
		/*for (int i=0;i<NFASuccessStates.size();i++)
		{
			System.out.println(NFASuccessStates.get(i));
		}*/
		EpsilonTransitions(NFAStates,NFAStatesNames,NFASuccessStates);
	}
	public void EpsilonTransitions(ArrayList<State> NFAStates,
			ArrayList<String> NFAStatesNames,
			ArrayList<String> NFASucessStates ) {
		ArrayList<State> ConstructedStates = new ArrayList<State>();
		ArrayList<String> ConstructedNames = new ArrayList<String>();

		
		State FirstState=null;
		String FirstStateName=null;
		for (int i=0;i<NFAStates.size();i++) {
			if(NFAStates.get(i).Name.equals("0"));
			FirstState=NFAStates.get(i);
			FirstStateName=NFAStatesNames.get(i);

			break;
		}
		//System.out.println(NFAStatesNames.indexOf(NFAStates.get(3).Name));
		//Change to while !Constructed
		while(!AllConstructed(ConstructedStates)) {
			State CurrentState=null;
			System.out.println(ConstructedStates);
			if(ConstructedStates.size()==0) {
				 CurrentState=FirstState;
				 System.out.println("Here");
			}
			else {
				for(int i=0;i<ConstructedStates.size();i++) {
					if(!ConstructedStates.get(i).constructured) {
						CurrentState=ConstructedStates.get(i);
						CurrentState.constructured=true;
						break;
					}
				}
			}
			CurrentState= GetClosure( CurrentState, NFAStates);

					if(!ConstructedNames.contains(CurrentState.Name)){
						ConstructedNames.add(CurrentState.Name);
						

						
					}
					for(int i=0;i<ConstructedStates.size();i++) {
						if(ConstructedStates.get(i).Name.equals(CurrentState.Name)) {
							ConstructedStates.remove(i);
							break;
							
						}
					}
					ConstructedStates.add(CurrentState);

					if(!ConstructedNames.contains(CurrentState.OneState)){
						ConstructedNames.add(CurrentState.OneState);
						ConstructedStates.add(new State(CurrentState.OneState));
						
					}
					if(!ConstructedNames.contains(CurrentState.ZeroState)){
						ConstructedNames.add(CurrentState.ZeroState);
						ConstructedStates.add(new State(CurrentState.ZeroState));
						
					}
				
				
				
					System.out.println(CurrentState.OneState+"One State");
					System.out.println(CurrentState.ZeroState+"Zero State");

			System.out.println("Current State"+CurrentState);
			System.out.println("New State"+CurrentState);
			CurrentState.constructured=true;

		//	System.out.println("New State"+NewState.constructured);


			//CurrentState.constructured=true;



			
		}
		
	   this.MyStates=ConstructedStates;
	   //Begin Printing DFA
	   System.out.println("Begin Printing DFA");
		for(int i=0;i<MyStates.size();i++) {
			//MyStates.get(i).constructured=true;
			System.out.println(MyStates.get(i));;
			
		}
		for(int i=0;i<MyStates.size();i++) {
			for(int j=0;j<NFASucessStates.size();j++) {
				if(MyStates.get(i).Name.contains(NFASucessStates.get(j)))
				{
					if(!this.SuccessStates.contains(MyStates.get(i).Name)) {
						this.SuccessStates.add(MyStates.get(i).Name);
						
					}
				}
			
			}
		}
	   ////// 
	  System.out.println("Printing Success States");
	  for(int i=0;i<SuccessStates.size();i++) {
		  System.out.println(SuccessStates.get(i));
	  }
		
	
		
	}
	public State GetClosure(State CurrentState, ArrayList<State> NFAStates){
		ArrayList<State> ClosureStates=null;
		ArrayList<String> OneState=new ArrayList<String>();
		ArrayList<String> ZeroState=new ArrayList<String>();
		//System.out.println(CurrentState);


		Stack<String> OneStateNames = new Stack();

		//GetStateName
		//System.out.println(CurrentState.Name);
		ArrayList<String> NewState=new ArrayList<String>();
		String[] SplitStates;
		if(CurrentState.Name.length()>1) {
		 SplitStates=CurrentState.Name.split("");

		}
		else {
			 SplitStates=new String[1];
			 SplitStates[0]=CurrentState.Name;

		}
		//System.out.println(SplitStates);
		for(int i=0;i<SplitStates.length;i++) {
		//	System.out.println(SplitStates.length);
			System.out.println("Hello"+SplitStates[i]);

			for (int j=0;j<NFAStates.size();j++) {
				if(NFAStates.get(j).Name.equals(SplitStates[i])){
					System.out.println(SplitStates[i]);
					if(!NewState.contains(NFAStates.get(j).Name)) {
					NewState.add(NFAStates.get(j).Name);

					}
					for(int k=0;k<NFAStates.get(j).EpsilonStates.size();k++) {
						if(!NewState.contains(NFAStates.get(j).EpsilonStates.get(k))) {
						NewState.add(NFAStates.get(j).EpsilonStates.get(k));
						}
					}
				}
			}
		}
		Collections.sort(NewState);
		//System.out.println(NewState);

		

        //Add Direct One Transitions		
		for(int j=0;j<NewState.size();j++) {
		for(int i=0;i<NFAStates.size();i++) {
			if(NFAStates.get(i).Name.equals(NewState.get(j))) {
				for(int k=0;k<NFAStates.get(i).OneStates.size();k++) {
					if(!OneStateNames.contains((NFAStates.get(i)).OneStates.get(k))) {
					OneStateNames.push((NFAStates.get(i)).OneStates.get(k));
					//System.out.println(NFAStates.get(i).OneStates.get(k));
					}
				}
			}
		}
		}
		
		
		System.out.println(OneStateNames);
		//One Closure
		while(!OneStateNames.isEmpty()) {
			String StateName= OneStateNames.pop();
			State s=null;
			for (int i=0;i<NFAStates.size();i++) {
				if(NFAStates.get(i).Name.equals(StateName)) {
					if(!OneState.contains(NFAStates.get(i).Name)) {
					OneState.add(NFAStates.get(i).Name);
					s=NFAStates.get(i);
					break;

					}
				}
			}
			//Push Its Epsilon Transitions
			if(s!=null) {
				
				for(int i=0;i<s.EpsilonStates.size();i++) {
					OneStateNames.push(s.EpsilonStates.get(i));
					
				}
			}
			
			
			
		}
		Collections.sort(OneState);
		System.out.println(OneState);

		Stack<String> ZeroStateNames = new Stack();
         //Add Direct Zero Transitions
		for(int j=0;j<NewState.size();j++) {
			for(int i=0;i<NFAStates.size();i++) {
				if(NFAStates.get(i).Name.equals(NewState.get(j))) {
					for(int k=0;k<NFAStates.get(i).ZeroStates.size();k++) {
						if(!ZeroStateNames.contains((NFAStates.get(i)).ZeroStates.get(k))) {
						ZeroStateNames.push((NFAStates.get(i)).ZeroStates.get(k));
						}
					}
				}
			}
			}
		// Add Epsilon Transitions
		while(!ZeroStateNames.isEmpty()) {
			String StateName= ZeroStateNames.pop();
			State s=null;
			for (int i=0;i<NFAStates.size();i++) {
				if(NFAStates.get(i).Name.equals(StateName)) {
					if(!ZeroState.contains(NFAStates.get(i).Name)) {
					ZeroState.add(NFAStates.get(i).Name);
					s=NFAStates.get(i);
					break;

					}
				}
			}
			//Push Its Epsilon Transitions
			if(s!=null) {
				
				for(int i=0;i<s.EpsilonStates.size();i++) {
					ZeroStateNames.push(s.EpsilonStates.get(i));
					
				}
			}
		//	
			
			
			
		}

		Collections.sort(ZeroState);
		System.out.println(ZeroState);
	 /*	if(NewState.isEmpty()) {
			State One=new State(NewState.toString());
			State Zero=new State(NewState.toString());
			State State= new State(NewState.toString(),NewState.toString(),NewState.toString());
			State.constructured=true;
			ClosureStates=new ArrayList<State>();
		    ClosureStates.add(State);
			ClosureStates.add(Zero);
			ClosureStates.add(One);
			return ClosureStates;


		}
		else {


		}*/
		Collections.sort(OneState);
		Collections.sort(ZeroState);
		String OneString="";
		String ZeroString="";
		String NewStateString="";
		
		for(int i=0;i<OneState.size();i++) {
			OneString+=OneState.get(i)+"";
		}
		for(int i=0;i<ZeroState.size();i++) {
			ZeroString+=ZeroState.get(i)+"";
		}
		
		for(int i=0;i<NewState.size();i++) {
			NewStateString+=NewState.get(i)+"";
		}
        //System.out.println(NewState);
		//System.out.println(OneState);
		//System.out.println(ZeroState.toString());
	//State One=new State(OneString);
		State Zero=new State(ZeroString);
		State State= new State(NewStateString,ZeroString,OneString);
		State.constructured=true;
		System.out.println(State);
		//ClosureStates=new ArrayList<State>();
	   // ClosureStates.add(State);

	    //System.out.println(ClosureStates);
		//System.out.println(State);
		return State;
		

		
	}
	public boolean AllConstructed(ArrayList<State> ConstructedStates) {
		if(ConstructedStates.size()==0) {
			return false;
		} //Change Later to False
		for(int i=0;i<ConstructedStates.size();i++) {
			if(!ConstructedStates.get(i).constructured)
			{
				return false;
			}
		
		}
		return true;
		
	}
	/*public void constructDFA(String DFARepresentation) {
		this.Representation=DFARepresentation;
		String[] parts = DFARepresentation.split("#");
		String[] States=parts[0].split(";");
		SuccessStates=parts[1].split(",");
		for(int i=0;i<States.length;i++)
		{
			String[] StateArray=States[i].split(",");
			State s=new State(StateArray[0],StateArray[1],StateArray[2]);
			MyStates.add(s);
			//System.out.println(s);
			
		}
		
	}*/
	public boolean run(String Input) {
		boolean Success=false;

		String[] InputArray=Input.split(",");
		String CurrentState=MyStates.get(0).Name;
		// CurrentState="0";
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
		for(int i=0;i<SuccessStates.size();i++) {
			if(CurrentState.equals(SuccessStates.get(i))) {

				return true;
			}
			
		}
		return false;
		
	}
	public String toString() {
		return Representation;
		
		
	}
	public static void main(String[]args) {
		DFA dfa= new DFA("0,1;1,2;2,3#0,0;1,1;2,3;3,3#1,0;2,1;3,2#1,2,3",true);
		//System.out.println("First DFA" );
		//System.out.println(dfa);
		/*System.out.println(dfa.run("1,0,1,0,0"));
		System.out.println(dfa.run("0,0,0,1,0"));
		System.out.println(dfa.run("0,0,1,0,1,0,0,0"));
		System.out.println(dfa.run("1,0,1,1,1,1,1"));
		System.out.println(dfa.run("1,1,0,1,1"));*/

	//	DFA dfa2= new DFA("0,0;0,1#0,1;1,0##0",true);
		
		//DFA dfa3= new DFA("0,2;1,0;2,1#2,1;2,2#0,1#1",true);
 


	   System.out.println("First DFA Runs");

	    /*System.out.println(dfa.run("0,1,0,0"));
		System.out.println(dfa.run("1,1,1,1"));

		System.out.println(dfa.run("0,1,0,0,0"));

		System.out.println(dfa.run("0,0"));

		System.out.println(dfa.run("1,1,0,1,1,0,0"));*/

		System.out.println("Second DFA Runs");
		DFA dfa2= new DFA("0,1;1,3;3,3#0,2;2,3;3,3#1,2;3,2#3",true);

		
		System.out.println(dfa2.run("0,1,0,1,1,0,0"));
		System.out.println(dfa2.run("0,1,0,1,0,1"));

		System.out.println(dfa2.run("1,1,1,0,1,0"));

		System.out.println(dfa2.run("1,0,1,0,0"));

		System.out.println(dfa2.run("1,0,1,0,1"));
		
		

	}
}
