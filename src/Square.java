public class Square {
    private final boolean Movable;
    private boolean Covered;
    private final int x;
    private final int y;



    private boolean kBlock;


    private int source; // 1 = WKing 2 = WQueen 3 = both

    public Square(int x, int y, boolean Movable) { // blank tiles
        this.x = x;
        this.y = y;
        this.Movable = Movable;
        this.kBlock = false;
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


    public boolean isCovered() {
        return Covered;
    }


    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        if (this.source > 0) {
            this.source = 3;
        } else {
            this.source = source;
        }
    }

    public void setCover(boolean Covered) {
        this.Covered = Covered;

    }

    public boolean iskBlock() {
        return kBlock;
    }

    public void setkBlock(boolean kBlock) {
        this.kBlock = kBlock;
    }
}