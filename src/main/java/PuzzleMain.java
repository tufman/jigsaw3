import java.io.IOException;

public class PuzzleMain {

    public static void main(String[] args) throws IOException {

        // Our Test files
        //String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\Good4Pieces";     //  --- works expected
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\Good16Pieces";    //  --- works expected
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\BadInputFile20Pieces";
        //  String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\SolverReturnNULL.in";  // -- works expected

        // Amir - Simple Files (*.in )
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\simple\\test1.in";    // -- works as expected
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\simple\\test2.in";   // -- works fine ???
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\simple\\test3.in";   // -- works as expected
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\simple\\test4.in";   // should work return error
        String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\simple\\test5.in";


        Puzzle puzzle = new Puzzle();
        puzzle.readInputFile(filePath);


        puzzle.printErrorsFromReadingInputFile();
//        System.out.println("Get board from puzzle solution");
//        PuzzleElement[][] board = PuzzleSolver.start();

        System.out.println("#############################");
        System.out.println("#############################");
        System.out.println("##         Solution        ##");
         puzzle.printSolution();


    }
}
