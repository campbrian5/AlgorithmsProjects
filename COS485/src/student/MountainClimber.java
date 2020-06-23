/**
 * 	Program 4: Mountain Climber
 * 	
 * 	Description: This program finds the shortest cost path up a mountain given a 2D array 
 *          representation of the mountain terrain, the start and goal points, and the 
 *          number of rows and columns of the terrain.
 * 
 *  Class: COS 485 - Algorithms - Professor Boothe
 *  Date: 27 March 2020
 *  Author: Brian Campbell
 */

package student;

/* 
 * This class is meant to contain your algorithm.
 * You should implement the static method: findRoute
 * The input is:
 *   the number of rows and columns of
 *   a 2D array of integer heights
 *   a staring Point where row = start.y and col = start.x
 *   a goal Point
 * 
 * You should return an ArrayList of Points(x = col, y = row) for the least cost path
 * from the start to the goal.
 */
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class MountainClimber
{
    public static ArrayList<Point> findRoute(int rows, int cols, int[][] grid, 
                                             Point start, Point goal) {
    	PriorityQueue<Contender> contenders = new PriorityQueue<>();
    	Contender[][] visited = new Contender[rows][cols];
    	Contender curCont;
        ArrayList<Point> path = new ArrayList<Point>();
        Point curPoint = start;
        double cost = 0.0;
        
        // visit the starting point
        visited[curPoint.y][curPoint.x] = curCont = new Contender(curPoint);
        
        do {
        	// add contenders to heap
            if (curPoint.x > 0) {
        		// add row-1 if it hasn't been visited yet
            	if(visited[curPoint.y][curPoint.x - 1] == null) {
            		contenders.add(new Contender(curPoint, new Point(curPoint.x - 1, curPoint.y), 
            				Math.abs(grid[curPoint.y][curPoint.x - 1] - 
            						grid[curPoint.y][curPoint.x]), curCont.cost));
            	}
        		if (curPoint.y > 0) {
        			// add col-1 if it hasn't been visited yet
        			if(visited[curPoint.y - 1][curPoint.x] == null) {
        				contenders.add(new Contender(curPoint, new Point(curPoint.x, curPoint.y - 1), 
        						Math.abs(grid[curPoint.y - 1][curPoint.x] - 
        								grid[curPoint.y][curPoint.x]), curCont.cost));
        			}
        			// add row-1, col-1 if it hasn't been visited yet
        			if(visited[curPoint.y - 1][curPoint.x - 1] == null) {
        				contenders.add(new Contender(curPoint, new Point(curPoint.x - 1, curPoint.y - 1), 
        						Math.abs(grid[curPoint.y - 1][curPoint.x - 1] - 
        								grid[curPoint.y][curPoint.x]), curCont.cost));
        			}
        		}
        		if (curPoint.y < rows - 1) {
        			// add col+1 if it hasn't been visited yet
        			if(visited[curPoint.y + 1][curPoint.x] == null) {
        				contenders.add(new Contender(curPoint, new Point(curPoint.x, curPoint.y + 1), 
        						Math.abs(grid[curPoint.y + 1][curPoint.x] - 
        								grid[curPoint.y][curPoint.x]), curCont.cost));
        			}
    				// add row-1, col+1 if it hasn't been visited yet
        			if(visited[curPoint.y + 1][curPoint.x - 1] == null) {
        				contenders.add(new Contender(curPoint, new Point(curPoint.x - 1, curPoint.y + 1), 
        						Math.abs(grid[curPoint.y + 1][curPoint.x - 1] - 
        								grid[curPoint.y][curPoint.x]), curCont.cost));
        			}
        		}
            } else {
        		if (curPoint.y > 0) {
    				// add col-1 if it hasn't been visited yet
        			if(visited[curPoint.y - 1][curPoint.x] == null) {
        				contenders.add(new Contender(curPoint, new Point(curPoint.x, curPoint.y - 1), 
        						Math.abs(grid[curPoint.y - 1][curPoint.x] - 
        								grid[curPoint.y][curPoint.x]), curCont.cost));
        			}
        		}
        		if (curPoint.y < rows - 1) {
    				// add col+1 if it hasn't been visited yet
        			if(visited[curPoint.y + 1][curPoint.x] == null) {
        				contenders.add(new Contender(curPoint, new Point(curPoint.x, curPoint.y + 1), 
        						Math.abs(grid[curPoint.y + 1][curPoint.x] - 
        								grid[curPoint.y][curPoint.x]), curCont.cost));
        			}
        		}
            }
        		
        	if (curPoint.x < cols - 1) {
        		// add row+1 if it hasn't been visited yet
        		if(visited[curPoint.y][curPoint.x + 1] == null) {
        			contenders.add(new Contender(curPoint, new Point(curPoint.x + 1, curPoint.y), 
        					Math.abs(grid[curPoint.y][curPoint.x + 1] - 
        							grid[curPoint.y][curPoint.x]), curCont.cost));
        		}
        		if (curPoint.y  > 0) {
    				// add row+1, col-1 if it hasn't been visited yet
        			if(visited[curPoint.y - 1][curPoint.x + 1] == null) {
        				contenders.add(new Contender(curPoint, new Point(curPoint.x + 1, curPoint.y - 1), 
        						Math.abs(grid[curPoint.y - 1][curPoint.x + 1] - 
        								grid[curPoint.y][curPoint.x]), curCont.cost));
        			}
        		}
        		if (curPoint.y  < rows - 1) {
					// add row+1, col+1 if it hasn't been visited yet
        			if(visited[curPoint.y + 1][curPoint.x + 1] == null) {
        				contenders.add(new Contender(curPoint, new Point(curPoint.x + 1, curPoint.y + 1), 
        						Math.abs(grid[curPoint.y + 1][curPoint.x + 1] - 
        								grid[curPoint.y][curPoint.x]), curCont.cost));
        			}
        		}
        	}
        	
        	// eliminate contenders which have been visited
        	do {
        		curCont = contenders.poll();
        	} while (visited[curCont.cur.y][curCont.cur.x] != null);
        	
        	// visit next best contender
        	visited[curCont.cur.y][curCont.cur.x] = curCont;
        	curPoint = curCont.cur;
        	
        // exit when the goal is found
        } while (!curPoint.equals(goal));
		
        // current contender is the goal, so its cost is the total cost
        cost = curCont.cost;
        
        // work backwards from goal to find path
        do {
        	path.add(curCont.cur);
        	curCont = visited[curCont.prev.y][curCont.prev.x];
        } while (curCont.prev != null);
        
        // add start point, and reverse the order
        path.add(start);
        Collections.reverse(path);
        
        System.out.println("Cost = "+cost);
        return path; 
    }	

    
    // Contender object implements Comparable
    // 		a Contender has a previous point, a current point, and the total cost from 
    // 		start to the current point
    public static class Contender implements Comparable<Contender> {
    	// Define fields
    	Point prev;
    	Point cur;
    	Double cost;
    	
    	// Constructor for start point, previous point is null
    	public Contender(Point cur) {
    		this.cur = cur;
    		cost = 0.0;
    	}
    	
    	// Constructor calculates cost based on direction, change in height, and previous cost
    	public Contender(Point prev, Point cur, int deltaHeight, double prevCost) {
    		this.prev = prev;
    		this.cur = cur;
    		
    		// calculate cost using previous cost
    		if (prev.x == cur.x || prev.y == cur.y) { // moving horiz or vert
    			this.cost = prevCost + 1.0 + Math.pow(deltaHeight, 3.0);
    		} else { // moving diagonally
    			this.cost = prevCost + Math.sqrt(2.0) + (Math.pow(deltaHeight, 3.0) / 2.0);
    		}
    	}
    	
    	// compareTo compares cost of contenders
    	@Override
    	public int compareTo(Contender other) {
    		return cost.compareTo(other.cost);
    	}
    }

}