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

    // Méthode pour simuler le circuit
    public void simuler() {
        // Initialiser tous les fils à "undefined"
        for (Wire wire : wires) {
            wire.setValue("undefined");
        }

        // Nombre maximum d'itérations pour éviter les boucles infinies
        int MAX_ITERATIONS = 1000;
        boolean stable = false;

        // Boucle de simulation
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            stable = true;

            // Parcourir tous les fils
            for (Wire wire : wires) {
                String newValue = calculerValeurFil(wire);

                // Si la valeur du fil change, mettre à jour et marquer comme instable
                if (!newValue.equals(wire.getValue())) {
                    wire.setValue(newValue);
                    stable = false;
                }
            }

            // Si le circuit est stable, on arrête la simulation
            if (stable) {
                break;
            }
        }

        // Si le circuit n'est pas stable après MAX_ITERATIONS, lever une exception
        if (!stable) {
            throw new RuntimeException("Circuit instable : pas de point fixe trouvé après " + MAX_ITERATIONS + " itérations.");
        }
    }

    // Méthode pour calculer la valeur d'un fil en fonction des composants connectés
    private String calculerValeurFil(Wire wire) {
        // Récupérer les composants connectés au fil
        List<MemoryComponent> composantsConnectes = wire.getConnections();

        // Calculer la valeur du fil en fonction des composants
        // (Cette partie dépend de la logique spécifique de chaque composant YOUCEF help me here bro)
        String valeur = "undefined";

        for (MemoryComponent composant : composantsConnectes) {
            if (composant instanceof AndGate) {
                valeur = calculerValeurAND(composant);
            } else if (composant instanceof OrGate) {
                valeur = calculerValeurOR(composant);
            } else if (composant instanceof NotGate) {
                valeur = calculerValeurNOT(composant);
            }
            // Ajouter d'autres types de composants ici si nécessaire,YOUUUUUCEEEF <3
        }

        return valeur;
    }

    // Méthodes pour calculer les valeurs des portes logiques
    private String calculerValeurAND(MemoryComponent composant) {
        // Logique pour la porte AND YOUUUUCEFF HELPPP!!!
        // Exemple : si toutes les entrées sont "1", la sortie est "1", sinon "0" c'est ca Youcef !!!?
        return "0"; // À implémenter avec youcef
    }

    private String calculerValeurOR(MemoryComponent composant) {
        // Logique pour la porte OR
        // Exemple : si au moins une entrée est "1", la sortie est "1", sinon "0" c'est ca Youcef !!!?
        return "0"; // À implémenter avec youcef
    }

    private String calculerValeurNOT(MemoryComponent composant) {
        // Logique pour la porte NOT
        // Exemple : si l'entrée est "1", la sortie est "0", et vice versa c'est ca Youcef !!!?
        return "0"; // À implémenter avec youcef
    }
}

