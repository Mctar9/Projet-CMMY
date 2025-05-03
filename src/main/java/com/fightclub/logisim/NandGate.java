import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Représente une porte logique NAND dans un circuit mémoire.
 * Cette porte hérite de MemoryComponent et définit son apparence graphique.
 */
public class NandGate extends MemoryComponent {

    // --------------constructeur--------------//

    /**
     * Constructeur de la porte NAND.
     * Initialise les points de connexion et définit le type de la porte.
     * 
     * @param id     identifiant unique de la porte
     * @param x      position horizontale
     * @param y      position verticale
     * @param input1 premier valeur d'entrée
     * @param input2 deuxieme valeur d'entrée
     * 
     * @author Riyad Derguini
     */
    public NandGate(int id, int x, int y, Wire f1, Wire f2) {
        // Appel du constructeur parent avec un identifiant unique et une position
        // centrée
        super(id, x, y);
        this.type = ComponentType.NAND; // Type de la porte
        initConnectionPoints();
    }

    // --------------méthodes--------------//

    /**
     * Initialise les points de connexion de la porte NAND.
     * Deux entrées et une sortie sont créées.
     * 
     * @author Riyad Derguini
     */
    @Override
    protected void initConnectionPoints() {
        inputs = new ArrayList<>(2);
        outputs = new ArrayList<>(1);
        
        int margin = 2;
        int w = getWidth();
        int h = getHeight();
        
        switch(getRotationAngle()) {
            case 0: // →
                inputs.add(new ConnectionPoint(this, getX() - margin, getY() + h/3, true));
                inputs.add(new ConnectionPoint(this, getX() - margin, getY() + 2*h/3, true));
                outputs.add(new ConnectionPoint(this, getX() + w + margin, getY() + h/2, false));
                break;
                
            case 90: // ↑
                inputs.add(new ConnectionPoint(this, getX() + w/3, getY() - margin, true));
                inputs.add(new ConnectionPoint(this, getX() + 2*w/3, getY() - margin, true));
                outputs.add(new ConnectionPoint(this, getX() + w/2, getY() + h , false));
                break;
                
            case 180: // ←
                inputs.add(new ConnectionPoint(this, getX() + w + margin, getY() + h/3, true));
                inputs.add(new ConnectionPoint(this, getX() + w + margin, getY() + 2*h/3, true));
                outputs.add(new ConnectionPoint(this, getX() - margin, getY() + h/2, false));
                break;
                
            case 270: // ↓
                inputs.add(new ConnectionPoint(this, getX() + w/3, getY() + h + margin, true));
                inputs.add(new ConnectionPoint(this, getX() + 2*w/3, getY() + h + margin, true));
                outputs.add(new ConnectionPoint(this, getX() + w/2, getY() - margin, false));
                break;
        }
    }
    

    /**
     * Retourne la valeur de sortie de la porte NAND.
     * Elle est calculée en effectuant l'opération logique NAND sur les deux
     * entrées.
     * Elle utilise la méthode andz et neg de QuadBool pour effectuer l'opération,
     * ensuite
     * elle met à jour la valeur de sortie.
     * 
     * @return valeur de sortie
     * 
     * @author Riyad Derguini
     */
    @Override
    public void compute() {

        outputs.get(0).getWire().setValue(QuadBool.neg(inputs.get(0).getValue().andz(inputs.get(1).getValue())));

    }
    @Override
    public void draw(Graphics g, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();
    
        // Transformation pour rotation
        g2d.translate(getX() + getWidth() / 2, getY() + getHeight() / 2);
        g2d.rotate(Math.toRadians(getRotationAngle()));
        g2d.translate(-getWidth() / 2, -getHeight() / 2);
    
        // Dessin de la base AND
        int rectWidth = 50; // Réduit de 10px
        int arcWidth = 20;
        int totalWidthWithoutBubble = rectWidth + arcWidth;
        int bubbleDiameter = 14;
    
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, rectWidth, getHeight()); // Partie rectangulaire
        g2d.fillArc(rectWidth - 10, 0, arcWidth, getHeight(), -90, 180); // Arc (décalé pour garder la forme)
    
        // Bulle de négation
        int bubbleCenterX = totalWidthWithoutBubble; // Juste après l'arc
        g2d.setColor(Color.WHITE);
        g2d.fillOval(bubbleCenterX - bubbleDiameter / 2, getHeight() / 2 - bubbleDiameter / 2, bubbleDiameter, bubbleDiameter);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(bubbleCenterX - bubbleDiameter / 2, getHeight() / 2 - bubbleDiameter / 2, bubbleDiameter, bubbleDiameter);
    
        // Texte centré
        g2d.setColor(Color.BLACK);
        g2d.drawString("NAND", 10, getHeight() / 2 + 5);
    
        g2d.setTransform(oldTransform);
        drawConnectionPoints(g2d);
    }
}