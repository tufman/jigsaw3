import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PuzzleSolver {

    private List<PuzzleElement> jigsawElementInputList = new ArrayList<>();
    private List<List<String>> jigsawElementResultList = new ArrayList<List<String>>();
    private int [] numOfAvailableRowsForSolution;
    private Map<Enum, List<Integer>> availableOptionsForSolution;
    private List<Integer> indexesOfTopLowerLetfCorners = new ArrayList<>();



    public PuzzleSolver(List<PuzzleElement> jigsawElementList, int [] numOfAvailableLineForSolution, Map<Enum, List<Integer>> availableOptionsForSolution) {
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
        findSolutionFor(availableOptionsForSolution.get(PUZZLEDIRECTIONS.TOP_LEFT_CORNER));

        //In case we wouldl ike to start with the 1st Top Left Corenr
        //findSolutionFor(startWithTopLeftCorner);
    }

    private void findSolutionFor(List<Integer> topLeftCornerIndexesInPuzzleElementList) {
        int totalPuzzleElements = jigsawElementInputList.size();
        System.out.println("Total Puzzle Elements are : " + totalPuzzleElements);
        for (Integer currentTopLeftCornerIndex : topLeftCornerIndexesInPuzzleElementList){
            System.out.println("Search solution for Top Left Corner " + jigsawElementInputList.get(currentTopLeftCornerIndex));
            for (int serachForSolutionWithRows : numOfAvailableRowsForSolution){
                System.out.println("Search solution for number of rows " + serachForSolutionWithRows +
                        " number of columns " + totalPuzzleElements/serachForSolutionWithRows);
                int usedPuzzleElements = 1;
                int notUsedPuzzleElements = totalPuzzleElements - usedPuzzleElements;
                System.out.println("Used so far " + usedPuzzleElements + " Puzzle Elements");
                System.out.println("Not used so far " + notUsedPuzzleElements + " Puzzle Elements");
            }
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
        System.out.println(Arrays.toString(numOfAvailableRowsForSolution));
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
