
public class FDFAState extends State{

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
