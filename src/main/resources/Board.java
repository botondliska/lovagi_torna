import javax.swing.*;
import java.awt.*;

public class Board {

    private JButton[][] board;
    private final int boardSize;

    public Board(int size) {
        boardSize = size;
        board = new JButton[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                board[i][j] = new JButton();
            }
        }
    }

    public int getBoardSize() {
        return boardSize;
    }

}