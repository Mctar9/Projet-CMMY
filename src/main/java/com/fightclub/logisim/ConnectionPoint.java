
import java.awt.*;
import java.util.Objects;

/**
 * Représente un point de connexion dans un circuit logique.
 * Un point de connexion peut être une entrée ou une sortie, lié à un composant
 * mémoire parent.
 * Il est utilisé pour connecter des fils entre les composants.
 */
public class ConnectionPoint {

    // -------------- ATTRIBUTS --------------//

    private int x, y;
    private boolean isInput; // vrai si c'est une entrée, faux si c'est une sortie
    private boolean highlighted = false; // vrai si le point est surligné
    private MemoryComponent parent; // Composant parent auquel ce point est relié
    private Wire connectedWire; 

    // -------------- CONSTRUCTEURS --------------//

    /**
     * Construit un point de connexion à une position donnée.
     * 
     * @param parent  Composant mémoire auquel ce point appartient
     * @param x       Coordonnée x
     * @param y       Coordonnée y
     * @param isInput Vrai si le point est une entrée, faux si c'est une sortie
     */
    public ConnectionPoint(MemoryComponent parent, int x, int y, boolean isInput) {
        this.x = x;
        this.y = y;
        this.isInput = isInput;
        this.parent = parent;
        
    }

    // --------------SETTEURS ET GETTEURS --------------//

    /**
     * Met à jour l'état visuel de surbrillance du point.
     * 
     * @param highlighted vrai pour activer la surbrillance
     */
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    /**
     * Set la valeur logique transportée par le point de connexion.
     * 
     * @param value
     */
    public void setWire(Wire fil) {
        this.connectedWire = fil;
    }

    /**
     * @return le fil connecté à ce point de connexion
     * 
     * @author Riyad Derguini
     */
    public Wire getWire() {
        return this.connectedWire;
    }

    // -------------- AUTRES MÉTHODES --------------//`

    /**
     * Modifie la position du point.
     * 
     * @param newX nouvelle position x
     * @param newY nouvelle position y
     */
    public void updatePosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    /**
     * Vérifie si un point (px, py) est contenu dans le cercle de ce point de
     * connexion.
     * 
     * @param px Coordonnée x du point à tester
     * @param py Coordonnée y du point à tester
     * @return vrai si le point est à proximité (<= 10 pixels)
     */
    public boolean contains(int px, int py) {
        return Math.hypot(px - x, py - y) <= 10;
    }

    /**
     * Déplace ce point de connexion selon un vecteur (dx, dy).
     * 
     * @param dx déplacement en x
     * @param dy déplacement en y
     */
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * Dessine le point de connexion sur le canvas.
     * 
     * @param g2d   Contexte graphique
     * @param color Couleur de base à utiliser
     */
    public void draw(Graphics2D g2d, Color color) {
        g2d.setColor(highlighted ? Color.YELLOW : color);
        g2d.fillOval(x - 5, y - 5, 10, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x - 5, y - 5, 10, 10);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ConnectionPoint that = (ConnectionPoint) o;
        return x == that.x &&
                y == that.y &&
                isInput == that.isInput &&
                parent.getId() == that.parent.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, isInput, parent.getId());
    }

    // Getters

    /**
     * @return Coordonnée x du point
     */
    public int getX() {
        return x;
    }

    /**
     * @return Coordonnée y du point
     */
    public int getY() {
        return y;
    }

    /**
     * @return vrai si ce point est une entrée
     */
    public boolean isInput() {
        return isInput;
    }

    /**
     * @return Composant parent auquel ce point est relié
     */
    public MemoryComponent getParentComponent() {
        return parent;
    }

    /**
     * @return la valeur logique transportée par le point de connexion
     */
    public QuadBool getValue() {
        return connectedWire.getValue();
    }
}
