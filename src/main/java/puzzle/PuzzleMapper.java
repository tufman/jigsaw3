package puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuzzleMapper {
    private Map<Integer, List<PuzzleElement>> puzzleStructure = new HashMap<>();
    private int totalSumOfAllEdges;

    private  int counterOfPuzzleElements = 0;

    //Map that will hold all the Keys "TopLeft", "BottomPlus" and the relevant List of integers that will point to their reference in the Puzzle List<PuzzleElement> puzzleElementList
    private  Map<PuzzleDirections, List<Integer>> availableOptionsForSolution = new HashMap<>();

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

}
