import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class Wire {
    private String value; // Valeur du fil (0, 1, ou "undefined")
    private List<MemoryComponent> connections; // Liste des composants connectés
		private MemoryComponent start; // Graphical start component
    private MemoryComponent end;   // Graphical end component
    
    // Constructeur
    public Wire(MemoryComponent start, MemoryComponent end) {
        this.value = "undefined"; // Default value
        this.connections = new ArrayList<>();
        this.start = start;
        this.end = end;
        connections.add(start);
        connections.add(end);
    }

    // Getter pour la valeurs
    public String getValue() {
        return value;
    }

    // Setter pour la valeur
    public void setValue(String value) {
        this.value = value;
    }

    // Ajouter un composant à la liste des connexions
    public void addConnection(MemoryComponent component) {
        if (!connections.contains(component)) { // Évite les doublons
            connections.add(component);
        }
    }

    // Récupérer la liste des composants connectés
    public List<MemoryComponent> getConnections() {
        return new ArrayList<>(connections); // Return copy for encapsulation
    }
    // Récupérer le composant de départ
    public MemoryComponent getStart() {
        return start;
    }

    // Récupérer le composant d'arrivée
    public MemoryComponent getEnd() {
        return end;
    }

    // Modifier le composant de départ
    public void setStart(MemoryComponent start) {
        this.start = start;
        if (!connections.contains(start)) {
            connections.add(start);
        }
    }

    // Modifier le composant d'arrivée
    public void setEnd(MemoryComponent end) {
        this.end = end;
        if (!connections.contains(end)) {
            connections.add(end);
        }
    }
    
    // Dessine un fil entre les deux composants
    public void draw(Graphics g) {
        g.setColor(Color.BLACK); // Couleur du fil
        g.drawLine(
            start.getCenterX(), start.getCenterY(), // Centre du composant de départ
            end.getCenterX(), end.getCenterY()      // Centre du composant d'arrivée
        );
    }
}
