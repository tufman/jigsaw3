package puzzle.puzzleClient;

import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Stack;

public class WritePuzzlePiecesWithJSON {

    private Stack<ArrayList<Integer>> stackOfGoodLines = new Stack<>();
    private boolean rotation;
    private String puzzleName;
    private Puzzle puzzle;

//    public static void main(String[] args) throws IOException {
//        puzzle = new Puzzle("PuzzleName", false);
//
//        simulatePuzzlePiecesCreation();
//
//        //buildGson();
//        printJsonToFile();
//    }


    public WritePuzzlePiecesWithJSON(Stack<ArrayList<Integer>> stackOfGoodLines, boolean rotation, String puzzleName) throws IOException{

        this.stackOfGoodLines = stackOfGoodLines;
        this.rotation = rotation;
        this.puzzleName = puzzleName;

        //puzzle = new Puzzle("PuzzleName", false);

        //simulatePuzzlePiecesCreation();

        //buildGson();
        printJsonToFile();
    }

    private  void printJsonToFile() throws IOException {
        try (Writer writer = new FileWriter("C:\\Test\\Json\\Output.json")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(puzzle, writer);
            //gson.toJson(123, writer);
        }
    }

    private  void simulatePuzzlePiecesCreation() {
        for (int i =0; i < 5; i++){
            ArrayList<Integer> temp = new ArrayList<>();
            for (int k =0; k < 5; k++){
                temp.add(i);
            }
            puzzle.addPuzzlePieces(temp);
        }
    }

}
