import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Circuit extends JPanel {
    private List<MemoryComponent> components = new ArrayList<>();
    private List<Wire> wires = new ArrayList<>();
    private MemoryComponent selectedComponent = null;

    private ConnectionPoint wireStartPoint = null;
    private Point currentMousePosition = null;

    private boolean addingComponent = false;
    private boolean deletingMode = false;
    private String addingComponentType;

    public Circuit() {
        setBackground(Color.WHITE);
        setFocusable(true);
        requestFocusInWindow();

        // Gestion des clics souris
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
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
            if (wire != null) wires.remove(wire);
        }
        repaint();
    }

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

    private ConnectionPoint findConnectionPoint(int x, int y) {
        for (MemoryComponent comp : components) {
            for (ConnectionPoint p : comp.getInputs()) {
                if (p.contains(x, y)) return p;
            }
            for (ConnectionPoint p : comp.getOutputs()) {
                if (p.contains(x, y)) return p;
            }
        }
        return null;
    }

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

    public void enableAddingComponent(String type) {
        addingComponent = true;
        addingComponentType = type.toUpperCase();
    }

    public void enableDeletingMode() {
        deletingMode = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        wires.forEach(wire -> wire.draw(g));
        components.forEach(comp -> comp.draw(g, comp == selectedComponent));

        if (wireStartPoint != null && currentMousePosition != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5}, 0));
            g2d.drawLine(wireStartPoint.getX(), wireStartPoint.getY(),
                         currentMousePosition.x, currentMousePosition.y);
        }
    }
}
