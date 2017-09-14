# 8-15-Tiles-Puzzle-using-IDA*
Solving 8/15 puzzle using the  IDA* algorithm.


Problem Introduction:

Sliding puzzle problems are good examples to understand informored search algorithms. In Artificial Intelligence, A* and IDA* algorithms are used to find the goal using the heuristic. 

A* algorithm follows BFS approach, With admisible heuristic we can find optimal path by expanding the less nodes.

Since A* algorithim has memory constraint(BFS space complexity is b power d), it is not possible to solve when the state space increases, ex. 15 puzzle. To overcome this limit ida* algorithim is used. In ida*, depth-first search (space complexity is b*d) being called iteratively with depth limit value. When f(n) reaches iterative depth limit( when depth-limit>f(n) ) its path is cutoff and continoueses again. Here f(n)=g(n)+h(n), g(n) is the cost to the node and h(n) is heuristic calculated using Manhattan Distance.

Usage:

*Run the program with valid arguments. In case user failed to  enter valid input like puzzle type or not valid tiles system shows error message on the screen* 

*Sample run*

java -jar IDA_TilesPuzzle.jar 8tiles 1 2 3 4 5 6 0 7 8


java -jar IDA_TilesPuzzle.jar 15tiles 14 2 1 7 6 0 5 4 11 9 12 3 8 13 10 15


(To run the program individually IDAStar.java has the main method to run)


Further Enhancements:

There is lot of chance to improve the performance of this program further. Since I was in hurry to  submit my assignment I did code boilor plate coding and using the unnecesary variables(like pathVisited is HashMap variable used to track the required moves to reach goal state this can be avoided by keeping path variable in Node and handling state properly).

In 8/15 Tiles puzzle almost half of the states are not solvable. So, before running the program we can include the check if the input instance is solvable or not. Below is the link that explained clearly how to verify instance is solvable or  not. http://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/ 
