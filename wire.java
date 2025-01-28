import java.util.ArrayList;
import java.util.List;

public class wire {
    private String value; // Valeur du fil (0, 1, ou "undefined")
    private List<Component> connections; // Liste des composants connectés

    // Constructeur
    public wire(Component start, Component end) {
        this.value = "undefined"; // Default value
        this.connections = new ArrayList<>();
        connections.add(start);
        connections.add(end);
    }

    // Getter pour la valeur
    public String getValue() {
        return value;
    }

    // Setter pour la valeur
    public void setValue(String value) {
        this.value = value;
    }

    // Ajouter un composant à la liste des connexions
    public void addConnection(Component component) {
        if (!connections.contains(component)) { // Évite les doublons
            connections.add(component);
        }
    }

    // Récupérer la liste des composants connectés
    public List<Component> getConnections() {
        return connections;
    }

}
