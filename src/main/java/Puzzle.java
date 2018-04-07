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
    private Map<Enum, List<Integer>> availableOptionsForSolution = new HashMap<>();
    private List<Integer> idsForErrorsNotInRange = new ArrayList<>();
    private ArrayList<Integer> splittedLineToInt = new ArrayList<>();
    private boolean [] puzzleElementIDs;


    Properties prop = null;

    public Puzzle(){
    }


    public void readInputFile(String filePath) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
        } catch (IOException e) {
            e.getMessage();
            System.out.println("Puzzle Fail to Init");
        }
        if (fis == null) {
            return;
        }
        try (InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr)) {
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
                try{
                    expectedNumOfElementsFromFirstLine = Integer.parseInt(numElementArr[1].trim());
                    puzzleElementIDs = new boolean [expectedNumOfElementsFromFirstLine];
                }catch (NumberFormatException e ){
                    addErrorWrongFirstLine(line);
                }

                continue;
            }
            line = line.trim();
            String [] lineToArray = line.split(" ");
            splittedLineToInt.clear();
            for (String str : lineToArray){
                if (str.length() == 0){
                    continue;
                }
                try {
                    splittedLineToInt.add( Integer.parseInt(str));
                }catch (NumberFormatException e ) {
                    //TODO - make it more elegant ...
                    addErrorWrongElementFormat(-9999, line);
                }
            }


            int id = splittedLineToInt.get(0);

            if(splittedLineToInt.size()==5) {
//                int id = numFromLine.get(0);
                if (verifyIdInRange(id)) {
                    if (verifyAllEdgesInRange(splittedLineToInt)) {
                        PuzzleElement element = new PuzzleElement(splittedLineToInt);
                        puzzleElementList.add(element);
                        //TODO calculate the edges and add it to optionsOfSolution
                        //utils.mapElementToSolutionList(element, puzzleElementList.size()-1);
                        Utils.mapElementToSolutionList(element, puzzleElementList.size() - 1);
                        markExistElement(id);
                        continue;
                    }
                    // left, top, right and bottom between -1 to 1
                    else {
                        addErrorWrongElementFormat(id, line);
                    }
                }
                //ID is not in range
                 else{
                    addIDToNotInRangeList(id);
                }

            }
            //Num of edges is not 4 (id + 4 edges)
            else{
                addErrorWrongElementFormat(id, line);
            }
        }


        if (idsForErrorsNotInRange.size() > 0){
            String wrongElementID = prop.getProperty("wrongElementIDs");
            wrongElementID = wrongElementID.replace("SIZE", String.valueOf(expectedNumOfElementsFromFirstLine));
            for (int i = 0; i < idsForErrorsNotInRange.size(); i++){
                wrongElementID += idsForErrorsNotInRange.get(i) + ",";
            }
            errorsReadingInputFile.add(wrongElementID);
        }

        if (expectedNumOfElementsFromFirstLine != puzzleElementList.size()){
            String allIDs = "";
            for (int i = 0; i < puzzleElementIDs.length; i++){
                if (!(puzzleElementIDs[i])){
                    allIDs += (i+1) + ",";
                }
            }
            String errorToAdd = (prop.getProperty("missingPuzzleElements"));
            errorToAdd += allIDs;
            errorsReadingInputFile.add(errorToAdd);
        }




        this.availableOptionsForSolution = Utils.getSolutionMap();
        verifyAllCornersExist();

        ArrayList<Integer> numOfAvailableRowsForSolution = Utils.getNumOfRowsForSolution();

        if (errorsReadingInputFile.size() ==0 && numOfAvailableRowsForSolution != null &&
                puzzleElementList != null && availableOptionsForSolution.get(PUZZLEDIRECTIONS.TOP_LEFT_CORNER).size() > 0){
            ArrayList<Integer> numOfRowsForSolution = Utils.getNumOfRowsForSolution();
            PuzzleSolver puzzleSolver = new PuzzleSolver(puzzleElementList, numOfRowsForSolution, availableOptionsForSolution);
        }

        printErrorsFromReadingInputFile();

    }

    private void verifyAllCornersExist() {
        String error = prop.getProperty("missingCorner");
        if (this.availableOptionsForSolution.get(PUZZLEDIRECTIONS.TOP_LEFT_CORNER).size() == 0){
            String errorTopLeftCorner = error.replace("<>", "TL");
            errorsReadingInputFile.add(errorTopLeftCorner);
        }
        if (this.availableOptionsForSolution.get(PUZZLEDIRECTIONS.TOP_RIGHT_CORNER).size() == 0){
            String errorTopRightCorner = error.replace("<>", "TR");
            errorsReadingInputFile.add(errorTopRightCorner);
        }
        if (this.availableOptionsForSolution.get(PUZZLEDIRECTIONS.BOTTOM_LEFT_CORNER).size() == 0){
            String errorBottomLeftCorner = error.replace("<>", "BL");
            errorsReadingInputFile.add(errorBottomLeftCorner);
        }
        if (this.availableOptionsForSolution.get(PUZZLEDIRECTIONS.BOTTOM_RIGHT_CORNER).size() == 0){
            String errorBottomRight = error.replace("<>", "BR");
            errorsReadingInputFile.add(errorBottomRight);
        }

    }

    private void addErrorWrongFirstLine(String line) {
        String errMsg = prop.getProperty("wrongFirstLineFormat");
        errorsReadingInputFile.add(errMsg + line);
    }

    private void addIDToNotInRangeList(int id) {
        idsForErrorsNotInRange.add(id);
    }

    private void addErrorWrongElementFormat(int id, String line) {
        String errorToAdd = (prop.getProperty("wrongElementsFormat"));
        if (!(id == -9999)){
            String errorToAdd1 = errorToAdd.replace("<id>", String.valueOf(id));
            errorsReadingInputFile.add(errorToAdd1 + line);
        }else{
            errorsReadingInputFile.add(errorToAdd + line);
        }


    }

    private void markExistElement(int id) {
        puzzleElementIDs[id-1] = true;
    }

    private boolean verifyIdInRange(Integer idToCheck) {
        return idToCheck <= expectedNumOfElementsFromFirstLine;
    }

    public void printErrorsFromReadingInputFile() {
        System.out.println("----------------------------------");
        System.out.println("--- All Errors from Input File ---");
        if (errorsReadingInputFile.size() > 0){
            for (String error : errorsReadingInputFile){
                System.out.println(error);
            }
        }
        else{
            System.out.println("NO ERRORS");
        }
    }


    private boolean verifyAllEdgesInRange(ArrayList<Integer> numFromLine) {
        for (int i =1; i < numFromLine.size(); i++){
            if (!(numFromLine.get(i)>=-1 && numFromLine.get(i) <= 1)){
                return false;
            }
        }
        return true;
    }


    public void printListOfElements(){
        System.out.println("----------------------------------");
        System.out.println("----   printListOfElements   ----");
            for (PuzzleElement element: puzzleElementList){
                System.out.println(element);
            }
    }

    private void initConfiguration() throws IOException {
        GetPropertyValues properties = new GetPropertyValues();
        prop = properties.getPropValues();

        System.out.println("--------------------------------------------");
        System.out.println("--- Existing Errors in config.properties ---");
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
