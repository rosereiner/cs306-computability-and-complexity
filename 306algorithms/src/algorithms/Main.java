package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {
	/**
	 * @author Rose Reiner
	 * @param args
	 * Main method that calls the other classes to run all the algorithms
	 */
	public static void main(String[] args) {
		//Call each experiment either for SAT or Knapsack separatly
		experiementSAT();
		//experimentKnap();
		
		//Run the individual one run below
		//Generating random 100 instances to run on Knapsack problems
		double[] valueDouble = generateRandomKnapsackInstances();
		double[] costDouble = generateRandomKnapsackInstances();
		int[] valueInt = convertRandomDoublesToInts(valueDouble);
		int[] costInt = convertRandomDoublesToInts(costDouble);
		//Generating a random Budget
		Random random = new Random();
		int budget = random.nextInt(100);
		System.out.println("random budget is " + budget);
		System.out.println();
		System.out.println("--------- 0-1Knapsack (CS305 Algorithm) ----------------------------------");
		long runtimeBefore = System.nanoTime();
		int knapsack01 = CS305Alg_Knapsack01.knapsack_01(valueInt.length, budget, valueInt, costInt);
		long runtimeAfter = System.nanoTime();
		long totalTime = ((runtimeAfter - runtimeBefore)/1000);
		System.out.println("01-Knapsack returned: " + knapsack01);
		System.out.println("Time taken before 0-1 knapsack ran is " + runtimeBefore + " and time taken after it " + runtimeAfter);
		System.out.println("Overall, the time taken running on " + valueInt.length + " instances with a budget of "
		+ budget + " took: [" + totalTime + " microseconds]");
		System.out.println();
		
		System.out.println("--------- Mincost Knapsack  -------------------------------------------------");
		runtimeBefore = System.nanoTime();
		Tables mincost = MinCost.mincost_alg(budget, valueInt, costInt);
		runtimeAfter = System.nanoTime();
		totalTime = ((runtimeAfter - runtimeBefore)/1000);
		int[][] mincost_return = mincost.getMinCost();
		boolean[][] take_return = mincost.getTake();
		String str = MinCost.constructMaxKnapsackSolution(mincost_return, take_return, valueInt, costInt, budget, valueInt.length);
		System.out.println("Mincost Knapsack Alg returned: " + str);
		System.out.println("Time taken before MinCost Knapsack ran is " + runtimeBefore + " and time taken after it " + runtimeAfter);
		System.out.println("Overall, the time taken running on " + valueInt.length + " instances with a budget of "
		+ budget + " took: [" + totalTime + " microseconds]");
		System.out.println();
		
		System.out.println("--------- Greedy 2-Approximation Knapsack  ----------------------------------");
		runtimeBefore = System.nanoTime();
		double greedy2approx = Greedy2Approx.modifiedGreedy(valueDouble.length, budget, valueDouble, costDouble);
		runtimeAfter = System.nanoTime();
		totalTime = ((runtimeAfter - runtimeBefore)/1000);
		System.out.println("Greedy 2-Approximation Alg returned: " + greedy2approx);
		System.out.println("Time taken before Greedy Knapsack ran is " + runtimeBefore + " and time taken after it " + runtimeAfter);
		System.out.println("Overall, the time taken running on " + valueDouble.length + " instances with a budget of "
		+ budget + " took: [" + totalTime + " microseconds]");
		System.out.println();
		
		System.out.println("--------- FPTAS  ------------------------------------------------------");
		double epsilon = 0.5;
		runtimeBefore = System.nanoTime();
		Tables fptas = FPTAS.FPTAS_Alg(valueInt.length, epsilon, valueInt, costInt, budget);
		runtimeAfter = System.nanoTime();
		totalTime = ((runtimeAfter - runtimeBefore)/1000);
		int[][] fptas_mincost = fptas.getMinCost();
		boolean[][] fptas_take = fptas.getTake();
		String fptasStr = MinCost.constructMaxKnapsackSolution(fptas_mincost, fptas_take, valueInt, costInt, budget, valueInt.length);
		System.out.println("Mincost Knapsack Alg returned: " + fptasStr);
		System.out.println("Time taken before FPTAS ran is " + runtimeBefore + " and time taken after it " + runtimeAfter);
		System.out.println("Overall, the time taken running on " + valueInt.length + " instances with a budget of "
		+ budget + " took: [" + totalTime + " microseconds]");
	}
	/**
	 * A method that runs the SAT experiment for 100 rounds
	 */
	public static void experiementSAT() {
		//Experimenting with Max3SAT
		int[] gsatData = new int[100];
		int[] Max3SATData = new int[100];
		int sum2 = 0;
		long totalTime = 0;
		long totalTime2 = 0;
		int[] gClause = new int[100];
		int[] mClause = new int[100];
		int sumClause1 = 0;
		int sumClause2 = 0;
		
		for(int i = 0; i < 100; i++) {
			System.out.println("Round " + (i+1));
			ArrayList<ArrayList<Literal>> ThreeSAT = generate3Sat();
			
//			ArrayList<Literal> truthAssignmentSoFar = new ArrayList<Literal>();
//			String str = DPLL.DPLLAlg2(ThreeSAT, truthAssignmentSoFar);
//			System.out.println(str);
			
			//GSAT
			GSAT[] truth_assignment = generateRandomTruthAssignments();
			long beginTime = System.nanoTime();
			String gsat = GSAT.GSAT_alg(ThreeSAT, truth_assignment);
			long endTime = System.nanoTime();
			totalTime = ((endTime-beginTime));
			String parseString = gsat.substring(8).trim();
			int pp = Integer.parseInt(parseString);
			pp = ThreeSAT.size() - pp;
			System.out.println("gsat returned -- Number of clauses satisfied: " + pp);
			gsatData[i] = (int) totalTime;
			gClause[i] = pp;
			sumClause1 += pp;

			
			
			//Max3SAT
			Literal[] holdingLiterals = new Literal[5];
			Literal X1 = new Literal("X1");
			Literal X2 = new Literal("X2");
			Literal X3 = new Literal("X3");
			Literal X4 = new Literal("X4");
			Literal X5 = new Literal("X5");
			holdingLiterals[0] = X1;
			holdingLiterals[1] = X2;
			holdingLiterals[2] = X3;
			holdingLiterals[3] = X4;
			holdingLiterals[4] = X5;
			long beginTime2 = System.nanoTime();
			String maxThreeSAT = MAX3SAT.MAX3SAT_alg(ThreeSAT, holdingLiterals);
			long endTime2 = System.nanoTime();
			totalTime2 = ((endTime2-beginTime2));
			sum2 += totalTime2;
			Max3SATData[i] = (int) totalTime2;
			String parsing = maxThreeSAT.substring(0,2).trim();
			int parsedString = Integer.parseInt(parsing);
			mClause[i] = parsedString;
			sumClause2 += parsedString;
			System.out.println("MAX3SAT RETURNED -- Number of Clauses Satisfied: " + parsedString);
			
			
			System.out.println();
		}
		
		Arrays.sort(Max3SATData);
		Arrays.sort(gsatData);
	
		int average1 = (int) (totalTime/100);
		int average2 = (int)(totalTime2/100);
		int medium = gsatData.length/2;
		int medium2 = Max3SATData.length/2;
		int median1 = (gsatData[medium -1] + gsatData[medium] /2);
		int median2 = (Max3SATData[medium2-1] + Max3SATData[medium2] /2);
		System.out.println("Running Time Results");
		System.out.println("GSAT RESULTS: " + " Average Time = " + average1 + ", Medium Time = " + median1 
				+ ", Maximum Time = " + gsatData[gsatData.length-1] + ", Minimum Time = " + gsatData[0]);
		System.out.println("MAX3SAT RESULTS: " + " Average Time = " + average2 + ", Medium Time = " + median2
				+ ", Maximum Time = " + Max3SATData[Max3SATData.length-1] + ", Minimum Time = " + Max3SATData[0]);

		int averageClauseG = (sumClause1/100);
		int averageClauseM = (sumClause2/100);
		Arrays.sort(gClause);
		Arrays.sort(mClause);
		int clauseMedian1 = ((gClause[(gClause.length/2)-1] + gClause[gClause.length/2])/2);
		int clauseMedian2 = ((mClause[(mClause.length/2)-1] + mClause[mClause.length/2])/2);
		System.out.println("Solution Results");
		System.out.println("GSAT RESULTS: " + "Average Clauses Satisfied = " + averageClauseG + ", Medium Clauses Satisfied = " + clauseMedian1
				+ ", Maximum Amount of Clauses Satisfied = " + gClause[gClause.length-1] + ", Minimum Amount of Clauses Solved = " + gClause[0]);  
		
		System.out.println("MAX3SAT RESULTS: " + "Average Clauses Satisfied = " + averageClauseM + ", Medium Clauses Satisfied = " + clauseMedian2
				+ ", Maximum Amount of Clauses Satisfied = " + mClause[mClause.length-1] + ", Minimum Amount of Clauses Solved = " + mClause[0]); 
	
	}
	
	/**
	 * A method that runs the experiments for Knapsack for 100 rounds
	 * Generates 100 rounds of 100 items for the knapsack algorithms to compare the results
	 */
	
	public static void experimentKnap() {
		System.out.println("Testing Algorithms");
		System.out.println();
		double[] valueDouble = new double[100];
		double[] costDouble = new double[100];
		int[] valueInt = new int[100];
		int[] costInt = new int[100];
		int budget =0;
		long runtimeBefore = 0;
		long runtimeAfter =0;
		long totalTime = 0;
		Random random = new Random();
		//Generating a random Budget
		budget = random.nextInt(100);
		String[][] data = new String[102][10];
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[0].length; j++) {
				valueDouble = generateRandomKnapsackInstances();
				costDouble = generateRandomKnapsackInstances();
				valueInt = convertRandomDoublesToInts(valueDouble);
				costInt = convertRandomDoublesToInts(costDouble);
				//Generating a random Budget
				budget = random.nextInt(100);
				data[0][0] = "Round Number";
				data[1][j] = " ";
				data[i][0] = "Round" + (i-1) + "   ";
				data[0][1] = "  Budget   ";
				data[0][2] = "  0-1 Knapsack  ";
				data[0][3] = "  0-1 Runtime  ";
				data[0][4] = "  MinCost Knapsack   ";
				data[0][5] = "  MinCost  Runtime  ";
				data[0][6] = "  FPTAS ";
				data[0][7] = "  FPTAS Runtime  ";
				data[0][8] = "  Greedy ";
				data[0][9] = "  Greedy Runtime  ";
		
				data[i][1] = "  Budget = " + budget;
				//0-1 Knapsack
				 runtimeBefore = System.nanoTime();
				int knapsack01 = CS305Alg_Knapsack01.knapsack_01(valueInt.length, budget, valueInt, costInt);
				runtimeAfter = System.nanoTime();
				totalTime = ((runtimeAfter - runtimeBefore));
				if(knapsack01 == 0) {
					data[i][2] = "0"+knapsack01 +knapsack01 +knapsack01;
				}else{
					data[i][2] = "0" + knapsack01;
				}
				data[i][3] = "0" + totalTime;

				
				//MinCost
				runtimeBefore = System.nanoTime();
				Tables mincost = MinCost.mincost_alg(budget, valueInt, costInt);
				runtimeAfter = System.nanoTime();
				totalTime = ((runtimeAfter - runtimeBefore)/1000);
				int[][] mincost_return = mincost.getMinCost();
				boolean[][] take_return = mincost.getTake();
				String str = MinCost.constructMaxKnapsackSolution(mincost_return, take_return, valueInt, costInt, budget, valueInt.length);
				if(str.contentEquals("0")) {
					data[i][4] = "0" + str + str+ str;
				}else{
					data[i][4] = "0" + str;
				}
				data[i][5] = "0" + totalTime;
				
				
				//FPTAS
				double epsilon = 0.5;
				runtimeBefore = System.nanoTime();
				Tables fptas = FPTAS.FPTAS_Alg(valueInt.length, epsilon, valueInt, costInt, budget);
				runtimeAfter = System.nanoTime();
				totalTime = ((runtimeAfter - runtimeBefore)/1000);
				int[][] fptas_mincost = fptas.getMinCost();
				boolean[][] fptas_take = fptas.getTake();
				String fptasStr = MinCost.constructMaxKnapsackSolution(fptas_mincost, fptas_take, valueInt, costInt, budget, valueInt.length);
				if(fptasStr.contentEquals("0")) {
					data[i][6] = "0" + fptasStr + fptasStr + fptasStr;
				}else{
					data[i][6] = "0" + fptasStr;
				}
				data[i][7] = "0" + totalTime;
				
				
				//Greedy
//				runtimeBefore = System.nanoTime();
//				double greedy2approx = Greedy2Approx.modifiedGreedy(valueDouble.length, budget, valueDouble, costDouble);
//				runtimeAfter = System.nanoTime();
//				totalTime = ((runtimeAfter - runtimeBefore)/1000);
//				data[i][8] = "  Returned: " + greedy2approx +" ";
//				data[i][9] = "  [" + totalTime + " micro]   ";
				
				
			}
		}
		String str = "";
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[0].length; j++) {
				String dataAsString = data[i][j];
				str += dataAsString +"    ";
			}
			str += "\n";
		}
		System.out.println(str);
		String getData = "";
		String getData2 = "";
		String getData3 = "";
		int dataToInt = 0;
		int sumKnap = 0;
		int averageKnap = 0;
		int dataToInt2 = 0;
		int sumMinC = 0;
		int averageMin = 0;
		int dataToInt3 = 0;
		int sumFPT = 0;
		int averageFPT = 0;
		int[] knapData = new int[102];
		int[] minData = new int[102];
		int[] fptasData = new int[102];
		int[] knapRun = new int[102];
		int[] minRun = new int[102];
		int[] fptasRun = new int[102];
		int sum1 = 0;
		int sum2 = 0;
		int sum3 = 0;
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[0].length; j++) {
				if(i > 1 && j == 2) {
					getData = data[i][2];
					dataToInt = Integer.parseInt(getData);
					knapData[i] = dataToInt;
					sumKnap += dataToInt;
					
				}
				if(i > 1 && j == 4) {
					getData2 = data[i][4];
					dataToInt2 = Integer.parseInt(getData2);
					minData[i] = dataToInt2;
					sumMinC += dataToInt2;
				}
				if(i > 1 && j == 6) {
					getData3 = data[i][6];
					dataToInt3 = Integer.parseInt(getData3);
					fptasData[i] = dataToInt3;
					sumFPT += dataToInt3;
				}
				//getting runtimes
				if(i > 1 && j == 3) {
					String getTime = data[i][3];
					int getTimeToInt = Integer.parseInt(getTime);
					knapRun[i] = getTimeToInt;
					sum1 += getTimeToInt;
				}
				if(i > 1 && j == 5) {
					String getTime = data[i][5];
					int getTimeToInt = Integer.parseInt(getTime);
					minRun[i] = getTimeToInt;
					sum2 += getTimeToInt;
				}
				if(i > 1 && j == 7) {
					String getTime = data[i][7];
					int getTimeToInt = Integer.parseInt(getTime);
					fptasRun[i] = getTimeToInt;
					sum3 += getTimeToInt;
				}
			}
		}
		//Calculate average
		averageKnap = (sumKnap/100);
		averageMin = (sumMinC/100);
		averageFPT = (sumFPT/100);
		//Calculate Median
		Arrays.sort(knapData);
		Arrays.sort(minData);
		Arrays.sort(fptasData);
		int middle1 = knapData.length/2;
		int medianKnap = ((knapData[middle1-1] + knapData[middle1])/2);
		int middle2 = minData.length/2;
		int medianMin = ((minData[middle2-1] + minData[middle2])/2);
		int middle3 = fptasData.length/2;
		int medianfptas = ((fptasData[middle3-1] + fptasData[middle3])/2);
		
		//Max and Min
		int maxKnap = knapData[knapData.length-1];
		int minKnap = knapData[0];
		int maxMin = knapData[minData.length-1];
		int minMin = knapData[0];
		int maxfptas = knapData[fptasData.length-1];
		int minfptas = knapData[0];
		
		/*									*/
		int kRAverage = (sum1/100);
		int mRAverage = (sum2/100);
		int fRAverage = (sum3/100);
		Arrays.sort(knapRun);
		Arrays.sort(minRun);
		Arrays.sort(fptasRun);
		int maxKR = knapRun[knapRun.length-1];
		int minKR = knapRun[0];
		int maxMR = minRun[minRun.length-1];
		int minMR = minRun[0];
		int maxFR = fptasRun[fptasRun.length-1];
		int minFR = fptasRun[0];
		int middleRun1 = knapRun.length/2;
		int middleRun2 = minRun.length/2;
		int middleRun3 = fptasRun.length/2;
		int medianKR = ((knapRun[middleRun1-1] + knapRun[middleRun1])/2);
		int medianMR = ((minRun[middleRun2-1] + minRun[middleRun2])/2);
		int medianFR = ((fptasRun[middleRun3-1] + fptasRun[middleRun3])/2);
		
		
		
		System.out.println("SOLUTION RESULTS");
		System.out.println("KNAPSACK: Average value = " + averageKnap + ", Median value = " + medianKnap +
				", Maximum value = " + maxKnap + ", Minimum value = " + minKnap);
		
		System.out.println("MINCOST: Average value = " + averageMin + ", Median = " + medianMin +
				", Maximum value = " + maxMin +", Minimum value = " + minMin);
		
		System.out.println("FPTAS: Average = " + averageFPT +", fptas median is " + medianfptas +
				", Maximum value = " + maxfptas + ", Minimum value = " + minfptas);
	
		//Calculate Run Times
		System.out.println();
		System.out.println("RUNTIME RESULTS");
		System.out.println("KNAPSACK: Average value = " + kRAverage + ", Median value = " + medianKR +
				", Maximum value = " + maxKR + ", Minimum value = " + minKR);
		System.out.println("KNAPSACK: Average value = " + mRAverage + ", Median value = " + medianMR +
				", Maximum value = " + maxMR + ", Minimum value = " + minMR);
	
		System.out.println("KNAPSACK: Average value = " + fRAverage + ", Median value = " + medianFR +
				", Maximum value = " + maxFR + ", Minimum value = " + minFR);
	
	}
	/**
	 * Generates small instances of greedy to compare it's results
	 * Separate because won't work with large instances
	 */
	public static void greedy() {
		System.out.println("Testing Algorithms");
		System.out.println();
		double[] valueDouble = new double[100];
		double[] costDouble = new double[100];
		int[] valueInt = new int[100];
		int[] costInt = new int[100];
		int budget =0;
		long runtimeBefore = 0;
		long runtimeAfter =0;
		long totalTime = 0;
		Random random = new Random();
		//Generating a random Budget
		budget = random.nextInt(100);
		String[][] data = new String[20][4];
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[0].length; j++) {
				valueDouble = generateRandomKnapsackInstances();
				costDouble = generateRandomKnapsackInstances();
				valueInt = convertRandomDoublesToInts(valueDouble);
				costInt = convertRandomDoublesToInts(costDouble);
				//Generating a random Budget
				budget = random.nextInt(100);
				data[0][0] = "Round Number";
				data[1][j] = " ";
				data[i][0] = "Round" + (i-1) + "   ";
				data[0][1] = "  Budget   ";
				data[0][2] = "  Greedy Knapsack   ";
				data[0][3] = "  Greedy Knapsack Runtime  ";
				
				runtimeBefore = System.nanoTime();
				double greedy2approx = Greedy2Approx.modifiedGreedy(valueDouble.length, budget, valueDouble, costDouble);
				runtimeAfter = System.nanoTime();
				totalTime = ((runtimeAfter - runtimeBefore)/1000);
				data[i][1] = "  Budget = " + budget;
				data[i][2] = "  Returned: " + greedy2approx +" ";
				data[i][3] = "  [" + totalTime + " micro]   ";
			}
		}
		String str = "";
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[0].length; j++) {
				String dataAsString = data[i][j];
				str += dataAsString +"    ";
			}
			str += "\n";
		}
		System.out.println(str);
	}

	/**
	 * A method that generates 100 random items from 1 to 1000
	 * @return
	 */
	public static double[] generateRandomKnapsackInstances() {
		double[] randomInstances = new double[100];
		Random random = new Random();
		for(int x = 0; x < randomInstances.length; x++) {
			double randomValue = random.nextInt(1001); 
			if(randomValue != 0) {
				randomInstances[x] = randomValue;
			}else {
				x--;
			}
		}
	//	System.out.println("value array has " + randomInstances.length + " values in it: " + Arrays.toString(randomInstances));	
		return randomInstances;
	}
	
	/**
	 * A method that converts the randomly generated instances from doubles to ints
	 * @param randomDouble an array of doubles
	 * @return an array of converted from doubles to ints
	 */
	public static int[] convertRandomDoublesToInts(double[] randomDouble) {
		int[] convertedToInts = new int[randomDouble.length];
		for(int i = 0; i < randomDouble.length; i++) {
			convertedToInts[i] = (int)randomDouble[i];
		}
	//	System.out.println("converted ints are " + Arrays.toString(convertedToInts));
		return convertedToInts;
	}
	
	/**
	 * A method that generates 50 clauses with 3 random literals in each with a random name
	 * @return
	 */
	public static ArrayList<ArrayList<Literal>> generate3Sat() {
		Random random = new Random();
		Literal[] holdingLits = new Literal[5];
		int n = random.nextInt(5);
		int m = 50;
		
		Clause clause = new Clause();
	
		
		ArrayList<ArrayList<Literal>> outterclause = new ArrayList<ArrayList<Literal>>();
		for(int i = 0; i < holdingLits.length; i++) {
			Literal creatingLiterals = new Literal("X"+(i+1));
			holdingLits[i] = creatingLiterals;
		}
		for(int i = 0; i < m; i++) {
			ArrayList<Literal> innerclause = new ArrayList<Literal>();
			while(innerclause.size() != 3) {
				int randomLit = random.nextInt(5);
				int posOrneg = random.nextInt(2);
				if(posOrneg == 0) {
					clause.setLiteralToAClause(innerclause, holdingLits[randomLit].getNegatedLiteralConvertToLiteral());
				}else {
					clause.setLiteralToAClause(innerclause, holdingLits[randomLit]);
				}
			}
			
			clause.setFormula(outterclause, innerclause);
		}
	//	System.out.println("There are " + outterclause.size() + " clauses, and they are: " + "\n" 
	//			+  clause.getFormula());
		
		return outterclause;
	}
	
	/**
	 * A method that generates a random GSAT[] that represents a truth assignment for the GSAT algorithm
	 * Each GSAT is given a random assignment
	 * @return an array of GSAT that each has a boolean value
	 */
	public static GSAT[] generateRandomTruthAssignments() {
		Literal X1 = new Literal("X1");
		Literal X2 = new Literal("X2");
		Literal X3 = new Literal("X3");
		Literal X4 = new Literal("X4");
		Literal X5 = new Literal("X5");

		GSAT XONE = new GSAT(X1.getName());
		GSAT XTWO = new GSAT(X2.getName());
		GSAT XTHREE = new GSAT(X3.getName());
		GSAT XFOUR = new GSAT(X4.getName());
		GSAT XFIVE = new GSAT(X5.getName());
		GSAT[] truth_assignment = { XONE, XTWO, XTHREE, XFOUR, XFIVE };
		
		Random random = new Random();

		for (int i = 0; i < truth_assignment.length; i++) {
			 int ran = random.nextInt(2); // gets values 0 to 1
		//	 System.out.println("random number is " + ran);
			 if (ran == 0) {
			 truth_assignment[i].setValue(false);
			 } else {
			 truth_assignment[i].setValue(true);
			 }
		//	System.out.println("the random truth assignments are " + truth_assignment[i].getName() + " = "
			//		+ truth_assignment[i].getValue());
		}
		return truth_assignment;
	}

}
