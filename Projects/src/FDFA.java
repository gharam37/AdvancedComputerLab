import java.util.ArrayList;
import java.util.Stack;

//Each Class used to be in a seperate file
public class FDFA extends Machine{

	public FDFA(String Representation) {
		super(Representation);
		// TODO Auto-generated constructor stub
	}

	@Override
	void Start(String Representation) {
		this.Representation=Representation;
		String[] parts = Representation.split("#");
		String[] States=parts[0].split(";");
		String [] SuccessArray=parts[1].split(",");
		
		for (int i=0;i<SuccessArray.length;i++) {
			SuccessStates.add(SuccessArray[i]);
		}
		for(int i=0;i<States.length;i++)
		{
			String[] StateArray=States[i].split(",");
			
			FDFAState s=new FDFAState(StateArray[0],StateArray[1],StateArray[2],StateArray[3]);
			MyStates.add(s);
			System.out.println(s);
			
		}
		
	}
	
	public String run(String input) {
		Stack<String> stack= new Stack<String>();
		String[] Tape=input.split(",");
		int R=0;
		int L=0;
		String output="";
		FDFAState s= (FDFAState)(MyStates.get(0));
		//System.out.println(s.Name);
		stack.push(s.Name);
	 while(R<Tape.length) {
		stack= new Stack<String>();
	    s= (FDFAState)(MyStates.get(0));
		stack.push(s.Name);

		while(L<Tape.length) {
			String current = stack.peek();
			for(int j=0;j<MyStates.size() && L<Tape.length;j++) {
				s= (FDFAState)(MyStates.get(j));
				if(s.Name.equals(current)) {
					if(Tape[L].equals("0"))
					{
						current=s.ZeroState;
					}
					else
					{
						current=s.OneState;

					}
					stack.push(current);
					L+=1;


				}
			}
			
		}
		String LatestSuccess=stack.peek();
		while( !stack.isEmpty() && !SuccessStates.contains(stack.peek()))
		{
			stack.pop();
			L--;
		}
		if(!stack.isEmpty()) {
	    LatestSuccess=stack.peek();
		}
		
		for(int k=0;k<MyStates.size();k++) {
			s= (FDFAState)(MyStates.get(k));
			if(s.Name.equals(LatestSuccess)) {
				output+=s.Output;
				break;
			}

		}
		if(stack.isEmpty()) {
			//System.out.println("Not in Language");
			return output;
			
		}
		//System.out.println("Latest Success State"+LatestSuccess);

		//System.out.println("Current R"+R);

		//System.out.println("Current L"+L);
		//System.out.println("Current Stack "+stack);

		R=L;
	 }
		return output;

	}
	
	public static void main(String[] args) {
		System.out.println("First FDFA");
		FDFA f1=new FDFA("0,0,1,00;1,0,2,01;2,3,2,10;3,2,3,11#1,3");
		System.out.println("output "+f1.run("0,0,1,1,1"));
		System.out.println("output "+f1.run("0,0,1,1,1,0,0"));
		System.out.println("output "+f1.run("1,1,0,1,0,1"));
		System.out.println("output "+f1.run("1,1,0,1,0,1,0"));
		System.out.println("output "+f1.run("0,0,0"));
		
		
		////////////////
		
		System.out.println("Second FDFA");
		FDFA f2=new FDFA("0,1,0,00;1,3,0,01;2,1,3,10;3,2,3,11#3");
		System.out.println("output "+f2.run("1,0,0,0,0"));
		System.out.println("output "+f2.run("0,0"));
		System.out.println("output "+f2.run("0,0,0,0,1"));
		System.out.println("output "+f2.run("1,0,1,0,1"));

		System.out.println("output "+f2.run("1,0"));
		
		/*System.out.println("Third FDFA");
		FDFA f3=new FDFA("0,0,1,00;1,0,1,01#1");
		System.out.println("output f3 "+f3.run("0,0,1,0,0"));*/


		

	}

	


	
}

 abstract class Machine {
	
	ArrayList<String> SuccessStates = new ArrayList<String>();

	String Representation;
	ArrayList<Object> MyStates = new ArrayList<Object>();
public Machine(String Representation) {
	
	Start(Representation);
	
		
	}

     abstract void Start(String Representation);

}
 class State {

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
	
  class FDFAState extends State{

		String Output;
		public FDFAState(String Name) {
			super(Name);
			// TODO Auto-generated constructor stub
		}
		
		public FDFAState(String Name,String Zero, String One,String Output) {
			super(Name);
			this.Name=Name;
			this.ZeroState=Zero;
			this.OneState=One;
			this.Output=Output;
		}
		
		public FDFAState() {
			
		}


		
		public String toString() {
			
			return this.Name+","+this.ZeroState+","+this.OneState+","+this.Output;
			
		}
	}


