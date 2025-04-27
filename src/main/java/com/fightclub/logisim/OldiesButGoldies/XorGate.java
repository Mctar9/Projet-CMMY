import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class XorGate extends MemoryComponent {
    public XorGate(int id, int x, int y) {
        super(id, "XOR", x, y);
        initConnectionPoints();
    }

}