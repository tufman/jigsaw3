package puzzleGenerator;

import puzzle.PuzzleElement;

import java.util.ArrayList;
import java.util.Random;

public class PuzzleGenerator {

    private PuzzleElement [][] puzzleElements;
    private int rows;
    private int cols;
    int puzzleID = 0;

    public PuzzleGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        puzzleElements = new PuzzleElement[rows][cols];
    }

    public void createPuzzle() {
        for (int i=0; i< rows; i++){
            for (int j=0; j < cols; j++){
                ArrayList<Integer> arrForPuzzleElement = getPuzzleElement(i,j);
                puzzleElements [i][j] = new PuzzleElement(arrForPuzzleElement);
            }
        }
    }

    private ArrayList<Integer> getPuzzleElement(int i, int j) {
        ArrayList<Integer> retVal = new ArrayList<>();

        retVal.add(++puzzleID);
        retVal.add(getRandVal());
        retVal.add(getRandVal());
        retVal.add(getRandVal());
        retVal.add(getRandVal());

//        if (i!=0){
//            int requiredTop = getCorrectTop(i,j);
//        }
//        if (j!=0){
//            int requiredLeft = getCorrectLeft(i,j);
//        }

        //First Row
        if (i == 0){
            retVal.set(2,0);
        }else{
            retVal.set(2,getCorrectTop(i,j));
        }
        //Last Row
        if (i == (rows -1)){
            retVal.set(4,0);
        }
        //First Col
        if (j == 0){
            retVal.set(1,0);
        }else{
            retVal.set(1,getCorrectLeft(i,j));
        }
        //Last Col
        if (j == (cols -1)){
            retVal.set(3,0);
        }






        return retVal;
    }

    private int getCorrectLeft(int i, int j) {
        return (0 - puzzleElements[i][j-1].getRight());
    }

    private int getCorrectTop(int i, int j) {
        return (0 - puzzleElements[i-1][j].getBottom());
    }


    private int getRandVal() {
        Random r = new Random();
        int Low = -1;
        int High = 1;
        return r.nextInt(High-Low) + Low;
    }

    public void printPuzzleList() {
        System.out.println("NumElements = " + rows*cols);
        for (int i=0; i< rows; i++){
            for (int j=0; j < cols; j++){

                System.out.println(puzzleElements [i][j].getId() + " " + puzzleElements [i][j].getLeft() + " " + puzzleElements [i][j].getTop() + " " + puzzleElements [i][j].getRight() + " " + puzzleElements [i][j].getBottom());
            }
        }
    }
}
