import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReadFileContentTest {

    @ParameterizedTest
    @CsvSource({"NumElements=22,22", "NumElements =23, 23", " NumElements =24,24", "NumElements = 25,25", "NumElements = 26 ,26"})
    @DisplayName("Read number of Elements with Spaces")
    //@Test
    public void validNumOfElement(String textToWrite, int expectedVal) throws IOException {
        //String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\inputFileTest";
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\inputFileTest";

        try(FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osr = new OutputStreamWriter(fos)){
            osr.write(textToWrite);
        }


        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        assertEquals(expectedVal,readFileContent.getNumOfElementsFromFirstLine());


    }


    @ParameterizedTest
    @CsvSource({"NumElements=AAA,22"})
    @DisplayName("Invalid Num Of PuzzleElement -> throws Exception")
    //@Test
    public void notvalidNumOfElement(String textToWrite, int expectedVal) throws IOException {
        //String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\inputFileTest";
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\inputFileTest";

        try(FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osr = new OutputStreamWriter(fos)){
            osr.write(textToWrite);
        }

        Puzzle readFileContent = new Puzzle();

        assertThrows(NumberFormatException.class, () ->{
            readFileContent.readInputFile(filePath);
        });
    }

    @ParameterizedTest
    //@CsvSource({"1 -1 0 1 1,1,-1,0,1,1", "3 1 1 1 1,3 ,1 ,1 ,1 ,1", "6 -1 -1 -1 -1,6 ,-1 ,-1 ,-1 ,-1", "1 0 0 0 0,1 ,0 ,0 ,0 ,0"})
    @CsvSource({"1 -1 0 1 1,1,-1,0,1,1"})
    @DisplayName("Init one Elemnt from file ")
    //@Test
    public void validLineRepresentingElement(String elementRepresentation, int id, int left, int top, int right, int bottom) throws IOException {
        //String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\inputFileTest";
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\inputFileTest";

        try(FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osr = new OutputStreamWriter(fos)){
            osr.write(elementRepresentation);
        }

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(-1);
        ids.add(0);
        ids.add(1);
        ids.add(1);
        //PuzzleElement expectedElement = new PuzzleElement(id, left, top, right, bottom);
        PuzzleElement expectedElement = new PuzzleElement(ids);

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        PuzzleElement responseElement = readFileContent.getElementByIndex(0);

        assertTrue(expectedElement.equals(responseElement));
    }

//    @ParameterizedTest
//    @CsvSource({"inputFileTestWithDiaz,4"})
//    @DisplayName("Ignore lines with #")
//    public void validIgnoreLinesWithDiaz(String inputFile, int expectedValidElements) throws IOException {
//        String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\" + inputFile;
//
//        Puzzle readFileContent = new Puzzle(filePath);
//        readFileContent.readInputFile();
//
//        assertTrue(readFileContent.getActualNumOfElementsReadFromInputFile() == expectedValidElements);
//    }

    @ParameterizedTest
    @CsvSource({"validInputFileTestElementsAsExpected"})
    @DisplayName("Valid num of elements in file is as expected and Ignore lines with #")
    public void validNumOfElementsInFileAsExpectedInNumElements(String inputFile) throws IOException {
        //String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\" + inputFile;
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);

        assertTrue(readFileContent.getActualNumOfElementsReadFromInputFile() == readFileContent.getNumOfElementsFromFirstLine());
    }

    @ParameterizedTest
    @CsvSource({"notValidinputFileTestElementsAsExpected"})
    @DisplayName("NOT Valid num of elements in file is as expected and Ignore lines with #")
    public void notValidNumOfElementsInFileAsExpectedInNumElements(String inputFile) throws IOException {
        //String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\" + inputFile;
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);

        //verify that message exist in list of errors from Puzzle
        //assertTrue(readFileContent.getActualNumOfElementsReadFromInputFile() == readFileContent.getNumOfElementsFromFirstLine());
        assertTrue(readFileContent.verifyErrorExistInList("Missing puzzle element(s) with the following ID's: TBD elevant ID's"));
    }

    @ParameterizedTest
    @CsvSource({"validInputFileTestElementsAsExpected"})
    @DisplayName("print All Elemens From List")
    public void printAllElemensFromList(String inputFile) throws IOException {
        //String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\" + inputFile;
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        readFileContent.printListOfElements();
        assertTrue(readFileContent.getActualNumOfElementsReadFromInputFile() == readFileContent.getNumOfElementsFromFirstLine());
    }



    @ParameterizedTest
    @CsvSource({"invalid_IDElement_input"})
    @DisplayName("Validate element in input file - not int")
    public void validateInvalidIDElementInput (String inputFile) throws IOException {
        //String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\" + inputFile;
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        readFileContent.printListOfElements();
        assertTrue(readFileContent.verifyErrorExistInList("Puzzle ID <id> has wrong data: <complete line from file including ID>"));
    }


    @ParameterizedTest
    @CsvSource({"LTRBValidation"})
    @DisplayName("Left Top right and Bottom not in range -1 to 1")
    public void validateTheLTRBHasCorrectValues (String inputFile) throws IOException {
        //String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\" + inputFile;
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        readFileContent.printListOfElements();


        assertTrue(readFileContent.verifyErrorExistInList("L/T/R/B Number should be between -1 to 1 please fix line 1 2 1 1 1"));
        assertTrue(readFileContent.verifyErrorExistInList("L/T/R/B Number should be between -1 to 1 please fix line 2 0 -2 0 0"));
        assertTrue(readFileContent.verifyErrorExistInList("L/T/R/B Number should be between -1 to 1 please fix line 3 -1 1 2 1"));
        assertTrue(readFileContent.verifyErrorExistInList("L/T/R/B Number should be between -1 to 1 please fix line 4 1 0 1 -2"));
    }

    @ParameterizedTest
    @CsvSource({"validateEmptyLineAndSpaces"})
    @DisplayName("ignore Empty Lines and Lines with Spaces")
    public void validateFileWithEmptyLineAndSpaces (String inputFile) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        readFileContent.printListOfElements();

        assertTrue(readFileContent.getActualNumOfElementsReadFromInputFile() == readFileContent.getNumOfElementsFromFirstLine());
    }

    //@ParameterizedTest
    //@CsvSource({"SolvePuzzleWith2Pieces_All_Zero"})
    @DisplayName("Find Solution for 2 Puzzle Elements - All Zero")
    @Test
    public void SolvePuzzleWith2Pieces () throws IOException {

        //Create Elements
        ArrayList<Integer> tempElement1 = new ArrayList<>();
        ArrayList<Integer> tempElement2 = new ArrayList<>();

        tempElement1.add(1);
        tempElement1.add(0);
        tempElement1.add(0);
        tempElement1.add(0);
        tempElement1.add(0);

        tempElement2.add(2);
        tempElement2.add(0);
        tempElement2.add(0);
        tempElement2.add(0);
        tempElement2.add(0);

        PuzzleElement element1 = new PuzzleElement(tempElement1);
        PuzzleElement element2 = new PuzzleElement(tempElement2);

        //Add the Elements to a List -
        // This is teh 1st argument in PuzzleSolver Construction
        List<PuzzleElement> puzzleElementList = new ArrayList<>();
        puzzleElementList.add(element1);
        puzzleElementList.add(element2);


        //Build the array of int that contains the available num of rows for the solution
        //This is the 2nd argument in PuzzleSolver Construction
        int [] numOfAvailableLineForSolution = {1,2};


        //Build a Map that will contains all the possiblities that exist
        //The keys will be:
        //TOP_LEFT_CORNER
        //BOTTOM_LEFT_CORNER
        //TOP_RIGHT_CORNER
        //BOTTOM_RIGHT_CORNER
        //LEFT_0
        //LEFT_1
        //LEFT_-1
        //TOP_0
        //TOP_1
        //TOP_-1
        //RIGHT_0
        //RIGHT_1
        //RIGHT_-1
        //BOTTOM_0
        //BOTTOM_1
        //BOTTOM_-1

        //The value will be the location in the List (Parameter 1)
        Map<String, List<Integer>> availableOptionsForSolution = new HashMap<>();
        List<Integer> topLeftCornerList = new ArrayList<>();
        List<Integer> bottomLeftCornerList = new ArrayList<>();
        List<Integer> topRightCornerList = new ArrayList<>();
        List<Integer> bottomRightCornerList = new ArrayList<>();

        topLeftCornerList.add(0);
        topLeftCornerList.add(1);

        bottomLeftCornerList.add(0);
        bottomLeftCornerList.add(1);

        topRightCornerList.add(0);
        topRightCornerList.add(1);

        bottomRightCornerList.add(0);
        bottomRightCornerList.add(1);

        List<Integer> leff_0 = new ArrayList<>();
        List<Integer> left_Minus = new ArrayList<>();
        List<Integer> left_1 = new ArrayList<>();

        leff_0.add(0);
        leff_0.add(1);

        List<Integer> top_0 = new ArrayList<>();
        List<Integer> top_Minus = new ArrayList<>();
        List<Integer> top_1 = new ArrayList<>();

        top_0.add(0);
        top_0.add(1);

        List<Integer> right_0 = new ArrayList<>();
        List<Integer> right_Minus = new ArrayList<>();
        List<Integer> right_1 = new ArrayList<>();

        right_0.add(0);
        right_0.add(1);

        List<Integer> bottom_0 = new ArrayList<>();
        List<Integer> bottom_Minus = new ArrayList<>();
        List<Integer> bottom_1 = new ArrayList<>();

        bottom_0.add(0);
        bottom_0.add(1);

        availableOptionsForSolution.put("TOP_LEFT_CORNER", topLeftCornerList);
        availableOptionsForSolution.put("BOTTOM_LEFT_CORNER", bottomLeftCornerList);
        availableOptionsForSolution.put("TOP_RIGHT_CORNER", topRightCornerList);
        availableOptionsForSolution.put("BOTTOM_RIGHT_CORNER", bottomRightCornerList);

        availableOptionsForSolution.put("LEFT_0", leff_0);
        availableOptionsForSolution.put("TOP_0", top_0);
        availableOptionsForSolution.put("RIGHT_0", right_0);
        availableOptionsForSolution.put("BOTTOM_0", bottom_0);

        //Map<String, List<Integer>> cornersMap = new HashMap<>();
        List<Integer> listOfInteger = new ArrayList<>();
        listOfInteger.add(1);
        //cornersMap.put("TopLeft", listOfInteger);

        PuzzleSolver puzzleSolver = new PuzzleSolver(puzzleElementList, numOfAvailableLineForSolution, availableOptionsForSolution);


    }

    @ParameterizedTest
    @CsvSource({"validTestForUtilsGetSolutionMap"})
    @DisplayName("Validate Utils Solution Map")
    //@Test
    public void validTestForUtilsGetSolutionMap (String inputFile) throws IOException {
    //public void validTestForUtilsGetSolutionMap () throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;
        //String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\validTestForUtilsGetSolutionMap";

        Utils.claenSolutionMap();

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        readFileContent.printListOfElements();

        Map<String, List<Integer>> solutionMap = Utils.getSolutionMap();

        assertTrue(solutionMap.get("TOP_LEFT_CORNER").size() == 1);
        assertTrue(solutionMap.get("TOP_LEFT_CORNER").get(0) == 0);

        assertTrue(solutionMap.get("LEFT_PLUS").size() == 2, String.format("LEFT_PLUS size -> Expected %d, actuall %d",2,solutionMap.get("LEFT_PLUS").size()));
        assertTrue(solutionMap.get("LEFT_PLUS").get(0) == 2, String.format("LEFT_PLUS(0) -> Expected %d, actuall %d",2,solutionMap.get("LEFT_PLUS").get(0)));
        assertTrue(solutionMap.get("LEFT_PLUS").get(1) == 4, String.format("LEFT_PLUS(1) -> Expected %d, actuall %d",4,solutionMap.get("LEFT_PLUS").get(1)));


        System.out.println("My Break");

    }

}
