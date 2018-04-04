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

    //Utils utils = new Utils();


    Properties prop = null;

    public Puzzle(){
    }


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
                try{
                    expectedNumOfElementsFromFirstLine = Integer.parseInt(numElementArr[1].trim());
                }catch (NumberFormatException e ){
                    String errMsg = prop.getProperty("wrongFirstLineFormat");
                    errorsReadingInputFile.add(errMsg + line);
                }

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
                    //utils.mapElementToSolutionList(element, puzzleElementList.size()-1);
                    Utils.mapElementToSolutionList(element, puzzleElementList.size()-1);

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

    public void printErrorsFromReadingInputFile() {
        System.out.println("----------------------------------");
        System.out.println("--- All Errors from Input File ---");
        if (errorsReadingInputFile.size() > 0){
            for (String error : errorsReadingInputFile){
                System.out.println(error);
            }
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
