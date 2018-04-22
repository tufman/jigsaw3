package puzzle;

import java.io.IOException;

public class PuzzleMain {

    public static void main(String[] args) throws IOException {

     String inputFile = args[0];
     String resultFile = args[1];

     PuzzleManager puzzleManager = new PuzzleManager();
     puzzleManager.manage(inputFile,resultFile);
    }
}
