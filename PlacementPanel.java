import javax.swing.*;
import java.awt.*;

public class PlacementPanel extends JPanel {
    public PlacementPanel() {
        setBackground(Color.LIGHT_GRAY); // Fond gris
        setPreferredSize(new Dimension(800, 600)); // Taille préférée
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Affichage simple pour le moment
        g.setColor(Color.BLACK);
    }
}
