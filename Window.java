import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Window {
    private JFrame frame; // Fenêtre principale de l'application
    private Circuit circuit; // Zone de placement pour les composants

    public Window() {
        // Initialisation de la fenêtre principale
        frame = new JFrame("Simulation de circuit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        // Initialisation de la zone de placement
        circuit = new Circuit();
        frame.add(circuit, BorderLayout.CENTER); // Ajoute le panneau de placement au centre

        // Barre d'outils
        JPanel toolbar = createToolbar();
        frame.add(toolbar, BorderLayout.SOUTH); // Ajoute la barre d'outils en bas

        // Affiche la fenêtre
        frame.setVisible(true);
    }

    @SuppressWarnings("unused")
    private JPanel createToolbar() {
        JPanel toolbar = new JPanel();
        JButton addComponentButton = new JButton("Ajouter un composant");
        JButton connectButton = new JButton("Relier");

        // Active le mode "ajouter un composant"
        addComponentButton.addActionListener(e -> circuit.enableAddingComponent());

        // Active le mode "relier des composants"
        connectButton.addActionListener(e -> circuit.enableConnectingMode());

        toolbar.add(addComponentButton);
        toolbar.add(connectButton);
        keyBoardShortcut();
        return toolbar;
    }

}
