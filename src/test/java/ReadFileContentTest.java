import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @DisplayName("Invalid Num Of PuzzleElement -> throws Exception")
    public void notValidNumOfElement() throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\7";


        Puzzle puzzle = new Puzzle();

        puzzle.readInputFile(filePath);

        assertTrue(puzzle.verifyErrorExistInList("ERROR: NUm Of Elements is not valid NumElements=AAA,22"));
        assertTrue(puzzle.verifyErrorExistInList("ERROR: NUm Of Elements is not valid NumElements=AAA"));
        assertTrue(puzzle.verifyErrorExistInList("ERROR: Missing puzzle element(s) with the following IDs: 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,"));

    }

//    @ParameterizedTest
//    //@CsvSource({"1 -1 0 1 1,1,-1,0,1,1", "3 1 1 1 1,3 ,1 ,1 ,1 ,1", "6 -1 -1 -1 -1,6 ,-1 ,-1 ,-1 ,-1", "1 0 0 0 0,1 ,0 ,0 ,0 ,0"})
//    @CsvSource({"1 -1 0 1 1,1,-1,0,1,1"})
//    @DisplayName("Init one Elemnt from file ")
//    //@Test
//    public void validLineRepresentingElement(String elementRepresentation, int id, int left, int top, int right, int bottom) throws IOException {
//        //String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\inputFileTest";
//        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\inputFileTest";
//
//        try(FileOutputStream fos = new FileOutputStream(filePath);
//            OutputStreamWriter osr = new OutputStreamWriter(fos)){
//            osr.write(elementRepresentation);
//        }
//
//        ArrayList<Integer> ids = new ArrayList<>();
//        ids.add(1);
//        ids.add(-1);
//        ids.add(0);
//        ids.add(1);
//        ids.add(1);
//        //PuzzleElement expectedElement = new PuzzleElement(id, left, top, right, bottom);
//        PuzzleElement expectedElement = new PuzzleElement(ids);
//
//        Puzzle readFileContent = new Puzzle();
//        readFileContent.readInputFile(filePath);
//        PuzzleElement responseElement = readFileContent.getElementByIndex(0);
//
//        assertTrue(expectedElement.equals(responseElement));
//    }

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
    @CsvSource({"1"})
    @DisplayName("Valid num of elements in file is as expected and Ignore lines with #")
    public void validNumOfElementsInFileAsExpectedInNumElements(String inputFile) throws IOException {
        //String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\" + inputFile;
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);

        //assertTrue(readFileContent.getActualNumOfElementsReadFromInputFile() == readFileContent.getNumOfElementsFromFirstLine());
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle of size <4> cannot have the following IDs: 6,8,"));
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Missing puzzle element(s) with the following IDs: 1,3,"));


    }

    @ParameterizedTest
    @CsvSource({"2"})
    @DisplayName("NOT Valid num of elements in file is as expected and Ignore lines with #")
    public void notValidNumOfElementsInFileAsExpectedInNumElements(String inputFile) throws IOException {
        //String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\" + inputFile;
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);

        //assertTrue(readFileContent.verifyErrorExistInList("Number of puzzle elements does not fit the actual that exist in the file."));

        //readFileContent.printErrorsFromReadingInputFile();

        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle of size <5> cannot have the following IDs: 6,8,"));
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Missing puzzle element(s) with the following IDs: 1,3,5,"));
    }

    @ParameterizedTest
    @CsvSource({"1"})
    @DisplayName("print All Elemens From List")
    public void printAllElemensFromList(String inputFile) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        readFileContent.printListOfElements();
        assertTrue(readFileContent.getActualNumOfElementsReadFromInputFile() != readFileContent.getNumOfElementsFromFirstLine());

        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle of size <4> cannot have the following IDs: 6,8,"));
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Missing puzzle element(s) with the following IDs: 1,3,"));
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


        readFileContent.printErrorsFromReadingInputFile();
    }


    @ParameterizedTest
    @CsvSource({"4"})
    @DisplayName("Left Top right and Bottom not in range -1 to 1")
    public void validateTheLTRBHasCorrectValues (String inputFile) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        readFileContent.printListOfElements();


        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle ID 1 has wrong data: 1 2 1 1 1"));
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle ID 2 has wrong data: 2 0 -2 0 0"));
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle ID 3 has wrong data: 3 -1 1 2 1"));
        assertTrue(readFileContent.verifyErrorExistInList("ERROR: Puzzle ID 4 has wrong data: 4 1 0 1 -2"));

        readFileContent.printErrorsFromReadingInputFile();
    }

    @ParameterizedTest
    @CsvSource({"5"})
    @DisplayName("ignore Empty Lines and Lines with Spaces")
    public void validateFileWithEmptyLineAndSpaces (String inputFile) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        readFileContent.printListOfElements();

        assertTrue(readFileContent.getActualNumOfElementsReadFromInputFile() == readFileContent.getNumOfElementsFromFirstLine());
    }

    @ParameterizedTest
    @CsvSource({"8"})
    @DisplayName("ID's must be in range")
    public void idNotInRange (String inputFile) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        //readFileContent.printListOfElements();

        //assertTrue(readFileContent.getActualNumOfElementsReadFromInputFile() == readFileContent.getNumOfElementsFromFirstLine());
    }


    @ParameterizedTest
    @CsvSource({"8"})
    @DisplayName("folderNotFound")

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
    @DisplayName("Validate Utils Solution Map")
    public void validTestForUtilsGetSolutionMap (String inputFile) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\java\\" + inputFile;

        Utils.claenSolutionMap();

        Puzzle readFileContent = new Puzzle();
        readFileContent.readInputFile(filePath);
        readFileContent.printListOfElements();

        Map<Enum, List<Integer>> solutionMap = Utils.getSolutionMap();

        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.TOP_LEFT_CORNER).size() == 1);
        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.TOP_LEFT_CORNER).get(0) == 0);

        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.LEFT_PLUS).size() == 2, String.format("LEFT_PLUS size -> Expected %d, actuall %d",2,solutionMap.get(PUZZLEDIRECTIONS.LEFT_PLUS).size()));
        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.LEFT_PLUS).get(0) == 2, String.format("LEFT_PLUS(0) -> Expected %d, actuall %d",2,solutionMap.get(PUZZLEDIRECTIONS.LEFT_PLUS).get(0)));
        assertTrue(solutionMap.get(PUZZLEDIRECTIONS.LEFT_PLUS).get(1) == 4, String.format("LEFT_PLUS(1) -> Expected %d, actuall %d",4,solutionMap.get(PUZZLEDIRECTIONS.LEFT_PLUS).get(1)));


        System.out.println("My Break");

    }

}
