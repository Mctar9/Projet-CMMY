import java.awt.*; // Cette importation permet d'utiliser les classes graphiques comme Graphics pour dessiner les composants.

public class Component {
    private int x;
    private int y;
    private final int SIZE = 50; // Taille fixe du composant: 50 pixels

    // Constructeur de la classe
    public Component(int x, int y) {
        this.x = x;
        this.y = y;
    }
    // Méthode pour dessiner graphiquement le composant dans le panneau.
    public void draw(Graphics g) {
        g.setColor(Color.BLUE); // Définit la couleur de remplissage du composant comme bleu
        g.fillRect(x, y, SIZE, SIZE); // Dessine un rectangle rempli
        g.setColor(Color.BLACK); // Définit la couleur de la bordure du rectangle comme noir
        g.drawRect(x, y, SIZE, SIZE); // Dessine la bordure du rectangle
    }
}