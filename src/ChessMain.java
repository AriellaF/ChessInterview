import java.util.ArrayList;
import java.util.Scanner;


public class ChessMain {
    static ArrayList<String> qMoves = new ArrayList<>();
    static ArrayList<String> kMoves = new ArrayList<>();
    static ArrayList<String> qChecks = new ArrayList<>();
    static ArrayList<String> kChecks = new ArrayList<>();
    static ArrayList<String> kMates = new ArrayList<>();
    static ArrayList<String> qMates = new ArrayList<>();
    static Board Orig;
    static boolean thrown = false;

    public static void main(String[] args) {
        test();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter location of the white king.");
        String wKing = scanner.next();

        System.out.println("Please enter location of the white queen.");
        String wQueen = scanner.next();

        System.out.println("Please enter location of the black king.");
        String bKing = scanner.next();


        PlayChess(wKing, wQueen, bKing);

        System.out.println("The white King can make the following moves to achieve check mate:");
        System.out.println(printList(kMates));

        System.out.println("The white King can make the following moves to achieve check:");
        System.out.println(printList(kChecks));

        System.out.println("The white King can make the following moves:");
        System.out.println(printList(kMoves));

        System.out.println("The white Queen can make the following moves to achieve check mate:");
        System.out.println(printList(qMates));

        System.out.println("The white Queen can make the following moves to achieve check:");
        System.out.println(printList(qChecks));

        System.out.println("The white Queen can make the following moves:");
        System.out.println(printList(qMoves));


    }

    private static void PlayChess(String wKing, String wQueen, String bKing) {
        Piece whiteKing = new Piece(parseX(wKing), parseY(wKing), true, "wK");

        Piece whiteQueen = new Piece(parseX(wQueen), parseY(wQueen), true, "wQ");

        Piece blackKing = new Piece(parseX(bKing), parseY(bKing), true, "bK");


        try {
            Orig = new Board(whiteKing, blackKing, whiteQueen);
            if (!Orig.isValid()) {
                thrown = true;
                return;
            }
            Orig.kingCover(whiteKing);
            Orig.kingCover(blackKing);

            Orig.calcCheck(blackKing);

            Orig.QueenCover(whiteQueen);
        } catch (Exception e) {
            System.out.println("A piece was placed out of bounds.");
            thrown = true;
            return;
        }

        if (Orig.isCheck()) {
            System.out.println("King cannot start in check! Try again with valid configuration.");
            thrown = true;
            return;
        }
        if (Orig.calcCheckMate(blackKing)) {
            System.out.println("No new moves. White wins!");
            thrown = true;
            return;
        }

//        printBoard(Orig);
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {

                if (Orig.getSquare(i, j).getSource() == 1 && !(Orig.getSquare(i, j) instanceof Piece && !(Orig.getSquare(i, j).iskBlock()))) {
                    Kmove(i, j, blackKing, whiteQueen);
                } else if (Orig.getSquare(i, j).getSource() == 2 && !(Orig.getSquare(i, j) instanceof Piece)) {
                    Qmove(whiteKing, blackKing, i, j);
                } else if (Orig.getSquare(i, j).getSource() == 3 && !(Orig.getSquare(i, j) instanceof Piece)) {
                    if (!(Orig.getSquare(i, j).iskBlock())) {
                        Kmove(i, j, blackKing, whiteQueen);

                    }
                    Qmove(whiteKing, blackKing, i, j);
                }
            }
        }
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


        if (tmp.calcCheckMate(BK)) {
            kMates.add(chessMove(x, y));
        } else if (tmp.calcCheck(BK)) {
            kChecks.add(chessMove(x, y));
        } else {
            kMoves.add(chessMove(x, y));
        }
    }


    static int parseY(String space) {
        return Integer.parseInt(space.substring(1, 2));
    }

    static int parseX(String space) {
        char temp = Character.toLowerCase(space.charAt(0));
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

    static void reset() {
        qMoves = new ArrayList<>();
        kMoves = new ArrayList<>();
        qChecks = new ArrayList<>();
        qMates = new ArrayList<>();
        kChecks = new ArrayList<>();
        kMates = new ArrayList<>();
        thrown = false;

    }

    static void test() {

        // g2 h1 a8         1. because white king blocks queens check initially (queen can only move up and left)
        // c3 h2 a1         2. tests if king protecting the queen causes checkmate
        // c8 h2 a1         3. opposite of above - if program sees unprotected queen next to opposing king = not checkmate
        // a1 b1 a3         4. white king cant move
        // i8 a1 b7         5. test if program will deny out of bounds moves or will it just break
        // a1 a1 a1         6. test if program denies piles of pieces
        // a1 c8 a2         7. test if program doesnt allow adjacent kings
        // a1 c8 c7         8. test if it denies starting in check (shoud work the same for starting in check mate)
        // b4 d6 e3         9. generic starting positions
        // f1 a1 h1         10. generic starting position - checkmate possible with queen moving to h8


        System.out.println("Test 1: g2 h1 a8");
        PlayChess("g2", "h1", "a8");
        if (!printList(kChecks).equals("f1, f2, g1, g3, h2, h3")) {
            System.out.println("King checks incorrectly identified.");
        } else if (!printList(kMoves).equals("f3")) {
            System.out.println("King moves incorrectly identified.");
        } else {
            System.out.println("Test 1 Passed");
        }
        reset();

        System.out.println("Test 2: c3 h2 a1");
        PlayChess("c3", "h2", "a1");
        if (!printList(qMates).equals("b2")) {
            System.out.println("Queen check mates incorrectly identified.");
        } else if (!printList(qChecks).equals("a2, g1, h1")) {
            System.out.println("Queen checks incorrectly identified.");
        } else {
            System.out.println("Test 2 Passed");
        }
        reset();


        System.out.println("Test 3: c8 h2 a1");
        PlayChess("c8", "h2", "a1");
        if (!qMates.isEmpty()) {
            System.out.println("Queen check mates incorrectly identified.");
        } else if (!printList(qChecks).equals("a2, b2, e5, g1, h1, h8")) {
            System.out.println("Queen checks incorrectly identified.");
        } else {
            System.out.println("Test 3 Passed");
        }
        reset();

        System.out.println("Test 4: a1 b1 a3");
        PlayChess("a1", "b1", "a3");
        if (!kMates.isEmpty() || !kChecks.isEmpty()) {
            System.out.println("King checks incorrectly identified.");
        } else if (!kMoves.isEmpty()) {
            System.out.println("King moves incorrectly identified.");
        } else {
            System.out.println("Test 4 Passed");
        }
        reset();


        System.out.println("Test 5: i8 a1 b7");
        PlayChess("i8", "a1", "b7");
        if (!thrown) {
            System.out.println("Error incorrectly identified");
        } else {
            System.out.println("Test 5 Passed");
        }

        reset();

        System.out.println("Test 6: a1 a1 a1");
        PlayChess("a1", "a1", "a1");

        if (!thrown || !kMoves.isEmpty() || !kChecks.isEmpty() || !kMates.isEmpty() || !qMates.isEmpty() || !qMoves.isEmpty()|| !qChecks.isEmpty()) {
            System.out.println("Error incorrectly identified");
        } else {
            System.out.println("Test 6 Passed");
        }
        reset();

        System.out.println("Test 7: a1 c8 a2");
        PlayChess("a1", "c8", "a2");

        if (!thrown) {
            System.out.println("Error incorrectly identified");
        } else if (!qMoves.isEmpty() || !kMoves.isEmpty() || !kChecks.isEmpty() || !kMates.isEmpty() || !qMates.isEmpty() || !qChecks.isEmpty()) {
            System.out.println("Error incorrectly handled");
        } else {
            System.out.println("Test 7 Passed");
        }
        reset();

        System.out.println("Test 8: a1 c8 c7");
        PlayChess("a1", "c8", "c7");

        if (!thrown || !qChecks.isEmpty() || !kMoves.isEmpty() || !kChecks.isEmpty() || !kMates.isEmpty() || !qMates.isEmpty() || !qMoves.isEmpty()) {
            System.out.println("Error incorrectly identified");
        } else {
            System.out.println("Test 8 Passed");
        }
        reset();

        System.out.println("Test 9: b4 d6 e3");
        PlayChess("b4", "d6", "e3");
        if (!kMates.isEmpty() || !kChecks.isEmpty()) {
            System.out.println("King checks incorrectly identified.");
        } else if (!printList(kMoves).equals("a3, a4, a5, b3, b5, c3, c4, c5")) {
            System.out.println("King moves incorrectly identified.");
        } else if (!qMates.isEmpty()) {
            System.out.println("Queen mates incorrectly identified.");
        } else if (!printList(qMoves).equals("a6, b8, c6, c7, d1, d5, d7, d8, f6, f8, g6, h2")||!printList(qChecks).equals("b6, c5, d2, d3, d4, e5, e6, e7, f4, g3, h6")) {
            System.out.println("Queen moves incorrectly identified.");
        } else {
            System.out.println("Test 9 Passed");
        }
        reset();

        System.out.println("Test 10: f1 a1 h1");
        PlayChess("f1", "a1", "h1");
        if (!kMates.isEmpty() && !printList(kMoves).equals("e2, f2")) {
            System.out.println("King checks incorrectly identified.");
        } else if (!printList(kMoves).equals("e1")) {
            System.out.println("King moves incorrectly identified.");
        } else if (!printList(qMates).equals("h8")) {
            System.out.println("Queen mates incorrectly identified.");
        } else if (!printList(qChecks).equals("a8")) {
            System.out.println("Queen checks incorrectly identified.");
        } else if (!printList(qMoves).equals("a2, a3, a4, a5, a6, a7, b1, b2, c1, c3, d1, d4, e1, e5, f6, g7")) {
            System.out.println("Queen moves incorrectly identified.");
        } else {
            System.out.println("Test 10 Passed");
        }
        reset();
    }

}






