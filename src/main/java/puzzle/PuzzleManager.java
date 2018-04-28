package puzzle;

/*
@Author Yelena Koviar
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;

public class PuzzleManager {

    private PuzzleElement[][] board = null;
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PuzzleManager{");
        sb.append("board=").append(board == null ? "null" : Arrays.asList(board).toString());
        sb.append('}');
        return sb.toString();
    }

    public PuzzleElement[][] manage(String filePath, String filePathToOutput) throws IOException, ExecutionException, InterruptedException {

        Puzzle puzzle1 = new Puzzle();

        WritePuzzleStatus writePuzzleStatus = new WritePuzzleStatus(filePathToOutput);

        if (puzzle1.readInputFile(filePath)) {

            ArrayList<Integer> numOfRowsForSolution = puzzle1.getNumOfRowsForSolution1();

            final int  numOfThreadsToOpen = numOfRowsForSolution.size();
            //TODO: need to delete the print - only for debug!!
            System.out.println("Num of Threads : "+numOfThreadsToOpen);
            ExecutorService executorService = Executors.newFixedThreadPool(numOfThreadsToOpen);

            for (int i = 0; i < numOfThreadsToOpen; i++) {
                Future<PuzzleElement[][]> future = executorService.submit(new Callable() {
                    public PuzzleElement[][] call() throws Exception {
                        System.out.println("Starting...");
                        System.out.println(Thread.currentThread().getId());
                        //Todo : need to find a way to insert the numofrows to the inner class.-- DONE!
                        PuzzleSolver puzzleSolver = new PuzzleSolver(puzzle1, numOfThreadsToOpen);
                        board = puzzleSolver.solve();
                        Thread.sleep(4000);
                        return board;
                    }
                });
                executorService.shutdown();
                //todo: consider using thread local
                if (future.get() == null) {
                    System.out.println("Result is Empty : " + future.get());
                }
                if (!(future.get() == null)) {

                    System.out.println("Result is : " + future.get());
                    System.out.println("Is Thread Completed :" + future.isDone());
                    System.out.println(board);
                    break;

                }
            }
            System.out.println("Solution!!!");

        }
        else
            writePuzzleStatus.WriteResultToFile(board);
        return board;
    }
}



