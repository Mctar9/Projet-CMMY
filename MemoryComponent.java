import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class MemoryComponent {
    private int id;
    private String type;
    private int x, y;
    private boolean isVisited;
    private final int WIDTH = 80;
    private final int HEIGHT = 60;
    private int rotationAngle = 0;
    private Image image;

    public MemoryComponent(int id, String type, int x, int y) {
        this.id = id;
        this.type = type;
        this.x = x - WIDTH / 2; // Centrage sur le point de clic
        this.y = y - HEIGHT / 2;
        this.isVisited = false;
        this.image = loadImage();
    }

    // Méthodes d'accès
    public int getId() { return id; }
    public String getType() { return type; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getCenterX() { return x + WIDTH / 2; }
    public int getCenterY() { return y + HEIGHT / 2; }
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

    // Dessin du composant avec image
    public void draw(Graphics g, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform originalTransform = g2d.getTransform();

        // Rotation autour du centre
        g2d.rotate(Math.toRadians(rotationAngle), 
                  x + WIDTH / 2.0, 
                  y + HEIGHT / 2.0);

        // Dessin de l'image
        g2d.drawImage(image, x, y, null);

        // Bordure de sélection
        if (isSelected) {
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(x, y, WIDTH, HEIGHT);
        }

        g2d.setTransform(originalTransform);
    }

    // Chargement de l'image
    private Image loadImage() {
        try {
            // Chemin absolu depuis la racine des ressources
            String imagePath = "/img/" + this.type.toLowerCase() + ".png";
            System.out.println("Trying to load: " + imagePath); // Debug
            
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
            if (icon.getImage() == null) throw new Exception();
            
            return icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.err.println("ERREUR: Image non trouvée pour " + type);
            return createFallbackImage();
        }
    }

    // Image de secours
    private BufferedImage createFallbackImage() {
        BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        // Fond semi-transparent
        g2d.setColor(new Color(255, 0, 0, 100));
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        
        // Texte d'erreur
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        String text = "Image manquante";
        int textWidth = g2d.getFontMetrics().stringWidth(text);
        g2d.drawString(text, (WIDTH - textWidth)/2, HEIGHT/2);
        
        g2d.dispose();
        return img;
    }
}   