import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;


public class Task7 {
	static class Table {
		
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


	static  class CFG {

			String Representation;
			ArrayList<String[]> Grammer;
			ArrayList<String> First;
			ArrayList<String> Follow;
			

			Table Mytable;
			
			public String table() {
				Table t= new Table(this.Representation);
				Mytable=t;
				
				return this.Mytable.table();
			}
			
			

			public static String LRE(String input) {
			    ArrayList<String[]> Grammer=new ArrayList<String[]>();
				String output="";
				//Prepare Grammer
			    String[] rules=input.split(";");
			    for(int i=0;i<rules.length;i++) {
			    	Grammer.add(rules[i].split(",",-1));
			    	//System.out.println(rules[i].split(",").length);
			    }
			    int OriginalGrammerLength=Grammer.size();
			    for(int i=0;i<OriginalGrammerLength;i++) {
			    	String []Substitution =null;
			    	//System.out.println(toRuleString(Grammer.get(i)));
			    	if(i!=0) {
			    	Substitution = Substitute(Grammer.get(i),Grammer,i);
			    	}
			    	else {
			    		Substitution=Grammer.get(i);
			    	}
			    	//System.out.println(toRuleString(Substitution));

			    	ArrayList<String []> NewRules=EliminateDirectRecursion(Substitution);
			    	if(NewRules.size()==2) {
			    		Grammer.remove(i);
			    		Grammer.add(i,NewRules.get(0));
			    		Grammer.add(NewRules.get(1));

			    		
			    	}
			    	else {
			    		Grammer.remove(i);
			    		Grammer.add(i,NewRules.get(0));
			    		
			    	}
			    	
			    	
			    }
			    for(int i=0;i<Grammer.size();i++) {
			    	if(i==Grammer.size()-1) {
			    		output+=toRuleString(Grammer.get(i));
			    	}
			    	else {
			    		output+=toRuleString(Grammer.get(i))+";";

			    	}
			    	//System.out.println(toRuleString(Grammer.get(i)));
			    }
			    //System.out.println(Grammer.get(0)[1]);

			    
				return output;
			}
			public static ArrayList<String []> EliminateDirectRecursion(String[] rule){
				ArrayList<String []> rules=new ArrayList<String[]>();
				String RuleName=GetRuleName(rule[0]);
				ArrayList<String> Alphas=new ArrayList<>();
				ArrayList<String> Betas=new ArrayList<>();
				
				for(int i=1;i<rule.length;i++) {
					String RHSName=GetRuleName(rule[i]);
					if(RHSName.equals(RuleName)) {
						String alpha=rule[i].substring(RHSName.length());
						//System.out.println("Caught an alpha"+alpha);
						Alphas.add(alpha);
					}
					else {
						Betas.add(rule[i]);
						//System.out.println(rule[i]);
					}
					
				}
				if(Alphas.size()>0) {
					String NewRuleName=RuleName+"'";
					ArrayList<String> newRule =new ArrayList<String>();
					newRule.add(NewRuleName);
					for(int i=0;i<Alphas.size();i++) {
						newRule.add(Alphas.get(i)+NewRuleName);
						
					}
					newRule.add("");
					//System.out.println(newRule);
					//System.out.println("Found Alphas"+toRuleString(rule));
					ArrayList<String> olderRuleModified=new ArrayList<String>();
					olderRuleModified.add(RuleName);
					for(int i=0;i<Betas.size();i++) {
						olderRuleModified.add(Betas.get(i)+NewRuleName);
					}
					//System.out.println(olderRuleModified);
					
					rules.add(GetStringArray(olderRuleModified));
					rules.add(GetStringArray(newRule));


					
				}
				else {
					rules.add(rule);
				}
				
				return rules;
			}
			public static String[] GetStringArray(ArrayList<String> arr) 
		    { 
		  
		        // declaration and initialise String Array 
		        String str[] = new String[arr.size()]; 
		  
		        // ArrayList to Array Conversion 
		        for (int j = 0; j < arr.size(); j++) { 
		  
		            // Assign each value to String array 
		            str[j] = arr.get(j); 
		        } 
		  
		        return str; 
		    } 
			public static String GetRuleName(String ruleString) {
				if(ruleString.isEmpty()) {
					return "";
				}
				String RuleName=ruleString.charAt(0)+"";
				for(int i=1;i<ruleString.length();i++) {
					if((ruleString.charAt(i)+"").equals("'")) {
						RuleName+="'";
					}
					else {
						break;
					}
				}
				return RuleName;
				
			}
			public static String[] Substitute(String [] rule , ArrayList<String[]> grammer ,int index) {
				String[] newRule=rule.clone();
			    ArrayList<String> ListRule=new ArrayList<String>();
			    ListRule.add(rule[0]);
			    //System.out.println(ListRule.get(0));
			    //Loop Over All roles
			    String RuleName=GetRuleName(rule[0]);
			    boolean WillSub=false;
			    	for(int j=1;j<rule.length;j++) {
			    		if(rule[j].length()>0) {
			    		if(Character.isUpperCase(rule[j].charAt(0))){
			    			String SubRule=GetRuleName(rule[j]);
			    			
			    			//System.out.println("The rule is"+rule[j]);
			    			for(int i=0;i<index;i++) {
			    			if(grammer.get(i)[0].equals(SubRule) && !RuleName.equals(SubRule)) {
			    				WillSub=true;

			    				//System.out.println("Here");
			    				    //System.out.println("Got"+SubRule+toRuleString(grammer.get(i)));
			    			        String[] SubRuleArray=grammer.get(i);
			    			        for(int k=1;k<SubRuleArray.length;k++) {
			    			        	String newString=SubRuleArray[k]+rule[j].substring((SubRule).length(),rule[j].length());
			    			        	if(!newString.equals(rule[j])) {
			    			        	//System.out.println(newString);
			    			        	ListRule.add(newString);
			    			        	}
			    			        }
			    			        for(int z=1;z<rule.length;z++) {
			    			        	if(z!=j) {
			    			        	ListRule.add(rule[z]);
			    			        	//System.out.println(rule[z]);

			    			        	}
			    			        }
			    			        
			    					//System.out.println(SubRule);
			    	    	rule=GetStringArray(ListRule);
			    	    	//System.out.println(toRuleString(rule));
			    	    	j=0;
			    	    	

			    					
			    			}

			    			}
			    	    	
			    	    	
			    	    	//System.out.println(toRuleString(rule));
			    		    


			    		}
			    		ListRule=new ArrayList<String>();
			    		ListRule.add(rule[0]);
			    	
			    		
			    		
			    	
			    }
			    	}

			    	if(WillSub) {
			    		
			    	
				return rule;
			    	}
			    	else {
			    		return newRule;
			    	}
				
			}
			
			public String First() {
				String s= "";
				for (int i=0;i<Grammer.size();i++) {
					//System.out.println("Current Rule"+GetRuleName(Grammer.get(i)[0]));
					if(GetFirst(GetRuleName(Grammer.get(i)[0]))==null) {
						//System.out.println("Here"+GetRuleName(Grammer.get(i)[0]));

						String first=HelperFirst(Grammer.get(i),null);

						first=sortString(first);
						//System.out.println("Adding First to list"+GetRuleName(Grammer.get(i)[0])+","+first);
						First.add(GetRuleName(Grammer.get(i)[0])+","+first);

					}
				}
				for(int i=0;i<First.size()-1;i++) {
					String [] FirstString=First.get(i).split(",");
					//System.out.println(FirstString[1]);
					s+=First.get(i)+";";
					
					//System.out.println(First.get(i));
				}
				s+=First.get(First.size()-1);

				return s;
				
			}
			public String HelperFirst(String [] Rule, String Previous) {
				String first="";
				//System.out.println("Rule to work wiz"+Rule);
				String RuleString =toRuleString(Rule);
				String RuleName=GetRuleName(RuleString);
				//System.out.println(RuleName);

				//Array<String> ComputedRules=
				for(int i=1;i<Rule.length;i++) {
					
					/*if(Character.isLowerCase(Rule[i].charAt(0))) {
						first+=Rule[i].charAt(0)+"";
					    System.out.println("No Recursion"+Rule[i]);

					}*/
					//Skip if cycle
					if(Previous ==null || !Previous.equals(Rule[i].charAt(0)+"")){
					String Output= ComputeFirst(RuleName,Rule[i]);
					first+=Output;
					}
					
					
					
				}
				

				
				return uniqueCharacters(first);
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
			public String GetFirst(String RuleName) {
				for (int i=0;i<this.First.size();i++) {
					String rule=this.First.get(i);
					//Added First to List
					//System.out.println("In get first"+rule);
					if(!rule.isEmpty() &&(rule.charAt(0)+"").equals(RuleName)){
						return rule;
					}
					
				}
				return null;
			}
			
			public boolean GoesToEpsilon(String[] rule) {
				for(int i=0;i<rule.length;i++) {
					if(rule[i].equals("e")) {
						return true;
					}
				}
				return false;
				
			}
			//Takes a String one RHS of a rule
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
			public String ComputeFirst(String RuleName,String Rules) {
				
				String FirstofRHS = "";
				if( Rules.length()>0 ) {
					if( Character.isLowerCase(Rules.charAt(0))) {
					return Rules.charAt(0)+"";
					}
					else {
						//System.out.println(FindRule(Rules.charAt(0)+""));
						String First=HelperFirst(FindRule(Rules.charAt(0)+""),RuleName);
						if(First.contains("e") && Rules.length()>1) {
							First = First.replace("e", "");
							return First+ComputeFirst(Rules.charAt(0)+"",Rules.substring(1));
							
						}
						else {
							return First;
						}
						
					}
				}
				return "";




				
				
			}
			public String[] FindRule(String RuleName) {
				for(int i=0;i<Grammer.size();i++) {
					//System.out.println(RuleName);
					if(Grammer.get(i)[0].equals(RuleName)) {
						return Grammer.get(i);
					}
				}
				return null;
			}
			public String Follow() {
				String s= "";
				for (int i=0;i<Grammer.size();i++) {
					//System.out.println("Current Rule"+GetRuleName(Grammer.get(i)[0]));
					
						String follow=HelperFollow(Grammer.get(i)[0]," ");
						
						follow=sortString(follow);
						if(follow.contains("$")) {
							follow=follow.replace("$", "");
							follow=follow+"$";
						}


						//System.out.println("Adding First to list"+GetRuleName(Grammer.get(i)[0])+","+first);
						Follow.add(GetRuleName(Grammer.get(i)[0])+","+follow);

					
				}
				for(int i=0;i<Follow.size()-1;i++) {
					String [] FollowString=Follow.get(i).split(",");
					//System.out.println("Follow of "+FollowString[0]+" "+FollowString[1]);
					s+=Follow.get(i)+";";
					
					//System.out.println(First.get(i));
				}
				s+=Follow.get(Follow.size()-1);
				

				return s;
				
			}
			String GetFollow(String RuleName) {
				for (int i=0;i<this.Follow.size();i++) {
					String rule=this.Follow.get(i);
					//Added First to List
					//System.out.println("In get first"+rule);
					if(!rule.isEmpty() &&(rule.charAt(0)+"").equals(RuleName)){
						return rule;
					}
					
				}
				return null;
			}
			public String HelperFollow(String RuleName,String Previous) {
				String follow="";
				//System.out.println(RuleName);

				//Array<String> ComputedRules=
				if(GetFollow(RuleName)!=null) {
					return GetFollow(RuleName).split(",")[1];
				}
						for(int i=0;i<Grammer.size();i++) {
							String[] Rule=Grammer.get(i);
							for(int j=1;j<Rule.length;j++) {
							if(! Previous.equals(Rule[0]) ) {
								//System.out.println("Here for in HELPER FOLLOW"+RuleName);

							String output=ComputeFollow(Rule[0],RuleName,Rule[j]);
							follow+=output;

							}
							else {

								//System.out.println("Couldn't Get in Cuz "+Previous);
							}
						}
							

						}
						//Previous= " ";


						

			if(RuleName.equals("S")) {
							follow+="$";
						}	
			return uniqueCharacters(follow);
				
			}
			
			public String ComputeFollow(String HeadName,String RuleName,String Rule) {
				String output="";
				this.First();
				if(Rule.length()>1) {
					if(!(Rule.charAt(0)+"").equals(RuleName)) {
						//System.out.println(HeadName+"Rule name"+RuleName+" "+Rule.substring(1));
						//System.out.println(Rule.substring(1).length());
						return ComputeFollow(HeadName,RuleName,(Rule.substring(1)));
					}
					else if(Character.isLowerCase(Rule.charAt(1))) {
						
						return Rule.charAt(1)+ComputeFollow(HeadName,RuleName,(Rule.substring(2)));
					}
					else if (Character.isUpperCase(Rule.charAt(1))){
						String[] FirstString=GetFirst(Rule.charAt(1)+"").split(",");
						if(FirstString[1].contains("e")) {
							String EpsilonRemoved=FirstString[1].replace("e","");
							Rule=Rule.replace(Rule.charAt(1)+"", "");
							if(Rule.length()==1) {
								//System.out.println("Removed Epsilon from"+HeadName+RuleName);

								return EpsilonRemoved+HelperFollow(HeadName,RuleName);
							}
							else {
							return EpsilonRemoved+ComputeFollow(HeadName,RuleName,Rule);
							}
						}
						else {
							return FirstString[1]+ComputeFollow(HeadName,RuleName,Rule.substring(2));
						}
					}
				}
				else if((Rule.length()==1 ) && (Rule.charAt(0)+"").equals(RuleName)) {
					//System.out.println("Here for "+RuleName+" Head "+HeadName);
					//System.out.println( HelperFollow(HeadName,RuleName));

					return HelperFollow(HeadName,RuleName);
					
				}
				else {
					//System.out.println("Neither");
				}
				
				
				return output;
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
			
			public CFG(String s) {
				this.Representation=s;
				
				 this.Grammer=new ArrayList<String[]>();
				 this.First=new ArrayList<String>();
				 this.Follow=new ArrayList<String>();


					String output="";
					//Prepare Grammer
				    String[] rules=s.split(";");
				    for(int i=0;i<rules.length;i++) {
				    	Grammer.add(rules[i].split(",",-1));
				    	//System.out.println(rules[i].split(",").length);
				    }
				    
			}
			public String parse(String s) {
				return this.Mytable.Parse(s);
			}
			public static void main(String[]args) {
				String grammar = "S,iST,e;T,cS,a";
				String input1 = "iiac";
				String input2 = "iia";
				CFG g = new CFG(grammar);
				System.out.println(g.table());
				System.out.println(g.parse(input1));
				System.out.println(g.parse(input2));
				
			}
	 }
}
