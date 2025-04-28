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
    public XorGate(int id, int x, int y, QuadBool input1, QuadBool input2) {
        // Appel du constructeur parent avec un identifiant unique et une position
        // centrée
        super(id, x, y);
        this.type = ComponentType.XOR; // Type de la porte
        initConnectionPoints();
        this.inputs.get(0).setValue(input1);
        this.inputs.get(1).setValue(input2);
    }

    // --------------méthodes--------------//

    /**
     * Initialise les points de connexion de la porte OR.
     * Deux entrées et une sortie sont créées.
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

        outputs.get(0).setValue(aAndNotB.sup(bAndNotA));
    }

    @Override
    public void draw(Graphics g, boolean isSelected) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }

}