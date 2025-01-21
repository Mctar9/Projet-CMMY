import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PlacementPanel extends JPanel {
    private List<Component> components; // Liste des composants placés
    private Component selectedComponent = null; // Composant actuellement sélectionné
    private boolean addingComponent = false; // Indicateur pour ajouter un composant

    public PlacementPanel() {
        setBackground(Color.LIGHT_GRAY); // Fond gris pour le panneau
        components = new ArrayList<>(); // Initialise la liste des composants

        // Ajoute un MouseListener pour gérer les clics
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (addingComponent) { // Mode ajout activé
                    addNewComponent(e.getX(), e.getY()); // Ajoute un composant aux coordonnées du clic
                    addingComponent = false; // Désactive le mode ajout
                } else {
                    // Sélectionne un composant sous le clic
                    selectedComponent = findComponentAtAt(e.getX(), e.getY());
                    repaint(); // Met à jour l'affichage pour montrer la sélection
                }
            }
        });

        // Ajoute un KeyListener pour gérer la rotation avec la touche "R"
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'r' && selectedComponent != null) {
                    selectedComponent.rotate(); // Tourne le composant sélectionné
                    repaint(); // Met à jour l'affichage pour refléter la rotation
                }
            }
        });

        setFocusable(true); // Rend le panneau focusable pour détecter les événements clavier
        requestFocusInWindow(); // Donne immédiatement le focus au panneau
    }

    // Méthode pour activer l'ajout de composants
    public void enableAddingComponent() {
        addingComponent = true; // Active le mode ajout
    }

    // Ajoute un composant à la liste et redessine le panneau
    public void addNewComponent(int x, int y) {
        components.add(new Component(x, y)); // Crée et ajoute un nouveau composant
        repaint(); // Redessine le panneau pour afficher le composant
    }

    // Recherche un composant à une position donnée
    
    public Component findComponentAtAt(int x, int y) {
    for (Component component : components) {
        if (component.contains(x, y)) {
            return component; // Retourne un composant trouvé dans la liste
        }
    }
    return null; // Aucun composant trouvé
}


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Efface le fond et prépare le panneau

        // Dessine chaque composant de la liste
        for (Component component : components) {
            component.draw(g, component == selectedComponent); // Passe true si le composant est sélectionné
        }
    }
}
