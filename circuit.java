import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Circuit extends JPanel {
    private List<MemoryComponent> components = new ArrayList<>();
    private List<Wire> wires = new ArrayList<>();
    private MemoryComponent selectedComponent = null;
    private boolean addingComponent = false;
    private MemoryComponent firstSelectedForWire = null;
    private boolean connectingMode = false;
    private boolean deletingMode = false;
    private String addingComponentType = "AND";
    private static final int COMPONENT_SIZE = 50;
    private static final int COMPONENT_WIDTH = 100;
    private static final int COMPONENT_HEIGHT = 50;

    public void addComponent(MemoryComponent component) {
        components.add(component);
    }

    private MemoryComponent getComponent(int x, int y) {
        for (MemoryComponent component : components) {
            if (component.contains(x, y)) {
                return component;
            }
        }
        return null;
    }

    private Wire getWireAt(int x, int y) {
        final int TOLERANCE = 5;
        for (Wire wire : wires) {
            int x1 = wire.getStart().getCenterX();
            int y1 = wire.getStart().getCenterY();
            int x2 = wire.getEnd().getCenterX();
            int y2 = wire.getEnd().getCenterY();
            
            double distance = Line2D.ptSegDist(x1, y1, x2, y2, x, y);
            if (distance < TOLERANCE) {
                return wire;
            }
        }
        return null;
    }

    //--------------INTERFACE GRAPHIQUE--------------//
    
    public Circuit() {
        setBackground(Color.LIGHT_GRAY);
        setFocusable(true);
        requestFocusInWindow();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();

                if (deletingMode) {
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
                    deletingMode = false;
                    repaint();
                }
                else if (addingComponent) {
                    addComponent(new MemoryComponent(
                        components.size() + 1,
                        addingComponentType,
                        e.getX() - COMPONENT_WIDTH/2,
                        e.getY() - COMPONENT_HEIGHT/2
                    ));
                    addingComponent = false;
                }
                else if (connectingMode) {
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

    public void enableAddingComponent(String type) {
        addingComponent = true;
        addingComponentType = type;
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

        for (Wire wire : wires) {
            wire.draw(g);
        }

        for (MemoryComponent component : components) {
            component.draw(g, component == selectedComponent);
        }
    }
}