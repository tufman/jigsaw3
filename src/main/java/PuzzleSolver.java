import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PuzzleSolver {

    private List<PuzzleElement> jigsawElementInputList = new ArrayList<>();
    private List<List<String>> jigsawElementResultList = new ArrayList<List<String>>();
    private int [] numOfAvailableLineForSolution;
    private Map<Enum, List<Integer>> availableOptionsForSolution;

    public PuzzleSolver(List<PuzzleElement> jigsawElementList, int [] numOfAvailableLineForSolution, Map<Enum, List<Integer>> availableOptionsForSolution) {
        this.jigsawElementInputList = jigsawElementList;
        this.numOfAvailableLineForSolution = numOfAvailableLineForSolution;
        this.availableOptionsForSolution = availableOptionsForSolution;

        printSolverInputList();
        printSolverAvailableNumOfSolution();
        printOptionsForResults();

        PuzzleElement startWithTopLeftCorner = null;
        try{
            startWithTopLeftCorner = jigsawElementInputList.get(availableOptionsForSolution.get(PUZZLEDIRECTIONS.TOP_LEFT_CORNER).get(0));
        }
        catch (IndexOutOfBoundsException e){
            //TODO - Finish the game....
        }

        findSolutionFor(startWithTopLeftCorner);
    }

    private void findSolutionFor(PuzzleElement startWithTopLeftCorner) {
        System.out.println("Recursive Function - As appear below:");
        System.out.println("Since TopLeftCorner always should exist -> In case not, no available solution!!!");
        System.out.println("Upon given the NumOfElements, and num of rows ==>> num of columns can be calculated");
        System.out.println("First we will seek for the 1st row, so 1st row is full");
        System.out.println("In case we won't have it, no available solution");
        System.out.println("Than, for each element for the 1st row, we will try to fill its column");
        System.out.println("keep in mind to \"save\" LEFT_0 for the 1st column, and RIGHT_0 for last column");
        System.out.println("1st Row, with TOP_0, and last row BOTTOM_0");
    }

    private void printOptionsForResults() {
        System.out.println("====================================");
        System.out.println("    Option Map for find results:    ");
        System.out.println("====================================");
        System.out.println(availableOptionsForSolution);

    }

    private void printSolverAvailableNumOfSolution() {
        System.out.println("====================================");
        System.out.println("    Optional lines for solution:    ");
        System.out.println("====================================");
        System.out.println(Arrays.toString(numOfAvailableLineForSolution));
    }

    private void printSolverInputList() {
        System.out.println("====================================");
        System.out.println("    Should solve the below input    ");
        System.out.println("====================================");
        int index =0;
        for (PuzzleElement element : jigsawElementInputList){
            System.out.println(index + " : " + element);
            index++;
        }
    }
}
