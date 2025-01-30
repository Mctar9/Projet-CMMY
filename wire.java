import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class wire {
    private String value; // Valeur du fil (0, 1, ou "undefined")
    private List<MemoryComponent> connections; // Liste des composants connectés
		private MemoryComponent start; // Graphical start component
    private MemoryComponent end;   // Graphical end component
    
    // Constructeur
    public wire(MemoryComponent start, MemoryComponent end) {
        this.value = "undefined"; // Default value
        this.connections = new ArrayList<>();
        connections.add(start);
        connections.add(end);
        this.start = start;
        this.end = end;
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
    
    // Dessine un fil entre les deux composants
    public void draw(Graphics g) {
        g.setColor(Color.BLACK); // Couleur du fil
        g.drawLine(
            start.getCenterX(), start.getCenterY(), // Centre du composant de départ
            end.getCenterX(), end.getCenterY()      // Centre du composant d'arrivée
        );
    }
}
