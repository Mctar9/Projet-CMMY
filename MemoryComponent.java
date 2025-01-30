import java.awt.*;
public class MemoryComponent {
    private int id;         // Unique identifier
    private String type;    // Type of the component (AND, OR, NOT, etc.)
    private int x, y;       // Position on the grid
    private boolean isVisited; // Temporary flag for memory model calculations
    private final int SIZE = 50;
    private int rotationAngle = 0; // Angle de rotation du composant

    public MemoryComponent(int id, String type, int x, int y) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.isVisited = false;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getType() { return type; }
    public int getX() { return x; }
    public int getY() { return y; }
    // Renvoie la coordonnée X du centre
    public int getCenterX() {
        return x + SIZE / 2;
    }

    // Renvoie la coordonnée Y du centre
    public int getCenterY() {
        return y + SIZE / 2;
    }
    public void setVisited(boolean visited) { this.isVisited = visited; }
    public boolean isVisited() { return isVisited; }

    // Déplace le composant en modifiant ses coordonnées
    public void move(int dx, int dy) {
        x += dx; // Modifie la coordonnée X
        y += dy; // Modifie la coordonnée Y
    }
    
      // Vérifie si un point est dans les limites du composant
    public boolean contains(int px, int py) {
        return px >= x && px <= x + SIZE && py >= y && py <= y + SIZE;
    }

    public void rotate() {
        rotationAngle = (rotationAngle + 90) % 360; // Incrémente l'angle par 90° et le ramène entre 0 et 359
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

