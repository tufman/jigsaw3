package puzzle;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PuzzleMain {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

     String inputFile = args[0];
     String resultFile = args[1];


       ExecutorService executor = Executors.newFixedThreadPool(10);
       for (int i =1 ; i<3 ; i++) {
           executor.submit(() -> {
               String threadName = Thread.currentThread().getName();
               System.out.println("Hello " + threadName);
           });
       }

        executor.shutdown();

     PuzzleManager puzzleManager = new PuzzleManager();
     puzzleManager.manage(inputFile,resultFile);
    }
}
