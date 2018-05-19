package puzzle.puzzleClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Puzzle {
    String name;
    Boolean rotation;
    //Map<Integer, PuzzlePiece> puzzlePieces = new HashMap<>();


    List<PuzzlePiece> puzzlePieces1 = new ArrayList<>();

    public Puzzle(String name, Boolean rotation, Stack<ArrayList<Integer>> puzzlePieces1) {
        this.name = name;
        this.rotation = rotation;
        fillArrayOfPuzzlePieces(puzzlePieces1);
    }

    public Puzzle() {

    }

    private void fillArrayOfPuzzlePieces(Stack<ArrayList<Integer>> puzzlePiecesStack) {
        while (puzzlePiecesStack.size() != 0){
            ArrayList<Integer> popedPuzzleElement = puzzlePiecesStack.pop();
            PuzzlePiece puzzlePiece = new PuzzlePiece(popedPuzzleElement);
            puzzlePieces1.add(puzzlePiece);
        }
    }

    public void addPuzzlePieces(ArrayList<Integer> piece) {
        int num = piece.get(0);
        PuzzlePiece puzzlePiece = new PuzzlePiece(num);
        //puzzlePieces.put(puzzlePiece.getID(), puzzlePiece);
        puzzlePieces1.add(puzzlePiece);
    }

    public String getName() {
        return name;
    }

    public Boolean getRotation() {
        return rotation;
    }

    public List<PuzzlePiece> getPuzzlePieces1() {
        return puzzlePieces1;
    }
}
