import java.awt.*;

/**
 * Représente une LED dans un circuit logique.
 * La LED s'allume en vert lorsque le signal d'entrée est à 1, sinon elle reste éteinte.
 */
public class LedLight extends MemoryComponent {
    private Wire inputWire; // Le fil connecté à l'entrée de la LED

    /**
     * Construit une LED à la position spécifiée.
     * @param id Identifiant unique
     * @param x Position en x
     * @param y Position en y
     */
    public LedLight(int id, int x, int y) {
        super(id, x, y);
        initConnectionPoints();
    }

    /**
     * Dessine la LED sur le circuit.
     * @param g Contexte graphique
     * @param isSelected Vrai si le composant est sélectionné
     */
    @Override
    public void draw(Graphics g, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;

        // Définit la couleur de la LED selon le signal d'entrée
        Color ledColor = Color.DARK_GRAY; // État éteint par défaut
        if (inputWire != null && "1".equals(inputWire.getValue())) {
            ledColor = new Color(152, 195, 121); // Vert clair lorsqu'allumée
        }

        // Dessine le corps de la LED
        g2d.setColor(ledColor);
        g2d.fillOval(getX(), getY(), getWidth(), getHeight());

        // Dessine le contour de la LED
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawOval(getX(), getY(), getWidth(), getHeight());

        // Dessine le label
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        g2d.drawString("LED", getX() + getWidth()/4, getY() + getHeight()/2);

        // Dessine la surbrillance si sélectionnée
        if (isSelected) {
            g2d.setColor(new Color(97, 175, 239));
            g2d.drawRect(getX(), getY(), getWidth(), getHeight());
        }

        drawConnectionPoints(g2d);
    }

    /**
     * Définit le fil d'entrée pour cette LED.
     * @param wire Le fil d'entrée à connecter
     */
    public void setInputWire(Wire wire) {
        this.inputWire = wire;
    }

    /**
     * Vérifie l'état actuel de la LED.
     * @return Vrai si la LED est allumée (entrée à 1), faux sinon
     */
    public boolean isLit() {
        return inputWire != null && "1".equals(inputWire.getValue());
    }

    @Override
    public void compute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compute'");
    }
}