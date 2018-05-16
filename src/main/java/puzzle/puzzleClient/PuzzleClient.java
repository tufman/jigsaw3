/**
 * The main logic of reading the input file, and validate it.
 * Once the file is valid, it will map the input lines to Puzzle Elements by using the PuzzleMapper
 *
 * Author:
 * Shay Tufman

 * */

package puzzle.puzzleClient;

import puzzle.GetPuzzleErrors;
import puzzle.PuzzleElement;
import puzzle.PuzzleMapper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutorService;

public class PuzzleClient {

    private int expectedNumOfElementsFromFirstLine;
    private List<PuzzleElement> puzzleElementList = new ArrayList<>();
    private int countElement=0;
    private Stack<ArrayList<Integer>> stackOfGoodLines = new Stack<>();

    public List<String> getErrorsReadingInputFile() {
        return errorsReadingInputFile;
    }

    private List<String> errorsReadingInputFile = new ArrayList<>();
    private Map<Integer, List<PuzzleElement>> availableOptionsForSolution = new HashMap<>();
    private List<Integer> idsForErrorsNotInRange = new ArrayList<>();
    private ArrayList<Integer> splittedLineToInt = new ArrayList<>();
    private Map<Integer, List<Integer>> puzzleOutput = new HashMap<>();

    private boolean[] puzzleElementIDs;
    private ArrayList<Integer> numOfRowsForSolution;
    private PuzzleMapper puzzleMapper = new PuzzleMapper();

    private Properties prop = null;

    //Params from the command line for the Client
    private String serverIp;
    private String serverPort;
    private String filePath;
    private String filePathToSave;
    private boolean isRotation;
    public boolean allClientParamsExist;


    public PuzzleClient() {
    }

    public boolean isAllClientParamsExist() {
        return allClientParamsExist;
    }

    public boolean readInputFile(String filePath, boolean isRotation, boolean isMultiThread, int numOfThreads) throws IOException {
        this.isRotation = isRotation;


        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
        } catch (IOException e) {
            e.getMessage();
            System.out.println("Puzzle Fail to Init");
        }
        if (fis == null) {
            return false;
        }
        try (InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            initConfiguration();
            readDataFromFile(br);
        }
        return true;//errorsReadingInputFile.isEmpty();
    }


    private void readDataFromFile(BufferedReader br) throws IOException {
        System.out.println("--------------------------------------------");
        System.out.println("---         Data from  input file        ---");
        System.out.println("--------------------------------------------");
        String line;
        while ((line = br.readLine()) != null) {
            splittedLineToInt = new ArrayList<>();
            System.out.println("line: " + line);
            if (line.trim().length() == 0) {
                continue;
            }

            if (line.charAt(0) == '#') {
                continue;
            }

            if (line.contains("NumElements")) {
                extractNumOfElements(line);
                continue;
            }
            parseLineFromFileToIntArr(line);
            int id = splittedLineToInt.get(0);
            if (id < 1) {
                addErrorWrongElementFormat(id, line);
                continue;
            }
            if (splittedLineToInt.size() == 5) {
                if (verifyIdInRange(id)) {
                    if (verifyAllEdgesInRange(splittedLineToInt)) {
                        stackOfGoodLines.push(splittedLineToInt);
                        markExistElement(splittedLineToInt.get(0));
                    }
                    // left, top, right and bottom between -1 to 1
                    else {
                        addErrorWrongElementFormat(id, line);
                    }
                }
                //ID is not in range
                else {
                    addIDToNotInRangeList(id);
                }
            }
            //Num of edges is not 4 (id + 4 edges)
            else {
                addErrorWrongElementFormat(id, line);
            }
        }

        if (idsForErrorsNotInRange.size() > 0) {
            addErrorForIDsNotInRange();
        }
        if ((expectedNumOfElementsFromFirstLine) != stackOfGoodLines.size()) {
            addErrorMissingPuzzleElements();
        }
        if (stackOfGoodLines.size() > 0) {
            createAndMapPuzzleElements();
            this.availableOptionsForSolution = puzzleMapper.getPuzzleStructure();
            verifyAtLeastOneLineAvailable();
            verifyAllCornersExist();
            verifySumZero();
        }
    }

    private void createAndMapPuzzleElements() {
        ExecutorService executor = null;
            System.out.println("Indexing by Single Thread");
            while (stackOfGoodLines.size() != 0) {
                indexingPuzzle();
            }
    }

    private void indexingPuzzle() {
        //stackOfGoodLines.pop() is synchronaized by java....
        // puzzleMapper.addElementToStructure(element) is synchronized - keep it.
        // addPuzzleElementToList(element) is synchronized - keep it.

        ArrayList<Integer> popedLineFromStack = stackOfGoodLines.pop();

         int x = 0;
        //insert element with 0 rotation
        PuzzleElement element = new PuzzleElement(popedLineFromStack, x);
        puzzleMapper.addElementToStructure(element);
        addPuzzleElementToList(element);
        countElement++;
        if (element.getSumOfEdges() == 1111 || element.getSumOfEdges() == -1111 || element.getSumOfEdges() == 0) {
            x = 0;
        } else if (element.getTop() == element.getBottom() && element.getLeft() == element.getRight()) {
            x = 2;
        } else x = 4;

        if (isRotation){
            for (int rotate = 1; rotate < x; rotate++) {
                element = new PuzzleElement(popedLineFromStack, rotate);
                addPuzzleElementToList(element);
                puzzleMapper.addElementToStructure(element);
            }
        }
    }


    private synchronized void addPuzzleElementToList(PuzzleElement element) {
        puzzleElementList.add(element);
    }

    private void addErrorMissingPuzzleElements() {
        String allIDs = "";
        for (int i = 0; i < puzzleElementIDs.length; i++) {
            if (!(puzzleElementIDs[i])) {
                allIDs += (i + 1) + ",";
            }
        }
        String errorToAdd = (prop.getProperty("missingPuzzleElements"));
        allIDs = allIDs.substring(0, allIDs.length() - 1);
        errorToAdd += allIDs;
        errorsReadingInputFile.add(errorToAdd);
    }

    private void addErrorForIDsNotInRange() {
        String wrongElementID = prop.getProperty("wrongElementIDs");
        wrongElementID = wrongElementID.replace("SIZE", String.valueOf(expectedNumOfElementsFromFirstLine));
        for (int i = 0; i < idsForErrorsNotInRange.size(); i++) {
            if (i == (idsForErrorsNotInRange.size() - 1)) {
                wrongElementID += idsForErrorsNotInRange.get(i);
                continue;
            }
            wrongElementID += idsForErrorsNotInRange.get(i) + ",";
        }
        errorsReadingInputFile.add(wrongElementID);
    }

    private void parseLineFromFileToIntArr(String line) {
        line = line.trim();
        String[] lineToArray = line.split(" ");
        splittedLineToInt.clear();
        for (String str : lineToArray) {
            if (str.length() == 0) {
                continue;
            }
            try {
                splittedLineToInt.add(Integer.parseInt(str));
            } catch (NumberFormatException e) {
                //TODO - make it more elegant ...
                addErrorWrongElementFormat(-9999, line);
            }
        }
    }

    private void extractNumOfElements(String line) {
        String[] numElementArr = line.split("=");
        try {
            expectedNumOfElementsFromFirstLine = Integer.parseInt(numElementArr[1].trim());
            puzzleElementIDs = new boolean[expectedNumOfElementsFromFirstLine];
        } catch (NumberFormatException e) {
            addErrorWrongFirstLine(line);
        }
    }

    private void verifySumZero() {
        if (!(puzzleMapper.getTotalSumOfAllEdges() == 0)) {
            errorsReadingInputFile.add(prop.getProperty("sumOfAllEdgesIsNotZero"));
        }
    }

    public ArrayList<Integer> getNumOfRowsForSolution(int numOfElm) {
        numOfRowsForSolution = puzzleMapper.getNumOfRowsForSolution(numOfElm);
        return numOfRowsForSolution;
    }


    public int getCounterOfPuzzleElementList() {
        return countElement;
    }
    public Map<Integer, List<PuzzleElement>> getAvailableOptionsForSolution() {
        return availableOptionsForSolution;
    }



    private void verifyAtLeastOneLineAvailable() {
        if ((this.availableOptionsForSolution.get(4) == null ) || this.availableOptionsForSolution.get(444) == null ||
                !(this.availableOptionsForSolution.get(4).size() > 0) || !(this.availableOptionsForSolution.get(444).size() > 0)) {
            String error = prop.getProperty("wrongNumberOfStraighEdges");
            errorsReadingInputFile.add(error);
        }
    }

    /**
     * 7 - Top Left Corner
     * 77 - Top Right Corner
     * 777 - Bottom Left Corner
     * 7777 - Bottom Right Corner
     */
    private void verifyAllCornersExist() {
        String error = prop.getProperty("missingCorner");
        if (this.availableOptionsForSolution.get(7) == null) {
            String errorTopLeftCorner = error.replace("<>", "TL");
            errorsReadingInputFile.add(errorTopLeftCorner);
        }
        if (this.availableOptionsForSolution.get(77) == null) {
            String errorTopRightCorner = error.replace("<>", "TR");
            errorsReadingInputFile.add(errorTopRightCorner);
        }
        if (this.availableOptionsForSolution.get(777) == null) {
            String errorBottomLeftCorner = error.replace("<>", "BL");
            errorsReadingInputFile.add(errorBottomLeftCorner);
        }
        if (this.availableOptionsForSolution.get(7777) == null) {
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
        if (!(id == -9999)) {
            String errorToAdd1 = errorToAdd.replace("<id>", String.valueOf(id));
            errorsReadingInputFile.add(errorToAdd1 + line);
        } else {
            errorsReadingInputFile.add(errorToAdd + line);
        }
    }

    private void markExistElement(int id) {
        puzzleElementIDs[id - 1] = true;
    }

    private boolean verifyIdInRange(Integer idToCheck) {
        return idToCheck <= expectedNumOfElementsFromFirstLine;
    }

    public void printErrorsFromReadingInputFile() {
        System.out.println("----------------------------------");
        System.out.println("--- All Errors from Input File ---");
        System.out.println("----------------------------------");
        if (errorsReadingInputFile.size() > 0) {
            for (String error : errorsReadingInputFile) {
                System.out.println(error);
            }
        } else {
            System.out.println("NO ERRORS");
        }
    }

    private boolean verifyAllEdgesInRange(ArrayList<Integer> numFromLine) {
        for (int i = 1; i < numFromLine.size(); i++) {
            if (!(numFromLine.get(i) >= -1 && numFromLine.get(i) <= 1)) {
                return false;
            }
        }
        return true;
    }

    public void printListOfElements() {
        System.out.println("----------------------------------");
        System.out.println("----   printListOfElements   ----");
        System.out.println("----------------------------------");
        for (PuzzleElement element : puzzleElementList) {
            System.out.println(element);
        }
    }

    private void initConfiguration() throws IOException {
        GetPuzzleErrors properties = new GetPuzzleErrors();
        prop = properties.getPropValues();

        System.out.println("--------------------------------------------");
        System.out.println("--- Existing Errors in config.properties ---");
        System.out.println("--------------------------------------------");
        prop.forEach((key, value) -> System.out.println(key + " : " + value));
    }

    public int getNumOfElementsFromFirstLine() {
        return expectedNumOfElementsFromFirstLine;
    }

    public boolean verifyErrorExistInList(String error) {
        return errorsReadingInputFile.contains(error);
    }

    public int getActualNumOfElementsReadFromInputFile() {
        return puzzleElementList.size();
    }

    //reade output file with ids only
    public void readOutputFile(String filePath) throws IOException {
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
            readDataFromOutputFile(br);
        }
    }

    private void readDataFromOutputFile(BufferedReader br) throws IOException {

        String line;
        Integer lineIndex = 0;
        List<Integer> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            System.out.println("line: " + line);
            if (line.trim().length() == 0) {
                continue;
            }
            line = line.trim();
            String[] lineToArray = line.split(" ");
            list = new ArrayList<>();
            for (String str : lineToArray) {
                if (str.length() == 0) {
                    continue;
                }
                try {
                    list.add(Integer.parseInt(str));
                    puzzleOutput.put(lineIndex, list);
                } catch (NumberFormatException e) {
                    //TODO - make it more elegant ...
                    addErrorWrongElementFormat(-9999, line);
                }
            }
            puzzleOutput.put(lineIndex, list);
            lineIndex++;
        }
    }

    //TODO - add support in rotation
    //check if output file is solution for input
    public boolean isIOSolvable() {
        PuzzleElement elm;
        int index = 0;
        boolean isSolution = false;
        PuzzleElement[][] board = null;
        int row = puzzleOutput.size();
        int col = 0;
        int i = 0, j = 0;
        if (row != 0) {
            col = expectedNumOfElementsFromFirstLine / row;
            board = new PuzzleElement[row][col];
        }

        for (Map.Entry<Integer, List<Integer>> ids : puzzleOutput.entrySet()) {
            for (Integer id : ids.getValue()) {
                elm = getById(id);
//                if(i>=0 && i<=row && j>=0 && j<=col && (board[i][j-1].getRight()+elm.getLeft()==0)&&(board[i-1][j].getBottom()+elm.getTop()==0)){
                if (i == 0 && j == 0) { // first TOP_LEFT_CORNER
                    board[i][j++] = elm;
                } else if (i == row - 1 && j == 0) { // first BOTTOM_LEFT_CORNER
                    board[i][j++] = elm;
                } else if (i == row - 1 && j == col - 1) { // last BOTTOM_RIGHT_CORNER
                    board[i][j++] = elm;
                } else if (i == 0 && j == col - 1) { // last TOP_RIGHT_CORNER
                    board[i][j++] = elm;
                }
                //check if edge
                else if (i == 0) { // first row
                    if ((board[i][j - 1].getRight() + elm.getLeft() == 0)) board[i][j++] = elm;
                } else if (j == 0) { // first column
                    if ((board[i - 1][j].getBottom() + elm.getTop() == 0)) board[i][j++] = elm;
                } else if (i == row - 1) { // last row
                    if ((board[i - 1][j].getBottom() + elm.getTop() == 0) && (board[i][j - 1].getRight() + elm.getLeft() == 0))
                        board[i][j++] = elm;
                } else if (j == col - 1) { // last column
                    if ((board[i - 1][j].getBottom() + elm.getTop() == 0) && (board[i][j - 1].getRight() + elm.getLeft() == 0))
                        board[i][j++] = elm;
                } else { // middle element
                    if ((board[i - 1][j].getBottom() + elm.getTop() == 0) && (board[i][j - 1].getRight() + elm.getLeft() == 0))
                        board[i][j++] = elm;
                }
//                    board[i][j++] = elm;
                System.out.print(id);
            }
//            }
            i++;
            j = 0;
        }

        return isProperPuzzle(board);
    }

    //get puzzle element by id from output file
    public PuzzleElement getById(int id) {
        PuzzleElement puzzleElement = null;
        int count = puzzleElementList.size() - 1;
        while (count >= 0) {
            puzzleElement = puzzleElementList.get(count);
            if (puzzleElement.getId() == id)
                return puzzleElement;
            count--;
        }
        return puzzleElement;
    }

    //check if puzzle have straight edges
    private boolean isProperPuzzle(PuzzleElement[][] board) {
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (i == 0) {
                    if (!(board[i][j].getTop() == 0)) {
                        return false;
                    }
                }
                if (i == board[0].length - 1) {
                    if (!(board[i][j].getBottom() == 0)) {
                        return false;
                    }
                }
                if (j == 0) {
                    if (!(board[i][j].getLeft() == 0)) {
                        return false;
                    }
                }
                if (j == board[0].length - 1) {
                    if (!(board[i][j].getRight() == 0)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void extractParameters(String[] args) {
        if (args.length == 0) {
            printUsgae();
            return;
        }
        int paramLocation = 0;

        for (String arg : args) {
            paramLocation++;
            if (arg.equals("-ip")){
                serverIp = args[paramLocation];
                continue;
            }

            if (arg.equals("-port")){
                serverPort = args[paramLocation];
                continue;
            }

            if (arg.equals("-input")) {
                filePath = args[paramLocation];
                continue;
            }
            if (arg.equals("-output")) {
                filePathToSave = args[paramLocation];
                continue;
            }
            if (arg.equals("-rotate")) {
                isRotation = true;
                continue;
            }
        }

        if ((filePath == null) || (filePathToSave == null)) {
            printUsgae();
        }else{
            allClientParamsExist = true;
        }
    }

    private void printUsgae() {
        System.out.println("There were no commandline arguments passed!");

        System.out.println("The below parameters should appaer for the Puzzle Client");
        System.out.println("-ip (Optional) - for the Server IP, default is 127.0.0.1");
        System.out.println("-port (Optional) - for the Server Port, default is like the Server port");
        System.out.println("-input <path> - full path for the Puzzle input file");
        System.out.println("-output <fileout> - full path for the Puzzle output file");
        System.out.println("-rotate (Optional) - in case support rotation is required, in case not appear - will be set to false;");
    }

    public void sendJsonToServer() {
        System.out.println("About to send to Server Json withh all Parameters from File");
        if (serverIp == null || serverIp.length() == 0){
            serverIp = "127.0.0.1";
        }
        if (serverPort == null || serverPort.length() == 0){
            //TODO change from Hard coded to be the Server port....
            serverPort = "4500";
        }
        System.out.println("====================================");
        System.out.println("==        CONNECTION DETAILS      ==");
        System.out.println("====================================");

        System.out.println("Server IP: " + serverIp);
        System.out.println("Server Port: " + serverPort);

        System.out.println("====================================");
        System.out.println("==        OTHER DETAILS      ==");
        System.out.println("====================================");


        System.out.println("Input File: " + filePath);
        System.out.println("Result File: " + filePathToSave);
        System.out.println("Rotation: " + isRotation);

    }
}
