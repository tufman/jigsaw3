package puzzle;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PuzzleMain {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

     String inputFile = args[0];
     String resultFile = args[1];
     PuzzleManager puzzleManager = new PuzzleManager();
     puzzleManager.manage(inputFile,resultFile);
    }
}
