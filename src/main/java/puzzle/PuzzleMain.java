package puzzle;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

public class PuzzleMain {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        PuzzleManager puzzleManager = new PuzzleManager();
        LocalDateTime timeStart = LocalDateTime.now();
        puzzleManager.extractParameters(args);
        puzzleManager.manage();
        LocalDateTime timeEnd = LocalDateTime.now();
        System.out.println("run time : " + (timeEnd.toString() + " " + timeStart.toString()));
    }
}
