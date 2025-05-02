import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant un composant mémoire logique (porte logique,
 * etc.).
 * Contient la position, les entrées/sorties et les méthodes de dessin et
 * déplacement.
 */
public abstract class MemoryComponent {

    // -------------- ATTRIBUTS --------------//

    private int id;
    protected ComponentType type; // type de composant (ET, OU, NON, etc.)
    private int x, y;
    private boolean isVisited; // état de visite pour l'algorithme de parcours
    private final int WIDTH = 80;
    private final int HEIGHT = 60;
    private int rotationAngle = 0;
    protected List<ConnectionPoint> inputs; // liste des points de connexion d'entrée
    protected List<ConnectionPoint> outputs; // liste des points de connexion de sortie

    // -------------- CONSTRUCTEUR --------------//

    /**
     * Constructeur d'un composant mémoire centré sur les coordonnées données.
     * 
     * @param id Identifiant du composant
     * @param x  Coordonnée X du clic
     * @param y  Coordonnée Y du clic
     */
    public MemoryComponent(int id, int x, int y) {
        this.id = id;
        this.x = x - WIDTH / 2;
        this.y = y - HEIGHT / 2;
        this.isVisited = false;
    }

    // -------------GETTEURS/SETTEURS --------------//

    public int getId() {
        return id;
    }

    public ComponentType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCenterX() {
        return x + WIDTH / 2;
    }

    public int getCenterY() {
        return y + HEIGHT / 2;
    }

    public void setVisited(boolean visited) {
        this.isVisited = visited;
    }

    public boolean isVisited() {
        return isVisited;
    }

    protected int getRotationAngle() {
        return rotationAngle;
    }

    protected int getWidth() {
        return WIDTH;
    }

    protected int getHeight() {
        return HEIGHT;
    }

    public List<ConnectionPoint> getInputs() {
        return inputs;
    }

    public List<ConnectionPoint> getOutputs() {
        return outputs;
    }

    public List<ConnectionPoint> getAllConnectionPoints() {
        List<ConnectionPoint> allPoints = new ArrayList<>(inputs);
        allPoints.addAll(outputs);
        return allPoints;
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    protected void setRotationAngle(int angle) {
        this.rotationAngle = angle;
    }

    // -------------- MÉTHODES --------------//

    /**
     * Méthode abstraite pour calculer la sortie du composan Doit être implémentée
     * par les sous-classes.
     * Chaque sous-classe doit définir comment elle calcule sa sortie en fonction de
     * ses entrées.
     */
    public abstract void compute();

    /**
     * Déplace le composant en centrant sur la nouvelle position et met à jour les
     * points de connexion.
     * 
     * @param newX Nouvelle coordonnée X
     * @param newY Nouvelle coordonnée Y
     */
    public void moveTo(int newX, int newY) {
        int dx = newX - (x + WIDTH / 2);
        int dy = newY - (y + HEIGHT / 2);
        x = newX - WIDTH / 2;
        y = newY - HEIGHT / 2;
        for (ConnectionPoint point : inputs) {
            point.move(dx, dy);
        }
        for (ConnectionPoint point : outputs) {
            point.move(dx, dy);
        }
    }

    /**
     * Vérifie si un point (px, py) est à l'intérieur du composant.
     * 
     * @param px Coordonnée X
     * @param py Coordonnée Y
     * @return true si le point est à l'intérieur
     */
    public boolean contains(int px, int py) {
        return px >= x && px <= x + WIDTH &&
                py >= y && py <= y + HEIGHT;
    }

    /**
     * Applique une rotation de 90° au composant.
     */
    public void rotate() {
        rotationAngle = (rotationAngle + 90) % 360;
    }

    /**
     * Méthode abstraite pour dessiner le composant.
     * 
     * @param g          Contexte graphique
     * @param isSelected true si le composant est sélectionné
     */
    public abstract void draw(Graphics g, boolean isSelected);

    /**
     * Dessine les points de connexion (rouge pour entrées, vert pour sorties).
     * 
     * @param g2d Contexte graphique 2D
     */
    protected void drawConnectionPoints(Graphics2D g2d) {
        for (ConnectionPoint point : inputs) {
            point.draw(g2d, Color.RED);
        }
        for (ConnectionPoint point : outputs) {
            point.draw(g2d, Color.GREEN);
        }
    }

    /**
     * Renvoie un point de connexion situé à des coordonnées données.
     * 
     * @param px Coordonnée X
     * @param py Coordonnée Y
     * @return Le point trouvé ou null
     */
    public ConnectionPoint getConnectionPointAt(int px, int py) {
        for (ConnectionPoint point : inputs) {
            if (point.contains(px, py))
                return point;
        }
        for (ConnectionPoint point : outputs) {
            if (point.contains(px, py))
                return point;
        }
        return null;
    }

    /**
     * Met à jour la position des points de connexion en fonction de la position du
     * composant.
     */
    public void updateConnectionPoints() {
        for (ConnectionPoint point : inputs) {
            point.updatePosition(
                    x + (point.isInput() ? 0 : WIDTH),
                    y + HEIGHT / 3);
        }
        for (ConnectionPoint point : outputs) {
            point.updatePosition(
                    x + (point.isInput() ? 0 : WIDTH),
                    y + HEIGHT / 2);
        }
    }

    /**
     * Initialise ou met à jour les points de connexion du composant.
     */
    protected void initConnectionPoints() {
        if (inputs.isEmpty()) {
            inputs.add(new ConnectionPoint(this, x, y + HEIGHT / 3, true));
            inputs.add(new ConnectionPoint(this, x, y + 2 * HEIGHT / 3, true));
        } else {
            inputs.get(0).updatePosition(x, y + HEIGHT / 3);
            inputs.get(1).updatePosition(x, y + 2 * HEIGHT / 3);
        }

        if (outputs.isEmpty()) {
            outputs.add(new ConnectionPoint(this, x + WIDTH, y + HEIGHT / 2, false));
        } else {
            outputs.get(0).updatePosition(x + WIDTH, y + HEIGHT / 2);
        }
    }

    /**
     * Vérifie si un fil est connecté à ce composant.
     * 
     * @param wire Le fil à tester
     * @return true si connecté à une entrée ou une sortie
     */
    public boolean isConnectedTo(Wire wire) {
        return inputs.contains(wire.getEnd()) || outputs.contains(wire.getStart());
    }

    public QuadBool calculerSortie() {
        this.compute();
        return outputs.get(0).getWire().getValue();
        
    }

  
}
