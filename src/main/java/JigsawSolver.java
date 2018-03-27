import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JigsawSolver {

    private List<Element> jigsawElementInputList = new ArrayList<>();
    private List<Element> jigsawElementResultList = new ArrayList<>();
    private int [] numOfAvailableLineForSolution;
    private Map<String, List<Integer>> cornersMap;

    public JigsawSolver(List<Element> jigsawElementList, int [] numOfAvailableLineForSolution, Map<String, List<Integer>> cornersMap) {
        this.jigsawElementInputList = jigsawElementList;
        this.numOfAvailableLineForSolution = numOfAvailableLineForSolution;
        this.cornersMap = cornersMap;

        printSolverInputList();
    }

    private void printSolverInputList() {
        System.out.println("====================================");
        System.out.println("    Should solve the below input    ");
        System.out.println("====================================");
        for (Element element : jigsawElementInputList){
            System.out.println(element);
        }
    }
}
