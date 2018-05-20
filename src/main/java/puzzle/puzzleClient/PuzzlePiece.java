package puzzle.puzzleClient;

import java.util.ArrayList;
import java.util.Arrays;

public class PuzzlePiece {

    int id;

    int [] piece = new int[4];


    public PuzzlePiece(int id) {
        this.id = id;
        for (int i=0; i < 4; i++){
            piece[i] = id;
        }
    }

    public PuzzlePiece(ArrayList<Integer> popedPuzzleElement) {
        this.id = popedPuzzleElement.get(0);
        for (int i=0; i < 4; i++){
            piece[i] = popedPuzzleElement.get(i+1);
        }
    }

    public ArrayList<Integer> getPuzzlePiecesAsArrList(){
        ArrayList<Integer> retVal = new ArrayList<>();
        retVal.add(this.id);
        for (int i=0; i < piece.length; i++){
            retVal.add(piece[i]);
        }
        return retVal;
    }

    @Override
    public String toString() {
        return "PuzzlePiece{" +
                "id=" + id +
                ", piece=" + Arrays.toString(piece) +
                '}';
    }
}
