import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;

/**
 * Représente un composant constant (0 ou 1) dans un circuit logique.
 * Comme les autres portes logiques mais avec une valeur de sortie fixe et
 * aucune entrée.
 */
public class ConstantComponent extends MemoryComponent {

    // -------------- ATTRIBUTS --------------//
    private final QuadBool value;  // Valeur constante (0 ou 1)

    // -------------- CONSTRUCTEUR --------------//

    /**
     * Construit un composant constant à la position spécifiée.
     * 
     * @param id    Identifiant unique du composant
     * @param value Valeur constante ("0" ou "1")
     * @param x     Position horizontale
     * @param y     Position verticale
     */
    public ConstantComponent(int id, QuadBool value, int x, int y) {
        super(id, x, y);
        this.value = value;
        this.type = value == QuadBool.TRUE ? ComponentType.HIGH : ComponentType.LOW;
        this.inputs = new ArrayList<>(); // Pas d'entrées
        this.outputs = new ArrayList<>();
        initConnectionPoints();
    }

    /**
     * Initialise ou met à jour les points de connexion (aucun pour un composant constant).
     */
    @Override
    protected void initConnectionPoints() {
        outputs.clear();
        // Une seule sortie centrée à droite
        outputs.add(new ConnectionPoint(this, getX() + getWidth(), getY() + getHeight()/2, false));
    }

    /**
     * Retourne la valeur de sortie constante.
     * 
     * @return "0" ou "1" selon le composant
     */
    public QuadBool getOutputValue() {
        return value;
    }

    /**
     * Cette méthode n'est pas utilisée car un composant constant n'a pas d'entrée.
     
    @Override
    public void setInputWire(Wire wire) {
        // Pas d'entrée pour un composant constant
        throw new UnsupportedOperationException("Un composant constant n'accepte pas d'entrée");
    }
        */

    /**
     * Cette méthode n'est pas utilisée pour un composant constant.
     */
    @Override
    public void compute() {
        if (!outputs.isEmpty()) {
            outputs.get(0).getWire().setValue(value);
        }
    }

    // -------------- DESSIN --------------//

    /**
     * Dessine le composant constant (0 ou 1) sur le circuit.
     * 
     * @param g          Contexte graphique
     * @param isSelected Vrai si le composant est sélectionné
        */
        
        @Override
        public void draw(Graphics g, boolean isSelected) {
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform oldTransform = g2d.getTransform();
    
            // Applique la rotation
            g2d.translate(getX() + getWidth()/2, getY() + getHeight()/2);
            g2d.rotate(Math.toRadians(getRotationAngle()));
            g2d.translate(-getWidth()/2, -getHeight()/2);
    
            // Dessin du cercle
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillOval(0, 0, getWidth(), getHeight());
    
            // Texte de la valeur
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            String displayText = value == QuadBool.TRUE ? "1" : "0";
            g2d.drawString(displayText, getWidth()/2 - 8, getHeight()/2 + 8);
    
            g2d.setTransform(oldTransform);
    
            // Points de connexion
            drawConnectionPoints(g2d);
            
            // Bordure de sélection
            if (isSelected) {
                g2d.setColor(Color.BLUE);
                g2d.drawOval(getX(), getY(), getWidth(), getHeight());
            }
        }
}
