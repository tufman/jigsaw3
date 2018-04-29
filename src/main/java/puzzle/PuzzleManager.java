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


    public PuzzleElement[][] manage(String filePath, String filePathToOutput) throws IOException, ExecutionException, InterruptedException {

        Puzzle puzzle1 = new Puzzle();

        WritePuzzleStatus writePuzzleStatus = new WritePuzzleStatus(filePathToOutput);

        if (puzzle1.readInputFile(filePath)) {

            ArrayList<Integer> numOfRowsForSolution = puzzle1.getNumOfRowsForSolution1();

            int numOfThreadsToOpen = numOfRowsForSolution.size();

            ExecutorService executorService = Executors.newFixedThreadPool(numOfThreadsToOpen);

            for (int i = 0; i < numOfRowsForSolution.size(); i++) {
                int finalI = i;
                Future<PuzzleElement[][]> future = executorService.submit(new Callable() {
                    public PuzzleElement[][] call() throws Exception {
                        PuzzleSolver puzzleSolver = new PuzzleSolver(puzzle1, numOfRowsForSolution.get(finalI));
                        board = puzzleSolver.solve();
                        return board;
                    }
                });
                try {
                    //todo: consider using thread local
                    if (future.get() == null) {
                        System.out.println("Result is Empty : " + future.get());
                    }

                    if (!(future.get() == null)) {

                        //System.out.println("Result is : " + future.get());
                        System.out.println("Result is : " + printBoardResult(future.get()));
                        break;

                    }
                } catch (InterruptedException e) {

                }

            }
            executorService.shutdown();

        } else
            writePuzzleStatus.WriteResultToFile(board);
        return board;
    }

    private String printBoardResult(PuzzleElement[][] puzzleElements) {
        String retVal = "";
        for (int i =0; i < puzzleElements.length; i++){
            for (int j=0; j< puzzleElements[0].length; j++){
                retVal += "[" + puzzleElements[i][j].getId() + "]";
            }
        }
        return retVal;
    }
}



