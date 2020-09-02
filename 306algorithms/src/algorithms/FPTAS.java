package algorithms;

import java.util.Arrays;

public class FPTAS {

	/**
	 * Scales the values in Vi then calls the MinCost Knapsack algorithm
	 * @param n number of values
	 * @param epsilon error tolerance
	 * @param Vi array of values
	 * @param Ci array of costs
	 * @param B budget
	 * @return an Object of Tables that contains a mincost table and a take table
	 */
	public static Tables FPTAS_Alg(int n, double epsilon,int[] Vi, int[] Ci, int B) {
		int[] tempVi = new int[Vi.length];
		for(int i = 0; i < Vi.length; i++) {
			int newInt = Vi[i];
			tempVi[i] = newInt;
		}
		Arrays.sort(tempVi);
		double a_max = (double)tempVi[tempVi.length - 1];
		double F = 0.0;
		double scaled = 0.0;
		
		F = (n)*(1/epsilon);
		//scale the values
		for(int i = 0; i < Vi.length; i++) {
			scaled = (Vi[i]/a_max);
			scaled = (scaled*F);
			scaled = Math.floor(scaled);
			Vi[i] = (int)scaled;

		}
		return MinCost.mincost_alg(B, Vi, Ci);	
	}
}
