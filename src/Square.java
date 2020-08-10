public class Square {
    private boolean Movable;
    private boolean Covered;
    private boolean blocked;
    private int x;
    private int y;


    private int source; // 1 = WKing 2 = WQueen 3 = both

    public Square(int x, int y, boolean Movable) { // blank tiles
        this.x = x;
        this.y = y;
        this.Movable = Movable;
        this.blocked = false;

        this.Covered = !Movable;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public boolean isMovable() {
        return Movable;
    }

    public void setMovable(boolean movable) {
        Movable = movable;
    }

    public boolean isCovered() {
        return Covered;
    }

    public void setCovered(boolean covered) {
        this.Covered = covered;
    }


    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        if (this.source>0){
            this.source=3;
        }
        else {
            this.source = source;
        }
    }

    public void setCover(boolean Covered) {
        this.Covered = Covered;

    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
