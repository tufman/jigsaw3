import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import puzzle.Puzzle;
import puzzle.PuzzleDirections;
import puzzle.PuzzleMapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReadFileContentTest {

    @ParameterizedTest
    @CsvSource({"NumElements=22,22", "NumElements =23, 23", " NumElements =24,24", "NumElements = 25,25", "NumElements = 26 ,26"})
    @DisplayName("Read number of Elements with Spaces")
    public void validNumOfElement(String textToWrite, int expectedVal) throws IOException {
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
    @CsvSource({"7"})
    @DisplayName("Invalid Num Element")
    public void notValidNumOfElement(String inputFile) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle puzzle = new Puzzle();

        puzzle.readInputFile(filePath);

        assertTrue(puzzle.verifyErrorExistInList("ERROR: Num Of Elements is not valid NumElements=AAA,22"));
        assertTrue(puzzle.verifyErrorExistInList("ERROR: Num Of Elements is not valid NumElements=AAA"));
        assertTrue(puzzle.verifyErrorExistInList("ERROR: Missing puzzle element(s) with the following IDs: 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22"));
    }




    @ParameterizedTest
    @CsvSource({"2"})
    @DisplayName("Puzzle Element ID out of range")
    public void notValidNumOfElementsInFileAsExpectedInNumElements(String inputFile) throws IOException {

        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);


        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle of size <5> cannot have the following IDs: 6,8,9"));
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle ID <id> has wrong data: 9 1 0 1 -   1"));
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Missing puzzle element(s) with the following IDs: 1,3,5"));
    }




    @ParameterizedTest
    @CsvSource({"3"})
    @DisplayName("Validate element in input file - not int, range and num of Edges")
    public void validateInvalidIDElementInput (String inputFile) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        //readFileContent.printListOfElements();
        //assertTrue(readFileContent.verifyErrorExistInList("Please correct this line (should contain int ) D 1 1 1 1"));
        //Range 1 to -1
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle ID 8 has wrong data: 8 1 1 1 -2"));
        //Puzzle ID
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle ID <id> has wrong data: D 1 1 1 1"));
        //5 edges
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle ID 10 has wrong data: 10 1 1 1 1 1"));
        //3 edges
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle ID 11 has wrong data: 11 1 1 1"));



        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Missing puzzle element(s) with the following IDs: 1,2,3,4,5,6,9"));

        readFileContent.printListOfElements();

        readFileContent.printErrorsFromReadingInputFile();
    }

    @ParameterizedTest
    @CsvSource({"1"})
    @DisplayName("Missing Corners")
    public void missingCorners (String inputFile) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;
        PuzzleMapper puzzleMapper = new PuzzleMapper();
//        puzzleMapper.claenSolutionMap();

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        //readFileContent.printListOfElements();


        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Cannot solve puzzle: missing corner element: TL"));
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Cannot solve puzzle: missing corner element: TR"));
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Cannot solve puzzle: missing corner element: BL"));
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Cannot solve puzzle: missing corner element: BR"));


    }

    @ParameterizedTest
    @CsvSource({"1"})
    @DisplayName("Missing Straight Edges")
    public void missingStraightEdges (String inputFile) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        PuzzleMapper puzzleMapper = new PuzzleMapper();
//        puzzleMapper.claenSolutionMap();

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);

        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Cannot solve puzzle: wrong number of straight edges"));

    }


    @ParameterizedTest
    @CsvSource({"4"})
    @DisplayName("Edge Range -1 to 1")
    public void validateTheLTRBHasCorrectValues (String inputFile) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        readFileContent.printListOfElements();


        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle ID 1 has wrong data: 1 2 1 1 1"));
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle ID 2 has wrong data: 2 0 -2 0 0"));
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle ID 3 has wrong data: 3 -1 1 2 1"));
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle ID 4 has wrong data: 4 1 0 1 -2"));
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle ID -1 has wrong data: -1 0 0 0 0"));

    }

    @ParameterizedTest
    @CsvSource({"5"})
    @DisplayName("Ignore: Empty Lines, Lines with Spaces and  with #")
    public void validateFileWithEmptyLineAndSpaces (String inputFile) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        readFileContent.printListOfElements();

        assertTrue(readFileContent.getActualNumOfElementsReadFromInputFile() == 8);
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle of size <4> cannot have the following IDs: 8,6"));
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Missing puzzle element(s) with the following IDs: 1,3"));
    }

    @ParameterizedTest
    @CsvSource({"8"})
    @DisplayName("ID's not in NumElement range")
    public void idNotInRange (String inputFile) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        //readFileContent.printListOfElements();

        //assertTrue(readFileContent.getActualNumOfElementsReadFromInputFile() == readFileContent.getNumOfElementsFromFirstLine());
    }

    @ParameterizedTest
    @CsvSource({"9"})
    @DisplayName("Sum of all edges is not Zero")
    public void sumOfEdgesIsNotZero (String inputFile) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        //readFileContent.printListOfElements();

        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Sum of all edges is not zero."));
    }

    @ParameterizedTest
    @CsvSource({"8"})
    @DisplayName("Folder/File Not Found")

    public void folderNotFound (String inputFile) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java1\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        try {
            readFileContent.readInputFile(filePath);
        }catch (IOException e ) {

        }


    }





    @ParameterizedTest
    @CsvSource({"6"})
    @DisplayName("Validate PuzzleMapper Solution Map")
    public void validTestForUtilsGetSolutionMap (String inputFile) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        PuzzleMapper puzzleMapper = new PuzzleMapper();
        //puzzleMapper.claenSolutionMap();

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        readFileContent.printListOfElements();


        //Map<PuzzleDirections, List<Integer>> solutionMap = puzzleMapper.getSolutionMap();
//        Map<PuzzleDirections, List<Integer>> solutionMap = readFileContent.getSolutionMap();

//        assertTrue(solutionMap.get(PuzzleDirections.TOP_LEFT_CORNER).size() == 1);
//        assertTrue(solutionMap.get(PuzzleDirections.TOP_LEFT_CORNER).get(0) == 1);
//
//        assertTrue(solutionMap.get(PuzzleDirections.LEFT_PLUS).size() == 2, String.format("LEFT_PLUS size -> Expected %d, actuall %d",2,solutionMap.get(PuzzleDirections.LEFT_PLUS).size()));
//        assertTrue(solutionMap.get(PuzzleDirections.LEFT_PLUS).get(0) == 3, String.format("LEFT_PLUS(0) -> Expected %d, actuall %d",2,solutionMap.get(PuzzleDirections.LEFT_PLUS).get(0)));
//        assertTrue(solutionMap.get(PuzzleDirections.LEFT_PLUS).get(1) == 5, String.format("LEFT_PLUS(1) -> Expected %d, actuall %d",4,solutionMap.get(PuzzleDirections.LEFT_PLUS).get(1)));


    }

    @Ignore
    @ParameterizedTest
    @CsvSource({"test2.in, test2.out"})
    @DisplayName("I/O is solution")

    public void verifyOutputFileIsSolutionForInput (String in, String out) throws IOException {
        String fileInputPath = System.getProperty("user.dir") + "\\src\\main\\resources\\add\\" +in;//System.getProperty("user.dir") + "\\src\\main\\resources\\add\\test2.in";
        String fileOutputPath = System.getProperty("user.dir") + "\\src\\main\\resources\\add\\" +out;//System.getProperty("user.dir") + "\\src\\main\\resources\\add\\test2.out";
        Puzzle puzzle = new Puzzle();
        puzzle.readOutputFile(fileOutputPath);
        puzzle.readInputFile(fileInputPath);
        assertTrue(puzzle.isIOSolvable(), "output file is not solution for input");

    }
    @Ignore
    @ParameterizedTest
    @CsvSource({"test9.in, test9.out"})
    @DisplayName("I/O is not solution")

    public void verifyOutputFileIsNotSolutionForInput (String in, String out) throws IOException {
        String fileInputPath = System.getProperty("user.dir") + "\\src\\main\\resources\\add\\" +in;//System.getProperty("user.dir") + "\\src\\main\\resources\\add\\test2.in";
        String fileOutputPath = System.getProperty("user.dir") + "\\src\\main\\resources\\add\\" +out;//System.getProperty("user.dir") + "\\src\\main\\resources\\add\\test2.out";
        Puzzle puzzle = new Puzzle();
        puzzle.readOutputFile(fileOutputPath);
        puzzle.readInputFile(fileInputPath);
        assertFalse(puzzle.isIOSolvable(), "output file is solution for input");

    }

}
