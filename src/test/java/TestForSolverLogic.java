import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestForSolverLogic {



    //@ParameterizedTest
    //@CsvSource({"SolvePuzzleWith2Pieces_All_Zero"})
    @DisplayName("Find Solution for 2 Puzzle Elements - All Zero")
    @Test
    public void SolvePuzzleWith2Pieces () throws IOException {

        //Create Elements
        ArrayList<Integer> tempElement1 = new ArrayList<>();
        ArrayList<Integer> tempElement2 = new ArrayList<>();
        ArrayList<Integer> tempElement3 = new ArrayList<>();
        ArrayList<Integer> tempElement4 = new ArrayList<>();

        tempElement1.add(1);
        tempElement1.add(0);
        tempElement1.add(0);
        tempElement1.add(-1);
        tempElement1.add(1);

        tempElement2.add(2);
        tempElement2.add(1);
        tempElement2.add(0);
        tempElement2.add(0);
        tempElement2.add(-1);

        tempElement3.add(3);
        tempElement3.add(0);
        tempElement3.add(-1);
        tempElement3.add(0);
        tempElement3.add(0);

        tempElement4.add(4);
        tempElement4.add(0);
        tempElement4.add(1);
        tempElement4.add(0);
        tempElement4.add(0);

        PuzzleElement element1 = new PuzzleElement(tempElement1);
        PuzzleElement element2 = new PuzzleElement(tempElement2);
        PuzzleElement element3 = new PuzzleElement(tempElement3);
        PuzzleElement element4 = new PuzzleElement(tempElement4);

        //Add the Elements to a List -
        // This is teh 1st argument in PuzzleSolver Construction
        List<PuzzleElement> puzzleElementList = new ArrayList<>();
        puzzleElementList.add(element1);
        puzzleElementList.add(element2);
        puzzleElementList.add(element3);
        puzzleElementList.add(element4);


        //Build the array of int that contains the available num of rows for the solution
        //This is the 2nd argument in PuzzleSolver Construction
        int [] numOfAvailableLineForSolution = {1,2};


        //Build a Map that will contains all the possiblities that exist
        //The keys will be:
        //TOP_LEFT_CORNER
        //BOTTOM_LEFT_CORNER
        //TOP_RIGHT_CORNER
        //BOTTOM_RIGHT_CORNER
        //LEFT_0
        //LEFT_1
        //LEFT_-1
        //TOP_0
        //TOP_1
        //TOP_-1
        //RIGHT_0
        //RIGHT_1
        //RIGHT_-1
        //BOTTOM_0
        //BOTTOM_1
        //BOTTOM_-1

        //The value will be the location in the List (Parameter 1)
        Map<Enum, List<Integer>> availableOptionsForSolution = new HashMap<>();
        List<Integer> topLeftCornerList = new ArrayList<>();
        List<Integer> bottomLeftCornerList = new ArrayList<>();
        List<Integer> topRightCornerList = new ArrayList<>();
        List<Integer> bottomRightCornerList = new ArrayList<>();

        //Corners
        topLeftCornerList.add(0);
        bottomLeftCornerList.add(2);
        bottomLeftCornerList.add(3);
        topRightCornerList.add(2);
        bottomRightCornerList.add(2);
        bottomRightCornerList.add(3);



        List<Integer> leff_0 = new ArrayList<>();
        List<Integer> left_Minus = new ArrayList<>();
        List<Integer> left_1 = new ArrayList<>();

        leff_0.add(0);
        leff_0.add(2);
        leff_0.add(3);
        left_1.add(1);


        List<Integer> top_0 = new ArrayList<>();
        List<Integer> top_Minus = new ArrayList<>();
        List<Integer> top_1 = new ArrayList<>();

        top_0.add(0);
        top_0.add(1);
        top_Minus.add(2);
        top_1.add(3);

        List<Integer> right_0 = new ArrayList<>();
        List<Integer> right_Minus = new ArrayList<>();
        List<Integer> right_1 = new ArrayList<>();

        right_0.add(1);
        right_0.add(2);
        right_0.add(3);
        right_Minus.add(0);

        List<Integer> bottom_0 = new ArrayList<>();
        List<Integer> bottom_Minus = new ArrayList<>();
        List<Integer> bottom_1 = new ArrayList<>();

        bottom_0.add(2);
        bottom_0.add(3);
        bottom_1.add(0);
        bottom_Minus.add(1);

        availableOptionsForSolution.put(PUZZLEDIRECTIONS.TOP_LEFT_CORNER, topLeftCornerList);
        availableOptionsForSolution.put(PUZZLEDIRECTIONS.BOTTOM_LEFT_CORNER, bottomLeftCornerList);
        availableOptionsForSolution.put(PUZZLEDIRECTIONS.TOP_RIGHT_CORNER, topRightCornerList);
        availableOptionsForSolution.put(PUZZLEDIRECTIONS.BOTTOM_RIGHT_CORNER, bottomRightCornerList);

        availableOptionsForSolution.put(PUZZLEDIRECTIONS.LEFT_ZERO, leff_0);
        availableOptionsForSolution.put(PUZZLEDIRECTIONS.LEFT_PLUS, left_1);
        availableOptionsForSolution.put(PUZZLEDIRECTIONS.LEFT_MINUS, left_Minus);

        availableOptionsForSolution.put(PUZZLEDIRECTIONS.TOP_ZERO, top_0);
        availableOptionsForSolution.put(PUZZLEDIRECTIONS.TOP_PLUS, top_1);
        availableOptionsForSolution.put(PUZZLEDIRECTIONS.TOP_MINUS, top_Minus);

        availableOptionsForSolution.put(PUZZLEDIRECTIONS.RIGHT_ZERO, right_0);
        availableOptionsForSolution.put(PUZZLEDIRECTIONS.RIGHT_PLUS, right_1);
        availableOptionsForSolution.put(PUZZLEDIRECTIONS.RIGHT_MINUS, right_Minus);

        availableOptionsForSolution.put(PUZZLEDIRECTIONS.BOTTOM_ZERO, bottom_0);
        availableOptionsForSolution.put(PUZZLEDIRECTIONS.BOTTOM_PLUS, bottom_1);
        availableOptionsForSolution.put(PUZZLEDIRECTIONS.BOTTOM_MINUS, bottom_Minus);

        List<Integer> listOfInteger = new ArrayList<>();
        listOfInteger.add(1);

        PuzzleSolver puzzleSolver = new PuzzleSolver(puzzleElementList, numOfAvailableLineForSolution, availableOptionsForSolution);


    }

}
