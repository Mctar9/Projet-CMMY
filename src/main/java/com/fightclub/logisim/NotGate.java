package com.fightclub.logisim;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Représente une porte logique NOT dans un circuit mémoire.
 * Cette porte hérite de MemoryComponent et définit son apparence graphique.
 */
public class NotGate extends MemoryComponent {

    // --------------constructeur--------------//

    /**
     * Construit une porte NOT avec un identifiant, une position, et initialise ses
     * points de connexion.
     * 
     * @param id    identifiant unique de la porte
     * @param x     position horizontale
     * @param y     position verticale
     * @param input valeur d'entrée
     * 
     * @author Riyad Derguini
     */
    public NotGate(int id, int x, int y, Wire f) {
        // Appel du constructeur parent avec un identifiant unique et une position
        // centrée
        super(id, x, y);
        this.type = ComponentType.NOT; // Type de la porte
        initConnectionPoints();
    }

    // --------------méthodes--------------//

    /**
     * Retourne la valeur de sortie de la porte NOT.
     * Elle est calculée en effectuant l'opération logique NOT sur l'entrée.
     * Elle utilise la méthode neg de QuadBool pour effectuer l'opération, ensuite
     * elle met à jour la valeur de sortie.
     * 
     * @return valeur de sortie
     * 
     * @author Riyad Derguini
     */
    @Override
    protected void initConnectionPoints() {
        inputs = new ArrayList<>(1);
        outputs = new ArrayList<>(1);
        
        int margin = 5;
        int w = getWidth();
        int h = getHeight();
        
        switch(getRotationAngle()) {
            case 0: // →
                inputs.add(new ConnectionPoint(this, getX() - margin, getY() + h/2, true));
                outputs.add(new ConnectionPoint(this, getX() + w + margin, getY() + h/2, false));
                break;
                
            case 90: // ↑
                inputs.add(new ConnectionPoint(this, getX() + w/2, getY() - margin, true));
                outputs.add(new ConnectionPoint(this, getX() + w/2, getY() + h + margin, false));
                break;
                
            case 180: // ←
                inputs.add(new ConnectionPoint(this, getX() + w + margin, getY() + h/2, true));
                outputs.add(new ConnectionPoint(this, getX() - margin, getY() + h/2, false));
                break;
                
            case 270: // ↓
                inputs.add(new ConnectionPoint(this, getX() + w/2, getY() + h + margin, true));
                outputs.add(new ConnectionPoint(this, getX() + w/2, getY() - margin, false));
                break;
        }
    }
    

    /**
     * Retourne la valeur de sortie de la porte NOT.
     * Elle est calculée en effectuant l'opération logique NOT sur l'entrée.
     * Elle utilise la méthode neg de QuadBool pour effectuer l'opération, ensuite
     * elle met à jour la valeur de sortie.
     * 
     * @return valeur de sortie
     * 
     * @author Riyad Derguini
     */
    public void compute() {

        outputs.get(0).getWire().setValue(QuadBool.neg(this.inputs.get(0).getValue()));

    }

    @Override
    public void draw(Graphics g, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(getX() + getWidth() / 2, getY() + getHeight() / 2);
        g2d.rotate(Math.toRadians(getRotationAngle()));
        g2d.translate(-getWidth() / 2, -getHeight() / 2);

        // Corps de la porte NOT (triangle + petit cercle)
        g2d.setColor(Color.LIGHT_GRAY);
        Polygon triangle = new Polygon();
        triangle.addPoint(0, 0);
        triangle.addPoint(0, getHeight());
        triangle.addPoint(getWidth() - 10, getHeight() / 2);
        g2d.fillPolygon(triangle);

        g2d.fillOval(getWidth() - 10, getHeight() / 2 - 5, 10, 10); // cercle

        // Texte
        g2d.setColor(Color.BLACK);
        g2d.drawString("NOT", 10, getHeight() / 2 + 5);

        g2d.setTransform(oldTransform);
        drawConnectionPoints(g2d);

        if (isSelected) {
            g2d.setColor(Color.BLUE);
            g2d.drawRect(getX(), getY(), getWidth(), getHeight());
        }
    }
}
