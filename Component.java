import java.awt.*;

public class Component {
    private int x;
    private int y;
    private final int SIZE = 100; // Taille du composant (exemple)

    public Component(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 200, 200); // Dessine un rectangle pour représenter le composant
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 100, 299); // Bordure du rectangle
    }
}
