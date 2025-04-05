import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class OrGate extends MemoryComponent {
    public OrGate(int id, int x, int y) {
        super(id, "OR", x, y);
        initConnectionPoints();
    }

    @Override
    public void draw(Graphics g, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(getX() + getWidth() / 2, getY() + getHeight() / 2);
        g2d.rotate(Math.toRadians(getRotationAngle()));
        g2d.translate(-getWidth() / 2, -getHeight() / 2);

        // Corps de la porte OR
        g2d.setColor(Color.LIGHT_GRAY);
        Path2D orShape = new Path2D.Double();
        int w = getWidth();
        int h = getHeight();

        orShape.moveTo(0, 0);
        orShape.curveTo(w / 3.0, 0, w - 10, 0, w, h / 2.0);
        orShape.curveTo(w - 10, h, w / 3.0, h, 0, h);
        orShape.curveTo(w / 5.0, h * 0.66, w / 5.0, h * 0.33, 0, 0);
        g2d.fill(orShape);

        // Texte
        g2d.setColor(Color.BLACK);
        g2d.drawString("OR", 15, getHeight() / 2 + 5);

        g2d.setTransform(oldTransform);
        drawConnectionPoints(g2d);

        if (isSelected) {
            g2d.setColor(Color.BLUE);
            g2d.drawRect(getX(), getY(), getWidth(), getHeight());
        }
    }
}
