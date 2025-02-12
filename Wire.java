import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.geom.Line2D;

public class Wire {
    private String value;
    private List<MemoryComponent> connections;
    private MemoryComponent start;
    private MemoryComponent end;
    
    public Wire(MemoryComponent start, MemoryComponent end) {
        this.value = "undefined";
        this.connections = new ArrayList<>();
        this.start = start;
        this.end = end;
        connections.add(start);
        connections.add(end);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addConnection(MemoryComponent component) {
        if (!connections.contains(component)) {
            connections.add(component);
        }
    }

    public List<MemoryComponent> getConnections() {
        return new ArrayList<>(connections);
    }

    public MemoryComponent getStart() {
        return start;
    }

    public MemoryComponent getEnd() {
        return end;
    }

    public void setStart(MemoryComponent start) {
        this.start = start;
        if (!connections.contains(start)) {
            connections.add(start);
        }
    }

    public void setEnd(MemoryComponent end) {
        this.end = end;
        if (!connections.contains(end)) {
            connections.add(end);
        }
    }

//--------------INTERFACE GRAPHIQUE--------------//

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(
            start.getCenterX(), start.getCenterY(),
            end.getCenterX(), end.getCenterY()
        );
    }
}