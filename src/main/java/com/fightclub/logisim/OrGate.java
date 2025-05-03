import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;


/**
 * Représente une porte logique OR dans un circuit mémoire.
 * Cette porte hérite de MemoryComponent et définit son apparence graphique.
 */
public class OrGate extends MemoryComponent {

    // --------------constructeur--------------//

    /**
     * Construit une porte OR avec un identifiant, une position, et initialise ses
     * points de connexion.
     * 
     * @param id identifiant unique de la porte
     * @param x  position horizontale
     * @param y  position verticale
     * @param input1 premier valeur d'entrée
     * @param input2 deuxieme valeur d'entrée
     * 
     * @author Riyad Derguini
     */
    public OrGate(int id, int x, int y, Wire f1, Wire f2) {
        // Appel du constructeur parent avec un identifiant unique et une position
        // centrée
        super(id, x, y);
        this.type = ComponentType.OR; // Type de la porte
        initConnectionPoints();
    }

    // --------------méthodes--------------//

    /**
     * Initialise les points de connexion de la porte OR.
     * Deux entrées et une sortie sont créées.
     * 
     * @author Riyad Derguini
     */
    @Override
    protected void initConnectionPoints() {
        inputs = new ArrayList<>(2);
        outputs = new ArrayList<>(1);
        
        int margin = 3;
        int w = getWidth();
        int h = getHeight();
        
        switch(getRotationAngle()) {
            case 0: // →
                inputs.add(new ConnectionPoint(this, getX() - margin, getY() + h/4, true));
                inputs.add(new ConnectionPoint(this, getX() - margin, getY() + 3*h/4, true));
                outputs.add(new ConnectionPoint(this, getX() + w + margin, getY() + h/2, false));
                break;
                
            case 90: // ↑
                inputs.add(new ConnectionPoint(this, getX() + w/4, getY() - margin, true));
                inputs.add(new ConnectionPoint(this, getX() + 3*w/4, getY() - margin, true));
                outputs.add(new ConnectionPoint(this, getX() + w/2, getY() + h + margin, false));
                break;
                
            case 180: // ←
                inputs.add(new ConnectionPoint(this, getX() + w + margin, getY() + h/4, true));
                inputs.add(new ConnectionPoint(this, getX() + w + margin, getY() + 3*h/4, true));
                outputs.add(new ConnectionPoint(this, getX() - margin, getY() + h/2, false));
                break;
                
            case 270: // ↓
                inputs.add(new ConnectionPoint(this, getX() + w/4, getY() + h + margin, true));
                inputs.add(new ConnectionPoint(this, getX() + 3*w/4, getY() + h + margin, true));
                outputs.add(new ConnectionPoint(this, getX() + w/2, getY() - margin, false));
                break;
        }
    }

    /**
     * Retourne la valeur de sortie de la porte OR.
     * Elle est calculée en effectuant l'opération logique OR sur les deux entrées.
     * Elle utilise la méthode andz et neg de QuadBool pour effectuer l'opération en utilisant la loi de morgan, ensuite
     * elle met à jour la valeur de sortie.
     * 
     * @return valeur de sortie
     * 
     * @author Riyad Derguini
     */
    @Override
    public void compute() {

        outputs.get(0).getWire().setValue(QuadBool.neg(QuadBool.neg(inputs.get(0).getValue()).andz(QuadBool.neg(inputs.get(1).getValue()))));

    }

    @Override
    public void draw(Graphics g, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(getX() + getWidth() / 2, getY() + getHeight() / 2);
        g2d.rotate(Math.toRadians(getRotationAngle()));
        g2d.translate(-getWidth() / 2, -getHeight() / 2);

        // Corps de la porte OR
        g2d.setColor(Color.LIGHT_GRAY);
        Path2D orShape = new Path2D.Double();
        int w = getWidth();
        int h = getHeight();

        orShape.moveTo(0, 0);
        orShape.curveTo(w / 3.0, 0, w - 10, 0, w, h / 2.0);
        orShape.curveTo(w - 10, h, w / 3.0, h, 0, h);
        orShape.curveTo(w / 5.0, h * 0.66, w / 5.0, h * 0.33, 0, 0);
        g2d.fill(orShape);

        // Texte
        g2d.setColor(Color.BLACK);
        g2d.drawString("OR", 10, getHeight() / 2 + 5);

        g2d.setTransform(oldTransform);
        drawConnectionPoints(g2d);

        if (isSelected) {
            g2d.setColor(Color.BLUE);
            g2d.drawRect(getX(), getY(), getWidth(), getHeight());
        }
    }
}
