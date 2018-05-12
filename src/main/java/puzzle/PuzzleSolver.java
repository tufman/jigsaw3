package puzzle;

import java.util.ArrayList;
import java.util.List;

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


    public PuzzleElement[][] solve() {
        System.out.println("\u001B[34m" + " Try to solve " + rows + "X" + columns + " by thread ID " + Thread.currentThread().getId() + " name " + Thread.currentThread().getName());
        PuzzleElement[][] solve = puzzleSolution.solve(0, 0, this);
        return solve;
    }

    public PuzzleElement[][] getBoard() {

        return board;
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


    public void insert(PuzzleElement p, int currentRow, int currentColumn) {
        board[currentRow][currentColumn]=p;
    }
}
