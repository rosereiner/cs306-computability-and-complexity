package algorithms;

import java.util.ArrayList;
import java.util.Random;
/**
@author Rose Reiner
*/

public class MAX3SAT {
	
	/**
	 * A method that calculates the expected number of clauses satisfied by a random boolean variable assignment
	 * Calculates the E[y]
	 * @param formula A set of m clauses with 3 literals in each clause
	 * @param truth_assignment An array of Literals
	 * @return a String that says how many clauses were satisfied and the truth assignment
	 */
	public static String MAX3SAT_alg(ArrayList<ArrayList<Literal>> formula, Literal[] truth_assignment) {

		double expected_Yi = 0.0; //Pr(Ci is satisfied) = probability of the event occuring
		double PR_ofCi_Sat = 0.0; //probability that Ci is satisfied
		double PR_ofCi_Unsat = 0.0; //Probability that Ci is unsatisfied
		double Y = 0.0; // The number of clauses satisfied
		double expected_Y = 0.0; //Expected number of clauses satisfed
		
		int m = formula.size(); //number of clauses in the formula
		//int n = truth_assignment.length; // number of literals
		
		//Flip "coin" and assign values based on the coin flip
		Random random = new Random();
		String str = "";
		for(int i = 0; i < truth_assignment.length; i++) {
			int value = random.nextInt(2); //gets random numbers 0-1
			if(value == 1) { //1 is heads
				truth_assignment[i].setValue(true);
			}else { //0 is tails
				truth_assignment[i].setValue(false);
			}
			str += truth_assignment[i].getName() + " = " + truth_assignment[i].getValue() + " ";
		}
		
		//Calculate the E[Yi]
		PR_ofCi_Unsat = Math.pow(0.5, formula.get(0).size());
		PR_ofCi_Sat = (1 - PR_ofCi_Unsat);
		expected_Yi = PR_ofCi_Sat;
		
		expected_Y = (expected_Yi*m);
		System.out.println("E[Y] = " + expected_Y);
		
		// go through each clause and count how many unsatisfied
		for (int x = 0; x < formula.size(); x++) {
			boolean result = checkSatisfied(formula.get(x), truth_assignment);
			if(result == true) {
				Y++;
			}
		}
		return Y + " clauses were satisfied with the truth assignment: " + str;
	}
	
	
	/**
	 * A method that is called from findUnSatisfied that compares the name of each literal in a clause
	 * and assigns it value depending on the truth_assignment given
	 * @param clause; each individual clause in the formula
	 * @param truth_assignment; the truth_assignment 
	 * @return true if the clause is satisfied and false if the clause is unsatisfied
	 */
	public static boolean checkSatisfied(ArrayList<Literal> clause, Literal[] truth_assignment) {
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
}
