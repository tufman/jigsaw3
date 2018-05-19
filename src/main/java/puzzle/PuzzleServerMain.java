package puzzle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class PuzzleServerMain {

    private static Logger logger = LogManager.getLogger(PuzzleServerMain.class);

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

//        logger.info("INFO - Main From Puzzle Server - Demo");
//        logger.error("ERROR - Main From Puzzle Server - Demo");
//        logger.fatal("FATAL - Main From Puzzle Server - Demo");


        PuzzleManager puzzleManager = new PuzzleManager();
        long startTime = System.currentTimeMillis();
        puzzleManager.extractParameters(args);
        puzzleManager.manage();
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println();
        logger.info("======= TOTAL TIME ==========");
        logger.info("\u001B[0m" + "Total time (Milliseconds) " + totalTime);
        logger.info("=============================");
    }
}
