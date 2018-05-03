package puzzle;

import java.io.IOException;
import java.time.LocalDateTime;

public class PuzzleMain {

    private static boolean isRotation;
    private static boolean isMultiThread;
    private static String filePath;
    private static String filePathToSave;
    private static int numOfThreads;

    public static void main(String[] args) throws IOException, InterruptedException {
        extractParameters(args);
        LocalDateTime timeStart = LocalDateTime.now();
        PuzzleManager puzzleManager = new PuzzleManager();
        puzzleManager.manage(filePath, filePathToSave, isRotation, isMultiThread, numOfThreads);
        LocalDateTime timeEnd = LocalDateTime.now();
        System.out.println("run time : " + (timeEnd.toString() + " " + timeStart.toString()));
    }

    private static void extractParameters(String[] args) {
        if (args.length == 0)
        {
            printUsgae();
        }
        int paramLocation =0;

        for (String arg : args){
            paramLocation++;
            if (arg.equals("-input")){
                filePath = args[paramLocation];
                continue;
            }
            if (arg.equals("-output")){
                filePathToSave = args[paramLocation];
                continue;
            }
            if (arg.equals("-rotate")){
                isRotation = true;
                continue;
            }
            if (arg.equals("-threads")){
                try{
                    numOfThreads = Integer.parseInt(args[paramLocation]);
                }
                catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
                    numOfThreads = 4;
                    isMultiThread = true;
                }
                continue;
            }
        }

        if ((filePath == null) || (filePathToSave == null)){
            printUsgae();
        }
    }

    private static void printUsgae() {
        System.out.println("There were no commandline arguments passed!");

        System.out.println("The below parameters should appaer");
        System.out.println("-input <path> - full path for the Puzzle input file");
        System.out.println("-output <fileout> - full path for the Puzzle output file");
        System.out.println("-rotate - in case support rotation is required, in case not appear - will be set to false;");
        System.out.println("-thread <numOfThreads> - in case flag not appear, the default will be 1. In case wrong number, the default is 4");
    }
}
