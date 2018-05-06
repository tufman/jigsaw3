package puzzle;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class PuzzleManager {

    private PuzzleElement[][] board = null;
    private boolean isRotation;
    private boolean isMultiThread;
    private int numOfThreads;
    private static String filePath;
    private static String filePathToSave;


    public void manage() throws IOException, ExecutionException, InterruptedException {

        Puzzle puzzle1 = new Puzzle();
        WritePuzzleStatus writePuzzleStatus = new WritePuzzleStatus(filePathToSave);
        if (puzzle1.readInputFile(filePath, isRotation, isMultiThread, numOfThreads)){
            PuzzleSolver puzzleSolver = new PuzzleSolver(puzzle1, numOfThreads);
            board = puzzleSolver.solve();
            //write puzzle solution to output file
            writePuzzleStatus.WriteResultToFile(board);
            //sout
            puzzle1.printSolution();
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
            if (arg.equals("-input")) {
                filePath = args[paramLocation];
                continue;
            }
            if (arg.equals("-output")) {
                filePathToSave = args[paramLocation];
                continue;
            }
            if (arg.equals("-rotate")) {
                isRotation = true;
                continue;
            }
            if (arg.equals("-threads")) {
                try {
                    numOfThreads = Integer.parseInt(args[paramLocation]);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    numOfThreads = 4;
                    isMultiThread = true;
                }
                continue;
            }
        }

        if ((filePath == null) || (filePathToSave == null)) {
            printUsgae();
        }
    }

    private void printUsgae() {
        System.out.println("There were no commandline arguments passed!");

        System.out.println("The below parameters should appaer");
        System.out.println("-input <path> - full path for the Puzzle input file");
        System.out.println("-output <fileout> - full path for the Puzzle output file");
        System.out.println("-rotate - in case support rotation is required, in case not appear - will be set to false;");
        System.out.println("-thread <numOfThreads> - in case flag not appear, the default will be 1. In case wrong number, the default is 4");
    }


}

