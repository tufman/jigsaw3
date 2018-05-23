/**
 * Manage the Puzzle flow, in case a valid input file, transfer tp Solver the Puzzle.
 * In case not valid, prints relevant errors to the output file.
 *
 * Author:
 * Yelena Koviar
 * Shay Tufman - extract parameters

 * */
package puzzle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import puzzle.puzzleClient.WritePuzzleStatus;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class PuzzleManager {

    private PuzzleElement[][] board = null;
    private boolean isMultiThread;
    private static int numOfThreads;
    private static int serverPort;

    private static Logger logger = LogManager.getLogger(PuzzleServerMain.class);


    public void manage() throws IOException, ExecutionException, InterruptedException {
        Puzzle puzzle1 = new Puzzle();
        puzzle1.run(serverPort);
    }


    public void extractParameters(String[] args) {
        if (args.length == 0) {
            serverPort = 7095;
        }
        int paramLocation = 0;

        for (String arg : args) {
            paramLocation++;
            if (arg.equals("-port")) {
                try {
                    serverPort = Integer.parseInt(args[paramLocation]);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    serverPort = 7095;
                }
            }
            if (arg.equals("-threads")) {
                try {
                    numOfThreads = Integer.parseInt(args[paramLocation]);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    numOfThreads = 4;
                }
                isMultiThread = true;
                continue;
            }
            if (serverPort == 0){
                serverPort = 7095;
            }
        }

        printServertParameters(args);
    }

    private void printServertParameters(String[] args) {
        logger.info("========== Server Configuration =============");
        logger.info("Client Parameters from command line: " + args);
        logger.info("Client Final Parameters for Execution:");
        logger.info("Server Port = " + serverPort);
        logger.info("MultiThread = " + isMultiThread);
        logger.info("Number of Threads = " + numOfThreads + (" in case of 0 - Changed on solver to 1"));
        logger.info("=============================================");
    }

    private void printUsgae() {
        System.out.println("There were no commandline arguments passed!");

        System.out.println("The below parameters should appaer");
        System.out.println("-port (Optional) <port number - in case not appear, deafult will be 7095");
        System.out.println("-thread (Optional) <numOfThreads> - in case flag not appear, the default will be 1. In case wrong number, the default is 4");
    }

    public static int getNumOfThreads() {
        return numOfThreads;
    }

    public int getServerPort() {
        return serverPort;
    }
}

