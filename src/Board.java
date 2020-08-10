public class Board {
    Square[][] Board;
    private boolean check;
    private boolean checkMate;
    private boolean path;


    public Board(Piece wK, Piece bK, Piece wQ) {
        Board = new Square[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0 || j == 0 || i == 9 || j == 9) {
                    Board[i][j] = new Square(i, j, false);
                } else {
                    Board[i][j] = new Square(i, j, true);
                }
            }
        }
        Board[wK.getY()][wK.getX()] = wK;
        if (Board[bK.getY()][bK.getX()] instanceof Piece) {
            System.out.println("Invalid configuration! Only one piece allowed per square.");
            System.exit(0);
        } else {
            Board[bK.getY()][bK.getX()] = bK;
        }

        if (Board[wQ.getY()][wQ.getX()] instanceof Piece) {
            System.out.println("Invalid configuration! Only one piece allowed per square.");
            System.exit(0);
        } else {
            Board[wQ.getY()][wQ.getX()] = wQ;
        }

    }

    public void QueenCover(Piece Queen) {


        int i = Queen.getX();
        int j = Queen.getY();
        this.path = true;

        //right
        while (this.getSquare(i, j).isMovable()) {

            if (this.queenBlocker(i, j)) {
                break;
            }
            i++;
        }
        i = Queen.getX();
        j = Queen.getY();
        this.path = true;
        //left
        while (this.getSquare(i, j).isMovable()) {
            if (this.queenBlocker(i, j)) {
                break;
            }
            i--;

        }

        i = Queen.getX();
        j = Queen.getY();
        this.path = true;
        while (this.getSquare(i, j).isMovable()) {

            if (this.queenBlocker(i, j)) {
                break;
            }
            j++;
        }
        i = Queen.getX();
        j = Queen.getY();
        this.path = true;
        while (this.getSquare(i, j).isMovable()) {

            if (this.queenBlocker(i, j)) {
                break;
            }
            j--;
        }
        i = Queen.getX();
        j = Queen.getY();
        this.path = true;

        while (this.getSquare(i, j).isMovable()) {
            if (this.queenBlocker(i, j)) {
                break;
            }

            i++;
            j++;
        }
        i = Queen.getX();
        j = Queen.getY();
        this.path = true;

        while (this.getSquare(i, j).isMovable()) {
            if (this.queenBlocker(i, j)) {
                break;
            }
            i--;
            j--;
        }
        i = Queen.getX();
        j = Queen.getY();
        this.path = true;

        while (this.getSquare(i, j).isMovable()) {
            if (this.queenBlocker(i, j)) {
                break;
            }

            i--;
            j++;
        }
        i = Queen.getX();
        j = Queen.getY();
        this.path = true;

        while (this.getSquare(i, j).isMovable()) {
            if (this.queenBlocker(i, j)) {
                break;
            }

            i++;
            j--;
        }
        this.getSquare(Queen.getX(), Queen.getY()).setCover(false);

    }

    public void kingCover(Piece King) {
        int x = King.getX();
        int y = King.getY();
        for (int r = 1; r > -2; r--) {
            for (int c = 1; c > -2; c--) {
                this.getSquare(r + x, y + c).setCover(true);
                this.getSquare(r + x, y + c).setSource(1);

            }
        }
        this.getSquare(x, y).setCover(false);
    }

    public boolean calcCheckMate(Square kingTile) {
        int x = kingTile.getX();
        int y = kingTile.getY();
        for (int r = 1; r > -2; r--) {
            for (int c = 1; c > -2; c--) {
                if (!this.getSquare(r + x, y + c).isCovered()) {
                    this.checkMate = false;
                    return false;
                }
            }
        }
        this.checkMate = true;
        return true;
    }

    public boolean calcCheck(Square kingTile) {
        this.check = kingTile.isCovered();
        return this.check;
    }

    public Square getSquare(int x, int y) {
        return Board[y][x];
    }


    public boolean isCheck() {
        return check;
    }

    public boolean queenBlocker(int i, int j) {
        if (this.getSquare(i, j) instanceof Piece) {
            Piece tmp = (Piece) this.getSquare(i, j);
            if (tmp.getName().equals("bK")) {
                this.path = false;
                this.check = true;
            } else if (tmp.getName().equals("wK")) {
                return true;
            }
        }
        if (this.path) {
            this.getSquare(i, j).setCover(true);
            this.getSquare(i, j).setSource(2);
        } else {
            this.getSquare(i, j).setCover(true);
            this.getSquare(i, j).setSource(2);
            this.getSquare(i, j).setBlocked(true);
        }
        return false;
    }

}