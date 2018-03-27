import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Puzzle {

    private String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\main\\resources\\inputFile";
    private int expectedNumOfElementsFromFirstLine;
    private List<PuzzleElement> jigsawElementList = new ArrayList<>();
    private List<String> errorsReadingInputFile = new ArrayList<>();
    Properties prop = null;

    public Puzzle(){
    }

//    public Puzzle(){
//        this.filePath = filePath;
//    }

//    public Puzzle(String filePath){
//        this.filePath = filePath;
//
//    }

    public void readInputFile(String filePath) throws IOException {
        try(FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr)){

            initConfiguration();

            readDataFromFile(br);

        }
    }

    private void readDataFromFile(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null){
            if (line.charAt(0) == '#'){
                continue;
            }
            if(line.contains("NumElements")){
                String [] numElementArr = line.split("=");
                expectedNumOfElementsFromFirstLine = Integer.parseInt(numElementArr[1].trim());
                continue;
            }
            String [] stringsFromLineArr = line.split(" ");
            ArrayList<Integer> numFromLine = new ArrayList<>();
            for (String str : stringsFromLineArr){
                try {
                    numFromLine.add( Integer.parseInt(str));
                }catch (NumberFormatException e ) {
                    errorsReadingInputFile.add(prop.getProperty("wrongElementsFormat"));

                }

            }

            if(numFromLine.size()==5) {
                // PuzzleElement element = new PuzzleElement(numFromLine[0], numFromLine[1], numFromLine[2],numFromLine[3],numFromLine[4]);
                PuzzleElement element = new PuzzleElement(numFromLine);
                jigsawElementList.add(element);
            }
        }

        if (expectedNumOfElementsFromFirstLine != jigsawElementList.size()){
            errorsReadingInputFile.add(prop.getProperty("missingElementInConfigurationFile"));
            //TODO should we stop or throw exception?
        }

        //TODO check if a valid result is available

        //TODO in case (valid result) send jigsawElementList to Find solution

        Map<String, List<Integer>> cornersMap = new HashMap<>();
        int [] numOfAvailableLineForSolution = null;
        JigsawSolver jigsawSolver = new JigsawSolver(jigsawElementList, numOfAvailableLineForSolution,cornersMap);
    }


        public void printListOfElements(){
            for (PuzzleElement element: jigsawElementList){
                System.out.println(element);
            }


        for (String errorMsg : errorsReadingInputFile){
            System.out.println(errorMsg);
        }

    }

    private void initConfiguration() throws IOException {
        GetPropertyValues properties = new GetPropertyValues();
        prop = properties.getPropValues();

        System.out.println("####################################");
        System.out.println("Existing Errors in config.properties");
        System.out.println("####################################");
        prop.forEach((key, value) -> System.out.println(key + " : " + value));
    }

    public int getNumOfElementsFromFirstLine(){
        return expectedNumOfElementsFromFirstLine;
    }


    public boolean verifyErrorExistInList(String error){
        return errorsReadingInputFile.contains(error);
    }

    public PuzzleElement getElementByIndex(int index) {
        return jigsawElementList.get(index);
    }

    public int getActualNumOfElementsReadFromInputFile(){
        return jigsawElementList.size();
    }

}
