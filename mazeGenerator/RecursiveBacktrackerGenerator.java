package mazeGenerator;

import maze.Maze;
import maze.Cell;

/**
 * Generate 1 of 3 types of maze
 * By recursive backtracking
 *  For some mazes like Hexagon mazes if the size to big must run java machine with option -Xss8M
 *  to increase the cache to ensure the recursion running
 * @author Tam Huynh
 * @author Wolf Zimmermann
 *
 */
public class RecursiveBacktrackerGenerator implements MazeGenerator {


    /**
     *
     * @param maze The reference of Maze object to generate.
     */
    @Override
    public void generateMaze(Maze maze) {

     //   System.out.println("Type: " + maze.type);
           int corR = (int) (Math.random() * 100 % maze.sizeR);
           int corC = (int) (Math.random() * 100 % maze.sizeC);
           if(maze.type == maze.HEX){
             corC = (int) (Math.random() * 100 % maze.sizeC + (corR + 1 )/ 2);
          }

        //in case coordinate r and c has tunnel generate other pair of value for starting point

        while (maze.map[corR][corC] != null && maze.map[corR][corC].tunnelTo!=null) {
            corR = (int) (Math.random() * 100 % maze.sizeR);
            corC = (int) (Math.random() * 100 % maze.sizeC);

        }
       // generateMaze(maze,2,2);

        switch(maze.type){
            case 0: generateMaze(maze,corR,corC);
                    break;
            case 1: generateTunnel(maze,corR,corC);
                    break;
            case 2: generateHex(maze,corR,corC);
                    break;
        }



        // System.out.println("Tunnel size: " + maze.sizeTunnel);
       // System.out.println("Tunnel [3,3] go to: " + maze.map[3][3].tunnelTo.r + ": " +  maze.map[3][3].tunnelTo.c );
    } // end of generateMaze()


    /**
     * Algorithm
     * base case: all cells are visited (generate back to starting point)
     * while  maze.map[r][c] has any neighbor cell available (unvisited)
     * call generateMaze go to each neighbor toward to the end of the path (no more neigh to visit or dead end)
     * then go back carve the wall connected with the cell it come form
     *
     * @param maze Input the maze
     * @param r Input the row coordinate
     * @param c Input the column coordinate
     */

    public void generateMaze(Maze maze, int r, int c) {

        while (randomDir(maze.map[r][c]) != -1) {
            int direction = randomDir(maze.map[r][c]);
            int newR = maze.map[r][c].r + Maze.deltaR[direction];
            int newC = maze.map[r][c].c + Maze.deltaC[direction];
            //   System.out.println("r: " + r + "c: " + c + "direction: " + direction);
            if ((0 <= newR && newR <= maze.sizeR - 1) && (0 <= newC && newC <= maze.sizeC - 1)) {

                if (!hasBrokenWall(maze.map[newR][newC])) {
                    maze.map[newR][newC].wall[Maze.oppoDir[direction]].present = false;
                    generateMaze(maze, newR, newC);

                }
            }
        }
    }//end of gennerateMaze


    /**
     * Algorithm
     * base case: all cells are visited (generate back to starting point)
     * while  maze.map[r][c] has any neighbor cell available (unvisited)
     * call generateHex go to each neighbor toward to the end of the path (no more neigh to visit or dead end)
     * then go back carve the wall connected with the cell it come form
     *
     * @param maze Input the maze
     * @param r Input the row coordinate
     * @param c Input the column coordinate
     */
    public void generateHex(Maze maze, int r, int c ) {
      // System.out.println("c");

        while (randomDir(maze.map[r][c]) != -1) {
            int direction = randomDir(maze.map[r][c]);
            int newR = maze.map[r][c].r + Maze.deltaR[direction];
            int newC = maze.map[r][c].c + Maze.deltaC[direction];
             // System.out.println("r: " + r + "c: " + c + "direction: " + direction);
            if ((0 <= newR && newR < maze.sizeR) && ((newR+1)/2 <= newC && newC < maze.sizeC + (newR + 1)/2)) {

                if (!hasBrokenWall(maze.map[newR][newC])) {
                    maze.map[newR][newC].wall[Maze.oppoDir[direction]].present = false;
                    generateHex(maze, newR, newC);

                }
            }
        }
    }//end of generateHex

    /**
     *
     * @param aCell Input a cell
     *
     * return true if cell has a broken wall
     *  false if all walls of cell are up
     */
    public boolean hasBrokenWall(Cell aCell) {

        for (int i = 0; i < Maze.NUM_DIR; i++) {
            if (aCell.wall[i] != null) {
                if (!aCell.wall[i].present) return true;
            }
        }
        return false;
    }// end of isBrokenWall

    /**
     *
     * @param aCell Input a cell
     *
     * return the direction that has a valid neighbour
     * return -1 if not found
     */
    public int dir(Cell aCell) {

        int direction = (int) (Math.random() * 100 % Maze.NUM_DIR);
        //System.out.println("direction: "+ direction);
        for (int i = 0; i < Maze.NUM_DIR; i++) {

            if (aCell.neigh[direction] != null) {

                for (int j = 0; j < Maze.NUM_DIR; j++) {
                    if (aCell.neigh[direction].wall[j] != null) {

                        if (!aCell.neigh[direction].wall[j].present) { //no wall
                            break; //visited
                        } else if (direction == Maze.NUM_DIR - 1) { //all wall still present Hexagon
                            return direction;
                        }

                    } else if (j == Maze.NUM_DIR - 1) {//all wall still present Normal maze
                        return direction;
                    }

                }
            }
            if (direction == Maze.NUM_DIR - 1) direction = 0;
            else direction++;


        }
        return -1; //if neigh is visited or has a broken wall
    }//end of dir

    /**
     *
     * @param aCell
     * @return
     */
    public int randomDir(Cell aCell) {

        int direction = (int) (Math.random() * 100 % Maze.NUM_DIR);
        //System.out.println("direction: "+ direction);
        // if(aCell == null){return -1;}
        for (int i = 0; i < Maze.NUM_DIR; i++) {
            if (aCell.neigh[direction] == null) {
                if (direction == Maze.NUM_DIR - 1) direction = 0;//normal maze cell reach end of array
                else direction++;
            } else {

                   if (!hasBrokenWall(aCell.neigh[direction]) ) return direction;
                   else if ( direction == Maze.NUM_DIR - 1)direction = 0; //hex maze cell reach end and neigh is visited
                   else direction++;
            }
        }
        return -1; //no neigh available
    }// end of randomDir

    /**
     * Algorithm
     * base case: all cells are visited (generate back to starting point)
     * while  maze.map[r][c] has any neighbor cell available (unvisited)
     * call generateHex go to each neighbor toward to the end of the path (no more neigh to visit or dead end)
     * then go back carve the wall connected with the cell it come form
     * On the way if meet tunnel jump to the other side of tunnel call generateTunnel
     *
     * @param maze Input the maze
     * @param r Input the row coordinate
     * @param c Input the column coordinate
     */

    public void generateTunnel(Maze maze, int r, int c) {
       // System.out.println("running tunnel ");
      //  if (maze.map[3][3].tunnelTo != null) {
             // maze.map[r][c].wall[Maze.oppoDir[direction]].present = false;
          //  generateMaze(maze, maze.map[r][c].tunnelTo.r, maze.map[r][c].tunnelTo.c);
           // System.out.println("hey tunnel");

     //   }
       // System.out.println("hey tunnel" + maze.map[3][3].tunnelTo);
        while (randomDir(maze.map[r][c]) != -1 ) {

            int direction = randomDir(maze.map[r][c]);
            int newR = maze.map[r][c].r + Maze.deltaR[direction];
            int newC = maze.map[r][c].c + Maze.deltaC[direction];
          //  System.out.println("r: " + r + "c: " + c + "direction: " + direction);


            if ((0 <= newR && newR < maze.sizeR ) && (0 <= newC && newC < maze.sizeC )) {

                if (!hasBrokenWall(maze.map[newR][newC])&& (maze.map[newR][newC].tunnelTo == null)) {
                 //   System.out.println("i do not have tunnel");
                    maze.map[newR][newC].wall[Maze.oppoDir[direction]].present = false;
                    generateTunnel(maze, newR, newC);
                } else if (maze.map[newR][newC].tunnelTo != null) {
                  // System.out.println("hey tunnel" + maze.map[newR][newC].tunnelTo);
                    maze.map[newR][newC].wall[Maze.oppoDir[direction]].present = false;
                    generateTunnel(maze,maze.map[newR][newC].tunnelTo.r, maze.map[newR][newC].tunnelTo.c);
                }


            }
        }


    }//end of generateTunnel
} // end of class RecursiveBacktrackerGenerator
