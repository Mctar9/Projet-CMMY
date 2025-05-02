import java.awt.*;
import java.awt.geom.AffineTransform;

public class NotGate extends MemoryComponent {
    public NotGate(int id, int x, int y) {
        super(id, "NOT", x, y);
        initConnectionPoints();
    }

    @Override
    public void draw(Graphics g, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(getX() + getWidth() / 2, getY() + getHeight() / 2);
        g2d.rotate(Math.toRadians(getRotationAngle()));
        g2d.translate(-getWidth() / 2, -getHeight() / 2);

        // Corps de la porte NOT (triangle + petit cercle)
        g2d.setColor(Color.LIGHT_GRAY);
        Polygon triangle = new Polygon();
        triangle.addPoint(0, 0);
        triangle.addPoint(0, getHeight());
        triangle.addPoint(getWidth() - 10, getHeight() / 2);
        g2d.fillPolygon(triangle);

        g2d.fillOval(getWidth() - 10, getHeight() / 2 - 5, 10, 10); // cercle

        // Texte
        g2d.setColor(Color.BLACK);
        g2d.drawString("NOT", 10, getHeight() / 2 + 5);

        g2d.setTransform(oldTransform);
        drawConnectionPoints(g2d);

        if (isSelected) {
            g2d.setColor(Color.BLUE);
            g2d.drawRect(getX(), getY(), getWidth(), getHeight());
        }
    }
}
