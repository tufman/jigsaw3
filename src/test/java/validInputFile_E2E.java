//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class validInputFile_E2E {
//
//
//    @ParameterizedTest
//    @CsvSource({"validFourPuzzleElementsForSolver"})
//    @DisplayName("Validate Utils Solution Map - all options")
//    public void validTestForUtilsGetSolutionMap (String inputFile) throws IOException {
//        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;
//
//        Utils.claenSolutionMap();
//
//        Puzzle readFileContent = new Puzzle();
//        readFileContent.readInputFile(filePath);
//        readFileContent.printListOfElements();
//
//        Map<Enum, List<Integer>> solutionMap = Utils.getSolutionMap();
//
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.TOP_LEFT_CORNER).size() == 1);
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.TOP_LEFT_CORNER).get(0) == 0);
//
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_LEFT_CORNER).size() == 2);
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_LEFT_CORNER).get(0) == 2);
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_LEFT_CORNER).get(1) == 3);
//
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.TOP_RIGHT_CORNER).size() == 1);
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.TOP_RIGHT_CORNER).get(0) == 1);
//
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_RIGHT_CORNER).size() == 2);
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_RIGHT_CORNER).get(0) == 2);
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_RIGHT_CORNER).get(1) == 3);
////LEFT
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.LEFT_ZERO).size() == 3, String.format("LEFT_ZERO size -> Expected %d, actuall %d",3,solutionMap.get(PUZZLEDIRECTIONS.LEFT_ZERO).size()));
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.LEFT_ZERO).get(0) == 0, String.format("LEFT_ZERO(0) -> Expected %d, actuall %d",0,solutionMap.get(PUZZLEDIRECTIONS.LEFT_ZERO).get(0)));
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.LEFT_ZERO).get(1) == 2, String.format("LEFT_ZERO(1) -> Expected %d, actuall %d",2,solutionMap.get(PUZZLEDIRECTIONS.LEFT_ZERO).get(1)));
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.LEFT_ZERO).get(2) == 3, String.format("LEFT_ZERO(2) -> Expected %d, actuall %d",3,solutionMap.get(PUZZLEDIRECTIONS.LEFT_ZERO).get(2)));
//
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.LEFT_PLUS).size() == 1, String.format("LEFT_PLUS size -> Expected %d, actuall %d",1,solutionMap.get(PUZZLEDIRECTIONS.LEFT_PLUS).size()));
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.LEFT_PLUS).get(0) == 1, String.format("LEFT_PLUS(0) -> Expected %d, actuall %d",1,solutionMap.get(PUZZLEDIRECTIONS.LEFT_PLUS).get(0)));
//
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.LEFT_MINUS).size() == 0, String.format("LEFT_MINUS size -> Expected %d, actuall %d",0,solutionMap.get(PUZZLEDIRECTIONS.LEFT_MINUS).size()));
//
////TOP
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.TOP_ZERO).size() == 2, String.format("TOP_ZERO size -> Expected %d, actuall %d",2,solutionMap.get(PUZZLEDIRECTIONS.TOP_ZERO).size()));
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.TOP_ZERO).get(0) == 0, String.format("TOP_ZERO(0) -> Expected %d, actuall %d",0,solutionMap.get(PUZZLEDIRECTIONS.TOP_ZERO).get(0)));
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.TOP_ZERO).get(1) == 1, String.format("TOP_ZERO(1) -> Expected %d, actuall %d",1,solutionMap.get(PUZZLEDIRECTIONS.TOP_ZERO).get(1)));
//
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.TOP_PLUS).size() == 1, String.format("TOP_PLUS size -> Expected %d, actuall %d",1,solutionMap.get(PUZZLEDIRECTIONS.TOP_PLUS).size()));
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.TOP_PLUS).get(0) == 3, String.format("TOP_PLUS(0) -> Expected %d, actuall %d",3,solutionMap.get(PUZZLEDIRECTIONS.TOP_PLUS).get(0)));
//
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.TOP_MINUS).size() == 1, String.format("TOP_MINUS size -> Expected %d, actuall %d",1,solutionMap.get(PUZZLEDIRECTIONS.TOP_MINUS).size()));
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.TOP_MINUS).get(0) == 2, String.format("TOP_MINUS(0) -> Expected %d, actuall %d",2,solutionMap.get(PUZZLEDIRECTIONS.TOP_MINUS).get(0)));
//
////RIGHT
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.RIGHT_ZERO).size() == 3, String.format("RIGHT_ZERO size -> Expected %d, actuall %d",3,solutionMap.get(PUZZLEDIRECTIONS.RIGHT_ZERO).size()));
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.RIGHT_ZERO).get(0) == 1, String.format("RIGHT_ZERO(0) -> Expected %d, actuall %d",1,solutionMap.get(PUZZLEDIRECTIONS.RIGHT_ZERO).get(0)));
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.RIGHT_ZERO).get(1) == 2, String.format("RIGHT_ZERO(1) -> Expected %d, actuall %d",2,solutionMap.get(PUZZLEDIRECTIONS.RIGHT_ZERO).get(1)));
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.RIGHT_ZERO).get(2) == 3, String.format("RIGHT_ZERO(2) -> Expected %d, actuall %d",3,solutionMap.get(PUZZLEDIRECTIONS.RIGHT_ZERO).get(2)));
//
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.RIGHT_PLUS).size() == 0, String.format("RIGHT_PLUS size -> Expected %d, actuall %d",0,solutionMap.get(PUZZLEDIRECTIONS.RIGHT_PLUS).size()));
//
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.RIGHT_MINUS).size() == 1, String.format("RIGHT_MINUS size -> Expected %d, actuall %d",1,solutionMap.get(PUZZLEDIRECTIONS.RIGHT_MINUS).size()));
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.RIGHT_MINUS).get(0) == 0, String.format("RIGHT_MINUS(0) -> Expected %d, actuall %d",0,solutionMap.get(PUZZLEDIRECTIONS.RIGHT_MINUS).get(0)));
//
////BOTTOM
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_ZERO).size() == 2, String.format("BOTTOM_ZERO size -> Expected %d, actuall %d",2,solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_ZERO).size()));
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_ZERO).get(0) == 2, String.format("BOTTOM_ZERO(0) -> Expected %d, actuall %d",3,solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_ZERO).get(0)));
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_ZERO).get(1) == 3, String.format("BOTTOM_ZERO(1) -> Expected %d, actuall %d",3,solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_ZERO).get(1)));
//
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_PLUS).size() == 1, String.format("BOTTOM_PLUS size -> Expected %d, actuall %d",1,solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_PLUS).size()));
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_PLUS).get(0) == 0, String.format("BOTTOM_PLUS(0) -> Expected %d, actuall %d",0,solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_PLUS).get(0)));
//
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_MINUS).size() == 1, String.format("BOTTOM_MINUS size -> Expected %d, actuall %d",1,solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_MINUS).size()));
//        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_MINUS).get(0) == 1, String.format("BOTTOM_MINUS(0) -> Expected %d, actuall %d",1,solutionMap.get(PUZZLEDIRECTIONS.BOTTOM_MINUS).get(0)));
//
//        //System.out.println("My Break");
//
//    }
//
//}
