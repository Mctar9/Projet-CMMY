import javax.swing.*;
import java.awt.*;

public class CircuitUI {
    private JFrame frame;
    private PlacementPanel placementPanel;

    public CircuitUI() {
        // Initialisation de la fenêtre principale
        frame = new JFrame("Simulation de circuit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Initialisation du panneau de placement
        placementPanel = new PlacementPanel();
        frame.add(placementPanel, BorderLayout.CENTER);

        // Affiche la fenêtre
        frame.setVisible(true);
    }
}
