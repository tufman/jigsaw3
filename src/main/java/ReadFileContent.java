import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadFileContent {
    String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\main\\resources\\inputFile";
    int numElementsFromFirstLine;
    List<Element> jigsawElementList = new ArrayList<>();

    public ReadFileContent(){
        this.filePath = filePath;
    }

    public ReadFileContent(String filePath){
        this.filePath = filePath;
    }

    public void readContentFromFile() throws IOException {
        try(FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr)){

            String line;
            while ((line = br.readLine()) != null){
                if (line.charAt(0) == '#'){
                    continue;
                }
                if(line.contains("NumElements")){
                    String [] numElementArr = line.split("=");
                    //String tempValue = numElementArr[1].trim();
                    numElementsFromFirstLine = Integer.parseInt(numElementArr[1].trim());
                    continue;
                }
                String [] stringsFromLineArr = line.split(" ");
                int [] numFromLine = new int[stringsFromLineArr.length];
                int startLocation = 0;
                for (String str : stringsFromLineArr){
                    numFromLine[startLocation] = Integer.parseInt(str);
                    startLocation++;
                }
                Element element = new Element(numFromLine[0], numFromLine[1], numFromLine[2],numFromLine[3],numFromLine[4]);
                jigsawElementList.add(element);
            }
        }
    }

    public int getNumOfElementsFromFirstLine(){
        return numElementsFromFirstLine;
    }

    public Element getFirstElement() {
        return jigsawElementList.get(0);
    }

    public int getActualNumOfElementsReadFromInputFile(){
        return jigsawElementList.size();
    }

    //Comment

    ///
    ///
    ///



    //
}
