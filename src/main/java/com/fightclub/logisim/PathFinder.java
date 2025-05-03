package com.fightclub.logisim;
import java.util.*;

/**
 * Implémentation de l'algorithme de recherche de chemin par parcours en largeur (BFS)
 */
public class PathFinder {

    /**
     * Trouve un chemin entre deux points dans une matrice en utilisant BFS
     * @param matrix Matrice des obstacles (true = libre, false = obstacle)
     * @param startX Coordonnée X de départ
     * @param startY Coordonnée Y de départ
     * @param endX Coordonnée X d'arrivée
     * @param endY Coordonnée Y d'arrivée
     * @return Liste des points du chemin ou null si aucun chemin trouvé
     */
    public static List<Point> findPath(boolean[][] matrix, int startX, int startY, int endX, int endY) {
        // Vérification des limites de la matrice
        int n = matrix.length;
        if (startX < 0 || startX >= n || startY < 0 || startY >= n ||
            endX < 0 || endX >= n || endY < 0 || endY >= n) {
            return null;
        }

        // File pour le BFS contenant les chemins à explorer
        Queue<List<Point>> queue = new LinkedList<>();
        // Ensemble des points déjà visités
        Set<Point> visited = new HashSet<>();

        // Point de départ
        Point start = new Point(startX, startY);
        List<Point> initialPath = new ArrayList<>();
        initialPath.add(start);
        queue.add(initialPath);
        visited.add(start);

        // Directions possibles (haut, bas, gauche, droite)
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            List<Point> currentPath = queue.poll();
            Point currentPoint = currentPath.get(currentPath.size() - 1);

            // Si on a atteint la destination
            if (currentPoint.x == endX && currentPoint.y == endY) {
                return currentPath;
            }

            // Explorer les 4 directions
            for (int[] dir : directions) {
                int newX = currentPoint.x + dir[0];
                int newY = currentPoint.y + dir[1];
                Point newPoint = new Point(newX, newY);

                // Vérifier les limites et si la case est libre et non visitée
                if (newX >= 0 && newX < n && newY >= 0 && newY < n && 
                    matrix[newX][newY] && !visited.contains(newPoint)) {
                    
                    // Créer un nouveau chemin
                    List<Point> newPath = new ArrayList<>(currentPath);
                    newPath.add(newPoint);
                    
                    // Ajouter à la file et marquer comme visité
                    queue.add(newPath);
                    visited.add(newPoint);
                }
            }
        }

        // Aucun chemin trouvé
        return null;
    }

    /**
     * Classe interne pour représenter un point dans la matrice
     */
    public static class Point {
        public final int x;
        public final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

}