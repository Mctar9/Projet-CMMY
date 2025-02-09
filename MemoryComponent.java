import java.awt.*;
import javax.swing.*;

public class MemoryComponent {
    private int id;
    private String type;
    private int x, y;
    private boolean isVisited;
    private final int WIDTH = 100;
    private final int HEIGHT = 50;
    private int rotationAngle = 0;
    private Image icon; // Préparation pour les images

    public MemoryComponent(int id, String type, int x, int y) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.isVisited = false;
        //this.icon = loadIcon(); // À implémenter ultérieurement
    }

    // Méthodes d'accès
    public int getId() { return id; }
    public String getType() { return type; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getCenterX() { return x + WIDTH/2; }
    public int getCenterY() { return y + HEIGHT/2; }
    public void setVisited(boolean visited) { this.isVisited = visited; }
    public boolean isVisited() { return isVisited; }

    // Déplacement du composant
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    // Vérification du clic
    public boolean contains(int px, int py) {
        return px >= x && px <= x + WIDTH && 
               py >= y && py <= y + HEIGHT;
    }

    // Rotation
    public void rotate() {
        rotationAngle = (rotationAngle + 90) % 360;
    }

    // Dessin du composant
    public void draw(Graphics g, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        
        // Couleur de fond
        g2d.setColor(isSelected ? new Color(255, 100, 100) : new Color(70, 130, 180));
        
        // Applique la rotation
        g2d.rotate(Math.toRadians(rotationAngle), getCenterX(), getCenterY());
        
        // Dessine le rectangle principal
        g2d.fillRoundRect(x, y, WIDTH, HEIGHT, 15, 15);
        
        // Dessine la bordure
        g2d.setColor(isSelected ? Color.RED : Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x, y, WIDTH, HEIGHT, 15, 15);

        // Dessine le texte centré
        drawCenteredText(g2d);
        
        // Réinitialise la rotation
        g2d.rotate(-Math.toRadians(rotationAngle), getCenterX(), getCenterY());
    }

    private void drawCenteredText(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        
        FontMetrics fm = g2d.getFontMetrics();
        String text = type.toUpperCase();
        
        int textWidth = fm.stringWidth(text);
        int textX = x + (WIDTH - textWidth)/2;
        int textY = y + (HEIGHT/2) + (fm.getAscent() - fm.getDescent())/2;
        
        g2d.drawString(text, textX, textY);
    }

    // Méthode préparatoire pour les images
    private Image loadIcon() {
        try {
            // Exemple pour charger une image :
            // return new ImageIcon(getClass().getResource("/icons/" + type + ".png")).getImage();
            return null;
        } catch (Exception e) {
            System.out.println("Image non trouvée pour " + type);
            return null;
        }
    }
}