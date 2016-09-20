package mazeSolver;

import maze.Cell;
import maze.Maze;

import java.util.Stack;

/**
 * Implements the recursive backtracking maze solving algorithm.
 * @author Tam Huynh
 * @author Wolf Zimmermann
 */
public class RecursiveBacktrackerSolver implements MazeSolver {

    boolean solved = false;
	boolean hasDeadend[][] ;
	boolean isExplored[][];
    int countExplored = 0;
	@Override
	public void solveMaze(Maze maze) {
        // TODO Auto-generated method stub
        hasDeadend = new boolean[maze.sizeR][maze.sizeC];
        if (maze.type == 2) {
            isExplored = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];
        } else {
            isExplored = new boolean[maze.sizeR][maze.sizeC];
        }
        //solveMaze(maze,maze.entrance.r, maze.entrance.c);
        //solveTunnel(maze,maze.entrance.r, maze.entrance.c);
        // boolean a = soMaze(maze,maze.entrance.r, maze.entrance.c);
        boolean a;
        // boolean a = solveTunnelMaze(maze,maze.entrance.r, maze.entrance.c);
        // boolean a = solveHex(maze,maze.entrance.r, maze.entrance.c);
        switch (maze.type) {
            case 0:
                a = soMaze(maze, maze.entrance.r, maze.entrance.c);
                break;
            case 1:
                a = solveTunnelMaze(maze, maze.entrance.r, maze.entrance.c);
                break;
            case 2:
                a = solveHex(maze, maze.entrance.r, maze.entrance.c);
                break;

        }

    } // end of solveMaze()


	@Override
	public boolean isSolved() {
		// TODO Auto-generated method stub
         return this.solved;

	} // end if isSolved()


	@Override
	public int cellsExplored() {
		// TODO Auto-generated method stub
		return countExplored;
	} // end of cellsExplored()

    /**
     * Algorithm
     * base case: cell is visited or exit found or hit dead end (false case)
     * call soMaze ( return true if any available neigh to visit )
     *
     * @param maze Input the maze
     * @param r Input the row coordinate
     * @param c Input the column coordinate
     * @return true if successful exit, false if not
     */
    public boolean soMaze(Maze maze, int r, int c) {

        if ((0 > r || r >= maze.sizeR) || (0 > c || c >= maze.sizeC)) return false;
      //  System.out.println("checking [r][c]: " + r + " : " + c);

        //  System.out.println("r: " + r + "c: " + c + "direction: " + direction);
        if (isExplored[r][c]) {
            //     System.out.println("oh la" + hasDeadend(maze.map[r][c], direction));
            return false;
        }
        isExplored[r][c] = true;
        maze.drawFtPrt(maze.map[r][c]);
        countExplored++;
        if (maze.map[r][c] == maze.exit) {
            this.solved = true;
            return false;
        }

        //    System.out.println("checking [newR][newC]: " + newR + " : " + newC);

        if ((!hasWall(maze.map[r][c], Maze.NORTH) && soMaze(maze, r + Maze.deltaR[Maze.NORTH], c + Maze.deltaC[Maze.NORTH]))
                || (!hasWall(maze.map[r][c], Maze.SOUTH) && soMaze(maze, r + Maze.deltaR[Maze.SOUTH], c + Maze.deltaC[Maze.SOUTH]))
                || (!hasWall(maze.map[r][c], Maze.WEST) && soMaze(maze, r + Maze.deltaR[Maze.WEST], c + Maze.deltaC[Maze.WEST]))
                || (!hasWall(maze.map[r][c], Maze.EAST) && soMaze(maze, r + Maze.deltaR[Maze.EAST], c + Maze.deltaC[Maze.EAST]))) {

            // maze.drawFtPrt(maze.map[r][c]);
            //soMaze(maze, newR, newC);
            return true;
        } else return false;
    }//end of soMaze

    /**
     *  * Algorithm
     * base case: cell is visited  or hit dead end or exit found(false case)
     * call solveTunnelMaze ( return true if any available neigh to visit )
     * when cell hit tunnel jump to other side and call solveTunnel maze (( return true if any available neigh to visit ))
     * @param maze Input the maze
     * @param r Input the row coordinate
     * @param c Input the column coordinate
     * @return true if successful exit, false if not
     */
    public boolean solveTunnelMaze(Maze maze, int r, int c) {


        if ((0 > r || r >= maze.sizeR) || (0 > c || c >= maze.sizeC)) return false;
        System.out.println("checking [r][c]: " + r + " : " + c);

        //  System.out.println("r: " + r + "c: " + c + "direction: " + direction);
        if (isExplored[r][c]) {
            //     System.out.println("oh la" + hasDeadend(maze.map[r][c], direction));
            return false;
        }
        isExplored[r][c] = true;
        maze.drawFtPrt(maze.map[r][c]);
        countExplored++;
        if (maze.map[r][c] == maze.exit) {
            this.solved = true;
            return false;
        }
        int newR = r;
        int newC=c;
        if(maze.map[r][c].tunnelTo!=null){
            newR = maze.map[r][c].tunnelTo.r;
            newC = maze.map[r][c].tunnelTo.c;
          //  System.out.println("tunnel happen");
           // maze.drawFtPrt(maze.map[newR][newC]);
        }
        //    System.out.println("checking [newR][newC]: " + newR + " : " + newC);

        if ((!hasWall(maze.map[newR][newC], Maze.NORTH) && solveTunnelMaze(maze, newR + Maze.deltaR[Maze.NORTH], newC + Maze.deltaC[Maze.NORTH]))
                || (!hasWall(maze.map[newR][newC], Maze.SOUTH) && solveTunnelMaze(maze, newR + Maze.deltaR[Maze.SOUTH], newC + Maze.deltaC[Maze.SOUTH]))
                || (!hasWall(maze.map[newR][newC], Maze.WEST) && solveTunnelMaze(maze, newR + Maze.deltaR[Maze.WEST], newC + Maze.deltaC[Maze.WEST]))
                || (!hasWall(maze.map[newR][newC], Maze.EAST) && solveTunnelMaze(maze, newR + Maze.deltaR[Maze.EAST], newC + Maze.deltaC[Maze.EAST]))) {

            // maze.drawFtPrt(maze.map[r][c]);
            //soMaze(maze, newR, newC);
            return true;
        } else return false;
    }//end of solveTunnel

    /**
     *  * Algorithm
     * base case: cell is visited or exit found or hit dead end (false case)
     * call solveHex ( return true if any available neigh to visit )
     *
     * @param maze Input the maze
     * @param r Input the row coordinate
     * @param c Input the column coordinate
     * @return true if successful exit, false if not
     */
    public boolean solveHex(Maze maze, int r, int c) {


        // maze.drawFtPrt(maze.map[r][c]);
        if ((0 > r || r >= maze.sizeR) || ((r + 1) / 2 > c || c >= maze.sizeC + (r + 1) / 2)) return false;
      //  System.out.println("checking [r][c]: " + r + " : " + c);

        //  System.out.println("r: " + r + "c: " + c + "direction: " + direction);
        if (isExplored[r][c]) {
            return false;
        }
        {
            isExplored[r][c] = true;
            maze.drawFtPrt(maze.map[r][c]);
            countExplored++;
            if (maze.map[r][c] == maze.exit) {

                this.solved = true;
                return false;}
            //    maze.drawFtPrt(maze.map[r][c]);


            if ((!hasWall(maze.map[r][c], Maze.EAST) && solveHex(maze, r + Maze.deltaR[Maze.EAST], c + Maze.deltaC[Maze.EAST]))
                    || (!hasWall(maze.map[r][c], Maze.WEST) && solveHex(maze, r + Maze.deltaR[Maze.WEST], c + Maze.deltaC[Maze.WEST]))
                    || (!hasWall(maze.map[r][c], Maze.NORTHWEST) && solveHex(maze, r + Maze.deltaR[Maze.NORTHWEST], c + Maze.deltaC[Maze.NORTHWEST]))
                    || (!hasWall(maze.map[r][c], Maze.SOUTHWEST) && solveHex(maze, r + Maze.deltaR[Maze.SOUTHWEST], c + Maze.deltaC[Maze.SOUTHWEST]))
                    || (!hasWall(maze.map[r][c], Maze.SOUTHEAST) && solveHex(maze, r + Maze.deltaR[Maze.SOUTHEAST], c + Maze.deltaC[Maze.SOUTHEAST]))
                    || (!hasWall(maze.map[r][c], Maze.NORTHEAST) && solveHex(maze, r + Maze.deltaR[Maze.NORTHEAST], c + Maze.deltaC[Maze.NORTHEAST]))) {
             //   System.out.println("happen ");
                //     maze.drawFtPrt(maze.map[r][c]);

                return true;
            }

            else {
           //     System.out.println("DEADEND ");
                return false;
            }
        }
        //  return false;
    }//end of solveHex

    /**
     * Solve normal or Hex maze by non recursive backtracking
     * Add entrance to stack
     * Do
     *    if  any available unvisited neighbor
     *    add to stack
     *    hit wall or dead end pop out
     * While (not found exit)   //perfect maze is solvable
     * Final stack is the path from entrance to exit
     * @param maze Input the maze
     * @param r Input the row coordinate
     * @param c Input the column coordinate
     */
    public void solveMaze(Maze maze, int c, int r) {
        Stack<Cell> cells = new Stack<Cell>();
        cells.push(maze.entrance);


        do {

            //System.out.println("running "+i +" time "  + cells.peek());
            isExplored[cells.peek().r][cells.peek().c] = true;
            maze.drawFtPrt(cells.peek());
            if (cells.peek() == maze.exit) {
                this.solved = true;
            }

            int test1 = dir(cells.peek());
            //System.out.println("a direction of unvisited neigh : " + test1);

            if (test1 >= 0 && test1 <= Maze.NUM_DIR - 1) {
               // System.out.println("test1: " + test1);
                cells.push(cells.peek().neigh[test1]);
                countExplored++;

               // System.out.println("adding [" + cells.peek().r + " ," + cells.peek().c + "]");
            } else {
                //       System.out.println("popping [" + cells.peek().r +" ,"+ cells.peek().c + "]");
              //  System.out.println("dead end");
              //  System.out.println("Popping r: " + cells.peek().r + "c: " + cells.peek().c);
                cells.pop();
            }

            // System.out.println("is empty" + cells.empty());
        } while (!this.solved); //perfect maze is solvable

    }
    /**
     * Solve Tunnel maze by non recursive backtracking
     *
     * Add entrance to stack
     * Do
     *    if cell has tunnel jump to other side
     *    else if any available unvisited neighbor
     *    add to stack
     *    hit wall or dead end pop out
     * While (not found exit)   //perfect maze is solvable
     * Final stack is the path from entrance to exit
     *
     * @param maze Input the maze
     * @param r Input the row coordinate
     * @param c Input the column coordinate
     */
    public void solveTunnel(Maze maze, int c, int r) {
        Stack<Cell> cells = new Stack<Cell>();
        cells.push(maze.entrance);
        countExplored = 0;
        isExplored[maze.entrance.r][maze.entrance.c] = true;
        int i=0;
        do {
            i++;
            //System.out.println("running "+i +" time "  + cells.peek());
            isExplored[cells.peek().r][cells.peek().c] = true;
            maze.drawFtPrt(cells.peek());
            if(cells.peek()==maze.exit) {
                this.solved = true;
            }
            if(cells.peek().tunnelTo!=null) {
                cells.push(cells.peek().tunnelTo);
                maze.drawFtPrt(cells.peek());
                countExplored++;
                int test1 = dir(cells.peek());
                //System.out.println("a direction of unvisited neigh : " + test1);
                if (test1 >= 0 && test1 <= Maze.NUM_DIR - 1) {
                  //  System.out.println("test1: " + test1);
                    cells.push(cells.peek().neigh[test1]);
                    countExplored++;

                   // System.out.println("adding [" + cells.peek().r + " ," + cells.peek().c + "]");
                }
            }else {
                   int  test1 = dir(cells.peek());
                    //System.out.println("a direction of unvisited neigh : " + test1);
                    if (test1 >= 0 && test1 <= Maze.NUM_DIR - 1) {
                     //   System.out.println("test1: " + test1);
                        cells.push(cells.peek().neigh[test1]);
                        countExplored++;

                      //  System.out.println("add [" + cells.peek().r + " ," + cells.peek().c + "]");
                    } else {
                        //       System.out.println("popping [" + cells.peek().r +" ,"+ cells.peek().c + "]");
                    //    System.out.println("dead end");
                     //   System.out.println("Popping r: " + cells.peek().r + "c: " + cells.peek().c);
                        cells.pop();
                    }

            }
            // System.out.println("is empty" + cells.empty());
        }while (!this.solved); //perfect maze is solvable

    }
	/*

    public boolean solMaze(Maze maze, int r, int c) {
        // maze.drawFtPrt(maze.map[r][c]);

        int direction = (int) (Math.random() * 100 % Maze.NUM_DIR);
      //  System.out.println("r: " + r + "c: " + c + "direction: " + direction);
       // System.out.println("running" + isExplored[r][c]);
        if(isExplored[r][c] || hasWall(maze.map[r][c],Maze.EAST)) return false;
        else {
            System.out.println("else running" + isExplored[r][c]);
            isExplored[r][c] = true;
            maze.drawFtPrt(maze.map[r][c]);
            if(maze.map[r][c]==maze.exit) {
                this.solved = true;
                return true;
            }
            for (int i = 0; i < Maze.NUM_DIR; i++) {
                if (maze.map[r][c].wall[direction] != null) {
                    if (!maze.map[r][c].wall[direction].present) {
                        if (maze.map[r][c].neigh[direction] != null) {
                            System.out.println("r: " + r + "c: " + c + "direction: " + direction);
                            if (solMaze(maze, r + Maze.deltaR[direction], c + Maze.deltaC[direction])) {

                                return true;
                            } else return false;
                        }

                    } else {
                        if (solMaze(maze, r, c)) {
                            return true;
                        } else
                            return false;
                    }
                }
                if (direction == Maze.NUM_DIR - 1) direction = 0;
                else direction++;
            }
            return false;
        }

    }//end of soMaze
    */

    /**
     *
     * @param aCell
     * @param direction
     * @return
     */
    /*
	public boolean hasDeadend(Cell aCell, int direction){
        if(aCell.wall[direction]!=null) {
            if(aCell.wall[direction].present == true) return true;
        }

        return false;
    }
    */
    /**
     *
     * @param aCell Inout a cell
     * @return int value -1 if not available neigh to visit
     * else return direction
     */
    public int dir( Cell aCell) {

        int direction = (int) (Math.random() * 100 % Maze.NUM_DIR);
        System.out.println(" before direction: " + direction);
        int i;
        for (i = 0; i < Maze.NUM_DIR; i++) {
            if (aCell.wall[direction] != null) {
				if (aCell.wall[direction].present) {

					if (direction == Maze.NUM_DIR - 1) direction = 0;
					else direction++;
				} else if (aCell.neigh[direction] != null) {
                    if (!isExplored[aCell.neigh[direction].r][aCell.neigh[direction].c]){
                        System.out.println("direction: " + direction);
                    return direction;
                }
                    else if (direction == Maze.NUM_DIR - 1) direction = 0;
                    else direction++;
				}
			}else if (direction == Maze.NUM_DIR - 1) direction = 0;
                else direction++;

        }
        return -1;
    }//end of dir

    /**
     *
     * @param aCell Inout a cell
     * @return int value -1 if not available neigh to visit
     * else return direction
     */
    public int randomDir( Cell aCell) {

        int direction = (int) (Math.random() * 100 % Maze.NUM_DIR);
     //   System.out.println(" before direction: " + direction);

        for (int i = 0; i < Maze.NUM_DIR; i++) {
            if (aCell.wall[direction] != null) {
                if (aCell.wall[direction].present) {
                    if (direction == Maze.NUM_DIR - 1) direction = 0;
                    else direction++;
                } else if (aCell.neigh[direction] != null) { //neigh Null mean outside the maze
                    if (!isExplored[aCell.r + Maze.deltaR[direction]][aCell.c + Maze.deltaC[direction]]) {
                  //      System.out.println("direction: " + direction);

                        return direction;
                    } else {
                        if (direction == Maze.NUM_DIR - 1) direction = 0;
                        else direction++;
                    }
                } else {if (direction == Maze.NUM_DIR - 1) direction = 0;
                else direction++;}
            }else if (direction == Maze.NUM_DIR - 1) direction = 0;
            else direction++;

        }
        return -1;
    }// end of randomDir

    /**
     *
     * @param aCell Input a Cell
     * @param direction Input int value direction
     * @return true if there is a wall in that direction, false if not
     */
    public boolean hasWall(Cell aCell, int direction) {
        return (aCell.wall[direction].present);
     //   return false;
    }// end of isBrokenWall
} // end of class RecursiveBackTrackerSolver
