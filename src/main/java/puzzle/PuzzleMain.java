package puzzle;

import java.io.IOException;

public class PuzzleMain {

    private static boolean isRotation ;
    private static boolean isMultiThread ;
    public static void main(String[] args) throws IOException {
        if(args[0].equals("roatation")){
            isRotation = true;
        }
        if(args[1].equals("multithread")){
            isMultiThread = true;
        }
     String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\Good4Pieces";
     String filePathToSave = System.getProperty("user.dir") + "\\src\\main\\resources\\result.txt";
     PuzzleManager puzzleManager = new PuzzleManager();
     puzzleManager.manage(filePath,filePathToSave, isRotation, isMultiThread);
    }
}
