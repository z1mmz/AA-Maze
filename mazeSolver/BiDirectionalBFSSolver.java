package mazeSolver;

import maze.Maze;
import maze.*;
import java.util.*;
import mazeSolver.MazeSolver;

/**
 * Implements Bi-directional BFS maze solving algorithm
 * *****************************************************
 * 1. put start and finish cells into queues
 * 2. While items in queues pop and mark as Visited
 * 3. check both visted cells to see if seaches cross Over
 * 		if it is we have solved the maze
 * 4. add neighbours that have theirs walls down to the queues and add tunnels
 * if there are any
 * 5. repeat from 2 till finish
 * *****************************************************************************
 *
* @author Wolf Zimmermann
* @author Tam Huynh
*
**/
public class BiDirectionalBFSSolver implements MazeSolver {
Boolean solved = false;
int checked=0;
	@Override
	public void solveMaze(Maze maze) {
		Cell rootS = maze.entrance;
		Cell rootF = maze.exit;
		Queue<Cell> qS = new LinkedList<Cell>();
		Queue<Cell> qF = new LinkedList<Cell>();
		//put start and finsih in the queues
		qS.add(rootS);
		qF.add(rootF);
ArrayList<Cell> vistedS = new ArrayList<Cell>();
ArrayList<Cell> vistedF = new ArrayList<Cell>();
		while(qS.peek() != null && qF.peek() != null){
			Cell currS =  (Cell)qS.remove();
			Cell currF = (Cell)qF.remove();
			//get pop a cell from the queue and visit it
			maze.drawFtPrt(currF);
			maze.drawFtPrt(currS);
			checked += 2;
			//add the cell to the list of visited cells
			vistedS.add(currS);
			vistedF.add(currF);
			// chcek if the visited cells overlap if they have we have solved the maze
			for(int x = 0 ; x < vistedF.size(); x++){
				for(int i = 0; i < vistedS.size(); i++){
					if(vistedF.get(x).r == vistedS.get(i).r && vistedF.get(x).c == vistedS.get(i).c){
							solved = true;
							return;
					}
				}
			}
			// add neighbors that have a path to them ad have no been visted by start direction bfs
			for(int x = 0 ; x < currS.neigh.length ; x++ ){
					if(currS.neigh[x] != null){
						if(currS.wall[x] != null){
							if(currS.wall[x].present == false){
								if(!vistedS.contains(currS.neigh[x]) && !qS.contains(currS.neigh[x])){
								qS.add(currS.neigh[x]);
								if(currS.tunnelTo != null){
									qS.add(currS.tunnelTo);
								}
								}
							}
						}
					}
			}
			// add neighbors that have a path to them ad have no been visted by finish direction bfs
			for(int x = 0 ; x < currF.neigh.length ; x++ ){
					if(currF.neigh[x] != null){
						if(currF.wall[x] != null){
							if(currF.wall[x].present == false){
								if(!vistedF.contains(currF.neigh[x]) && !qF.contains(currF.neigh[x])){
								qF.add(currF.neigh[x]);
								if(currF.tunnelTo != null){
									qF.add(currF.tunnelTo);
								}
								}
							}
						}
					}
			}
		}
	} // end of solveMaze()


	@Override
	public boolean isSolved() {
		return solved;
	} // end of isSolved()


	@Override
	public int cellsExplored() {
		// TODO Auto-generated method stub
		return checked;
	} // end of cellsExplored()

} // end of class BiDirectionalBFSSolver
