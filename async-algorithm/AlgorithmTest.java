// JUnit: https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter/5.10.0
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

/**
 * The AlgorithmTest class provides JUnit tests for the recursive methods in the Algorithm class.
 * 
 * How to compile and run the application:
 * 1. Open a terminal or command prompt.
 * 2. Compile: 
 *      `javac -cp async-algorithm/junit-platform-console-standalone-1.9.3.jar async-algorithm/*.java`
 * 3. Run: 
 *      `java -jar async-algorithm/junit-platform-console-standalone-1.9.3.jar --class-path async-algorithm --scan-classpath`
 * 4. Clean: 
 *      `rm -rf async-algorithm/*.class`
 */
public class AlgorithmTest {

    /**
     * Tests the factorial method.
     */
    @Test
    public void testFactorial() {
        assertEquals(1, Algorithm.factorial(0), "0! should be 1");
        assertEquals(1, Algorithm.factorial(1), "1! should be 1");
        assertEquals(2, Algorithm.factorial(2), "2! should be 2");
        assertEquals(6, Algorithm.factorial(3), "3! should be 6");
        assertEquals(24, Algorithm.factorial(4), "4! should be 24");
        assertEquals(120, Algorithm.factorial(5), "5! should be 120");
    }

    /**
     * Tests the bunnyEars method.
     */
    @Test
    public void testBunnyEars() {
        assertEquals(0, Algorithm.bunnyEars(0), "0 bunnies should have 0 ears");
        assertEquals(2, Algorithm.bunnyEars(1), "1 bunny should have 2 ears");
        assertEquals(4, Algorithm.bunnyEars(2), "2 bunnies should have 4 ears");
        assertEquals(10, Algorithm.bunnyEars(5), "5 bunnies should have 10 ears");
        assertEquals(24, Algorithm.bunnyEars(12), "12 bunnies should have 24 ears");
    }

    /**
     * Tests the fibonacci method.
     */
    @Test
    public void testFibonacci() {
        assertEquals(0, Algorithm.fibonacci(0), "fibonacci(0) should be 0");
        assertEquals(1, Algorithm.fibonacci(1), "fibonacci(1) should be 1");
        assertEquals(1, Algorithm.fibonacci(2), "fibonacci(2) should be 1");
        assertEquals(2, Algorithm.fibonacci(3), "fibonacci(3) should be 2");
        assertEquals(3, Algorithm.fibonacci(4), "fibonacci(4) should be 3");
        assertEquals(5, Algorithm.fibonacci(5), "fibonacci(5) should be 5");
        assertEquals(8, Algorithm.fibonacci(6), "fibonacci(6) should be 8");
        assertEquals(13, Algorithm.fibonacci(7), "fibonacci(7) should be 13");
    }

    /**
     * Tests the sumDigits method.
     */
    @Test
    public void testSumDigits() {
        assertEquals(9, Algorithm.sumDigits(126), "Sum of digits of 126 should be 9");
        assertEquals(13, Algorithm.sumDigits(49), "Sum of digits of 49 should be 13");
        assertEquals(3, Algorithm.sumDigits(12), "Sum of digits of 12 should be 3");
        assertEquals(1, Algorithm.sumDigits(10), "Sum of digits of 10 should be 1");
        assertEquals(0, Algorithm.sumDigits(0), "Sum of digits of 0 should be 0");
        assertEquals(10, Algorithm.sumDigits(730), "Sum of digits of 730 should be 10");
    }

    /**
     * Tests the count8 method.
     */
    @Test
    public void testCount8() {
        assertEquals(1, Algorithm.count8(8), "count8(8) should return 1");
        assertEquals(2, Algorithm.count8(818), "count8(818) should return 2");
        assertEquals(4, Algorithm.count8(8818), "count8(8818) should return 4");
        assertEquals(4, Algorithm.count8(8088), "count8(8088) should return 4");
        assertEquals(0, Algorithm.count8(123), "count8(123) should return 0");
        assertEquals(2, Algorithm.count8(81238), "count8(81238) should return 2");
        assertEquals(6, Algorithm.count8(88788), "count8(88788) should return 6");
        assertEquals(1, Algorithm.count8(8234), "count8(8234) should return 1");
        assertEquals(1, Algorithm.count8(2348), "count8(2348) should return 1");
        assertEquals(3, Algorithm.count8(23884), "count8(23884) should return 3");
        assertEquals(0, Algorithm.count8(0), "count8(0) should return 0");
        assertEquals(5, Algorithm.count8(1818188), "count8(1818188) should return 5");
        assertEquals(5, Algorithm.count8(8818181), "count8(8818181) should return 5");
        assertEquals(1, Algorithm.count8(1080), "count8(1080) should return 1");
        assertEquals(3, Algorithm.count8(188), "count8(188) should return 3");
        assertEquals(9, Algorithm.count8(88888), "count8(88888) should return 9");
        assertEquals(2, Algorithm.count8(9898), "count8(9898) should return 2");
        assertEquals(1, Algorithm.count8(78), "count8(78) should return 1");
    }

    /**
     * Tests the powerN method.
     */
    @Test
    public void testPowerN() {
        assertEquals(3, Algorithm.powerN(3, 1), "powerN(3, 1) should return 3");
        assertEquals(9, Algorithm.powerN(3, 2), "powerN(3, 2) should return 9");
        assertEquals(27, Algorithm.powerN(3, 3), "powerN(3, 3) should return 27");
        assertEquals(2, Algorithm.powerN(2, 1), "powerN(2, 1) should return 2");
        assertEquals(4, Algorithm.powerN(2, 2), "powerN(2, 2) should return 4");
        assertEquals(8, Algorithm.powerN(2, 3), "powerN(2, 3) should return 8");
        assertEquals(16, Algorithm.powerN(2, 4), "powerN(2, 4) should return 16");
        assertEquals(32, Algorithm.powerN(2, 5), "powerN(2, 5) should return 32");
        assertEquals(10, Algorithm.powerN(10, 1), "powerN(10, 1) should return 10");
        assertEquals(100, Algorithm.powerN(10, 2), "powerN(10, 2) should return 100");
        assertEquals(1000, Algorithm.powerN(10, 3), "powerN(10, 3) should return 1000");
    }














    /********************************************************************************
     * ******************************************************************************
     * AP Challenge Questions
     * ******************************************************************************
     ********************************************************************************/

    
    /**
     * Tests the scoresIncreasing method.
     */
    @Test
    public void testScoresIncreasing() {
        assertTrue(Algorithm.scoresIncreasing(new int[]{1, 3, 4}));            // → True
        assertFalse(Algorithm.scoresIncreasing(new int[]{1, 3, 2}));           // → False
        assertTrue(Algorithm.scoresIncreasing(new int[]{1, 1, 4}));            // → True
        assertTrue(Algorithm.scoresIncreasing(new int[]{1, 1, 2, 4, 4, 7}));   // → True
        assertFalse(Algorithm.scoresIncreasing(new int[]{1, 1, 2, 4, 3, 7}));  // → False
        assertTrue(Algorithm.scoresIncreasing(new int[]{-5, 4, 11}));          // → True
    }

    /**
     * Tests the scores100 method.
     */
    @Test
    public void testScores100() {
        assertTrue(Algorithm.scores100(new int[]{1, 100, 100}));           // → True
        assertFalse(Algorithm.scores100(new int[]{1, 100, 99, 100}));        // → False
        assertTrue(Algorithm.scores100(new int[]{100, 1, 100, 100}));        // → True
        assertFalse(Algorithm.scores100(new int[]{100, 1, 100, 1}));         // → False
        assertFalse(Algorithm.scores100(new int[]{1, 2, 3, 4, 5}));          // → False
        assertFalse(Algorithm.scores100(new int[]{1, 2, 100, 4, 5}));        // → False
    }

    /**
     * Tests the scoresClump method.
     */
    @Test
    public void testScoresClump() {
        assertTrue(Algorithm.scoresClump(new int[]{3, 4, 5}));           // → True
        assertFalse(Algorithm.scoresClump(new int[]{3, 4, 6}));          // → False
        assertTrue(Algorithm.scoresClump(new int[]{1, 3, 5, 5}));         // → True
        assertTrue(Algorithm.scoresClump(new int[]{2, 4, 5, 6}));         // → True
        assertFalse(Algorithm.scoresClump(new int[]{2, 4, 5, 7}));         // → False
        assertTrue(Algorithm.scoresClump(new int[]{2, 4, 4, 7}));          // → True
        assertFalse(Algorithm.scoresClump(new int[]{3, 3, 6, 7, 9}));       // → False
        assertTrue(Algorithm.scoresClump(new int[]{3, 3, 7, 7, 9}));        // → True
        assertFalse(Algorithm.scoresClump(new int[]{4, 5, 8}));             // → False
    }

    /**
     * Tests the scoresAverage method.
     */
    @Test
    public void testScoresAverage() {
        assertEquals(4, Algorithm.scoresAverage(new int[]{2, 2, 4, 4}));
        assertEquals(4, Algorithm.scoresAverage(new int[]{4, 4, 4, 2, 2, 2}));
        assertEquals(4, Algorithm.scoresAverage(new int[]{3, 4, 5, 1, 2, 3}));
        assertEquals(6, Algorithm.scoresAverage(new int[]{5, 6}));
        assertEquals(5, Algorithm.scoresAverage(new int[]{5, 4}));
        assertEquals(5, Algorithm.scoresAverage(new int[]{5, 4, 5, 6, 2, 1, 2, 3}));
    }


    
    /**
     * Tests the wordsCount method.
     */
    @Test
    public void testWordsCount() {
        assertEquals(2, Algorithm.wordsCount(new String[]{"a", "bb", "b", "ccc"}, 1));
        assertEquals(1, Algorithm.wordsCount(new String[]{"a", "bb", "b", "ccc"}, 3));
        assertEquals(0, Algorithm.wordsCount(new String[]{"a", "bb", "b", "ccc"}, 4));
        assertEquals(2, Algorithm.wordsCount(new String[]{"xx", "yyy", "x", "yy", "z"}, 1));
        assertEquals(2, Algorithm.wordsCount(new String[]{"xx", "yyy", "x", "yy", "z"}, 2));
        assertEquals(1, Algorithm.wordsCount(new String[]{"xx", "yyy", "x", "yy", "z"}, 3));
    }
    
    /**
     * Tests the wordsFront method.
     */
    @Test
    public void testWordsFront() {
        assertArrayEquals(new String[]{"a"}, Algorithm.wordsFront(new String[]{"a", "b", "c", "d"}, 1));
        assertArrayEquals(new String[]{"a", "b"}, Algorithm.wordsFront(new String[]{"a", "b", "c", "d"}, 2));
        assertArrayEquals(new String[]{"a", "b", "c"}, Algorithm.wordsFront(new String[]{"a", "b", "c", "d"}, 3));
        assertArrayEquals(new String[]{"a", "b", "c", "d"}, Algorithm.wordsFront(new String[]{"a", "b", "c", "d"}, 4));
        assertArrayEquals(new String[]{"Hi"}, Algorithm.wordsFront(new String[]{"Hi", "There"}, 1));
        assertArrayEquals(new String[]{"Hi", "There"}, Algorithm.wordsFront(new String[]{"Hi", "There"}, 2));
    }
    
    /**
     * Tests the wordsWithoutList method.
     */
    @Test
    public void testWordsWithoutList() {
        List<String> expected1 = Arrays.asList("bb", "ccc");
        assertEquals(expected1, Algorithm.wordsWithoutList(new String[]{"a", "bb", "b", "ccc"}, 1));

        List<String> expected2 = Arrays.asList("a", "bb", "b");
        assertEquals(expected2, Algorithm.wordsWithoutList(new String[]{"a", "bb", "b", "ccc"}, 3));

        List<String> expected3 = Arrays.asList("a", "bb", "b", "ccc");
        assertEquals(expected3, Algorithm.wordsWithoutList(new String[]{"a", "bb", "b", "ccc"}, 4));

        List<String> expected4 = Arrays.asList("xx", "yyy", "yy");
        assertEquals(expected4, Algorithm.wordsWithoutList(new String[]{"xx", "yyy", "x", "yy", "z"}, 1));

        List<String> expected5 = Arrays.asList("yyy", "x", "z");
        assertEquals(expected5, Algorithm.wordsWithoutList(new String[]{"xx", "yyy", "x", "yy", "z"}, 2));
    }

    /**
     * Tests the hasOne method.
     */
    @Test
    public void testHasOne() {
        assertTrue(Algorithm.hasOne(10));
        assertFalse(Algorithm.hasOne(22));
        assertFalse(Algorithm.hasOne(220));
        assertTrue(Algorithm.hasOne(212));
        assertTrue(Algorithm.hasOne(1));
        assertFalse(Algorithm.hasOne(9));
        assertTrue(Algorithm.hasOne(211112));
        assertTrue(Algorithm.hasOne(121121));
        assertFalse(Algorithm.hasOne(222222));
        assertTrue(Algorithm.hasOne(56156));
        assertFalse(Algorithm.hasOne(56556));
    }

    /**
     * Tests the dividesSelf method.
     */
    @Test
    public void testDividesSelf() {
        assertTrue(Algorithm.dividesSelf(128));
        assertTrue(Algorithm.dividesSelf(12));
        assertFalse(Algorithm.dividesSelf(120));
        assertTrue(Algorithm.dividesSelf(122));
        assertFalse(Algorithm.dividesSelf(13));
        assertFalse(Algorithm.dividesSelf(32));
        assertTrue(Algorithm.dividesSelf(22));
        assertFalse(Algorithm.dividesSelf(42));
        assertTrue(Algorithm.dividesSelf(212));
        assertFalse(Algorithm.dividesSelf(213));
        assertTrue(Algorithm.dividesSelf(162));
    }

    /**
     * Tests the copyEvens method.
     */ 
    @Test
    public void testCopyEvens() {
        assertArrayEquals(new int[]{2, 4}, 
            Algorithm.copyEvens(new int[]{3, 2, 4, 5, 8}, 2));

        assertArrayEquals(new int[]{2, 4, 8}, 
            Algorithm.copyEvens(new int[]{3, 2, 4, 5, 8}, 3));

        assertArrayEquals(new int[]{6, 2, 4}, 
            Algorithm.copyEvens(new int[]{6, 1, 2, 4, 5, 8}, 3));

        assertArrayEquals(new int[]{6, 2, 4, 8}, 
            Algorithm.copyEvens(new int[]{6, 1, 2, 4, 5, 8}, 4));

        assertArrayEquals(new int[]{4}, 
            Algorithm.copyEvens(new int[]{3, 1, 4, 1, 5}, 1));

        assertArrayEquals(new int[]{2}, 
            Algorithm.copyEvens(new int[]{2}, 1));

        assertArrayEquals(new int[]{6, 2}, 
            Algorithm.copyEvens(new int[]{6, 2, 4, 8}, 2));

        assertArrayEquals(new int[]{6, 2, 4}, 
            Algorithm.copyEvens(new int[]{6, 2, 4, 8}, 3));

        assertArrayEquals(new int[]{6, 2, 4, 8}, 
            Algorithm.copyEvens(new int[]{6, 2, 4, 8}, 4));

        assertArrayEquals(new int[]{8}, 
            Algorithm.copyEvens(new int[]{1, 8, 4}, 1));

        assertArrayEquals(new int[]{8, 4}, 
            Algorithm.copyEvens(new int[]{1, 8, 4}, 2));

        assertArrayEquals(new int[]{2, 8}, 
            Algorithm.copyEvens(new int[]{2, 8, 4}, 2));
    }
}
