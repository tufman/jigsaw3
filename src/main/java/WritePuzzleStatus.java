import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class WritePuzzleStatus {

    private String filePathToSave;

    public WritePuzzleStatus(String filePathToSave) throws IOException {
        this.filePathToSave = filePathToSave;
    }

    public void WriteErrorsToFile(List<String> errorsReadingInputFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePathToSave);
             OutputStreamWriter osr = new OutputStreamWriter(fos)) {
            for (String err : errorsReadingInputFile) {
                osr.write(err + '\n');
            }

        }
    }


    public void WriteResultToFile(PuzzleElement[][] board) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePathToSave);
             OutputStreamWriter osr = new OutputStreamWriter(fos)) {
            if (board == null) {
                osr.write("Cannot solve puzzle: it seems that there is no proper solution");
            } else {
                for (int ii = 0; ii <= board.length - 1; ii++) {
                    String line = "";
                    for (int jj = 0; jj <= board[0].length - 1; jj++) {
                        if (jj == board[0].length - 1) {
                            line += board[ii][jj].getId();
                        } else {
                            line += board[ii][jj].getId() + " ";
                        }

                    }
                    if (ii == board.length - 1) {
                        osr.write(line);
                    } else {
                        osr.write(line + '\n');
                    }


                }
            }

        }

    }
}