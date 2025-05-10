// File: AlgorithmAPXtraTest.java
// JDK-17: https://developers.redhat.com/products/openjdk/download?sc_cid=701f2000000RWTnAAO
// JUnit: https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter/5.10.0
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

/**
 * The AlgorithmAPXtraTest class provides JUnit tests for the recursive methods in the AlgorithmAPXtra class.
 * 
 * How to compile and run the application:
 * 1. Open a terminal or command prompt.
 * 2. Compile (Current Test Only): 
 *      `javac -cp std04-anderson-algo2/junit-platform-console-standalone-1.9.3.jar std04-anderson-algo2/AlgorithmAPXtra*.java`
 * 3. Compile (All Tests): 
 *      `javac -cp std04-anderson-algo2/junit-platform-console-standalone-1.9.3.jar std04-anderson-algo2/*.java`
 * 4. Run & Clean: 
 *      `java -jar std04-anderson-algo2/junit-platform-console-standalone-1.9.3.jar --class-path std04-anderson-algo2 --scan-classpath; rm -rf std04-anderson-algo2/*.class`
 */
public class AlgorithmAPXtraTest {
    

    /**
     * < Q27 >
     * Tests the makeBricks method.
     */
    @Test
    public void testMakeBricks() {
        assertTrue(AlgorithmAPXtra.makeBricks(3, 1, 8));
        assertFalse(AlgorithmAPXtra.makeBricks(3, 1, 9));
        assertTrue(AlgorithmAPXtra.makeBricks(3, 2, 10));
        assertTrue(AlgorithmAPXtra.makeBricks(3, 2, 8));
        assertFalse(AlgorithmAPXtra.makeBricks(3, 2, 9));
        assertTrue(AlgorithmAPXtra.makeBricks(6, 1, 11));
        assertFalse(AlgorithmAPXtra.makeBricks(6, 0, 11));
        assertTrue(AlgorithmAPXtra.makeBricks(1, 4, 11));
        assertTrue(AlgorithmAPXtra.makeBricks(0, 3, 10));
        assertFalse(AlgorithmAPXtra.makeBricks(1, 4, 12));
        assertTrue(AlgorithmAPXtra.makeBricks(3, 1, 7));
        assertFalse(AlgorithmAPXtra.makeBricks(1, 1, 7));
        assertTrue(AlgorithmAPXtra.makeBricks(2, 1, 7));
        assertTrue(AlgorithmAPXtra.makeBricks(7, 1, 11));
        assertTrue(AlgorithmAPXtra.makeBricks(7, 1, 8));
        assertFalse(AlgorithmAPXtra.makeBricks(7, 1, 13));
        assertTrue(AlgorithmAPXtra.makeBricks(43, 1, 46));
        assertFalse(AlgorithmAPXtra.makeBricks(40, 1, 46));
        assertTrue(AlgorithmAPXtra.makeBricks(40, 2, 47));
        assertTrue(AlgorithmAPXtra.makeBricks(40, 2, 50));
        assertFalse(AlgorithmAPXtra.makeBricks(40, 2, 52));
        assertFalse(AlgorithmAPXtra.makeBricks(22, 2, 33));
        assertTrue(AlgorithmAPXtra.makeBricks(0, 2, 10));
        assertTrue(AlgorithmAPXtra.makeBricks(1000000, 1000, 1000100));
        assertFalse(AlgorithmAPXtra.makeBricks(2, 1000000, 100003));
        assertTrue(AlgorithmAPXtra.makeBricks(20, 0, 19));
        assertFalse(AlgorithmAPXtra.makeBricks(20, 0, 21));
        assertFalse(AlgorithmAPXtra.makeBricks(20, 4, 51));
        assertTrue(AlgorithmAPXtra.makeBricks(20, 4, 39));
    }
    

    /**
     * < Q28 >
     * Tests the countYZ method.
     */
    @Test
    public void testCountYZ() {
        assertEquals(2, AlgorithmAPXtra.countYZ("fez day"));
        assertEquals(2, AlgorithmAPXtra.countYZ("day fez"));
        assertEquals(2, AlgorithmAPXtra.countYZ("day fyyyz"));
        assertEquals(1, AlgorithmAPXtra.countYZ("day yak"));
        assertEquals(1, AlgorithmAPXtra.countYZ("day:yak"));
        assertEquals(2, AlgorithmAPXtra.countYZ("!!day--yaz!!"));
        assertEquals(0, AlgorithmAPXtra.countYZ("yak zak"));
        assertEquals(2, AlgorithmAPXtra.countYZ("DAY abc XYZ"));
        assertEquals(3, AlgorithmAPXtra.countYZ("aaz yyz my"));
        assertEquals(2, AlgorithmAPXtra.countYZ("y2bz"));
        assertEquals(0, AlgorithmAPXtra.countYZ("zxyx"));
    }

    /**
     * < Q29 >
     * Tests the maxSpan method.
     */
    @Test
    public void testMaxSpan() {
        assertEquals(4, AlgorithmAPXtra.maxSpan(new int[]{1, 2, 1, 1, 3}));
        assertEquals(6, AlgorithmAPXtra.maxSpan(new int[]{1, 4, 2, 1, 4, 1, 4}));
        assertEquals(6, AlgorithmAPXtra.maxSpan(new int[]{1, 4, 2, 1, 4, 4, 4}));
        assertEquals(3, AlgorithmAPXtra.maxSpan(new int[]{3, 3, 3}));
        assertEquals(3, AlgorithmAPXtra.maxSpan(new int[]{3, 9, 3}));
        assertEquals(2, AlgorithmAPXtra.maxSpan(new int[]{3, 9, 9}));
        assertEquals(1, AlgorithmAPXtra.maxSpan(new int[]{3, 9}));
        assertEquals(2, AlgorithmAPXtra.maxSpan(new int[]{3, 3}));
        assertEquals(0, AlgorithmAPXtra.maxSpan(new int[]{}));
        assertEquals(1, AlgorithmAPXtra.maxSpan(new int[]{1}));
    }

    // Q30 comming soon..!
}
