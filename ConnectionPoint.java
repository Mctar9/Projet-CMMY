import java.awt.*;
import java.util.Objects;

public class ConnectionPoint {
    private int x, y;
    private boolean isInput;
    private boolean highlighted = false;
    private MemoryComponent parent;



    public ConnectionPoint(MemoryComponent parent, int x, int y, boolean isInput) {
        this.x = x;
        this.y = y;
        this.isInput = isInput;
        this.parent = parent;
    }


    public boolean contains(int px, int py) {
        return Math.hypot(px - x, py - y) <= 10;
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void draw(Graphics2D g2d, Color color) {
        g2d.setColor(highlighted ? Color.YELLOW : color);
        g2d.fillOval(x - 5, y - 5, 10, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x - 5, y - 5, 10, 10);
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public void updatePosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isInput() { return isInput; }
    public MemoryComponent getParentComponent() { return parent; }
}