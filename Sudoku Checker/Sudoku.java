import acm.program.*;

public class Sudoku extends ConsoleProgram {
	private static final int N = 9;
	public void run() {
/*		int[][] array = new int[n][n];
		for (int i=0; i<array.length; i++) {
			for(int j=0; j<array.length; j++) {
				array[i][j] = readInt("Enter the value of "
						+i+"X"+j+" element: ");
			}
		} */

		int [][] array = {
				{3,9,2,4,6,5,8,1,7},
				{7,4,1,8,9,3,6,2,5},
				{6,8,5,2,7,1,4,3,9},
				{2,5,4,1,3,8,7,9,6},
				{8,3,9,6,2,7,1,5,4},
				{1,7,6,9,5,4,2,8,3},
				{9,6,7,5,8,2,3,4,1},
				{4,2,3,7,1,9,5,6,8},
				{5,1,8,3,4,6,9,7}
		};
		
		if(checkSudokuSolution(array)) {
			println("Is a valid solution.");
		}
		else {
			println("Is a wrong solution");
		}
							
}
	
	private boolean checkSudokuSolution(int[][] array) {
		
		//9X9 rows
		for (int i=0; i<array.length; i++) {
			for(int j=0; j<array.length; j++) {
				for(int k=j+1; k<array.length; k++) {
					if (array[i][j] == array[i][k]) {
//						println("row "+i+","+j+","+k);
						return false;
					}
				}
			}
		} 
		
		//9X9 columns
		for (int j=0; j<array.length; j++) {
			for(int i=0; i<array.length; i++) {
				for (int k=i+1; k<array.length; k++) {
					if (array[i][j] == array[k][j]) {
//						println("columns "+i+","+j+","+k);
						return false;
					}
				}
			}
		}
		
		//3X3 matrices
		for (int loop=0; loop<array.length; loop++) {
			if(!check3X3(array, (loop/3)*3, (loop%3)*3)) {
//				println(loop/3+","+(loop%3)*3);
				return false;
			}
		}
	
		return true;
	}
	
	private boolean check3X3(int[][] array, int i, int j) {
		int[] tempArray = new int[array.length];
		int t=0;
		for (int c=i; c<i+(array.length)/3; c++) {
			for(int r=j; r<j+(array.length)/3; r++) {
				tempArray[t++] = array[c][r];
			}
		}
		
		for(int l=0; l<tempArray.length-1; l++) {
			for(int k=l+1; k<tempArray.length; k++) {
				if (tempArray[l] == tempArray[k]) {
					return false;
				}
			}
		}
		return true;
	}
}
