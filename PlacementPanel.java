import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PlacementPanel extends JPanel {
    private List<Component> components; // Liste des composants placés
    private boolean addingComponent = false; // Indicateur pour ajouter un composant

    public PlacementPanel() {
        setBackground(Color.LIGHT_GRAY); // Fond gris pour le panneau
        components = new ArrayList<>(); // Initialise la liste des composants

        // Ajoute un MouseListener pour détecter les clics de souris
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (addingComponent) { // Vérifie si l'ajout est activé
                    // Récupère les coordonnées du clic
                    int x = e.getX();
                    int y = e.getY();

                    // Ajoute un nouveau composant aux coordonnées cliquées
                    addNewComponent(x, y);

                    // Désactive l'ajout après avoir placé un composant
                    addingComponent = false;
                }
            }
        });
    }

    // Méthode pour activer l'ajout de composants
    public void enableAddingComponent() {
        addingComponent = true; // Active l'ajout via clic
    }

    // Ajoute un composant à la liste et redessine le panneau
    public void addNewComponent(int x, int y) {
        components.add(new Component(x, y)); // Crée et ajoute un nouveau composant
        repaint(); // Redessine le panneau pour afficher le composant
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Efface le fond et prépare le panneau

        // Dessine chaque composant de la liste
        for (Component component : components) {
            component.draw(g); // Appelle la méthode draw() de chaque composant
        }
    }
}
