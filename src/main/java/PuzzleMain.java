import java.io.IOException;

public class PuzzleMain {

    public static void main(String[] args) throws IOException {

        String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\main\\resources\\inputFile";
        //String filePath = "C:\\t1\\t1.txt";
        Puzzle puzzle = new Puzzle();
        puzzle.readInputFile(filePath);
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
