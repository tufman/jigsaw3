package puzzle;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

public class PuzzleMain {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        PuzzleManager puzzleManager = new PuzzleManager();
        //LocalDateTime timeStart = LocalDateTime.now();
        //long startTime = System.nanoTime();
        long startTime = System.currentTimeMillis();
        puzzleManager.extractParameters(args);
        puzzleManager.manage();
        //LocalDateTime timeEnd = LocalDateTime.now();
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        //System.out.println("run time : " + (timeEnd.toString() + " " + timeStart.toString()));
        System.out.println("Total time (Milliseconds) " + totalTime);
    }
}
