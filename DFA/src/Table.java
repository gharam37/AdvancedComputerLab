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
		////system.out.println(this.Follow);
		
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
			////system.out.println(Rules.get(i));
			for(int j=1;j<Grammer.get(i).length;j++) {
				////system.out.println(Grammer.get(i)[j]);
				terminalString+= Grammer.get(i)[j].replaceAll("[A-Z]", "");
				terminalString= terminalString.replace("e", "");
				  
				  

			}
		}
		terminalString=sortString(uniqueCharacters(terminalString));
		terminalString+="$";
		String [] terminalsArray=terminalString.split("");
        terminals = Arrays.asList(terminalsArray); 

        
		  ////system.out.println(Rules.get(1));
        
        for(int i=0;i<Rules.size();i++) {
        	for(int j=0;j<terminals.size();j++) {
        		ArrayList<String> Derivations= new ArrayList<String>();
                Derivations.add(Rules.get(i)+","+terminals.get(j));
        		////system.out.println("Constructing for rule "+Rules.get(i)+" and terminal "+terminals.get(j));
        	    Derivations.addAll(Derivations((String)Rules.get(i),(String)terminals.get(j)));
        	    if(Derivations.size()>1) {
        	    Representation.add(Derivations);
        	    }
        	
        	}
        }

        ////system.out.println(Representation);
		////system.out.println(this.Representation);
		
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
			////system.out.println(ruleArray[i]);
			boolean Derivable=IsDerivable(rule,terminal,ruleArray[i]);
			if( Derivable) {
        		////system.out.println("rule "+rule+" can derive terminal "+terminal+ " using "+ruleArray[i]);
        		
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
				////system.out.println(firstOfRule);
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

		String OriginalInput=input;

		input+="$";


		ArrayList<String> outputList=new ArrayList<String>();
		outputList.add("S");
        Stack<String> stack = new Stack<String>(); 
        stack.push("$");
        stack.push("S");
       // //system.out.println(stack);

        
        String Top=stack.pop();
        int i = 0;
        int count=0;
        boolean popped=false;
        while(!stack.isEmpty()   ) {


        	char CurrentChar=input.charAt(i);
        	if(Character.isLowerCase(Top.charAt(0))) {
        		if((CurrentChar+"").equals(Top)) {
        			//Matched+=Top;
        		    Top=stack.pop();
        		    popped=true;
        		i++;

        		}
        		else {
            		////system.out.println("Here");
            		////system.out.println(CurrentChar);
            		////system.out.println(Top);

        			outputList.add("ERROR");
        			////system.out.println(outputList);
        			return ConstructDerivation(outputList);
        			
        		}
        		//break;
        		
        	}
        	else {
            	String [] Substitution=Substitution(Top+","+input.charAt(i));
            	if(Substitution!=null) {

            		String Production="";

            		if(!IsEpsilon(Substitution)) {
            			for(int j=Substitution.length-1;j>=0;j--) {
            				////system.out.println(Substitution[j]);
                			
                    	stack.push(Substitution[j]);

                		}

            			if(Character.isLowerCase(stack.peek().charAt(0))) {
            			Top=stack.pop();
                		i++;
            			}


            			

            			
                		//stack.pop();

            			for(int k=0;k<Substitution.length;k++) {
            				//Production+=Substitution[k];
            				
            			}
            			

            			
            		}
            		Production+=input.substring(0,i);

        			String StackContent=stack.toString();
            		StackContent =StackContent.replace(",", "");
            		StackContent =StackContent.replace(" ", "");
            		StackContent =StackContent.replace("$", "");
            		StackContent =StackContent.replace("[", "");
            		StackContent =StackContent.replace("]", "");
            		StackContent = new StringBuffer(StackContent).reverse().toString();
            		Production+=StackContent;

            		outputList.add(Production);

            	}
            	else {
            		outputList.add("ERROR");
            		return ConstructDerivation(outputList);
            	}


        	}
        	////system.out.println(outputList);
        	////system.out.println("stack"+stack);
        	////system.out.println("Productions "+outputList);
        		if(!popped) {
        	Top=stack.pop();
        	        
        		}
        		
        	
			popped=false;



        }
        
        if(!(outputList.get(outputList.size()-1).equals(OriginalInput))){
        	outputList.add("ERROR");
        	
        }
        	
        ////system.out.println(outputList);
        	

		return ConstructDerivation(outputList);
	}
	public String[] Substitution(String Rule) {
		for(int i=0;i<Representation.size();i++)
		{
			if(Representation.get(i).size()>1) {
			for (int j=0;j<Representation.get(i).size();j++) {
				if(Representation.get(i).get(j).equals(Rule)) {
					////system.out.println(Representation.get(i).get(1)+Rule +"Rule");
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
	
	public String ConstructDerivation(ArrayList<String> outputList) {
		String StackContent=outputList.toString();
		StackContent =StackContent.replace(" ", "");
		StackContent =StackContent.replace("[", "");
		StackContent =StackContent.replace("]", "");
		
		return StackContent;
	}
	
	public boolean IsEpsilon(String[] Substitution) {
		boolean IsEpsilon=false;
		for(int i=0;i<Substitution.length;i++) {
			if(Substitution[i].equals("e")) {
				return true;
			}
		}
		
		return IsEpsilon;
	}
	public String table() {
		return this.toString();
	}
	public static void main(String[]args) {
		
		String input ="S,lLr,a;L,lLrD,aD;D,cSD,e";
		 //input ="S,La,b;L,S,Z,e;Z,k";

		Table t=new Table(input);
		System.out.println(t);
		System.out.println("Parse tree is "+t.Parse("laclacarr"));
		////system.out.println("Here");
	}

}
