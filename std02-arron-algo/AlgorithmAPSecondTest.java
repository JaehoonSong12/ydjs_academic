// File: AlgorithmAPSecondTest.java
// JDK-17: https://developers.redhat.com/products/openjdk/download?sc_cid=701f2000000RWTnAAO
// JUnit: https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter/5.10.0
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

/**
 * The AlgorithmAPSecondTest class provides JUnit tests for the recursive methods 
 * in the AlgorithmAPSecond class.
 * 
 * How to compile and run the application:
 * 1. Open a terminal or command prompt.
 * 2. Compile (Current Test Only): 
 *      ```
 *      javac -cp junit-1.9.3.jar std02-arron-algo/AlgorithmAPSecond*.java
 *      ```
 * 3. Compile (All Tests): 
 *      `javac -cp junit-1.9.3.jar std02-arron-algo/*.java`
 * 4. Run & Clean: 
 *      ```
 *      java -jar junit-1.9.3.jar --class-path std02-arron-algo --scan-classpath; rm -rf std02-arron-algo/*.class
 *      ```
 */
public class AlgorithmAPSecondTest {
    



    /**
     * < Q20 >
     * Tests the bubbleSort method.
     */
    @Test
    public void testBubbleSort() {
        assertArrayEquals(new int[]{1, 2, 4, 5, 8}, 
            AlgorithmAPSecond.bubbleSort(new int[]{5, 1, 4, 2, 8}));

        assertArrayEquals(new int[]{}, 
            AlgorithmAPSecond.bubbleSort(new int[]{}));

        assertArrayEquals(new int[]{3}, 
            AlgorithmAPSecond.bubbleSort(new int[]{3}));
    }

    /**
     * < Q21 >
     * Tests the selectionSort method.
     */
    @Test
    public void testSelectionSort() {
        assertArrayEquals(new int[]{11, 12, 22, 25, 64}, 
            AlgorithmAPSecond.selectionSort(new int[]{64, 25, 12, 22, 11}));

        assertArrayEquals(new int[]{1, 2}, 
            AlgorithmAPSecond.selectionSort(new int[]{2, 1}));
    }

    // /**
    //  * < Q22 >
    //  * Tests the insertionSort method.
    //  */
    // @Test
    // public void testInsertionSort() {
    //     assertArrayEquals(new int[]{5, 6, 11, 12, 13}, 
    //         AlgorithmAPSecond.insertionSort(new int[]{12, 11, 13, 5, 6}));

    //     assertArrayEquals(new int[]{1, 2, 3}, 
    //         AlgorithmAPSecond.insertionSort(new int[]{1, 2, 3}));
    // }

    /**
     * < Q23 >
     * Tests the mergeSort method.
     */
    @Test
    public void testMergeSort() {
        assertArrayEquals(new int[]{3, 9, 10, 27, 38, 43, 82}, 
            AlgorithmAPSecond.mergeSort(new int[]{38, 27, 43, 3, 9, 82, 10}));

        assertArrayEquals(new int[]{}, 
            AlgorithmAPSecond.mergeSort(new int[]{}));
    }

    /**
     * < Q24 >
     * Tests the quickSort method.
     */
    @Test
    public void testQuickSort() {
        assertArrayEquals(new int[]{1, 5, 7, 8, 9, 10}, 
            AlgorithmAPSecond.quickSort(new int[]{10, 7, 8, 9, 1, 5}));

        assertArrayEquals(new int[]{}, 
            AlgorithmAPSecond.quickSort(new int[]{}));
    }

    // /**
    //  * < Q25 >
    //  * Tests the heapSort method.
    //  */
    // @Test
    // public void testHeapSort() {
    //     assertArrayEquals(new int[]{5, 6, 7, 11, 12, 13}, 
    //         AlgorithmAPSecond.heapSort(new int[]{12, 11, 13, 5, 6, 7}));

    //     assertArrayEquals(new int[]{}, 
    //         AlgorithmAPSecond.heapSort(new int[]{}));
    // }

    /**
     * < Special >
     * Compares empirical running-times of O(n²) sorts vs. O(n log n) sorts.
     */
    @Test
    public void testTimeComplexityClassification() {
        final int n = 5_000;
        int[] base = new Random(0).ints(n, 0, n).toArray();

        // make copies
        int[] a1 = base.clone(), a2 = base.clone(), a3 = base.clone();
        int[] b1 = base.clone(), b2 = base.clone(), b3 = base.clone();

        // time O(n²) sorts
        long tBubble = time(() -> AlgorithmAPSecond.bubbleSort(a1));
        long tSelection = time(() -> AlgorithmAPSecond.selectionSort(a2));
        long tInsertion = time(() -> AlgorithmAPSecond.insertionSort(a3));

        // time O(n log n) sorts
        long tMerge = time(() -> AlgorithmAPSecond.mergeSort(b1));
        long tQuick = time(() -> AlgorithmAPSecond.quickSort(b2));
        long tHeap = time(() -> AlgorithmAPSecond.heapSort(b3));

        // on large n, each O(n²) should be noticeably slower than each O(n log n)
        assertTrue(tBubble > tQuick, String.format("bubble (%d ms) should be slower than quick (%d ms)", tBubble, tQuick));
        assertTrue(tBubble > tMerge, String.format("bubble (%d ms) should be slower than merge (%d ms)", tBubble, tMerge));
        // assertTrue(tBubble > tHeap, String.format("bubble (%d ms) should be slower than heap (%d ms)", tBubble, tHeap));

        // selection vs O(n log n)
        assertTrue(tSelection > tQuick, String.format("selection (%d ms) should be slower than quick (%d ms)", tSelection, tQuick));
        assertTrue(tSelection > tMerge, String.format("selection (%d ms) should be slower than merge (%d ms)", tSelection, tMerge));
        // assertTrue(tSelection > tHeap, String.format("selection (%d ms) should be slower than heap (%d ms)", tSelection, tHeap));
        // insertion vs O(n log n)
        // assertTrue(tInsertion > tQuick, String.format("insertion (%d ms) should be slower than quick (%d ms)", tInsertion, tQuick));
        // assertTrue(tInsertion > tMerge, String.format("insertion (%d ms) should be slower than merge (%d ms)", tInsertion, tMerge));
        // assertTrue(tInsertion > tHeap, String.format("insertion (%d ms) should be slower than heap (%d ms)", tInsertion, tHeap));
    }

    // helper to measure elapsed milliseconds
    private long time(Runnable sortCall) {
        long start = System.nanoTime();
        sortCall.run();
        return (System.nanoTime() - start) / 1_000_000;
    }
}
