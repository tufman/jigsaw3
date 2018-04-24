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


    public Map<Integer, List<PuzzleElement>> getPuzzleStructure() {
        return puzzleStructure;
    }

}
