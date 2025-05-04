package com.fightclub.logisim;

import java.awt.*;
import java.util.ArrayList;

public class LedLight extends MemoryComponent {

    public LedLight(int id, int x, int y) {
        super(id, x, y);
        this.type = ComponentType.LED;
        initConnectionPoints();
    }

    @Override
    protected void initConnectionPoints() {
        inputs = new ArrayList<>(1);
        outputs = new ArrayList<>(0); // Une LED n'a pas de sortie

        // Une seule entrée centrée à gauche
        inputs.add(new ConnectionPoint(this, getX(), getY() + getHeight()/2, true));
    }

    @Override
    public void draw(Graphics g, boolean isSelected) {
        Graphics2D g2d = (Graphics2D)g;
        
        // Couleur basée sur QuadBool
        Color ledColor;
        if (getInputValue() == QuadBool.TRUE) {
            ledColor = Color.YELLOW; // Vert vif allumé
        } else if (getInputValue() == QuadBool.FALSE) {
            ledColor = Color.GRAY; // Gris foncé éteint
        } else {
            ledColor = Color.GRAY  ; // Orange pour états spéciaux
        }

        // Corps de la LED
        g2d.setColor(ledColor);
        g2d.fillOval(getX(), getY(), getWidth()-20, getHeight());



        // Contour
        g2d.setColor(isSelected ? Color.BLUE : Color.BLACK);
        g2d.drawOval(getX(), getY(), getWidth()-20, getHeight());

        // Dessin des points de connexion
        drawConnectionPoints(g2d);
    }

    private QuadBool getInputValue() {
        if (!inputs.isEmpty() && inputs.get(0).getWire() != null) {
            return inputs.get(0).getWire().getValue();
        }
        return QuadBool.NOTHING;
    }

    @Override
    public void compute() {
        // pas de compute pou le led
        throw new UnsupportedOperationException("Unimplemented method 'compute'");
    }
}