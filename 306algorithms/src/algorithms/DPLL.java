package algorithms;

import java.util.ArrayList;

public class DPLL {

	public static void main(String[] args) {
		Clause clause = new Clause();
		ArrayList<Literal> truthAssignmentSoFar = new ArrayList<Literal>();
		
		ArrayList<Literal> clause1 = new ArrayList<Literal>();
		ArrayList<Literal> clause2 = new ArrayList<Literal>();
		ArrayList<Literal> clause3 = new ArrayList<Literal>();
		ArrayList<Literal> clause4 = new ArrayList<Literal>();
		
		ArrayList<ArrayList<Literal>> formula = new ArrayList<ArrayList<Literal>>();

		Literal X1 = new Literal("X1");
		Literal X2 = new Literal("X2");
		Literal X3 = new Literal("X3");
		Literal X4 = new Literal("X4");
		clause.setLiteralToAClause(clause1, X1.getNegatedLiteralConvertToLiteral());
		clause.setLiteralToAClause(clause1, X2);
		clause.setLiteralToAClause(clause2, X1.getNegatedLiteralConvertToLiteral());
		clause.setLiteralToAClause(clause2, X3);
		clause.setLiteralToAClause(clause2, X4.getNegatedLiteralConvertToLiteral());
		clause.setLiteralToAClause(clause3, X2.getNegatedLiteralConvertToLiteral());
		clause.setLiteralToAClause(clause3, X3.getNegatedLiteralConvertToLiteral());
		clause.setLiteralToAClause(clause4, X3);
		clause.setLiteralToAClause(clause4, X4);

		clause.setFormula(formula, clause1);
		clause.setFormula(formula, clause2);
		clause.setFormula(formula, clause3);
		clause.setFormula(formula, clause4);

		String result = DPLLAlg2(formula, truthAssignmentSoFar);
		System.out.println("result is " + result);
	}

	/**
	 * Performs unit propagation on the formula and creates a unit clause if it does
	 * not have one
	 * 
	 * @param formula
	 * @param truthAssignmentSoFar
	 * @return a String that says if its been satisfied or not and the truth
	 *         assignment
	 */
	public static String DPLLAlg2(ArrayList<ArrayList<Literal>> formula, ArrayList<Literal> truthAssignmentSoFar) {
		// String satisfiable = "SATISFIABLE " + "[" + newAssignment + "]";
		// String unsatisfiable = "UNSATISFIABLE";
		// Make a copy of the formula
		ArrayList<ArrayList<Literal>> duplicatedFormula = duplicateFormula(formula);
		ArrayList<ArrayList<Literal>> newForm = new ArrayList<ArrayList<Literal>>();

		String satisfiedDetermined = "";

		// Call Unit Propagation
		satisfiedDetermined = DPLLCycle(formula, truthAssignmentSoFar);
		if (satisfiedDetermined == "satisfiable" || satisfiedDetermined == "unsatisfied") {
			return satisfiedDetermined;
		}

		newForm = propagate(formula, true);
		satisfiedDetermined = DPLLCycle(newForm, truthAssignmentSoFar);
		if (satisfiedDetermined == "satisfiable") {
			return satisfiedDetermined;
		} else if (satisfiedDetermined == "unsatisfiable") {
			newForm = propagate(duplicatedFormula, false);
			truthAssignmentSoFar = new ArrayList<Literal>();
			satisfiedDetermined = DPLLAlg2(newForm, truthAssignmentSoFar);
		}
		return satisfiedDetermined + " the truth assignment is: " + toString(truthAssignmentSoFar);
	}

	/**
	 * Calls unit propagation and checks if the results of it are satisifed or not
	 * 
	 * @return A string that says satisfiable or unsatisfiable
	 */
	public static String DPLLCycle(ArrayList<ArrayList<Literal>> formula, ArrayList<Literal> truthAssignmentSoFar) {
		ArrayList<ArrayList<Literal>> newFormula = new ArrayList<ArrayList<Literal>>();
		// Call Unit Propagation
		Formula returnedUnitProp = UnitPropagation(formula, truthAssignmentSoFar);

		newFormula = returnedUnitProp.getReturnedFormula();

		if (newFormula.isEmpty()) {
			return "satisfiable";
		}
		for (int x = 0; x < newFormula.size(); x++) {
			if (newFormula.get(x).isEmpty()) {
				return "unsatisfiable";
			}
		}
		return "propagate";
	}

	/**
	 * If the formula doesn't have a unit clause, it takes the literal not in the
	 * truth assignment and adds it to the clause as an individual clause
	 * 
	 * @param type
	 *            a boolean to determine if the literal added was positive or it's
	 *            negated version
	 * @return the new formula with a unit clause now
	 */
	public static ArrayList<ArrayList<Literal>> propagate(ArrayList<ArrayList<Literal>> newFormula, boolean type) {
		Literal duplicateLiteral = newFormula.get(0).get(0);
		Literal temp = new Literal(duplicateLiteral.getName());
		ArrayList<Literal> tempArrayList = new ArrayList<Literal>();
		if (type) {
			if (temp.getName().contains("not_")) {
				// println("the name without substring is " + temp.getName());
				temp.setName(temp.getName().substring(4, 6));
				// println("the name substring is now " + temp.getName());
			}
			temp.setValue(true);
			tempArrayList.add(temp);
			newFormula.add(0, tempArrayList);
		} else {
			temp.setName(duplicateLiteral.getName());
			temp.setValue(false);
			tempArrayList.add(temp);
			newFormula.add(0, tempArrayList);
		}
		return newFormula;
	}

	/**
	 * Goes through the formula and preforms unit propagation
	 * 
	 * @param formula
	 * @param truthAssignmentSoFar
	 * @return
	 */
	public static Formula UnitPropagation(ArrayList<ArrayList<Literal>> formula,
			ArrayList<Literal> truthAssignmentSoFar) {
		Literal unitClause = new Literal("");
		
		for (int x = 0; x < formula.size(); x++) {
			while (UnitClause(formula).getName() != "" && !formula.get(x).isEmpty()) {
				unitClause = UnitClause(formula);

				if (!unitClause.getName().contains("not_")) {
					unitClause.setValue(true);
					truthAssignmentSoFar.add(unitClause);
				} else {
					unitClause.setValue(false);
					Literal tempClause = new Literal("");
					tempClause.setName(unitClause.getName().substring(4, 6));
					tempClause.setValue(unitClause.getValue());
					truthAssignmentSoFar.add(tempClause);
				}

				// println("the unit clause is " + unitClause.getName() + " and it's value is "
				// + unitClause.getValue());
				boolean quit = false;
				for (int i = 0; i < formula.size(); i++) {
					for (int j = 0; j < formula.get(i).size() || quit; j++) {

						if (!unitClause.getName().contains("not_")) {

							if (unitClause.getName().equals(formula.get(i).get(j).getName())) {
								// println("unit clause is " + unitClause.getName() + " the item is "
								// + formula.get(i).get(j).getName());
								formula.remove(i); // remove every clause with that in it
								if (formula.isEmpty()) {
									quit = true;
									break;
								}
								j = -1; // set j so it becomes 0 again if a clause is removed

								// in case at the end of it
								if (formula.size() == 1 && formula.get(i).size() == 1) {
									quit = true;
									break;
								}

							} else if (unitClause.getNameOfNegatedLiteral().equals(formula.get(i).get(j).getName())) {
								// println("unit clause is " + unitClause.getName() + " the item is should be
								// negated"
								// + formula.get(i).get(j).getName());

								formula.get(i).remove(j); // just remove the item from the clause

								if (formula.get(i).isEmpty()) {
									return (new Formula(formula, truthAssignmentSoFar));
								}
							}
						} else {
							if (unitClause.getName().equals(formula.get(i).get(j).getNameOfNegatedLiteral())) {
								formula.get(i).remove(j); // remove every item from every clause in the formula
								if (formula.get(i).isEmpty()) {
									// formula.remove(i);
									return (new Formula(formula, truthAssignmentSoFar));
								}
							} else if (unitClause.getName().equals(formula.get(i).get(j).getName())) {
								// println("unit clause is " + unitClause.getName() + " the item is "
								// + formula.get(i).get(j).getName());

								formula.remove(i); // remove every clause that contains negated literal from formula

								if (formula.isEmpty()) {
									quit = true;
									break;
								}

								j = -1;

								// in case at the end of it
								if (formula.size() == 1 && formula.get(i).size() == 1) {
									quit = true;
									break;
								}

							}
						}
					}
				}
			}
		}
		return new Formula(formula, truthAssignmentSoFar);
	}

	/**
	 * Determines if there is a unit clause in the formula already
	 * 
	 * @return A literal that is the unit clause
	 */
	public static Literal UnitClause(ArrayList<ArrayList<Literal>> formula) {
		Literal unitClause = new Literal("");
		for (int i = 0; i < formula.size(); i++) {
			for (int j = 0; j < formula.get(i).size(); j++) {
				if (formula.get(i).size() == 1) {
					unitClause.setName(formula.get(i).get(j).getName());
					unitClause.setValue(formula.get(i).get(j).getValue());
				}
			}
		}
		return unitClause;
	}

	public static void println(String str) {
		System.out.println(str);
	}

	/**
	 * Creates a deep copy of the formula so it can feed it to DPLL unaltered
	 * 
	 * @param formula
	 * @return A copy of the formula
	 */
	public static ArrayList<ArrayList<Literal>> duplicateFormula(ArrayList<ArrayList<Literal>> formula) {
		ArrayList<ArrayList<Literal>> tempFormula = new ArrayList<ArrayList<Literal>>();

		for (int x = 0; x < formula.size(); x++) {
			ArrayList<Literal> tempArray = new ArrayList<Literal>();
			for (int y = 0; y < formula.get(x).size(); y++) {
				Literal newLit = new Literal("");
				newLit.setName(formula.get(x).get(y).getName());
				newLit.setValue(formula.get(x).get(y).getValue());
				tempArray.add(newLit);
			}
			tempFormula.add(tempArray);
		}
		return tempFormula;
	}

	/**
	 * A simple method to print of the truth assignment
	 * 
	 * @param truthAssignmentSoFar
	 * @return
	 */
	public static String toString(ArrayList<Literal> truthAssignmentSoFar) {
		String str = "";
		for (int i = 0; i < truthAssignmentSoFar.size(); i++) {
			str += truthAssignmentSoFar.get(i).getName() + " = " + truthAssignmentSoFar.get(i).getValue() + " ";
		}
		return str;
	}
}
