package algorithms;

import java.util.ArrayList;
import java.util.Arrays;

public class Greedy2Approx {

	public static void main(String[] args) {
		double[] Vi = { 1, 2, 5, 6 };
		double[] Ci = { 2, 3, 4, 5 };
		double result = 0.0;
		result = modifiedGreedy(4, 8, Vi, Ci);
		System.out.println("modified greedy returns: " + result);
	}

	public static double modifiedGreedy(int n, int B, double[] Vi, double[] Ci) {
		double a_max = 0;
		int L = B; // L is the amount of money left
		double valueOfG = 0;
		Item[] items = new Item[n];
		Item[] sortedItems = new Item[n];
		double[] sortedFractions = new double[n];

		ArrayList<Integer> G = new ArrayList<Integer>(); // Initialize G to be an empty set

		double fractionValue = 0.0;
		for (int i = 0; i < n; i++) {
			Item A = new Item();
			fractionValue = (double) (Vi[i] / Ci[i]);
			sortedFractions[i] = fractionValue;

			A.setValue((int) Vi[i]);
			A.setCost((int) Ci[i]);
			A.setFraction(fractionValue);
			items[i] = A;

		}

		int i = (n - 1);
		for (int x = 0; x < sortedFractions.length; x++) {
			for (int y = 0; y < items.length; y++) {
				if (sortedFractions[x] == items[y].getFraction()) {
					sortedItems[i] = items[y];
					i--;
				}
			}
		}

		for (int a = 0; a < n; a++) {

			if (sortedItems[a].getCost() <= L && L > 0) {
				G.add(sortedItems[a].getValue());
				L = (L - sortedItems[a].getCost());
				valueOfG += sortedItems[a].getValue();
			}

		}

		// sort Vi to find value of a_max
		Arrays.sort(Vi);

		// set value of a_max to be the last value in the array since it's sorted in
		// ascending order
		a_max = Vi[n - 1];

		// Get Value(G) which is the total values of items in G

		// Check
		if (a_max > valueOfG) {
			return a_max;
		} else {
			return valueOfG;
		}

	}

}
