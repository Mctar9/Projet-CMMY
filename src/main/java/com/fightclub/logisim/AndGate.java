
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;

/**
 * Représente une porte logique AND dans un circuit mémoire.
 * Cette porte hérite de MemoryComponent et définit son apparence graphique.
 */
public class AndGate extends MemoryComponent {

    // --------------constructeur--------------//

    /**
     * Construit une porte AND avec un identifiant, une position, et initialise ses
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
    public AndGate(int id, int x, int y, Wire f1, Wire f2) {
        // Appel du constructeur parent avec un identifiant unique et une position
        // centrée
        super(id, x, y);
        this.type = ComponentType.AND; // Type de la porte
        initConnectionPoints();
    }

    // --------------méthodes--------------//

    

    /**
     * Initialise les points de connexion de la porte AND.
     * Deux entrées et une sortie sont créées.
     * 
     * @author Riyad Derguini
     */
    @Override
    protected void initConnectionPoints() {
        inputs = new ArrayList<>(2);
        outputs = new ArrayList<>(1);
        
        // Positions de base (0 degré)
        int baseX = getX();
        int baseY = getY();
        
        switch(getRotationAngle()) {
            case 0:
                inputs.add(new ConnectionPoint(this, baseX, baseY + getHeight()/3, true));
                inputs.add(new ConnectionPoint(this, baseX, baseY + 2*getHeight()/3, true));
                outputs.add(new ConnectionPoint(this, baseX + getWidth(), baseY + getHeight()/2, false));
                break;
            case 90:
                inputs.add(new ConnectionPoint(this, baseX + getWidth()/3, baseY, true));
                inputs.add(new ConnectionPoint(this, baseX + 2*getWidth()/3, baseY, true));
                outputs.add(new ConnectionPoint(this, baseX + getWidth()/2, baseY + getHeight(), false));
                break;
            case 180:
                inputs.add(new ConnectionPoint(this, baseX + getWidth(), baseY + getHeight()/3, true));
                inputs.add(new ConnectionPoint(this, baseX + getWidth(), baseY + 2*getHeight()/3, true));
                outputs.add(new ConnectionPoint(this, baseX, baseY + getHeight()/2, false));
                break;
            case 270:
                inputs.add(new ConnectionPoint(this, baseX + getWidth()/3, baseY + getHeight(), true));
                inputs.add(new ConnectionPoint(this, baseX + 2*getWidth()/3, baseY + getHeight(), true));
                outputs.add(new ConnectionPoint(this, baseX + getWidth()/2, baseY, false));
                break;
        }
    }

    /**
     * Retourne la valeur de sortie de la porte AND.
     * Elle est calculée en effectuant l'opération logique AND sur les deux entrées.
     * Elle utilise la méthode andz de QuadBool pour effectuer l'opération, ensuite
     * elle met à jour la valeur de sortie.
     * 
     * @return valeur de sortie
     * 
     * @author Riyad Derguini
     */
    @Override
    public void compute() {

        outputs.get(0).getWire().setValue(inputs.get(0).getValue().andz(inputs.get(1).getValue()));

    }

    // -------partie graphique-------//

    /**
     * Dessine graphiquement la porte AND avec sa forme (rectangle + demi-cercle),
     * son texte, ses points de connexion
     * et sa bordure si elle est sélectionnée.
     * 
     * @param g          contexte graphique
     * @param isSelected vrai si la porte est actuellement sélectionnée
     */
    @Override
    public void draw(Graphics g, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;

        // Sauvegarde de la transformation
        AffineTransform oldTransform = g2d.getTransform();

        // Translation au centre du composant
        g2d.translate(getX() + getWidth() / 2, getY() + getHeight() / 2);
        // Rotation
        g2d.rotate(Math.toRadians(getRotationAngle()));
        // Translation inverse pour dessiner
        g2d.translate(-getWidth() / 2, -getHeight() / 2);

        // Corps de la porte AND
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, getWidth() - 10, getHeight()); // Partie rectangulaire
        g2d.fillArc(getWidth() - 20, 0, 20, getHeight(), -90, 180); // Demi-cercle

        // Texte
        g2d.setColor(Color.BLACK);
        g2d.drawString(type.toString(), 15, getHeight() / 2 + 5);

        // Restauration de la transformation
        g2d.setTransform(oldTransform);

        // Dessin des points de connexion (coordonnées absolues)
        drawConnectionPoints(g2d);

        // Bordure de sélection
        if (isSelected) {
            g.setColor(Color.BLUE);
            ((Graphics2D)g).setStroke(new BasicStroke(2));
            g.drawRect(getX()-2, getY()-2, getWidth()+4, getHeight()+4);
        }
    
    }
}
