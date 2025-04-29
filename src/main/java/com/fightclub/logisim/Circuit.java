import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

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

                ConnectionPoint p = findConnectionPoint(e.getX(), e.getY());
                if (p != null && !p.isInput()) {
                    wireStartPoint = p;
                    currentMousePosition = e.getPoint();
                } else {
                    selectedComponent = getComponent(e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (wireStartPoint != null) {
                    ConnectionPoint target = findConnectionPoint(e.getX(), e.getY());
                    if (target != null && target.isInput() &&
                            !target.getParentComponent().equals(wireStartPoint.getParentComponent())) {
                        wires.add(new Wire(wireStartPoint, target));
                    }
                }
                wireStartPoint = null;
                currentMousePosition = null;
                selectedComponent = null;
                repaint();
            }
        });

        // Déplacement de la souris
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (wireStartPoint != null) {
                    currentMousePosition = e.getPoint();
                } else if (selectedComponent != null) {
                    selectedComponent.moveTo(e.getX(), e.getY());
                }
                repaint();
            }
        });

        // Clavier (désactivé ici)
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Rien pour l'instant
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
                components.add(new ConstantComponent(components.size() + 1,QuadBool.FALSE, e.getX(), e.getY()));
                break;
            case "1":
                components.add(new ConstantComponent(components.size() + 1,QuadBool.TRUE, e.getX(), e.getY()));
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

    /**
     * Simule le comportement du circuit jusqu'à trouver un état stable (point fixe).
     * 
     * <p>Algorithme :</p>
     * <ol>
     *   <li>Initialise tous les fils à la valeur I (état initial)</li>
     *   <li>Répète jusqu'à MAX_ITERATIONS :
     *     <ul>
     *       <li>Pour chaque fil, calcule sa nouvelle valeur basée sur les sorties des composants connectés</li>
     *       <li>Compare avec l'ancienne valeur</li>
     *       <li>Si aucune valeur n'a changé (point fixe atteint), termine avec succès</li>
     *     </ul>
     *   </li>
     *   <li>Si MAX_ITERATIONS est atteint sans convergence, lève une exception</li>
     * </ol>
     * 
     * @throws CircuitInstableException si aucun point fixe n'est trouvé après MAX_ITERATIONS
     */
    public void simuler() throws CircuitInstableException {

        //petit test pour riadh pour tester que la methode simuler est lancé. CHECK IT BROO !!!!!!!!!!!!
        this.setBackground(Color.BLACK);
        

        // Étape 1: Initialisation
        for (Wire fil : wires) {
            fil.setValue(QuadBool.NOTHING); // Valeur initiale
        }

        // Étape 2: Recherche du point fixe
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            boolean stable = true;

            // Pour chaque fil du circuit
            for (Wire fil : wires) {
                //Todo: Vérifier si le fil est connecté à un composant mémoire
            }

            // Point fixe atteint
            if (stable) {
               //Todo: Afficher les valeurs finales des fils
            }
        }

        // Échec de convergence
        //Todo: Lève une exception
    }

    /**
     * Calcule la nouvelle valeur d'un fil en fonction des composants connectés.
     * 
     * @param fil le fil dont on veut calculer la valeur
     * @return la valeur QuadBool résultante (supremum des sorties connectées)
     
    private QuadBool calculerValeurFil(Wire fil) {
        //Todo: Implémenter la logique de calcul
    }*/

        /**
     * Exception levée lorsque le circuit n'atteint pas un état stable.
     */
    class CircuitInstableException extends Exception {
        //Todo: Implémenter la logique d'exception
    }
}





