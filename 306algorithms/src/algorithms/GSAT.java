package algorithms;

import java.util.ArrayList;


public class GSAT {
	private String name;
	private boolean value;

	/**
	 * A method that flips through all the truth assignment values and finds the best one of each
	 * @param outterclause is the formula consisted of m clauses and n literals
	 * @param incomingTruthAssignment the randomized truth assignment values
	 * @return the best truth assignments(s) that give the most amount of clauses satisfied
	 */
	public static String GSAT_alg(ArrayList<ArrayList<Literal>> outterclause, GSAT[] incomingTruthAssignment) {
		int rounds = incomingTruthAssignment.length;
		int roundsOn = incomingTruthAssignment.length -(incomingTruthAssignment.length - 1);
		//String best_assignment = "";
		int temp = 0;
		int betterCount = 0;
		


		GSAT[] truth_assignment = new GSAT[incomingTruthAssignment.length];
		//Duplicate the incoming truth assignment
		for(int i =0; i < truth_assignment.length; i++) {
			truth_assignment[i] = incomingTruthAssignment[i].duplicate();
		}
		String best_assignment = "";
		int numberOfUnsatisfied =0;
		while(roundsOn <= rounds) {
			numberOfUnsatisfied= findUnSatisfied(outterclause, truth_assignment);
		
			if(roundsOn == 1) {
				temp = numberOfUnsatisfied;
				betterCount = temp;
				best_assignment = Truth_Assignment_To_String(truth_assignment);
			}else{
				if(numberOfUnsatisfied < betterCount) {
					temp = numberOfUnsatisfied;
					betterCount = temp;
					best_assignment = Truth_Assignment_To_String(truth_assignment);
				}else if(numberOfUnsatisfied == betterCount) {
					best_assignment += " and " + Truth_Assignment_To_String(truth_assignment);
				}
			}

			//duplicate truth assignment again
			for(int i =0; i < truth_assignment.length; i++) {
				truth_assignment[i] = incomingTruthAssignment[i];
			}
			
			//Change one of the literals
			if(roundsOn > 1) {
				truth_assignment[roundsOn-2].setValue(!incomingTruthAssignment[roundsOn-2].getValue());
			}
			truth_assignment[roundsOn-1].setValue(!incomingTruthAssignment[roundsOn-1].getValue());
	
			//increment Rounds
			roundsOn++;
		}
	
		//return best_assignment + " and count of unsatisfied was " + betterCount;
		return "count = " + betterCount;
	}
	/**
	 * A method that loops through the formula and counts how many unsatisfied clauses there are 
	 * with that truth assignment
	 * @param outterclause; the formula that consists of clauses m and literals n
	 * @param truth_assignment; randomized truth assignment
	 * @return the number of unsatisfied clauses 
	 */
	public static int findUnSatisfied(ArrayList<ArrayList<Literal>> outterclause, GSAT[] truth_assignment) {
		int count = 0;
		// go through each clause and count how many unsatisfied
		for (int x = 0; x < outterclause.size(); x++) {
			boolean result = isSatisfied(outterclause.get(x), truth_assignment);
			if(result == false) {
				count++;
			}
		}
		return count;
	}
	/**
	 * A method that is called from findUnSatisfied that compares the name of each literal in a clause
	 * and assigns it value depending on the truth_assignment given
	 * @param clause each individual clause in the formula outterclause
	 * @param truth_assignment the truth_assignment 
	 * @return true if the clause is satisfied and false if the clause is unsatisfied
	 */
	public static boolean isSatisfied(ArrayList<Literal> clause, GSAT[] truth_assignment) {
		boolean result = false;
		boolean thevalue = false;
		boolean[] assignments = new boolean[truth_assignment.length];
		for (int x = 0; x < clause.size(); x++) {
			for (int y = 0; y < truth_assignment.length; y++) {

				if (clause.get(x).getName().equals(truth_assignment[y].getName())) {
					thevalue  = truth_assignment[y].getValue();
					assignments[y] = thevalue;
				
					
				}else if(clause.get(x).getName().equals("not_" + truth_assignment[y].getName())) {
					thevalue = !(truth_assignment[y].getValue());
					assignments[y] = thevalue;
				}
			}
			
			for(int y = 0; y < assignments.length; y++) {
				if(assignments[y] == true) {
					result = true;
					break;
				}
				result = false;
			}
		}

		return result;
	}
	
	/**
	 * A method that converts the GSAT[] truth_assignment to a string
	 * @param truth_assignment; an array of GSAT 
	 * @return a string representation of the truth_assignment 
	 */
	public static String Truth_Assignment_To_String(GSAT[] truth_assignment) {
		String str = "";
		for(int x = 0; x < truth_assignment.length; x++) {
			str += truth_assignment[x].getName() + " = " + truth_assignment[x].getValue() + " ";
		}
		return str;
	}
	/**
	 * A method that converts the clauses and literals into a string
	 * @param outterclause; formula consisting of clauses and literals
	 * @return a string representation of the formula
	 */
	public static String clauses_toString(ArrayList<ArrayList<Literal>> outterclause) {
		String str = "";
		for (int i = 0; i < outterclause.size(); i++) {
			for (int j = 0; j < outterclause.get(i).size(); j++) {
				str += outterclause.get(i).get(j).getName() + " ";
			}
			str +=", ";
		}
		return str;
	}
	
	/**
	 * An object constructor called GSAT that has a name and boolean value
	 */
	public GSAT(String name) {
		this.name = name;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public GSAT duplicate() {
		GSAT duplicateGSAT = new GSAT(this.getName());
		duplicateGSAT.setValue(this.value);
		return duplicateGSAT;
	}
	
}
