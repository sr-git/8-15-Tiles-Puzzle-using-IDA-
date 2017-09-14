/*
 * Author: Surendra Chekuri
 * File name: IDAStar.java
 * Dependencies: IDAStarHelper.java,Node.java,CommonConstants.java
 * Description: This the main program to run 8 tiles puzzle or 15 tiles puzze. 
 * Usage(15 tiles) sample: 15tiles 3 1 0 7 11 5 4 15 9 12 14 8 6 13 2 10
 *      8 tiles sample: 8tiles 1 2 3 4 5 6 7 8 0
 * 
 * :
 * 
 * */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class IDAStar {
	int blankPosition = 0;
	// visitedNodes: to avoid the visiting parent node/ duplicate node
	HashMap<String, String> visitedNodes = new HashMap<String, String>();
	LinkedHashMap<String, String> pathNodes = new LinkedHashMap<String, String>();
	int visited = 0;
      /*Iterative deepening method */
	public Set<String> ida(Node startNode) {
		visitedNodes.clear();
		Node initNode = startNode;
		int fValueMin;
		/*fValueMin depth to run the program.
		IDA* method being called iteratively until the depth reaches*/
		for (fValueMin = initNode.getfValue(); fValueMin < 100; fValueMin++) {
			visitedNodes.clear();
			pathNodes.clear();
			// Depth First search 
			Node nextNode = dfs(startNode, fValueMin, visitedNodes);
			/*Verifying the returned is goal state or not. If it is goal the goal exit loop*/
			if (nextNode != null && nextNode.equals(CommonConstants.goalState)) {
				System.out.println("Goal state Found::");
				System.out.println("Nodes Visited:" + visited);
				return pathNodes.keySet();
			}
			// System.out.println("Iteration:" + fValueMin);
		}
		System.out.println("Number of Nodes Visited:" + visited);
		return null;
	}

	public Node dfs(Node node, int fValueMin,
			HashMap<String, String> visitedNodes) {
		visited++;
		if (node != null) {
			// Keep track of visited node of each Ida iteration
			visitedNodes.put(IDAStarHelper.convertToString(node.puzzle), null);
			pathNodes.put(IDAStarHelper.convertToString(node.puzzle), null);
		}
		// verifying goal state or not
		if (node != null && node.equals(CommonConstants.goalState)) {
			return node;
		}
         /*Verifies node f value with Iterative IDA depth.
		if it reaches more value assuming no solution in this iteration.*/
		if (node.getfValue() > fValueMin) {
			return null;
		}
		// Fetching valid moves to current puzzle.
		ArrayList<String> moves = getMoves(node.getPuzzle());
		Node test = null;
		for (String move : moves) {
			// creating new puzzle for valid move.
			Node newNode = IDAStarHelper.moveTiles(move, node);
			if (!visitedNodes.containsKey(IDAStarHelper
					.convertToString(newNode.puzzle))) {
				// if new puzzele not visited already, run recursive dfs
				test = dfs(newNode, fValueMin, visitedNodes);
				if (test != null)
					return test;
				// visitedNodes.remove(IDAStarHelper.convertToString(node.puzzle));
				pathNodes.remove(IDAStarHelper.convertToString(node.puzzle));
			}
             // If reaches max fValueMin. undoing the step.
			IDAStarHelper.backTrackMove(node, move);
		}

		return null;
	}
   /*This method is used to fetch all valid moves for given puzzle*/
	private ArrayList<String> getMoves(int[] puzzle) {
		blankPosition = IDAStarHelper.blankPosition(puzzle);
		ArrayList<String> validMoves = IDAStarHelper.getMoves(blankPosition);
		return validMoves;
	}

	public static void main(String[] args) {
		int[] puzzle = null;
		if (!(args.length == 10 || args.length == 17)) {
			System.out.println("Invalid run.");
			System.out
					.println("Ex. input: IDAStar.java 8tiles 1 2 0 3 4 5 6 7 8");
			System.exit(1);
		}
		// Deciding 8tiles or 15 tiles based on user input.
		if (args[0].equals("8tiles")) {
			puzzle = new int[9];
			CommonConstants.PUZZLE_TYPE = 9;
			CommonConstants.goalState = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };
			CommonConstants.depthLimit = 50;
			for (int i = 1; i < args.length; i++) {
				puzzle[i - 1] = Integer.parseInt(args[i]);
			}
			IDAStarHelper.verifyUniqueArray(puzzle,9);
			
		} else if (args[0].equals("15tiles")) {
			puzzle = new int[16];
			CommonConstants.PUZZLE_TYPE = 16;
			CommonConstants.goalState = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9,
					10, 11, 12, 13, 14, 15, 0 };
			CommonConstants.depthLimit = 90;
			for (int i = 1; i < args.length; i++) {
				puzzle[i - 1] = Integer.parseInt(args[i]);
			}
			IDAStarHelper.verifyUniqueArray(puzzle,16);
		} else {
			System.out.println("First argument should be 8tiles or 15tiles");
			System.exit(1);
		}
		// int[] puzzle = { 6,1,8,7,0,3,4,2,5 };
		System.out.println("Searching Goal: ");
		Node startNode = new Node(0, 0, 0, puzzle);
		IDAStar test = new IDAStar();
		final long startTime = System.currentTimeMillis();
		Set<String> pathVisited = test.ida(startNode);
		final long endTime = System.currentTimeMillis();
		if (pathVisited != null) {
			System.out.println("Moves to reach goal.");
			IDAStarHelper.printArray(pathVisited);
		} else {
			System.out.println("No solution found!");
		}
		System.out.print("Time taken to run the IDA*:" + (endTime - startTime)
				+ " milli seconds.");
	}
}
