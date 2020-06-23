/*
 * Program 3: Test Formatting
 * Author: Brian Campbell
 */

package student;
/* 
 * This class is meant to contain your algorithm.
 * You should implement the static method:
 *   formatParagraph - which formats one paragraph
 */
import java.util.ArrayList;

public class TextFormatting {
	// input:
	//   words: an array of the words in the paragraph
	//   width: the desired formatting width
	//   result: an empty ArrayList into which you should add the resulting paragraph
	//
	// returns:
    //   the formatted paragraph as an ArrayList of Strings, 1 string for each
	//     formatted line of the paragraph
	//   the return value is the total calculated badness value for the paragraph
	public static int formatParagraph(String[] words, int width, ArrayList<String> result) {
		int[][] memo = new int[words.length][];
		int[][] path = new int[2][words.length];
		int i = 0,
				p = 0,
				curLength = 0;
		
		// memoize (if a line can't fit, enter 999999999)
		for (int j=0; j<words.length; j++) {
			memo[j] = new int[words.length + 1];
			curLength = words[j].length();
			memo[j][j] = (int) Math.pow(width-curLength, 3);
			for(int k=j+1; k<words.length; k++) {
				curLength += 1 + words[k].length();
				if (width >= curLength) {
					memo[j][k] = (int) Math.pow(width-curLength, 3);
				} else {
					memo[j][k] = 999999999;
				}
			}
		}
		
		// build best path with matrix
		for (int j = words.length - 1; j >= 0; j--) {
			int k = words.length - 1;
			if(memo[j][k] == 999999999) { // doesn't fit, split
				while(memo[j][k-1] == 999999999) {
					k--;
				}
				int min = 999999999;
				int minIndex = k;
				int temp;
				for (int m = k; m > j; m--) {
					temp = (memo[j][m-1]) + (path[1][m]); 
					if (temp <= min) {
						min = temp;
						minIndex = m;
					}
				}
				path[1][j] = min;
				path[0][j] = minIndex;
			}else{ // it fits
				path[1][j] = memo[j][k];
				path[0][j] = k+1;
			}
		}
		
		// use path to build result
		while (p < words.length) {       // this loop adds another line to result
			StringBuilder buf = new StringBuilder();
			p = path[0][p];
			buf.append(words[i++]);
			// add more words until full
			while (i < p) {
				buf.append(" "+words[i++]);      // space between words
			}
			result.add(buf.toString());   // add this line to the paragraph
		}
		
		return path[1][0];
	}
	

	// extra credit paragraph formatting that has no penalty for the last line of the paragraph
	public static int xc_formatParagraph(String[] words, int width, ArrayList<String> result) {
		return -1;		// not implemented
	}

}



