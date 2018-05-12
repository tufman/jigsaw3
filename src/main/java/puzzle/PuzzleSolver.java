package puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

//public class PuzzleSolver implements Runnable{
public class PuzzleSolver{

    private List<Integer> usedElementById;
    private PuzzleElement[][] board;
    private PuzzleSolution puzzleSolution;
    private int rows;
    private int columns;


    public final static int JOKER = 5;

    public PuzzleSolver(int r, int c, PuzzleSolution puzzleSolution) {
        board = new PuzzleElement[r][c];
        usedElementById = new ArrayList<>();
        this.puzzleSolution = puzzleSolution;
        this.rows = r;
        this.columns = c;

    }


    //public void run() {
    public PuzzleElement[][] solve() {
        System.out.println("rows=" + rows);
        System.out.println("columns=" + columns);
        System.out.println(" Try to solve " + rows + "X" + columns + " by thread ID " + Thread.currentThread().getId() + " name " + Thread.currentThread().getName());
        //board = puzzleSolution.solve(0, 0, this);
        PuzzleElement[][] solve = puzzleSolution.solve(0, 0, this);

        return solve;

    }

    public PuzzleElement[][] getBoard() {
        return board;
    }


    /**
     * put piece in to board
     * @param e
     * @param currentRow
     * @param currentColumn
     * @return
     */
    public boolean tryInsert(PuzzleElement e, int currentRow, int currentColumn) {

        // TOP_LEFT_CORNER for one row solution
        if (rows ==1 && currentColumn ==0){
            if (!(e.getLeft()==0 && e.getTop()==0 && e.getBottom()==0)) return false;
        }
        //  TOP_LEFT_CORNER for one column solution
        else if (columns ==1 && currentRow ==0) {
            if ( !(e.getLeft()==0 && e.getTop()==0 && e.getRight()==0))
                return false;
        }
        //TOP_LEFT_CORNER 7
        if (currentRow == 0 && currentColumn == 0) {
            if (!(e.getLeft()==0 && e.getTop()==0 )) return false;
        }
        // BOTTOM_LEFT_CORNER 777
        else if (currentRow == rows-1 && currentColumn == 0) {
            if (!((e.getLeft()==0 && e.getBottom()==0)&&(board[currentRow-1][currentColumn].getBottom()+e.getTop() == 0))) return false;
        }
        // TOP_RIGHT_CORNER 77
        else if (currentRow == 0 && currentColumn==columns-1) {
            if ( !(( e.getTop()==0 && e.getRight()==0)&& (board[currentRow][currentColumn-1].getRight()+e.getLeft() == 0))) return false;
        }
        // BOTTOM_RIGHT_CORNER 7777
        else if (currentRow == rows -1 && currentColumn==columns-1) {
            if (!((e.getBottom()==0 && e.getRight()==0)&& (board[currentRow][currentColumn-1].getRight()+e.getLeft() == 0)
                    && (board[currentRow-1][currentColumn].getBottom()+e.getTop() == 0))) return false;
        }
        // one row solution
        else if (currentRow == rows -1 && currentRow == 0) {
            if ( !((e.getBottom()==0 && e.getTop()==0) && (board[currentRow][currentColumn-1].getRight()+e.getLeft() == 0)))
                return false;
        }
        //check if edge
        else if (currentRow == 0) { // first row
            if ( !((e.getTop()==0) && (board[currentRow][currentColumn-1].getRight()+e.getLeft() == 0)))
                return false;
        }
        // last row
        else if (currentRow == rows -1) {
            if ( !((e.getBottom()==0 ) && (board[currentRow-1][currentColumn].getBottom()+e.getTop() == 0)
                    && (board[currentRow][currentColumn-1].getRight()+e.getLeft() == 0)))
                return false;
        }
        //  one column solution
        else if (currentColumn == 0 && currentColumn==columns-1) {
            if (!((e.getLeft()==0 && e.getRight()==0)&&(board[currentRow-1][currentColumn].getBottom()+e.getTop() == 0)))
                return false;
        }
        // first column
        else if (currentColumn == 0) {
            if ( !((e.getLeft()==0 && e.getRight()==0)&&(board[currentRow-1][currentColumn].getBottom()+e.getTop() == 0)))
                return false;
        }
        // last column
        else if (currentColumn==columns-1) {
            if ( !((e.getRight()==0)&&(board[currentRow-1][currentColumn].getBottom()+e.getTop() == 0)
                    && (board[currentRow][currentColumn-1].getRight()+e.getLeft() == 0)))
                return false;
        }
        // middle element
        else {
            if ( !((board[currentRow-1][currentColumn].getBottom()+e.getTop() == 0)&& (board[currentRow][currentColumn-1].getRight()+e.getLeft() == 0)))
                return false;
        }

        board[currentRow][currentColumn]=e;
        return true;
    }

    /**
     * set element as used to avoid reuse
     * @param p
     */
    public void setAsUsed(PuzzleElement p) {
        usedElementById.add(p.getId());
    }

    /**
     * set element not in use if not fit in bord
     * @param p
     */
    public void setAsNotUsed(PuzzleElement p) {
        usedElementById.remove(usedElementById.indexOf(p.getId()));
    }

    /**
     * check if element in use
     * @param p
     * @return
     */
    public boolean inUse(PuzzleElement p) {
        if(usedElementById==null){
            return false;
        }
        else if(usedElementById.contains(p.getId())){
            return true;
        }
        return false;
    }


    /**
     * create key by using current place in board
     * @param r
     * @param c
     * @return
     */
    public int createKey(int r, int c) {



        int left,right,top,bottom;
        if (c == 0) {
            left = 0;
        }else{
            left = 0 - board[r][c - 1].getRight();
        }
        if (c == board[0].length -1) {
            right = 0;
        }else{
            right = JOKER;
        }
        if (r == 0) {
            top = 0;
        }else{
            top = 0 - board[r - 1][c].getBottom();
        }

        if (r == board.length -1) {
            bottom = 0;
        }else{
            bottom = JOKER;
        }
        return  left * 1000 + top * 100 + right*10 + bottom;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }


}
