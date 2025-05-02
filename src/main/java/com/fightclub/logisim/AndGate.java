
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
    public void initConnectionPoints() {
        inputs = new ArrayList<ConnectionPoint>(2);
        outputs = new ArrayList<ConnectionPoint>(1);

        // Points de connexion d'entrée
        inputs.add(new ConnectionPoint(this, getX(), getY() + getHeight() / 3, true));        // Première entrée (en haut à gauche)
        inputs.add(new ConnectionPoint(this, getX(), getY() + 2 * getHeight() / 3, true));     // Deuxième entrée (en bas à gauche)

        // Point de connexion de sortie
        outputs.add(new ConnectionPoint(this, getX() + getWidth(), getY() + getHeight() / 2, false)); // Sortie au milieu à droite
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
        g2d.drawString("AND", 15, getHeight() / 2 + 5);

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
