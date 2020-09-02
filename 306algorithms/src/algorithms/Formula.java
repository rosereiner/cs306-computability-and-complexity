package algorithms;

import java.util.ArrayList;


public class Formula {
	ArrayList<ArrayList<Literal>> formula = new ArrayList<ArrayList<Literal>>();
	ArrayList<Literal> truthAssignmentSoFar = new ArrayList<Literal>();
	Literal unitClause;
	String name;

	public Formula(ArrayList<ArrayList<Literal>> formula, ArrayList<Literal> truthAssignmentSoFar) {
		this.formula = formula;
		this.truthAssignmentSoFar = truthAssignmentSoFar;
	}
	
	public ArrayList<ArrayList<Literal>> getReturnedFormula(){
		return formula;
	}
	
	public String getReturnedTruthAssignmentSoFarAsAString(){
		String str = "";
		for(int x = 0; x < truthAssignmentSoFar.size(); x++) {
			str += truthAssignmentSoFar.get(x).getName() + " is " + truthAssignmentSoFar.get(x).getValue();
		}
		return str;
	}
	public ArrayList<Literal> getReturnedTruthAssignmentAsArrayList(){
		return truthAssignmentSoFar;
	}
	
	
	
}
