package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import static comp1110.ass2.Games.SOLUTIONS;
import static org.junit.Assert.assertTrue;

public class SolutionsTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(120000);

    private void test(String objective, String expected) {
        String out = FitGame.getSolution(objective);
        assertTrue("No solution returned for objective " + objective + ", expected " + expected, out != null);
        assertTrue("For objective " + objective + ", was expecting " + expected + ", but got " + out, out.equals(expected));
    }

    @Test
    public void testStarter() {
        for (int i = 0; i < (SOLUTIONS.length / 5); i++) {
            test(SOLUTIONS[i].objective, SOLUTIONS[i].placement);
        }
    }

    @Test
    public void testJunior() {
        for (int i = (SOLUTIONS.length / 5); i < (2 * (SOLUTIONS.length / 5)); i++) {
            test(SOLUTIONS[i].objective, SOLUTIONS[i].placement);
        }
    }

    @Test
    public void testExpert() {
        for (int i = (2 * (SOLUTIONS.length / 5)); i < (3 * (SOLUTIONS.length / 5)); i++) {
            test(SOLUTIONS[i].objective, SOLUTIONS[i].placement);
        }
    }

    @Test
    public void testMaster() {
        for (int i = (3 * (SOLUTIONS.length / 5)); i < (4 * (SOLUTIONS.length / 5)); i++) {
            test(SOLUTIONS[i].objective, SOLUTIONS[i].placement);
        }
    }

    @Test
    public void testWizard() {
        for (int i = (4 * (SOLUTIONS.length / 5)); i < SOLUTIONS.length; i++) {
            test(SOLUTIONS[i].objective, SOLUTIONS[i].placement);
        }
    }
}
