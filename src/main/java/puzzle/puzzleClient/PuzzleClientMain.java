package puzzle.puzzleClient;

import java.io.IOException;

public class PuzzleClientMain {

    public static void main(String[] args) {
        PuzzleClient puzzleClient = new PuzzleClient();
        puzzleClient.extractParameters(args);
        if (puzzleClient.isAllClientParamsExist()){
            try {
                if(puzzleClient.readInputFile()){
                    puzzleClient.connectToServer();
//                    puzzleClient.sendJsonToServer();
                }
            } catch (IOException e) {
                //TODO add log
                e.printStackTrace();
            }
        }





    }


}
