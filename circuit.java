import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Circuit extends JPanel {
    private List<MemoryComponent> components = new ArrayList<>();
    private List<Wire> wires = new ArrayList<>();
    private MemoryComponent selectedComponent = null;
    private MemoryComponent firstSelectedForWire = null;
    
    // États des modes
    private boolean addingComponent = false;
    private boolean connectingMode = false;
    private boolean deletingMode = false;
    
    private String addingComponentType;

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
                    // Déplacer le composant sélectionné
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
                selectedComponent = null; // Désélectionner après le déplacement
            }
        });    
    }

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

    private void deleteComponentOrWire(MouseEvent e) {
        MemoryComponent component = getComponent(e.getX(), e.getY());
        if (component != null) {
            components.remove(component);
            wires.removeIf(wire -> 
                wire.getStart() == component || wire.getEnd() == component);
        } else {
            Wire wire = getWireAt(e.getX(), e.getY());
            if (wire != null) {
                wires.remove(wire);
            }
        }
    }

    private void addNewComponent(MouseEvent e) {
        components.add(new MemoryComponent(
            components.size() + 1,
            addingComponentType,
            e.getX(), // Le centrage est géré dans le constructeur de MemoryComponent
            e.getY()
        ));
    }

    private void handleWireConnection(MouseEvent e) {
        MemoryComponent clickedComponent = getComponent(e.getX(), e.getY());
        if (clickedComponent != null) {
            if (firstSelectedForWire == null) {
                firstSelectedForWire = clickedComponent;
            } else {
                wires.add(new Wire(firstSelectedForWire, clickedComponent));
                firstSelectedForWire = null;
                connectingMode = false;
            }
        }
    }

    private void selectComponent(MouseEvent e) {
        selectedComponent = getComponent(e.getX(), e.getY());
    }

    private void handleKeyPress(KeyEvent e) {
        if (selectedComponent != null) {
            int moveStep = 10; // Pas de déplacement réduit pour plus de précision
            switch (e.getKeyCode()) {
                /*
                case KeyEvent.VK_UP:    selectedComponent.move(0, -moveStep); break;
                case KeyEvent.VK_DOWN:  selectedComponent.move(0, moveStep); break;
                case KeyEvent.VK_LEFT:  selectedComponent.move(-moveStep, 0); break;
                case KeyEvent.VK_RIGHT: selectedComponent.move(moveStep, 0); break;
                
                case KeyEvent.VK_R:     selectedComponent.rotate(); break;
                */
            }
        }
        repaint();
    }

    // Méthodes d'accès aux composants
    private MemoryComponent getComponent(int x, int y) {
        return components.stream()
            .filter(c -> c.contains(x, y))
            .findFirst()
            .orElse(null);
    }

    private Wire getWireAt(int x, int y) {
        final int TOLERANCE = 5;
        return wires.stream()
            .filter(w -> w.isPointOnWire(x, y, TOLERANCE))
            .findFirst()
            .orElse(null);
    }

    // Gestion des modes
    public void enableAddingComponent(String type) {
        addingComponent = true;
        addingComponentType = type.toUpperCase(); // Uniformisation de la casse
    }

    public void enableConnectingMode() {
        connectingMode = true;
        firstSelectedForWire = null;
    }

    public void enableDeletingMode() {
        deletingMode = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Dessiner les fils en premier
        wires.forEach(wire -> wire.draw(g));
        
        // Dessiner les composants par-dessus
        components.forEach(comp -> comp.draw(g, comp == selectedComponent));
    }
}