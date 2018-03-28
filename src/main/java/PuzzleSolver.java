import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PuzzleSolver {

    private List<PuzzleElement> jigsawElementInputList = new ArrayList<>();
    //private List<PuzzleElement> jigsawElementResultList = new ArrayList<>();
    private List<List<String>> jigsawElementResultList = new ArrayList<List<String>>();
    private int [] numOfAvailableLineForSolution;
    private Map<String, List<Integer>> cornersMap;

    public PuzzleSolver(List<PuzzleElement> jigsawElementList, int [] numOfAvailableLineForSolution, Map<String, List<Integer>> cornersMap) {
        this.jigsawElementInputList = jigsawElementList;
        this.numOfAvailableLineForSolution = numOfAvailableLineForSolution;
        this.cornersMap = cornersMap;

        printSolverInputList();
    }

    private void printSolverInputList() {
        System.out.println("====================================");
        System.out.println("    Should solve the below input    ");
        System.out.println("====================================");
        for (PuzzleElement element : jigsawElementInputList){
            System.out.println(element);
        }
    }
}
