package algorithms;

import java.util.ArrayList;

public class Clause {
	String clauseName;
	ArrayList<Literal> innerclause;
	ArrayList<ArrayList<Literal>> outterclause;
	Literal name;
	Literal unitClause;

	public Clause() {

	}
	public void set3SAT(ArrayList<Literal> innerclause, Literal name1, Literal name2, Literal name3) {
		this.innerclause = innerclause;
		innerclause.add(name1);
		innerclause.add(name2);
		innerclause.add(name3);
	}
	public ArrayList<Literal> get3Sat() {
		return innerclause;
	}

	public void setLiteralToAClause(ArrayList<Literal> innerclause, Literal name) {
		this.innerclause = innerclause;
		this.name = name;
		innerclause.add(name);
	}

	public void setFormula(ArrayList<ArrayList<Literal>> outterclause, ArrayList<Literal> innerclause) {
		this.outterclause = outterclause;
		this.innerclause = innerclause;

		outterclause.add(innerclause);
	}

	public String getFormula() {
		String newStr = "";
		for (int i = 0; i < outterclause.size(); i++) {
			newStr += "[";
			for (int j = 0; j < outterclause.get(i).size(); j++) {

				if (j < outterclause.get(i).size() - 1 && outterclause.get(i).size() != 1) {
					newStr += outterclause.get(i).get(j).getName() + ", ";
				} else {
					newStr += outterclause.get(i).get(j).getName();
				}
			}

			newStr += "],";

		}
		return newStr;
	}

}
