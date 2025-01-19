import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlacementPanel extends JPanel {
    private List<Component> components; // Liste des composants placés

    public PlacementPanel() {
        this.components = new ArrayList<>();
        this.setBackground(Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(800, 600));
    }

    // Méthode pour ajouter un nouveau composant
    public void addNewComponent(int x, int y) {
        Component newComponent = new Component(x, y);
        components.add(newComponent);
        repaint(); // Redessine le panneau avec le nouveau composant
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner tous les composants
        for (Component component : components) {
            component.draw(g);
        }
    }
}
