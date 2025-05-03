import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Circuit class represents a circuit board where memory components and wires
 * can be added, moved, and deleted.
 * It handles mouse events for interaction with the components and wires.
 */
public class Circuit extends JPanel {

    // --------------Attributs--------------//

    private static final int MAX_ITERATIONS = 1000;

    private List<MemoryComponent> components;
    private MemoryComponent selectedComponent;

    private List<Wire> wires;
    private ConnectionPoint wireStartPoint;

    private Point currentMousePosition;

    private boolean addingComponent;
    private boolean deletingMode;
    private String addingComponentType;

    // --------------Constructeur--------------//

    /**
     * Constructor for the Circuit class.
     * Initializes the panel, sets the background color, and adds mouse listeners
     * for interaction.
     */
    public Circuit() {
        setBackground(Color.WHITE);
        setFocusable(true);
        requestFocusInWindow();
    
        components = new ArrayList<>();
        wires = new ArrayList<>();
        selectedComponent = null;
        wireStartPoint = null;
        currentMousePosition = null;
        addingComponent = false;
        deletingMode = false;
    
        // Gestion des clics souris
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow(); // Prendre le focus clavier
                
                if (addingComponent) {
                    addNewComponent(e);
                    addingComponent = false;
                    repaint();
                    return;
                }
                
                if (deletingMode) {
                    deleteComponentOrWire(e);
                    deletingMode = false;
                    return;
                }
    
                // 1. Vérifier d'abord les points de connexion
                ConnectionPoint clickedPoint = findConnectionPoint(e.getX(), e.getY());
                if (clickedPoint != null && !clickedPoint.isInput()) {
                    wireStartPoint = clickedPoint;
                    currentMousePosition = e.getPoint();
                    return;
                }
    
                // 2. Sinon, gérer la sélection de composant
                selectedComponent = getComponent(e.getX(), e.getY());
                repaint();
            }
    
            @Override
            public void mouseReleased(MouseEvent e) {
                if (wireStartPoint != null) {
                    ConnectionPoint endPoint = findConnectionPoint(e.getX(), e.getY());
                    
                    // Vérification simple ici
                    if (endPoint != null && endPoint.isInput() && endPoint.canConnect()) {
                        Wire newWire = new Wire(wireStartPoint, endPoint);
                        wires.add(newWire);
                        wireStartPoint.connectWire(newWire);
                        endPoint.connectWire(newWire);
                    }
                    
                    wireStartPoint = null;
                    currentMousePosition = null;
                }
                repaint();
            }
        });
    
        // Gestion du drag pour prévisualisation des fils
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (wireStartPoint != null) {
                    currentMousePosition = e.getPoint();
                    repaint();
                } else if (selectedComponent != null) {
                    selectedComponent.moveTo(e.getX(), e.getY());
                    repaint();
                }
            }
        });
    
        // Raccourcis clavier pour déplacement
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (selectedComponent == null) return;
    
                int step = 20; 
                
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        selectedComponent.move(-step, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        selectedComponent.move(step, 0);
                        break;
                    case KeyEvent.VK_UP:
                        selectedComponent.move(0, -step);
                        break;
                    case KeyEvent.VK_DOWN:
                        selectedComponent.move(0, step);
                        break;
                    case KeyEvent.VK_ESCAPE:
                        selectedComponent = null; // Désélection
                        break;
                    case KeyEvent.VK_R: // Rotation avec la touche R
                        selectedComponent.rotate();
                        break;
                }
                repaint();
            }
        });
    }

    // --------------Méthodes--------------//

    /**
     * Deletes a component or wire from the circuit based on the mouse event.
     * If a component is clicked, it removes the component and its connected wires.
     * If a wire is clicked, it removes the wire.
     *
     * @param e the mouse event
     */
    private void deleteComponentOrWire(MouseEvent e) {
        MemoryComponent component = getComponent(e.getX(), e.getY());
        if (component != null) {
            List<Wire> toRemove = new ArrayList<>();
            for (Wire w : wires) {
                if (w.isConnectedTo(component)) {
                    toRemove.add(w);
                }
            }
            wires.removeAll(toRemove);
            components.remove(component);
        } else {
            Wire wire = getWireAt(e.getX(), e.getY());
            if (wire != null)
                wires.remove(wire);
        }
        repaint();
    }

    /**
     * Adds a new component to the circuit at the specified mouse event location.
     * The type of component is determined by the addingComponentType variable.
     *
     * @param e the mouse event
     */
    private void addNewComponent(MouseEvent e) {
        switch (addingComponentType) {
            case "AND":
                components.add(new AndGate(components.size() + 1, e.getX(), e.getY(), null, null));
                break;
            case "OR":
                components.add(new OrGate(components.size() + 1, e.getX(), e.getY(), null, null));
                break;
            case "NOT":
                components.add(new NotGate(components.size() + 1, e.getX(), e.getY(), null));
                break;
            case "XOR":
                components.add(new XorGate(components.size() + 1, e.getX(), e.getY(), null, null));
                break;
            case "NAND":
                components.add(new NandGate(components.size() + 1, e.getX(), e.getY(), null, null));
                break;
            case "0":
                components.add(new ConstantComponent(components.size() + 1, QuadBool.FALSE, e.getX(), e.getY()));
                break;
            case "1":
                components.add(new ConstantComponent(components.size() + 1, QuadBool.TRUE, e.getX(), e.getY()));
                break;
            case "LED":
                components.add(new LedLight(components.size() + 1, e.getX(), e.getY()));
                break;
        }
        repaint();
    }

    /**
     * Finds a connection point at the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the connection point if found, null otherwise
     */
    private ConnectionPoint findConnectionPoint(int x, int y) {
        for (MemoryComponent comp : components) {
            for (ConnectionPoint p : comp.getInputs()) {
                if (p.contains(x, y))
                    return p;
            }
            for (ConnectionPoint p : comp.getOutputs()) {
                if (p.contains(x, y))
                    return p;
            }
        }
        return null;
    }

    /**
     * Gets the component at the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the component if found, null otherwise
     */
    private MemoryComponent getComponent(int x, int y) {
        return components.stream()
                .filter(c -> c.contains(x, y))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets the wire at the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the wire if found, null otherwise
     */
    private Wire getWireAt(int x, int y) {
        final int TOLERANCE = 5;
        return wires.stream()
                .filter(w -> w.isPointOnWire(x, y, TOLERANCE))
                .findFirst()
                .orElse(null);
    }

    /**
     * Enables the adding component mode with the specified type.
     *
     * @param type the type of component to add (AND, OR, NOT, XOR, NAND)
     */
    public void enableAddingComponent(String type) {
        addingComponent = true;
        addingComponentType = type;
    }

    /**
     * Enables the deleting mode for components and wires.
     */
    public void enableDeletingMode() {
        deletingMode = true;
    }

    /**
     * Disables the adding component mode.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        wires.forEach(wire -> wire.draw(g));
        components.forEach(comp -> comp.draw(g, comp == selectedComponent));

        if (wireStartPoint != null && currentMousePosition != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[] { 5 }, 0));
            g2d.drawLine(wireStartPoint.getX(), wireStartPoint.getY(),
                    currentMousePosition.x, currentMousePosition.y);
        }
    }

    // ------------------last ver simuler-----------------//
    /**
 * Simule le circuit jusqu'à trouver un état stable
 * @throws CircuitInstableException si la simulation ne converge pas
 */
public void simuler() throws CircuitInstableException {
    // 1. Initialisation
    for (Wire fil : wires) {
        fil.setValue(QuadBool.NOTHING);
    }

    // 2. Recherche du point fixe
    for (int i = 0; i < MAX_ITERATIONS; i++) {
        boolean stable = true;

        // Pour chaque fil
        for (Wire fil : wires) {
            QuadBool nouvelleValeur = QuadBool.NOTHING;

            // Calculer la valeur à partir des composants connectés
            for (ConnectionPoint point : fil.getConnections()) {
                if (!point.isInput()) { // Si c'est une sortie
                    MemoryComponent comp = point.getParentComponent();
                    
                    // Récupérer les valeurs des entrées
                    List<QuadBool> entrees = new ArrayList<>();
                    for (ConnectionPoint entree : comp.getInputs()) {
                        entrees.add(entree.getWire() != null ? entree.getWire().getValue() : QuadBool.NOTHING);
                    }
                    
                    // Calculer la sortie
                    QuadBool sortie = comp.calculerSortie();
                    nouvelleValeur = nouvelleValeur.sup(sortie);
                }
            }

            // Mettre à jour si nécessaire
            if (!fil.getValue().equals(nouvelleValeur)) {
                fil.setValue(nouvelleValeur);
                stable = false;
            }
        }

        if (stable) {
            return; // Circuit stable
        }
    }

    throw new CircuitInstableException("Pas de point fixe après " + MAX_ITERATIONS + " itérations");
}


    ////////////////////////////////////////////////////////////////////////////

    public String exportAsText() {
        StringBuilder sb = new StringBuilder();

        // Exporter les composants
        for (MemoryComponent comp : components) {
            sb.append("Composant:");
            sb.append(" type=").append(comp.getClass().getSimpleName());
            sb.append(" id=").append(comp.getId());
            sb.append(" x=").append(comp.getX());
            sb.append(" y=").append(comp.getY());
            sb.append("\n");
        }

        // Exporter les connexions (fils)
        for (Wire wire : wires) {
            MemoryComponent fromComp = wire.getStart().getParentComponent();
            MemoryComponent toComp = wire.getEnd().getParentComponent();

            int fromId = fromComp.getId();
            int toId = toComp.getId();

            int fromIndex = fromComp.getOutputs().indexOf(wire.getStart());
            int toIndex = toComp.getInputs().indexOf(wire.getEnd());

            sb.append("Connexion: from=").append(fromId).append(".").append(fromIndex)
                    .append(" to=").append(toId).append(".").append(toIndex)
                    .append("\n");
        }

        return sb.toString();
    }

    public void importFromFile(File file) throws IOException {
        components.clear();
        wires.clear();

        Map<Integer, MemoryComponent> idMap = new HashMap<>();
        List<String> lignes = Files.readAllLines(file.toPath());

        for (String ligne : lignes) {
            if (ligne.startsWith("Composant:")) {
                String[] parts = ligne.split(" ");
                String type = parts[1].split("=")[1];
                int id = Integer.parseInt(parts[2].split("=")[1]);
                int x = Integer.parseInt(parts[3].split("=")[1]);
                int y = Integer.parseInt(parts[4].split("=")[1]);

                MemoryComponent comp = switch (type) {
                    case "AndGate" -> new AndGate(id, x, y, null, null);
                    case "OrGate" -> new OrGate(id, x, y, null, null);
                    case "NotGate" -> new NotGate(id, x, y, null);
                    case "XorGate" -> new XorGate(id, x, y, null, null);
                    case "NandGate" -> new NandGate(id, x, y, null, null);
                    case "ConstantComponent" -> new ConstantComponent(id, QuadBool.FALSE, x, y); // TODO: gérer valeur
                    default -> null;
                };

                if (comp != null) {
                    idMap.put(id, comp);
                    components.add(comp);
                }
            } else if (ligne.startsWith("Connexion:")) {
                String[] parts = ligne.split(" ");
                String from = parts[1].split("=")[1];
                String to = parts[2].split("=")[1];

                int fromId = Integer.parseInt(from.split("\\.")[0]);
                int fromIndex = Integer.parseInt(from.split("\\.")[1]);
                int toId = Integer.parseInt(to.split("\\.")[0]);
                int toIndex = Integer.parseInt(to.split("\\.")[1]);

                ConnectionPoint src = idMap.get(fromId).getOutputs().get(fromIndex);
                ConnectionPoint dst = idMap.get(toId).getInputs().get(toIndex);

                wires.add(new Wire(src, dst));
            }
        }

        repaint();
    }
}
