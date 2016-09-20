package mazeGenerator;

import maze.Maze;
import maze.Wall;
import maze.Cell;
import java.util.*;

public class ModifiedPrimsGenerator implements MazeGenerator {
/**
* @author Wolf Zimmermann
* @author Tam Huynh
***/
	@Override
	public void generateMaze(Maze maze) {
		ArrayList<Cell> z = new ArrayList<Cell>();
		ArrayList<Cell> frontier = new ArrayList<Cell>();
		Cell[][] grid = maze.map;

		// create a list of all cells
		ArrayList<Cell> allCells = new ArrayList<Cell>();
		for(int r = 0 ; r < maze.sizeR ; r++)
			{
				for(int c = 0 ; c < maze.sizeC ; c++)
				{

					allCells.add(grid[r][c]);
				}
			}
			long seed = System.nanoTime();
			while(allCells.get(0) == null){

			 Collections.shuffle(allCells, new Random(seed));
			 // pick a random first cell for the maze
		 }
		 // that random cell to Z
			 z.add(allCells.get(0));
			 // add all neighbours to the frontier set
			 frontier.addAll(Arrays.asList(z.get(0).neigh));

			 // while z does not have all the maze cells
			 while(z.size() != allCells.size()){

				 seed = System.nanoTime();
	 			 Collections.shuffle(frontier, new Random(seed));
				 Cell randoCell = frontier.get(0);
				 // Grab a random cell from the frontier
					Boolean removed = false;

				 if(randoCell == null){
					 continue;
				 }
				 if(z.contains(randoCell)){
					 frontier.remove(0);
					 continue;
				 }
				 for(Cell zCell:z){
					 if(!Collections.disjoint(Arrays.asList(zCell.wall), Arrays.asList(randoCell.wall))){
						//  find the cell which wall to knock down
						 if(removeMatchingWall(zCell.wall , randoCell.wall)){
							 if(z.contains(randoCell.tunnelTo)){
								 // if its already tunneled to lets skip
								 break;
							 }
							 removed = true;
							 break;
						 }

					 }
				 }
				 if(removed == true){
					 //if the wall is knoked down remove from the frontier and add to Z
					 frontier.remove(0);
					 z.add(randoCell);
					 if(randoCell.tunnelTo != null){
						 // also add the tunnel if there is one
						 z.add(randoCell.tunnelTo);
					 }
					 // add all the neighbours to the frontier
					 frontier.addAll(Arrays.asList(randoCell.neigh));

				 }

			 }


	} // end of generateMaze()
	public Boolean removeMatchingWall(Wall[] a, Wall[] b){
		int w = 0;
		for(int x = 0; x < a.length; x++){
			for(int y = 0 ; y < b.length; y++){
				if(a[x] != null && b[y] != null ){
					if(a[x] == b[y]){
						b[y].present = false;
						a[x].present = false;
						w++;
						return true;
					}
				}
			}
		}
		return false;
	}

} // end of class ModifiedPrimsGenerator
