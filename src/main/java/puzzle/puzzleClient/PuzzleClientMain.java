package puzzle.puzzleClient;

public class PuzzleClientMain {

    public static void main(String[] args) {
        PuzzleClient puzzleClient = new PuzzleClient();
        puzzleClient.extractParameters(args);
        if (puzzleClient.isAllClientParamsExist()){
            puzzleClient.sendJsonToServer();
        }





    }


}
