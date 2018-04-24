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
        int key = 0;
        List<PuzzleElement>list;

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



        //create real element without joker
        key = e.getSumOfEdges();
        addElementToMapByKey(e, key);

        //create element with joker on bottom
        key = e.getSumOfEdgesJokerBottom();
        addElementToMapByKey(e, key);

        //create element with joker on right
        key = e.getSumOfEdgesJokerRight();
        addElementToMapByKey(e, key);

        //create element with joker on right&bottom
        key = e.getSumOfEdgesJokerRightAndBottom();
        addElementToMapByKey(e, key);
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
