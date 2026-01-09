import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.JOptionPane;

public class BoardGUI {

    private JButton[][] buttons;
    private Board board;
    private JPanel boardPanel;
    private int from=-100;
    private int to=-100;
    private boolean whiteTurn=true;


    public BoardGUI(int boardSize) {
        board = new Board(boardSize);
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(boardSize, boardSize));
        buttons = new JButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                final JButton button;
                if(i==0 && j==0)button=new Piece(Color.WHITE,i*boardSize+j);
                else if(i==0 && j==boardSize-1)button=new Piece(Color.BLACK,i*boardSize+j);
                else if(i==boardSize-1 && j==0)button=new Piece(Color.BLACK,i*boardSize+j);
                else if(i==boardSize-1 && j==boardSize-1)button=new Piece(Color.WHITE,i*boardSize+j);
                else button = new Field(Color.GRAY,i*boardSize+j);
                button.setPreferredSize(new Dimension(120, 120));
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
                button.addActionListener(e -> clicked(button));
                buttons[i][j] = button;
                boardPanel.add(button);
            }
        }
    }

    private void clicked(JButton button) {
        if(button instanceof Piece){
            Piece piece = (Piece) button;
            if(piece.getColor()==Color.WHITE && !whiteTurn)return;
            if(piece.getColor()==Color.BLACK && whiteTurn)return;
            from = piece.getNumber();
        }
        else if (from!=-100 && button instanceof Field) {
            Field field = (Field) button;
            to = field.getNumber();
            if(isValidMove(from,to)){
                refresh(from,to);
                whiteTurn = !whiteTurn;
            }
            else from=-100;
        }
    }

    private boolean isValidMove(int from, int to) {
        int size=board.getBoardSize();
        switch (size){
            case 4:
                if(Math.abs(from-to)==2||Math.abs(from-to)==6||Math.abs(from-to)==7||Math.abs(from-to)==9)return true;
                return false;
            case 6:
                if(Math.abs(from-to)==4||Math.abs(from-to)==8||Math.abs(from-to)==11||Math.abs(from-to)==13)return true;
                return false;
            case 8:
                if(Math.abs(from-to)==6||Math.abs(from-to)==10||Math.abs(from-to)==15||Math.abs(from-to)==17)return true;
                return false;
            default:
                return false;
        }
    }

    public void refresh(int x, int y) {
        Piece piece = (Piece) buttons[x/board.getBoardSize()][x%board.getBoardSize()];
        Field field = (Field) buttons[y/board.getBoardSize()][y%board.getBoardSize()];
        boardPanel.remove(piece);
        boardPanel.remove(field);
        Piece p = new Piece(piece.getColor(),y);
        Field f = new Field(piece.getColor(),x);
        buttons[x / board.getBoardSize()][x % board.getBoardSize()] = f;
        buttons[y / board.getBoardSize()][y % board.getBoardSize()] = p;
        if(x<y){
            boardPanel.add(f,x);
            boardPanel.add(p,y);
        }
        else {
            boardPanel.add(p,y);
            boardPanel.add(f,x);
        }
        f.setPreferredSize(new Dimension(80, 80));
        f.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        f.addActionListener(e -> clicked(f));
        p.setPreferredSize(new Dimension(80, 80));
        p.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        p.addActionListener(e -> clicked(p));
        boardPanel.revalidate();
        boardPanel.repaint();
        from=-100;
        to=-100;
        if(isOver()){
            JOptionPane.showMessageDialog(boardPanel,whiteTurn?"White player won, Congrats!":"Black player won, Congrats!");
            startNewGame(board.getBoardSize());
        }
    }

    public boolean isOver(){
        Color x,y;
        for(int i=0;i<board.getBoardSize();++i){
            for(int j=0;j<board.getBoardSize();++j){
                int counter = 1;
                if(!(i>board.getBoardSize()-3 || j>board.getBoardSize()-3)){
                    if(buttons[i][j] instanceof Piece) {
                        x = ((Piece) buttons[i][j]).getColor();
                    }
                    else {
                        x = ((Field) buttons[i][j]).getColor();
                    }
                    if(i>2 && j<board.getBoardSize()-3) { //jobbfel
                        for(int k=1;k<4;++k){
                            if(buttons[i-k][j+k] instanceof Piece){
                                y = ((Piece) buttons[i-k][j+k]).getColor();
                            }
                            else {
                                y = ((Field) buttons[i-k][j+k]).getColor();
                            }
                            if(x.equals(y) && !x.equals(Color.GRAY))counter++;
                        }
                        if(counter==4)return true;
                        else counter=1;
                    }
                    if(j<board.getBoardSize()-3) { //jobbra
                        for(int k=1;k<4;++k){
                            if(buttons[i][j+k] instanceof Piece){
                                y = ((Piece) buttons[i][j+k]).getColor();
                            }
                            else {
                                y = ((Field) buttons[i][j+k]).getColor();
                            }
                            if(x.equals(y) && !x.equals(Color.GRAY))counter++;
                        }
                        if(counter==4)return true;
                        else counter=1;
                    }
                    if(i<board.getBoardSize()-3 && j<board.getBoardSize()-3) { //jobble
                        for(int k=1;k<4;++k){
                            if(buttons[i+k][j+k] instanceof Piece){
                                y = ((Piece) buttons[i+k][j+k]).getColor();
                            }
                            else {
                                y = ((Field) buttons[i+k][j+k]).getColor();
                            }
                            if(x.equals(y) && !x.equals(Color.GRAY))counter++;
                        }
                        if(counter==4)return true;
                        else counter=1;
                    }
                    if(i<board.getBoardSize()-3) { //le
                        for(int k=1;k<4;++k){
                            if(buttons[i+k][j] instanceof Piece){
                                y = ((Piece) buttons[i+k][j]).getColor();
                            }
                            else {
                                y = ((Field) buttons[i+k][j]).getColor();
                            }
                            if(x.equals(y) && !x.equals(Color.GRAY))counter++;
                        }
                        if(counter==4)return true;
                    }
                }
            }
        }
        return false;
    }

    private void startNewGame(int boardSize) {
        board = new Board(boardSize);
        boardPanel.removeAll(); // Eltávolítja a régi táblát
        boardPanel.setLayout(new GridLayout(boardSize, boardSize));
        buttons = new JButton[boardSize][boardSize];

        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                final JButton button;
                if (i == 0 && j == 0) button = new Piece(Color.WHITE, i * boardSize + j);
                else if (i == 0 && j == boardSize - 1) button = new Piece(Color.BLACK, i * boardSize + j);
                else if (i == boardSize - 1 && j == 0) button = new Piece(Color.BLACK, i * boardSize + j);
                else if (i == boardSize - 1 && j == boardSize - 1) button = new Piece(Color.WHITE, i * boardSize + j);
                else button = new Field(Color.GRAY, i * boardSize + j);

                button.setPreferredSize(new Dimension(80, 80));
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                button.addActionListener(e -> clicked(button));
                buttons[i][j] = button;
                boardPanel.add(button);
            }
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }


    public JPanel getBoardPanel() {
        return boardPanel;
    }
}