package algorithms;

/**
 * @author Rose Reiner A method that implements the dynamic programming problem
 *         from CS305 O(nW) Dynamic Programming Problem
 */
public class CS305Alg_Knapsack01 {

	/**
	 * A method to implement the 01-Knapsack O(nW) problem algorithm
	 * 
	 * @param n for the number of items in our knapsack,
	 * 
	 * @param B for the budget; the max amount of money we can spend on an item
	 * 
	 * @param Vi for the value of each item stored in an array of ints
	 * 
	 * @param Ci for the cost of each item stored in an array of ints Implementing
	 * this base case first
	 */
	public static int knapsack_01(int n, int B, int[] Vi, int[] Ci) {
		// Initialize size of table to be filled out... where rows are the number of
		// items + 1 and columns are the max budget from 0..B
		int[][] table = new int[n + 1][B + 1];
		
		// Handle the base-case first...when we run out of items i, then the value of
		// them is 0
		
		// Length of the rows is 5 from index 0...4 so we fill out all columns on row 4
		// because thats the end of the rows and it includes 0
		for (int b = 0; b <= B; b++) {
			table[n][b] = 0;
		}

		// Consider all the i's from n downto 1 and all the b's 0 to B
		int maxValue = 0;
		int firstChoice = 0;
		int secondChoice = 0;
		for (int i = n; i >= 1; i--) {
			for (int b = 0; b <= B; b++) {
				// If the cost of i <= b then we take the max of the two options
				if (Ci[i - 1] <= b) {

					// We take the item
					firstChoice = (table[i][b - Ci[i - 1]] + Vi[i - 1]);
					// We don't take the item
					secondChoice = table[i][b];
					// take the max of both options
					maxValue = Math.max(firstChoice, secondChoice);

					// Assign the value to the table
					table[i - 1][b] = maxValue;
					
				} else {
					// Skip the item because we can't afford it
					table[i - 1][b] = table[i][b];
				}
			}
		}
		return table[1][B];
	}

}
