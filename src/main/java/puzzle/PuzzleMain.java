package puzzle;

import java.io.IOException;

public class PuzzleMain {

    private static boolean isRotation ;
    private static boolean isMultiThread ;
    public static void main(String[] args) throws IOException {
        if(args[0].equals("rotation")){
            isRotation = true;
        }
        if(args[1].equals("multithreaded")){
            isMultiThread = true;
        }
     String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\Good16Pieces";
     String filePathToSave = System.getProperty("user.dir") + "\\src\\main\\resources\\result.txt";
     PuzzleManager puzzleManager = new PuzzleManager();
     puzzleManager.manage(filePath,filePathToSave, isRotation, isMultiThread);
    }
}
