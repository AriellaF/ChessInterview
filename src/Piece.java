public class Piece extends Square {


    private final String name;


    public Piece(int x, int y, boolean Movable, String name) {
        super(x, y, Movable);
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public Piece clone() {
        return new Piece(this.getX(), this.getY(), this.isMovable(), this.getName());
    }
}
