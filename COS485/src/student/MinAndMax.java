package student;

import java.util.Arrays;

/* 
 * This class is meant to contain your algorithm.
 * You should implement the static method: minAndMax()
 * The input is:
 *   an array of Value objects
 *   
 * These objects are comparable using the compareTo() method.
 * Your goal is to find both the minimum and maximum values using at most 1.5 N comparisons.
 * 
 * You should return an array of two Value objects the minimum and maximum values.
 */

import minAndMax.Value;

public class MinAndMax {
	
	/**
	 * Separates input values into min and max prospects
	 * for helper methods sort out.
	 * @param values
	 * @return a Value array of length 2, (min, max)
	 */
	public static Value[] minAndMax(Value[] values) {
		int length = values.length,
				i = 0,
				j = 0;
		Value[] minProspects = null;
		Value[] maxProspects = null;
		
		// if the list has an odd amount, add the first 
		// value to both prospect lists 
		if (length % 2 == 1) {
			minProspects = new Value[(length + 1) / 2];
			maxProspects = new Value[(length + 1) / 2];
			
			minProspects[j] = values[0];
			maxProspects[j] = values[0];
			i++;
			j++;
		} else {
			minProspects = new Value[length/2];
			maxProspects = new Value[length/2];
		}
		
		// compare neighbors, the smaller is a min prospect
		// the larger is a max prospect
		for (; i < (length - 2); i += 2, j++) {
			if (values[i].compareTo(values[i+1]) < 0) {
				minProspects[j] = values[i];
				maxProspects[j] = values[i+1];
			} else {
				minProspects[j] = values[i+1];
				maxProspects[j] = values[i];
			}
		}
		
		// let the helper classes sort out min and max
		return  new Value[] {min(minProspects), max(maxProspects)}; // make a 2 element array v1 is min and v0 is max
	}				
	
	// Recursive method to determine minimum value of a list
	private static Value min(Value[] values) {
		int length = values.length;
		
		 // hard to beat values replace nulls
		Value winner = new Value(999999999);
		Value contender = new Value(999999999);
		
		// a list of one, it must be the winner
		if (length == 1) {
			if (values[0] != null) {
				winner = values[0];
			} 
		} else if (length == 2) { 
			
			// a list of 2, eliminate nulls and compare if needed
			if (values[0] == null) {
				if (values[1] != null) {
					winner = values[1];
				}
			} else if(values[1] == null) {
				winner = values[0];
			} else if (values[0].compareTo(values[1]) < 0) {
				winner = values[0];
			} else {
				winner = values[1];
			}
		} else {
			
			// a list of more, recurse and compare
			winner = min(Arrays.copyOfRange(values, 0, length / 2));
			contender = min(Arrays.copyOfRange(values, length / 2, length));
			if (winner.compareTo(contender) > 0) {
				winner = contender;
			}
		}
			
		return winner;
	}
	
	// Recursive method to determine maximum value of a list
	private static Value max(Value[] values) {
		int length = values.length;
		
		 // hard to beat values replace nulls
		Value winner = new Value(-999999999);
		Value contender = new Value(-999999999);
		
		// a list of one, it must be the winner
		if (length == 1) {
			if (values[0] != null) {
				winner = values[0];
			} 
		} else if (length == 2) {
			
			// a list of 2, eliminate nulls and compare if needed
			if (values[0] == null) {
				if (values[1] != null) {
					winner = values[1];
				}
			} else if(values[1] == null) {
				winner = values[0];
			} else if (values[0].compareTo(values[1]) > 0) {
				winner = values[0];
			} else {
				winner = values[1];
			}
		} else {
			
			// a list of more, recurse and compare
			winner = max(Arrays.copyOfRange(values, 0, length / 2));
			contender = max(Arrays.copyOfRange(values, length / 2, length));
			
			if (winner.compareTo(contender) < 0) {
				winner = contender;
			}
		}
			
		return winner;
	}
}