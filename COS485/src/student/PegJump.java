/**
 *  Program 5: Peg Jump
 *  
 *  Description: This program accepts a PegJumpPuzzle and an ArrayList of Jump
 *  	objects. It uses backtracking to identify a solution which leaves one
 *   	peg in the same spot as the starting hole.
 *   
 *   Date: 31 March 2020
 *   Author: Brian Campbell
 */
package student;
/* 
 * This Student class is meant to contain your algorithm.
 * You should implement the static methods:
 *
 *   solvePegJump - finds a solution and the number of nodes examined in the search
 *                  it should fill in the jumpList argument with the jumps that form
 *                  your solution
 *
 * The input is a PegJumpPuzzle object, which has:
 *   a size, the number of holes numbered 0 .. size()-1
 *   the startHole that is initially empty
 *   an ArrayList of allowed jumps, which are triples (from, over, dest)
 *   a jump takes the peg 'from' over the peg in 'over' (removing it) and into 'dest'
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import pegJump.*;

public class PegJump {
	
	private static int jumpCnt;
	private static boolean[] pegs;
	
	public static double solvePegJump(PegJumpPuzzle puzzle, ArrayList<Jump> jumpList) 
	{	
	    // initialize the puzzle
		// make an array to keep tracks of where the pegs are
		// and put pegs in all holes except the starting hole
		pegs = new boolean[puzzle.numHoles()];  // hole numbers start at 0
		for (int i = 0; i < puzzle.numHoles(); i++)  
			pegs[i] = true;                      // fill all holes
    	pegs[puzzle.getStartHole()] = false;     // clear starting hole
    	
    	// find the optimal solution
    	backtrack(puzzle, jumpList, puzzle.numHoles()-1);
    	// correct the order of jumps
    	Collections.reverse(jumpList);
    	// backtrack for best outcome
		return jumpCnt; 
	}	
	
	private static int backtrack(PegJumpPuzzle puzzle, ArrayList<Jump> jumpList, int numPegs) {
		
		if (numPegs > 1) { // not the last peg
			// start doing jumps
			Iterator<Jump> jitr = puzzle.jumpIterator();
			while (jitr.hasNext()) { 
				Jump j = jitr.next();
				int from = j.getFrom();
				int over = j.getOver();
				int dest = j.getDest();
					if (pegs[from] && pegs[over] && !pegs[dest]) {
					// found a valid jump
					pegs[from] = false;    // do the jump
					pegs[over] = false;
					pegs[dest] = true;
					jumpCnt++;
					if (backtrack(puzzle, jumpList, numPegs-1) == 0) {
						// jump was part of optimal solution
						jumpList.add(j);
						return 0;
					} else {
						// undo the jump
						pegs[from] = true;
						pegs[over] = true;
						pegs[dest] = false;
					}
				}
			}
		} else if (pegs[puzzle.getStartHole()] == true){ // last peg
			return 0;
		}
		
		return -1;
	}
}