import javax.swing.*;
import java.awt.*;

public class CircuitUI {
    private JFrame frame;
    private PlacementPanel placementPanel; // Zone de placement des composants
    private JButton addComponentButton;   // Bouton pour ajouter un composant

    public CircuitUI() {
        // Initialisation de la fenêtre principale
        frame = new JFrame("Simulation de circuit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Initialisation de la zone de placement
        placementPanel = new PlacementPanel();
        frame.add(placementPanel, BorderLayout.CENTER);

        // Initialisation de la barre d'outils
        JPanel toolbar = createToolbar();
        frame.add(toolbar, BorderLayout.SOUTH);

        // Affiche la fenêtre
        frame.setVisible(true);
    }

    private JPanel createToolbar() {
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Bouton pour ajouter un composant
        addComponentButton = new JButton("Ajouter un composant");
        addComponentButton.setFocusPainted(false); // Supprime le cadre du texte
        toolbar.add(addComponentButton);

        return toolbar;
    }
}
