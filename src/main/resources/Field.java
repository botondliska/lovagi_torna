import javax.swing.*;
import java.awt.Color;

public class Field extends JButton {

    private Color color;
    private int number;

    public Field(Color color, int number) {
        this.color = color;
        this.setBackground(color);
        this.number = number;
    }

    public Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }
}
