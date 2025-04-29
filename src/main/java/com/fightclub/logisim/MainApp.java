import javax.swing.SwingUtilities;

/**
 * MainApp class serves as the entry point for the application.
 */
public class MainApp {
    public static void main(String[] args) {
        // Démarre l'application (appel au constructeur de CurcuitUI)
        SwingUtilities.invokeLater(Window::new); 
    }
}