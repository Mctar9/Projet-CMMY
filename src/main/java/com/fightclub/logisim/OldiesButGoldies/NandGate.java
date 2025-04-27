import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class NandGate extends MemoryComponent {
    public NandGate(int id, int x, int y) {
        super(id, "Nand", x, y);
        initConnectionPoints();
    }

}