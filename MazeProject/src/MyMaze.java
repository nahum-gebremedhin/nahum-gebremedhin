// Names: Nahum Gebremedhin
// x500s: gebre109

import java.util.Random;

public class MyMaze{
    Cell[][] maze;
    int rows;
    int cols;

    public MyMaze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        int i,j;
        maze = new Cell[rows][cols];

        for(i = 0; i < rows; i++){
            for(j = 0; j < cols; j++){
                maze[i][j] = new Cell();
            }
        }
    }

    /* TODO: Create a new maze using the algorithm found in the writeup. */
    public static MyMaze makeMaze(int rows, int cols) {
        MyMaze maze1 = new MyMaze(rows, cols);
        Stack1Gen<int[]> stack = new Stack1Gen<int[]>();
        int i, j;

        stack.push(new int[]{0,0});         //initialize stack with start index {0,0}
        maze1.maze[0][0].setVisited(true);  //and marks the start as visited

        while(!stack.isEmpty()){    //start of the search algorithm loop
           int count = 0;
           int[] top = stack.top();


           Stack1Gen<int[]> neighbor = new Stack1Gen<int[]>();    //makes a stack of all unvisited neighbors

           if(top[0] != 0 && !maze1.maze[top[0] - 1][top[1]].getVisited()){ //each if statement correlates to the 4
               neighbor.push(new int[]{top[0]-1,top[1]});                        //possible neighbor spots
               count++;
           }
           if(top[1] != cols-1 && !maze1.maze[top[0]][top[1] + 1].getVisited()){
               neighbor.push(new int[]{top[0],top[1]+1});
               count++;
           }
           if(top[0] != rows-1 && !maze1.maze[top[0] + 1][top[1]].getVisited()){
               neighbor.push(new int[]{top[0]+1,top[1]});
               count++;
           }
           if(top[1] != 0 && !maze1.maze[top[0]][top[1] - 1].getVisited()){
               neighbor.push(new int[]{top[0],top[1]-1});
               count++;
           }


           if(count != 0) {               //if the cell has at least one unvisited neighbor the stack will pop
               Random r = new Random();   //a random amount of times and get the cell at the top
               int n = r.nextInt(count);
               for(i = 0; i < n; i++){
                   neighbor.pop();
               }
               int[] randNeighbor = neighbor.top();

               stack.push(randNeighbor);
               maze1.maze[randNeighbor[0]][randNeighbor[1]].setVisited(true);  //mark neighbor as visited



               if(randNeighbor[0] == top[0]-1){                                    //depending on where the neighbor cell is in relation to the current cell,
                   maze1.maze[randNeighbor[0]][randNeighbor[1]].setBottom(false);  //the corresponding bottom or right wall will be removed
               }
               if(randNeighbor[1] == top[1]+1){
                   maze1.maze[top[0]][top[1]].setRight(false);
               }
               if(randNeighbor[0] == top[0]+1){
                   maze1.maze[top[0]][top[1]].setBottom(false);
               }
               if(randNeighbor[1] == top[1]-1){
                   maze1.maze[randNeighbor[0]][randNeighbor[1]].setRight(false);
               }
           }
           if(count == 0){
               stack.pop();
           }
        }
        for(i = 0; i < rows; i++){     //resets the visited attribute of every cell to false
            for(j = 0; j < cols; j++){
                maze1.maze[i][j].setVisited(false);
            }
        }
        return maze1;
    }


    /* TODO: Print a representation of the maze to the terminal */
    public void printMaze(boolean path) {
        int i, j;
        String s = "|";


        for(i = 0; i < this.cols; i++){   //creates the top border of the maze
            s += "---|";
        }
        s += "\n ";

        if(!path){                   //creates maze with no asterisks
            for(i = 0; i < this.rows; i++){
                s += "|";

                for(j = 0; j < this.cols; j++){  //creates cells and vertical borders between cells if a border exists
                    if(this.maze[i][j].getRight() && i+j != ((rows-1)+(cols-1))){
                        s += "   |";
                    }
                    else{
                        s += "    ";
                    }
                }
                s += "\n";
                s +='|';
                for(j = 0; j < this.cols; j++){       //creates horizontal borders between cells if they exist
                    if(this.maze[i][j].getBottom()){
                        s += "---|";
                    }
                    else{
                        s += "   |";
                    }
                }
                s += "\n";
            }
        }
        if(path){      //creates maze with asterisk in every visited cell
            for(i = 0; i < this.rows; i++){
                if(i == 0){

                }
                else {
                    s += "|";
                }
                for(j = 0; j < this.cols; j++){
                    if(this.maze[i][j].getVisited()){
                        s += " * ";
                    }
                    else{
                        s += "   ";
                    }
                    if(this.maze[i][j].getRight() && i+j != ((rows-1)+(cols-1))){
                        s += "|";
                    }
                    else{
                        s += " ";
                    }
                }
                s += "\n";
                s += "|";
                for(j = 0; j < this.cols; j++){
                    if(this.maze[i][j].getBottom()){
                        s += "---|";
                    }
                    else{
                        s += "   |";
                    }
                }
                s += "\n";
            }
        }
        System.out.println(s);
    }

    /* TODO: Solve the maze using the algorithm found in the writeup. */
    public void solveMaze() {
        Q1Gen<int[]> q = new Q1Gen<int[]>();
        q.add(new int[]{0,0});

        while(!q.isEmpty()){
            int[] front = q.remove();
            this.maze[front[0]][front[1]].setVisited(true);

            if((front[0]+front[1]) == ((rows-1)+(cols-1))){
                break;
            }

            else{ //checks if neighbors are reachable and un-visited then enqueues them
                if(front[0] != 0 && !this.maze[front[0] - 1][front[1]].getBottom() && !this.maze[front[0] - 1][front[1]].getVisited()){
                    q.add(new int[]{front[0]-1,front[1]});
                }
                if(front[1] != cols-1 && !maze[front[0]][front[1]+1].getVisited() && !maze[front[0]][front[1]].getRight()){
                    q.add(new int[]{front[0],front[1]+1});
                }
                if(front[0] != rows-1 && !maze[front[0]][front[1]].getBottom() && !maze[front[0]+1][front[1]].getVisited()){
                    q.add(new int[]{front[0]+1,front[1]});
                }
                if(front[1] != 0 && !maze[front[0]][front[1]-1].getVisited() && !maze[front[0]][front[1]-1].getRight()){
                    q.add(new int[]{front[0],front[1]-1});
                }
            }
        }
        this.printMaze(true);
    }



    public static void main(String[] args){
        /* Any testing can be put in this main function */

        MyMaze m = makeMaze(7,9);
        MyMaze maze2 = makeMaze(5,20);
        m.solveMaze();
        maze2.solveMaze();

    }
}
