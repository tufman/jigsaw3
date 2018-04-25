package puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuzzleMapper {
    private Map<Integer, List<PuzzleElement>> puzzleStructure = new HashMap<>();
    private int totalSumOfAllEdges;

    private  int counterOfPuzzleElements = 0;

    public void addElementToStructure(PuzzleElement e){
        totalSumOfAllEdges += e.getSumOfEdges();
        mapZeroEdges(e);
        mapCorners(e);
        //create real element without joker
        addElementToMapByKey(e, e.getSumOfEdges());
        //create element with joker on bottom
        addElementToMapByKey(e, e.getSumOfEdgesJokerBottom());
        //create element with joker on right
        addElementToMapByKey(e, e.getSumOfEdgesJokerRight());
        //create element with joker on right&bottom
        addElementToMapByKey(e, e.getSumOfEdgesJokerRightAndBottom());
    }

    private void mapCorners(PuzzleElement e) {
        int keyForTopLeftCorner = 7;
        int keyForTopRightCorner = 77;
        int keyForBottomLeftCorner = 777;
        int keyForBottomRightCorner = 7777;

        //TopLeftCorner
        if(e.getTopLeftCorner()){
            addElementToMapByKey(e, keyForTopLeftCorner);
        }
        //TopRightCorner
        if(e.getTopRightCorner()){
            addElementToMapByKey(e, keyForTopRightCorner);
        }
        //BottomLeftCorner
        if(e.getBottomLeftCorner()){
            addElementToMapByKey(e, keyForBottomLeftCorner);
        }
        //BottomLeftCorner
        if(e.getBottomRightCorner()){
            addElementToMapByKey(e, keyForBottomRightCorner);
        }
    }

    private void mapZeroEdges(PuzzleElement e) {
        int keyForLeftZero = 4;
        int keyForTopZero = 44;
        int keyForRightZero = 444;
        int keyBottomZero = 4444;

        //LeftZero
        if (e.getLeft() == 0){
            addElementToMapByKey(e, keyForLeftZero);
        }
        //TopZero
        if (e.getTop() == 0){
            addElementToMapByKey(e, keyForTopZero);
        }
        //RightZero
        if (e.getRight() == 0){
            addElementToMapByKey(e, keyForRightZero);
        }
        //BottomZero
        if (e.getBottom() == 0){
            addElementToMapByKey(e, keyBottomZero);
        }
    }

    private void addElementToMapByKey(PuzzleElement e, int key) {
        List<PuzzleElement> list;
        if(puzzleStructure.get(key)==null) {
            list = new ArrayList<>();
        }else list = new ArrayList<>(puzzleStructure.get(key));
        list.add(e);
        puzzleStructure.put(key,list);
    }

    public int getTotalSumOfAllEdges() {
        return totalSumOfAllEdges;
    }

    public Map<Integer, List<PuzzleElement>> getPuzzleStructure() {
        return puzzleStructure;
    }

    public  ArrayList<Integer> getNumOfRowsForSolution() {
        int numOfLeft = puzzleStructure.get(4).size();
        int numOfRight = puzzleStructure.get(444).size();
        int commonLeftZeroAndRightZero = Math.min(numOfLeft, numOfRight);
        boolean isPrime = true;

        ArrayList<Integer> retVal = new ArrayList<>();

        if (puzzleStructure.size() >0 && counterOfPuzzleElements!=0 ) {
            if (puzzleStructure.get(44).size() == puzzleStructure.get(4444).size() && puzzleStructure.get(44).size() == counterOfPuzzleElements) {
                retVal.add(1);
            }
            for (int divisor = 2; divisor <= counterOfPuzzleElements / 2; divisor++) {
                if (counterOfPuzzleElements % divisor == 0) {
                    isPrime = false;
                    break; // num is not a prime, no reason to continue checking
                }
            }
            if (isPrime && puzzleStructure.get(44).size() / counterOfPuzzleElements == puzzleStructure.get(4444).size() / counterOfPuzzleElements) {
                retVal.add(counterOfPuzzleElements);
                return retVal;
            }
            for (int i = 2; i < commonLeftZeroAndRightZero; i++) {
                if ((counterOfPuzzleElements % i) == 0) {
                    if (puzzleStructure.get(44).size() >= counterOfPuzzleElements / i && puzzleStructure.get(4444).size() >= counterOfPuzzleElements / i) {
                        retVal.add(i);
                    }
                }
            }
        }
        return retVal;
    }
}
