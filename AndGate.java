
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Représente une porte logique AND dans un circuit mémoire.
 * Cette porte hérite de MemoryComponent et définit son apparence graphique.
 */
public class AndGate extends MemoryComponent {

    /**
     * Construit une porte AND avec un identifiant, une position, et initialise ses points de connexion.
     * @param id identifiant unique de la porte
     * @param x position horizontale
     * @param y position verticale
     */
    public AndGate(int id, int x, int y) {
        super(id, "AND", x, y);
        initConnectionPoints();
    }

    /**
     * Dessine graphiquement la porte AND avec sa forme (rectangle + demi-cercle), son texte, ses points de connexion
     * et sa bordure si elle est sélectionnée.
     * @param g contexte graphique
     * @param isSelected vrai si la porte est actuellement sélectionnée
     */
    @Override
    public void draw(Graphics g, boolean isSelected) {
        Graphics2D g2d = (Graphics2D)g;

        // Sauvegarde de la transformation
        AffineTransform oldTransform = g2d.getTransform();

        // Translation au centre du composant
        g2d.translate(getX() + getWidth()/2, getY() + getHeight()/2);
        // Rotation
        g2d.rotate(Math.toRadians(getRotationAngle()));
        // Translation inverse pour dessiner
        g2d.translate(-getWidth()/2, -getHeight()/2);

        // Corps de la porte AND
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, getWidth()-10, getHeight()); // Partie rectangulaire
        g2d.fillArc(getWidth()-20, 0, 20, getHeight(), -90, 180); // Demi-cercle

        // Texte
        g2d.setColor(Color.BLACK);
        g2d.drawString("AND", 15, getHeight()/2 + 5);

        // Restauration de la transformation
        g2d.setTransform(oldTransform);

        // Dessin des points de connexion (coordonnées absolues)
        drawConnectionPoints(g2d);

        // Bordure de sélection
        if (isSelected) {
            g2d.setColor(Color.BLUE);
            g2d.drawRect(getX(), getY(), getWidth(), getHeight());
        }
    }
}
