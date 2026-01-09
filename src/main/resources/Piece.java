import javax.swing.*;
import java.awt.*;

public class Piece extends JButton{
    private final Color color;
    private int number;

    private ImageIcon black = new ImageIcon("black_knight.png");
    private ImageIcon white = new ImageIcon("white_knight.png");

    public Piece(Color color, int number) {
        this.color = color;
        this.number = number;
        this.setBackground(color);
        if(color == Color.BLACK)this.setIcon(black);
        else if(color == Color.WHITE)this.setIcon(white);
    }

    public Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }
}
