import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Circuit extends JPanel {
    // Attributs existants (inchangés)
    private List<MemoryComponent> components = new ArrayList<>();
    private List<Wire> wires = new ArrayList<>();
    private MemoryComponent selectedComponent = null;
    private boolean addingComponent = false;
    
    // Nouveaux attributs pour la connexion
    private MemoryComponent firstSelectedForWire = null;
    private boolean connectingMode = false;

    public Circuit() {
        setBackground(Color.LIGHT_GRAY);
        setFocusable(true);
        requestFocusInWindow();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();

                if (addingComponent) {
                    addComponent(new MemoryComponent(1, "ADD", e.getX(), e.getY()));
                    addingComponent = false;
                } 
                else if (connectingMode) { // Partie ajoutée pour la connexion
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
                else {
                    selectedComponent = getComponent(e.getX(), e.getY());
                }
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (selectedComponent != null) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:    selectedComponent.move(0, -20); break;
                        case KeyEvent.VK_DOWN:  selectedComponent.move(0, 20); break;
                        case KeyEvent.VK_LEFT:  selectedComponent.move(-20, 0); break;
                        case KeyEvent.VK_RIGHT: selectedComponent.move(20, 0); break;
                        case KeyEvent.VK_R:     selectedComponent.rotate(); break;
                    }
                    repaint();
                }
            }
        });
    }

    // Méthode existante (inchangée)
    public void enableAddingComponent() {
        addingComponent = true;
    }

    // Nouvelle méthode pour activer le mode connexion
    public void enableConnectingMode() {
        connectingMode = true;
        firstSelectedForWire = null;
    }

    // Méthode existante (inchangée)
    public void addComponent(MemoryComponent component) {
        components.add(component);
    }

    // Méthode existante (inchangée)
    private MemoryComponent getComponent(int x, int y) {
        for (MemoryComponent component : components) {
            if (component.contains(x, y)) {
                return component;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessin des fils ajouté
        for (Wire wire : wires) {
            wire.draw(g);
        }

        // Dessin existant des composants
        for (MemoryComponent component : components) {
            component.draw(g, component == selectedComponent);
        }
    }
}