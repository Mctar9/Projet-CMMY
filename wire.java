import java.util.ArrayList;
import java.util.List;

public class Wire {
    private String value; // Valeur du fil (0, 1, ou "undefined")
    private List<Component> connections; // Liste des composants connectés
    private List<int[]> path; // Liste des coordonnées (x, y) représentant le trajet du fil

    // Constructeur
    public Wire() {
        this.value = "undefined"; // Valeur par défaut
        this.connections = new ArrayList<>(); // Liste des connexions initialisée vide
        this.path = new ArrayList<>(); // Liste des coordonnées initialisée vide
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

    // Définir le chemin du fil
    public void setPath(List<int[]> path) {
        this.path = path;
    }
}
