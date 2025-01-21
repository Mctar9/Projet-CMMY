import java.awt.*;

public class Component {
    private int x, y; // Coordonnées du composant
    private final int SIZE = 50; // Taille fixe du composant
    private int rotationAngle = 0; // Angle de rotation du composant

    public Component(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Tourne le composant de 90 degrés
    public void rotate() {
        rotationAngle = (rotationAngle + 90) % 360; // Incrémente l'angle par 90° et le ramène entre 0 et 359
    }

    // Vérifie si un point est dans les limites du composant
    public boolean contains(int px, int py) {
        return px >= x && px <= x + SIZE && py >= y && py <= y + SIZE;
    }

    // Dessine le composant en tenant compte de l'angle de rotation
    public void draw(Graphics g, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(isSelected ? Color.RED : Color.BLUE); // Rouge si sélectionné, sinon bleu
        g2d.rotate(Math.toRadians(rotationAngle), x + SIZE / 2.0, y + SIZE / 2.0); // Applique la rotation
        g2d.fillRect(x, y, SIZE, SIZE+50); // Dessine le rectangle
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, SIZE, SIZE+50); // Dessine la bordure
        g2d.rotate(-Math.toRadians(rotationAngle), x + SIZE / 2.0, y + SIZE / 2.0); // Réinitialise la rotation
    }
}
