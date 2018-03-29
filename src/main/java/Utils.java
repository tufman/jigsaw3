import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    //I think it will be easier to fill the list as part of the file reading,
    // and in the end before we will send it to Solver, to put it in the relevant Map
    private static List<Integer> leftPlus = new ArrayList<>();
    private static List<Integer> leftZero = new ArrayList<>();
    private static List<Integer> leftMinus = new ArrayList<>();
    private static List<Integer> topPlus = new ArrayList<>();
    private static List<Integer> topZero = new ArrayList<>();
    private static List<Integer> topMinus = new ArrayList<>();
    private static List<Integer> rightPlus = new ArrayList<>();
    private static List<Integer> rightZero = new ArrayList<>();
    private static List<Integer> rightMinus = new ArrayList<>();
    private static List<Integer> bottomPlus = new ArrayList<>();
    private static List<Integer> bottomZero = new ArrayList<>();
    private static List<Integer> bottomMinus = new ArrayList<>();
    private static List<Integer> topLeftCorner = new ArrayList<>();
    private static List<Integer> bottomLeftCorner = new ArrayList<>();
    private static List<Integer> topRightCorner = new ArrayList<>();
    private static List<Integer> bottomRightCorner = new ArrayList<>();

    //Map that will hold all the Keys "TopLeft", "BottomPlus" and the relevant List of integers that will point to their reference in the Puzzle List<PuzzleElement> puzzleElementList
    private static Map<String, List<Integer>> availableOptionsForSolution = new HashMap<>();





    public static Map<String, List<Integer>> getSolutionMap() {
        availableOptionsForSolution.put("TOP_LEFT_CORNER", topLeftCorner);
        availableOptionsForSolution.put("BOTTOM_LEFT_CORNER", bottomLeftCorner);
        availableOptionsForSolution.put("TOP_RIGHT_CORNER", topRightCorner);
        availableOptionsForSolution.put("BOTTOM_RIGHT_CORNER", bottomRightCorner);

        availableOptionsForSolution.put("LEFT_0", leftZero);
        availableOptionsForSolution.put("LEFT_PLUS", leftPlus);
        availableOptionsForSolution.put("LEFT_MINUS", leftMinus);

        availableOptionsForSolution.put("TOP_0", topZero);
        availableOptionsForSolution.put("TOP_PLUS", topPlus);
        availableOptionsForSolution.put("TOP_MINUS", topMinus);

        availableOptionsForSolution.put("RIGHT_0", rightZero);
        availableOptionsForSolution.put("RIGHT_PLUS", rightPlus);
        availableOptionsForSolution.put("RIGHT_MINUS", rightMinus);

        availableOptionsForSolution.put("BOTTOM_0", bottomZero);
        availableOptionsForSolution.put("BOTTOM_PLUS", bottomPlus);
        availableOptionsForSolution.put("BOTTOM_MINUS", bottomMinus);

        return availableOptionsForSolution;
    }

    public static void mapElementToSolutionList(PuzzleElement element, int indexInPuzzleElementList) {
        //Map<String, List<Integer>> availableOptionsForSolution
        switch(element.left){
            case -1:  {
                leftMinus.add(indexInPuzzleElementList);
                break;
            }
            case 0: {
                leftZero.add(indexInPuzzleElementList);
                break;
            }
            case 1: {
                leftPlus.add(indexInPuzzleElementList);
                break;
            }
            default: //Currently do nothing
        }
        switch(element.top){
            case -1:  {
                topMinus.add(indexInPuzzleElementList);
                break;
            }
            case 0: {
                topZero.add(indexInPuzzleElementList);
                break;
            }
            case 1: {
                topPlus.add(indexInPuzzleElementList);
                break;
            }
            default: //Currently do nothing
        }
        switch(element.right){
            case -1:  {
                rightMinus.add(indexInPuzzleElementList);
                break;
            }
            case 0: {
                rightZero.add(indexInPuzzleElementList);
                break;
            }
            case 1: {
                rightPlus.add(indexInPuzzleElementList);
                break;
            }
            default: //Currently do nothing
        }
        switch(element.bottom){
            case -1:  {
                bottomMinus.add(indexInPuzzleElementList);
                break;
            }
            case 0: {
                bottomZero.add(indexInPuzzleElementList);
                break;
            }
            case 1: {
                bottomPlus.add(indexInPuzzleElementList);
                break;
            }
            default: //Currently do nothing
        }
        if (element.left == 0 && element.top == 0){
            topLeftCorner.add(indexInPuzzleElementList);
        }
        if (element.left == 0 && element.bottom == 0){
            bottomLeftCorner.add(indexInPuzzleElementList);
        }
        if (element.right == 0 && element.top == 0){
            topRightCorner.add(indexInPuzzleElementList);
        }
        if (element.right == 0 && element.bottom == 0){
            bottomRightCorner.add(indexInPuzzleElementList);
        }
    }

    public static void claenSolutionMap() {
        availableOptionsForSolution.clear();
        leftPlus.clear();
        leftZero.clear();
        leftMinus.clear();
        topPlus.clear();
        topZero.clear();
        topMinus.clear();
        rightPlus.clear();
        rightZero.clear();
        rightMinus.clear();
        bottomPlus.clear();
        bottomZero.clear();
        bottomMinus.clear();
        topLeftCorner.clear();
        bottomLeftCorner.clear();
        topRightCorner.clear();
        bottomRightCorner.clear();

    }
}
