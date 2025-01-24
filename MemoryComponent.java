public class MemoryComponent {
    private int id;         // Unique identifier
    private String type;    // Type of the component (AND, OR, NOT, etc.)
    private int x, y;       // Position on the grid
    private boolean isVisited; // Temporary flag for memory model calculations

    public MemoryComponent(int id, String type, int x, int y) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.isVisited = false;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getType() { return type; }
    public int getX() { return x; }
    public int getY() { return y; }
    public void setVisited(boolean visited) { this.isVisited = visited; }
    public boolean isVisited() { return isVisited; }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
}

