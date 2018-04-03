import java.util.*;

public class PuzzleSolver {

    private List<PuzzleElement> jigsawElementInputList = new ArrayList<>();
    //private List<List<PuzzleElement>> jigsawElementResultList = new ArrayList<List<PuzzleElement>>();
    private PuzzleElement [][]  jigsawElementResultList;
    private ArrayList<Integer> numOfAvailableRowsForSolution;
    private Map<Enum, List<Integer>> availableOptionsForSolution;
    private List<Integer> indexesOfTopLowerLetfCorners = new ArrayList<>();
    private boolean [] usedElements = new boolean[jigsawElementInputList.size()];
    private int usedPuzzleElements;
    private int totalPuzzleElements;
    private int nomOfRowsForSolution;
    private int nomOfColumnsForSolution;
    private int row = 0;
    private int column = 0;


    public PuzzleSolver(List<PuzzleElement> jigsawElementList, ArrayList<Integer> numOfAvailableLineForSolution, Map<Enum, List<Integer>> availableOptionsForSolution) {
        this.jigsawElementInputList = jigsawElementList;
        this.numOfAvailableRowsForSolution = numOfAvailableLineForSolution;
        this.availableOptionsForSolution = availableOptionsForSolution;

        printJigsawElementInputList();
        printAvailableLeftCorners();
        printNumOfAvailableRowsForSolution();
        printAvailableOptionsForSolution();

        PuzzleElement startWithTopLeftCorner = null;
        startWithTopLeftCorner = jigsawElementInputList.get(availableOptionsForSolution.get(PUZZLEDIRECTIONS.TOP_LEFT_CORNER).get(0));

        //I think it will be better to call findSolution with all the available Top Left Corners


        //jigsawElementResultList.clear();

        for (Integer currentTopLeftCornerIndex :availableOptionsForSolution.get(PUZZLEDIRECTIONS.TOP_LEFT_CORNER)){
            System.out.println();
            System.out.println("=======START=======");
            totalPuzzleElements = jigsawElementInputList.size();
            System.out.println("Total Puzzle Elements are : " + totalPuzzleElements);
            System.out.println("Start search solution for Top Left Corner " + jigsawElementInputList.get(availableOptionsForSolution.get(PUZZLEDIRECTIONS.TOP_LEFT_CORNER).get(currentTopLeftCornerIndex)));

            for (int serachForSolutionWithRows : numOfAvailableRowsForSolution) {
                nomOfRowsForSolution = serachForSolutionWithRows;
                nomOfColumnsForSolution = totalPuzzleElements / serachForSolutionWithRows;
                System.out.println("#########################################################");
                System.out.println("Search solution for number of rows " + serachForSolutionWithRows +
                        " number of columns " + nomOfColumnsForSolution);
                System.out.println("#########################################################");
                jigsawElementResultList = new PuzzleElement[serachForSolutionWithRows][totalPuzzleElements / serachForSolutionWithRows];
                usedElements = new boolean[jigsawElementInputList.size()];
                usedPuzzleElements = 0;
                findSolutionFor(jigsawElementList.get(currentTopLeftCornerIndex));
            }
            System.out.println();
        }
    }

    private void findSolutionFor(PuzzleElement puzzleElement) {
        row = 0;
        column = 0;

        while(usedPuzzleElements < totalPuzzleElements && row < jigsawElementResultList.length && column <  jigsawElementResultList[0].length){

            if(puzzleElement == null){
                break;
            }

                //TODO remove print debug...
                System.out.println("Used so far " + usedPuzzleElements + " Puzzle Elements");
                System.out.println("Not used so far " + (totalPuzzleElements - usedPuzzleElements) + " Puzzle Elements");
                System.out.println("Puzzle Element: " + puzzleElement);
                System.out.println("row =" + row);
                System.out.println("column = " + column);
                jigsawElementResultList[row][column++] = puzzleElement;
                markElementAsUsed(puzzleElement);



                if (usedPuzzleElements == totalPuzzleElements){
                    //TODO remove print debug...
                    System.out.println("Used all " + totalPuzzleElements + " Puzzle elements");
                    System.out.println("CHECKING SOLUTION");
                    if(checkFrameForSolve()){
                        //TODO print solve or what defined in spec...
                        System.out.println("Great, puzzle was solved :)");
                    }else{
                        System.out.println("Sorry, might try another solution... :)");
                    }
                    break;
                }
                if (column ==  jigsawElementResultList[0].length) {
                    //TODO remove print debug...
                    System.out.println("End of columns in row " + row + " Move o next Row");
                    row++;
                    column = 0;
                }

                if (row == jigsawElementResultList.length) {
                    //TODO remove print debug...
                    System.out.println("End of rows and colums...");
                    break;
                }


                //puzzleElement = jigsawElementInputList.get(++tempCounter);
                puzzleElement = getElementByIndex(puzzleElement);

        }

    }

    private PuzzleElement getElementByIndex(PuzzleElement currentPuzzleElement) {
        Set<Integer> cutOfIndexes = new HashSet<>();
        PuzzleElement nextPuzzleElement = null;

        //TODO add the left checks & what required from next Puzzle Element
        if (row == 0) {
            for (int index : availableOptionsForSolution.get(PUZZLEDIRECTIONS.TOP_ZERO)){
                cutOfIndexes.add(index);
            }


        }
        if (column == 0){
            for (int index : availableOptionsForSolution.get(PUZZLEDIRECTIONS.LEFT_ZERO)){
                cutOfIndexes.add(index);
            }

        }
        if (column == nomOfColumnsForSolution -1){
            for (int index : availableOptionsForSolution.get(PUZZLEDIRECTIONS.RIGHT_ZERO)){
                cutOfIndexes.add(index);
            }

        }
        if (row == nomOfRowsForSolution -1){
            for (int index : availableOptionsForSolution.get(PUZZLEDIRECTIONS.BOTTOM_ZERO)){
                cutOfIndexes.add(index);
            }

        }
        //return nextPuzzleElement;
        //TODO if corner required...
        //TODO - usedElements[x] to true...
        for (int candidateIndexToReturn : cutOfIndexes){
            if (!(usedElements[candidateIndexToReturn])){
                nextPuzzleElement = jigsawElementInputList.get(candidateIndexToReturn);
            }
        }
        return nextPuzzleElement;
    }

    private boolean checkFrameForSolve() {
//        System.out.println("If all row 0 -> TOP_ZERO ");
//        System.out.println("AND");
//        System.out.println("If all row " + (jigsawElementResultList.length -1) + " -> BOTTOM_ZERO ");
//        System.out.println("AND");
//        System.out.println("If all colums 0 -> LEFT_ZERO ");
//        System.out.println("AND");
//        System.out.println("If all column " + (jigsawElementResultList[0].length -1) + " -> RIGHT_ZERO ");
//        System.out.println("!!! SOLVED !!!");

        for (int col = 0; col < jigsawElementResultList[0].length; col++){
            System.out.print("\nChecking (TOP_ZERO) for (0," + col + ")" + jigsawElementResultList[0][col]);
            if (!(jigsawElementResultList[0][col].top==0)){
                System.out.print(" ==>> FAILED\n\n");
                return false;
            }
            System.out.print("\nChecking (BOTTOM_ZERO) for (" + (jigsawElementResultList.length-1) + "," + col + " )" + jigsawElementResultList[jigsawElementResultList.length-1][col]);
            if (!(jigsawElementResultList[jigsawElementResultList.length-1][col].bottom==0)){
                System.out.print(" ==>> FAILED\n\n");
                return false;
            }
        }

        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        for (int row = 0; row < jigsawElementResultList.length; row++){
            System.out.print("\nChecking (LEFT_ZERO) for (" + row + ",0) " + jigsawElementResultList[row][0]);
            if (!(jigsawElementResultList[row][0].left==0)){
                System.out.print(" ==>> FAILED\n\n");
                return false;
            }
            System.out.print("\nChecking (RIGHT_ZERO) for (" + row + "," + (jigsawElementResultList.length-1) + ")" + jigsawElementResultList[row][(jigsawElementResultList.length-1)]);
            if (!(jigsawElementResultList[row][(jigsawElementResultList.length-1)].right==0)){
                System.out.print(" ==>> FAILED\n\n");
                return false;
            }
        }
        return true;



    }


    //TODO look for left comapre to 0 to my right edge
        //TODO Once all will be used, check the frame of all is 0


    private void markElementAsUsed(PuzzleElement currentElement) {

        //System.out.println("Mark element: " + currentElement);
        System.out.println(" -- Marked as Used --");
        usedElements[currentElement.id-1] = true;
        usedPuzzleElements++;
        System.out.println("Current Status of usedElements[]");
        String usedElementsStatus = "";
        for (int i=0; i < usedElements.length; i++){
            usedElementsStatus += (usedElements[i]) ? " " + i + ":T |" : " " + i + ":F |";
        }
        System.out.println(usedElementsStatus);

        System.out.println("Current Puzzle IDs jigsawElementResultList [][]");
        for (int ii = 0; ii<= jigsawElementResultList.length-1; ii++){
            for (int jj =0; jj <= jigsawElementResultList[0].length-1; jj++){
//                System.out.print("(" + ii + "," + jj + ")" + jigsawElementResultList[ii][jj] + " ; ");
                System.out.print("[" + jigsawElementResultList[ii][jj] + "],");
            }
            System.out.println();
        }
    }

    private void printAvailableLeftCorners() {
        System.out.println("#################################################");

        System.out.println("Available Top Left Corners:");
        indexesOfTopLowerLetfCorners = availableOptionsForSolution.get(PUZZLEDIRECTIONS.TOP_LEFT_CORNER);
        for (Integer index : indexesOfTopLowerLetfCorners){
            System.out.println("index: " + index + " => " + jigsawElementInputList.get(availableOptionsForSolution.get(PUZZLEDIRECTIONS.TOP_LEFT_CORNER).get(index)));
        }
        System.out.println("#################################################");

    }

//    private void findSolutionFor(PuzzleElement topLeftCorenrTofinSolution) {
//        System.out.println("Recursive Function - As appear below:");
//        System.out.println("If we are here, there were no errors in the parsing of the input file, Thereis at least 1 available row for solution" +
//                "There are elements in Puzzle Elements, and there is at least 1 Top Left Corner");
//        System.out.println("Upon given the NumOfElements, and num of rows ==>> num of columns can be calculated");
//        System.out.println("First we will seek for the 1st row, so 1st row is full");
//        System.out.println("In case we won't have it, no available solution");
//        System.out.println("Than, for each element for the 1st row, we will try to fill its column");
//        System.out.println("keep in mind to \"save\" LEFT_0 for the 1st column, and RIGHT_0 for last column");
//        System.out.println("1st Row, with TOP_0, and last row BOTTOM_0");
//
//        System.out.println("=====================================================");
//        System.out.println("Start findSolutionFor " + topLeftCorenrTofinSolution);
//        System.out.println("Total Num Of Elements " + this.jigsawElementInputList.size());
//        System.out.print("Available potential rows for solution ");
//        for (int j = 0; j <   numOfAvailableRowsForSolution.length ; j++){
//            System.out.print(" " + numOfAvailableRowsForSolution[j]);
//        }
//        System.out.println();
//        for (int i = 0; i < numOfAvailableRowsForSolution.length; i ++){
//            System.out.println("#################################################");
//            System.out.println();
//            System.out.println("Looking for solution for row with " +((this.jigsawElementInputList.size())/(numOfAvailableRowsForSolution[i]))  + " Puzzle elemnets");
//            System.out.println();
//            System.out.println("#################################################");
//        }
//
//        //int currentRows = this.jigsawElementInputList.size() / availableOptionsForSolution.get(PUZZLEDIRECTIONS.TOP_LEFT_CORNER).get(0);
//
//
//
//    }

    private void printAvailableOptionsForSolution() {
        System.out.println("========================================================================================");
        System.out.println("    Option Map for find results Map<Enum, List<Integer>> availableOptionsForSolution (): ");
        System.out.println("========================================================================================");
        System.out.println(availableOptionsForSolution);
        System.out.println();

    }

    private void printNumOfAvailableRowsForSolution() {
        System.out.println("==========================================================================");
        System.out.println("    Optional rows for solution (int [] numOfAvailableRowsForSolution):    ");
        System.out.println("==========================================================================");
        System.out.println(numOfAvailableRowsForSolution);
    }

    private void printJigsawElementInputList() {
        System.out.println("=================================================================================");
        System.out.println("    Should solve the below input (List<PuzzleElement> jigsawElementInputList):   ");
        System.out.println("=================================================================================");
        int index =0;
        for (PuzzleElement element : jigsawElementInputList){
            System.out.println(index + " : " + element);
            index++;
        }
    }
}
