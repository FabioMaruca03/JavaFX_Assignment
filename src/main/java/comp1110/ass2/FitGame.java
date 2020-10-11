package comp1110.ass2;

import comp1110.ass2.model.Sizes;
import javafx.css.Size;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static comp1110.ass2.model.Helper.*;

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
        if (placement == null || placement.isBlank() ||
                placement.toCharArray().length > 10*4 || placement.toCharArray().length % 4 != 0) return false;
        else {

            char[][] pieces = new char[placement.toCharArray().length/4][];

            for (int i = 0; i < placement.toCharArray().length; i+=4) {
                // Extrapolate current piece
                char[] temp = {placement.charAt(i), placement.charAt(i+1), placement.charAt(i+2), placement.charAt(i+3)};
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

            System.out.println("Temp = " + Arrays.toString(temp));
            System.out.println("Sorted Temp = " + Arrays.toString(keys));
            return Arrays.equals(keys, temp);
        }
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
        if (isPlacementWellFormed(placement)) {
            for (int i = 1; i+4 < placement.toCharArray().length; i+=4) {

            }
        }
        return false; // TODO Task 5: determine whether a placement string is valid
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
        List<Sizes> blankGame = List.of(
                Sizes.B, Sizes.G, Sizes.I, Sizes.L, Sizes.N, Sizes.O, Sizes.P,Sizes.R,
                Sizes.S, Sizes.Y, Sizes.s, Sizes.y, Sizes.b, Sizes.g, Sizes.i,Sizes.l,
                Sizes.n, Sizes.o, Sizes.p, Sizes.r
                );

        char[] s = new char[]{'N', 'E', 'S', 'W'};

        Set<String> viablePieces = new HashSet<>();

        if (placement.isBlank()) {
            for (Sizes sizes : blankGame) {
                for (char c : s) {
                    boolean d = false;
                    switch (c) {
                        case 'N': case 'S': {
                            if (sizes.w + col < 10 && sizes.h + row < 5)
                                d = true;
                            break;
                        }
                        case 'E': {
                            if (sizes.h + col < 10 && sizes.w + row < 5 && row-sizes.h >=0)
                                d = true;
                            break;
                        }
                        case 'W': {
                            if (sizes.h + col < 10 && sizes.w + row < 5 && row-sizes.h <=10)
                                d = true;
                            break;
                        }
                    }

                    if (d)
                        viablePieces.add(sizes.name() + col + row + c);
                }
            }
            System.out.println(viablePieces);
            return viablePieces;
        }
        else if (!isPiecePlacementWellFormed(placement))
            return null;
        else {
            Sizes[] sizes = new Sizes[placement.toCharArray().length/4];
            for (int i = 0; i+4 < placement.toCharArray().length; i+=4) {
                sizes[i/4] = Sizes.valueOf(String.valueOf(placement.charAt(i)));
            }

            for (int i = 0; i < sizes.length; i++) {
                int xPos = Integer.parseInt(String.valueOf(placement.charAt(i+1)));
                int yPos = Integer.parseInt(String.valueOf(placement.charAt(i+2)));
                char r = placement.charAt(i+3);

                switch (r) {
                    case 'N' : {
                        if (isOn(xPos, xPos+sizes[i].w, col) && isOn(yPos, yPos+sizes[i].h, row))
                            return null;
                        break;
                    }
                    case 'E' : {
                        if (isOn(yPos, yPos+sizes[i].w, col) && isOn(xPos, xPos+sizes[i].h, row))
                            return null;
                        break;
                    }
                    case 'S' : {
                        if (isOn(xPos, xPos+sizes[i].w, col) && isOn(yPos, yPos+sizes[i].h, row))
                            return null;
                        break;
                    }
                    case 'W' : {
                        if (isOn(xPos, xPos+sizes[i].w, col) && isOn(yPos, yPos+sizes[i].h, row))
                            return null;
                        break;
                    }
                    default: return null;
                }

            }

        }
        return null;
        //return null; // FIXME Task 6: determine the set of all viable piece placements given existing placements
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
