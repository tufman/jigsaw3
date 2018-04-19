package puzzle;

import java.io.IOException;

public class PuzzleManager {

private PuzzleElement[][] board = null;


public void manage(String filePath, String filePathToOutput) throws IOException {

    Puzzle puzzle1 = new Puzzle();
    String fileInputPath = System.getProperty("user.dir") + "\\src\\main\\resources\\add\\test2.in";
    String fileOutputPath = System.getProperty("user.dir") + "\\src\\main\\resources\\add\\test2.out";
    puzzle1.readOutputFile(fileOutputPath);
//    puzzle1.isIOSolvable();

    WritePuzzleStatus writePuzzleStatus = new WritePuzzleStatus(filePathToOutput);
       if(puzzle1.readInputFile(filePath)) {
           PuzzleSolver puzzleSolver = new PuzzleSolver(puzzle1);
           board = puzzleSolver.solve();
           //write puzzle solution to output file
           writePuzzleStatus.WriteResultToFile(board);
           //sout
           puzzle1.printSolution();

       }else {
           writePuzzleStatus.WriteErrorsToFile(puzzle1.getErrorsReadingInputFile());
       }
//    System.out.println("the output puzzle is correct: "+ puzzle1.isIOSolvable());
    }

}
