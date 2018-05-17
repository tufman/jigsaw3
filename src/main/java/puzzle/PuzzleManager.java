/**
 * Manage the Puzzle flow, in case a valid input file, transfer tp Solver the Puzzle.
 * In case not valid, prints relevant errors to the output file.
 *
 * Author:
 * Yelena Koviar
 * Shay Tufman - extract parameters

 * */
package puzzle;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class PuzzleManager {

    private PuzzleElement[][] board = null;
    private boolean isMultiThread;
    private int numOfThreads;
    private int serverPort;


    public void manage() throws IOException, ExecutionException, InterruptedException {

        Puzzle puzzle1 = new Puzzle();
        //WritePuzzleStatus writePuzzleStatus = new WritePuzzleStatus(filePathToSave);
        //TODO change to be the result file tht already exist in the code tree
        WritePuzzleStatus writePuzzleStatus = new WritePuzzleStatus("C:\\Test\\Json\\result.txt");
        puzzle1.extractDataFromJson();
        //ClientHandler clientHandler = new ClientHandler();
        puzzle1.run(serverPort);
        if (puzzle1.readInputFile(isMultiThread, numOfThreads)){
            PuzzleSolution puzzleSolver = new PuzzleSolution(puzzle1, numOfThreads);
            board = puzzleSolver.solve();
            writePuzzleStatus.WriteResultToFile(board, true);
        } else{
            writePuzzleStatus.WriteErrorsToFile(puzzle1.getErrorsReadingInputFile());
        }
    }

    public void extractParameters(String[] args) {
        if (args.length == 0) {
            printUsgae();
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
    }

    private void printUsgae() {
        System.out.println("There were no commandline arguments passed!");

        System.out.println("The below parameters should appaer");
        System.out.println("-port (Optional) <port number - in case not appear, deafult will be 7095");
        System.out.println("-thread (Optional) <numOfThreads> - in case flag not appear, the default will be 1. In case wrong number, the default is 4");
    }


}

