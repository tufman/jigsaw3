package puzzleGenerator;

public class Main {

    private static int rows = 5;
    private static int cols = 5;

    public static void main(String[] args) {
        PuzzleGenerator puzzleGenerator = new PuzzleGenerator(rows, cols);
        puzzleGenerator.createPuzzle();
        puzzleGenerator.printPuzzleList();
    }
}
