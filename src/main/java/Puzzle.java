import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Puzzle {

    private String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\main\\resources\\inputFile";
    private int expectedNumOfElementsFromFirstLine;
    private List<PuzzleElement> puzzleElementList = new ArrayList<>();
    private List<String> errorsReadingInputFile = new ArrayList<>();
    Map<String, List<Integer>> availableOptionsForSolution = new HashMap<>();

    //I think it will be easier to fill the list as part of the file reading,
    // and in the end before we will send it to Solver, to put it in the relevant Map
    List<Integer> leftPlus = new ArrayList<>();
    List<Integer> leftZero = new ArrayList<>();
    List<Integer> leftMinus = new ArrayList<>();
    List<Integer> topPlus = new ArrayList<>();
    List<Integer> topZero = new ArrayList<>();
    List<Integer> topMinus = new ArrayList<>();
    List<Integer> rightPlus = new ArrayList<>();
    List<Integer> rightZero = new ArrayList<>();
    List<Integer> rightMinus = new ArrayList<>();
    List<Integer> bottomPlus = new ArrayList<>();
    List<Integer> bottomZero = new ArrayList<>();
    List<Integer> bottomMinus = new ArrayList<>();
    List<Integer> topLeftCorner = new ArrayList<>();
    List<Integer> bottomLeftCorner = new ArrayList<>();
    List<Integer> topRightCorner = new ArrayList<>();
    List<Integer> bottomRightCorner = new ArrayList<>();

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
            System.out.println("line: " + line);
            if (line.trim().length() == 0){
                continue;
            }

            if (line.charAt(0) == '#'){
                continue;
            }

            if(line.contains("NumElements")){
                String [] numElementArr = line.split("=");
                expectedNumOfElementsFromFirstLine = Integer.parseInt(numElementArr[1].trim());
                continue;
            }
            //Validate that a line that represents a PuzzleElement is valid (integers)
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
                //Validate that a Left, Top, Right & Bottom between -1 to 1
                if (allNumbersInRange(numFromLine)){
                    PuzzleElement element = new PuzzleElement(numFromLine);
                    puzzleElementList.add(element);
                    //TODO calculate the edges and add it to optionsOfSolution
                    addOptionsToSolution(element, puzzleElementList.size()-1);
                    continue;
                }else{
                    errorsReadingInputFile.add(prop.getProperty("numberNotInRange") + line);
                }
            }
        }

        if (expectedNumOfElementsFromFirstLine != puzzleElementList.size()){
            errorsReadingInputFile.add(prop.getProperty("missingElementInConfigurationFile"));
            //TODO should we stop or throw exception?
        }

        //TODO check if a valid result is available

        //TODO in case (valid result) send puzzleElementList to Find solution

        
        initSolutionMap();

        int [] numOfAvailableLineForSolution = null;
        PuzzleSolver puzzleSolver = new PuzzleSolver(puzzleElementList, numOfAvailableLineForSolution, availableOptionsForSolution);
    }

    private void initSolutionMap() {
        availableOptionsForSolution.put("TOP_LEFT_CORNER", topLeftCorner);
        availableOptionsForSolution.put("BOTTOM_LEFT_CORNER", bottomLeftCorner);
        availableOptionsForSolution.put("TOP_RIGHT_CORNER", topRightCorner);
        availableOptionsForSolution.put("BOTTOM_RIGHT_CORNER", bottomRightCorner);

        availableOptionsForSolution.put("LEFT_0", leftZero);
        availableOptionsForSolution.put("LEFT_PLUS", leftPlus);
        availableOptionsForSolution.put("LEFT_MINUS", leftMinus);

        availableOptionsForSolution.put("TOP_0", topZero);
        availableOptionsForSolution.put("TOP_PLUS", topPlus);
        availableOptionsForSolution.put("TOP_MINUS", topMinus);

        availableOptionsForSolution.put("RIGHT_0", rightZero);
        availableOptionsForSolution.put("RIGHT_PLUS", rightPlus);
        availableOptionsForSolution.put("RIGHT_MINUS", rightMinus);

        availableOptionsForSolution.put("BOTTOM_0", bottomZero);
        availableOptionsForSolution.put("BOTTOM_PLUS", bottomPlus);
        availableOptionsForSolution.put("BOTTOM_MINUS", bottomMinus);
    }

    private void addOptionsToSolution(PuzzleElement element, int indexInPuzzleElementList) {
        //Map<String, List<Integer>> availableOptionsForSolution
        switch(element.left){
            case -1:  {
                leftMinus.add(indexInPuzzleElementList);
                break;
            }
            case 0: {
                leftZero.add(indexInPuzzleElementList);
                break;
            }
            case 1: {
                leftPlus.add(indexInPuzzleElementList);
                break;
            }
            default: //Currently do nothing
        }
        switch(element.top){
            case -1:  {
                topMinus.add(indexInPuzzleElementList);
                break;
            }
            case 0: {
                topZero.add(indexInPuzzleElementList);
                break;
            }
            case 1: {
                topPlus.add(indexInPuzzleElementList);
                break;
            }
            default: //Currently do nothing
        }
        switch(element.right){
            case -1:  {
                rightMinus.add(indexInPuzzleElementList);
                break;
            }
            case 0: {
                rightZero.add(indexInPuzzleElementList);
                break;
            }
            case 1: {
                rightPlus.add(indexInPuzzleElementList);
                break;
            }
            default: //Currently do nothing
        }
        switch(element.bottom){
            case -1:  {
                bottomMinus.add(indexInPuzzleElementList);
                break;
            }
            case 0: {
                bottomZero.add(indexInPuzzleElementList);
                break;
            }
            case 1: {
                bottomPlus.add(indexInPuzzleElementList);
                break;
            }
            default: //Currently do nothing
        }
        if (element.left == 0 && element.top == 0){
            topLeftCorner.add(indexInPuzzleElementList);
        }
        if (element.left == 0 && element.bottom == 0){
            bottomLeftCorner.add(indexInPuzzleElementList);
        }
        if (element.right == 0 && element.top == 0){
            topRightCorner.add(indexInPuzzleElementList);
        }
        if (element.right == 0 && element.bottom == 0){
            bottomRightCorner.add(indexInPuzzleElementList);
        }
    }

    private boolean allNumbersInRange(ArrayList<Integer> numFromLine) {
        for (int i =1; i < numFromLine.size(); i++){
            if (!(numFromLine.get(i)>=-1 && numFromLine.get(i) <= 1)){
                return false;
            }
        }
        return true;
    }


    public void printListOfElements(){
            for (PuzzleElement element: puzzleElementList){
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
        return puzzleElementList.get(index);
    }

    public int getActualNumOfElementsReadFromInputFile(){
        return puzzleElementList.size();
    }

}
