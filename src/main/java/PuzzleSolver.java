import java.util.*;

public class PuzzleSolver {

    private List<PuzzleElement> elements = new ArrayList<>();
    private ArrayList<Integer> rowOptions;
    private Map<PuzzleDirections, List<Integer>> positionToElements; // a map between a location within the board to a list of available elements which fits this position
    private List<Integer> indexesOfTopLowerLetfCorners = new ArrayList<>();
    private int totalPuzzleElements;
    private int numOfRowsForSolution;
    private int numOfColumnsForSolution;
    private PuzzleElement[][] board;
    private int rows;
    private int columns;

    public PuzzleSolver(List<PuzzleElement> elements, ArrayList<Integer> rowOptions, Map<PuzzleDirections, List<Integer>> positionToElements) {
        this.elements = elements;
        this.rowOptions = rowOptions;
        this.positionToElements = positionToElements;

    }

    public PuzzleElement[][] start() {

        for (int i = 0; i< rowOptions.size(); i++) {
            int r = rowOptions.get(i);
            int c = elements.size() / r;
            // try to buid a puzzle

            initPuzzle(c,r);

            PuzzleElement[][] board = solve(0,0, elements);

            if (board != null)
                return board;
        }
        return null;
    }

    public void initPuzzle(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        board = new PuzzleElement[rows][columns];
    }

    public PuzzleElement[][] solve(int r, int c, List<PuzzleElement> piecesLeft) {

        if (isSolved(r,c))
            return board;

        // Try each remaining piece in this square
        for (PuzzleElement p : piecesLeft) {

            if (tryInsert(p, r, c)) {
                // It fits: recurse to try the next square
                // Create the new list of pieces left
                List<PuzzleElement> piecesLeft2 = new ArrayList<PuzzleElement>(piecesLeft);
                piecesLeft2.remove(p);

                // (can stop here and return success if piecesLeft2 is empty)
                // Find the next poisition
                Position next = nextPos(r, c);

                // Recurse to try next square
                PuzzleElement[][] solution = solve(next.row, next.column, piecesLeft2);
                if (solution != null) {
                    // This sequence worked - success!
                    return solution;
                }
            }
        }
        // no solution with this piece
        return null;
    }

    private boolean tryInsert(PuzzleElement e, int r, int c) {
        //check if corner
        if (r == 0 && c == 0) { // first TOP_LEFT_CORNER
            if (!fit(e.id, PuzzleDirections.TOP_LEFT_CORNER)) return false;
        }
        else if (r == rows-1 && c == 0) { // first BOTTOM_LEFT_CORNER
            if (!fit(e.id, PuzzleDirections.BOTTOM_LEFT_CORNER)) return false;
        }
        else if (r == rows -1 && c==columns-1) { // last BOTTOM_RIGHT_CORNER
            if (!fit(e.id, PuzzleDirections.BOTTOM_RIGHT_CORNER)) return false;
        }
        else if (r == 0 && c==columns-1) { // last TOP_RIGHT_CORNER
            if (!fit(e.id, PuzzleDirections.TOP_RIGHT_CORNER)) return false;
        }
        //check if edge
        else if (r == 0) { // first row
            if (!(fit(e.id, PuzzleDirections.TOP_ZERO) && (board[r][c-1].right+e.left == 0))) return false;
        }
        else if (c == 0) { // first column
            if (!(fit(e.id, PuzzleDirections.LEFT_ZERO) && (board[r-1][c].bottom+e.top == 0))) return false;
        }
        else if (r == rows -1) { // last row
            if (!(fit(e.id, PuzzleDirections.BOTTOM_ZERO) && (board[r-1][c].bottom+e.top == 0)&& (board[r][c-1].right+e.left == 0))) return false;
        }
        else if (c==columns-1) { // last column
            if (!(fit(e.id, PuzzleDirections.RIGHT_ZERO) && (board[r-1][c].bottom+e.top == 0)&& (board[r][c-1].right+e.left == 0))) return false;
        }
        else { // middle element
            if (!((board[r-1][c].bottom+e.top == 0)&& (board[r][c-1].right+e.left == 0))) return false;
        }

        board[r][c]=e;
        return true;
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

    public boolean fit(int puzzleElement, PuzzleDirections direction){
        return positionToElements.get(direction).contains(puzzleElement);

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
