package puzzle;

import java.io.IOException;

public class PuzzleMain {

    public static void main(String[] args) throws IOException {
//
     String filePath = System.getProperty("user.dir")+"\\src\\\\main\\resources\\Good16Pieces";
     String filePathToSave = System.getProperty("user.dir") + "\\src\\main\\resources\\result.txt";
     PuzzleManager puzzleManager = new PuzzleManager();
     puzzleManager.manage(filePath,filePathToSave);
    }
}
