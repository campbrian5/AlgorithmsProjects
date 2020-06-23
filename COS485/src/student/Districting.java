/**
 * 	Program: Districting
 * 	Description: Finds a combination of contiguous districts whos populations are within 10%
 *      using randomized iterative improvement, and depth first search to check continuity.
 * 
 * 	Authors: Dejan Tisma and Brian Campbell
 * 	Date: 2/27/2020
 */
package student;

import districting.DistrictingTester;

import java.util.concurrent.ThreadLocalRandom;

/* 
 * This class is meant to contain your algorithm.
 * You should implement the static method: makeDistricts
 * The input is:
 *   the number of rows and columns of
 *   a 2D array of integer populations in each array cell in the populations 
 *   the number of districts desired
 *   
 * Your task is to break it up into districts that are:
 *   contiguous - (all cells in a region are connected by shared edges)
 *   close to equal in population
 *      
 * You should return a 2D array of the district numbers for each cell. 
 * District number should start at 1.
 * 
 * Note: this assignment can be animated. If you would like to watch your algorithm
 * as it works, you can inserts calls to:
 *          DistrictingTester.show(districts);
 * at any points in your program where it would be useful to see the current state of
 * the districts array. Read more detailed instructions using the [About] button in the
 * scaffold.
 * 
 * The starting code contains example animations calls.
 */
public class Districting {
	public static int[][] makeDistricts(int rows, int cols, int[][] populations, int numDistricts) {
        int[][] districts = new int[rows][cols]; // for storing the district numbers
        int[] districtPops = new int[numDistricts];
        boolean updated;
        int totalPopulation = 0, lowPop = 999999, lowDistrict = 0, highPop = 0, curDistrict = 0;
        
        // calculate total population
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                totalPopulation += populations[r][c];
            }
        }
        // calculate average population
        int tpd = totalPopulation / numDistricts;

        // initial districting fills column by column, and row by row
        // incrementing the district if the population meets or exceeds the avg
        int d = 1;
        int pop = 0;
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                if ((c % 2) == 0) {
                    pop += populations[r][c];
                    fill(districts, r, c, r + 1, c + 1, d);
                } else {
                    pop += populations[rows-1-r][c];
                    fill(districts, rows-1-r, c, rows-r, c + 1, d);
                }
                if (pop >= tpd) {
                    if (pop > highPop) { // find initial highest district
                        highPop = pop;
                    }
                    districtPops[d - 1] = pop;
                    pop = 0;
                    d++;
                }
            }
        }
        // last district is always lowest
        lowPop = pop;
        lowDistrict = d;
        districtPops[d - 1] = pop;

        // iterative improvement, finding < 10% difference
        while (((double) lowPop / (double) highPop) < .9) {
            updated = false;
            for (int c = 0; c < cols; c++) {
                for (int r = 0; r < rows; r++) {
                    if (districts[r][c] == lowDistrict) {
                    	// randomly choose a neighboring cell to steal
                        int randomNum = ThreadLocalRandom.current().nextInt(1,5); //1 top, 2 right, 3 bottom, 4 left
                        
                        if(randomNum == 1 && !updated) {
                            if (r > 0 && districts[r - 1][c] != lowDistrict) {
                            	// steal the cell
                                curDistrict = districts[r - 1][c];
                                districts[r - 1][c] = lowDistrict;
                                // check for continuity
                                if (!isConnected(districts, rows, cols, curDistrict)) { // reset is not contiguous
                                    districts[r - 1][c] = curDistrict;
                                } else {
                                    // update populations
                                    lowPop = districtPops[lowDistrict - 1] += populations[r - 1][c];
                                    districtPops[curDistrict - 1] -= populations[r - 1][c];
                                    // if new low/high district, break loops
                                    for (int d2 = 0; d2 < numDistricts; d2++) {
                                        if (districtPops[d2] < lowPop) {
                                            lowPop = districtPops[d2];
                                            lowDistrict = d2 + 1;
                                            updated = true;
                                        } else if (districtPops[d2] > highPop) {
                                            highPop = districtPops[d2];
                                            updated = true;
                                        }
                                    }
                                }
                            }
                        }

                        if(randomNum == 2 && !updated) {
                            if (r < rows - 1 && districts[r + 1][c] != lowDistrict) {
                                curDistrict = districts[r + 1][c];
                                districts[r + 1][c] = lowDistrict;
                                if (!isConnected(districts, rows, cols, curDistrict)) {
                                    districts[r + 1][c] = curDistrict;
                                } else {
                                    // update populations
                                    lowPop = districtPops[lowDistrict - 1] += populations[r + 1][c];
                                    districtPops[curDistrict - 1] -= populations[r + 1][c];
                                    // if new low district, break loops
                                    for (int d2 = 0; d2 < numDistricts; d2++) {
                                        if (districtPops[d2] < lowPop) {
                                            lowPop = districtPops[d2];
                                            lowDistrict = d2 + 1;
                                            updated = true;
                                        } else if (districtPops[d2] > highPop) {
                                            highPop = districtPops[d2];
                                            updated = true;
                                        }
                                    }
                                }
                            }
                        }

                        if(randomNum == 3 && !updated) {
                            if (!updated && c > 0 && districts[r][c - 1] != lowDistrict) {
                                curDistrict = districts[r][c - 1];
                                districts[r][c - 1] = lowDistrict;
                                if (!isConnected(districts, rows, cols, curDistrict)) {
                                    districts[r][c - 1] = curDistrict;
                                } else {
                                    lowPop = districtPops[lowDistrict - 1] += populations[r][c - 1];
                                    districtPops[curDistrict - 1] -= populations[r][c - 1];
                                    for (int d2 = 0; d2 < numDistricts; d2++) {
                                        if (districtPops[d2] < lowPop) {
                                            lowPop = districtPops[d2];
                                            lowDistrict = d2 + 1;
                                            updated = true;
                                        } else if (districtPops[d2] > highPop) {
                                            highPop = districtPops[d2];
                                            updated = true;
                                        }
                                    }
                                }
                            }
                        }
                        if(randomNum == 4 && !updated) {
                            if (!updated && c < cols - 1 && districts[r][c + 1] != lowDistrict) {
                                curDistrict = districts[r][c + 1];
                                districts[r][c + 1] = lowDistrict;
                                if (!isConnected(districts, rows, cols, curDistrict)) {
                                    districts[r][c + 1] = curDistrict;
                                } else {
                                    lowPop = districtPops[lowDistrict - 1] += populations[r][c + 1];
                                    districtPops[curDistrict - 1] -= populations[r][c + 1];
                                    for (int d2 = 0; d2 < numDistricts; d2++) {
                                        if (districtPops[d2] < lowPop) {
                                            lowPop = districtPops[d2];
                                            lowDistrict = d2 + 1;
                                            updated = true;
                                        } else if (districtPops[d2] > highPop) {
                                            highPop = districtPops[d2];
                                            updated = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (updated)
                        break;
                }
                if (updated)
                    break;
            }
        }

        return districts;
    }

	// fill in the array region [r1, r2) x [c1, c2) with value d
	public static void fill(int[][] a, int r1, int c1, int r2, int c2, int d) {
		for (int r = r1; r < r2; r++)
			for (int c = c1; c < c2; c++)
				a[r][c] = d;
	}

	// check district d for continuity
	public static Boolean isConnected(int[][] districts, int rows, int cols, int d) {
		boolean[][] visited = new boolean[rows][cols];
		Boolean connected = true;
		boolean isFirst = true;
		int numCells = 0, numConnected = 0;

		// find a starting point
		for (int c = 0; c < cols; c++) {
			for (int r = 0; r < rows; r++) {
				if (districts[r][c] == d) {
					if (isFirst) { // Depth first search for continuity check
						numConnected = checkNeighbors(districts, visited, rows, cols, r, c, d);
						isFirst = false;
					}
					numCells++; // increment total number of cells for check
				}

			}
		}

		// compare connected vs actual number of cells in district
		if (numConnected != numCells)
			connected = false;

		return connected;
	}

	// Depth first search for number of connected cells
	public static int checkNeighbors(int[][] districts, boolean[][] visited, int rows, int cols, int startRow,
			int startCol, int d) {
		int numVisited = 1;

		visited[startRow][startCol] = true; // visit this cell

		if (startRow > 0 && !visited[startRow - 1][startCol] && districts[startRow - 1][startCol] == d) { // check above
			numVisited += checkNeighbors(districts, visited, rows, cols, startRow - 1, startCol, d);
		}
		if (startCol > 0 && !visited[startRow][startCol - 1] && districts[startRow][startCol - 1] == d) { // check left
			numVisited += checkNeighbors(districts, visited, rows, cols, startRow, startCol - 1, d);
		}
		if (startRow < rows - 1 && !visited[startRow + 1][startCol] && districts[startRow + 1][startCol] == d) { // check
																													// below
			numVisited += checkNeighbors(districts, visited, rows, cols, startRow + 1, startCol, d);
		}
		if (startCol < cols - 1 && !visited[startRow][startCol + 1] && districts[startRow][startCol + 1] == d) { // check
																													// right
			numVisited += checkNeighbors(districts, visited, rows, cols, startRow, startCol + 1, d);
		}

		return numVisited;
	}
}