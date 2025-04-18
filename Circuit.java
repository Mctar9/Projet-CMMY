import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente le panneau principal du circuit logique.
 * Gère les composants, les fils, les interactions souris et clavier,
 * ainsi que les différents modes (ajout, suppression, connexion).
 */
public class Circuit extends JPanel {
    private List<MemoryComponent> components = new ArrayList<>(); //liste des composants
    private List<Wire> wires = new ArrayList<>(); //liste des fils
    private MemoryComponent selectedComponent = null; // composant sélectionné

    // États des modes
    private boolean addingComponent = false;
    private boolean connectingMode = false;
    private boolean deletingMode = false;
    private ConnectionPoint firstSelectedPoint = null;
    private String addingComponentType;

    /**
     * Initialise le circuit avec les gestionnaires d'événements nécessaires
     * pour les interactions utilisateur (clavier, souris, glisser-déposer).
     */
    public Circuit() {
        setBackground(new Color(255, 255, 255));
        setFocusable(true);
        requestFocusInWindow();

        // Gestion des clics souris
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
                repaint();
            }
        });

        // Gestion du clavier
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
                repaint();
            }
        });

        // Gestion du drag-and-drop
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedComponent != null) {
                    selectedComponent.moveTo(e.getX(), e.getY());
                    repaint();
                }
            }
        });

        // Gestion du clic pour sélectionner un composant
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedComponent = getComponent(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedComponent = null;
            }
        });
    }

    /**
     * Gère les clics de souris selon le mode actif (ajout, suppression, etc).
     * @param e Événement de souris
     */
    private void handleMouseClick(MouseEvent e) {
        requestFocusInWindow();

        if (deletingMode) {
            deleteComponentOrWire(e);
            deletingMode = false;
        } else if (addingComponent) {
            addNewComponent(e);
            addingComponent = false;
        } else if (connectingMode) {
            handleWireConnection(e);
        } else {
            selectComponent(e);
        }
    }

    /**
     * Supprime un composant ou un fil situé à l'endroit du clic.
     * @param e Événement de souris
     */
    private void deleteComponentOrWire(MouseEvent e) {
        MemoryComponent component = getComponent(e.getX(), e.getY());
        if (component != null) {
            List<Wire> wiresToRemove = new ArrayList<>();
            for (Wire wire : wires) {
                for (ConnectionPoint input : component.getInputs()) {
                    if (input.equals(wire.getEnd())) {
                        wiresToRemove.add(wire);
                        break;
                    }
                }
                for (ConnectionPoint output : component.getOutputs()) {
                    if (output.equals(wire.getStart())) {
                        wiresToRemove.add(wire);
                        break;
                    }
                }
            }
            wires.removeAll(wiresToRemove);
            components.remove(component);
        } else {
            Wire wire = getWireAt(e.getX(), e.getY());
            if (wire != null) wires.remove(wire);
        }
        repaint();
    }

    /**
     * Ajoute un nouveau composant logique au circuit à l'endroit du clic.
     * @param e Événement de souris
     */
    private void addNewComponent(MouseEvent e) {
        switch (addingComponentType) {
            case "AND":
                components.add(new AndGate(components.size() + 1, e.getX(), e.getY()));
                break;
            case "OR":
                components.add(new OrGate(components.size() + 1, e.getX(), e.getY()));
                break;
            case "NOT":
                components.add(new NotGate(components.size() + 1, e.getX(), e.getY()));
                break;
        }
    }

    /**
     * Gère la connexion de deux points de connexion via un fil.
     * @param e Événement de souris
     */
    private void handleWireConnection(MouseEvent e) {
        ConnectionPoint clickedPoint = findConnectionPoint(e.getX(), e.getY());

        if (clickedPoint == null) {
            firstSelectedPoint = null; 
            repaint();
            return;
        }

        if (firstSelectedPoint == null) {
            if (!clickedPoint.isInput()) {
                firstSelectedPoint = clickedPoint;
            }
        } else {
            if (clickedPoint.isInput()) {
                if (!firstSelectedPoint.getParentComponent().equals(clickedPoint.getParentComponent())) {
                    wires.add(new Wire(firstSelectedPoint, clickedPoint));
                }
            }
            firstSelectedPoint = null;
        }
        repaint();
    }

    /**
     * Recherche un point de connexion présent à une position donnée.
     * @param x Coordonnée X
     * @param y Coordonnée Y
     * @return Le point de connexion trouvé, ou null si aucun
     */
    private ConnectionPoint findConnectionPoint(int x, int y) {
        for (MemoryComponent comp : components) {
            for (ConnectionPoint point : comp.getInputs()) {
                if (point.contains(x, y)) return point;
            }
            for (ConnectionPoint point : comp.getOutputs()) {
                if (point.contains(x, y)) return point;
            }
        }
        return null;
    }

    /**
     * Sélectionne un composant sous le curseur de la souris.
     * @param e Événement de souris
     */
    private void selectComponent(MouseEvent e) {
        selectedComponent = getComponent(e.getX(), e.getY());
    }

    /**
     * Gère les interactions clavier (déplacement ou rotation de composants).
     * @param e Événement clavier
     */
    private void handleKeyPress(KeyEvent e) {
        if (selectedComponent != null) {
            switch (e.getKeyCode()) {
                // déplacements / rotations désactivés ici
            }
        }
        repaint();
    }

    /**
     * Récupère un composant situé à des coordonnées données.
     * @param x Coordonnée X
     * @param y Coordonnée Y
     * @return Le composant trouvé ou null
     */
    private MemoryComponent getComponent(int x, int y) {
        return components.stream()
                .filter(c -> c.contains(x, y))
                .findFirst()
                .orElse(null);
    }

    /**
     * Récupère un fil situé à proximité des coordonnées données.
     * @param x Coordonnée X
     * @param y Coordonnée Y
     * @return Le fil trouvé ou null
     */
    private Wire getWireAt(int x, int y) {
        final int TOLERANCE = 5;
        return wires.stream()
                .filter(w -> w.isPointOnWire(x, y, TOLERANCE))
                .findFirst()
                .orElse(null);
    }

    /**
     * Active le mode d'ajout d'un composant spécifique.
     * @param type Type de composant (ex: AND, OR, NOT)
     */
    public void enableAddingComponent(String type) {
        addingComponent = true;
        addingComponentType = type.toUpperCase();
    }

    /**
     * Active le mode de connexion entre deux composants.
     */
    public void enableConnectingMode() {
        connectingMode = true;
    }

    /**
     * Active le mode de suppression d'un composant ou d'un fil.
     */
    public void enableDeletingMode() {
        deletingMode = true;
    }

    /**
     * Dessine tous les composants et les fils du circuit.
     * @param g Contexte graphique
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        wires.forEach(wire -> wire.draw(g));
        components.forEach(comp -> comp.draw(g, comp == selectedComponent));
    }
}
