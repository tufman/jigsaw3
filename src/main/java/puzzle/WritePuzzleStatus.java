package puzzle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class WritePuzzleStatus {

    public static final String CANNOT_SOLVE_PUZZLE_IT_SEEMS_THAT_THERE_IS_NO_PROPER_SOLUTION = "Cannot solve puzzle: it seems that there is no proper solution";
    private String filePathToSave;

    public WritePuzzleStatus(String filePathToSave) throws IOException {
        this.filePathToSave = filePathToSave;
    }

    public void printSolution(PuzzleElement[][] board) {
        if (board != null) {
            for (int ii = 0; ii <= board.length - 1; ii++) {
                for (int jj = 0; jj <= board[0].length - 1; jj++) {
                    System.out.print(board[ii][jj].toString() + " ");

                }
                System.out.println();
            }
        }
    }


    public void WriteErrorsToFile(List<String> errorsReadingInputFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePathToSave);
             OutputStreamWriter osr = new OutputStreamWriter(fos)) {
            for (String err : errorsReadingInputFile) {
                osr.write(err + '\n');
            }

        }
    }


    public void WriteResultToFile(PuzzleElement[][] board, boolean printToConsole) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePathToSave);
             OutputStreamWriter osr = new OutputStreamWriter(fos)) {
            if (board == null) {
                osr.write(CANNOT_SOLVE_PUZZLE_IT_SEEMS_THAT_THERE_IS_NO_PROPER_SOLUTION);
                if (printToConsole){
                    System.out.println(CANNOT_SOLVE_PUZZLE_IT_SEEMS_THAT_THERE_IS_NO_PROPER_SOLUTION);
                }
            } else {
                for (int ii = 0; ii <= board.length - 1; ii++) {
                    StringBuffer line= new StringBuffer();
                    String line1 = "";
                    for (int jj = 0; jj <= board[0].length - 1; jj++) {
                        if (jj == board[0].length - 1) {
                            line.append(board[ii][jj].getId());
                            line.append(board[ii][jj].getRotation().getMonetaryValue()+" ");
                            //System.out.println(board[ii][jj].getId()+board[ii][jj].getRotation().getMonetaryValue()+" ");
                        } else {
                            line.append(board[ii][jj].getId());
                            line.append(board[ii][jj].getRotation().getMonetaryValue()+" ");
                            //System.out.println(board[ii][jj].getId()+board[ii][jj].getRotation().getMonetaryValue()+" ");
                        }

                    }
                    if (ii == board.length - 1) {
                        osr.write(line.toString());
                        if (printToConsole){
                            System.out.print(line.toString());
                        }
                    } else {
                        osr.write(line.toString() + '\n');
                        if (printToConsole){
                            System.out.print(line.toString());
                            System.out.println();
                        }
                    }
                }
            }

        }

    }
}