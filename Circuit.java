import java.util.ArrayList;
import java.util.List;

public class Circuit {
    private List<Component> components = new ArrayList<>();
    private List<Wire> wires = new ArrayList<>();

    // Ajouter un composant
    public void addComponent(Component component) {
        components.add(component);
    }

    // Connecter deux composants avec un fil
    public void connect(Component start, Component end) {
        Wire wire = new Wire(start, end);
        wires.add(wire);
    }

    // Accéder aux composants
    public List<Component> getComponents() {
        return components;
    }

    // Accéder aux fils
    public List<Wire> getWires() {
        return wires;
    }
}

