package mazeGenerator;

import maze.Maze;
import maze.*;
import java.util.*;
public class KruskalGenerator implements MazeGenerator {
// KruskalGenerator
//
// First Create a disjoint set of cells
// then I create a list of all the wall/edges
// I then join the to tunneled cells sets
// Now all the data is setup to apply Kruskal
// While There are unchecked walls
// pick a random wall
// find the cell that had this wall
// find which one of its neighbors has this wall
// if the neighbor is in another set other than the cell we first found
// it is disjoint, we then join their sets and remove the wall from the cell,
// and from the list of walls to be checked.

/**
* @author Wolf Zimmermann
* @author Tam Huynh
***/
	@Override
	public void generateMaze(Maze maze) {
		// TODO Auto-generated method stub
		//list of walls
		ArrayList<Wall> edges = new ArrayList<Wall>();
		//grid of all cells
		Cell[][] grid = maze.map;
		//set of trees
		ArrayList<ArrayList<Cell>>sets = new ArrayList<ArrayList<Cell>>();
		if(maze.type == maze.HEX){
		for(int r = 0; r < maze.sizeR ; r++){
				for(int c = 0; c < maze.sizeC + (r + 1) / 2; c++){
					if(grid[r][c] != null){
					ArrayList<Cell> subSet = new ArrayList<Cell>();
					subSet.add(grid[r][c]);
					 sets.add(subSet);
// data setup for Kruskal
// get all the edges
					for(int x = 0; x < grid[r][c].wall.length; x++){
								if(grid[r][c].wall[x] != null){
									edges.add(grid[r][c].wall[x]);
								}
							}
					}
				}
			}
		}else{
			for(int r = 0; r < maze.sizeR ; r++){
					for(int c = 0; c < maze.sizeC ; c++){
						if(grid[r][c] != null){
						ArrayList<Cell> subSet = new ArrayList<Cell>();
						subSet.add(grid[r][c]);
						 sets.add(subSet);
						for(int x = 0; x < grid[r][c].wall.length; x++){
									if(grid[r][c].wall[x] != null){
										edges.add(grid[r][c].wall[x]);
									}
								}
						}
					}
				}
		}
// join the disjoint tunnel cells
		for(int i = 0 ; i < sets.size(); i++){
			if(sets.get(i).get(0).tunnelTo != null){
				for(int x = 0 ; x < sets.size(); x++){
					if (sets.get(i).get(0).tunnelTo == sets.get(x).get(0)){
						sets.get(i).addAll(sets.get(x));
						sets.remove(x);
					}
				}
			}
		}


		while(edges.size() > 0){
			//choose a random edge
		 long seed = System.nanoTime();
			Collections.shuffle(edges, new Random(seed));
			Wall tempWall = edges.get(edges.size() - 1);
			//go through all the sets to find a cell with the edge
			for(int i = 0 ; i < sets.size(); i++){
				for(int x = 0; x < sets.get(i).size(); x++){
					// if it has the wall we picked
					if(Arrays.asList(sets.get(i).get(x).wall).contains(tempWall)== true){
							for(int y = 0 ; y < sets.get(i).get(x).neigh.length; y++){
								//skip the null neighbors
								if(sets.get(i).get(x).neigh[y] == null ){
									continue;
								}
								// find the neighbor who shares this wall
								if(Arrays.asList(sets.get(i).get(x).neigh[y].wall).contains(tempWall)== true){
									//see if neighbor is disjoint
									for(int z = 0 ; z < sets.size(); z++){
										//skip its own set
										if(z == i){
											continue;
										}
										for(int g = 0; g < sets.get(z).size(); g++){
											// if the neighbor is an any other set than its own merge the neighbors set into the fist found set.
										if(sets.get(z).get(g) == sets.get(i).get(x).neigh[y]){
											tempWall.present = false;
											sets.get(i).addAll(sets.get(z));
											sets.remove(z);
											break;

										}
									}
								}
								}
							}
					}


				}


			}
			edges.remove(edges.size() - 1);

				}
	} // end of generateMaze()
}
