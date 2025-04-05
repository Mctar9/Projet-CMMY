import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MemoryComponent {
    private int id;
    private String type;
    private int x, y;
    private boolean isVisited;
    private final int WIDTH = 80;
    private final int HEIGHT = 60;
    private int rotationAngle = 0;
    protected List<ConnectionPoint> inputs = new ArrayList<>();
    protected List<ConnectionPoint> outputs = new ArrayList<>();

    public MemoryComponent(int id, String type, int x, int y) {
        this.id = id;
        this.type = type;
        this.x = x - WIDTH / 2; // Centrage sur le point de clic
        this.y = y - HEIGHT / 2;
        this.isVisited = false;
    }

    // Méthodes d'accès
    public int getId() { return id; }
    public String getType() { return type; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getCenterX() { return x + WIDTH / 2; }
    public int getCenterY() { return y + HEIGHT / 2; }
    public void setVisited(boolean visited) { this.isVisited = visited; }
    public boolean isVisited() { return isVisited; }
    protected int getRotationAngle() { return rotationAngle; }
    protected int getWidth() { return WIDTH; }
    protected int getHeight() { return HEIGHT; }
    public List<ConnectionPoint> getInputs() { return inputs; }
    public List<ConnectionPoint> getOutputs() { return outputs; }
    public List<ConnectionPoint> getAllConnectionPoints() {
        List<ConnectionPoint> allPoints = new ArrayList<>(inputs);
        allPoints.addAll(outputs);
        return allPoints;
    }


    protected void setX(int x) { this.x = x; }
    protected void setY(int y) { this.y = y; }
    protected void setRotationAngle(int angle) { this.rotationAngle = angle; }


    /*  Déplacement du composant 
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
    */
    
    
    // Centrer le composant sur la position de la souris
    public void moveTo(int newX, int newY) {
        // Calcul du déplacement
        int dx = newX - (x + WIDTH/2);
        int dy = newY - (y + HEIGHT/2);
        
        // Mise à jour position
        x = newX - WIDTH/2;
        y = newY - HEIGHT/2;
        
        // Mise à jour des points
        for (ConnectionPoint point : inputs) {
            point.move(dx, dy);
        }
        for (ConnectionPoint point : outputs) {
            point.move(dx, dy);
        }
    }

    // Vérification du clic
    public boolean contains(int px, int py) {
        return px >= x && px <= x + WIDTH && 
               py >= y && py <= y + HEIGHT;
    }

    // Rotation
    public void rotate() {
        rotationAngle = (rotationAngle + 90) % 360;
    }
    public abstract void draw(Graphics g, boolean isSelected);

    protected void drawConnectionPoints(Graphics2D g2d) {
        for (ConnectionPoint point : inputs) {
            point.draw(g2d, Color.RED); // Entrées en rouge
        }
        for (ConnectionPoint point : outputs) {
            point.draw(g2d, Color.GREEN); // Sorties en vert
        }
    }

    public ConnectionPoint getConnectionPointAt(int px, int py) {
        for (ConnectionPoint point : inputs) {
            if (point.contains(px, py)) return point;
        }
        for (ConnectionPoint point : outputs) {
            if (point.contains(px, py)) return point;
        }
        return null;
    }

    public void updateConnectionPoints() {
        for (ConnectionPoint point : inputs) {
            point.updatePosition(
                x + (point.isInput() ? 0 : WIDTH),
                y + HEIGHT/3 // Ajustez selon le point
            );
        }
        for (ConnectionPoint point : outputs) {
            point.updatePosition(
                x + (point.isInput() ? 0 : WIDTH),
                y + HEIGHT/2
            );
        }
    }

    protected void initConnectionPoints() {
        // Ne pas clear() les listes - conserve les références existantes
        
        if (inputs.isEmpty()) {
            inputs.add(new ConnectionPoint(this, x, y + HEIGHT/3, true));
            inputs.add(new ConnectionPoint(this, x, y + 2*HEIGHT/3, true));
        } else {
            inputs.get(0).updatePosition(x, y + HEIGHT/3);
            inputs.get(1).updatePosition(x, y + 2*HEIGHT/3);
        }
        
        if (outputs.isEmpty()) {
            outputs.add(new ConnectionPoint(this, x + WIDTH, y + HEIGHT/2, false));
        } else {
            outputs.get(0).updatePosition(x + WIDTH, y + HEIGHT/2);
        }
    }

    public boolean isConnectedTo(Wire wire) {
        // Vérifie si le fil est connecté à une entrée ou sortie de ce composant
        return inputs.contains(wire.getEnd()) || outputs.contains(wire.getStart());
    }
}   