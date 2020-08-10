import java.util.ArrayList;
import java.util.Scanner;

public class ChessMain {
    static ArrayList<String> qMoves = new ArrayList<>();
    static ArrayList<String> kMoves = new ArrayList<>();
    static ArrayList<String> qChecks = new ArrayList<>();
    static ArrayList<String> qMates = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please use lowercase.");
        System.out.println("Please enter location of the white king.");
        String wKing = scanner.next();

        System.out.println("Please enter location of the white queen.");
        String wQueen = scanner.next();

        System.out.println("Please enter location of the black king.");
        String bKing = scanner.next();

        Piece whiteKing = new Piece(parseX(wKing), parseY(wKing), true, "wK");

        Piece whiteQueen = new Piece(parseX(wQueen), parseY(wQueen), true, "wQ");

        Piece blackKing = new Piece(parseX(bKing), parseY(bKing), true, "bK"); // yo by making this not movable it becomes covered so it checks itself
        Board Orig = new Board(whiteKing, blackKing, whiteQueen);
        blackKing.setCover(false);
        Orig.kingCover(whiteKing);

        Orig.calcCheck(blackKing);


        Orig.QueenCover(whiteQueen);
        if (Orig.isCheck()) {
            System.out.println("King cannot start in check! Try again with valid configuration.");
            System.exit(0);
        }
        if (Orig.calcCheckMate(blackKing)) {
            System.out.println("No new moves. White wins!");

            System.exit(0);
        }

        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {

                if (Orig.getSquare(i, j).getSource() == 1 && !(Orig.getSquare(i, j) instanceof Piece)) {
                    Kmove(i, j, blackKing, whiteQueen);
                } else if (Orig.getSquare(i, j).getSource() == 2 && !(Orig.getSquare(i, j) instanceof Piece)) {
                    Qmove(whiteKing, blackKing, i, j);
                } else if (Orig.getSquare(i, j).getSource() == 3 && !(Orig.getSquare(i, j) instanceof Piece)) {
                    Kmove(i, j, blackKing, whiteQueen);
                    Qmove(whiteKing, blackKing, i, j);
                }
            }
        }

        System.out.println("The white King can make the following moves:");
        System.out.println(printList(kMoves));

        System.out.println("The white Queen can make the following moves to achieve check mate:");
        System.out.println(printList(qMates));

        System.out.println("The white Queen can make the following moves to achieve check:");
        System.out.println(printList(qChecks));

        System.out.println("The white Queen can make the following moves:");
        System.out.println(printList(qMoves));



    }

    private static String printList(ArrayList<String> l) {
        return String.join(", ", l);
    }

    private static void Qmove(Piece wK, Piece bK, int x, int y) {
        Piece WK = wK.clone();
        Piece BK = bK.clone();
        Piece move = new Piece(x, y, true, "wQ");
        Board tmp = new Board(WK, BK, move);

        tmp.QueenCover(move);

        tmp.kingCover(WK);


        if (tmp.calcCheckMate(BK)) {
            qMates.add(chessMove(x, y));

        } else if (tmp.calcCheck(BK)) {
            qChecks.add(chessMove(x, y));
        } else {
            qMoves.add(chessMove(x, y));
        }

    }

    private static void Kmove(int x, int y, Piece bK, Piece wQ) {
        Piece move = new Piece(x, y, false, "wK");
        Piece BK = bK.clone();
        Piece WQ = wQ.clone();
        Board tmp = new Board(move, BK, WQ);
        tmp.kingCover(move);
        tmp.QueenCover(WQ);

        if (!tmp.calcCheck(BK) && !tmp.calcCheckMate(BK)) {
            kMoves.add(chessMove(x, y));
        }
    }


    static int parseY(String space) {
        return Integer.parseInt(space.substring(1, 2));
    }

    static int parseX(String space) {
        char temp = space.charAt(0);
        return (temp - 'a' + 1);
    }

    static String chessMove(int x, int y) {
        char tempX = (char) (x + 96);
        return "" + tempX + "" + (y);
    }

    static void printBoard(Board b) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (b.getSquare(j, i) instanceof Piece) {
                    if (((Piece) b.getSquare(j, i)).getName().equals("wQ")) {
                        System.out.print("Q");
                    } else if (b.getSquare(j, i).isCovered()) {
                        System.out.print("k");
                    } else {
                        System.out.print("K");
                    }
                } else if (!b.getSquare(j, i).isMovable()) {
                    System.out.print("x");
                } else if (b.getSquare(j, i).isCovered()) {
                    System.out.print("+");
                } else {
                    System.out.print(".");
                }
            }
            System.out.print("\n");
        }
    }
}


