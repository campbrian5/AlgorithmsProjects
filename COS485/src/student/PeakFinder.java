package student;


/* 
 * This class is meant to contain your algorithm.
 * You should implement the static method: findPeak
 * The input is:
 *   a ReadOnlyArrayList<Integer> that only provides .size() and .get(index)
 *   
 * This array is like a mountain profile. The first part of the array rises with every
 * value to a peak value, and then the values decrease with every value until the end.
 * 
 * Your task is to efficiently find the index of the peak value while looking at as few values
 * as possible. 
 * 
 * You should return the index of the peak value.
 */

import peakFinder.ReadOnlyArrayList;

public class PeakFinder {
	public static int findPeak(ReadOnlyArrayList<Integer> values) {
		int index = values.size() / 2;
		int peak = 0,
				low = 0, 
				high = values.size();
		
		while (index > low && index < high) {
			if (values.get(index) < values.get(index - 1)) {
				peak = index - 1;
				high = index;
			} else {
				peak = index;
				low = index;
			}
			index = (high + low) / 2;
		}
			
		return peak; 
	}	
}