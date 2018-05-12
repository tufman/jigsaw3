package puzzle;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class PuzzleMain {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        PuzzleManager puzzleManager = new PuzzleManager();
        long startTime = System.currentTimeMillis();
        puzzleManager.extractParameters(args);
        puzzleManager.manage();
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println();
        System.out.println("\u001B[0m" + "Total time (Milliseconds) " + totalTime);
    }
}
