package algorithms;

public class Tables {
	private int[][] MinCost;
	private boolean[][] Take;
	
	public Tables(int[][] MinCost, boolean[][] Take) {
		this.MinCost = MinCost;
		this.Take = Take;
	}
	
	public int[][] getMinCost(){
		return MinCost;
	}
	
	public boolean[][] getTake(){
		return Take;
	}
	
	public String getTakeValues() {
		String takevalue = "";
		for(int x = 0; x < Take.length; x++) {
			for(int y = 0; y < Take[0].length; y++) {
				if(Take[x][y] == true) {
					takevalue += "[" + x + "," + y + "]" + ",";
				}
			}
		}
		return takevalue;
	}
	
	public String getMinCost_Values() {
		String mincost_values = "";
		for(int x = 0; x < MinCost.length; x++) {
			for(int y = 0; y < MinCost[0].length; y++) {
					mincost_values += MinCost[x][y] + ", ";
					
			}
		}
		return mincost_values;
	}
	
	
	
}
