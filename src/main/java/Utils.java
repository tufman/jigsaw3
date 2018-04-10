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

    private static int counterOfPuzzleElements = 0;

    //Map that will hold all the Keys "TopLeft", "BottomPlus" and the relevant List of integers that will point to their reference in the Puzzle List<PuzzleElement> puzzleElementList
    private static Map<PuzzleDirections, List<Integer>> availableOptionsForSolution = new HashMap<>();





    //public static Map<String, List<Integer>> getSolutionMap() {
    public static Map<PuzzleDirections, List<Integer>> getSolutionMap() {
        availableOptionsForSolution.put(PuzzleDirections.TOP_LEFT_CORNER, topLeftCorner);
        availableOptionsForSolution.put(PuzzleDirections.BOTTOM_LEFT_CORNER, bottomLeftCorner);
        availableOptionsForSolution.put(PuzzleDirections.TOP_RIGHT_CORNER, topRightCorner);
        availableOptionsForSolution.put(PuzzleDirections.BOTTOM_RIGHT_CORNER, bottomRightCorner);

        availableOptionsForSolution.put(PuzzleDirections.LEFT_ZERO, leftZero);
        availableOptionsForSolution.put(PuzzleDirections.LEFT_PLUS, leftPlus);
        availableOptionsForSolution.put(PuzzleDirections.LEFT_MINUS, leftMinus);

        availableOptionsForSolution.put(PuzzleDirections.TOP_ZERO, topZero);
        availableOptionsForSolution.put(PuzzleDirections.TOP_PLUS, topPlus);
        availableOptionsForSolution.put(PuzzleDirections.TOP_MINUS, topMinus);

        availableOptionsForSolution.put(PuzzleDirections.RIGHT_ZERO, rightZero);
        availableOptionsForSolution.put(PuzzleDirections.RIGHT_PLUS, rightPlus);
        availableOptionsForSolution.put(PuzzleDirections.RIGHT_MINUS, rightMinus);

        availableOptionsForSolution.put(PuzzleDirections.BOTTOM_ZERO, bottomZero);
        availableOptionsForSolution.put(PuzzleDirections.BOTTOM_PLUS, bottomPlus);
        availableOptionsForSolution.put(PuzzleDirections.BOTTOM_MINUS, bottomMinus);

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

        counterOfPuzzleElements++;
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

    public static ArrayList<Integer> getNumOfRowsForSolution() {
        int numOfLeft = leftZero.size();
        int numOfRight = rightZero.size();
        int commonLeftZeroAndRightZero = Math.min(numOfLeft, numOfRight);
        boolean isPrime = true;

        ArrayList<Integer> retVal = new ArrayList<>();
        if(topZero.size() ==  bottomZero.size()){
            retVal.add(1);
        }

        for(int divisor = 2; divisor <= counterOfPuzzleElements / 2; divisor++) {
            if (counterOfPuzzleElements % divisor == 0) {
                isPrime = false;
                break; // num is not a prime, no reason to continue checking
            }
        }

        if (isPrime && topZero.size()/counterOfPuzzleElements == bottomZero.size()/counterOfPuzzleElements){
            retVal.add(counterOfPuzzleElements);
            return retVal;
        }

        for (int i = 2; i < commonLeftZeroAndRightZero; i++){
            if ((counterOfPuzzleElements % i) == 0){
                if (topZero.size()>=counterOfPuzzleElements/i && bottomZero.size()>=counterOfPuzzleElements/i){
                    retVal.add(i);
                }

            }
        }
        return retVal;
    }
}
