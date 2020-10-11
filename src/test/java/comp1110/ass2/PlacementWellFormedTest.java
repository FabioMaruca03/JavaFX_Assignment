package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static comp1110.ass2.Games.SOLUTIONS;
import static comp1110.ass2.TestUtility.BAD_PIECES;
import static org.junit.Assert.assertTrue;


public class PlacementWellFormedTest {
    // original duration < 0.05 sec -> 0.5 sec
    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    private void test(String in, boolean expected) {
        boolean out = FitGame.isPlacementWellFormed(in);
        assertTrue("Input was '" + in + "', expected " + expected + " but got " + out, out == expected);
    }

    @Test
    public void n4Pieces() {
        test("", false); // empty string
        test("o81NR42N", true); // two pieces placed on the board
        test("g13El84WO01NS30Ey81NB", false); // five pieces placed on the board, followed by 'B'
        test("b00Wg13Wi91Nl90EN22No03EP73Sr94Ss84SY42E", true); // complete board string
    }

    @Test
    public void piecesWellFormed() {
        for (int i = 0; i < SOLUTIONS.length; i++)
            for (int j = 4; j < SOLUTIONS[i].placement.length(); j += 4) {
                test(SOLUTIONS[i].placement.substring(0, j), true);
                test(SOLUTIONS[i].placement.substring(0, j - 4) + BAD_PIECES[i % BAD_PIECES.length], false);
            }
    }

    @Test
    public void correctOrder() {
        for (int i = 0; i < SOLUTIONS.length; i++) {
            test(SOLUTIONS[i].placement, true);
            String wrong = SOLUTIONS[i].placement.substring(SOLUTIONS[i].placement.length() / 2);
            wrong += SOLUTIONS[i].placement.substring(0, SOLUTIONS[i].placement.length() / 2);
            test(wrong, false);
        }
    }

    @Test
    public void duplicatesA() {
        for (int i = 0; i < 40; i += 4) {
            if (i == 16)
                continue;
            String bad = SOLUTIONS[100].placement.substring(0, i) +
                    SOLUTIONS[100].placement.substring(16, 20) +
                    SOLUTIONS[100].placement.substring(i + 4, 40);
            test(SOLUTIONS[i + 32].placement, true);
            test(bad, false);
        }
    }

    @Test
    public void duplicatesB() {
        for (int i = 0; i < 40; i += 4) {
            if (i == 16)
                continue;
            String bad = SOLUTIONS[100].placement.substring(0, i) +
                    SOLUTIONS[100].placement.substring(16, 20).charAt(0) +
                    "00W" +
                    SOLUTIONS[100].placement.substring(i + 4, 40);
            test(SOLUTIONS[i + 32].placement, true);
            test(bad, false);
        }
    }
}
