import java.io.IOException;

public class PuzzleMain {

    public static void main(String[] args) throws IOException {

        String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\main\\resources\\inputFile";
        //String filePath = "C:\\t1\\t1.txt";
        Puzzle puzzle = new Puzzle();
        puzzle.readInputFile(filePath);
        puzzle.printErrorsFromReadingInputFile();

    }
}
