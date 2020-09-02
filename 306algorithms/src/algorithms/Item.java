package algorithms;

public class Item {
		int cost;
		int value;
		double fraction;
	
	public Item(){
		
	}
	
	public int setCost(int cost) {
		return this.cost = cost;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int setValue(int value) {
		return this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public double setFraction(double fraction) {
		return this.fraction = fraction;
	}
	
	public double getFraction() {
		return fraction;
	}
	
}
