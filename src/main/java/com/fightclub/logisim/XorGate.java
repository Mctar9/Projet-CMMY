package com.fightclub.logisim;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;

/**
 * La classe XorGate représente une porte logique XOR dans un circuit.
 * Elle hérite de la classe MemoryComponent et gère les points de connexion,
 * le calcul de la sortie, et le dessin de la porte.
 */
public class XorGate extends MemoryComponent {

    // --------------constructeur--------------//

    /**
     * Construit une porte XOR avec un identifiant, une position, et initialise ses
     * points de connexion.
     * 
     * @param id     identifiant unique de la porte
     * @param x      position horizontale
     * @param y      position verticale
     * @param input1 premier valeur d'entrée
     * @param input2 deuxieme valeur d'entrée
     * 
     * @author Riyad Derguini
     */
    public XorGate(int id, int x, int y, Wire f1, Wire f2) {
        // Appel du constructeur parent avec un identifiant unique et une position
        // centrée
        super(id, x, y);
        this.type = ComponentType.XOR; // Type de la porte
        initConnectionPoints();
    }

    // --------------méthodes--------------//


    @Override
    protected void initConnectionPoints() {
        inputs = new ArrayList<>(2);
        outputs = new ArrayList<>(1);
        
        int margin = 5; // Décalage extérieur
        int w = getWidth();
        int h = getHeight();
        
        switch(getRotationAngle()) {
            case 0: // Horizontal →
                inputs.add(new ConnectionPoint(this, getX() - margin, getY() + h/3, true));
                inputs.add(new ConnectionPoint(this, getX() - margin, getY() + 2*h/3, true));
                outputs.add(new ConnectionPoint(this, getX() + w + margin, getY() + h/2, false));
                break;
                
            case 90: // Haut ↑
                inputs.add(new ConnectionPoint(this, getX() + w/3, getY() - margin, true));
                inputs.add(new ConnectionPoint(this, getX() + 2*w/3, getY() - margin, true));
                outputs.add(new ConnectionPoint(this, getX() + w/2, getY() + h + margin, false));
                break;
                
            case 180: // Gauche ←
                inputs.add(new ConnectionPoint(this, getX() + w + margin, getY() + h/3, true));
                inputs.add(new ConnectionPoint(this, getX() + w + margin, getY() + 2*h/3, true));
                outputs.add(new ConnectionPoint(this, getX() - margin, getY() + h/2, false));
                break;
                
            case 270: // Bas ↓
                inputs.add(new ConnectionPoint(this, getX() + w/3, getY() + h + margin, true));
                inputs.add(new ConnectionPoint(this, getX() + 2*w/3, getY() + h + margin, true));
                outputs.add(new ConnectionPoint(this, getX() + w/2, getY() - margin, false));
                break;
        }
    }

    /**
     * Retourne la valeur de sortie de la porte XOR.
     * Elle est calculée en effectuant l'opération logique XOR sur les deux entrées.
     * Elle utilise les méthodes andz et neg de QuadBool pour calculer (A ET NON B)
     * et (B ET NON A),
     * puis combine les résultats avec l'opération sup pour obtenir le résultat
     * final.
     * La valeur calculée est ensuite assignée à la sortie.
     * 
     * @return valeur de sortie de la porte XOR
     */
    @Override
    public void compute() {

        QuadBool aAndNotB = inputs.get(0).getValue().andz(QuadBool.neg(inputs.get(1).getValue()));
        QuadBool bAndNotA = inputs.get(1).getValue().andz(QuadBool.neg(inputs.get(0).getValue()));

        outputs.get(0).getWire().setValue(aAndNotB.sup(bAndNotA));
    }

    @Override
    public void draw(Graphics g, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();
    
        // Transformation pour rotation
        g2d.translate(getX() + getWidth() / 2, getY() + getHeight() / 2);
        g2d.rotate(Math.toRadians(getRotationAngle()));
        g2d.translate(-getWidth() / 2, -getHeight() / 2);
    
        int width = 70; // largeur principale
        int height = getHeight();
        int extraOffset = 8; // décalage pour la 2e courbe de XOR
    
        // --- DESSIN ---
        g2d.setColor(Color.LIGHT_GRAY);
    
        // Forme principale du XOR remplie
        Path2D.Double path = new Path2D.Double();
        path.moveTo(0, 0);
        path.curveTo(width / 2.0, 0, width / 2.0, height, 0, height);
        path.curveTo(width / 3.0, height, width - 10, height, width + 5, height / 2.0);
        path.curveTo(width - 10, 0, width / 3.0, 0, 0, 0);
        g2d.fill(path); // remplissage gris clair
    
        // Petite courbe supplémentaire du XOR
        Path2D.Double extraCurve = new Path2D.Double();
        extraCurve.moveTo(-extraOffset, 0);
        extraCurve.curveTo(width / 2.0 - extraOffset, 0, width / 2.0 - extraOffset, height, -extraOffset, height);
        g2d.draw(extraCurve);
    
        // --- TEXTE (facultatif) ---
        g2d.setColor(Color.BLACK);
        g2d.drawString("XOR", width / 3, height / 2 + 5);
    
        g2d.setTransform(oldTransform);
        drawConnectionPoints(g2d);
    
    }
}