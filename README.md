# ChessInterview
We have only three pieces (King and Queen on one side and a King - on the other). 
The input is some initial legal position (locations of the three pieces). The output is the list of all legal moves with an indication of whether the move is a check or a checkmate.

I decided to code for the case that white has a queen and a king left and it is white's turn since the only thing the lone king can do is run away from check.

## Square

Each Square on the chess board is represented as a square object with the following atributes:

**intager x,y** coordinate

**boolean Covered** indicating that the square is in the line of fire of a piece

**boolean Movable** indicating if a piece can leagally move there (immovable squares used to buffer the board)

**int source** indicating the source of the cover of a square

## Piece

Each piece is represented by a piece object which extends square.

**String Name** Indicates the type and color of the piece

**Piece clone** Returns deep copy of piece to be used in move calculations

## Board

Board object is a 10x10 array of squares and pieces with the edge squares being a buffer that pieces cannot move on.

**boolean check** indicates if pieces are in check

### Methods

**QueenMover**
Used in Queen cover to mark all squares where queen can move

**QueenCover**
Used to mark all squares where queen can move

**KingCover**
Used to mark all squares where king can move

**CalcCheckMate**
Calculates if board is in check mate by seeing if the black king has any valid moves by checking if surrounding squares are movable/ covered

Note: Because the black king has no way to check/check mate the other king, I took check mate to mean that the black king has no valid moves. 

**CalcCheck**
Calculates if board is in check by checking if the black king square is covered

## ChessMain
Takes input of piece configuration from user and exits with error message if invalid configuration is input. On valid input, it calculates and outputs possible moves.

### Methods

**printList**
Prints arraylist of moves with comma delimeter

**Qmove**
On input of a valid queen move, calculates if move is a check or checkmate.

**Kmove**
On input of a possible king move, calculates if move is valid (not a check or checkmate).

**ParseY**
On input of String board coordinate returns integer y coordinate

**ParseX**
On input of String board coordinate returns integer x coordinate

**ChessMove**
On input of integer board coordinates returns corresponding string coordinates

**PrintBoard**
Used for debugging :)
