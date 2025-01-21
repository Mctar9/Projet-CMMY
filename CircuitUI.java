import javax.swing.*;
import java.awt.*;

public class CircuitUI {
    private JFrame frame; // Fenêtre principale de l'application
    private PlacementPanel placementPanel; // Zone de placement pour les composants

    public CircuitUI() {
        // Initialisation de la fenêtre principale
        frame = new JFrame("Simulation de circuit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        // Initialisation de la zone de placement
        placementPanel = new PlacementPanel();
        frame.add(placementPanel, BorderLayout.CENTER); // Ajoute le panneau de placement au centre

        // Barre d'outils
        JPanel toolbar = createToolbar();
        frame.add(toolbar, BorderLayout.SOUTH); // Ajoute la barre d'outils en bas

        // Affiche la fenêtre
        frame.setVisible(true);
    }

    private JPanel createToolbar() {
        JPanel toolbar = new JPanel(); // Barre d'outils
        JButton addComponentButton = new JButton("Ajouter un composant"); // Bouton pour ajouter un composant

        // Action pour activer l'ajout de composants
        addComponentButton.addActionListener(e -> placementPanel.enableAddingComponent());

        toolbar.add(addComponentButton); // Ajoute le bouton à la barre d'outils
        return toolbar;
    }
}
