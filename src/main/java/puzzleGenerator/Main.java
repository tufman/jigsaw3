package puzzleGenerator;

public class Main {

    private static int rows = 4;
    private static int cols = 4;

    public static void main(String[] args) {
        PuzzleGenerator puzzleGenerator = new PuzzleGenerator(rows, cols);
        puzzleGenerator.createPuzzle();
        puzzleGenerator.printPuzzleList();
    }
}
