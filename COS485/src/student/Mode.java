/**
 *  Program 6: Mode
 *  Description: This program finds the MODE of an unordered array 
 *  	of integers in AVERAGE CASE linear time.
 *  
 *  Author: Brian Campbell
 *  Date : 4/15/2020
 */
package student;

import java.util.HashMap;
import java.util.Map;

/*
  Find the MODE of an unordered array of integers in AVERAGE CASE linear time.
*/
public class Mode {
    public static int mode(int[] scores) {
        HashMap<Integer, Integer> tracker = new HashMap<>();
        Integer val;
        int modeVal = 1,
        		modeIndex = 0;
        
        
        for (int i = 0; i < scores.length; i++) {
        	// add the score to a hashmap for easy access
        	if((val = tracker.putIfAbsent(scores[i], 1)) != null) {
        		// if it's in the map
        		// increment the number of occurrences
        		tracker.put(scores[i], val + 1);
        		// if the new value is greater than the current mode
        		if (val + 1 > modeVal) {
        			// update the mode
        			modeVal = val + 1;
        			modeIndex = i;
        		}
        	}
        }
        
        return scores[modeIndex];
    }
}