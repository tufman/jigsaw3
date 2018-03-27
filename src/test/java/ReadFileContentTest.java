import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

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

        PuzzleElement expectedElement = new PuzzleElement(id, left, top, right, bottom);

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


}
