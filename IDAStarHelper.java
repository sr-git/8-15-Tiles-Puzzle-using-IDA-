/*
 * Author: Surendra Chekuri
 * File name: IDAStarHelper.java
 * Dependencies: Node.java,CommonConstants.java
 * Description: Helper methods to get blank position, moving the tiles, get valid moves.
 * */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class IDAStarHelper {
	public static ArrayList<String> getMoves(int position) {
		ArrayList<String> invalidMove = new ArrayList<String>();
		ArrayList<String> allMoves = new ArrayList<String>();
		ArrayList<String> validMoves = new ArrayList<String>();
		allMoves.add("D");
		allMoves.add("U");
		allMoves.add("R");
		allMoves.add("L");
		if (CommonConstants.PUZZLE_TYPE == 9) {
			if (position < 3)
				invalidMove.add("D");
			if (position > 5)
				invalidMove.add("U");
			if (position % 3 == 0)
				invalidMove.add("R");
			if (position % 3 == 2)
				invalidMove.add("L");

		} else if (CommonConstants.PUZZLE_TYPE == 16) {
			if (position < 4)
				invalidMove.add("D");
			if (position > 11)
				invalidMove.add("U");
			if (position % 4 == 0)
				invalidMove.add("R");
			if (position % 4 == 3)
				invalidMove.add("L");
		}
		for (String move : allMoves) {
			if (!invalidMove.contains(move))
				validMoves.add(move);
		}
		return validMoves;
	}

	public static int blankPosition(int[] state) {
		int position = 0;
		if (state == null)
			return position;
		for (int index = 0; index < state.length; index++) {
			if (state[index] == CommonConstants.BLANK) {
				position = index;
				break;
			}
		}
		return position;
	}

	public static Node moveTiles(String move, Node node) {
		Node n = node;
		int position = IDAStarHelper.blankPosition(n.getPuzzle());
		int swapIndex = 0;
		int[] newPuzzle = n.getPuzzle();
		if (CommonConstants.PUZZLE_TYPE == 9) {
			if (move == "U")
				swapIndex = position + 3;
			if (move == "D")
				swapIndex = position - 3;
			if (move == "L")
				swapIndex = position + 1;
			if (move == "R")
				swapIndex = position - 1;
		} else {
			if (move == "U")
				swapIndex = position + 4;
			if (move == "D")
				swapIndex = position - 4;
			if (move == "L")
				swapIndex = position + 1;
			if (move == "R")
				swapIndex = position - 1;

		}
		int temp = newPuzzle[swapIndex];
		newPuzzle[swapIndex] = newPuzzle[position];
		newPuzzle[position] = temp;
		Node newNode = new Node(n.getCost() + 1, n.getHeuristicValue(),
				n.getfValue(), newPuzzle);
		return newNode;

	}

	public static Node backTrackMove(Node node, String move) {
		int blankPosition = IDAStarHelper.blankPosition(node.getPuzzle());
		int swap = 0;
		if (CommonConstants.PUZZLE_TYPE == 9) {
			if (move == "L")
				swap = blankPosition - 1;
			if (move == "R")
				swap = blankPosition + 1;
			if (move == "U")
				swap = blankPosition - 3;
			if (move == "D")
				swap = blankPosition + 3;
		} else {
			if (move == "L")
				swap = blankPosition - 1;
			if (move == "R")
				swap = blankPosition + 1;
			if (move == "U")
				swap = blankPosition - 4;
			if (move == "D")
				swap = blankPosition + 4;
		}
		int temp = node.puzzle[swap];
		node.puzzle[swap] = node.puzzle[blankPosition];
		node.puzzle[blankPosition] = temp;
		return node;
	}

	public static int findHeuristic(int[] puzzle) {
		if (puzzle == null)
			return 1;
		/* ManhattanDistance as heuristic */
		int manhattanDistance = 0;
		if (CommonConstants.PUZZLE_TYPE == 16) {
			for (int i = 0; i < puzzle.length; i++) {
				int tile = puzzle[i];
				if (tile != CommonConstants.BLANK) {
					int goalPositionRow = (tile - 1) / 4;
					int curPositionRow = i / 4;
					int goalPositionCol = (tile - 1) % 4;
					int curPositionCol = i % 4;
					manhattanDistance += Math.abs(goalPositionRow
							- curPositionRow)
							+ Math.abs(goalPositionCol - curPositionCol);
				}
			}
		} else {
			for (int i = 0; i < puzzle.length; i++) {
				int tile = puzzle[i];
				if (tile != CommonConstants.BLANK) {
					int goalPositionRow = (tile - 1) / 3;
					int curPositionRow = i / 3;
					int goalPositionCol = (tile - 1) % 3;
					int curPositionCol = i % 3;
					manhattanDistance += Math.abs(goalPositionRow
							- curPositionRow)
							+ Math.abs(goalPositionCol - curPositionCol);
				}
			}
		}
		return manhattanDistance*2;

	}

	public static String convertToString(int[] puzzle) {
		String puzzleString = "";
		for (int i : puzzle) {
			puzzleString = puzzleString + i + ",";
		}
		return puzzleString;
	}

	public static void printArray(Set<String> pathVisited) {
       System.out.println("Total no of moves:"+pathVisited.size());
		for (String path : pathVisited) {
			String[] puzzle = path.split(",");
			if (CommonConstants.PUZZLE_TYPE == 9) {
				System.out.println(puzzle[0] + " " + puzzle[1] + " "
						+ puzzle[2]);
				System.out.println(puzzle[3] + " " + puzzle[4] + " "
						+ puzzle[5]);
				System.out.println(puzzle[6] + " " + puzzle[7] + " "
						+ puzzle[8]);
				System.out.println();
				System.out.println("------------------");
			} else {
				System.out.println(puzzle[0] + "\t" + puzzle[1] + "\t"
						+ puzzle[2] + "\t" + puzzle[3]);
				System.out.println(puzzle[4] + "\t" + puzzle[5] + "\t"
						+ puzzle[6] + "\t" + puzzle[7]);
				System.out.println(puzzle[8] + "\t" + puzzle[9] + "\t"
						+ puzzle[10] + "\t" + puzzle[11]);
				System.out.println(puzzle[12] + "\t" + puzzle[13] + "\t"
						+ puzzle[14] + "\t" + puzzle[15]);
				System.out.println();
				System.out.println("----------------------");
			}
		}
	}

	public static void verifyUniqueArray(int[] puzzle, int actualSize) {
		ArrayList validNumbers=new ArrayList();
		if(actualSize==9){
			
			validNumbers.add(1);validNumbers.add(2);validNumbers.add(3);validNumbers.add(4);validNumbers.add(5);
			validNumbers.add(5);validNumbers.add(6);validNumbers.add(7);validNumbers.add(8);validNumbers.add(0);
			
		}else{
			validNumbers.add(1);validNumbers.add(2);validNumbers.add(3);validNumbers.add(4);validNumbers.add(5);
			validNumbers.add(5);validNumbers.add(6);validNumbers.add(7);validNumbers.add(8);validNumbers.add(0);
			validNumbers.add(9);validNumbers.add(10);
			validNumbers.add(11);validNumbers.add(12);validNumbers.add(13);validNumbers.add(14);validNumbers.add(15);
			
		}
		Set elements=new HashSet();
		for(int num:puzzle){
			if(validNumbers.contains(num))
			elements.add(num);
		}
		if(elements.size()!=actualSize){
			System.out.println("Please enter unique tiles or valid tiles to proceed");
			System.exit(1);
		}
		
	}
}
