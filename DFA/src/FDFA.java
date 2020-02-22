import java.util.ArrayList;
import java.util.Stack;

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
			System.out.println("Not in Language");
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
		FDFA f1=new FDFA("0,0,1,00;1,2,1,01;2,0,3,10;3,3,3,11#0,1,2");
		System.out.println("output "+f1.run("1,0,0"));
		System.out.println("output "+f1.run("1,0,1"));
		System.out.println("output "+f1.run("1,1,0"));
		System.out.println("output "+f1.run("1,0,1,1,0"));
		System.out.println("output "+f1.run("0,1,1"));
		
		////////////////
		
		System.out.println("Second FDFA");
		FDFA f2=new FDFA("0,1,0,00;1,1,2,01;2,3,2,10;3,3,3,11#1,2");
		System.out.println("output "+f2.run("0,1,1"));
		System.out.println("output "+f2.run("1,1,0"));
		System.out.println("output "+f2.run("0,0,1,0,1"));
		System.out.println("output "+f2.run("1,0,0,1,0,0"));

		System.out.println("output "+f2.run("1,0,0"));
		
		System.out.println("Third FDFA");
		FDFA f3=new FDFA("0,0,1,00;1,0,1,01#1");
		System.out.println("output f3 "+f3.run("0,0,1,0,0"));


		

	}

	
	
}
