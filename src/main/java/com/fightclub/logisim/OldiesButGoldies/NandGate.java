import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class NandGate extends MemoryComponent {
    public NandGate(int id, int x, int y) {
        super(id, "Nand", x, y);
        initConnectionPoints();
    }
    @Override
    public void draw(Graphics g, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();
        
        // Transformation pour rotation
        g2d.translate(getX() + getWidth()/2, getY() + getHeight()/2);
        g2d.rotate(Math.toRadians(getRotationAngle()));
        g2d.translate(-getWidth()/2, -getHeight()/2);
    
        // Dessin de la base AND (60px de large + 20px d'arc)
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, 60, getHeight()); // Partie rectangulaire
        g2d.fillArc(50, 0, 30, getHeight(), -90, 180); // Arc de sortie
    
        // Bulle de négation (positionnée après l'arc)
        g2d.setColor(Color.WHITE);
        g2d.fillOval(75, getHeight()/2 - 7, 14, 14);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(75, getHeight()/2 - 7, 14, 14);
    
        // Texte centré
        g2d.drawString("NAND", 20, getHeight()/2 + 5);
    
        g2d.setTransform(oldTransform);
        drawConnectionPoints(g2d);
    }

}