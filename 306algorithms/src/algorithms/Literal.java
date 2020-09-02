package algorithms;

public class Literal {
	String name;
	boolean value;
	NegatedLiteral negatedLiteral = new NegatedLiteral();
	
	
	public Literal(String name) {
		this.name = name;
		//this.value = value;

		negatedLiteral.setName("not_" + name);
		negatedLiteral.setValue(!value);
	}
	public void setName(String name) {
		this.name = name;
		negatedLiteral.setName("not_" + name);
	}
	public String getName() {
		return name;
	}
	
	public String getNameOfNegatedLiteral(){
		return negatedLiteral.getName();
	}
	public NegatedLiteral getNegatedLiteral() {
		return negatedLiteral;
	}
	
	public void setValue(boolean value) {
		this.value = value;
		negatedLiteral.setValue(!value);
	}
	
	public boolean getValue() {
		return value;
	}
	
	public boolean getValueOfNegatedLiteral() {
		return negatedLiteral.getValue();
	}
	
	public Literal getNegatedLiteralConvertToLiteral() {
		Literal newLit = new Literal(negatedLiteral.getName());
		newLit.setValue(negatedLiteral.getValue());
		return newLit;
	}
	
	public String getPositiveOfNegativeLiteral() {
		return negatedLiteral.getName().substring(4);	
	}
	
	public Literal getLiteral() {
		return new Literal("");
	}

	
}
