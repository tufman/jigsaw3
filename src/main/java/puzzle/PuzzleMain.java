package puzzle;

import java.io.IOException;

public class PuzzleMain {

    private static boolean isRotation;
    private static boolean isMultiThread;
    private static String filePath;
    private static String filePathToSave;
    private static int numOfThreads;

    public static void main(String[] args) throws IOException {
        extractParameters(args);


//        if(args[0].equals("rotation")){
//            isRotation = true;
//        }
//        if(args[1].equals("multithreaded")){
//            isMultiThread = false;
//        }
//     filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\Good2Pieces";
//     filePathToSave = System.getProperty("user.dir") + "\\src\\main\\resources\\result.txt";
     PuzzleManager puzzleManager = new PuzzleManager();
     puzzleManager.manage(filePath,filePathToSave, isRotation, isMultiThread, numOfThreads);
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
