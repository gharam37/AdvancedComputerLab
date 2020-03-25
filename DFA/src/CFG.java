import java.util.ArrayList;

import java.util.Collections;
import java.util.Stack;
import java.util.Arrays;

public class CFG {

	public static String LRE(String input) {
	    ArrayList<String[]> Grammer=new ArrayList<String[]>();
		String output="";
		//Prepare Grammer
	    String[] rules=input.split(";");
	    for(int i=0;i<rules.length;i++) {
	    	Grammer.add(rules[i].split(",",-1));
	    	//System.out.println(rules[i].split(",").length);
	    }
	    for(int i=0;i<Grammer.size();i++) {
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
	    		Grammer.add(i+1,NewRules.get(1));

	    		
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
	    	System.out.println(toRuleString(Grammer.get(i)));
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
		String[] newRule=null;
	    ArrayList<String> ListRule=new ArrayList<String>();
	    ListRule.add(rule[0]);
	    //System.out.println(ListRule.get(0));
	    //Loop Over All roles
	    	for(int j=1;j<rule.length;j++) {
	    		if(rule[j].length()>0) {
	    		if(Character.isUpperCase(rule[j].charAt(0))){
	    			String SubRule=GetRuleName(rule[j]);
	    			//System.out.println("The rule is"+SubRule);
	    			for(int i=0;i<index;i++) {
	    			if(grammer.get(i)[0].equals(SubRule)) {

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
	    					
	    			}
	    			}
	    	    	rule=GetStringArray(ListRule);
	    	    	j=1;
	    	    	//System.out.println(toRuleString(rule));
	    		    


	    		}
	    		ListRule=new ArrayList<String>();
	    		ListRule.add(rule[0]);
	    		
	    		
	    	
	    }
	    	}

		return rule;
		
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
	
	public static void main(String[]args) {
		String input ="S,ScT,T;T,aSb,iaLb,i;L,SdL,S" ;
				
				
        String output = LRE(input);
       // System.out.print(output);
		
		System.out.println("Hello World");
		
	}
}
