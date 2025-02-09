import javax.swing.*;
import java.awt.*;

public class Window {
    private JFrame frame;
    private Circuit circuit;

    public Window() {
        frame = new JFrame("Simulation de circuit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        circuit = new Circuit();
        frame.add(circuit, BorderLayout.CENTER);

        JPanel toolbar = createToolbar();
        frame.add(toolbar, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createToolbar() {
        JPanel toolbar = new JPanel();
    
        // Nouveaux boutons
        String[] components = {"AND", "OR", "NOT", "0", "1"};
        for (String type : components) {
            JButton btn = new JButton(type);
            btn.addActionListener(e -> circuit.enableAddingComponent(type));
            toolbar.add(btn);
        }
    
        JButton connectButton = new JButton("Relier");
        JButton deleteButton = new JButton("Supprimer");
        
        connectButton.addActionListener(e -> circuit.enableConnectingMode());
        deleteButton.addActionListener(e -> circuit.enableDeletingMode());
    
        toolbar.add(connectButton);
        toolbar.add(deleteButton);
        
        return toolbar;
    }
}