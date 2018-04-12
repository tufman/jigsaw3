package puzzle;

import java.io.IOException;

public class PuzzleMain {



    public static void main(String[] args) throws IOException {

     String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\Good16Pieces";//System.getProperty("user.dir")+"\\src\\\\main\\resources\\Good16Pieces";    //  --- works expected


     Puzzle puzzle1 = new Puzzle();
     puzzle1.readInputFile(filePath);
     PuzzleSolver puzzleSolver = new PuzzleSolver(puzzle1);
     PuzzleElement[][] board = null;
     board = puzzleSolver.start();
     String filePathToSave = System.getProperty("user.dir") + "\\src\\main\\resources\\result.txt";
     WritePuzzleStatus writePuzzleStatus = new WritePuzzleStatus(filePathToSave);
     writePuzzleStatus.WriteResultToFile(board);
     puzzle1.printSolution();

    }
}
