package puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuzzleMapper {
    private Map<Integer, List<PuzzleElement>> puzzleStructure = new HashMap<>();
    private int totalSumOfAllEdges;
    //I think it will be easier to fill the list as part of the file reading,
    // and in the end before we will send it to Solver, to put it in the relevant Map
//    private  List<Integer> leftPlus = new ArrayList<>();
//    private  List<Integer> leftZero = new ArrayList<>();
//    private  List<Integer> leftMinus = new ArrayList<>();
//    private  List<Integer> topPlus = new ArrayList<>();
//    private  List<Integer> topZero = new ArrayList<>();
//    private  List<Integer> topMinus = new ArrayList<>();
//    private  List<Integer> rightPlus = new ArrayList<>();
//    private  List<Integer> rightZero = new ArrayList<>();
//    private  List<Integer> rightMinus = new ArrayList<>();
//    private  List<Integer> bottomPlus = new ArrayList<>();
//    private  List<Integer> bottomZero = new ArrayList<>();
//    private  List<Integer> bottomMinus = new ArrayList<>();
//    private  List<Integer> topLeftCorner = new ArrayList<>();
//    private  List<Integer> bottomLeftCorner = new ArrayList<>();
//    private  List<Integer> topRightCorner = new ArrayList<>();
//    private  List<Integer> bottomRightCorner = new ArrayList<>();

    private  int counterOfPuzzleElements = 0;

    //Map that will hold all the Keys "TopLeft", "BottomPlus" and the relevant List of integers that will point to their reference in the Puzzle List<PuzzleElement> puzzleElementList
    private  Map<PuzzleDirections, List<Integer>> availableOptionsForSolution = new HashMap<>();

    public void addElementToStructure(PuzzleElement e){

        totalSumOfAllEdges += e.getKey();

        int key = 0;
        List<PuzzleElement>list;
        //create real element without joker
        key = e.getLeft()*1000 + e.getTop()*100 + e.getRight()*10 + e.getBottom();
        if(puzzleStructure.get(key)==null) {
            list = new ArrayList<>();
        }else list = new ArrayList<>(puzzleStructure.get(key));
        list.add(e);
        puzzleStructure.put(key,list);

        //create element with joker on bottom
        key = e.getLeft()*1000 + e.getTop()*100 + e.getRight()*10 + 5;
        if(puzzleStructure.get(key)==null) {
            list = new ArrayList<>();
        }else list = new ArrayList<>(puzzleStructure.get(key));
        list.add(e);
        puzzleStructure.put(key,list);

        //create element with joker on right
        key = e.getLeft()*1000 + e.getTop()*100 + 5*10 + e.getBottom();
        if(puzzleStructure.get(key)==null) {
            list = new ArrayList<>();
        }else list = new ArrayList<>(puzzleStructure.get(key));
        list.add(e);
        puzzleStructure.put(key,list);

        //create element with joker on right&bottom
        key = e.getLeft()*1000 + e.getTop()*100 + 5*10 + 5;
        if(puzzleStructure.get(key)==null) {
            list = new ArrayList<>();
        }else list = new ArrayList<>(puzzleStructure.get(key));
        list.add(e);
        puzzleStructure.put(key,list);
    }

    public int getTotalSumOfAllEdges() {
        return totalSumOfAllEdges;
    }


    //    public List<PuzzleElement> getPuzzleList(Integer key) {
//        return puzzleStructure.get(key);
//    }

    public Map<Integer, List<PuzzleElement>> getPuzzleStructure() {
        return puzzleStructure;
    }
    //
//    //public static Map<String, List<Integer>> getSolutionMap() {
//    public  Map<PuzzleDirections, List<Integer>> getSolutionMap() {
//        availableOptionsForSolution.put(PuzzleDirections.TOP_LEFT_CORNER, topLeftCorner);
//        availableOptionsForSolution.put(PuzzleDirections.BOTTOM_LEFT_CORNER, bottomLeftCorner);
//        availableOptionsForSolution.put(PuzzleDirections.TOP_RIGHT_CORNER, topRightCorner);
//        availableOptionsForSolution.put(PuzzleDirections.BOTTOM_RIGHT_CORNER, bottomRightCorner);
//
//        availableOptionsForSolution.put(PuzzleDirections.LEFT_ZERO, leftZero);
//        availableOptionsForSolution.put(PuzzleDirections.LEFT_PLUS, leftPlus);
//        availableOptionsForSolution.put(PuzzleDirections.LEFT_MINUS, leftMinus);
//
//        availableOptionsForSolution.put(PuzzleDirections.TOP_ZERO, topZero);
//        availableOptionsForSolution.put(PuzzleDirections.TOP_PLUS, topPlus);
//        availableOptionsForSolution.put(PuzzleDirections.TOP_MINUS, topMinus);
//
//        availableOptionsForSolution.put(PuzzleDirections.RIGHT_ZERO, rightZero);
//        availableOptionsForSolution.put(PuzzleDirections.RIGHT_PLUS, rightPlus);
//        availableOptionsForSolution.put(PuzzleDirections.RIGHT_MINUS, rightMinus);
//
//        availableOptionsForSolution.put(PuzzleDirections.BOTTOM_ZERO, bottomZero);
//        availableOptionsForSolution.put(PuzzleDirections.BOTTOM_PLUS, bottomPlus);
//        availableOptionsForSolution.put(PuzzleDirections.BOTTOM_MINUS, bottomMinus);
//
//        return availableOptionsForSolution;
//    }
//
//    public  void mapElementToSolutionList(PuzzleElement element, int indexInPuzzleElementList) {
//        //Map<String, List<Integer>> availableOptionsForSolution
//        switch(element.getLeft()){
//            case -1:  {
//                leftMinus.add(indexInPuzzleElementList);
//                break;
//            }
//            case 0: {
//                leftZero.add(indexInPuzzleElementList);
//                break;
//            }
//            case 1: {
//                leftPlus.add(indexInPuzzleElementList);
//                break;
//            }
//            default: //Currently do nothing
//        }
//        switch(element.getTop()){
//            case -1:  {
//                topMinus.add(indexInPuzzleElementList);
//                break;
//            }
//            case 0: {
//                topZero.add(indexInPuzzleElementList);
//                break;
//            }
//            case 1: {
//                topPlus.add(indexInPuzzleElementList);
//                break;
//            }
//            default: //Currently do nothing
//        }
//        switch(element.getRight()){
//            case -1:  {
//                rightMinus.add(indexInPuzzleElementList);
//                break;
//            }
//            case 0: {
//                rightZero.add(indexInPuzzleElementList);
//                break;
//            }
//            case 1: {
//                rightPlus.add(indexInPuzzleElementList);
//                break;
//            }
//            default: //Currently do nothing
//        }
//        switch(element.getBottom()){
//            case -1:  {
//                bottomMinus.add(indexInPuzzleElementList);
//                break;
//            }
//            case 0: {
//                bottomZero.add(indexInPuzzleElementList);
//                break;
//            }
//            case 1: {
//                bottomPlus.add(indexInPuzzleElementList);
//                break;
//            }
//            default: //Currently do nothing
//        }
//        if (element.getLeft() == 0 && element.getTop() == 0){
//            topLeftCorner.add(indexInPuzzleElementList);
//        }
//        if (element.getLeft() == 0 && element.getBottom() == 0){
//            bottomLeftCorner.add(indexInPuzzleElementList);
//        }
//        if (element.getRight() == 0 && element.getTop() == 0){
//            topRightCorner.add(indexInPuzzleElementList);
//        }
//        if (element.getRight() == 0 && element.getBottom() == 0){
//            bottomRightCorner.add(indexInPuzzleElementList);
//        }
//
//        counterOfPuzzleElements++;
//    }
//
//    public  void claenSolutionMap() {
//        availableOptionsForSolution.clear();
//        leftPlus.clear();
//        leftZero.clear();
//        leftMinus.clear();
//        topPlus.clear();
//        topZero.clear();
//        topMinus.clear();
//        rightPlus.clear();
//        rightZero.clear();
//        rightMinus.clear();
//        bottomPlus.clear();
//        bottomZero.clear();
//        bottomMinus.clear();
//        topLeftCorner.clear();
//        bottomLeftCorner.clear();
//        topRightCorner.clear();
//        bottomRightCorner.clear();
//
//    }
//
//    public  ArrayList<Integer> getNumOfRowsForSolution() {
//        int numOfLeft = leftZero.size();
//        int numOfRight = rightZero.size();
//        int commonLeftZeroAndRightZero = Math.min(numOfLeft, numOfRight);
//        boolean isPrime = true;
//
//        ArrayList<Integer> retVal = new ArrayList<>();
//
//        if (counterOfPuzzleElements >0){
//            if(topZero.size() == counterOfPuzzleElements && bottomZero.size() == counterOfPuzzleElements){
//                retVal.add(1);
//            }
//
//            for(int divisor = 2; divisor <= counterOfPuzzleElements / 2; divisor++) {
//                if (counterOfPuzzleElements % divisor == 0) {
//                    isPrime = false;
//                    break; // num is not a prime, no reason to continue checking
//                }
//            }
//
//            if (isPrime && topZero.size()/counterOfPuzzleElements == bottomZero.size()/counterOfPuzzleElements){
//                retVal.add(counterOfPuzzleElements);
//                return retVal;
//            }
//
//            for (int i = 2; i < commonLeftZeroAndRightZero; i++){
//                if ((counterOfPuzzleElements % i) == 0){
//                    if (topZero.size()>=counterOfPuzzleElements/i && bottomZero.size()>=counterOfPuzzleElements/i){
//                        retVal.add(i);
//                    }
//
//                }
//            }
//        }
//
//
//        return retVal;
//    }
}
