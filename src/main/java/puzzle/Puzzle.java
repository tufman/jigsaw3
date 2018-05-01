package puzzle;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Puzzle {

    private int expectedNumOfElementsFromFirstLine;
    private List<PuzzleElement> puzzleElementList = new ArrayList<>();
    private int countElement=0;
    private Stack<PuzzleElement> puzzleElementListFromInputFile = new Stack<>();
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
    private PuzzleElement[][] board = null;
    private ArrayList<Integer> numOfRowsForSolution;
    private PuzzleMapper puzzleMapper = new PuzzleMapper();

    private Properties prop = null;
    private List<Integer> idsList;

    //ToDo - add if for the rotation
    private boolean isRotation;
    private boolean isMultiThread;
    private int numOfTheads;

    public Puzzle() {
    }


    public boolean readInputFile(String filePath, boolean isRotation, boolean isMultiThread, int numOfThreads) throws IOException {
        this.isRotation = isRotation;
        this.isMultiThread = isMultiThread;
        this.numOfTheads = numOfThreads;

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
//            System.out.println("availableOptionsForSolution size: " + availableOptionsForSolution.size() + "  " + availableOptionsForSolution);
            verifyAtLeastOneLineAvailable();
            verifyAllCornersExist();
            verifySumZero();
            //System.out.println("puzzle: " + puzzleMapper.getPuzzleStructure());
        }
    }

    private void createAndMapPuzzleElements() {
        if (isMultiThread) {
            ExecutorService executor = Executors.newFixedThreadPool(numOfTheads);
            while (stackOfGoodLines.size() != 0) {
                executor.submit(() -> {
                    indexingPuzzle();
                });
            }
            System.out.println();
            //Consider using invoke all and list of runnables
            executor.shutdown();
        } else {
            while (stackOfGoodLines.size() != 0) {
                indexingPuzzle();
            }
        }
        System.out.println("puz   " + "\u001B[32m" + puzzleMapper.getPuzzleStructure().size() + " puzzle element " + puzzleElementList);
    }

    private void indexingPuzzle() {
        //stackOfGoodLines.pop() is synchronaized by java....
        // puzzleMapper.addElementToStructure(element) is synchronized - keep it.
        // addPuzzleElementToList(element) is synchronized - keep it.

        ArrayList<Integer> popedLineFromStack = stackOfGoodLines.pop();

        //TODO: remove this line - for debug of MultiThread....
        System.out.println("CreateAndMap line from stack " + popedLineFromStack + " by " + Thread.currentThread().getId());

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

    //TODO:new logic for available solution
    public ArrayList<Integer> getNumOfRowsForSolution(int numOfElm) {
        numOfRowsForSolution = puzzleMapper.getNumOfRowsForSolution(numOfElm);
        return numOfRowsForSolution;
    }

    public List<PuzzleElement> getPuzzleElementList() {
        return puzzleElementList;
    }

    public int getCounterOfPuzzleElementList() {
        return countElement;
    }
    public Map<Integer, List<PuzzleElement>> getAvailableOptionsForSolution() {
        return availableOptionsForSolution;
    }

    public void printSolution() {
        if (board != null) {
            for (int ii = 0; ii <= board.length - 1; ii++) {
                for (int jj = 0; jj <= board[0].length - 1; jj++) {
                    System.out.print(board[ii][jj].toString() + " ");

                }
                System.out.println();
            }
        }
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

    public PuzzleElement getElementByIndex(int index) {
        return puzzleElementList.get(index);
    }

    public int getActualNumOfElementsReadFromInputFile() {
        return puzzleElementList.size();
    }

    private void WriteErrorsToFile(List<String> errorsReadingInputFile, String filePathToSave) throws IOException {

        File file = new File(filePathToSave + "//results");

        try (FileOutputStream fos = new FileOutputStream(filePathToSave);
             OutputStreamWriter osr = new OutputStreamWriter(fos)) {
            for (String err : errorsReadingInputFile) {
                osr.write(err + '\n');
            }

        }
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
//            initConfiguration();
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

    //return list of ids from output file
    public List<Integer> getIdsList() {
        return idsList;
    }

}
