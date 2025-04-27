import javax.swing.SwingUtilities;

public class MainApp {
    public static void main(String[] args) {
        // Démarre l'application (appel au constructeur de CurcuitUI)
        SwingUtilities.invokeLater(Window::new); 
    }
}