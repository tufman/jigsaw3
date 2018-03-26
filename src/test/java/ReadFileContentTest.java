import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    @DisplayName("Read number of Elements")
    //@Test
    public void validNumOfElement(String textToWrite, int expectedVal) throws IOException {
        String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\inputFileTest";

        try(FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osr = new OutputStreamWriter(fos)){
            osr.write(textToWrite);
        }


        ReadFileContent readFileContent = new ReadFileContent(filePath);
        readFileContent.readContentFromFile();
        assertEquals(expectedVal,readFileContent.getNumOfElements());


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
            readFileContent.readContentFromFile();
        });
    }

    @ParameterizedTest
    //@CsvSource({"1 -1 0 1 1,1,-1,0,1,1", "3 1 1 1 1,3 ,1 ,1 ,1 ,1", "6 -1 -1 -1 -1,6 ,-1 ,-1 ,-1 ,-1", "1 0 0 0 0,1 ,0 ,0 ,0 ,0"})
    @CsvSource({"1 -1 0 1 1,1,-1,0,1,1"})
    @DisplayName("Get Element ")
    //@Test
    public void validLineRepresentingElement(String elementRepresentation, int id, int left, int top, int right, int bottom) throws IOException {
        String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\test\\java\\inputFileTest";

        try(FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osr = new OutputStreamWriter(fos)){
            osr.write(elementRepresentation);
        }

        Element expectedElement = new Element(id, left, top, right, bottom);

        ReadFileContent readFileContent = new ReadFileContent(filePath);
        readFileContent.readContentFromFile();
        Element responseElement = readFileContent.getFirstElement();

        assertTrue(expectedElement.equals(responseElement));
    }


}
