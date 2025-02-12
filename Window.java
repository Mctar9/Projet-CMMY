import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class Window {
    private JFrame frame;
    private Circuit circuit;

    public Window() {
        frame = new JFrame("Logic Circuit Designer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        
        // Configuration du layout principal
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(3);
        splitPane.setDividerLocation(150); // Largeur réduite du panel latéral
        
        circuit = new Circuit();
        JPanel sidebar = createSidebar();
        
        splitPane.setLeftComponent(sidebar);
        splitPane.setRightComponent(circuit);
        
        frame.add(splitPane);
        frame.setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(44, 62, 80)); // Couleur de fond
        sidebar.setBorder(new EmptyBorder(5, 5, 5, 5)); // Marges minimales

        // Section Portes Logiques
        addSection(sidebar, "PORTES", new String[][] {
            {"🟦 AND", "Porte ET"},
            {"🟧 OR", "Porte OU"},
            {"🟥 NOT", "Porte NON"},
            {"🟪 XOR", "Porte OU-X"},
            {"🟫 NAND", "Porte NON-ET"}
        });

        // Section Entrées/Sorties
        addSection(sidebar, "I/O", new String[][] {
            {"🟢 1", "Signal HIGH"},
            {"🔴 0", "Signal LOW"},
            {"💡 LED", "Sortie LED"}
        });

        // Section Outils
        addSection(sidebar, "OUTILS", new String[][] {
            {"⚡ Connexion", "Mode Connexion"},
            {"🗑️ Supprimer", "Mode Suppression"}
        });

        return sidebar;
    }

    private void addSection(JPanel parent, String title, String[][] items) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(null);
        section.setAlignmentX(0.0f); // Alignement à gauche

        // Titre de section
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblTitle.setForeground(new Color(189, 195, 199)); // Couleur de texte
        lblTitle.setBorder(new EmptyBorder(2, 2, 2, 2)); // Marges minimales
        section.add(lblTitle);

        // Boutons
        for (String[] item : items) {
            JButton btn = createCompactButton(item[0], item[1]);
            btn.addActionListener(e -> handleButtonAction(item[0]));
            section.add(btn);
            section.add(Box.createVerticalStrut(2)); // Espace minimal
        }

        parent.add(section);
        parent.add(Box.createVerticalStrut(5)); // Espace entre sections
    }

    private JButton createCompactButton(String label, String tooltip) {
        JButton btn = new JButton(label);
        btn.setToolTipText(tooltip); // Info-bulle
        btn.setBackground(new Color(52, 73, 94)); // Couleur de fond
        btn.setForeground(Color.WHITE); // Couleur de texte
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Police
        btn.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4)); // Marges internes
        btn.setMaximumSize(new Dimension(140, 30)); // Taille fixe
        btn.setAlignmentX(0.0f);
        btn.setFocusPainted(false); // Désactive le focus visuel

        // Effet hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(41, 128, 185)); // Couleur au survol
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(52, 73, 94)); // Couleur normale
            }
        });

        return btn;
    }

    private void handleButtonAction(String command) {
        // Extraire le type de la commande (ex: "⚡ Connexion" -> "CONNECT")
        String type = command.split(" ")[1].toUpperCase();

        switch(type) {
            case "CONNEXION":
                circuit.enableConnectingMode();
                break;
            case "SUPPRIMER":
                circuit.enableDeletingMode();
                break;
            default:
                circuit.enableAddingComponent(type);
                break;
        }
    }
}