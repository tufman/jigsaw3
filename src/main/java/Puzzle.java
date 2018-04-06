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
    private boolean [] puzzleElementIDs;
    //Utils utils = new Utils();


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
                    String errMsg = prop.getProperty("wrongFirstLineFormat");
                    errorsReadingInputFile.add(errMsg + line);
                }

                continue;
            }
            //Validate that a line that represents a PuzzleElement is valid (integers)
            line = line.trim();
            String [] stringsFromLineArr = line.split(" ");
            ArrayList<Integer> numFromLine = new ArrayList<>();
            for (String str : stringsFromLineArr){
                if (str.length() == 0){
                    continue;
                }
                try {

                    numFromLine.add( Integer.parseInt(str));
                }catch (NumberFormatException e ) {
                    String errMsg = prop.getProperty("wrongElementsFormat");
                    errorsReadingInputFile.add(errMsg + line);
                }
            }


            int id = numFromLine.get(0);

            if(numFromLine.size()==5) {
//                int id = numFromLine.get(0);
                if (idInRange(id)) {
                    if (allNumbersInRange(numFromLine)) {
                        PuzzleElement element = new PuzzleElement(numFromLine);
                        puzzleElementList.add(element);
                        //TODO calculate the edges and add it to optionsOfSolution
                        //utils.mapElementToSolutionList(element, puzzleElementList.size()-1);
                        Utils.mapElementToSolutionList(element, puzzleElementList.size() - 1);
                        markExistElement(id);
                        continue;
                    }
                    // left, top, right and bottom between -1 to 1
                    else {
                        //errorsReadingInputFile.add(prop.getProperty("numberNotInRange") + line);
                        wrongElementFormat(id, line);
                    }
                }
                //ID is not in range
                 else{
                    idsForErrorsNotInRange.add(id);
                }

            }
            else{
                wrongElementFormat(id, line);
            }
        }
        //}

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
            //TODO should we stop or throw exception?
        }

        //TODO check if a valid result is available

        //TODO in case (valid result) send puzzleElementList to Find solution


        //this.availableOptionsForSolution = utils.getSolutionMap();
        this.availableOptionsForSolution = Utils.getSolutionMap();
        //initSolutionMap();

        //int [] numOfAvailableLineForSolution = null;
        ArrayList<Integer> numOfAvailableRowsForSolution = Utils.getNumOfRowsForSolution();
        //Call to Solver, only if there are NO Error in the parsing that was executed,
        // and there is at least 1 available row for solution
        // and there are elements in puzzleElementList
        // and there is at least 1 Top Left Corner
        if (errorsReadingInputFile.size() ==0 && numOfAvailableRowsForSolution != null &&
                puzzleElementList != null && availableOptionsForSolution.get(PUZZLEDIRECTIONS.TOP_LEFT_CORNER).size() > 0){
            ArrayList<Integer> numOfRowsForSolution = Utils.getNumOfRowsForSolution();
            PuzzleSolver puzzleSolver = new PuzzleSolver(puzzleElementList, numOfRowsForSolution, availableOptionsForSolution);
        }

        printErrorsFromReadingInputFile();

    }

    private void wrongElementFormat(int id, String line) {
        String errorToAdd = (prop.getProperty("wrongElementsFormat"));
        errorToAdd = errorToAdd.replace("<id>", String.valueOf(id));
        //errorsReadingInputFile.add(prop.getProperty("numberNotInRange") + line);
        errorsReadingInputFile.add(errorToAdd + line);

    }

    private void markExistElement(int id) {
        puzzleElementIDs[id-1] = true;
    }

    private boolean idInRange(Integer idToCheck) {
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


    private boolean allNumbersInRange(ArrayList<Integer> numFromLine) {
        for (int i =1; i < numFromLine.size(); i++){
            if (!(numFromLine.get(i)>=-1 && numFromLine.get(i) <= 1)){
                return false;
            }
        }
        return true;
    }


    public void printListOfElements(){
        System.out.println("### printListOfElements ###");
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
