import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un fil reliant deux points de connexion dans un circuit logique.
 * Le fil est défini par un point de départ (output) et un point d'arrivée (input).
 * Il peut également avoir plusieurs connexions intermédiaires.
 */
public class Wire {
    /**
     * Valeur logique transportée par le fil (ex: "0", "1", ou "undefined").
     */
    private String value;

    /**
     * Liste des points connectés à ce fil.
     */
    private List<ConnectionPoint> connections;

    /**
     * Point de départ du fil (doit être une sortie).
     */
    private ConnectionPoint start;

    /**
     * Point d'arrivée du fil (doit être une entrée).
     */
    private ConnectionPoint end;

    /**
     * Construit un fil entre deux points de connexion.
     * @param start Point de départ (doit être une sortie)
     * @param end Point d'arrivée (doit être une entrée)
     * @throws IllegalArgumentException si les directions ne sont pas respectées
     */
    public Wire(ConnectionPoint start, ConnectionPoint end) {
        this.value = "undefined";
        this.connections = new ArrayList<>();
        this.start = start;
        this.end = end;
        connections.add(start);
        connections.add(end);

        if (start.isInput() || !end.isInput()) {
            throw new IllegalArgumentException("Un fil doit connecter une sortie (output) à une entrée (input)");
        }
    }

    /**
     * Retourne la valeur logique du fil.
     * @return valeur logique
     */
    public String getValue() {
        return value;
    }

    /**
     * Définit la valeur logique du fil.
     * @param value valeur à attribuer
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Ajoute un point de connexion au fil s'il n'est pas déjà présent.
     * @param point point à ajouter
     */
    public void addConnection(ConnectionPoint point) {
        if (!connections.contains(point)) {
            connections.add(point);
        }
    }

    /**
     * Retourne la liste des points connectés au fil.
     * @return liste des connexions
     */
    public List<ConnectionPoint> getConnections() {
        return new ArrayList<>(connections);
    }

    /**
     * Retourne le point de départ du fil.
     * @return point de départ
     */
    public ConnectionPoint getStart() {
        return start;
    }

    /**
     * Retourne le point d'arrivée du fil.
     * @return point d'arrivée
     */
    public ConnectionPoint getEnd() {
        return end;
    }

    /**
     * Modifie le point de départ du fil si c'est une sortie.
     * @param start nouveau point de départ
     */
    public void setStart(ConnectionPoint start) {
        if (!start.isInput()) {
            this.start = start;
            if (!connections.contains(start)) {
                connections.add(start);
            }
        }
    }

    /**
     * Modifie le point d'arrivée du fil si c'est une entrée.
     * @param end nouveau point d'arrivée
     */
    public void setEnd(ConnectionPoint end) {
        if (end.isInput()) {
            this.end = end;
            if (!connections.contains(end)) {
                connections.add(end);
            }
        }
    }

    //-------------- INTERFACE GRAPHIQUE --------------//

    /**
     * Dessine le fil sur l'interface graphique.
     * @param g contexte graphique
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.BLUE);
        g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());

        g2d.setColor(Color.CYAN);
        g2d.fillOval(start.getX() - 3, start.getY() - 3, 6, 6);
        g2d.fillOval(end.getX() - 3, end.getY() - 3, 6, 6);
    }

    /**
     * Vérifie si un point (x, y) est proche du fil, avec une tolérance donnée.
     * @param x coordonnée x
     * @param y coordonnée y
     * @param tolerance marge d'erreur
     * @return vrai si le point est proche du fil
     */
    public boolean isPointOnWire(int x, int y, int tolerance) {
        return Line2D.ptSegDist(
            start.getX(), start.getY(),
            end.getX(), end.getY(),
            x, y
        ) < tolerance;
    }

    /**
     * Met à jour la position du fil (placeholder si les points se déplacent dynamiquement).
     */
    public void updatePosition() {
        // Les ConnectionPoints sont mis à jour automatiquement via MemoryComponent
    }

    /**
     * Vérifie si ce fil est connecté à un composant mémoire donné.
     * @param comp composant à vérifier
     * @return vrai si connecté
     */
    public boolean isConnectedTo(MemoryComponent comp) {
        for (ConnectionPoint input : comp.getInputs()) {
            if (input.equals(end)) return true;
        }
        for (ConnectionPoint output : comp.getOutputs()) {
            if (output.equals(start)) return true;
        }
        return false;
    }
}
