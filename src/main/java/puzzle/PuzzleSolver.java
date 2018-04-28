package puzzle;

import java.util.*;

public class PuzzleSolver {

    private List<PuzzleElement> elements = new ArrayList<>();
    private ArrayList<Integer> rowOptions;
    // a map between a location within the board to a list of available elements which fits this position
    private Map<PuzzleDirections, List<Integer>> positionToElements;
    private Map<Integer, List<PuzzleElement>> puzzleStructure;
    private List<Integer> usedElementById;
    private PuzzleElement[][] board;
    private List<Integer> availableRowsForSolution;
    PuzzleMapper puzzleMapper = new PuzzleMapper();
    private int rows;
    private int columns;

    public PuzzleSolver(List<PuzzleElement> elements, ArrayList<Integer> rowOptions, Map<PuzzleDirections, List<Integer>> positionToElements) {
        this.elements = elements;
        this.rowOptions = rowOptions;
        this.positionToElements = positionToElements;
    }

    public PuzzleSolver(Puzzle puzzle1) {
        this.elements = puzzle1.getPuzzleElementList();
        this.puzzleStructure = puzzle1.getAvailableOptionsForSolution();
        availableRowsForSolution = puzzle1.getNumOfRowsForSolution(puzzle1.getPuzzleElementList().size());
        usedElementById = new ArrayList<>();
    }

    public PuzzleElement[][] solve() {
//TODO: create threadePool for available row for solution
        for (int i = 0; i< availableRowsForSolution.size(); i++) {
            int r = availableRowsForSolution.get(i);
            int c = elements.size() / r;
            // try to build a puzzle
            initPuzzle(c,r);
            PuzzleElement[][] board = solve(0,0);
            if (board != null){
                return board;
            }
        }
        return null;
    }

    private void initPuzzle(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        board = new PuzzleElement[rows][columns];
    }

    private PuzzleElement[][] solve(int r, int c) {

        if (isSolved(r,c))
            return board;
        // Try each remaining piece in this square
        for (PuzzleElement p : elements) {
            if(inUse(p))
                continue;
            setAsUsed(p);

            if (tryInsert(p, r, c)) {
//TODO:remove current for debug
                // It fits: recurse to try the next square
                // Create the new list of pieces left
//                List<PuzzleElement> piecesLeft2 = new ArrayList<PuzzleElement>(piecesLeft);
//                piecesLeft2.remove(p);
//                setAsUsed(p);
                // (can stop here and return success if piecesLeft2 is empty)
                // Find the next position
                Position next = nextPos(r, c);

                // Recurse to try next square
                PuzzleElement[][] solution = solve(next.row, next.column);
                if (solution != null) {
                    // This sequence worked - success!
                    return solution;
                }
            }
            else setAsNotUsed(p);
        }
        // no solution with this piece
        return null;
    }
//TODO: for all size of puzzle
    private boolean tryInsert(PuzzleElement e, int r, int c) {
        //check if corner
        if (r == 0 && c == 0) { // first TOP_LEFT_CORNER
            if (inUse(e) && !fit(e, getPuzzleList(getKey(0, 0, 0, 7)))) return false;
        }
        else if (r == rows-1 && c == 0) { // first BOTTOM_LEFT_CORNER
            if (inUse(e) && !fit(e, getPuzzleList(getKey(0,7,7,7)))) return false;
        }
        else if (r == rows -1 && c==columns-1) { // last BOTTOM_RIGHT_CORNER
            if (inUse(e) && !fit(e, getPuzzleList(getKey(7,7,7,7)))) return false;
        }
        else if (r == 0 && c==columns-1) { // last TOP_RIGHT_CORNER
            if (inUse(e) && !fit(e, getPuzzleList(getKey(0,0,7,7)))) return false;
        }
        //check if edge
        else if (r == 0) { // first row
            if (inUse(e) && !(fit(e, getPuzzleList(getKey(e.getLeft(),0,5,5))) && (board[r][c-1].getRight()+e.getLeft() == 0)))
                return false;
        }
        else if (c == 0) { // first column
            if (inUse(e) && !(fit(e, getPuzzleList(getKey(0,e.getTop(),5,5))) && (board[r-1][c].getBottom()+e.getTop() == 0)))
                return false;
        }
        else if (r == rows -1) { // last row
            if (inUse(e) && !(fit(e, getPuzzleList(getKey(e.getLeft(),e.getTop(),5,0))) && (board[r-1][c].getBottom()+e.getTop() == 0)
                    && (board[r][c-1].getRight()+e.getLeft() == 0)))
                return false;
        }
        else if (c==columns-1) { // last column
            if (inUse(e) && !(fit(e, getPuzzleList(getKey(e.getLeft(),e.getTop(),0,5))) && (board[r-1][c].getBottom()+e.getTop() == 0)
                    && (board[r][c-1].getRight()+e.getLeft() == 0)))
                return false;
        }
        else { // middle element
            if (inUse(e) && !((board[r-1][c].getBottom()+e.getTop() == 0)&& (board[r][c-1].getRight()+e.getLeft() == 0)))
                return false;
        }

        board[r][c]=e;
        return true;
    }

    private Integer getKey(int l, int t, int r, int b) {
        return l*1000 + t*100 + r*10 + b;
    }

    private boolean fit(PuzzleElement pe, List<PuzzleElement> puzzleList) {
        return puzzleList.contains(pe);
    }

    public List<PuzzleElement> getPuzzleList(Integer key) {
        return puzzleStructure.get(key);
    }

    private void setAsUsed(PuzzleElement p) {
        usedElementById.add(p.getId());
    }

    private void setAsNotUsed(PuzzleElement p) {
        usedElementById.remove(usedElementById.indexOf(p.getId()));
    }

    private boolean inUse(PuzzleElement p) {
        if(usedElementById==null){
            return false;
        }
        else if(usedElementById.contains(p.getId())){
            return true;
        }
        return false;
    }

    private Position nextPos(int r, int c) {
        if(c < columns-1)  // if can move right
            return new Position(r,c+1);
        return new Position(r+1,0); // move to next row (even if it overflows)
    }

    private boolean isSolved(int r, int c) {
        if (c >= columns || r >= rows) // at last position
            return true;
        return false;
    }

    private class Position {
        private int row;
        private int column;
        public Position (int row, int column) {
            this.column = column;
            this.row = row;
        }
    }

}
