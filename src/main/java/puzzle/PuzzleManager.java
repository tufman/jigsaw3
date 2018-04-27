package puzzle;

/*
@Author Yelena Koviar
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

public class PuzzleManager {

    private PuzzleElement[][] board = null;


    public PuzzleElement[][] manage(String filePath, String filePathToOutput) throws IOException, ExecutionException, InterruptedException {

        Puzzle puzzle1 = new Puzzle();

        WritePuzzleStatus writePuzzleStatus = new WritePuzzleStatus(filePathToOutput);
        if (puzzle1.readInputFile(filePath)) {

            ArrayList<Integer> numOfRowsForSolution = puzzle1.getNumOfRowsForSolution1();

            ExecutorService executorService = Executors.newFixedThreadPool(numOfRowsForSolution.size());
            //Change DummyClass to PuzzleSolver
            Future <PuzzleSolver>future;
            for (int i=0; i<numOfRowsForSolution.size(); i++){
                future = executorService.submit(new Callable(){
                    public Object call() throws Exception {
                        System.out.println("thread ID " );
                        //Thread.sleep(1500);
                        PuzzleSolver puzzleSolver = new PuzzleSolver(puzzle1);
                        puzzleSolver.solve();
                        return puzzleSolver;
                    }
                });
                if (future.get()==null){
                    System.out.println("Solution was not found....");
                }
                if (!(future.get()==null)){
                    System.out.println("Solution Found....");
                    System.out.println("future.get() = " + future.get().solve() );
                }

            }
            System.out.println("Solution!!!");

        }
        return new PuzzleElement[0][];
    }
}


            // TODO: OLD Multi thread Code  -- NEED TO DELETE !!


          //  ExecutorService executorService = Executors.newFixedThreadPool(numOfRowsForSolution.size());
            /*Set<Callable<PuzzleElement[][]>> callables = new HashSet<>();

            for (int i = 0; i < numOfRowsForSolution.size(); i++) {
              //  PuzzleSolver puzzleSolver = new PuzzleSolver(puzzle1, 4);
                callables.add(new Callable<PuzzleElement[][]>() {
                    @Override
                    public PuzzleElement[][] call() throws Exception {
                        //TODO:  repalce "4" with num Of Rows For solution -
                        PuzzleSolver puzzleSolver = new PuzzleSolver(puzzle1, 4);
                        board = puzzleSolver.solve();
                        return board;
                    }
                });

            }
                List<Future<PuzzleElement[][]>> futures = executorService.invokeAll(callables);

                for (Future<PuzzleElement[][]> future : futures) {

                    if (!(future.get() == null)) {
                        break;
                    } else {
                        System.out.println("no solution");
                    }
                }
            } else{
                writePuzzleStatus.WriteErrorsToFile(puzzle1.getErrorsReadingInputFile());
            }
        }*/

//        for (int i = 0; i < numOfRowsForSolution.size(); i++) {
//            future = executorService.submit(new Callable() {
//
//                public Object call() throws Exception {
//                    PuzzleSolver puzzleSolver = new PuzzleSolver(puzzle1, 4);
//                    board = puzzleSolver.solve();
//                    return board;
//
//                }
//            });


