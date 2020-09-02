package algorithms;

import java.util.ArrayList;
import java.util.Arrays;

public class MinCost {

	public static void main(String[] args) {
		int[][] mincost_return = null;
		boolean[][] take_return = null;
		int[] Vi = {75, 102, 75};
		int[] Ci = {50, 51, 50};
		Tables tables = new Tables(mincost_return, take_return);
		tables = mincost_alg(253, Vi, Ci);
		mincost_return = tables.getMinCost();
		take_return = tables.getTake();
		System.out.println(tables.getTakeValues());
		System.out.println(tables.getMinCost_Values());
		System.out.println();
		
		String str = constructMaxKnapsackSolution(mincost_return, take_return,Vi,Ci, 100, Vi.length);
		System.out.println("str is " + str);
	}
	
	public static Tables mincost_alg(int T, int[] Vi, int[] Ci) {
			double infinity = Double.POSITIVE_INFINITY;
			int NextT = 0;
			int[] tempVi = new int[Vi.length];
			for(int i = 0; i < Vi.length; i++) {
				int newInt = Vi[i];
				tempVi[i] = newInt;
			}
			Arrays.sort(tempVi);
			int A_max = tempVi[tempVi.length - 1];
			
			
			//int A_max = Vi[0];//A_max has the value of item 1
			//System.out.println("A_max value is: " + A_max);
			int[][] MinCost = new int[Vi.length + 1][(Vi.length*A_max) + 1];
			boolean[][] Take = new boolean[Vi.length + 1][Vi.length*A_max + 1];
			
			//Base Cases
			//When target is 0, then there is no cost 
			for(int i = 0; i <= Vi.length;i++) {
				MinCost[i][0] = 0;
			}
			//When we have 1 item and V(1) is >= t, so we take this item
			for(int t = 1; t <= Vi[0]; t++) {
				MinCost[0][t] = Ci[0]; //set MinCost of item 1 and target t to be the cost of item 1
				Take[0][t] = true; //we take this object
			}

			//When we have one item and V(1) is less than the target, we don't take item 1
			for(int t = Vi[0] + 1; t < (Vi.length*A_max) + 1; t++) {
				MinCost[0][t] = (int) infinity;
				Take[0][t] = false;
			}
			//Recursive part now
			
			for(int i=1; i < Vi.length; i++) { //Start at item 2
				for(int t = 1; t <= Vi.length*A_max; t++) { //Start at target 1 
//					System.out.println("At index " + i + " and target " + t + ", which is item : " + (i+1));
					//Set the next target to be 0 or the target - V(i) so we don't have negative indexes
					NextT = Math.max(0, (t - Vi[i]));
					//If MinCost of previous item at target t is less than or equal to the cost of this item + cost of last item, then don't take it
					if(MinCost[i-1][t] == MinCost[i-1][NextT]) {
						MinCost[i][t] = MinCost[i-1][t];
						Take[i][t] = false;
					}else if(MinCost[i-1][t] <= (Ci[i] + (MinCost[i-1][NextT]))) {
						MinCost[i][t] = MinCost[i-1][t];
						Take[i][t] = false;
					}else { //include the item
						MinCost[i][t] = (Ci[i] + MinCost[i-1][NextT]);
						Take[i][t] = true;
					}
					
				}
			}
			
			for(int i =0; i <= Vi.length; i++) {
				for(int t =0; t <= Vi.length*A_max; t++) {
			//		System.out.println("mincost table: " + Take[i][t]);
				}
			}
			return new Tables(MinCost, Take);
		}
	public static String constructMaxKnapsackSolution(int[][] mincost, boolean[][] Take,int[] Vi,int[] Ci,int B, int n) {
		int[] tempVi = new int[Vi.length];
		for(int i = 0; i < Vi.length; i++) {
			int newInt = Vi[i];
			tempVi[i] = newInt;
		}
		Arrays.sort(tempVi);
		int A_max = tempVi[tempVi.length - 1];
		//int A_max = Vi[0];//A_max has the value of item 1
		int optimalValue = n*(A_max);
		//int optimalValue = 150;
		
		while(optimalValue > 0 && mincost[n-1][optimalValue] >  B) {
			optimalValue = optimalValue - 1;
		}
		
		ArrayList<String> Solution = new ArrayList<String>();
		int i = n;
		int t = optimalValue;
		
		while(i > 0 && t > 0) {
			if (Take[i-1][t] == true) {
				Solution.add("item " + i); //object i was chosen for MinCost[i,t]
				t = t-Vi[i-1]; //update t to indeicate value recieved for i
			}
			i = i-1; //look at the previous object now
		}
		return optimalValue +"";
		//return "solution is " + Arrays.toString(Solution.toArray()) + " and the optimal value is " + optimalValue;
	}
}
