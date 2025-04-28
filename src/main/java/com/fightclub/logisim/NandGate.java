import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
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
    public NandGate(int id, int x, int y, QuadBool input1, QuadBool input2) {
        // Appel du constructeur parent avec un identifiant unique et une position
        // centrée
        super(id, x, y);
        this.type = ComponentType.NAND; // Type de la porte
        initConnectionPoints();
        this.inputs.get(0).setValue(input1);
        this.inputs.get(1).setValue(input2);
        ;
    }

    // --------------méthodes--------------//

    /**
     * Initialise les points de connexion de la porte NAND.
     * Deux entrées et une sortie sont créées.
     * 
     * @author Riyad Derguini
     */
    @Override
    public void initConnectionPoints() {
        inputs = new ArrayList<ConnectionPoint>(2);
        outputs = new ArrayList<ConnectionPoint>(1);

        // Points de connexion d'entrée
        inputs.add(new ConnectionPoint(this, 0, getHeight() / 2, true));
        inputs.add(new ConnectionPoint(this, 0, getHeight() / 2 + 20, true));

        // Point de connexion de sortie
        outputs.add(new ConnectionPoint(this, getWidth(), getHeight() / 2 + 10, false));

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

        outputs.get(0).setValue(QuadBool.neg(inputs.get(0).getValue().andz(inputs.get(1).getValue())));

    }

    @Override
    public void draw(Graphics g, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();

        // Transformation pour rotation
        g2d.translate(getX() + getWidth() / 2, getY() + getHeight() / 2);
        g2d.rotate(Math.toRadians(getRotationAngle()));
        g2d.translate(-getWidth() / 2, -getHeight() / 2);

        // Dessin de la base AND (60px de large + 20px d'arc)
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, 60, getHeight()); // Partie rectangulaire
        g2d.fillArc(50, 0, 30, getHeight(), -90, 180); // Arc de sortie

        // Bulle de négation (positionnée après l'arc)
        g2d.setColor(Color.WHITE);
        g2d.fillOval(75, getHeight() / 2 - 7, 14, 14);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(75, getHeight() / 2 - 7, 14, 14);

        // Texte centré
        g2d.drawString("NAND", 20, getHeight() / 2 + 5);

        g2d.setTransform(oldTransform);
        drawConnectionPoints(g2d);
    }

}