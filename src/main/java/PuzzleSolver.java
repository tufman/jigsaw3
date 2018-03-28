import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PuzzleSolver {

    private List<PuzzleElement> jigsawElementInputList = new ArrayList<>();
    //private List<PuzzleElement> jigsawElementResultList = new ArrayList<>();
    private List<List<String>> jigsawElementResultList = new ArrayList<List<String>>();
    private int [] numOfAvailableLineForSolution;
    private Map<String, List<Integer>> availableOptionsForSolution;

    public PuzzleSolver(List<PuzzleElement> jigsawElementList, int [] numOfAvailableLineForSolution, Map<String, List<Integer>> availableOptionsForSolution) {
        this.jigsawElementInputList = jigsawElementList;
        this.numOfAvailableLineForSolution = numOfAvailableLineForSolution;
        this.availableOptionsForSolution = availableOptionsForSolution;

        printSolverInputList();
        printSolverAvailableNumOfSolution();
        printOptionsForResults();
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
