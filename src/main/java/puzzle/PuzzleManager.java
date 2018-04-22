package puzzle;

/*
@Author Yelena Koviar
 */

import java.io.IOException;

public class PuzzleManager {

private PuzzleElement[][] board = null;


public void manage(String filePath, String filePathToOutput) throws IOException {

    Puzzle puzzle1 = new Puzzle();

    WritePuzzleStatus writePuzzleStatus = new WritePuzzleStatus(filePathToOutput);
       if(puzzle1.readInputFile(filePath)) {
           PuzzleSolver puzzleSolver = new PuzzleSolver(puzzle1);
           board = puzzleSolver.solve();
           //write puzzle solution to output file
           writePuzzleStatus.WriteResultToFile(board);
           puzzle1.printSolution();

       }else {
           writePuzzleStatus.WriteErrorsToFile(puzzle1.getErrorsReadingInputFile());
       }
    }

}
