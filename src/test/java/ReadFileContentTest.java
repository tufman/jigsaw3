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
        String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\inputFileTest";

        try(FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osr = new OutputStreamWriter(fos)){
            osr.write(textToWrite);
        }


        ReadFileContent readFileContent = new ReadFileContent(filePath);
        readFileContent.readInputFile();
        assertEquals(expectedVal,readFileContent.getNumOfElementsFromFirstLine());


    }


    @ParameterizedTest
    @CsvSource({"NumElements=AAA,22"})
    @DisplayName("Invalid Num Of Element -> throws Exception")
    //@Test
    public void notvalidNumOfElement(String textToWrite, int expectedVal) throws IOException {
        String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\inputFileTest";

        try(FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osr = new OutputStreamWriter(fos)){
            osr.write(textToWrite);
        }

        ReadFileContent readFileContent = new ReadFileContent(filePath);

        assertThrows(NumberFormatException.class, () ->{
            readFileContent.readInputFile();
        });
    }

    @ParameterizedTest
    //@CsvSource({"1 -1 0 1 1,1,-1,0,1,1", "3 1 1 1 1,3 ,1 ,1 ,1 ,1", "6 -1 -1 -1 -1,6 ,-1 ,-1 ,-1 ,-1", "1 0 0 0 0,1 ,0 ,0 ,0 ,0"})
    @CsvSource({"1 -1 0 1 1,1,-1,0,1,1"})
    @DisplayName("Init one Elemnt from file ")
    //@Test
    public void validLineRepresentingElement(String elementRepresentation, int id, int left, int top, int right, int bottom) throws IOException {
        String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\inputFileTest";

        try(FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osr = new OutputStreamWriter(fos)){
            osr.write(elementRepresentation);
        }

        Element expectedElement = new Element(id, left, top, right, bottom);

        ReadFileContent readFileContent = new ReadFileContent(filePath);
        readFileContent.readInputFile();
        Element responseElement = readFileContent.getFirstElement();

        assertTrue(expectedElement.equals(responseElement));
    }

//    @ParameterizedTest
//    @CsvSource({"inputFileTestWithDiaz,4"})
//    @DisplayName("Ignore lines with #")
//    public void validIgnoreLinesWithDiaz(String inputFile, int expectedValidElements) throws IOException {
//        String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\" + inputFile;
//
//        ReadFileContent readFileContent = new ReadFileContent(filePath);
//        readFileContent.readInputFile();
//
//        assertTrue(readFileContent.getActualNumOfElementsReadFromInputFile() == expectedValidElements);
//    }

    @ParameterizedTest
    @CsvSource({"validInputFileTestElementsAsExpected"})
    @DisplayName("Valid num of elements in file is as expected and Ignore lines with #")
    public void validNumOfElementsInFileAsExpectedInNumElements(String inputFile) throws IOException {
        String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\" + inputFile;

        ReadFileContent readFileContent = new ReadFileContent(filePath);
        readFileContent.readInputFile();

        assertTrue(readFileContent.getActualNumOfElementsReadFromInputFile() == readFileContent.getNumOfElementsFromFirstLine());
    }

    @ParameterizedTest
    @CsvSource({"notValidinputFileTestElementsAsExpected"})
    @DisplayName("NOT Valid num of elements in file is as expected and Ignore lines with #")
    public void notValidNumOfElementsInFileAsExpectedInNumElements(String inputFile) throws IOException {
        String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\" + inputFile;

        ReadFileContent readFileContent = new ReadFileContent(filePath);
        readFileContent.readInputFile();

        //verify that message exist in list of errors from ReadFileContent
        assertTrue(readFileContent.getActualNumOfElementsReadFromInputFile() == readFileContent.getNumOfElementsFromFirstLine());
    }

    @ParameterizedTest
    @CsvSource({"validInputFileTestElementsAsExpected"})
    @DisplayName("print All Elemens From List")
    public void printAllElemensFromList(String inputFile) throws IOException {
        String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\" + inputFile;

        ReadFileContent readFileContent = new ReadFileContent(filePath);
        readFileContent.readInputFile();
        readFileContent.printListOfElements();
        assertTrue(readFileContent.getActualNumOfElementsReadFromInputFile() == readFileContent.getNumOfElementsFromFirstLine());
    }


}
