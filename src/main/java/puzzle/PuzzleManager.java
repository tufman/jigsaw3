package puzzle;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuzzleManager {

//    public static void main(String[] args) throws IOException {
private PuzzleElement[][] board = null;


public void manage(String filePath, String filePathToOutput) throws IOException {

        Puzzle puzzle1 = new Puzzle();
    String fileInputPath = System.getProperty("user.dir") + "\\src\\main\\resources\\add\\test2.in";
    String fileOutputPath = System.getProperty("user.dir") + "\\src\\main\\resources\\add\\test2.out";
        //puzzle1.readOutputFile(filePath);
        //puzzle1.isIOSolvable();

    WritePuzzleStatus writePuzzleStatus = new WritePuzzleStatus(filePathToOutput);
       if(puzzle1.readInputFile(filePath)) {
           PuzzleSolver puzzleSolver = new PuzzleSolver(puzzle1);
           board = puzzleSolver.start();

           writePuzzleStatus.WriteResultToFile(board);
           puzzle1.printSolution();

       }else {
           writePuzzleStatus.WriteErrorsToFile(puzzle1.getErrorsReadingInputFile());
       }
    }
}
