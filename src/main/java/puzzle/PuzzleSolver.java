/**
 * solve puzzle with rotation
 * @param Map<Integer, List<PuzzleElement>> puzzleStructure
 * @param private PuzzleElement[][] board;
 * @param private List<Integer> usedElementById;
 * @param private List<Integer> availableRowsForSolution
 *
 * Author: Yelena Koviar
 *
 * */
package puzzle;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PuzzleSolver {
    private int numOfThreads;
    private List<PuzzleElement> elements = new ArrayList<>();
    private Map<Integer, List<PuzzleElement>> puzzleStructure;
    private List<Integer> usedElementById;
    private PuzzleElement[][] board;
    private List<Integer> availableRowsForSolution;
    private int rows;
    private int columns;
    private int counterOfElement;
    private static AtomicBoolean resultFound = new AtomicBoolean(false);
    private PuzzleElement[][] solutionBoard;


    public PuzzleSolver(Puzzle puzzle1, int numOfThreads) {
        this.elements = puzzle1.getPuzzleElementList();
        this.puzzleStructure = puzzle1.getAvailableOptionsForSolution();
        this.numOfThreads = numOfThreads;
        counterOfElement = puzzle1.getCounterOfPuzzleElementList();
        availableRowsForSolution = puzzle1.getNumOfRowsForSolution(counterOfElement);
        usedElementById = new ArrayList<>();
    }

    /**
     * initiate bord for available row solution and start the solver
     * @return
     */
    public PuzzleElement[][] solve() throws ExecutionException, InterruptedException {
//TODO: create threadPool for available row for solution
        if (numOfThreads == 0) {
            numOfThreads = 1;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);

        System.out.println("number of thread :  " + numOfThreads);
        Future<PuzzleElement[][]> future;
        Callable<PuzzleElement[][]> puzzleCallable = null;
        for (int i = 0; i < availableRowsForSolution.size(); i++) {
            int finalI = i;
            puzzleCallable = () -> {
                int r = availableRowsForSolution.get(finalI);
                int c = counterOfElement / r;
                System.out.println("r=" + r);
                System.out.println("c=" + c);
                initPuzzle(c, r);
                usedElementById.clear();
                System.out.println(Thread.currentThread().getName() + " Try to solve " + r + "X" + c);
                PuzzleElement[][] board = solve(0, 0);
                return board;
            };
            future = executorService.submit(puzzleCallable);
        }

        while (!(resultFound.get())) {
            System.out.println("Solution was not found...(yet)");
        }

        executorService.shutdown();
        return solutionBoard;
    }

    private void initPuzzle(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        board = new PuzzleElement[rows][columns];
    }

    /**
     * solve: recursive function get next plaice in board
     * @param r
     * @param c
     * @return
     */
    private PuzzleElement[][] solve(int r, int c) {
        if (isSolved(r,c)){
            return board;
        }

        int key= createKey(r,c);
        List<PuzzleElement> puzzleElements = puzzleStructure.get(key);
        if(puzzleElements==null){
            return null;
        }
        // Try each remaining piece in this square
        for (PuzzleElement p : puzzleElements) {
            if(inUse(p)){
                continue;
            }

            board[r][c]=p;
            setAsUsed(p);
            Position next = nextPos(r, c);
            PuzzleElement[][] solution = solve(next.row, next.column);
            if (solution != null && (!(resultFound.get())) ) {
                System.out.println("Solution was found by " + Thread.currentThread().getName());
                resultFound.set(true);
                solutionBoard = solution;
                return solution;
            }

            setAsNotUsed(p);
        }
        // no solution with this piece
        return null;
    }

    /**
     * create key by using current place in board
     * @param r
     * @param c
     * @return
     */
    private int createKey(int r, int c) {
        int left,right,top,bottom;
        if (c == 0) {
            left = 0;
        }else{
            left = 0 - board[r][c - 1].getRight();
        }
        if (c == board[0].length -1) {
            right = 0;
        }else{
            right = 5;
        }
        if (r == 0) {
            top = 0;
        }else{
            top = 0 - board[r - 1][c].getBottom();
        }

        if (r == board.length -1) {
            bottom = 0;
        }else{
            bottom = 5;
        }
        return  left * 1000 + top * 100 + right*10 + bottom;
    }


    /**
     * set element as used to avoid reuse
     * @param p
     */
    private void setAsUsed(PuzzleElement p) {
        usedElementById.add(p.getId());
    }

    /**
     * set element not in use if not fit in bord
     * @param p
     */
    private void setAsNotUsed(PuzzleElement p) {
        usedElementById.remove(usedElementById.indexOf(p.getId()));
    }

    /**
     * check if element in use
     * @param p
     * @return
     */
    private boolean inUse(PuzzleElement p) {
        if(usedElementById==null){
            return false;
        }
        else if(usedElementById.contains(p.getId())){
            return true;
        }
        return false;
    }

    private Position nextPos(int r, int c) {
        if(c < columns-1)  // if can move right
            return new Position(r,c+1);
        return new Position(r+1,0); // move to next row (even if it overflows)
    }

    private boolean isSolved(int r, int c) {
        return  (c >= columns || r >= rows); // at last position
    }

    private class Position {
        private int row;
        private int column;
        public Position (int row, int column) {
            this.column = column;
            this.row = row;
        }
    }

}