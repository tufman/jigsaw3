import java.util.ArrayList;
import java.util.List;

public class JigsawSolver {

    List<Element> jigsawElementInputList = new ArrayList<>();
    List<Element> jigsawElementResultList = new ArrayList<>();

    public JigsawSolver(List<Element> jigsawElementList) {
        jigsawElementInputList = jigsawElementList;

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
