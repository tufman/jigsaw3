import java.io.IOException;

public class PuzzleMain {

    public static void main(String[] args) throws IOException {

        //String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\main\\resources\\Good4Pieces";
        //String filePath = "C:\\GitRepository\\jigsaw1\\src\\\\main\\resources\\BadInputFile20Pieces";
        String filePath = "C:\\GitRepository\\jigsaw1\\src\\\\main\\resources\\oded.in";
       // String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\main\\resources\\Good16Pieces";
       // WritePuzzleStatus  writePuzzleStatus = new WritePuzzleStatus("C:\\GitRepository\\jigsaw1\\src\\main\\resources\\Results");
       // writePuzzleStatus.WriteErrorsToFile();
        //String filePath = "C:\\GitRepository\\jigsaw1\\src\\main\\resources\\Good16Pieces";

        Puzzle puzzle = new Puzzle();
        puzzle.readInputFile(filePath);

        //if errors of solver - discuss with Yelena ==> print to file

        //Print solve matrix to  file

        puzzle.printErrorsFromReadingInputFile();
//        System.out.println("Get board frompuzzle solution");
//        PuzzleElement[][] board = PuzzleSolver.start();

        System.out.println("#############################");
        System.out.println("#############################");
        System.out.println("##         Solution        ##");
         puzzle.printSolution();

//        for (int ii = 0; ii <= board.length - 1; ii++) {
//            for (int jj = 0; jj <= board[0].length - 1; jj++) {
//                System.out.print("[" + board[ii][jj] + "],");
//            }
//            System.out.println();
//        }



    }
}
