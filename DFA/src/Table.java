import java.util.*;
import java.util.Map.Entry; 

public class Table {
	
	CFG grammer;
	 ArrayList<ArrayList<String> > Representation = new ArrayList<ArrayList<String> > ();


	//ArrayList<String> First;
	//ArrayList<String> Follow;
	public Table(String input ) {
		this.grammer=new CFG(input);

		
		this.grammer.First();
		this.grammer.Follow();
		//this.First=grammer.First;
		//this.Follow=grammer.Follow;
		//System.out.println(this.Follow);
		
		Construct();
		
	}
	public static String toRuleString(String[] RuleArray) {
		String RuleString="";
		for(int i=0;i<RuleArray.length;i++) {
			if(i<RuleArray.length-1) {
				RuleString +=RuleArray[i]+",";
				
			}
			else {
				RuleString +=RuleArray[i]+"";
			}
		}
		return RuleString;
	}
	public static String uniqueCharacters(String test){
		//String string = "aabbccdefatafaz";

		char[] chars = test.toCharArray();
		Set<Character> charSet = new LinkedHashSet<Character>();
		for (char c : chars) {
		    charSet.add(c);
		}

		StringBuilder sb = new StringBuilder();
		for (Character character : charSet) {
		    sb.append(character);
		}
		return sb.toString();

	}
	 public static String sortString(String inputString) 
	    { 
	        // convert input string to char array 
	        char tempArray[] = inputString.toCharArray(); 
	          
	        // sort tempArray 
	        Arrays.sort(tempArray); 
	          
	        // return new sorted string 
	        return new String(tempArray); 
	    } 
	public void Construct() {
		ArrayList<String[]> Grammer=this.grammer.Grammer;
		List terminals=new ArrayList<String>();
		List Rules=new ArrayList<String>();

		String terminalString="";
		
		for(int i=0;i<Grammer.size();i++) {
			Rules.add(Grammer.get(i)[0]);
			//System.out.println(Rules.get(i));
			for(int j=1;j<Grammer.get(i).length;j++) {
				//System.out.println(Grammer.get(i)[j]);
				terminalString+= Grammer.get(i)[j].replaceAll("[A-Z]", "");
				terminalString= terminalString.replace("e", "");
				  
				  

			}
		}
		terminalString=sortString(uniqueCharacters(terminalString));
		terminalString+="$";
		String [] terminalsArray=terminalString.split("");
        terminals = Arrays.asList(terminalsArray); 

        
		  //System.out.println(Rules.get(1));
        
        for(int i=0;i<Rules.size();i++) {
        	for(int j=0;j<terminals.size();j++) {
        		ArrayList<String> Derivations= new ArrayList<String>();
                Derivations.add(Rules.get(i)+","+terminals.get(j));
        		//System.out.println("Constructing for rule "+Rules.get(i)+" and terminal "+terminals.get(j));
        	    Derivations.addAll(Derivations((String)Rules.get(i),(String)terminals.get(j)));
        	    if(Derivations.size()>1) {
        	    Representation.add(Derivations);
        	    }
        	
        	}
        }

        //System.out.println(Representation);
		//System.out.println(this.Representation);
		
	}
	
	public String toString() {
		String output="";
		for(int i=0;i<Representation.size();i++)
		{
			if(Representation.get(i).size()>1) {
			for (int j=0;j<Representation.get(i).size();j++) {
				output+=Representation.get(i).get(j)+",";
				
			}
			}
			output=output.substring(0, output.length()-1);
			output+=";";
		}
		output=output.substring(0, output.length()-1);

		return output;
	}
	
	public ArrayList<String> Derivations(String rule, String terminal){
		ArrayList<String> ruleDerivations=new ArrayList<String>();
		String [] ruleArray =this.grammer.FindRule(rule);
		for(int i=1;i<ruleArray.length;i++) {
			//System.out.println(ruleArray[i]);
			boolean Derivable=IsDerivable(rule,terminal,ruleArray[i]);
			if( Derivable) {
        		System.out.println("rule "+rule+" can derive terminal "+terminal+ " using "+ruleArray[i]);
        		
        		ruleDerivations.add(ruleArray[i]);

				
			}

		}
		
		return ruleDerivations;
	}
	
	public boolean IsDerivable(String rule,String terminal,String rhs) {
		if(rhs.equals("e")) {
			String followofRule=this.grammer.GetFollow(rule);
			if(followofRule.contains(terminal)) {
				
				return true;
				
			}
		}
		for(int i=0;i<rhs.length();i++) {
			if(Character.isLowerCase(rhs.charAt(i))) {
				
				String Character=rhs.charAt(i)+"";
				if(Character.equals(terminal)) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				String firstOfRule=this.grammer.GetFirst(rhs.charAt(i)+"");
				//System.out.println(firstOfRule);
				if(firstOfRule.contains(terminal)) {
					return true;
				}
				else {
					if(!firstOfRule.contains("e")) {
						return false;
					}
					else if(firstOfRule.contains("e") && i==rhs.length()-1) {
						String followofRule=this.grammer.GetFollow(rule);
						if(followofRule.contains(terminal)) {
							
							return true;
							
						}
					}
				}
				
			}
		}

		boolean Derivable=false;
		
		return Derivable;
	}
	public String Parse(String input) {

		input+="$";
		String OriginalInput=input;

		String output="S,";

		String Matched="";
        Stack<String> stack = new Stack<String>(); 
        stack.push("$");
        stack.push("S");
        while(!stack.isEmpty() ){
        	//System.out.println(stack);
        	String Top=stack.pop();
        	

        	if(Top.equals(input.charAt(0)+"")) {
        		Matched+=Top;
        		
        		input=input.substring(1);
        		//System.out.println("Input "+input);
        		//System.out.println("Matched "+Matched);
        		String StackContent=stack.toString();
        		StackContent =StackContent.replace(",", "");
        		StackContent =StackContent.replace(" ", "");
        		StackContent =StackContent.replace("$", "");
        		StackContent =StackContent.replace("[", "");
        		StackContent =StackContent.replace("]", "");
        		StackContent = new StringBuffer(StackContent).reverse().toString();



        		output+=Matched+StackContent+",";
        		

        	}
        	else {
        		if(Character.isUpperCase(Top.charAt(0))) {
        	String [] Substitution=Substitution(Top+","+input.charAt(0));
        	if(Substitution!=null) {
        		for(int j=Substitution.length-1;j>=0;j--) {
        			//System.out.println(input);
        			//System.out.println(Substitution[j]);
        			if(Substitution[j].equals("e")) {
        				//System.out.println("Hello");
                		String StackContent=stack.toString();
                		StackContent =StackContent.replace(",", "");
                		StackContent =StackContent.replace(" ", "");
                		StackContent =StackContent.replace("$", "");
                		StackContent =StackContent.replace("[", "");
                		StackContent =StackContent.replace("]", "");
                		StackContent = new StringBuffer(StackContent).reverse().toString();
                		String CurrentOuput= Matched+StackContent;
                		output+=Matched+StackContent+",";

        			System.out.println(CurrentOuput);
        			




            		
            		//System.out.println(Matched+StackContent);
        			}
        			else {
            			stack.push(Substitution[j]);

        			}
        		}
        		
        	}
        	else if(!HasEpsilon(Top)) {
        		String StackContent=stack.toString();
        		StackContent =StackContent.replace(",", "");
        		StackContent =StackContent.replace(" ", "");
        		StackContent =StackContent.replace("$", "");
        		StackContent =StackContent.replace("[", "");
        		StackContent =StackContent.replace("]", "");
        		StackContent = new StringBuffer(StackContent).reverse().toString();
        		String Stuff= Matched+StackContent;
        		
        		if(!Stuff.equals(OriginalInput)) {
        			//System.out.println("Error");
        			break;
        			
        		}
        		else {
        			//System.out.println(Stuff);
        		}
        		
        	       
        	}
        	
        
           

        }
        	}
        }
       
        //System.out.println(Matched);
        //System.out.println(input);
        //System.out.println(stack);
        if(!(stack.isEmpty() && input.isEmpty()))
        {
        	output+="ERROR";
        	//System.out.println("Error");
        }
        output=output.replace(Matched+",", "");
        if(output.charAt(output.length()-1)==',') {
        	output=output.substring(0,output.length()-1);
        }
        
        System.out.println(output);
        

		return output;
	}
	public String[] Substitution(String Rule) {
		for(int i=0;i<Representation.size();i++)
		{
			if(Representation.get(i).size()>1) {
			for (int j=0;j<Representation.get(i).size();j++) {
				if(Representation.get(i).get(j).equals(Rule)) {
					//System.out.println(Representation.get(i).get(1)+Rule +"Rule");
						return Representation.get(i).get(1).split("");

					
				}
				
			}
			}
			
		}
		
		return null;
		
	}
	
	public boolean HasEpsilon(String Rule) {
		boolean HasEpsilon =false;
		for(int i=0;i<Representation.size();i++)
		{
			if(Representation.get(i).size()>1) {
			for (int j=0;j<Representation.get(i).size();j++) {
				String RuleName=Representation.get(i).get(j).charAt(0)+"";
				if(RuleName.equals(Rule)) {
					if( Representation.get(i).get(1).equals("e")){
						return true;
					}
				}
				
			}
			}
			
		}
		
		return HasEpsilon;
	}
	
	public String table() {
		return this.toString();
	}
	public static void main(String[]args) {
		
		String input ="S,iST,e;T,cS,a";
		 //input ="S,La,b;L,S,Z,e;Z,k";

		Table t=new Table(input);
		System.out.println(t);
		t.Parse("iia");
		//System.out.println("Here");
	}

}
