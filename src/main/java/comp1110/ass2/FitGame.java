package comp1110.ass2;

import comp1110.ass2.model.Sizes;
import javafx.scene.chart.PieChart;

import java.util.*;


/**
 * This class provides the text interface for the IQ Fit Game
 * <p>
 * The game is based directly on Smart Games' IQ-Fit game
 * (https://www.smartgames.eu/uk/one-player-games/iq-fit)
 * </p>
 */
public class FitGame {
    private final static List<Sizes> blankGame = List.of(
            Sizes.B, Sizes.G, Sizes.I, Sizes.L, Sizes.N, Sizes.O, Sizes.P,Sizes.R,
            Sizes.S, Sizes.Y, Sizes.b, Sizes.g, Sizes.i, Sizes.l, Sizes.n,Sizes.o,
            Sizes.p, Sizes.r, Sizes.s, Sizes.y
    );

    private final static char[] s = new char[]{'N', 'S', 'W', 'E'};


    /**
     * Determine whether a piece placement is well-formed according to the
     * following criteria:
     * <pre>
     * - it consists of exactly four characters
     * - the first character is a valid piece descriptor character (b, B, g, G, ... y, Y)
     * - the second character is in the range 0 .. 9 (column)
     * - the third character is in the range 0 .. 4 (row)
     * - the fourth character is in valid orientation N, S, E, W
     * </pre>
     * @param piecePlacement A string describing a piece placement
     * @return True if the piece placement is well-formed
     */
    static synchronized boolean isPiecePlacementWellFormed(String piecePlacement) {
        if (piecePlacement.length() == 4) {
            char t = piecePlacement.toUpperCase().charAt(0); // Check for both lowercase and uppercase scenarios
            if (t == 'B' || t == 'G' || t == 'Y' || t == 'R' || t == 'O' || t == 'N' || t == 'I' || t == 'L' || t == 'P' || t == 'S') {
                if (!Character.isDigit(piecePlacement.toUpperCase().charAt(1)))
                    return false;
                int s = Integer.parseInt(String.valueOf(piecePlacement.toUpperCase().charAt(1)));
                if (s >= 0 && s <= 9) {
                    if (!Character.isDigit(piecePlacement.toUpperCase().charAt(2)))
                        return false;
                    s = Integer.parseInt(String.valueOf(piecePlacement.toUpperCase().charAt(2)));
                    if (s >= 0 && s <= 4) {
                        t = piecePlacement.toUpperCase().charAt(3);
                        boolean out = t == 'N' || t == 'S' || t == 'E' || t == 'W';
                        System.out.println("Piece of Placement : "+piecePlacement+" is well formed? "+out);
                        return out;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determine whether a placement string is well-formed:
     * <pre>
     * - it consists of exactly N four-character piece placements (where N = 1 .. 10);
     * - each piece placement is well-formed
     * - no shape appears more than once in the placement
     * - the pieces are ordered correctly within the string
     * </pre>
     * @param placement A string describing a placement of one or more pieces
     * @return True if the placement is well-formed
     */
    public synchronized static boolean isPlacementWellFormed(String placement) {
        if (placement == null || placement.isBlank() ||
                placement.toCharArray().length > 10*4 || placement.toCharArray().length % 4 != 0) return false;
        else {

            char[][] pieces = new char[placement.toCharArray().length/4][];

            for (int i = 0; i < placement.toCharArray().length; i+=4) {
                // Extrapolate current piece
                char[] temp = {
                        placement.charAt(i), placement.charAt(i+1), placement.charAt(i+2), placement.charAt(i+3)
                };
                if (!isPiecePlacementWellFormed(String.valueOf(temp))) {
                    return false;
                }
            }
            char[] keys = new char[pieces.length];
            char[] temp;
            for (int i = 0; i*4 < placement.toCharArray().length; i++) {
                keys[i] = Character.toUpperCase(placement.charAt(i*4));
            }

            temp = Arrays.copyOf(keys, pieces.length);
            Arrays.sort(keys);

            for (int i = 0; i < keys.length-1; i++) {
                if (keys[i] == keys[i+1])
                    return false;
            }

            return Arrays.equals(keys, temp); // Compares a sorted array with the current array
        }
    }

    private static int[][] board = new int[5][10];
    private synchronized static boolean notOverlaps(String placement) {
        System.out.println("soft overlap check: ");
        printBoard();
        System.out.println(placement);
            if (placement.isBlank()) { // if it's blank of course the board it's good
                return true;
            } else {
                char[][] pieces = extrapolatePiecesFromPlacement(placement);

                for (char[] piece : pieces) {
                    Sizes sizes = Sizes.valueOf(String.valueOf(piece[0]));
                    int xPos = Integer.parseInt(String.valueOf(piece[1]));
                    int yPos = Integer.parseInt(String.valueOf(piece[2]));
                    char orientation = piece[3];
                    boolean d = onBoardCheck(sizes, xPos, yPos, orientation);

                    if (!d) // if out of the board
                        return false;

                    switch (orientation) { // Overlapping cases
                        case 'N': {
                            // Pattern start
                            for (int j = 0; j < sizes.w; j++) {
                                if (board[yPos][xPos + j] == 0) { // Check place validity
                                    board[yPos][xPos + j] = 1; // Draw
                                } else return false;
                            }
                            if (board[yPos + 1][xPos + sizes.at[0] - 1] == 0) { // Draw first imperfection
                                board[yPos + 1][xPos + sizes.at[0] - 1] = 1;
                            } else return false;

                            if (sizes.at.length == 2) {
                                if (board[yPos + 1][xPos + sizes.at[1] - 1] == 0) { // Draw the second inperfection
                                    board[yPos + 1][xPos + sizes.at[1] - 1] = 1;
                                } else return false;
                            }

                            break;
                            // Pattern end
                        }
                        case 'S': {
                            for (int j = 0; j < sizes.w; j++) {
                                if (board[yPos + 1][xPos + j] == 0) {
                                    board[yPos + 1][xPos + j] = 1;
                                } else return false;
                            }
                            if (board[yPos][xPos + sizes.w - sizes.at[0]] == 0) {
                                board[yPos][xPos + sizes.w - sizes.at[0]] = 1;
                            } else return false;

                            if (sizes.at.length == 2) {
                                if (board[yPos][xPos + sizes.w - sizes.at[1]] == 0) {
                                    board[yPos][xPos + sizes.w - sizes.at[1]] = 1;
                                } else return false;
                            }

                            break;
                        }
                        case 'E': {
                            for (int j = 0; j < sizes.w; j++) {
                                if (board[yPos + j][xPos + 1] == 0) {
                                    board[yPos + j][xPos + 1] = 1;
                                } else return false;
                            }
                            if (board[yPos + sizes.at[0] - 1][xPos] == 0) {
                                board[yPos + sizes.at[0] - 1][xPos] = 1;
                            } else return false;

                            if (sizes.at.length == 2) {
                                if (board[yPos + sizes.at[1] - 1][xPos] == 0) {
                                    board[yPos + sizes.at[1] - 1][xPos] = 1;
                                } else return false;
                            }

                            break;
                        }
                        case 'W': {
                            for (int j = 0; j < sizes.w; j++) {
                                if (board[yPos + j][xPos] == 0) {
                                    board[yPos + j][xPos] = 1;
                                } else return false;
                            }
                            if (board[yPos + sizes.w - sizes.at[0]][xPos + 1] == 0) {
                                board[yPos + sizes.w - sizes.at[0]][xPos + 1] = 1;
                            } else return false;

                            if (sizes.at.length == 2) {
                                if (board[yPos + sizes.w - sizes.at[1]][xPos + 1] == 0) {
                                    board[yPos + sizes.w - sizes.at[1]][xPos + 1] = 1;
                                } else return false;
                            }

                            break;
                        }
                    }
                    printBoard();
                }
            }
            System.out.println("Placement : "+placement+" is valid!");
            return true;
   //     }
   //     return false;
    }

    private static void cloneBoard(int[][] clone, int[][] board) {
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, clone[i], 0, board[i].length);
        }
    }

    private static void printBoard() {
        System.out.println(); // DEBUG Print
        for (int j = 0; j < 5; j++) {
            for (int k = 0; k < 10; k++) {
                System.out.print(board[j][k] + "\t");
            }
            System.out.println();
        }
    }

    private static boolean onBoardCheck(Sizes sizes, int xPos, int yPos, char orientation) {
        boolean d = false;
        switch (orientation) { // Check if it's in the board
            case 'N':
            case 'S': {
                if (sizes.w - 1 + xPos < 10 && sizes.h - 1 + yPos < 5)
                    d = true;
                break;
            }
            case 'E':

            case 'W': {
                if (sizes.h - 1 + xPos < 10 && sizes.w - 1 + yPos < 5)
                    d = true;
                break;
            }
        }
        return d;
    }

    private static char[][] extrapolatePiecesFromPlacement(String placement) {
        char[][] pieces = new char[placement.toCharArray().length/4][];
        for (int i = 0, j = 0; i < placement.toCharArray().length; i+=4, j++) {
            // Get all pieces
            pieces[j] = new char[]{
                    placement.charAt(i), placement.charAt(i+1),
                    placement.charAt(i+2), placement.charAt(i+3)
            }; // input b00E at (0, 2),.  Expected [l02W, r01W]
        }
        return pieces;
    }

    /**
     * Determine whether a placement string is valid.
     *
     * To be valid, the placement string must be:
     * - well-formed, and
     * - each piece placement must be a valid placement according to the
     *   rules of the game:
     *   - pieces must be entirely on the board
     *   - pieces must not overlap each other
     *
     * @param placement A placement string
     * @return True if the placement sequence is valid
     */
    public synchronized static boolean isPlacementValid(String placement) {
        for (int i = 0; i < 5; i++) { // Create a blank board to emulate the game
            Arrays.fill(board[i], 0); // 0 for free space
        }
        return notOverlaps(placement);
    }

    /**
     * Given a string describing a placement of pieces, and a location
     * that must be covered by the next move, return a set of all
     * possible next viable piece placements which cover the location.
     *
     * For a piece placement to be viable it must:
     *  - be a well formed piece placement
     *  - be a piece that is not already placed
     *  - not overlap a piece that is already placed
     *  - cover the location
     *
     * @param placement A starting placement string
     * @param col      The location's column.
     * @param row      The location's row.
     * @return A set of all viable piece placements, or null if there are none.
     */
    static synchronized Set<String> getViablePiecePlacements(String placement, int col, int row) {
        Set<String> viable = new HashSet<>();
        Set<Character> keys = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            Arrays.fill(board[i], 0);
        }
        for (int i = 0; i < placement.length()/4; i++) {
            keys.add(placement.charAt(i*4));
        }

        char[][] pieces = extrapolatePiecesFromPlacement(placement);

        for (char[] piece : pieces) {
            Sizes sizes = Sizes.valueOf(String.valueOf(piece[0]));
            int xPos = Integer.parseInt(String.valueOf(piece[1]));
            int yPos = Integer.parseInt(String.valueOf(piece[2]));
            char orientation = piece[3];

            softOverlapCheck(sizes, xPos, yPos, orientation);
            printBoard();
        }

        int[][] boardBackup = new int[5][10];
        cloneBoard(boardBackup, board);

        for (int i = 0; i < boardBackup.length; i++) {
            int c = 0, x = -1;
            if (i == row) {
                if (i+1 < 4) {
                    for (int j = 0; j < 10; j++) {
                        if (boardBackup[i][j] == 0) {
                            if (x == -1)
                                x = j;
                            c++;
                        } else if (c < 3) {
                            c = -1;
                        }
                    }
                    if (c >= 3) {
                        int finalI = i;
                        int finalX = x;
                        blankGame.forEach(it->{
                            if (!keys.contains(Character.toLowerCase(it.name().charAt(0)))&&!keys.contains(Character.toUpperCase(it.name().charAt(0)))) {
                                String place = it.name()+finalX+finalI+'N';
                                if (onBoardCheck(it, finalX, finalI, 'N') && notOverlaps(place)) {
                                    viable.add(place);
                                }
                                cloneBoard(board, boardBackup);
                            }
                        });
                    }
                }
                cloneBoard(board, boardBackup);
                c = 0; x = -1;

                if (i-1 >= 0) {
                    for (int j = 0; j < 10; j++) {
                        if (boardBackup[i][j] == 0) {
                            if (x == -1)
                                x = j;
                            c++;
                        } else if (c < 3) {
                            c = -1;
                        }
                    }
                    if (c >= 3) {
                        int finalI = i-1;
                        int finalX = x;
                        blankGame.forEach(it->{
                            if (!keys.contains(Character.toLowerCase(it.name().charAt(0)))&&!keys.contains(Character.toUpperCase(it.name().charAt(0)))) {
                                String place = it.name()+finalX+finalI+'S';
                                if (onBoardCheck(it, finalX, finalI, 'S') && notOverlaps(place)) {
                                    viable.add(place);
                                }
                                cloneBoard(board, boardBackup);
                            }
                        }); // todo: keep debug from test_lastP
                    }
                }
            }
        }


        System.out.println("Viable for : "+placement+" are "+viable);
        return viable.isEmpty()?null:viable;
        // FIXME Task 6: determine the set of all viable piece placements given existing placements
    }

    private static void softOverlapCheck(Sizes sizes, int xPos, int yPos, char orientation) {
        switch (orientation) { // Overlapping cases
            case 'N': {
                // Pattern start
                for (int j = 0; j < sizes.w; j++) {
                    if (board[yPos][xPos + j] == 0) { // Check place validity
                        board[yPos][xPos + j] = 1; // Draw
                    }
                }
                if (board[yPos + 1][xPos + sizes.at[0] - 1] == 0) { // Draw first imperfection
                    board[yPos + 1][xPos + sizes.at[0] - 1] = 1;
                }

                if (sizes.at.length == 2) {
                    if (board[yPos + 1][xPos + sizes.at[1] - 1] == 0) { // Draw the second imperfection
                        board[yPos + 1][xPos + sizes.at[1] - 1] = 1;
                    }
                }

                break;
                // Pattern end
            }
            case 'S': {
                for (int j = 0; j < sizes.w; j++) {
                    if (board[yPos + 1][xPos + j] == 0) {
                        board[yPos + 1][xPos + j] = 1;
                    }
                }
                if (board[yPos][xPos + sizes.w - sizes.at[0]] == 0) {
                    board[yPos][xPos + sizes.w - sizes.at[0]] = 1;
                }

                if (sizes.at.length == 2) {
                    if (board[yPos][xPos + sizes.w - sizes.at[1]] == 0) {
                        board[yPos][xPos + sizes.w - sizes.at[1]] = 1;
                    }
                }

                break;
            }
            case 'E': {
                for (int j = 0; j < sizes.w; j++) {
                    if (board[yPos + j][xPos + 1] == 0) {
                        board[yPos + j][xPos + 1] = 1;
                    }
                }
                if (board[yPos + sizes.at[0] - 1][xPos] == 0) {
                    board[yPos + sizes.at[0] - 1][xPos] = 1;
                }

                if (sizes.at.length == 2) {
                    if (board[yPos + sizes.at[1] - 1][xPos] == 0) {
                        board[yPos + sizes.at[1] - 1][xPos] = 1;
                    }
                }

                break;
            }
            case 'W': {
                for (int j = 0; j < sizes.w; j++) {
                    if (board[yPos + j][xPos] == 0) {
                        board[yPos + j][xPos] = 1;
                    }
                }
                if (board[yPos + sizes.w - sizes.at[0]][xPos + 1] == 0) {
                    board[yPos + sizes.w - sizes.at[0]][xPos + 1] = 1;
                }

                if (sizes.at.length == 2) {
                    if (board[yPos + sizes.w - sizes.at[1]][xPos + 1] == 0) {
                        board[yPos + sizes.w - sizes.at[1]][xPos + 1] = 1;
                    }
                }

                break;
            }
        }
    }

    /**
     * Return the solution to a particular challenge.
     **
     * @param challenge A challenge string.
     * @return A placement string describing the encoding of the solution to
     * the challenge.
     */
    public static synchronized String getSolution(String challenge) {
        String result = "";
        List<String> pieces = new ArrayList<>();
        for (int i = 0; i < challenge.length() / 4; i++) {
            pieces.add(String.valueOf(challenge.charAt(i*4)+challenge.charAt(i*4+1)+challenge.charAt(i*4+2)+challenge.charAt(i*4+3)));
        }

        Map<String, Set<Character>> map = new HashMap<>();
        List<String> available = new ArrayList<>();
        for (Sizes sizes : blankGame) {
            for (char c : s) {
                available.add(sizes.name() + c);
            }
        }
        if (isPlacementValid(challenge)) {
            int counterColumn = 0;
            int x=0, y=0;
            int counterRow = 0;
            boolean isUpper = false;
            char candidate = ' ';
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 10; j++) {
                    if (board[i][j] == 0) {
                        if (x == 0)
                            x = j;
                        if (y == 0)
                            y = i;
                        counterRow++;
                    }
                }
                if (counterRow==4) {
                    if (y+1<5) {
                        int counter = 0;
                        for (int j = x; j < counterRow+x; j++) {
                            if(board[y+1][j] == 0)
                                counter++;
                        }
                        if (counter >= 2) {
                            isUpper = true;
                        }


                    } else if (y-1>0) {

                    }
                } else if (counterRow==3) {

                }
            }

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 5; j++) {

                }
            }

        }

        return result;  // FIXME Task 9: determine the solution to the game, given a particular challenge
    }
}
