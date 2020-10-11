package comp1110.ass2;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This class provides the text interface for the IQ Fit Game
 * <p>
 * The game is based directly on Smart Games' IQ-Fit game
 * (https://www.smartgames.eu/uk/one-player-games/iq-fit)
 */
public class FitGame {

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
    static boolean isPiecePlacementWellFormed(String piecePlacement) {
        if (piecePlacement.length() == 4) {
            char t = piecePlacement.toUpperCase().charAt(0);
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
                        return t == 'N' || t == 'S' || t == 'E' || t == 'W';
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
    public static boolean isPlacementWellFormed(String placement) {
        if (placement == null) return false;
        else {
            char[][] pieces = new char[10][];
            for (int i = 0, j = 0; i < placement.toCharArray().length; i+=4) {
                // Extrapolate current piece
                char[] temp = {placement.charAt(0), placement.charAt(1), placement.charAt(2), placement.charAt(3)};
                if (isPiecePlacementWellFormed(String.valueOf(temp))) {
                    for (char[] piece : pieces) {
                        // Check repetitions
                        if (Arrays.compare(piece, temp) == 0) {
                            return false;
                        }
                    }
                    if (j == 0)
                        pieces[0] = temp;
                    j++;
                } else return false;
            }
            char[] keys = new char[10];
            char[] temp;
            for (int i = 0; i < keys.length; i++) {
                keys[i] = placement.charAt(i*4);
            }
            temp = Arrays.copyOf(keys, 10);
            Arrays.sort(keys);
            if (Arrays.compare(keys, temp) != 0) {
                return false;
            }
        }
        return false; // FIXME Task 3: determine whether a placement is well-formed
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
    public static boolean isPlacementValid(String placement) {
        return false; // FIXME Task 5: determine whether a placement string is valid
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
    static Set<String> getViablePiecePlacements(String placement, int col, int row) {
        return null; // FIXME Task 6: determine the set of all viable piece placements given existing placements
    }

    /**
     * Return the solution to a particular challenge.
     **
     * @param challenge A challenge string.
     * @return A placement string describing the encoding of the solution to
     * the challenge.
     */
    public static String getSolution(String challenge) {
        return null;  // FIXME Task 9: determine the solution to the game, given a particular challenge
    }
}
