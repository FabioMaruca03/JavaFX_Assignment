package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertTrue;

public class PiecePlacementWellFormedTest {
    // Original duration < 0.05sec * 10 = 0.5sec
    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    private void test(String in, boolean expected) {
        boolean out = FitGame.isPiecePlacementWellFormed(in);
        assertTrue("Input was '" + in + "', expected " + expected + " but got " + out, out == expected);
    }

    @Test
    public void fourCharacters() {
        test("o02W", true);
        test("L4", false);
        test("N41N", true);
        test("o02WI2", false);
    }

    @Test
    public void firstCharacterOK() {
        String testString = "B84Sg62Ni21Wl02Nn93No64EP02WR33ES61NY43N";
        for (int i = 0; i < testString.length() - 4; i += 4) {
            test(testString.substring(i, i + 4), true);
            test(testString.substring(i + 1, i + 5), false);
        }
        String sampleIDs = "RXOQYML?G!SZBVNTPKIFr=o;ywlcgds`b1n'p3i";
        for (int i = 0; i < sampleIDs.length() - 2; i += 2) {
            test(sampleIDs.charAt(i) + "12W", true);
            test(sampleIDs.charAt(i + 1) + "12W", false);
        }
    }

    @Test
    public void secondCharacterOK() {
        String testString = "B92SG02Ni90NL94NN92Eo94EP32Wr60NS11NY42E";
        for (int i = 0; i < testString.length() - 4; i += 4) {
            test(testString.substring(i, i + 4), true);
            test(testString.substring(i + 1, i + 5), false);
        }
        for (char c = '0'; c <= '9'; c++) {
            test("r" + c + "2W", true);
        }
        test("r!2W", false);
    }

    @Test
    public void thirdCharacterOK() {
        String testString = "B71WG03Ei00Nl53NN84Wo14Wp72Er71Ws12WY43S";
        for (int i = 0; i < testString.length() - 4; i += 4) {
            test(testString.substring(i, i + 4), true);
            test(testString.substring(i + 1, i + 5), false);
        }
        for (char c = '0'; c <= '4'; c++) {
            test("r4" + c + "N", true);
        }
        for (char c = '5'; c <= '9'; c++) {
            test("r4" + c + "N", false);
        }
    }

    @Test
    public void fourthCharacterOK() {
        String IDs = "roylgsbnpiROYLGSBNPI";
        char[] o = { 'N', 'E', 'S', 'W'};
        for (int i = 0; i < IDs.length(); i++) {
            for (int r = 0; r <= 3; r ++) {
                test(IDs.charAt(i) + "40" + o[r], true);
            }
            for (int r = 0; r <= 3; r ++) {
                char c = (char) (o[r] + 3);
                test(IDs.charAt(i) + "40" + c, false);
            }
        }
    }
}
