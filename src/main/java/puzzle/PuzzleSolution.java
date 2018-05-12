/**
 * solve puzzle with rotation
 * @param Map<Integer, List<PuzzleElement>> puzzleStructure
 * @param private PuzzleElement[][] board;
 * @param private List<Integer> usedElementById;
 * @param private List<Integer> availableRowsForSolution
 *
 * By  initating  PuzzleSolver for each rowsXcolumns available option.
 * Expiring in case no result found by timer.
 *
 *
 * Author: Yelena Koviar
 *
 * */
package puzzle;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PuzzleSolution {
    private int numOfThreads;
    private Map<Integer, List<PuzzleElement>> puzzleStructure;
    private List<Integer> availableRowsForSolution;
    private int counterOfElement;
    private static AtomicBoolean resultFound = new AtomicBoolean(false);
    private PuzzleElement[][] solutionBoard;
    private long startTime;



    public PuzzleSolution(Puzzle puzzle1, int numOfThreads) {
        this.puzzleStructure = puzzle1.getAvailableOptionsForSolution();
        this.numOfThreads = numOfThreads;
        counterOfElement = puzzle1.getCounterOfPuzzleElementList();
        availableRowsForSolution = puzzle1.getNumOfRowsForSolution(counterOfElement);
        startTime = System.currentTimeMillis();
    }

    /**
     * initiate bord for available row solution and start the solver
     * @return
     */
    public PuzzleElement[][] solve() throws ExecutionException, InterruptedException {
        if (numOfThreads == 0) {
            numOfThreads = 1;
        }

        System.out.println("number of thread :  " + numOfThreads);

        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        Future future;

        for (int i = 0; i < availableRowsForSolution.size(); i++) {

            if (resultFound.get() == false){

                int r = availableRowsForSolution.get(i);
                int c = counterOfElement / r;

                PuzzleSolver currentThread = new PuzzleSolver(r,c, this);
                future = executorService.submit(() -> currentThread.solve());
            }
        }

        //TODO for debug - remove
        System.out.println("Loop for futures " + System.currentTimeMillis());


        while (!(resultFound.get())) {
//        //TODO - replace the "wait time" (120000 = 2 minutes) for solution from hardcoded to configuration

            if (startTime + 120000 < System.currentTimeMillis()){
                System.out.println("Timer Expired - Retun null for board ");
                executorService.shutdown();
               return null;
            }

        }
        executorService.shutdown();
        return solutionBoard;
    }



    /**
     * solve: recursive function get next plaice in board
     * @param r
     * @param c
     * @return
     */
    protected PuzzleElement[][] solve(int r, int c, PuzzleSolver currentThread) {





        if (isSolved(r,c, currentThread)){
            return currentThread.getBoard();
        }

        int key= currentThread.createKey(r,c);
        List<PuzzleElement> puzzleElements = puzzleStructure.get(key);
        if(puzzleElements==null){
            return null;
        }
        // Try each remaining piece in this square
        for (PuzzleElement p : puzzleElements) {
            if(currentThread.inUse(p)){
                continue;
            }
            if(currentThread.tryInsert(p,r,c)) {
                currentThread.setAsUsed(p);
                Position next = nextPos(r, c,currentThread);
                PuzzleElement[][] solution = solve(next.row, next.column, currentThread);
                if (solution != null && (!(resultFound.get()))) {
                    System.out.println("Solution was found by " + Thread.currentThread().getName() + " @ " + System.currentTimeMillis());
                    resultFound.set(true);
                    solutionBoard = solution;
                    return solution;
                }else {
                    if (!resultFound.get()){
                        currentThread.setAsNotUsed(p);
                    }
                    continue;
                }
            }

        }
        // no solution with this piece
        return null;
    }


    private Position nextPos(int r, int c, PuzzleSolver currentThread) {
        if(c < currentThread.getColumns()-1)  // if can move right
            return new Position(r,c+1);
        return new Position(r+1,0); // move to next row (even if it overflows)
    }

    private boolean isSolved(int r, int c, PuzzleSolver currentThread) {
        return  (c >= currentThread.getColumns() || r >= currentThread.getRows()); // at last position
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