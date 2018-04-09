import java.io.IOException;

public class PuzzleMain {

    public static void main(String[] args) throws IOException {

        // Our Test files
        //String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\Good4Pieces";     //  --- works expected
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\Good16Pieces";    //  --- works expected
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\BadInputFile20Pieces";
        //  String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\SolverReturnNULL.in";  // -- works expected


        // Amir - Additional  Files (*.in )
        String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test1.in";     // -- works as expected
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test2.in";     // -- works fine ???
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test3.in";     // -- works as expected
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test4.in";     // should work return error
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test5.in";     // should return error - input invalid ? ?   -has wrong data
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test6.in";     // -- works as expected
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test7.in";     //  -- works as expected ?? SHOULD BE MORE THAN 1 ERROR
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test8.in";     // fail !! need to fix validation - double writing & invalid Error
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test9.in";     // -- works as expected
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test10.in";    // -- works as expected * PRINTS HORIZONTAL
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test11.in";    // -- works as expected * NO SOLUTION
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test12.in";    // -- works as expected * CHECK ORDER?
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test13.in";    // -- works as expected * NO SOLUTION
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test14.in";    // -- works as expected
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test15.in";    // -- works as expected * NO SOLUTION
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test16.in";    // -- works as expected * NO SOLUTION
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test17.in";    // -- works as expected * NO SOLUTION
        //String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\add\\test18.in";      // -- works as expected * NO SOLUTION


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
