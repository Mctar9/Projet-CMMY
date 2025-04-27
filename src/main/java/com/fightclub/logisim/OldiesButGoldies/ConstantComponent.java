import java.awt.*;

/**
 * Représente un composant constant (0 ou 1) dans un circuit logique.
 * Comme les autres portes logiques mais avec une valeur de sortie fixe et aucune entrée.
 */
public class ConstantComponent extends MemoryComponent {
    private final String value; // "0" ou "1"

    /**
     * Construit un composant constant à la position spécifiée.
     * @param id Identifiant unique du composant
     * @param value Valeur constante ("0" ou "1")
     * @param x Position horizontale
     * @param y Position verticale
     */
    public ConstantComponent(int id, String value, int x, int y) {
        super(id, "CONST_" + value, x, y);
        this.value = value;
        initConnectionPoints(); // Initialise les points de connexion hérités de MemoryComponent
    }

    /**
     * Dessine le composant sur le circuit.
     * @param g Contexte graphique
     * @param isSelected Vrai si le composant est sélectionné
     */
    @Override
    public void draw(Graphics g, boolean isSelected) {
        // Dessine le corps du composant (couleur différente pour 0 et 1)
        g.setColor(value.equals("1") ? Color.GREEN : Color.RED);
        g.fillRect(getX(), getY(), getWidth(), getHeight());

        // Affiche la valeur
        g.setColor(Color.BLACK);
        g.drawString(value, getX() + 10, getY() + 15);

        // Mise en évidence si sélectionné
        if (isSelected) {
            g.setColor(Color.BLUE);
            g.drawRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    /**
     * Retourne la valeur de sortie constante.
     * @return "0" ou "1" selon le composant
     */
    public String getOutputValue() {
        return value;
    }

    /**
     * Désactive les entrées (car un composant constant n'a pas d'entrée)
     */
    @Override
    public void setInputWire(Wire wire) {
        throw new UnsupportedOperationException("Un composant constant n'accepte pas d'entrée");
    }
}