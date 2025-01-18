import javax.swing.*;
import java.awt.*;

public class CircuitUI {
    private JFrame frame;
    private PlacementPanel placementPanel;

    public CircuitUI() {
        frame = new JFrame("Simulation de circuit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        // Créer le panneau de placement
        placementPanel = new PlacementPanel();

        // Ajouter le panneau de placement au centre
        frame.add(placementPanel, BorderLayout.CENTER);

        // Ajouter la barre d'outils
        JPanel toolbar = createToolbar();
        frame.add(toolbar, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createToolbar() {
        JPanel toolbar = new JPanel();
        JButton addComponentButton = new JButton("Ajouter un composant");
        addComponentButton.setFocusable(false);

        // Action du bouton "Ajouter un composant"
        addComponentButton.addActionListener(e -> {
            Point coords = askForCoordinates(); // Demande des coordonnées
            if (coords != null) {
                placementPanel.addNewComponent(coords.x, coords.y);
            }
        });

        toolbar.add(addComponentButton);
        return toolbar;
    }

    private Point askForCoordinates() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        JTextField xField = new JTextField();
        JTextField yField = new JTextField();

        panel.add(new JLabel("Coordonnée X :"));
        panel.add(xField);
        panel.add(new JLabel("Coordonnée Y :"));
        panel.add(yField);

        int result = JOptionPane.showConfirmDialog(
            frame,
            panel,
            "Entrez les coordonnées",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                int x = Integer.parseInt(xField.getText().trim());
                int y = Integer.parseInt(yField.getText().trim());
                return new Point(x, y);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Veuillez entrer des nombres valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null; // Annulé ou erreur
    }
}
