import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
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
        splitPane.setDividerLocation(200); // Largeur augmentée pour les images

        frame.getContentPane().setBackground(Color.WHITE); // zone princupal en fond blanc
        frame.setJMenuBar(createSimulationMenuBar()); // Ajout de la barre de menu

        circuit = new Circuit();
        JPanel sidebar = createSidebar();
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setLeftComponent(sidebar);
        splitPane.setRightComponent(circuit);

        frame.add(splitPane);
        frame.setVisible(true);
        setupShortcuts();
    }

    private void setupShortcuts() {
        AbstractAction closeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.exit(0);
            }
        };

        JRootPane rootPane = frame.getRootPane();
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl Q"), "closeAction");
        rootPane.getActionMap().put("closeAction", closeAction);

    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Section Portes Logiques
        addSection(sidebar, "PORTES LOGIQUES", new String[][] {
                { "AND", "Porte ET" },
                { "OR", "Porte OU" },
                { "NOT", "Porte NON" },
                { "XOR", "Porte OU-X" },
                { "NAND", "Porte NON-ET" }
        });

        // Section Entrées/Sorties
        addSection(sidebar, "ENTRÉES/SORTIES", new String[][] {
                { "1", "Signal HIGH" },
                { "0", "Signal LOW" },
                { "LED", "Sortie LED" }
        });

        // Section Outils
        addSection(sidebar, "OUTILS", new String[][] {
                { "CONNECT", "Mode Connexion" },
                { "DELETE", "Mode Suppression" }
        });

        return sidebar;
    }

    private void addSection(JPanel parent, String title, String[][] items) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(null);
        section.setAlignmentX(0.0F);

        // Titre de section
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTitle.setForeground(new Color(170, 170, 170));
        lblTitle.setBorder(new EmptyBorder(8, 8, 8, 8));
        lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
        section.add(lblTitle);

        // Séparateur
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(127, 140, 141));
        section.add(separator);
        section.add(Box.createVerticalStrut(10));

        // Boutons avec images
        for (String[] item : items) {
            JButton btn = createImageButton(item[0], item[1]);
            btn.addActionListener(e -> handleButtonAction(item[0])); // Envoyer le type directement
            section.add(btn);
            section.add(Box.createVerticalStrut(8));
        }

        parent.add(section);
        parent.add(Box.createVerticalStrut(20));
    }

    private JButton createImageButton(String type, String tooltip) {
        JButton btn = new JButton();
        btn.setToolTipText(tooltip);
        btn.setBackground(new Color(50, 50, 50));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setPreferredSize(new Dimension(120, 80));

        try {
            // Chargement de l'image
            String imagePath = "PROJET-CMMY/img/and.png";
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
            Image scaled = icon.getImage().getScaledInstance(60, 40, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            // Fallback textuel
            btn.setText(type);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        }

        // Effet hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(65, 65, 65));
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(100, 100, 100)),
                        BorderFactory.createEmptyBorder(12, 12, 12, 12)));

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(50, 50, 50));
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(80, 80, 80)),
                        BorderFactory.createEmptyBorder(12, 12, 12, 12)));

            }
        });

        return btn;
    }

    private void handleButtonAction(String command) {
        switch (command) {
            case "CONNECT":
                circuit.enableConnectingMode();
                break;
            case "DELETE":
                circuit.enableDeletingMode();
                break;
            default:
                // Utiliser directement la commande comme type
                circuit.enableAddingComponent(command.toUpperCase());
                break;
        }
    }

    private JMenuBar createSimulationMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(250, 250, 250));
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));

        // Contrôles de simulation centrés
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.GREEN);
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 2));

        JButton startButton = createToolButton("▶", "Démarrer (Space)");
        JButton pauseButton = createToolButton("⏸", "Pause (P)");
        JButton resetButton = createToolButton("↺", "Réinitialiser (R)");

        centerPanel.add(startButton);
        centerPanel.add(pauseButton);
        centerPanel.add(resetButton);

        // Sélecteur de vitesse compact à droite
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.GREEN);
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 2));

        JComboBox<String> speedSelector = new JComboBox<>(new String[] { "1x", "2x", "5x", "Max" });
        speedSelector.setPrototypeDisplayValue("1x"); // Réduit la largeur
        speedSelector.setMaximumSize(new Dimension(60, 25));
        speedSelector.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        JLabel clockLabel = new JLabel("Horloge: 0");
        JLabel statusLabel = new JLabel("Statut: Arrêté");

        // Assemblage final
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(centerPanel);
        menuBar.add(Box.createHorizontalGlue());
        rightPanel.add(new JLabel("Vitesse:"));
        rightPanel.add(speedSelector);
        rightPanel.add(Box.createHorizontalStrut(15));
        rightPanel.add(clockLabel);
        rightPanel.add(Box.createHorizontalStrut(5));
        rightPanel.add(statusLabel);
        menuBar.add(rightPanel);

        return menuBar;
    }

    private JButton createToolButton(String iconText, String tooltip) {
        JButton btn = new JButton(iconText);
        btn.setFont(new Font("Arial Unicode MS", Font.PLAIN, 16)); // Taille de police augmentée
        btn.setToolTipText(tooltip);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(4, 12, 4, 12)));

        // Style hover
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(246, 246, 246));
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(180, 180, 180)),
                        BorderFactory.createEmptyBorder(4, 12, 4, 12)));

            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.WHITE);
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        BorderFactory.createEmptyBorder(4, 12, 4, 12)));
            }
        });

        return btn;
    }
}