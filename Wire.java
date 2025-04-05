import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Wire {
    private String value;
    private List<ConnectionPoint> connections;
    private ConnectionPoint start;
    private ConnectionPoint end;

    public Wire(ConnectionPoint start, ConnectionPoint end) {
        this.value = "undefined";
        this.connections = new ArrayList<>();
        this.start = start;
        this.end = end;
        connections.add(start);
        connections.add(end);
        
        // Validation de la connexion
        if (start.isInput() || !end.isInput()) {
            throw new IllegalArgumentException("Un fil doit connecter une sortie (output) à une entrée (input)");
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addConnection(ConnectionPoint point) {
        if (!connections.contains(point)) {
            connections.add(point);
        }
    }

    public List<ConnectionPoint> getConnections() {
        return new ArrayList<>(connections);
    }

    public ConnectionPoint getStart() {
        return start;
    }

    public ConnectionPoint getEnd() {
        return end;
    }

    public void setStart(ConnectionPoint start) {
        if (!start.isInput()) { // Doit être une sortie
            this.start = start;
            if (!connections.contains(start)) {
                connections.add(start);
            }
        }
    }

    public void setEnd(ConnectionPoint end) {
        if (end.isInput()) { // Doit être une entrée
            this.end = end;
            if (!connections.contains(end)) {
                connections.add(end);
            }
        }
    }

    //-------------- INTERFACE GRAPHIQUE --------------//

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        // Dessin de la ligne principale
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.BLUE);
        g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
        
        // Cercles aux extrémités
        g2d.setColor(Color.CYAN);
        g2d.fillOval(start.getX() - 3, start.getY() - 3, 6, 6);
        g2d.fillOval(end.getX() - 3, end.getY() - 3, 6, 6);
    }
    
    public boolean isPointOnWire(int x, int y, int tolerance) {
        return Line2D.ptSegDist(
            start.getX(), start.getY(),
            end.getX(), end.getY(),
            x, y
        ) < tolerance;
    }
    
    // Nouvelle méthode pour mettre à jour la position lors des déplacements
    public void updatePosition() {
        // Les ConnectionPoints sont mis à jour automatiquement via MemoryComponent
    }

    public boolean isConnectedTo(MemoryComponent comp) {
        // Vérifie si le fil est connecté à ce composant
        for (ConnectionPoint input : comp.getInputs()) {
            if (input.equals(end)) return true;
        }
        for (ConnectionPoint output : comp.getOutputs()) {
            if (output.equals(start)) return true;
        }
        return false;
    }
}